package com.akr.amila.opastus;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;

import static com.akr.amila.opastus.LoginActivity.PREFS_NAME;

public class AddDevice extends Activity {

    EditText name;
    String id;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        name = findViewById(R.id.addDevice_txt);
        preferences = getSharedPreferences(PREFS_NAME,0);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*0.8),(int)(height*0.6));

        if(preferences.getString("robo",id) != null) {
            id = preferences.getString("robo",id);
            name.setText(id);
        }
    }

    public void saveDevice(View view) {
        if(name.getText().length() > 0) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.putString("robo", name.getText().toString());
            editor.commit();

            Intent intent = new Intent(view.getContext(),LoginActivity.class);
            startActivity(intent);
        }
    }
}
