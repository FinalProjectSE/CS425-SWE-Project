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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.history){

            Intent x = new Intent(this, HistoryActivity.class);
            x.putExtra("mobile",getIntent().getStringExtra("Mobile"));
            x.putExtra("BloodGroup", getIntent().getStringExtra("BloodGroup"));
            startActivity(x);
        }
        else if (item.getItemId()==R.id.profileUser){
            Intent x = new Intent(this,ProfileActivity.class);
            x.putExtra("mobile",getIntent().getStringExtra("Mobile"));
            x.putExtra("recordId",getIntent().getStringExtra("recordId"));
            x.putExtra("BloodGroup", getIntent().getStringExtra("BloodGroup"));
            startActivity(x);
        }



        return super.onOptionsItemSelected(item);
    }

    public void Request(View view)
    {
        Intent x=new Intent(this, RequestActivity.class);
        x.putExtra("FullName", getIntent().getStringExtra("FullName"));
        x.putExtra("BloodGroup", getIntent().getStringExtra("BloodGroup"));
        x.putExtra("Mobile",getIntent().getStringExtra("Mobile"));
        startActivity(x);
    }


    public void Donation(View view)
    {
        Intent x = new Intent(this, DonationActivity.class);
        x.putExtra("Mobile", getIntent().getStringExtra("Mobile"));
        x.putExtra("BloodGroup",getIntent().getStringExtra("BloodGroup"));
        startActivity(x);
    }


    public void AboutUs(View view)
    {
        Intent x=new Intent(this,About_us.class);
        startActivity(x);
    }
}