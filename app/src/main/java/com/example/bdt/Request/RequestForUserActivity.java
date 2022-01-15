package com.example.bdt.Request;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import java.util.HashMap;
import java.util.Map;

public class RequestForUserActivity extends AppCompatActivity {

    //FireBase declare
    private Firebase mDatabase, mDatabase2, mDatabase3;
    DatabaseReference myRef, myRef2;
    ArrayList<String> list;


    //Declare
    TextView Name, MobileNumber, BloodGroup;
    Spinner Bloods, HospitalName;
    EditText numberOfUnites;
    Button Request;
    String RecordId;

    //for DataReference
    String recordId = "";
    String recordid2 = "";


    String currentDate;
    Boolean found = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_for_user);

        Firebase.setAndroidContext(this);

        Name = (TextView) findViewById(R.id.FUname);
        MobileNumber = (TextView) findViewById(R.id.MobileRe);
        BloodGroup = (TextView) findViewById(R.id.BloodGroupRe);
        numberOfUnites = (EditText) findViewById(R.id.NumberOfUniteRe);
        Bloods = (Spinner) findViewById(R.id.BloodsRe);
        Request = (Button) findViewById(R.id.RequestUser);
        HospitalName = (Spinner) findViewById(R.id.HospitalNameUser);

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

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,list);
        HospitalName.setAdapter(adapter);

        Calendar c = Calendar.getInstance();
        currentDate = DateFormat.getDateInstance().format(c.getTime());

        myRef2 = FirebaseDatabase.getInstance().getReference("RequestBlood");
        myRef = FirebaseDatabase.getInstance().getReference("RequesterTable");


        mDatabase3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    HashMap<String, Object> map = dataSnapshot1.getValue(HashMap.class);

                    String m = (String) map.get("mobile");
                    int n = (int) map.get("numberOfUnites");
                    if (n > 0) {
                        if (m.equalsIgnoreCase(MobileNumber.getText().toString())) {
                            found = true;
                        }
                    }

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        Name.setText(getIntent().getStringExtra("FullName"));
        BloodGroup.setText(getIntent().getStringExtra("BloodGroup"));
        MobileNumber.setText(getIntent().getStringExtra("Mobile"));
    }


    public void Request(View view) {

        if (found) {
            new AlertDialog.Builder(RequestForUserActivity.this)
                    .setTitle("Save a Life Team")
                    .setMessage("No cant Request a blood right now ")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    }).show();
        }
    }

    public void r() {


        if ((TextUtils.isEmpty(recordid2)) && TextUtils.isEmpty(recordId)) {
            if (Bloods.getSelectedItem().toString().equals("BloodGroup")) {

                if (TextUtils.isEmpty(recordId)) {
                    recordId = myRef.push().getKey();

                    Requests re = new Requests();

                    re.setFuName(Name.getText().toString());
                    re.setMobile(MobileNumber.getText().toString());
                    re.setBloodGroup(BloodGroup.getText().toString());
                    re.setHospitalName(HospitalName.getSelectedItem().toString());
                    re.setNumberOfUnites(Integer.parseInt(numberOfUnites.getText().toString()));
                    myRef2.child(recordId).setValue(re);

                    Toast.makeText(this, "Save  " + recordId, Toast.LENGTH_LONG).show();

                } else {

                    if (!TextUtils.isEmpty(recordId)) {

                        if (!TextUtils.isEmpty(Name.getText().toString()))
                            myRef2.child(recordId).child("fuName").setValue(Name.getText().toString());

                        if (!TextUtils.isEmpty(MobileNumber.getText().toString()))
                            myRef2.child(recordId).child("Mobile").setValue(MobileNumber.getText().toString());

                        if (!TextUtils.isEmpty(BloodGroup.getText().toString()))
                            myRef2.child(recordId).child("BloodGroup").setValue(BloodGroup.getText().toString());


                        if (!TextUtils.isEmpty(HospitalName.getSelectedItem().toString()))
                            myRef2.child(recordId).child("HospitalName").setValue(HospitalName.getSelectedItem().toString());

                        if (!TextUtils.isEmpty(numberOfUnites.getText().toString()))
                            myRef2.child(recordId).child("numberOfUnites").setValue(Integer.parseInt(numberOfUnites.getText().toString()));


                    }

                }

            } else {
                if (TextUtils.isEmpty(recordId)) {
                    recordId = myRef.push().getKey();

                    Requests re = new Requests();
                    re.setFuName(Name.getText().toString());
                    re.setMobile(MobileNumber.getText().toString());
                    re.setBloodGroup(Bloods.getSelectedItem().toString());
                    re.setHospitalName(HospitalName.getSelectedItem().toString());
                    re.setNumberOfUnites(Integer.parseInt(numberOfUnites.getText().toString()));
                    myRef2.child(recordId).setValue(re);

                    Toast.makeText(this, "Save  " + recordId, Toast.LENGTH_LONG).show();

                } else {

                    if (!TextUtils.isEmpty(recordId)) {

                        if (!TextUtils.isEmpty(Name.getText().toString()))
                            myRef2.child(recordId).child("fuName").setValue(Name.getText().toString());

                        if (!TextUtils.isEmpty(MobileNumber.getText().toString()))
                            myRef2.child(recordId).child("Mobile").setValue(MobileNumber.getText().toString());

                        if (!TextUtils.isEmpty(Bloods.getSelectedItem().toString()))
                            myRef2.child(recordId).child("BloodGroup").setValue(Bloods.getSelectedItem().toString());


                        if (!TextUtils.isEmpty(HospitalName.getSelectedItem().toString()))
                            myRef2.child(recordId).child("HospitalName").setValue(HospitalName.getSelectedItem().toString());

                        if (!TextUtils.isEmpty(numberOfUnites.getText().toString()))
                            myRef2.child(recordId).child("numberOfUnites").setValue(Integer.parseInt(numberOfUnites.getText().toString()));

                    }

                }
            }
        }
        recordid2 = myRef.push().getKey();

        mDatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (TextUtils.isEmpty(recordid2)) {


                    RequestBlood req = new RequestBlood();

                    req.setMyMobile(MobileNumber.getText().toString());
                    req.setDate(currentDate);

                    myRef.child(recordid2).setValue(req);
                    Toast.makeText(getApplicationContext(), "Save  " + recordid2, Toast.LENGTH_LONG).show();

                } else {

                    if (!TextUtils.isEmpty(recordid2)) {

                        if (!TextUtils.isEmpty(MobileNumber.getText().toString()))
                            myRef.child(recordid2).child("Mobile").setValue(MobileNumber.getText().toString());

                        if (!TextUtils.isEmpty(currentDate))
                            myRef.child(recordid2).child("Date").setValue(currentDate);
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }


}