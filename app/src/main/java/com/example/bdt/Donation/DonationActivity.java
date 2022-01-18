package com.example.bdt.Donation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    private Firebase mDatabase;
    RecyclerView recyclerView;
    Requests p1 = new Requests();
    ArrayList<Requests> m = new ArrayList<>();
    String b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);

        Firebase.setAndroidContext(this);

        recyclerView = (RecyclerView)findViewById(R.id.myRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        b=getIntent().getStringExtra("BloodGroup");

        mDatabase = new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/RequestBlood");


        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    HashMap<String, Object> map = dataSnapshot1.getValue(HashMap.class);
                    String m2 = (String) map.get("mobile");
                    String bg = (String) map.get("bloodGroup");
                    if(((int)map.get("numberOfUnites"))>0) {

                        if (!(m2.equals(getIntent().getStringExtra("Mobile")))) {
                            switch (b)
                            {
                                case "O-":
                                    p1 = new Requests();
                                    p1.setFuName((String) map.get("fuName"));
                                    p1.setMobile((String) map.get("mobile"));
                                    p1.setHospitalName((String) map.get("hospitalName"));
                                    p1.setBloodGroup((String) map.get("bloodGroup"));
                                    p1.setNumberOfUnites((int) map.get("numberOfUnites"));
                                    p1.setUserid(dataSnapshot1.getKey());
                                    m.add(p1);
                                    break;

                                case "O+":
                                    if (bg.equalsIgnoreCase("o+") || bg.equalsIgnoreCase("a+") ||
                                            bg.equalsIgnoreCase("ab+") || bg.equalsIgnoreCase("b+")){
                                        p1 = new Requests();
                                        p1.setFuName((String) map.get("fuName"));
                                        p1.setMobile((String) map.get("mobile"));
                                        p1.setHospitalName((String) map.get("hospitalName"));
                                        p1.setBloodGroup((String) map.get("bloodGroup"));
                                        p1.setNumberOfUnites((int) map.get("numberOfUnites"));
                                        p1.setUserid(dataSnapshot1.getKey());
                                        m.add(p1);
                                    }
                                    break;

                                case "A-":
                                    if (bg.contains("A")){
                                        p1 = new Requests();
                                        p1.setFuName((String) map.get("fuName"));
                                        p1.setMobile((String) map.get("mobile"));
                                        p1.setHospitalName((String) map.get("hospitalName"));
                                        p1.setBloodGroup((String) map.get("bloodGroup"));
                                        p1.setNumberOfUnites((int) map.get("numberOfUnites"));
                                        p1.setUserid(dataSnapshot1.getKey());
                                        m.add(p1);

                                    }
                                    break;

                                case "A+":
                                    if (bg.equalsIgnoreCase("ab+") || bg.equalsIgnoreCase("a+")){
                                        p1 = new Requests();
                                        p1.setFuName((String) map.get("fuName"));
                                        p1.setMobile((String) map.get("mobile"));
                                        p1.setHospitalName((String) map.get("hospitalName"));
                                        p1.setBloodGroup((String) map.get("bloodGroup"));
                                        p1.setNumberOfUnites((int) map.get("numberOfUnites"));
                                        p1.setUserid(dataSnapshot1.getKey());
                                        m.add(p1);

                                    }
                                    break;

                                case "B-":
                                    if (bg.contains("B")){
                                        p1 = new Requests();
                                        p1.setFuName((String) map.get("fuName"));
                                        p1.setMobile((String) map.get("mobile"));
                                        p1.setHospitalName((String) map.get("hospitalName"));
                                        p1.setBloodGroup((String) map.get("bloodGroup"));
                                        p1.setNumberOfUnites((int) map.get("numberOfUnites"));
                                        p1.setUserid(dataSnapshot1.getKey());
                                        m.add(p1);

                                    }
                                    break;

                                case "B+":
                                    if (bg.equalsIgnoreCase("b+") || bg.equalsIgnoreCase("ab+"))
                                    {
                                        p1 = new Requests();
                                        p1.setFuName((String) map.get("fuName"));
                                        p1.setMobile((String) map.get("mobile"));
                                        p1.setHospitalName((String) map.get("hospitalName"));
                                        p1.setBloodGroup((String) map.get("bloodGroup"));
                                        p1.setNumberOfUnites((int) map.get("numberOfUnites"));
                                        p1.setUserid(dataSnapshot1.getKey());
                                        m.add(p1);
                                    }
                                    break;

                                case "AB-":
                                    if (bg.contains("AB")){
                                        p1 = new Requests();
                                        p1.setFuName((String) map.get("fuName"));
                                        p1.setMobile((String) map.get("mobile"));
                                        p1.setHospitalName((String) map.get("hospitalName"));
                                        p1.setBloodGroup((String) map.get("bloodGroup"));
                                        p1.setNumberOfUnites((int) map.get("numberOfUnites"));
                                        p1.setUserid(dataSnapshot1.getKey());
                                        m.add(p1);
                                    }
                                    break;

                                case "AB+":
                                    if (bg.equalsIgnoreCase("ab+"))
                                    {
                                        p1 = new Requests();
                                        p1.setFuName((String) map.get("fuName"));
                                        p1.setMobile((String) map.get("mobile"));
                                        p1.setHospitalName((String) map.get("hospitalName"));
                                        p1.setBloodGroup((String) map.get("bloodGroup"));
                                        p1.setNumberOfUnites((int) map.get("numberOfUnites"));
                                        p1.setUserid(dataSnapshot1.getKey());
                                        m.add(p1);
                                    }
                                    break;



                            }

                        }
                    }
                }

                RD a = new RD(getApplicationContext(),m,getIntent().getStringExtra("Mobile"));
                recyclerView.setAdapter(a);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}