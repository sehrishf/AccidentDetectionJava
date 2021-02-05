package com.sehrish.accidentdetect.repository;

import com.sehrish.accidentdetect.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    Hospital findFirstByName(String name);
}

