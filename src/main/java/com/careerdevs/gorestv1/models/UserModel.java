package com.careerdevs.gorestv1.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserModel {
    // use Jsonpropery to assign your own term
    //@JsonProperty("id")
    private int id;
    private String name;
    private String email;
    private String gender;
    private String status;

    //DO NOT DELETE OR CHANGE - DEFAULT CONSTRUCTOR
    public UserModel() {
    }

    public UserModel(String name, String email, String gender, String status) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getStatus() {
        return status;
    }
    public  String generatReport(){
        return name +  "is currently "+ status +" you may contact them at" + email;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
