package com.sehrish.accidentdetect.repository;

import com.sehrish.accidentdetect.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findAllByUserId(long userId);
}
