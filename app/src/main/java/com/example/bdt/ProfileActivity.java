package com.example.bdt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private Firebase UserTableFireBase, UserTableAndRecordID;

    String currentDate;
    String MobileFromProfile;
    String RecordIduser;
    String password;


    TextView FullName, MobileNumber, BloodGroup, City, Nationality;
    EditText Password;
    Button signout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Firebase.setAndroidContext(this);

        FullName = (TextView) findViewById(R.id.FullNameProfile);
        MobileNumber = (TextView) findViewById(R.id.MobileNumberProfile);
        BloodGroup = (TextView) findViewById(R.id.BloodGroupProfile);
        City = (TextView) findViewById(R.id.cityProfile);
        Nationality = (TextView) findViewById(R.id.nationalityProfile);
        Password = (EditText) findViewById(R.id.passwordProfile);
        signout = (Button) findViewById(R.id.Signout);

        RecordIduser = getIntent().getStringExtra("recordId");
        MobileFromProfile = getIntent().getStringExtra("Mobile");
        Calendar calendar = Calendar.getInstance();
        currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        UserTableFireBase = new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/UserTable");
        UserTableAndRecordID = new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/UserTable/" + RecordIduser);

        getInformationForUser();
    }

    public void getInformationForUser() {
        if (MobileFromProfile != null) {
            UserTableFireBase.addValueEventListener(new com.firebase.client.ValueEventListener() {
                @Override
                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Map<String, String> map = dataSnapshot1.getValue(Map.class);
                        if (MobileFromProfile.equalsIgnoreCase(map.get("mobile"))) {
                            String FullName = map.get("firstName");
                            String LastName = map.get("lastName");
                            String mobile = map.get("mobile");
                            String blood = map.get("bloodGroup");
                            String city = map.get("address");
                            String nationality = map.get("nationalatiy");
                            String password = map.get("password");

                            ProfileActivity.this.FullName.setText(FullName + " " + LastName);
                            MobileNumber.setText(mobile);
                            BloodGroup.setText(blood);
                            City.setText(city);
                            Nationality.setText(nationality);
                            Password.setText(password);
                        }
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.backToHomePage) {

            Intent x = new Intent(this, HomePageActivity.class);
            x.putExtra("Mobile", getIntent().getStringExtra("Mobile"));
            x.putExtra("BloodGroup", getIntent().getStringExtra("BloodGroup"));
            x.putExtra("recordId", getIntent().getStringExtra("recordId"));
            x.putExtra("FullName", getIntent().getStringExtra("FullName"));
            startActivity(x);
        }

        return super.onOptionsItemSelected(item);
    }


    public void Signout(View view) {
        Intent x = new Intent(this, LoginActivity.class);
        MobileNumber.setText("");
        Password.setText("");
        startActivity(x);

    }

    public void EditInfo(View view) {
        password = Password.getText().toString();
        UserTableAndRecordID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserTableAndRecordID.child("password").setValue(password);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}