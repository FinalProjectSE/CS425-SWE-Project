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

    private Firebase mDatabase, mDatabase2, mDatabase3;
    DatabaseReference myRef, myRef2;
    ArrayList<String> list;
    String recordID = "";
    String recordID2 = "";

    EditText Mobile, numberofUnites;
    Button Check, Request;
    TextView YourName, BloodGroup, YouMobile;
    Spinner BloodsType, HosName;
    boolean Found;

    boolean found = false;
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
        HosName = (Spinner) findViewById(R.id.HospitalName);


        Calendar c = Calendar.getInstance();
        currentDate = DateFormat.getDateInstance().format(c.getTime());

        mDatabase2 = new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/HospitalTable");
        mDatabase3 = new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/RequestBlood");


        list = new ArrayList<>();
        list.add("null");
        mDatabase2.addValueEventListener(new ValueEventListener() {
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
        HosName.setAdapter(adapter);

    }

    public void Check(View view) {
        Found = false;
        mDatabase = new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/UserTable");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Map<String, String> map = dataSnapshot1.getValue(Map.class);
                    String mobile = map.get("mobile");

                    if (mobile.equals(Mobile.getText().toString())) {
                        String name = map.get("fname") + " " + map.get("lname");
                        String blood = map.get("bloodGroup");
                        // String city = map.get("city");

                        YourName.setText(name);
                        BloodGroup.setText(blood);
                        YouMobile.setText(Mobile.getText().toString());
                        Found = true;

                    }

                }

                if (!Found) {
                    Intent x = new Intent(getApplicationContext(), RequestForUserManual.class);
                    x.putExtra("Mobilenum",Mobile.getText().toString());
                    startActivity(x);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mDatabase3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Map<String, String> map = dataSnapshot1.getValue(Map.class);

                    String m = map.get("mobile");
                    if (m.equalsIgnoreCase(YouMobile.getText().toString())) {
                        found=true;
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void Request(View view) {

        if (found)
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
            r();
        }
    }

    public void r() {

        myRef = FirebaseDatabase.getInstance().getReference("RequesterTable");
        myRef2 = FirebaseDatabase.getInstance().getReference("RequestBlood");

        if ((TextUtils.isEmpty(recordID2)) && TextUtils.isEmpty(recordID)) {
            if (BloodsType.getSelectedItem().toString().equals("BloodGroup")) {

                if (TextUtils.isEmpty(recordID)) {
                    recordID = myRef2.push().getKey();

                    Requests re = new Requests();

                    re.setFuName(YourName.getText().toString());
                    re.setMobile(YouMobile.getText().toString());
                    re.setBloodGroup(BloodGroup.getText().toString());
                    re.setHospitalName(HosName.getSelectedItem().toString());
                    re.setNumberOfUnites(Integer.parseInt(numberofUnites.getText().toString()));
                    myRef2.child(recordID).setValue(re);

                    Toast.makeText(this, "Save  " + recordID, Toast.LENGTH_LONG).show();

                } else {

                    if (!TextUtils.isEmpty(recordID)) {

                        if (!TextUtils.isEmpty(YourName.getText().toString()))
                            myRef2.child(recordID).child("fuName").setValue(YourName.getText().toString());

                        if (!TextUtils.isEmpty(YouMobile.getText().toString()))
                            myRef2.child(recordID).child("Mobile").setValue(YouMobile.getText().toString());

                        if (!TextUtils.isEmpty(BloodGroup.getText().toString()))
                            myRef2.child(recordID).child("BloodGroup").setValue(BloodGroup.getText().toString());


                        if (!TextUtils.isEmpty(HosName.getSelectedItem().toString()))
                            myRef2.child(recordID).child("HospitalName").setValue(HosName.getSelectedItem().toString());

                        if (!TextUtils.isEmpty(numberofUnites.getText().toString()))
                            myRef2.child(recordID).child("numberOfUnites").setValue(Integer.parseInt(numberofUnites.getText().toString()));

                    }

                }

            } else {
                if (TextUtils.isEmpty(recordID)) {
                    recordID = myRef2.push().getKey();

                    Requests re = new Requests();
                    re.setFuName(YourName.getText().toString());
                    re.setMobile(YouMobile.getText().toString());
                    re.setBloodGroup(BloodsType.getSelectedItem().toString());
                    re.setHospitalName(HosName.getSelectedItem().toString());
                    re.setNumberOfUnites(Integer.parseInt(numberofUnites.getText().toString()));
                    myRef2.child(recordID).setValue(re);

                    Toast.makeText(this, "Save  " + recordID, Toast.LENGTH_LONG).show();

                } else {

                    if (!TextUtils.isEmpty(recordID)) {

                        if (!TextUtils.isEmpty(YourName.getText().toString()))
                            myRef2.child(recordID).child("FuName").setValue(YourName.getText().toString());

                        if (!TextUtils.isEmpty(YouMobile.getText().toString()))
                            myRef2.child(recordID).child("Mobile").setValue(YouMobile.getText().toString());

                        if (!TextUtils.isEmpty(BloodsType.getSelectedItem().toString()))
                            myRef2.child(recordID).child("BloodGroup").setValue(BloodsType.getSelectedItem().toString());


                        if (!TextUtils.isEmpty(HosName.getSelectedItem().toString()))
                            myRef2.child(recordID).child("HospitalName").setValue(HosName.getSelectedItem().toString());

                        if (!TextUtils.isEmpty(numberofUnites.getText().toString()))
                            myRef2.child(recordID).child("numberOfUnites").setValue(Integer.parseInt(numberofUnites.getText().toString()));
                    }

                }
            }
        }


        recordID2 = myRef.push().getKey();

        mDatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if (TextUtils.isEmpty(recordID2)) {


                    RequestBlood req = new RequestBlood();

                    req.setMyMobile(Mobile.getText().toString());
                    req.setDate(currentDate);

                    myRef.child(recordID2).setValue(req);
                    Toast.makeText(getApplicationContext(), "Save  " + recordID2, Toast.LENGTH_LONG).show();

                } else {

                    if (!TextUtils.isEmpty(recordID2)) {

                        if (!TextUtils.isEmpty(Mobile.getText().toString()))
                            myRef.child(recordID2).child("Mobile").setValue(Mobile.getText().toString());

                        if (!TextUtils.isEmpty(currentDate))
                            myRef.child(recordID2).child("Date").setValue(currentDate);
                    }
                }
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}