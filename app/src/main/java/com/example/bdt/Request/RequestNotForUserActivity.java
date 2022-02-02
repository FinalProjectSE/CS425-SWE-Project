package com.example.bdt.Request;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bdt.Classes.Requests;
import com.example.bdt.HomePageActivity;
import com.example.bdt.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class RequestNotForUserActivity extends AppCompatActivity {

    private Firebase UserTable, HospitalTableDB, RequestBloodDB;
    DatabaseReference RequesterTableRef, RequestBloodRef;
    ArrayList<String> list;
    String recordID = "";
    String recordID2 = "";

    EditText Mobile, numberofUnites;
    Button Check, Request;
    TextView YourName, BloodGroup, YouMobile;
    Spinner BloodsType, HospitalName;
    boolean userMobileFound;

    boolean MobileNumberFounded = false;
    String currentDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_not_for_user);

        Firebase.setAndroidContext(this);


        Mobile = (EditText) findViewById(R.id.MobileNR);
        Check = (Button) findViewById(R.id.Check);
        Request = (Button) findViewById(R.id.Request);
        YourName = (TextView) findViewById(R.id.FullName);
        BloodGroup = (TextView) findViewById(R.id.BloodGroup);
        YouMobile = (TextView) findViewById(R.id.YourMobile);
        BloodsType = (Spinner) findViewById(R.id.Bloods);
        numberofUnites = (EditText) findViewById(R.id.NumberOfUnite);
        HospitalName = (Spinner) findViewById(R.id.HospitalName);


        Calendar calendar = Calendar.getInstance();
        currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        HospitalTableDB = new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/HospitalTable");
        RequestBloodDB = new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/RequestBlood");


        list = new ArrayList<>();
        list.add("null");
        HospitalTableDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 :dataSnapshot.getChildren())
                {
                    Map<String ,String> map = dataSnapshot1.getValue(Map.class);
                    String hospitalname = map.get("hospitalName");
                    list.add(hospitalname);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,list);
        HospitalName.setAdapter(adapter);

    }

    public void Check(View view) {
        userMobileFound = false;
        UserTable = new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/UserTable");
        UserTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Map<String, String> map = dataSnapshot1.getValue(Map.class);
                    String mobile = map.get("mobile");

                    if (Mobile.getText().toString().equals(mobile)) {
                        String name = map.get("firstName") + " " + map.get("lastName");
                        String blood = map.get("bloodGroup");
                        // String city = map.get("city");

                        YourName.setText(name);
                        BloodGroup.setText(blood);
                        YouMobile.setText(Mobile.getText().toString());
                        userMobileFound = true;

                    }

                }

                if (!userMobileFound) {
                    Intent intent = new Intent(getApplicationContext(), RequestForUserManual.class);
                    intent.putExtra("Mobilenum",Mobile.getText().toString());
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        RequestBloodDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Map<String, String> map = dataSnapshot1.getValue(Map.class);

                    String mobileNumberFromFirebase = map.get("mobileNumber");
                    if (YouMobile.getText().toString().equalsIgnoreCase(mobileNumberFromFirebase)) {
                        MobileNumberFounded =true;
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void Request(View view) {

        if (MobileNumberFounded)
            new AlertDialog.Builder(RequestNotForUserActivity.this)
                    .setTitle("Save a Life Team")
                    .setMessage("No cant Request a blood right now")
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

    public void SaveInformation() {

        RequesterTableRef = FirebaseDatabase.getInstance().getReference("RequesterTable");
        RequestBloodRef = FirebaseDatabase.getInstance().getReference("RequestBlood");

        if ((TextUtils.isEmpty(recordID2)) && TextUtils.isEmpty(recordID)) {
            if (BloodsType.getSelectedItem().toString().equals("BloodGroup")) {
                
                if (TextUtils.isEmpty(recordID)) {
                    recordID = RequestBloodRef.push().getKey();

                    Requests re = new Requests();
                    re.setFullName(YourName.getText().toString());
                    re.setMobileNumber(YouMobile.getText().toString());
                    re.setBloodGroup(BloodsType.getSelectedItem().toString());
                    re.setHospitalName(HospitalName.getSelectedItem().toString());
                    re.setNumberOfUnites(Integer.parseInt(numberofUnites.getText().toString()));
                    RequestBloodRef.child(recordID).setValue(re);

                    Toast.makeText(this, "Save  " + recordID, Toast.LENGTH_LONG).show();

                } else {

                    if (!TextUtils.isEmpty(recordID)) {

                        if (!TextUtils.isEmpty(YourName.getText().toString()))
                            RequestBloodRef.child(recordID).child("fullName").setValue(YourName.getText().toString());

                        if (!TextUtils.isEmpty(YouMobile.getText().toString()))
                            RequestBloodRef.child(recordID).child("mobileNumber").setValue(YouMobile.getText().toString());

                        if (!TextUtils.isEmpty(BloodsType.getSelectedItem().toString()))
                            RequestBloodRef.child(recordID).child("bloodGroup").setValue(BloodsType.getSelectedItem().toString());


                        if (!TextUtils.isEmpty(HospitalName.getSelectedItem().toString()))
                            RequestBloodRef.child(recordID).child("hospitalName").setValue(HospitalName.getSelectedItem().toString());

                        if (!TextUtils.isEmpty(numberofUnites.getText().toString()))
                            RequestBloodRef.child(recordID).child("numberOfUnites").setValue(Integer.parseInt(numberofUnites.getText().toString()));

                        Toast.makeText(this, "Save  " + recordID, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(this, HomePageActivity.class);
                        intent.putExtra("FullName", getIntent().getStringExtra("FullName"));
                        intent.putExtra("BloodGroup", getIntent().getStringExtra("BloodGroup"));
                        intent.putExtra("Mobile", getIntent().getStringExtra("Mobile"));
                        startActivity(intent);


                    }

                }
            }
        }


        recordID2 = RequesterTableRef.push().getKey();

        HospitalTableDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if (TextUtils.isEmpty(recordID2)) {


                    Requests req = new Requests();

                    req.setMobileNumber(Mobile.getText().toString());
                    req.setRequestDate(currentDate);

                    RequesterTableRef.child(recordID2).setValue(req);
                    Toast.makeText(getApplicationContext(), "Save  " + recordID2, Toast.LENGTH_LONG).show();

                } else {

                    if (!TextUtils.isEmpty(recordID2)) {

                        if (!TextUtils.isEmpty(Mobile.getText().toString()))
                            RequesterTableRef.child(recordID2).child("Mobile").setValue(Mobile.getText().toString());

                        if (!TextUtils.isEmpty(currentDate))
                            RequesterTableRef.child(recordID2).child("Date").setValue(currentDate);
                    }
                }
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}