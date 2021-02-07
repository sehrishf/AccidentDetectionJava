package com.sehrish.accidentdetect.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class UserFamilyContact {

    @Id
    @GeneratedValue
    private long id;

    private String mobileno;


}
