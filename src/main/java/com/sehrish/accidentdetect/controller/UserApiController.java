package com.sehrish.accidentdetect.controller;

import com.sehrish.accidentdetect.dto.LocationDto;
import com.sehrish.accidentdetect.dto.UserDto;
import com.sehrish.accidentdetect.entity.Location;
import com.sehrish.accidentdetect.entity.User;
import com.sehrish.accidentdetect.repository.LocationRepository;
import com.sehrish.accidentdetect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserApiController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/save")
    public User save(@RequestBody UserDto userDto) {

        User user = new User();

        user =userRepository.findByMobileno(user.getMobileno());

        if(user == null)
        {
            user = new User();
            user.setId(0);
            return user;
        }
        user = new User();
        user.setName(userDto.getName());
        user.setMobileno(userDto.getMobileno());
        user.setEmail(userDto.getEmail());

        String encodedPassword = bCryptPasswordEncoder.encode(userDto.getPassword());
        user.setPassword(encodedPassword);


        user = userRepository.saveAndFlush(user);

        return user;
    }

    @PostMapping("/get-user-by-mobile")
    public User getmobile(@RequestBody UserDto userDto) {

        User user = userRepository.findByMobileno(userDto.getMobileno());

        if(bCryptPasswordEncoder.matches(userDto.getPassword(),user.getPassword())){
            return user;
        }

        return null;
    }

}

