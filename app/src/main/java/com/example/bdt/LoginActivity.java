package com.example.bdt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private Firebase mDatabase;
    EditText MobileNumber, Password;
    Boolean found = false;
    String mobile,PasswordF,recordidUser,city,blood,Fullname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);
        mDatabase = new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/UserTable");
        MobileNumber =(EditText)findViewById(R.id.MobileNumber);
        Password = (EditText)findViewById(R.id.Password);
    }

    public void Login(View v) {
        found = checkYouInfo();
        if (!found){
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("Blood Donation Team")
                    .setMessage("Your number dose not exist , Sign up your information")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    }).show();
        }
    }


    public void Signup(View v)
    {
        Intent x = new Intent(this,SignUpActivity.class);
        startActivity(x);
    }

    public boolean checkYouInfo()
    {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Map<String, String> map = dataSnapshot1.getValue(Map.class);
                    mobile = map.get("mobile");
                    PasswordF = map.get("password");
                    recordidUser = dataSnapshot1.getKey();

                    Fullname = map.get("fname") + " " + map.get("lname");
                    blood = map.get("bloodGroup");
                    city = map.get("city");

                    //check for the password and mobileNumber

                    if ((mobile.equals(MobileNumber.getText().toString()))
                            && PasswordF.equals(Password.getText().toString())) {
                        found = true;
                        Intent x = new Intent(getApplicationContext(), HomePageActivity.class);
                        x.putExtra("FullName", Fullname);
                        x.putExtra("BloodGroup", blood);
                        x.putExtra("City", city);
                        x.putExtra("Mobile", mobile);
                        x.putExtra("recordId", recordidUser);
                        startActivity(x);
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
        return found;
    }
}