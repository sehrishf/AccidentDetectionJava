package com.sehrish.accidentdetect.entity;

import lombok.Data;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    private String mobileno;

    private String name;

    private String email;

    private String password;

    @OneToMany
    private List<UserFamilyContact> userFamilyContacts;
}
