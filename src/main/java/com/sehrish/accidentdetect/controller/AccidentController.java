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

@Controller
public class AccidentController {

    @Autowired
    AccidentRepository accidentRepository;

    @GetMapping("/web/accidentlocation")
    public String Test(@Param("uid") String uid, Model model) throws JsonProcessingException {
        long userId = Long.parseLong(uid);
        Accident accident = accidentRepository.findFirstByUserIdOrderByIdDesc(userId);
        if (accident == null) {
            AccidentDto accidentDto = new AccidentDto();
        } else {
            AccidentDto accidentDto = AccidentDto.builder()
                    .userId(userId)
                    .lat(accident.getLat())
                    .lon(accident.getLon())
                    .createDate(accident.getCreatedDate())
                    .build();
            model.addAttribute("accidentDto", accidentDto);
        }

        return "accidentlocation";
    }

}