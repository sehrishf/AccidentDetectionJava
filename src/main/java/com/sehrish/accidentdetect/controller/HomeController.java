package com.sehrish.accidentdetect.controller;

import com.sehrish.accidentdetect.dto.HomeDto;
import com.sehrish.accidentdetect.dto.LocationDto;
import com.sehrish.accidentdetect.entity.Accident;
import com.sehrish.accidentdetect.entity.Hospital;
import com.sehrish.accidentdetect.entity.HospitalUser;
import com.sehrish.accidentdetect.entity.Location;
import com.sehrish.accidentdetect.repository.AccidentRepository;
import com.sehrish.accidentdetect.repository.HospitalRepository;
import com.sehrish.accidentdetect.repository.HospitalUserRepository;
import com.sehrish.accidentdetect.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    LocationRepository locationRepository;

    @Autowired
    HospitalUserRepository hospitalUserRepository;

    @Autowired
    HospitalRepository hospitalRepository;

    @Autowired
    AccidentRepository accidentRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("")
    public String main(HomeDto model) {
       // return "home";
        return "index";
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

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("HospitalUser", new HospitalUser());

        return "signup_form";
    }
    @PostMapping("/process_register")
    public String processRegister(HospitalUser user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);

        hospitalUserRepository.save(user);

        return "register_success";
    }
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<HospitalUser> listUsers = hospitalUserRepository.findAll();
        model.addAttribute("listUsers", listUsers);

        return "hospitals";
    }


    @GetMapping("/accidents")
    public String accidentlist(Model model) {
        return "accidents";
    }
}
