package com.akr.amila.opastus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    TextView name;
    EditText pass;
    Button login;
    String id;

    public static Robot robot;
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static final String PREFS_NAME = "MyPrefFile";
    public static DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        name = findViewById(R.id.txt_name);
        pass = findViewById(R.id.txt_pass);
        login = findViewById(R.id.btn_login);

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME,0);

        if(preferences.getString("robo",id) != null) {
            id = preferences.getString("robo", id);
            name.setText("Enter pin for " + id);
            login.setBackground(getDrawable(R.drawable.login_btn));
            login.setTextColor(Color.parseColor("#4A86E8"));
            login.setEnabled(true);
            pass.setEnabled(true);
        }else{
            name.setText("Add a device");
            login.setBackground(getDrawable(R.drawable.login_btn_d));
            login.setTextColor(Color.parseColor("#c6c6c6"));
            login.setEnabled(false);
            pass.setEnabled(false);
        }
        //id = preferences.getString("robo",id);
    }

    public void logIn(final View view) {

        myRef = database.getReference(id+"/1234");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (pass.getText().length() > 0) {
                    if (dataSnapshot.exists()) {
                        robot = dataSnapshot.getValue(Robot.class);
                        if (Long.parseLong(pass.getText().toString()) == robot.getPass()) {
                            Intent intent = new Intent(view.getContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(view.getContext(), "Invalid Credentials", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(view.getContext(), "Problem connecting to the database.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(view.getContext(), "Invalid Credentials", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pass.setText(null);
    }

    public void addDevice(View view) {
        Intent intent = new Intent(view.getContext(),AddDevice.class);
        startActivity(intent);
    }
}
