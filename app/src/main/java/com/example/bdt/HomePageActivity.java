package com.example.bdt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.bdt.Donation.DonationActivity;
import com.example.bdt.History.HistoryActivity;
import com.example.bdt.Request.RequestActivity;

public class HomePageActivity extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.history) {
            intent = new Intent(this, HistoryActivity.class);

        } else if (item.getItemId() == R.id.profileUser) {
            intent = new Intent(this, ProfileActivity.class);
        }

        intent.putExtra("Mobile", getIntent().getStringExtra("Mobile"));
        intent.putExtra("recordId", getIntent().getStringExtra("recordId"));
        intent.putExtra("BloodGroup", getIntent().getStringExtra("BloodGroup"));
        intent.putExtra("FullName", getIntent().getStringExtra("FullName"));
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }

    public void Request(View view) {
        Intent intent = new Intent(this, RequestActivity.class);
        intent.putExtra("FullName", getIntent().getStringExtra("FullName"));
        intent.putExtra("BloodGroup", getIntent().getStringExtra("BloodGroup"));
        intent.putExtra("Mobile", getIntent().getStringExtra("Mobile"));
        startActivity(intent);
    }


    public void Donation(View view) {
        Intent intent = new Intent(this, DonationActivity.class);
        intent.putExtra("Mobile", getIntent().getStringExtra("Mobile"));
        intent.putExtra("FullName", getIntent().getStringExtra("FullName"));
        intent.putExtra("BloodGroup", getIntent().getStringExtra("BloodGroup"));
        startActivity(intent);
    }


    public void AboutUs(View view) {
        Intent intent = new Intent(this, About_us.class);
        startActivity(intent);
    }

}