package com.sehrish.accidentdetect.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data

public class Accident {

    @Id
    @GeneratedValue
    private long id;

    private String lat;

    private String lon;

    @OneToOne
    private Hospital hospital;

    @OneToOne
    private User user;

    private Date createdDate;

    @Column(nullable = false, columnDefinition="Boolean default false")
    private Boolean processed;
}
