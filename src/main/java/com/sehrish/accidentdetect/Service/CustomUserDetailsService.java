package com.sehrish.accidentdetect.Service;

import com.sehrish.accidentdetect.controller.CustomUserDetails;
import com.sehrish.accidentdetect.entity.HospitalUser;
import com.sehrish.accidentdetect.repository.HospitalUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private HospitalUserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HospitalUser user = userRepo.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Hospital not found");
        }
        return new CustomUserDetails(user);
    }

}