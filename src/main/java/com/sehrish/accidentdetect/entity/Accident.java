package com.sehrish.accidentdetect.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data

public class Accident {

    @Id
    @GeneratedValue
    private long id;


    private String lat;


    private String lon;

    private long userId;

    private Date createdDate;
}
