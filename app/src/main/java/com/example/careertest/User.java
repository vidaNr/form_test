package com.example.careertest;

import java.io.Serializable;

public class User implements Serializable {


    private String name;
    private String marital_Status;
    private String gender;
    private String birthDay;
//
//    public User(String name, String marital_Status, String gender, String birthDay) {
//        this.name = name;
//        this.marital_Status = marital_Status;
//        this.gender = gender;
//        this.birthDay = birthDay;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarital_Status() {
        return marital_Status;
    }

    public void setMarital_Status(String marital_Status) {
        this.marital_Status = marital_Status;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }
}
