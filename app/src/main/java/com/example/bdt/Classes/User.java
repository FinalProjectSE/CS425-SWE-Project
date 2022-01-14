package com.example.bdt.Classes;

import java.util.Date;

public class User {
    private String FName;
    private String LName;
    private String Mobile;
    private String Password;
    private String City;
    private String Nationalatiy;
    private Date DateOfBarth;
    private String BloodGroup;
    private String Address;
    private String Gender;

    public User() {

    }

    public User(String FName, String LName, String mobile, String password, String city, String nationalatiy, Date dateOfBarth, String bloodGroup, String address, String gender) {
        this.FName = FName;
        this.LName = LName;
        Mobile = mobile;
        Password = password;
        City = city;
        Nationalatiy = nationalatiy;
        DateOfBarth = dateOfBarth;
        BloodGroup = bloodGroup;
        Address = address;
        Gender = gender;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getNationalatiy() {
        return Nationalatiy;
    }

    public void setNationalatiy(String nationalatiy) {
        Nationalatiy = nationalatiy;
    }

    public Date getDateOfBarth() {
        return DateOfBarth;
    }

    public void setDateOfBarth(Date dateOfBarth) {
        DateOfBarth = dateOfBarth;
    }

    public String getBloodGroup() {
        return BloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        BloodGroup = bloodGroup;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

}
