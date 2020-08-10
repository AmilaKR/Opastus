package com.akr.amila.opastus;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.akr.amila.opastus.LoginActivity.myRef;
import static com.akr.amila.opastus.LoginActivity.robot;

public class SettingActivity extends Activity {

    EditText name;
    EditText pass;
    EditText nPass;
    EditText cPass;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*0.8),(int)(height*0.7));

        name = findViewById(R.id.editName);
        pass = findViewById(R.id.editPass);
        nPass = findViewById(R.id.editNpass);
        cPass = findViewById(R.id.editCpass);
        save = findViewById(R.id.btnSave);

        name.setText(robot.getName());
    }

    public void saveSettings(View view) {
        if(name.getText().toString().equals(robot.getName()) && pass.getText().length() <= 0 && nPass.getText().length() <= 0) {
            this.finish();
            Toast.makeText(SettingActivity.this, "No changes were made.", Toast.LENGTH_SHORT).show();
        }else {
            myRef.child("name").setValue(name.getText().toString());
            if(pass.getText().length() > 0) {
                if (Long.parseLong(pass.getText().toString()) == robot.getPass()) {
                    if (nPass.getText().toString().equals(cPass.getText().toString())) {
                        myRef.child("pass").setValue(Long.parseLong(nPass.getText().toString()));
                        Intent intent = new Intent(view.getContext(), LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SettingActivity.this, "Passwords does not match.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SettingActivity.this, "Invalid passwords.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            Toast.makeText(SettingActivity.this, "Saved successfully.", Toast.LENGTH_SHORT).show();
        }
        //System.exit(0);
    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

        Intent intent = new Intent(super.getBaseContext(),LoginActivity.class);
        startActivity(intent);
    }*/

}
