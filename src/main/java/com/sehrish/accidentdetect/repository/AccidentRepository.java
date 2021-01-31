package com.sehrish.accidentdetect.repository;

import com.sehrish.accidentdetect.entity.Accident;
import com.sehrish.accidentdetect.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

    @Repository
    public interface AccidentRepository extends JpaRepository<Accident, Long> {
        List<Accident> findAllByUserId(long userId);
        Accident findFirstByUserIdOrderByIdDesc(long userId);

    }