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

public class RequestForUserManual extends AppCompatActivity {

    DatabaseReference RequesterTableRef, RequestBloodRef;
    String recordID = "";
    String recordID2 = "";
    EditText fullName, lastName, Mobile, numberOfunits;
    Spinner BloodsType, BloodGroup, HospitalName;
    Button Request;
    ArrayList<String> list;
    private Firebase HospitalTableDB, RequestBloodDB;

    String currentDate;
    Boolean MobileNumberFounded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_for_user_manual);
        Firebase.setAndroidContext(this);

        fullName = (EditText) findViewById(R.id.FName);
        lastName = (EditText) findViewById(R.id.LName);
        Mobile = (EditText) findViewById(R.id.MobileRU);
        BloodsType = (Spinner) findViewById(R.id.Bloods);
        BloodGroup = (Spinner) findViewById(R.id.BloodGroup);
        Request = (Button) findViewById(R.id.Request);
        HospitalName = (Spinner) findViewById(R.id.HospitalName);
        numberOfunits = (EditText) findViewById(R.id.NumberOfUnite);

        Mobile.setText(getIntent().getStringExtra("Mobilenum"));
        RequestBloodDB = new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/RequestBlood");

        RequestBloodDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Map<String, String> map = dataSnapshot1.getValue(Map.class);

                    String mobileNumberFromDB = map.get("mobileNumber");
                    if (getIntent().getStringExtra("Mobilenum").equalsIgnoreCase(mobileNumberFromDB)) {
                        MobileNumberFounded = true;
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        Calendar c = Calendar.getInstance();
        currentDate = DateFormat.getDateInstance().format(c.getTime());

        HospitalTableDB = new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/HospitalTable");

        list = new ArrayList<>();
        list.add("null");
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

    public void SaveToFireBase2(View view) {

        if (MobileNumberFounded) {
            new AlertDialog.Builder(RequestForUserManual.this)
                    .setTitle("Save a Life Team")
                    .setMessage("No cant Request a blood right now")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    }).show();
        } else {
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
                    re.setFullName(fullName.getText().toString() + " " + lastName.getText().toString());
                    re.setMobileNumber(Mobile.getText().toString());
                    re.setBloodGroup(BloodGroup.getSelectedItem().toString());
                    re.setHospitalName(HospitalName.getSelectedItem().toString());
                    re.setNumberOfUnites(Integer.parseInt(numberOfunits.getText().toString()));
                    RequestBloodRef.child(recordID).setValue(re);

                    Toast.makeText(this, "Save  " + recordID, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                    intent.putExtra("FullName", getIntent().getStringExtra("FullName"));
                    intent.putExtra("BloodGroup", getIntent().getStringExtra("BloodGroup"));
                    intent.putExtra("Mobile", getIntent().getStringExtra("Mobile"));
                    startActivity(intent);

                } else {

                    if (!TextUtils.isEmpty(recordID)) {

                        if ((!TextUtils.isEmpty(fullName.getText().toString()) && (!TextUtils.isEmpty(lastName.getText().toString()))))
                            RequestBloodRef.child(recordID).child("fullName").setValue(fullName.getText().toString() + " " + lastName.getText().toString());

                        if (!TextUtils.isEmpty(Mobile.getText().toString()))
                            RequestBloodRef.child(recordID).child("mobileNumber").setValue(Mobile.getText().toString());

                        if (!TextUtils.isEmpty(BloodsType.getSelectedItem().toString()))
                            RequestBloodRef.child(recordID).child("bloodGroup").setValue(BloodsType.getSelectedItem().toString());


                        if (!TextUtils.isEmpty(HospitalName.getSelectedItem().toString()))
                            RequestBloodRef.child(recordID).child("hospitalName").setValue(HospitalName.getSelectedItem().toString());

                        if (!TextUtils.isEmpty(numberOfunits.getText().toString()))
                            RequestBloodRef.child(recordID).child("numberOfUnites").setValue(Integer.parseInt(numberOfunits.getText().toString()));

                        Toast.makeText(this, "Save  " + recordID, Toast.LENGTH_LONG).show();


                    }

                }
            }
        }



        HospitalTableDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (TextUtils.isEmpty(recordID2)) {
                    recordID2 = RequesterTableRef.push().getKey();
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