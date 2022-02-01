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

    private Firebase userTableDataBase;
    EditText MobileNumber, Password;
    Boolean informationFounded = false;
    String mobileNumberFromFirebase,
            passwordFromFirebase, recordUseridFirebase,
            cityFromFirebase, bloodFromFirebase, fullNameFromFirebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);
        userTableDataBase = new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/UserTable");
        MobileNumber = (EditText) findViewById(R.id.MobileNumber);
        Password = (EditText) findViewById(R.id.Password);
    }

    public void Signup(View v) {
        Intent x = new Intent(this, SignUpActivity.class);
        startActivity(x);
    }

    public void Login(View v) {
        checkYouInfo();
    }

    public void checkYouInfo() {
        userTableDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Map<String, String> map = dataSnapshot1.getValue(Map.class);
                    mobileNumberFromFirebase = map.get("mobile");
                    passwordFromFirebase = map.get("password");
                    recordUseridFirebase = dataSnapshot1.getKey();
                    fullNameFromFirebase = map.get("firstName") + " " + map.get("lastName");
                    bloodFromFirebase = map.get("bloodGroup");
                    cityFromFirebase = map.get("city");
                    if ((mobileNumberFromFirebase.equals(MobileNumber.getText().toString()))
                            && passwordFromFirebase.equals(Password.getText().toString())) {
                        informationFounded = true;
                        break;
                    }

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
        GoToHomePage();

    }

    public void GoToHomePage() {
        if (informationFounded) {
            Intent x = new Intent(getApplicationContext(), HomePageActivity.class);
            x.putExtra("FullName", fullNameFromFirebase);
            x.putExtra("BloodGroup", bloodFromFirebase);
            x.putExtra("City", cityFromFirebase);
            x.putExtra("Mobile", mobileNumberFromFirebase);
            x.putExtra("recordId", recordUseridFirebase);
            startActivity(x);

        } else {
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
}
