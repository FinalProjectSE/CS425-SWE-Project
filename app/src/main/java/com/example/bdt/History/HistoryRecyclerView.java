package com.example.bdt.History;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bdt.Classes.Records;
import com.example.bdt.R;

import java.util.ArrayList;

public class HistoryRecyclerView extends RecyclerView.Adapter<HistoryRecyclerView.viewitem> {

    Context context;
    ArrayList<Records> items;

    public HistoryRecyclerView(Context c, ArrayList<Records> item){
        context =c;
        items = item;
    }

    class  viewitem extends RecyclerView.ViewHolder{
        TextView Date2;

        public viewitem(View itemView){
            super(itemView);
            Date2 = (TextView)itemView.findViewById(R.id.Date);

        }
    }
    @NonNull
    @Override
    public HistoryRecyclerView.viewitem onCreateViewHolder(@NonNull ViewGroup Parent, int i) {
        View itemView = LayoutInflater.from(Parent.getContext())
                .inflate(R.layout.history_row,Parent,false);

        return new HistoryRecyclerView.viewitem(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryRecyclerView.viewitem holder, int i) {
        holder.Date2.setText(items.get(i).getDate());
    }

    @Override
    public int getItemCount() {
        return  items.size();
    }
}