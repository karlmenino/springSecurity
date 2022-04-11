package com.example.qcm.qcm.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private RoleEnum username;
    @ManyToMany
    private List<User> users=new ArrayList<>();
    public Role() {
    }

    public Role(RoleEnum username) {
        this.username = username;
    }
}
