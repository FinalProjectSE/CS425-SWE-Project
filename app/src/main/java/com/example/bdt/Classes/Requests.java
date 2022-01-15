package com.example.bdt.Classes;

public class Requests {
        private String FuName;
        private String BloodGroup;
        private String Mobile;
        private int NumberOfUnites;
        private String HospitalName;
        private String userid;


        public Requests() {
        }

        public Requests(String fuName, String bloodGroup, String mobile, int numberOfUnites, String hospitalName, String userid) {
            FuName = fuName;
            BloodGroup = bloodGroup;
            Mobile = mobile;
            NumberOfUnites = numberOfUnites;
            HospitalName = hospitalName;
            this.userid = userid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
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

        public String getFuName() {
            return FuName;
        }

        public void setFuName(String fuName) {
            FuName = fuName;
        }

        public String getBloodGroup() {
            return BloodGroup;
        }

        public void setBloodGroup(String bloodGroup) {
            BloodGroup = bloodGroup;
        }


        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String mobile) {
            Mobile = mobile;
        }
}
