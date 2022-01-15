package com.example.bdt.Classes;

public class RequestBlood {
    static String num;
    private String MyMobile;
    private String date;
    private String Rid;


    public RequestBlood() {
    }

    public RequestBlood(String myMobile, String date, String rid) {
        MyMobile = myMobile;
        this.date = date;
        Rid = rid;
    }

    public String getRid() {
        return Rid;
    }

    public void setRid(String rid) {
        Rid = rid;
    }

    public static String getNum() {
        return num;
    }

    public static void setNum(String num) {
        RequestBlood.num = num;
    }

    public String getMyMobile() {
        return MyMobile;
    }

    public void setMyMobile(String myMobile) {
        MyMobile = myMobile;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
