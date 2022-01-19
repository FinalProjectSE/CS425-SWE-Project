package com.example.bdt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
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

    DatabaseReference userTableReferance;
    private Firebase userTableDatabase;

    String recordID = "";

    // Declare
    EditText firstName, lastName, mobile, password, confirmPassword, nationality, address;
    RadioButton female, male;
    Spinner city, bloodGroup;
    Button signUp;

    boolean userMobileFound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Firebase.setAndroidContext(this);
        firstName = findViewById(R.id.FName);
        lastName = findViewById(R.id.LName);
        mobile = findViewById(R.id.mobile);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.ConfPassword);
        nationality = findViewById(R.id.Nationality);
        address = findViewById(R.id.Address);
        female = findViewById(R.id.FemaleGender);
        male = findViewById(R.id.MaleGender);
        city = findViewById(R.id.CitySpinner);
        bloodGroup = findViewById(R.id.BloodGroup);
        signUp = findViewById(R.id.Signup);

        userTableReferance = FirebaseDatabase.getInstance().getReference("UserTable");
        userTableDatabase = new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/UserTable");

    }

    public void SaveToFireBase(View view) {
        userTableDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Map<String, String> map = dataSnapshot1.getValue(Map.class);
                    String mobile = map.get("mobile");
                    if (mobile.equals(SignUpActivity.this.mobile.getText().toString())) {
                        userMobileFound = true;
                    }
                }
                saveingInfo();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void saveingInfo() {
        if (userMobileFound)
            new AlertDialog.Builder(SignUpActivity.this).setTitle("Blood Donation System Team")
                    .setMessage("You already have an account in the system").setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Intent goToLoginPage = new Intent(SignUpActivity.this,LoginActivity.class);
                            startActivity(goToLoginPage);
                        }
                    }).show();
        else {
            if (hasAnEmptyFields() && TextUtils.isEmpty(recordID)) {
                RecordIdEmpty();
            }
        }
    }

    public boolean hasAnEmptyFields() {

        if (TextUtils.isEmpty(firstName.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please Enter Your First Name!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(lastName.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please Enter Your Last Name!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(mobile.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please Enter Your Mobile Number!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(password.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please Enter Your Password!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(confirmPassword.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please Confirm Your Password!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(nationality.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please Enter Your Nationality!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(address.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please Enter Your Address!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(city.getSelectedItem().toString())) {
            Toast.makeText(getApplicationContext(), "Please Enter Your City!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.getText().toString().length() < 6) {
            Toast.makeText(getApplicationContext(), "your password is short, must be at least 6 characters!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Your Passwords dose not match please Enter your password Again!",
                    Toast.LENGTH_SHORT).show();
            password.setText("");
            confirmPassword.setText("");
            return false;
        }
        return true;
    }

    public void RecordIdEmpty() {
        recordID = userTableReferance.push().getKey();
        User user = new User();
        user.setFirstName(firstName.getText().toString());
        user.setLastName(lastName.getText().toString());
        user.setMobile(mobile.getText().toString());
        user.setPassword(password.getText().toString());
        user.setNationalatiy(nationality.getText().toString());
        user.setAddress(address.getText().toString());
        user.setCity(city.getSelectedItem().toString());
        user.setBloodGroup(bloodGroup.getSelectedItem().toString());

        userTableReferance.child(recordID).setValue(user);
        Toast.makeText(this, "Save  " + recordID, Toast.LENGTH_LONG).show();

        Intent x = new Intent(this, LoginActivity.class);
        startActivity(x);

    }


}