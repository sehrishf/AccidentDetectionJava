package com.sehrish.accidentdetect.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sehrish.accidentdetect.dto.AccidentDto;
import com.sehrish.accidentdetect.dto.Candidate;
import com.sehrish.accidentdetect.dto.LocationDto;
import com.sehrish.accidentdetect.dto.NearestHospitalsResponse;
import com.sehrish.accidentdetect.entity.Accident;
import com.sehrish.accidentdetect.entity.Hospital;
import com.sehrish.accidentdetect.entity.Location;
import com.sehrish.accidentdetect.repository.AccidentRepository;
import com.sehrish.accidentdetect.repository.HospitalRepository;
import com.sehrish.accidentdetect.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/location")
public class ApiController {

    @Autowired
    LocationRepository locationRepository;
    @Autowired
    AccidentRepository accidentRepository;
    @Autowired
    HospitalRepository hospitalRepository;


    @PostMapping("/save")
    public Location save(@RequestBody LocationDto locationDto) {

        Location location = new Location();
        location.setLat(locationDto.getLat());
        location.setLon(locationDto.getLon());
        location.setUserId(locationDto.getUserId());

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
        accident.setUserId(accidentDtoo.getUserId());

        accident = accidentRepository.saveAndFlush(accident);

        // cal google api for get nearest hospital
        FindNearestHostipal(accidentDtoo.getLat(),accidentDtoo.getLon(), accident.getId());
        //
        return accident;
    }
    private void FindNearestHostipal(String lat, String lon,long accidentid) throws JsonProcessingException {

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
                .queryParam("locationbias", "circle:50@"+lat+","+lon)
                .queryParam("key", "AIzaSyDDJZKw9mlX49vr0vkw4jd7xj2HuVPmCuw");


        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<NearestHospitalsResponse> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                NearestHospitalsResponse.class);

      //  if (response.getBody().status == "OK"){
            Hospital hospital = new Hospital();
            Candidate candidate = response.getBody().candidates.get(0);
            hospital.setName(candidate.getName());
            hospital.setAccidentId(accidentid);
            hospital.setLat(candidate.getGeometry().getLocation().lat + "");
            hospital.setLon(candidate.getGeometry().getLocation().lng + "");

            hospitalRepository.saveAndFlush(hospital);
        //}
        //ObjectMapper objectMapper = new ObjectMapper();
        // Candidates candidates = objectMapper.readValue(response.getBody(), Candidates.class);

    }

}
