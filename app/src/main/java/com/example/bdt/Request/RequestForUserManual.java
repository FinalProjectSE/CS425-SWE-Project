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
import java.util.Map;

public class RequestForUserManual extends AppCompatActivity {

    DatabaseReference myRef, myRef2;
    String recordID = "";
    String recordID2 = "";
    EditText FName, LName, Mobile, numberOfunits;
    Spinner BloodsType, BloodGroup, HospitalName;
    Button Request;
    ArrayList<String> list;
    private Firebase mDatabase, mDatabase3;

    String currentDate;
    Boolean found = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_for_user_manual);
        Firebase.setAndroidContext(this);

        FName = (EditText) findViewById(R.id.FName);
        LName = (EditText) findViewById(R.id.LName);
        Mobile = (EditText) findViewById(R.id.MobileRU);
        BloodsType = (Spinner) findViewById(R.id.Bloods);
        BloodGroup = (Spinner) findViewById(R.id.BloodGroup);
        Request = (Button) findViewById(R.id.Request);
        HospitalName = (Spinner) findViewById(R.id.HospitalName);
        numberOfunits = (EditText) findViewById(R.id.NumberOfUnite);

        Mobile.setText(getIntent().getStringExtra("Mobilenum"));
        mDatabase3 = new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/RequestBlood");

        mDatabase3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Map<String, String> map = dataSnapshot1.getValue(Map.class);

                    String m = map.get("mobile");
                    if (m.equalsIgnoreCase(getIntent().getStringExtra("Mobilenum"))) {
                        found=true;
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        Calendar c = Calendar.getInstance();
        currentDate = DateFormat.getDateInstance().format(c.getTime());

        mDatabase = new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/HospitalTable");

        list = new ArrayList<>();
        list.add("null");
        mDatabase.addValueEventListener(new ValueEventListener() {
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

    public void SaveToFireBase2(View view) {

        if (found) {
            new AlertDialog.Builder(RequestForUserManual.this)
                    .setTitle("Save a Life Team")
                    .setMessage("No cant Request a blood right now")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    }).show();
        }
        else{
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

                    re.setFuName(FName.getText().toString() + " " + LName.getText().toString());
                    re.setMobile(Mobile.getText().toString());
                    re.setBloodGroup(BloodGroup.getSelectedItem().toString());
                    re.setNumberOfUnites(Integer.parseInt(numberOfunits.getText().toString()));
                    re.setHospitalName(HospitalName.getSelectedItem().toString());
                    myRef2.child(recordID).setValue(re);

                    Toast.makeText(this, "Save  " + recordID, Toast.LENGTH_LONG).show();

                } else {

                    if (!TextUtils.isEmpty(recordID)) {

                        if ((!TextUtils.isEmpty(FName.getText().toString()) && (!TextUtils.isEmpty(LName.getText().toString()))))
                            myRef2.child(recordID).child("fuName").setValue(FName.getText().toString() + " " + LName.getText().toString());

                        if (!TextUtils.isEmpty(Mobile.getText().toString()))
                            myRef2.child(recordID).child("Mobile").setValue(Mobile.getText().toString());

                        if (!TextUtils.isEmpty(BloodGroup.getSelectedItem().toString()))
                            myRef2.child(recordID).child("BloodGroup").setValue(BloodGroup.getSelectedItem().toString());


                        if (!TextUtils.isEmpty(HospitalName.getSelectedItem().toString()))
                            myRef2.child(recordID).child("HospitalName").setValue(HospitalName.getSelectedItem().toString());

                        if (!TextUtils.isEmpty(numberOfunits.getText().toString()))
                            myRef2.child(recordID).child("numberOfUnites").setValue(Integer.parseInt(numberOfunits.getText().toString()));


                    }

                }

            } else {
                if (TextUtils.isEmpty(recordID)) {
                    recordID = myRef2.push().getKey();

                    Requests re = new Requests();
                    re.setFuName(FName.getText().toString() + " " + LName.getText().toString());
                    re.setMobile(Mobile.getText().toString());
                    re.setBloodGroup(BloodGroup.getSelectedItem().toString());
                    re.setHospitalName(HospitalName.getSelectedItem().toString());
                    re.setNumberOfUnites(Integer.parseInt(numberOfunits.getText().toString()));
                    myRef2.child(recordID).setValue(re);

                    Toast.makeText(this, "Save  " + recordID, Toast.LENGTH_LONG).show();

                } else {

                    if (!TextUtils.isEmpty(recordID)) {

                        if ((!TextUtils.isEmpty(FName.getText().toString()) && (!TextUtils.isEmpty(LName.getText().toString()))))
                            myRef2.child(recordID).child("fuName").setValue(FName.getText().toString() + " " + LName.getText().toString());

                        if (!TextUtils.isEmpty(Mobile.getText().toString()))
                            myRef2.child(recordID).child("Mobile").setValue(Mobile.getText().toString());

                        if (!TextUtils.isEmpty(BloodsType.getSelectedItem().toString()))
                            myRef2.child(recordID).child("BloodGroup").setValue(BloodsType.getSelectedItem().toString());


                        if (!TextUtils.isEmpty(HospitalName.getSelectedItem().toString()))
                            myRef2.child(recordID).child("HospitalName").setValue(HospitalName.getSelectedItem().toString());

                        if (!TextUtils.isEmpty(numberOfunits.getText().toString()))
                            myRef2.child(recordID).child("numberOfUnites").setValue(Integer.parseInt(numberOfunits.getText().toString()));


                    }

                }
            }
        }


        recordID2 = myRef.push().getKey();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (TextUtils.isEmpty(recordID2)) {

                    Requests req = new Requests();

                    req.setMobile(Mobile.getText().toString());
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