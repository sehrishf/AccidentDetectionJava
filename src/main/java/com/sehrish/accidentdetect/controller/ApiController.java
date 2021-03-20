package com.sehrish.accidentdetect.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sehrish.accidentdetect.Service.NotificationService;
import com.sehrish.accidentdetect.SessionHelper;
import com.sehrish.accidentdetect.dto.*;
import com.sehrish.accidentdetect.entity.*;
import com.sehrish.accidentdetect.entity.Location;
import com.sehrish.accidentdetect.repository.AccidentRepository;
import com.sehrish.accidentdetect.repository.HospitalRepository;
import com.sehrish.accidentdetect.repository.LocationRepository;
import com.sehrish.accidentdetect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/location")
public class ApiController {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private AccidentRepository accidentRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    NotificationService notificationService;

    @PostMapping("/save")
    public Location save(@RequestBody LocationDto locationDto) {

        Location location = new Location();
        location.setLat(locationDto.getLat());
        location.setLon(locationDto.getLon());
        location.setUserId(locationDto.getUserId());
        location.setCreatedDate(new Date());

        location = locationRepository.saveAndFlush(location);

        return location;
    }

    @PostMapping("/get-location-by-userid")
    public Location getLocationByUser(@RequestBody LocationDto locationDto) {

        Location location = new Location();
        location.setUserId(locationDto.getUserId());

        location = locationRepository.findFirstByUserIdOrderByIdDesc(location.getUserId());

        return location;
    }

    @PostMapping("/get")
    public List<Location> getUserLocations(@RequestBody LocationDto locationDto) {
        List<Location> locations = locationRepository.findAllByUserId(locationDto.getUserId());
        return locations;
    }

    @PostMapping("/save-accident")
    public Accident save(@RequestBody AccidentDto accidentDtoo) throws JsonProcessingException {

        Accident accident = new Accident();
        accident.setLat(accidentDtoo.getLat());
        accident.setLon(accidentDtoo.getLon());
        accident.setCreatedDate(new Date());
        accident.setProcessed(false);

        User user = userRepository.findById(accidentDtoo.getUserId());

        Date endDateTimeRange = new Date();
        Date startDateTimeRange = new Date(System.currentTimeMillis() - (4 * 60 * 60 * 1000));

        accidentRepository.findAccidentByUserAndDateTimeRange(startDateTimeRange, endDateTimeRange, user);
        Hospital hospital = findNearestHostipal(accidentDtoo.getLat(), accidentDtoo.getLon(), accident.getId());

        accident.setUser(user);
        accident.setHospital(hospital);
        accident = accidentRepository.saveAndFlush(accident);

        try {
            notificationService.findAllUserNearByToSendNotification(accidentDtoo.getLat(), accidentDtoo.getLon());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return accident;
    }

    private Hospital findNearestHostipal(String lat, String lon, long accidentid) throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        // https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=hospital
        // &inputtype=textquery&fields=photos,formatted_address,name,rating,opening_hours,geometry
        // &locationbias=circle:50@50.1201,8.6521&key=AIzaSyDDJZKw9mlX49vr0vkw4jd7xj2HuVPmCuw

        String url = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json";
        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("input", "hospital")
                .queryParam("inputtype", "textquery")
                .queryParam("fields", "formatted_address,name,rating,opening_hours,geometry")
                .queryParam("locationbias", "circle:1000@" + lat + "," + lon)
                .queryParam("key", "AIzaSyDDJZKw9mlX49vr0vkw4jd7xj2HuVPmCuw");


        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<NearestHospitalsResponse> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                NearestHospitalsResponse.class);

        Candidate candidate = response.getBody().candidates.get(0);
        String hospitalName = candidate.getName();
        String hospitalLat = candidate.getGeometry().getLocation().lat + "";
        String hospitalLon = candidate.getGeometry().getLocation().lng + "";

        Hospital hospital = hospitalRepository.findFirstByName(hospitalName);

        if(hospital == null) {
            hospital = new Hospital();
            hospital.setLon(hospitalLon);
            hospital.setLat(hospitalLat);
            hospital.setName(hospitalName);
            hospital = hospitalRepository.saveAndFlush(hospital);
        }

        return hospital;
    }

    @GetMapping("/save-all-hospital")
    private void FindAlltHostipal() throws JsonProcessingException {

        if(hospitalRepository.findAll().size() == 0) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
//https://maps.googleapis.com/maps/api/place/textsearch/json?query=frankfurt&type=hospital&key=AIzaSyDDJZKw9mlX49vr0vkw4jd7xj2HuVPmCuw

            String url = "https://maps.googleapis.com/maps/api/place/textsearch/json";
            RestTemplate restTemplate = new RestTemplate();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("query", "frankfurt")
                    .queryParam("type", "hospital")
                    .queryParam("key", "AIzaSyDDJZKw9mlX49vr0vkw4jd7xj2HuVPmCuw");


            HttpEntity<?> entity = new HttpEntity<>(headers);

            HttpEntity<HospitalsResponse> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    HospitalsResponse.class);

            //  if (response.getBody().status == "OK"){
            for (Result result : response.getBody().getResults()) {
                Geometry geometry = result.geometry;
                Hospital hospitalList = new Hospital();
                hospitalList.setLon(geometry.getLocation().getLng() + "");
                hospitalList.setLat(geometry.getLocation().getLat() + "");
                hospitalList.setName(result.getName());
                hospitalRepository.saveAndFlush(hospitalList);
            }
        }
    }

    @GetMapping("/get-accidents")
    public List<Accident> getAccidents() {

        HospitalUser loggedInHosppitalUser=SessionHelper.getLoggedInHospital();
        Hospital hospital = hospitalRepository.findFirstByName(loggedInHosppitalUser.getHospitalname());
        List<Accident> accidents = accidentRepository.findAllByHospitalOrderByProcessedAscCreatedDateDesc(hospital);

        return accidents;
    }


}
