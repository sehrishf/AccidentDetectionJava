package com.sehrish.accidentdetect.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data

public class Location {
    @Id @GeneratedValue
    private long id;


    private String lat;


    private String lon;

    private long userId;

    private Date createdDate;
}
