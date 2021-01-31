package com.sehrish.accidentdetect.repository;

import com.sehrish.accidentdetect.entity.Accident;
import com.sehrish.accidentdetect.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    List<Hospital> findAllByAccidentId(long accidentId);
    Hospital findFirstByAccidentIdOrderByIdDesc(long accidentId);

}

