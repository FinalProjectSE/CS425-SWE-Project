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
    private Firebase HospitalTableFireBase, RequestBloodFireBase;
    DatabaseReference RequesterTableRef, RequestBloodRef;
    ArrayList<String> list;


    //Declare
    TextView FullName, MobileNumber, BloodGroup;
    Spinner Bloods, HospitalName;
    EditText numberOfUnites;
    Button Request;

    //for DataReference
    String recordId = "";
    String recordid2 = "";

    String currentDate;
    Boolean MobileFoundinDatabase = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_for_user);

        Firebase.setAndroidContext(this);

        FullName = (TextView) findViewById(R.id.FUname);
        MobileNumber = (TextView) findViewById(R.id.MobileRe);
        BloodGroup = (TextView) findViewById(R.id.BloodGroupRe);
        numberOfUnites = (EditText) findViewById(R.id.NumberOfUniteRe);
        Bloods = (Spinner) findViewById(R.id.BloodsRe);
        Request = (Button) findViewById(R.id.RequestUser);
        HospitalName = (Spinner) findViewById(R.id.HospitalNameUser);

        HospitalTableFireBase = new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/HospitalTable");
        RequestBloodFireBase = new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/RequestBlood");

        list = new ArrayList<>();
        list.add("null");


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,list);
        HospitalName.setAdapter(adapter);

        Calendar c = Calendar.getInstance();
        currentDate = DateFormat.getDateInstance().format(c.getTime());

        RequestBloodRef = FirebaseDatabase.getInstance().getReference("RequestBlood");
        RequesterTableRef = FirebaseDatabase.getInstance().getReference("RequesterTable");

        HospitalRequest();
        RequestBlood();

        FullName.setText(getIntent().getStringExtra("FullName"));
        BloodGroup.setText(getIntent().getStringExtra("BloodGroup"));
        MobileNumber.setText(getIntent().getStringExtra("Mobile"));
    }


    public void Request(View view) {

        if (MobileFoundinDatabase) {
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
                            SaveRequest();
                        }
                    }).show();
    }

    public void SaveRequest() {

        if ((TextUtils.isEmpty(recordid2)) && TextUtils.isEmpty(recordId)) {
            if (Bloods.getSelectedItem().toString().equals("BloodGroup")) {

                if (TextUtils.isEmpty(recordId)) {
                    recordId = RequesterTableRef.push().getKey();
                    Requests requests = new Requests();

                    requests.setFullName(FullName.getText().toString());
                    requests.setMobileNumber(MobileNumber.getText().toString());
                    requests.setBloodGroup(BloodGroup.getText().toString());
                    requests.setHospitalName(HospitalName.getSelectedItem().toString());
                    requests.setNumberOfUnites(Integer.parseInt(numberOfUnites.getText().toString()));
                    RequestBloodRef.child(recordId).setValue(requests);

                }
            }
        }


        HospitalTableFireBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (TextUtils.isEmpty(recordid2)) {
                    recordid2 = RequesterTableRef.push().getKey();
                    RequestBlood requests = new RequestBlood();

                    requests.setMobileNumber(MobileNumber.getText().toString());
                    requests.setDate(currentDate);

                    RequesterTableRef.child(recordid2).setValue(requests);


                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void HospitalRequest(){
        HospitalTableFireBase.addValueEventListener(new ValueEventListener() {
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
    }
    public void RequestBlood(){
        RequestBloodFireBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    HashMap<String, Object> map = dataSnapshot1.getValue(HashMap.class);

                    String MobileNumberFromFirebase = (String) map.get("mobileNumber");
                    int numberOfUnitesFromFirebase = (int) map.get("numberOfUnites");
                    if (numberOfUnitesFromFirebase > 0) {
                        if (MobileNumberFromFirebase.equalsIgnoreCase(MobileNumber.getText().toString())) {
                            MobileFoundinDatabase = true;
                        }
                    }

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

}