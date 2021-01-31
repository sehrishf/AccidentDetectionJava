package com.sehrish.accidentdetect.controller;

import com.sehrish.accidentdetect.dto.HomeDto;
import com.sehrish.accidentdetect.dto.LocationDto;
import com.sehrish.accidentdetect.entity.Location;
import com.sehrish.accidentdetect.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    LocationRepository locationRepository;

    @GetMapping("")
    public String main(HomeDto model) {
        return "home";
    }

    @GetMapping("/web/tracelocation")
    public String Test(@Param("uid") String uid, Model model) {
        long userId = Long.parseLong(uid);
        Location location = locationRepository.findFirstByUserIdOrderByIdDesc(userId);
        LocationDto locationDto = LocationDto.builder()
                .userId(userId)
                .lat(location.getLat())
                .lon(location.getLon())
                .createdDate(location.getCreatedDate())
                .build();

        model.addAttribute("locationObj", locationDto);
        return "tracelocation";
    }
}
