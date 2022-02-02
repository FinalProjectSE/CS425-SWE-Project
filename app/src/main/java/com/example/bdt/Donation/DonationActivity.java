package com.example.bdt.Donation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.bdt.Classes.Requests;
import com.example.bdt.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class DonationActivity extends AppCompatActivity {

    private Firebase RequestBloodFireBase;
    RecyclerView recyclerView;
    Requests requests = new Requests();
    ArrayList<Requests> requestss = new ArrayList<>();
    String bloodGroup, myFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);

        Firebase.setAndroidContext(this);

        recyclerView = (RecyclerView) findViewById(R.id.myRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        bloodGroup = getIntent().getStringExtra("BloodGroup");
        myFullName = getIntent().getStringExtra("FullName");

        RequestBloodFireBase = new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/RequestBlood");

        RequestBloodFireBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    HashMap<String, Object> map = dataSnapshot1.getValue(HashMap.class);
                    String MobileFromFireBase = (String) map.get("mobileNumber");
                    String bloodGroupFromFireBase = (String) map.get("bloodGroup");
                    int numberOfUnites = (int) map.get("numberOfUnites");
                    if (numberOfUnites > 0) {

                        if (!(getIntent().getStringExtra("Mobile").equals(MobileFromFireBase))) {
                            switch (bloodGroup) {
                                case "O-":
                                    requests = new Requests();
                                    requests.setFullName((String) map.get("fullName"));
                                    requests.setMobileNumber((String) map.get("mobileNumber"));
                                    requests.setHospitalName((String) map.get("hospitalName"));
                                    requests.setBloodGroup((String) map.get("bloodGroup"));
                                    requests.setNumberOfUnites((int) map.get("numberOfUnites"));
                                    requests.setUserid(dataSnapshot1.getKey());
                                    requestss.add(requests);
                                    break;

                                case "O+":
                                    if (bloodGroupFromFireBase.contains("+")) {
                                        requests = new Requests();
                                        requests.setFullName((String) map.get("fullName"));
                                        requests.setMobileNumber((String) map.get("mobileNumber"));
                                        requests.setHospitalName((String) map.get("hospitalName"));
                                        requests.setBloodGroup((String) map.get("bloodGroup"));
                                        requests.setNumberOfUnites((int) map.get("numberOfUnites"));
                                        requests.setUserid(dataSnapshot1.getKey());
                                        requestss.add(requests);
                                    }
                                    break;

                                case "A-":
                                    if (bloodGroupFromFireBase.contains("A")) {
                                        requests = new Requests();
                                        requests.setFullName((String) map.get("fullName"));
                                        requests.setMobileNumber((String) map.get("mobileNumber"));
                                        requests.setHospitalName((String) map.get("hospitalName"));
                                        requests.setBloodGroup((String) map.get("bloodGroup"));
                                        requests.setNumberOfUnites((int) map.get("numberOfUnites"));
                                        requests.setUserid(dataSnapshot1.getKey());
                                        requestss.add(requests);

                                    }
                                    break;

                                case "A+":
                                    if (bloodGroupFromFireBase.equalsIgnoreCase("ab+") || bloodGroupFromFireBase.equalsIgnoreCase("a+")) {
                                        requests = new Requests();
                                        requests.setFullName((String) map.get("fullName"));
                                        requests.setMobileNumber((String) map.get("mobileNumber"));
                                        requests.setHospitalName((String) map.get("hospitalName"));
                                        requests.setBloodGroup((String) map.get("bloodGroup"));
                                        requests.setNumberOfUnites((int) map.get("numberOfUnites"));
                                        requests.setUserid(dataSnapshot1.getKey());
                                        requestss.add(requests);

                                    }
                                    break;

                                case "B-":
                                    if (bloodGroupFromFireBase.contains("B")) {
                                        requests = new Requests();
                                        requests.setFullName((String) map.get("fullName"));
                                        requests.setMobileNumber((String) map.get("mobileNumber"));
                                        requests.setHospitalName((String) map.get("hospitalName"));
                                        requests.setBloodGroup((String) map.get("bloodGroup"));
                                        requests.setNumberOfUnites((int) map.get("numberOfUnites"));
                                        requests.setUserid(dataSnapshot1.getKey());
                                        requestss.add(requests);

                                    }
                                    break;

                                case "B+":
                                    if (bloodGroupFromFireBase.equalsIgnoreCase("b+") || bloodGroupFromFireBase.equalsIgnoreCase("ab+")) {
                                        requests = new Requests();
                                        requests.setFullName((String) map.get("fullName"));
                                        requests.setMobileNumber((String) map.get("mobileNumber"));
                                        requests.setHospitalName((String) map.get("hospitalName"));
                                        requests.setBloodGroup((String) map.get("bloodGroup"));
                                        requests.setNumberOfUnites((int) map.get("numberOfUnites"));
                                        requests.setUserid(dataSnapshot1.getKey());
                                        requestss.add(requests);
                                    }
                                    break;

                                case "AB-":
                                    if (bloodGroupFromFireBase.contains("AB")) {
                                        requests = new Requests();
                                        requests.setFullName((String) map.get("fullName"));
                                        requests.setMobileNumber((String) map.get("mobileNumber"));
                                        requests.setHospitalName((String) map.get("hospitalName"));
                                        requests.setBloodGroup((String) map.get("bloodGroup"));
                                        requests.setNumberOfUnites((int) map.get("numberOfUnites"));
                                        requests.setUserid(dataSnapshot1.getKey());
                                        requestss.add(requests);
                                    }
                                    break;

                                case "AB+":
                                    if (bloodGroupFromFireBase.equalsIgnoreCase("ab+")) {
                                        requests = new Requests();
                                        requests.setFullName((String) map.get("fullName"));
                                        requests.setMobileNumber((String) map.get("mobileNumber"));
                                        requests.setHospitalName((String) map.get("hospitalName"));
                                        requests.setBloodGroup((String) map.get("bloodGroup"));
                                        requests.setNumberOfUnites((int) map.get("numberOfUnites"));
                                        requests.setUserid(dataSnapshot1.getKey());
                                        requestss.add(requests);
                                    }
                                    break;


                            }

                        }
                    }
                }

                RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getApplicationContext(), requestss, getIntent().getStringExtra("Mobile"));
                recyclerView.setAdapter(recyclerViewAdapter);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}