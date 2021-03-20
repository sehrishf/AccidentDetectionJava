package com.sehrish.accidentdetect.Service;

import com.pusher.rest.Pusher;
import com.sehrish.accidentdetect.entity.Location;
import com.sehrish.accidentdetect.entity.User;
import com.sehrish.accidentdetect.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private LocationRepository locationRepository;

    private static final String APP_ID = "1168496";
    private static final String KEY = "a9e662200a7506256f55";
    private static final String SECRET = "a473edae383211e0f7dc";

    public void sendNotification(long toUserId, String message) {

        Pusher pusher = new Pusher(APP_ID, KEY, SECRET);
        pusher.setCluster("eu");
        pusher.setEncrypted(true);

        pusher.trigger("my-channel", "my-event-" + toUserId, message);
    }

    public void findAllUserNearByToSendNotification(String lat, String lon, User user) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date today = formatter.parse(formatter.format(new Date()));
        List<Integer> alreadySentUserId = new ArrayList<>();
        int RADIUS_FOR_OTHER_USER_IN_METER = 1000;

        List<Location> locations = locationRepository.findAllUserLatestLocation(today);

        for(Location location: locations) {

            if(location.getUserId() != user.getId() && alreadySentUserId.contains(location.getUserId()) == false) {

                double distance = distanceInMeter(lat, lon, location.getLat(), location.getLon());
                if(distance <= RADIUS_FOR_OTHER_USER_IN_METER) {
                    sendNotification(location.getUserId(), "There is an accident nearby#" + lat + "#" + lon);
                }

                System.out.println(" ----- Distance = " + distance);
            }
            alreadySentUserId.add(Integer.parseInt(location.getUserId() + ""));
        }
    }

    private double distanceInMeter(String strLat1, String strLon1, String strLat2, String strLon2) {

        double lat1 = Double.parseDouble(strLat1);
        double lon1 = Double.parseDouble(strLon1);
        double lat2 = Double.parseDouble(strLat2);
        double lon2 = Double.parseDouble(strLon2);

        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            lon1 = Math.toRadians(lon1);
            lon2 = Math.toRadians(lon2);
            lat1 = Math.toRadians(lat1);
            lat2 = Math.toRadians(lat2);

            double dlon = lon2 - lon1;
            double dlat = lat2 - lat1;
            double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2),2);

            double c = 2 * Math.asin(Math.sqrt(a));

            // Radius of earth in kilometers. Use 3956
            // for miles
            double r = 6371;

            // calculate the result
            return c * r * 1000;
        }
    }

}