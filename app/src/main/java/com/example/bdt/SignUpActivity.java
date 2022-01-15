package com.example.bdt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bdt.Classes.User;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    DatabaseReference myRef;
    private Firebase mDatabase;
    Boolean isNewRecord=true;

    String recordID = "";

    //Declare
    EditText FName,LName,Mobile,Password,ConfirmPassword,Nationality,Address;
    RadioButton Female , Male;
    Spinner City,BloodGroup;
    Button SignUp;
    boolean found=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Firebase.setAndroidContext(this);
        FName = findViewById(R.id.FName);
        LName = findViewById(R.id.LName);
        Mobile = findViewById(R.id.mobile);
        Password = findViewById(R.id.password);
        ConfirmPassword = findViewById(R.id.ConfPassword);
        Nationality = findViewById(R.id.Nationality);
        Address = findViewById(R.id.Address);
        Female = findViewById(R.id.FemaleGender);
        Male = findViewById(R.id.MaleGender);
        City = findViewById(R.id.CitySpinner);
        BloodGroup = findViewById(R.id.BloodGroup);
        SignUp = findViewById(R.id.Signup);

        myRef = FirebaseDatabase.getInstance().getReference("UserTable");
        mDatabase = new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/UserTable");


    }



    public void SaveToFireBase(View view) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Map<String, String> map = dataSnapshot1.getValue(Map.class);
                    String mobile = map.get("mobile");
                    if (mobile.equals(Mobile.getText().toString())) {
                        found = true;
                    }
                }
                r();


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void r() {

        if (found)
            new AlertDialog.Builder(SignUpActivity.this)
                    .setTitle("Save a Life Team")
                    .setMessage("You already have an account in the system")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    }).show();
        else {


            final String sfirstName = FName.getText().toString();
            final String slastName = LName.getText().toString();
            final String smobileNumber = Mobile.getText().toString();
            final String spassword = Password.getText().toString();
            final String sconfirmPassword = ConfirmPassword.getText().toString();
            final String snationality = Nationality.getText().toString();
            final String saddress = Address.getText().toString();
            final String scity = Address.getText().toString();


            if (TextUtils.isEmpty(sfirstName)) {
                Toast.makeText(getApplicationContext(), "Please Enter Your First Name!", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(slastName)) {
                Toast.makeText(getApplicationContext(), "Please Enter Your Last Name!", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(smobileNumber)) {
                Toast.makeText(getApplicationContext(), "Please Enter Your Mobile Number!", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(spassword)) {
                Toast.makeText(getApplicationContext(), "Please Enter Your Password!", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(sconfirmPassword)) {
                Toast.makeText(getApplicationContext(), "Please Confirm Your Password!", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(snationality)) {
                Toast.makeText(getApplicationContext(), "Please Enter Your Nationality!", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(saddress)) {
                Toast.makeText(getApplicationContext(), "Please Enter Your Address!", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(scity)) {
                Toast.makeText(getApplicationContext(), "Please Enter Your City!", Toast.LENGTH_SHORT).show();
                return;
            } else if (spassword.length() < 6) {
                Toast.makeText(getApplicationContext(), "your password is short, must be at least 6 characters!", Toast.LENGTH_SHORT).show();
                return;
            } else if (!spassword.equals(sconfirmPassword)) {
                Toast.makeText(getApplicationContext(), "Your Passwords dose not match please Enter your password Again!", Toast.LENGTH_SHORT).show();
                Password.setText("");
                ConfirmPassword.setText("");
                return;
            } else {


                if (TextUtils.isEmpty(recordID)) {
                    recordID = myRef.push().getKey();


                    User u = new User();
                    u.setFName(FName.getText().toString());
                    u.setLName(LName.getText().toString());
                    u.setMobile(Mobile.getText().toString());
                    u.setPassword(Password.getText().toString());
                    u.setNationalatiy(Nationality.getText().toString());
                    u.setAddress(Address.getText().toString());
                    u.setCity(City.getSelectedItem().toString());
                    u.setBloodGroup(BloodGroup.getSelectedItem().toString());


                    myRef.child(recordID).setValue(u);
                    // addRecordChangeListener();
                    Toast.makeText(this, "Save  " + recordID, Toast.LENGTH_LONG).show();

                    Intent x = new Intent(this, LoginActivity.class);
                    startActivity(x);

                    //myRef.setValue(m);
                    //myRef.child("UserTable").child(userId).child("username").setValue(name);

                } else {

                    if (!TextUtils.isEmpty(recordID)) {

                        if (!TextUtils.isEmpty(FName.getText().toString()))
                            myRef.child(recordID).child("FName").setValue(FName.getText().toString());

                        if (!TextUtils.isEmpty(LName.getText().toString()))
                            myRef.child(recordID).child("LName").setValue(LName.getText().toString());

                        if (!TextUtils.isEmpty(Mobile.getText().toString()))
                            myRef.child(recordID).child("Mobile").setValue(Mobile.getText().toString());

                        if (!TextUtils.isEmpty(Password.getText().toString()))
                            myRef.child(recordID).child("Password").setValue(Password.getText().toString());

                        if (!TextUtils.isEmpty(BloodGroup.getSelectedItem().toString()))
                            myRef.child(recordID).child("BloodGroup").setValue(BloodGroup.getSelectedItem().toString());

                        if (!TextUtils.isEmpty(City.getSelectedItem().toString()))
                            myRef.child(recordID).child("City").setValue(City.getSelectedItem().toString());

                        if (!TextUtils.isEmpty(Address.getText().toString()))
                            myRef.child(recordID).child("Address").setValue(Address.getText().toString());


                        if (!TextUtils.isEmpty(Nationality.getText().toString()))
                            myRef.child(recordID).child("Nationality").setValue(Nationality.getText().toString());
                    }

                }

            }
        }
    }
}