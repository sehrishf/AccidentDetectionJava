package com.sehrish.accidentdetect.repository;

import com.sehrish.accidentdetect.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findAllByUserId(long userId);


    Location findFirstByUserIdOrderByIdDesc(long userId);


   /* @Query("SELECT * FROM Location l WHERE l.userId = :userId order by id desc limit 1")
    public Location find(@Param("lastName") long userId);*/
}
