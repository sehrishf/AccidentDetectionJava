package com.sehrish.accidentdetect.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class HospitalUser {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(nullable = false, length = 60)
    private String hospitalname;



    // getters and setters are not shown
}
