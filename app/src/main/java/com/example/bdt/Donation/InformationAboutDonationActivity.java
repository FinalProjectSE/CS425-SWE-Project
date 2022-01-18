package com.example.bdt.Donation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.bdt.Classes.Requests;
import com.example.bdt.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class InformationAboutDonationActivity extends AppCompatActivity {

    private Firebase mDatabase,md,md3;
    DatabaseReference myRef;

    String my_num;
    String id;
    String HosapitalName;

    String x;
    int s=0;


    double la=0.0;
    double lo=0.0;
    String locationCenter="";

    String dateinfirebase;
    String date;

    String RecordID,MyNumber;
    boolean Found = false;
    boolean FoundDate = false;

    CheckBox chb1,chb2,chb3,chb4,chb5,chb6,chb7,chb8,chb9,
            chb10,chb11,chb12,chb13,chb14,chb15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_about_donation);

        Firebase.setAndroidContext(this);
        chb1 = findViewById(R.id.checkBox1);
        chb2 = findViewById(R.id.checkBox2);
        chb3 = findViewById(R.id.checkBox3);
        chb4 = findViewById(R.id.checkBox4);
        chb5 = findViewById(R.id.checkBox5);
        chb6 = findViewById(R.id.checkBox6);
        chb7 = findViewById(R.id.checkBox7);
        chb8 = findViewById(R.id.checkBox8);
        chb9 = findViewById(R.id.checkBox9);
        chb10 = findViewById(R.id.checkBox10);
        chb11 = findViewById(R.id.checkBox11);
        chb12 = findViewById(R.id.checkBox12);
        chb13 = findViewById(R.id.checkBox13);
        chb14 = findViewById(R.id.checkBox14);
        chb15 = findViewById(R.id.checkBox15);

        x=getIntent().getStringExtra("donationRequestId");
        id=x.substring(0,20);
        HosapitalName = x.substring(21);

        MyNumber=getIntent().getStringExtra("num");
        Calendar c = Calendar.getInstance();


        md3=new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/DonationTable");
        md3.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Map<String,String> map = dataSnapshot1.getValue(Map.class);
                    String rid = map.get("rid");
                    String Number = map.get("myNumber");
                    if (rid.equalsIgnoreCase(id) && Number.equalsIgnoreCase(MyNumber)) {
                        Found = true;
                    }
                }
                
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });




        md= new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/RequesterTable/"+x);
        mDatabase = new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/HospitalTable");


        myRef = FirebaseDatabase.getInstance().getReference("DonationTable");

        my_num = getIntent().getStringExtra("num");

        mDatabase.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                for (com.firebase.client.DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    HashMap<String,Object> map = dataSnapshot1.getValue(HashMap.class);
                    if (map.get("hospitalName").toString().equalsIgnoreCase(HosapitalName)) {
                        locationCenter = (String) map.get("centerLocation");
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    //information for location
    public void ConfirmInfo(View view) {
        if (Found)
            new AlertDialog.Builder(InformationAboutDonationActivity.this)
                    .setTitle("Blood Donation System team")
                    .setMessage("You have already donat to this request ")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    }).show();
        else {
            r();
        }

    }

    public void r()
    {


        md.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {


                HashMap<String, Object> map = dataSnapshot.getValue(HashMap.class);
                s=(int)(map.get("numberOfUnites"));
                s--;

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        if (chb1.isChecked() || chb2.isChecked() || chb3.isChecked() || chb4.isChecked() ||
                chb5.isChecked() || chb6.isChecked() || chb7.isChecked() ||
                chb8.isChecked() ||chb9.isChecked() ||chb10.isChecked() ||
                chb11.isChecked() ||chb12.isChecked() ||chb13.isChecked() ||
                chb14.isChecked() ||chb15.isChecked() )
        {

            new AlertDialog.Builder(InformationAboutDonationActivity.this)
                    .setTitle("Blood Donation System team")
                    .setMessage("Sorry, you are not eligible to donate at this time")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            //Intent x=new Intent(FirstActivity.this,DonationActivity.class);
                            //startActivity(x);
                        }
                    }).show();
        }

        else {
            new AlertDialog.Builder(InformationAboutDonationActivity.this)
                    .setTitle("Blood Donation System team")
                    .setMessage("I declare that the information I have entered is correct, and I do not mind the donation center to withdraw the blood unit from me and " +
                            "make the necessary tests on it and take the necessary actions.")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            //  double latitude=la;
                            // double longitude=lo;
                            String link = locationCenter;
                            // String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                            startActivity(intent);

                        }
                    }).show();

            if (TextUtils.isEmpty(RecordID)){
                RecordID = myRef.push().getKey();
                Requests r = new Requests();
                r.setDate(date);
                r.setMobile(my_num);
                r.setUserid(x);
                myRef.child(RecordID).setValue(r);

                Toast.makeText(getApplicationContext(), "Save  " + RecordID, Toast.LENGTH_LONG).show();
            }

            else
            {
                if (!TextUtils.isEmpty(RecordID)) {

                    if (!TextUtils.isEmpty(my_num))
                        myRef.child(RecordID).child("Mobile").setValue(my_num);

                    if (!TextUtils.isEmpty(date))
                        myRef.child(RecordID).child("Date").setValue(date);

                    if (!TextUtils.isEmpty(x))
                        myRef.child(RecordID).child("Rid").setValue(x);
                }
            }

            md.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                @Override
                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                    md.child("numberOfUnites").setValue(s);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
    }
}