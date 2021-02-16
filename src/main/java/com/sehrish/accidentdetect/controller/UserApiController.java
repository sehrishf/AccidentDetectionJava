package com.sehrish.accidentdetect.controller;

import com.sehrish.accidentdetect.dto.AccidentDto;
import com.sehrish.accidentdetect.dto.LocationDto;
import com.sehrish.accidentdetect.dto.UserDto;
import com.sehrish.accidentdetect.dto.UserFriendDto;
import com.sehrish.accidentdetect.entity.Location;
import com.sehrish.accidentdetect.entity.User;
import com.sehrish.accidentdetect.entity.UserFamilyContact;
import com.sehrish.accidentdetect.repository.LocationRepository;
import com.sehrish.accidentdetect.repository.UserFriendRepository;
import com.sehrish.accidentdetect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserApiController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserFriendRepository userFriendRepository;

    @PostMapping("/save")
    public User save(@RequestBody UserDto userDto) {

        User user = new User();

        user =userRepository.findByMobileno(user.getMobileno());

        if(user != null)
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

    @PostMapping("/friendsinfo")
    public User savefriendinfo(@RequestBody UserFriendDto userFriendDto) {

        User user = userRepository.findById(Long.parseLong(userFriendDto.getUserid()));

        List<UserFamilyContact> userFamilyContacts= user.getUserFamilyContacts();
        UserFamilyContact userFamilyContact=new UserFamilyContact();
        userFamilyContact.setMobileno(userFriendDto.getMobileno());

        UserFamilyContact Data=userFriendRepository.saveAndFlush(userFamilyContact);
        userFamilyContacts.add(Data);
        user.setUserFamilyContacts(userFamilyContacts);

        userRepository.saveAndFlush(user);

        return user;
    }

    @PostMapping("/friendslist")
    public List<UserFamilyContact> getfriendinfo(@RequestBody UserDto userDto) {

        User user = userRepository.findById(userDto.getId());
        return user.getUserFamilyContacts();


    }


}

