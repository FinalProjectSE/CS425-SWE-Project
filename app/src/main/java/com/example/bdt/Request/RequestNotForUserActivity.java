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

import com.example.bdt.Classes.RequestBlood;
import com.example.bdt.Classes.Requests;
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

    private Firebase UserTableDB, HospitalTableDB, RequestBloodDB;
    DatabaseReference RequesterTableReference, RequestBloodReference;
    ArrayList<String> list;
    String recordID = "";
    String recordID2 = "";

    EditText mobileNumber, numberofUnites;
    Button Check, Request;
    TextView YourName, BloodGroup, YourMobile;
    Spinner BloodsType, HospitalName;
    boolean MobileFounded;

    boolean MobileFoundForRequest = false;
    String currentDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_not_for_user);

        Firebase.setAndroidContext(this);


        mobileNumber = (EditText) findViewById(R.id.MobileNR);
        Check = (Button) findViewById(R.id.Check);
        Request = (Button) findViewById(R.id.Request);
        YourName = (TextView) findViewById(R.id.FullName);
        BloodGroup = (TextView) findViewById(R.id.BloodGroup);
        YourMobile = (TextView) findViewById(R.id.YourMobile);
        BloodsType = (Spinner) findViewById(R.id.Bloods);
        numberofUnites = (EditText) findViewById(R.id.NumberOfUnite);
        HospitalName = (Spinner) findViewById(R.id.HospitalName);

        Calendar c = Calendar.getInstance();
        currentDate = DateFormat.getDateInstance().format(c.getTime());

        UserTableDB = new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/UserTable");
        HospitalTableDB = new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/HospitalTable");
        RequestBloodDB = new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/RequestBlood");


        list = new ArrayList<>();
        list.add("null");
        HospitalDB();

    }

    public void Check(View view) {
        MobileFounded = false;

        UserTableDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Map<String, String> map = dataSnapshot1.getValue(Map.class);
                    String mobileFromDB = map.get("mobile");

                    if (mobileFromDB.equals(mobileNumber.getText().toString())) {
                        String name = map.get("firstName") + " " + map.get("lastName");
                        String blood = map.get("bloodGroup");

                        YourName.setText(name);
                        BloodGroup.setText(blood);
                        YourMobile.setText(mobileNumber.getText().toString());
                        MobileFounded = true;

                    }

                }

                if (!MobileFounded) {
                    Intent x = new Intent(getApplicationContext(), RequestForUserManual.class);
                    x.putExtra("MobileNumber", mobileNumber.getText().toString());
                    startActivity(x);
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

                    String mobileFromDB = map.get("mobileNumber");
                    if (mobileFromDB.equalsIgnoreCase(YourMobile.getText().toString())) {
                        MobileFoundForRequest = true;
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }


    public void Request(View view) {

        if (MobileFoundForRequest)
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
            SaveInformationIntoDB();
        }
    }

    public void SaveInformationIntoDB() {

        RequesterTableReference = FirebaseDatabase.getInstance().getReference("RequesterTable");
        RequestBloodReference = FirebaseDatabase.getInstance().getReference("RequestBlood");

        if ((TextUtils.isEmpty(recordID2)) && TextUtils.isEmpty(recordID)) {
            if (BloodsType.getSelectedItem().toString().equals("BloodGroup")) {

                if (TextUtils.isEmpty(recordID)) {
                    recordID = RequestBloodReference.push().getKey();
                    Requests requests = new Requests();
                    requests.setFullName(YourName.getText().toString());
                    requests.setMobileNumber(YourMobile.getText().toString());
                    requests.setBloodGroup(BloodGroup.getText().toString());
                    requests.setNumberOfUnites(Integer.parseInt(numberofUnites.getText().toString()));
                    requests.setHospitalName(HospitalName.getSelectedItem().toString());
                    RequestBloodReference.child(recordID).setValue(requests);
                    Toast.makeText(this, "Save  " + recordID, Toast.LENGTH_LONG).show();
                }

                if (!TextUtils.isEmpty(recordID)) {

                    if (!TextUtils.isEmpty(YourName.getText().toString()))
                        RequestBloodReference.child(recordID).child("fullName").setValue(YourName.getText().toString());

                    if (!TextUtils.isEmpty(YourMobile.getText().toString()))
                        RequestBloodReference.child(recordID).child("mobileNumber").setValue(YourMobile.getText().toString());

                    if (!TextUtils.isEmpty(BloodGroup.getText().toString()))
                        RequestBloodReference.child(recordID).child("bloodGroup").setValue(BloodGroup.getText().toString());


                    if (!TextUtils.isEmpty(HospitalName.getSelectedItem().toString()))
                        RequestBloodReference.child(recordID).child("hospitalName").setValue(HospitalName.getSelectedItem().toString());

                    if (!TextUtils.isEmpty(numberofUnites.getText().toString()))
                        RequestBloodReference.child(recordID).child("numberOfUnites").setValue(Integer.parseInt(numberofUnites.getText().toString()));

                }
            }
        }

        HospitalTableDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (TextUtils.isEmpty(recordID2)) {
                    recordID2 = RequesterTableReference.push().getKey();
                    RequestBlood req = new RequestBlood();
                    req.setMobileNumber(mobileNumber.getText().toString());
                    req.setDate(currentDate);
                    RequesterTableReference.child(recordID2).setValue(req);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void HospitalDB() {
        HospitalTableDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Map<String, String> map = dataSnapshot1.getValue(Map.class);
                    String hospitalname = map.get("hospitalName");
                    list.add(hospitalname);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list);
        HospitalName.setAdapter(adapter);

    }

}
