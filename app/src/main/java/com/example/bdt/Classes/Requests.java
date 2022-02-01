package com.example.bdt.Classes;

public class Requests {
    private String FullName;
    private String BloodGroup;
    private String MobileNumber;
    private int NumberOfUnites;
    private String HospitalName;
    private String userid;
    private String RequestDate;

    public Requests(String fullName, String bloodGroup, String mobileNumber, int numberOfUnites, String hospitalName, String userid, String requestDate) {
        FullName = fullName;
        BloodGroup = bloodGroup;
        MobileNumber = mobileNumber;
        NumberOfUnites = numberOfUnites;
        HospitalName = hospitalName;
        this.userid = userid;
        RequestDate = requestDate;
    }

    public Requests() {
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getBloodGroup() {
        return BloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        BloodGroup = bloodGroup;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public int getNumberOfUnites() {
        return NumberOfUnites;
    }

    public void setNumberOfUnites(int numberOfUnites) {
        NumberOfUnites = numberOfUnites;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String hospitalName) {
        HospitalName = hospitalName;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRequestDate() {
        return RequestDate;
    }

    public void setRequestDate(String requestDate) {
        RequestDate = requestDate;
    }
}
