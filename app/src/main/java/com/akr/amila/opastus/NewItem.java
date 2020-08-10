package com.akr.amila.opastus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import static com.akr.amila.opastus.LoginActivity.myRef;

public class NewItem extends Activity {

    EditText title;
    EditText desc;
    EditText date;
    EditText time;
    int size;

    ScheduleItem item;
    String id;
    Boolean edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);
        title = findViewById(R.id.titleTxt);
        desc = findViewById(R.id.descTxt);
        date = findViewById(R.id.dateTxt);
        time = findViewById(R.id.timeTxt);

        DisplayMetrics displayMetrics = new DisplayMetrics();               //pop up
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*0.8),(int)(height*0.7));

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                edit = false;
            } else {
                edit = true;
                id = extras.getString("ID");
                myRef.child("schedule/"+id)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // get total available quest
                                item = dataSnapshot.getValue(ScheduleItem.class);
                                title.setText(item.title);
                                desc.setText(item.desc);
                                date.setText(item.date);
                                time.setText(item.time);
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }
        } else {
            id = (String) savedInstanceState.getSerializable("ID");
        }

        System.out.println(edit);
    }

    public void addNewItem(View view) {

        if (!edit) {
            DatabaseReference childRef = myRef.child("schedule/");
            Map<String, String> data = new HashMap<String, String>();
            data.put("title", title.getText().toString());
            data.put("desc", desc.getText().toString());
            data.put("date", date.getText().toString());
            data.put("time", time.getText().toString());

            childRef.push().setValue(data);

            Toast.makeText(NewItem.this, "New Item Added.", Toast.LENGTH_SHORT).show();
            this.finish();
            //Intent intent = new Intent(view.getContext(), ScheduleActivity.class);
            //startActivity(intent);
        }else{
            Map<String, Object> data = new HashMap<>();
            data.put("title", title.getText().toString());
            data.put("desc", desc.getText().toString());
            data.put("date", date.getText().toString());
            data.put("time", time.getText().toString());
            myRef.child("schedule/"+id).updateChildren(data);
            Toast.makeText(NewItem.this, "Item Updated.", Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }
}
