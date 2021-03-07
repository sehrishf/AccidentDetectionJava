package com.sehrish.accidentdetect.repository;

import com.sehrish.accidentdetect.entity.Accident;
import com.sehrish.accidentdetect.entity.Hospital;
import com.sehrish.accidentdetect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AccidentRepository extends JpaRepository<Accident, Long> {

    List<Accident> findAllByUserId(long userId);

    @Query("select a from Accident a where a.createdDate >= :startDateTime and a.createdDate <= :endDateTime " +
            "and a.user = :user")
    List<Accident> findAccidentByUserAndDateTimeRange(Date startDateTime, Date endDateTime, User user);

    Accident findFirstByUserIdOrderByIdDesc(long userId);

    Accident findByUserAndLatAndLon(User user, String lat, String lon);

    List<Accident> findAllByHospitalOrderByCreatedDate(Hospital hospital);

    List<Accident> findAllByHospitalOrderByProcessedAscCreatedDateDesc(Hospital hospital);

    Accident findById(long id);
}
