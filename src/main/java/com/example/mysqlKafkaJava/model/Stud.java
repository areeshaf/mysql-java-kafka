package com.example.mysqlKafkaJava.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Stud {

    @Id
    private String Stud_id;
    private String Stud_name;
    private String Stud_status;

    public String getStud_id() {
        return Stud_id;
    }

    public Stud() {
    }

    public void setStud_id(String stud_id) {
        Stud_id = stud_id;
    }

    public String getStud_name() {
        return Stud_name;
    }

    public void setStud_name(String stud_name) {
        Stud_name = stud_name;
    }

    public String getStud_status() {
        return Stud_status;
    }

    public void setStud_status(String stud_status) {
        Stud_status = stud_status;
    }
}
