package com.sehrish.accidentdetect.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Hospital {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String lat;

    private String lon;

    private long accidentId;

}
