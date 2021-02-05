package com.sehrish.accidentdetect.repository;

import com.sehrish.accidentdetect.entity.HospitalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalUserRepository extends JpaRepository<HospitalUser, Long> {

 public HospitalUser findByEmail(String email);

}