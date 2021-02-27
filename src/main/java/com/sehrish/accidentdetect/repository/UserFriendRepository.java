package com.sehrish.accidentdetect.repository;

import com.sehrish.accidentdetect.entity.User;
import com.sehrish.accidentdetect.entity.UserFamilyContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFriendRepository extends JpaRepository<UserFamilyContact, Long> {

    UserFamilyContact findById(long id);

}
