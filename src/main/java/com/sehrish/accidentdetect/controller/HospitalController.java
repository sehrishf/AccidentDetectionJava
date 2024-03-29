package com.sehrish.accidentdetect.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sehrish.accidentdetect.dto.AccidentDto;
import com.sehrish.accidentdetect.entity.Accident;
import com.sehrish.accidentdetect.repository.AccidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hospital/")
public class HospitalController {

    @GetMapping("accidents")
    public String accidents() {

        return "accidents";
    }

    @GetMapping("show-accident-in-map")
    public String GetaccidentMap(@Param("lat") String lat,@Param("lon")String lon, Model model) {

        AccidentDto accidentDto =new AccidentDto();
        accidentDto.setLat(lat);
        accidentDto.setLon(lon);
        model.addAttribute("accidentDto", accidentDto);
        return "traceaccidentlocation";
    }


}