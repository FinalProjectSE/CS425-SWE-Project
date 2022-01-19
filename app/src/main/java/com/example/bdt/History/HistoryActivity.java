package com.example.bdt.History;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bdt.Classes.Records;
import com.example.bdt.HomePageActivity;
import com.example.bdt.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {


    private Firebase mDatabase,mDatabase2;
    RecyclerView recyclerView;
    Records r = new Records();
    String my;
    ArrayList<Records> records = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Firebase.setAndroidContext(this);
        recyclerView = (RecyclerView)findViewById(R.id.myRecyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        my=getIntent().getStringExtra("mobile");
        mDatabase = new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/RequesterTable");
        mDatabase2 = new Firebase("https://finalprojectmiu-default-rtdb.firebaseio.com/DonationTable");

        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    Map<String, String> map = dataSnapshot1.getValue(Map.class);

                    if(my.equals(map.get("Mobile"))) {
                        r = new Records();
                        r.setDate(" you Have request a blood in " +map.get("Date"));
                        records.add(r);
                    }
                }

                HistoryRecyclerView a = new HistoryRecyclerView(getApplicationContext(),records);
                recyclerView.setAdapter(a);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mDatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    Map<String, String> map = dataSnapshot1.getValue(Map.class);

                    if(my.equals(map.get("myMobile"))) {
                        r = new Records();
                        r.setDate(" you Have Donate a blood in " + map.get("date"));
                        records.add(r);
                    }
                }

                HistoryRecyclerView a = new HistoryRecyclerView(getApplicationContext(),records);
                recyclerView.setAdapter(a);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

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
}