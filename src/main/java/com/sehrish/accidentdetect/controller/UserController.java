package com.sehrish.accidentdetect.controller;

import com.sehrish.accidentdetect.entity.Accident;
import com.sehrish.accidentdetect.entity.User;
import com.sehrish.accidentdetect.repository.AccidentRepository;
import com.sehrish.accidentdetect.repository.HospitalRepository;
import com.sehrish.accidentdetect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private AccidentRepository accidentRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/friend-accident")
    public String accidentlocation(@Param("uid") String uid, @Param("lat") String lat,
            @Param("lon") String lon, Model model) {

        User user = userRepository.findById(Integer.parseInt(uid));
        Accident accident = accidentRepository.findByUserAndLatAndLon(user, lat, lon);

        model.addAttribute("accident", accident);
        return "trace-friend-accident";
    }
}
