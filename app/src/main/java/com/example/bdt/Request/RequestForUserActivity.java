package com.example.bdt.Request;

import androidx.appcompat.app.AppCompatActivity;

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
    private Firebase HospitalTable, RequestBlood;
    DatabaseReference RequesterTableRef, RequestBloodRef;
    ArrayList<String> list;


    //Declare
    TextView Name, MobileNumber, BloodGroup;
    Spinner Bloods, HospitalName;
    EditText numberOfUnites;
    Button Request;

    //for DataReference
    String recordId = "";
    String recordid2 = "";


    String currentDate;
    Boolean mobileNumberFounded = false;

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

        HospitalTable = new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/HospitalTable");
        RequestBlood = new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/RequestBlood");

        list = new ArrayList<>();
        list.add("null");
        HospitalTable.addValueEventListener(new ValueEventListener() {
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

        RequesterTableRef = FirebaseDatabase.getInstance().getReference("RequesterTable");
        RequestBloodRef = FirebaseDatabase.getInstance().getReference("RequestBlood");



        RequestBlood.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    HashMap<String, Object> map = dataSnapshot1.getValue(HashMap.class);

                    String mobileNumber = (String) map.get("mobileNumber");
                    int numberOfUnites = (int) map.get("numberOfUnites");
                    if (numberOfUnites > 0) {
                        if (MobileNumber.getText().toString().equalsIgnoreCase(mobileNumber)) {
                            mobileNumberFounded = true;
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

        if (mobileNumberFounded) {
            new AlertDialog.Builder(RequestForUserActivity.this)
                    .setTitle("Save a Life Team")
                    .setMessage("You can't place a request in this time " +
                            "/n please try after your previous request is done ")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    }).show();
        } else
            new AlertDialog.Builder(RequestForUserActivity.this)
                    .setTitle("Consent Donation")
                    .setMessage("Are you sure that you would like to Request a blood")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            SaveInformation();
                        }
                    }).show();
    }

    public void SaveInformation() {

        if ((TextUtils.isEmpty(recordid2)) && TextUtils.isEmpty(recordId)) {
            if (Bloods.getSelectedItem().toString().equals("BloodGroup")) {

                if (TextUtils.isEmpty(recordId)) {
                    recordId = RequesterTableRef.push().getKey();

                    Requests re = new Requests();

                    re.setFullName(Name.getText().toString());
                    re.setMobileNumber(MobileNumber.getText().toString());
                    re.setBloodGroup(BloodGroup.getText().toString());
                    re.setHospitalName(HospitalName.getSelectedItem().toString());
                    re.setNumberOfUnites(Integer.parseInt(numberOfUnites.getText().toString()));
                    RequestBloodRef.child(recordId).setValue(re);

                    Toast.makeText(this, "Save  " + recordId, Toast.LENGTH_LONG).show();

                } else {

                    if (!TextUtils.isEmpty(recordId)) {

                        if (!TextUtils.isEmpty(Name.getText().toString()))
                            RequestBloodRef.child(recordId).child("fullName").setValue(Name.getText().toString());

                        if (!TextUtils.isEmpty(MobileNumber.getText().toString()))
                            RequestBloodRef.child(recordId).child("mobileNumber").setValue(MobileNumber.getText().toString());

                        if (!TextUtils.isEmpty(BloodGroup.getText().toString()))
                            RequestBloodRef.child(recordId).child("BloodGroup").setValue(BloodGroup.getText().toString());


                        if (!TextUtils.isEmpty(HospitalName.getSelectedItem().toString()))
                            RequestBloodRef.child(recordId).child("hospitalName").setValue(HospitalName.getSelectedItem().toString());

                        if (!TextUtils.isEmpty(numberOfUnites.getText().toString()))
                            RequestBloodRef.child(recordId).child("numberOfUnites").setValue(Integer.parseInt(numberOfUnites.getText().toString()));


                    }

                }

            } else {
                if (TextUtils.isEmpty(recordId)) {
                    recordId = RequesterTableRef.push().getKey();

                    Requests re = new Requests();
                    re.setFullName(Name.getText().toString());
                    re.setMobileNumber(MobileNumber.getText().toString());
                    re.setBloodGroup(Bloods.getSelectedItem().toString());
                    re.setHospitalName(HospitalName.getSelectedItem().toString());
                    re.setNumberOfUnites(Integer.parseInt(numberOfUnites.getText().toString()));
                    RequestBloodRef.child(recordId).setValue(re);

                    Toast.makeText(this, "Save  " + recordId, Toast.LENGTH_LONG).show();

                } else {

                    if (!TextUtils.isEmpty(recordId)) {

                        if (!TextUtils.isEmpty(Name.getText().toString()))
                            RequestBloodRef.child(recordId).child("fuName").setValue(Name.getText().toString());

                        if (!TextUtils.isEmpty(MobileNumber.getText().toString()))
                            RequestBloodRef.child(recordId).child("Mobile").setValue(MobileNumber.getText().toString());

                        if (!TextUtils.isEmpty(Bloods.getSelectedItem().toString()))
                            RequestBloodRef.child(recordId).child("BloodGroup").setValue(Bloods.getSelectedItem().toString());


                        if (!TextUtils.isEmpty(HospitalName.getSelectedItem().toString()))
                            RequestBloodRef.child(recordId).child("HospitalName").setValue(HospitalName.getSelectedItem().toString());

                        if (!TextUtils.isEmpty(numberOfUnites.getText().toString()))
                            RequestBloodRef.child(recordId).child("numberOfUnites").setValue(Integer.parseInt(numberOfUnites.getText().toString()));

                    }

                }
            }
        }
        recordid2 = RequesterTableRef.push().getKey();

        HospitalTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (TextUtils.isEmpty(recordid2)) {


                    Requests req = new Requests();

                    req.setMobileNumber(MobileNumber.getText().toString());
                    req.setRequestDate(currentDate);

                    RequesterTableRef.child(recordid2).setValue(req);
                    Toast.makeText(getApplicationContext(), "Save  " + recordid2, Toast.LENGTH_LONG).show();

                } else {

                    if (!TextUtils.isEmpty(recordid2)) {

                        if (!TextUtils.isEmpty(MobileNumber.getText().toString()))
                            RequesterTableRef.child(recordid2).child("Mobile").setValue(MobileNumber.getText().toString());

                        if (!TextUtils.isEmpty(currentDate))
                            RequesterTableRef.child(recordid2).child("Date").setValue(currentDate);
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

}