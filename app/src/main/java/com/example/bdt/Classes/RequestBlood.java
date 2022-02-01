package com.example.bdt.Classes;

public class RequestBlood {

    private String MyPhoneNumber;
    private String DonationDate;
    private String RecordId;


    public RequestBlood() {
    }

    public RequestBlood(String mobileNumber, String date, String rid) {
        MyPhoneNumber = mobileNumber;
        this.DonationDate = date;
        RecordId = rid;
    }

    public String getRid() {
        return RecordId;
    }

    public void setRid(String rid) {
        RecordId = rid;
    }



    public String getMobileNumber() {
        return MyPhoneNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MyPhoneNumber = mobileNumber;
    }

    public String getDate() {
        return DonationDate;
    }

    public void setDate(String date) {
        this.DonationDate = date;
    }

}
