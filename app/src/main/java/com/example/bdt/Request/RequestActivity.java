package com.example.bdt.Request;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.bdt.R;

public class RequestActivity extends AppCompatActivity {


    CheckBox RequestCheckBox ;
    Button CheckedButton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        RequestCheckBox = findViewById(R.id.requestCheckBox);
        CheckedButton = findViewById(R.id.checkRequestbutton);
    }

    public void checkRequest(View view) {

        if (RequestCheckBox.isChecked())
        {
            Intent x=new Intent(this,RequestForUserActivity.class);
            x.putExtra("FullName", getIntent().getStringExtra("FullName"));
            x.putExtra("BloodGroup", getIntent().getStringExtra("BloodGroup"));
            x.putExtra("Mobile",getIntent().getStringExtra("Mobile"));
            startActivity(x);

        }
        else
        {
            Intent x=new Intent(this,RequestNotForUserActivity.class);
            startActivity(x);
        }
    }
}