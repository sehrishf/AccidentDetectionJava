package com.sehrish.accidentdetect.repository;

import com.sehrish.accidentdetect.entity.Location;
import com.sehrish.accidentdetect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByMobileno(String mobileno);

    User findByMobilenoAndPassword(String mobileno,String passwprd);

    User findById(long id);

}
