package com.sehrish.accidentdetect.controller;

import com.sehrish.accidentdetect.dto.AccidentDto;
import com.sehrish.accidentdetect.dto.HomeDto;
import com.sehrish.accidentdetect.dto.LocationDto;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/user")
@Controller
public class UserController {
    @GetMapping("/friend-accident")
    public String accidentlocation(@Param("userid") String uid,@Param("lat") String lat,@Param("lon") String lon, Model model) {

        AccidentDto accidentDto=new AccidentDto();
        accidentDto.setLat(lat);
        accidentDto.setLon(lon);
        accidentDto.setUserId(Integer.parseInt(uid));
        model.addAttribute("accidentDto", accidentDto);
        return "tracelocation";
    }
}
