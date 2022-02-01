package com.example.bdt.Donation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bdt.Classes.Requests;
import com.example.bdt.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.viewitem>{
    TextView FullName,BloodGroup,Hospital, MobileNumber, NumberOfunits;
    Button DonationB;

    Context context;
    ArrayList<Requests> items;
    String MyPhoneNumber;
    String currentDate;

    String id;
    public RecyclerViewAdapter(Context c, ArrayList<Requests> item, String mobile){
        context =c;
        items = item;
        MyPhoneNumber =mobile;
    }


    class viewitem extends RecyclerView.ViewHolder{

        public viewitem(View itemView){
            super(itemView);

            Calendar c = Calendar.getInstance();
            currentDate = DateFormat.getDateInstance().format(c.getTime());

            FullName = (TextView)itemView.findViewById(R.id.FullnameDonation);
            BloodGroup = (TextView)itemView.findViewById(R.id.BloodGroupDonation);
            Hospital = (TextView)itemView.findViewById(R.id.HospitalDonation);
            MobileNumber = (TextView)itemView.findViewById(R.id.mobileDonation);
            DonationB = (Button)itemView.findViewById(R.id.Donations);
            NumberOfunits = (TextView) itemView.findViewById(R.id.UnitesDonation);
        }
    }


    @Override
    public viewitem onCreateViewHolder(final ViewGroup Parent, int i) {
        final View itemView = LayoutInflater.from(Parent.getContext())
                .inflate(R.layout.row,Parent,false);

        itemView.findViewById(R.id.Donations).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x = new Intent(Parent.getContext(),InformationAboutDonationActivity.class);
                x.putExtra("number", MyPhoneNumber);
                String id = (String) v.getTag();
                x.putExtra("donationRequestId",id);
                context.startActivity(x);
            }
        });

        return new viewitem(itemView);
    }

    @Override
    public void onBindViewHolder(viewitem holder, int i) {
        FullName.setText(items.get(i).getFullName());
        MobileNumber.setText(items.get(i).getMobileNumber());
        Hospital.setText(items.get(i).getHospitalName());
        BloodGroup.setText(items.get(i).getBloodGroup());
        NumberOfunits.setText(items.get(i).getNumberOfUnites()+"");
        DonationB.setTag(items.get(i).getUserid()+"-"+items.get(i).getHospitalName());



    }

    @Override
    public int getItemCount() {
        return  items.size();
    }
}