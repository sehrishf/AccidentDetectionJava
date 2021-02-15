package com.sehrish.accidentdetect.controller;

import com.sehrish.accidentdetect.dto.*;
import com.sehrish.accidentdetect.entity.Accident;
import com.sehrish.accidentdetect.repository.AccidentRepository;
import com.sehrish.accidentdetect.repository.HospitalRepository;
import com.sehrish.accidentdetect.repository.LocationRepository;
import com.sehrish.accidentdetect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/accident")
public class AccidentApiController {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private AccidentRepository accidentRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/updateisprocess")
    public Accident save(@RequestBody AccidentDto accidentDto) {

        Accident accident=accidentRepository.findById(accidentDto.getId());
        accident.setProcessed(accidentDto.isProcessed());
        accident = accidentRepository.saveAndFlush(accident);

        return accident;
    }





}
