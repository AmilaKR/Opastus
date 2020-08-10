package com.akr.amila.opastus;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.akr.amila.opastus.LoginActivity.myRef;

public class ScheduleActivity extends AppCompatActivity {

    FloatingActionButton fab;
    ScheduleItem item;
    DatabaseReference ref = myRef.child("schedule");
    ArrayList<ScheduleItem> scheduleItems;
    ArrayList<String> scheduleIndex;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        fab = findViewById(R.id.floatingActionButton2);
        recyclerView = findViewById(R.id.recyclerView);


        recyclerView.setHasFixedSize(true);   //scroll the list
        layoutManager = new LinearLayoutManager(this);

        scheduleItems = new ArrayList<>();
        scheduleIndex = new ArrayList<>();
        adapter = new ScheduleAdaptor(scheduleItems);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                scheduleIndex.add(dataSnapshot.getKey());

                item = dataSnapshot.getValue(ScheduleItem.class);
                scheduleItems.add(item);
                adapter = new ScheduleAdaptor(scheduleItems);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                item = dataSnapshot.getValue(ScheduleItem.class);
                scheduleItems.add(item);
                scheduleItems = new ArrayList<>();
                adapter = new ScheduleAdaptor(scheduleItems);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onLongItemClick(View view, int position) {
                String name = scheduleItems.get(position).title;
                String id = scheduleIndex.get(position);
                confirm(view, name, id);
                alertDialog.show();
            }
        }
        ));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),NewItem.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent,1234);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this.getBaseContext(),MainActivity.class);
        startActivity(intent);
    }

    private void confirm(final View v, String name, final String id){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Edit " + name);
        alertDialogBuilder.setPositiveButton("Edit",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(v.getContext(),NewItem.class);
                        intent.putExtra("ID",id);
                        startActivity(intent);
                    }
                });

        alertDialogBuilder.setNegativeButton("Delete",
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ref.child(id).removeValue();
                Toast.makeText(v.getContext(), "Item Deleted.", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        alertDialog = alertDialogBuilder.create();
    }
}