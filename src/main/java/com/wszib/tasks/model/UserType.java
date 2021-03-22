package com.wszib.tasks.model;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Component
@Entity
@Table(name = "usertype")
public class UserType implements Serializable {

    public static String USER_TYPE_ADMIN = "ADMIN";
    public static String USER_TYPE_LEADER = "LEADER";
    public static String USER_TYPE_USER = "USER";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "type")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UserType: id="+id+", type="+type;
    }
}
