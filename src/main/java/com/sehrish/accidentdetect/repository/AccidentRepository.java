package com.sehrish.accidentdetect.repository;

import com.sehrish.accidentdetect.entity.Accident;
import com.sehrish.accidentdetect.entity.Hospital;
import com.sehrish.accidentdetect.entity.Location;
import com.sehrish.accidentdetect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccidentRepository extends JpaRepository<Accident, Long> {

    List<Accident> findAllByUserId(long userId);

    Accident findFirstByUserIdOrderByIdDesc(long userId);

    Accident findByUserAndLatAndLon(User user, String lat, String lon);

    List<Accident> findAllByHospitalOrderByCreatedDate(Hospital hospital);

    List<Accident> findAllByHospitalOrderByCreatedDateDescProcessedAsc(Hospital hospital);

    Accident findById(long id);
}
