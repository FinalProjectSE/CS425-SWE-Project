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

import com.example.bdt.History.HistoryActivity;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private Firebase mDatabase,mDatabase3;
    String currentDate;
    String mobile2;

    boolean found=false;
    String RecordIduser;

    String password;
    //Declare
    TextView Name,MobileNumber,BloodGroup, City, Nationality;
    EditText Password;
    Button signout;

    String MOBILE,PASSWORD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Firebase.setAndroidContext(this);
        Name = (TextView) findViewById(R.id.FUnamePr);
        MobileNumber = (TextView) findViewById(R.id.MobilePr);
        BloodGroup = (TextView) findViewById(R.id.BloodGroupPr);
        City = (TextView) findViewById(R.id.cityPr);
        Nationality = (TextView) findViewById(R.id.nationalityPr);
        Password = (EditText) findViewById(R.id.passwordPr);
        signout = (Button) findViewById(R.id.Signout);

        RecordIduser = getIntent().getStringExtra("recordId");
        mDatabase = new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/UserTable");
        mDatabase3 = new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/UserTable/" + RecordIduser);

        Calendar c = Calendar.getInstance();
        currentDate = DateFormat.getDateInstance().format(c.getTime());


        mobile2 = getIntent().getStringExtra("mobile");


        if (mobile2 != null) {
            mDatabase.addValueEventListener(new com.firebase.client.ValueEventListener() {
                @Override
                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                     for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                           Map<String, String> map = dataSnapshot1.getValue(Map.class);
                                if (mobile2.equalsIgnoreCase(map.get("mobile"))) {
                                    String Fname = map.get("fname");
                                    String Lname = map.get("lname");
                                    String mobile = map.get("mobile");
                                    String blood = map.get("bloodGroup");
                                    String city = map.get("city");
                                    String nationality = map.get("nationalatiy");
                                    String password = map.get("password");

                                    Name.setText(Fname + " " + Lname);
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


                Name.setText(getIntent().getStringExtra("FullName"));
                BloodGroup.setText(getIntent().getStringExtra("BloodType"));
                MobileNumber.setText(getIntent().getStringExtra("mobile"));
                City.setText(getIntent().getStringExtra("City"));
                Nationality.setText(getIntent().getStringExtra("Nationality"));
                Password.setText(getIntent().getStringExtra("Password"));

            }
        }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.backToHomePage){

            Intent x = new Intent(this, HomePageActivity.class);
            x.putExtra("mobile",getIntent().getStringExtra("Mobile"));
            x.putExtra("BloodGroup", getIntent().getStringExtra("BloodGroup"));
            startActivity(x);
        }

        return super.onOptionsItemSelected(item);
    }




    public void Signout(View view) {
        Intent x = new Intent(this,LoginActivity.class);
        MobileNumber.setText("");
        Password.setText("");
        startActivity(x);

    }

    public void EditInfo(View view) {
        password = Password.getText().toString();
        mDatabase3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mDatabase3.child("password").setValue(password);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}