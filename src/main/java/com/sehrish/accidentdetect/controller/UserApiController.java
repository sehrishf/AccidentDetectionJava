package com.sehrish.accidentdetect.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pusher.rest.Pusher;
import com.sehrish.accidentdetect.dto.*;
import com.sehrish.accidentdetect.entity.Hospital;
import com.sehrish.accidentdetect.entity.User;
import com.sehrish.accidentdetect.entity.UserFamilyContact;
import com.sehrish.accidentdetect.repository.UserFriendRepository;
import com.sehrish.accidentdetect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
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

        UserFamilyContact Data = userFriendRepository.saveAndFlush(userFamilyContact);
        userFamilyContacts.add(Data);
        user.setUserFamilyContacts(userFamilyContacts);

        userRepository.saveAndFlush(user);

        return user;
    }

    @PostMapping("/friendslist")
    public List<UserFamilyContact> getfriendinfo(@RequestBody UserDto userDto) {

        Pusher pusher = new Pusher("1168496", "a9e662200a7506256f55", "a473edae383211e0f7dc");
        pusher.setCluster("eu");
        pusher.setEncrypted(true);

        pusher.trigger("my-channel", "my-event", Collections.singletonMap("message", userDto.getName()));

        User user = userRepository.findById(userDto.getId());
        return user.getUserFamilyContacts();
    }

    @PostMapping("/removefriend")
    public void removeFriend(@RequestBody RemoveContactDto removeContactDto) {
        User user = userRepository.findById(removeContactDto.getUserId());

        List<UserFamilyContact> friends = user.getUserFamilyContacts();
        UserFamilyContact userFamilyContact = userFriendRepository.findById(removeContactDto.getFriendId());
        friends.remove(userFamilyContact);

        userRepository.saveAndFlush(user);
    }


}

