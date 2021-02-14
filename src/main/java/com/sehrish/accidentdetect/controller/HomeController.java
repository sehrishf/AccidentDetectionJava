package com.sehrish.accidentdetect.controller;

import com.sehrish.accidentdetect.dto.AccidentDto;
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
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
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


    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

   // private BCryptPasswordEncoder bCryptPasswordEncoder;

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

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("HospitalUser", new HospitalUser());
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("HospitalUser", new HospitalUser());

        return "signup_form";
    }
    @PostMapping("/process_register")
    public String processRegister(HospitalUser user) {

        var userData=hospitalUserRepository.findByHospitalname(user.getHospitalname());
        if(userData != null){
               // it should be sugn up page but it required model to pass hwo can i??
            return "index";
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        hospitalUserRepository.save(user);
        return "register_success";
    }
    @PostMapping("/verify_user")
    public String verifyUser(HospitalUser user, HttpSession session) {

    //  BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
   //   String encodedPassword = passwordEncoder.encode(user.getPassword());
  //   user.setPassword(encodedPassword);

        var hospitalUser=hospitalUserRepository.findByEmail(user.getEmail());

        if(bCryptPasswordEncoder.matches(user.getPassword(),hospitalUser.getPassword())){
            session.setAttribute("hospitalUser", hospitalUser);
            return "redirect:hospital/accidents";
        }

        return "error";
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
