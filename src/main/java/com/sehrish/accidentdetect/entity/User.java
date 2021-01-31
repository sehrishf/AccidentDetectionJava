package com.sehrish.accidentdetect.entity;

import lombok.Data;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;

import javax.persistence.*;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    private String mobileno;

    private String name;
}
