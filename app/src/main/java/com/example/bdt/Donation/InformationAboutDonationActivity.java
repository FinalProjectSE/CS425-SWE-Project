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

import com.example.bdt.Classes.RequestBlood;
import com.example.bdt.Classes.Requests;
import com.example.bdt.HomePageActivity;
import com.example.bdt.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class InformationAboutDonationActivity extends AppCompatActivity {

    private Firebase md,md3;
    DatabaseReference myRef;

    String my_num;
    String id;

    String x;
    int s=0;

    String dateinfirebase;

    String date;

    String RecordID,MyNumber;
    boolean Found = false;

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

        MyNumber=getIntent().getStringExtra("num");
        Calendar c = Calendar.getInstance();
        final int da=c.get(Calendar.DAY_OF_MONTH);
        final int mon=c.get(Calendar.MONTH)+1;
        final int yr=c.get(Calendar.YEAR);

        date = String.valueOf(da)+"-"+String.valueOf(mon)+"-"+String.valueOf(yr);


        md3=new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/DonationTable/");
        md3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Map<String, String> map = dataSnapshot1.getValue(Map.class);
                    String rid = map.get("rid");
                    String Number = map.get("myNumber");
                    dateinfirebase = map.get("date");
                    if (rid.equalsIgnoreCase(id) && Number.equalsIgnoreCase(MyNumber)) {
                        Found = true;
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });




        md= new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/RequestBlood/"+id);


        myRef = FirebaseDatabase.getInstance().getReference("DonationTable");

        my_num = getIntent().getStringExtra("num");

    }

    //information for location
    public void ConfirmInfo(View view) {
        if (Found)
            new AlertDialog.Builder(InformationAboutDonationActivity.this)
                    .setTitle("BDT Team")
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


        md.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                HashMap<String, Object> map = dataSnapshot.getValue(HashMap.class);
                s = (int)map.get("numberOfUnites");

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
                    .setTitle("BDT Team")
                    .setMessage("Sorry, you are not eligible to donate at this time")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

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
                            Toast.makeText(getApplicationContext(), "thanks for donating   " , Toast.LENGTH_LONG).show();
                            Intent newIntent = new Intent(InformationAboutDonationActivity.this, HomePageActivity.class);
                            newIntent.putExtra("Mobile",MyNumber);
                            startActivity(newIntent);
                        }
                    }).show();

            if (TextUtils.isEmpty(RecordID)){
                RecordID = myRef.push().getKey();
                RequestBlood r = new RequestBlood();
                r.setDate(date);
                r.setMyMobile(my_num);
                r.setRid(x);
                myRef.child(RecordID).setValue(r);

                Toast.makeText(getApplicationContext(), "Save  " + RecordID, Toast.LENGTH_LONG).show();
            }

            else
            {
                if (!TextUtils.isEmpty(RecordID)) {

                    if (!TextUtils.isEmpty(my_num))
                        myRef.child(RecordID).child("myMobile").setValue(my_num);

                    if (!TextUtils.isEmpty(date))
                        myRef.child(RecordID).child("date").setValue(date);

                    if (!TextUtils.isEmpty(x))
                        myRef.child(RecordID).child("rid").setValue(x);
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