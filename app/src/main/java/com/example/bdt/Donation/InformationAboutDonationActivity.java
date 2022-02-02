package com.example.bdt.Donation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.bdt.Classes.RequestBlood;
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

    private Firebase RequestBloodDB, DonationTableDB;
    DatabaseReference DonationTableRef;

    String phoneNumber;
    String DonationId;

    String DonationRequestId;
    int s = 0;

    String date;

    String RecordID, MyNumber;
    boolean recordFounded = false;

    CheckBox chb1, chb2, chb3, chb4, chb5, chb6, chb7, chb8, chb9,
            chb10, chb11, chb12, chb13, chb14, chb15;

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

        DonationRequestId = getIntent().getStringExtra("donationRequestId");
        DonationId = DonationRequestId.substring(0, 20);

        MyNumber = getIntent().getStringExtra("number");
        Calendar c = Calendar.getInstance();

        final int da = c.get(Calendar.DAY_OF_MONTH);
        final int mon = c.get(Calendar.MONTH) + 1;
        final int yr = c.get(Calendar.YEAR);

        date = String.valueOf(da) + "-" + String.valueOf(mon) + "-" + String.valueOf(yr);

        DonationTableDB = new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/DonationTable");

        RequestBloodDB = new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/RequestBlood/" + DonationId);
        DonationTableRef = FirebaseDatabase.getInstance().getReference("DonationTable");
        phoneNumber = getIntent().getStringExtra("number");

        DonationTableDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Map<String, String> map = dataSnapshot1.getValue(Map.class);
                    String rid = map.get("RecordID");
                    String Number = map.get("MyPhoneNumber");

                    if(DonationRequestId.equalsIgnoreCase(rid)) {
                        if (phoneNumber.equalsIgnoreCase(Number)) {
                            recordFounded = true;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void ConfirmInfo(View view) {
        if (recordFounded)
            new AlertDialog.Builder(InformationAboutDonationActivity.this)
                    .setTitle("Save a Life Team")
                    .setMessage("You have already donat to this request ")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    }).show();
        else {
            SaveInformation();
        }
    }

    public void SaveInformation()
    {

        RequestBloodDB.addValueEventListener(new com.firebase.client.ValueEventListener() {
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
                    .setTitle("Save a Life Team")
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
                    .setTitle("Save a Life Team")
                    .setMessage("I declare that the information I have entered is correct, and I do not mind the donation center to withdraw the blood unit from me and"
                            +"make the necessary tests on it and take the necessary actions.")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                            intent.putExtra("FullName", getIntent().getStringExtra("FullName"));
                            intent.putExtra("BloodGroup", getIntent().getStringExtra("BloodGroup"));
                            intent.putExtra("Mobile", getIntent().getStringExtra("Mobile"));
                            startActivity(intent);
                        }
                    }).show();

            RecordID = DonationTableRef.push().getKey();
            if (TextUtils.isEmpty(RecordID)){

                RequestBlood r = new RequestBlood();
                r.setDate(date);
                r.setMobileNumber(phoneNumber);
                r.setRid(RecordID);
                DonationTableRef.child(RecordID).setValue(r);

                Toast.makeText(getApplicationContext(), "Save  " + RecordID, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                intent.putExtra("FullName", getIntent().getStringExtra("FullName"));
                intent.putExtra("BloodGroup", getIntent().getStringExtra("BloodGroup"));
                intent.putExtra("Mobile", getIntent().getStringExtra("Mobile"));
                startActivity(intent);
            }

            else
            {
                if (!TextUtils.isEmpty(RecordID)) {

                    if (!TextUtils.isEmpty(phoneNumber))
                        DonationTableRef.child(RecordID).child("MyPhoneNumber").setValue(phoneNumber);

                    if (!TextUtils.isEmpty(date))
                        DonationTableRef.child(RecordID).child("DonationDate").setValue(date);

                    if (!TextUtils.isEmpty(DonationId))
                        DonationTableRef.child(RecordID).child("RecordID").setValue(DonationRequestId);

                    Toast.makeText(getApplicationContext(), "Save  " + RecordID, Toast.LENGTH_LONG).show();
                    
                }
            }

            RequestBloodDB.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                @Override
                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                    RequestBloodDB.child("numberOfUnites").setValue(s);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
    }
}
