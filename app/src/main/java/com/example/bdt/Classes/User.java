package com.example.bdt.Classes;

import java.util.Date;

public class User {
    private String firstName;
    private String lastName;
    private String mobile;
    private String password;
    private String city;
    private String nationalatiy;
    private String bloodGroup;
    private String address;
    private String gender;

    public User() {

    }

    public User(String firstName, String lastName, String mobile, String password, String city, String nationalatiy, Date dateOfBarth, String bloodGroup, String address, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.password = password;
        this.city = city;
        this.nationalatiy = nationalatiy;
        this.bloodGroup = bloodGroup;
        this.address = address;
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNationalatiy() {
        return nationalatiy;
    }

    public void setNationalatiy(String nationalatiy) {
        this.nationalatiy = nationalatiy;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
