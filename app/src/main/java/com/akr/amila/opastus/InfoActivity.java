package com.akr.amila.opastus;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static com.akr.amila.opastus.LoginActivity.myRef;
import static com.akr.amila.opastus.LoginActivity.robot;

public class InfoActivity extends AppCompatActivity {

    ImageView batt;
    TextView pre;
    TextView rem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        batt = findViewById(R.id.battView);
        pre = findViewById(R.id.batText);
        rem = findViewById(R.id.remText);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                int bat = robot.getBat();
                System.out.println(bat);
                pre.setText(bat+"%");

                if(bat <= 10){
                    batt.setImageResource(R.drawable.ic_battery_alert_24dp);
                    pre.setTextColor(Color.parseColor("#B90009"));
                }else if(10 < bat && bat <= 20){
                    batt.setImageResource(R.drawable.ic_battery_20_24dp);
                    pre.setTextColor(Color.parseColor("#2665B9"));
                }else if(20 < bat && bat <= 30){
                    batt.setImageResource(R.drawable.ic_battery_30_24dp);
                    pre.setTextColor(Color.parseColor("#2665B9"));
                }else if(30 < bat && bat <= 50){
                    batt.setImageResource(R.drawable.ic_battery_50_24dp);
                    pre.setTextColor(Color.parseColor("#2665B9"));
                }else if(50 < bat && bat <= 60){
                    batt.setImageResource(R.drawable.ic_battery_60_24dp);
                    pre.setTextColor(Color.parseColor("#2665B9"));
                }else if(60 < bat && bat <= 80){
                    batt.setImageResource(R.drawable.ic_battery_80_24dp);
                    pre.setTextColor(Color.parseColor("#2665B9"));
                }else if(80 < bat && bat <= 90){
                    batt.setImageResource(R.drawable.ic_battery_90_24dp);
                    pre.setTextColor(Color.parseColor("#2665B9"));
                }else if(90 < bat && bat <= 100){
                    batt.setImageResource(R.drawable.ic_battery_100_24dp);
                    pre.setTextColor(Color.parseColor("#2665B9"));
                }else{
                    batt.setImageResource(R.drawable.ic_battery_unknown_24dp);
                    pre.setTextColor(Color.parseColor("#2665B9"));
                    pre.setText("0%");
                }
                remainingTime(bat);

            }

            public void remainingTime(int bat){
                int re = bat * 8;

                if(re >= 60){
                    rem.setText(re/60 + "hrs " + re % 60 + "mins remaining");
                }else{
                    rem.setText(re + "mins remaining");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Error", "Failed to read value.", error.toException());
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this.getBaseContext(),MainActivity.class);
        startActivity(intent);
    }

}
