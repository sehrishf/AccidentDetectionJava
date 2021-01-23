package com.sehrish.accidentdetect.controller;

import com.sehrish.accidentdetect.dto.LocationDto;
import com.sehrish.accidentdetect.entity.Location;
import com.sehrish.accidentdetect.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/location")
public class ApiController {

    @Autowired
    LocationRepository locationRepository;

    @PostMapping("/save")
    public Location save(@RequestBody LocationDto locationDto) {

        Location location = new Location();
        location.setLat(locationDto.getLat());
        location.setLon(locationDto.getLon());
        location.setUserId(locationDto.getUserId());

        location = locationRepository.saveAndFlush(location);

        return location;
    }

    @PostMapping("/get")
    public List<Location> getUserLocations(@RequestBody LocationDto locationDto) {
        List<Location> locations = locationRepository.findAllByUserId(locationDto.getUserId());
        return locations;
    }

}
