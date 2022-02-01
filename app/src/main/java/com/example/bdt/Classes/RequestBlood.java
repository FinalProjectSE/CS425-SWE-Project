package com.example.bdt.Classes;

public class RequestBlood {

    private String MobileNumber;
    private String Date;
    private String Rid;


    public RequestBlood() {
    }

    public RequestBlood(String mobileNumber, String date, String rid) {
        MobileNumber = mobileNumber;
        this.Date = date;
        Rid = rid;
    }

    public String getRid() {
        return Rid;
    }

    public void setRid(String rid) {
        Rid = rid;
    }



    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

}
