package com.akr.amila.opastus;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ScheduleAdaptor extends RecyclerView.Adapter<ScheduleAdaptor.ScheduleHolder> {


    private ArrayList<ScheduleItem> items;
    public static class ScheduleHolder extends RecyclerView.ViewHolder{


        public TextView textTitle;
        public TextView textDesc;
        public TextView textTime;

        public ScheduleHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_title);
            textDesc = itemView.findViewById(R.id.text_desc);
            textTime = itemView.findViewById(R.id.text_time);
        }
    }

    public ScheduleAdaptor(ArrayList<ScheduleItem> scheduleItems) {
        items = scheduleItems;
    }

    @NonNull
    @Override
    public ScheduleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.schedule_item,viewGroup,false);
        ScheduleHolder sh = new ScheduleHolder(v);
        return sh;
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleHolder scheduleHolder, int i) {
        ScheduleItem scheduleItems = items.get(i);

        scheduleHolder.textTitle.setText(scheduleItems.getTitle());
        scheduleHolder.textDesc.setText(scheduleItems.getDesc());
        scheduleHolder.textTime.setText(scheduleItems.getTime());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
