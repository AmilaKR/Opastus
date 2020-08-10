package com.akr.amila.opastus;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.akr.amila.opastus.LoginActivity.myRef;
import static com.akr.amila.opastus.LoginActivity.robot;

public class MainActivity extends AppCompatActivity {

    Button track;
    Button destination;
    Button schedule;
    Button info;
    Animation fromBottom;
    Animation fromTop;
    ConstraintLayout top;
    ImageView roboFull;
    ImageView mobile;
    TextView name;
    TextView ver;
    //Uri noti;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        track = findViewById(R.id.button);
        destination = findViewById(R.id.button2);
        schedule = findViewById(R.id.button3);
        info = findViewById(R.id.button4);
        fromBottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        top = findViewById(R.id.top_bar);
        fromTop = AnimationUtils.loadAnimation(this,R.anim.fromtop);
        roboFull = findViewById(R.id.robo_full);
        mobile = findViewById(R.id.mobile);
        name = findViewById(R.id.robo_name);
        ver = findViewById(R.id.vers);
        //noti = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        track.setAnimation(fromBottom);
        destination.setAnimation(fromBottom);
        schedule.setAnimation(fromBottom);
        info.setAnimation(fromBottom);
        top.setAnimation(fromTop);
        ver.setAnimation(fromBottom);

        ObjectAnimator blink1 = ObjectAnimator.ofFloat(roboFull,"Alpha",1.0f,0.3f,1.0f);
        ObjectAnimator blink2 = ObjectAnimator.ofFloat(mobile,"Alpha",0.3f,1.0f,0.3f);
        blink1.setDuration(1500);
        blink2.setDuration(1500);
        blink1.setRepeatMode(ValueAnimator.REVERSE);
        blink2.setRepeatMode(ValueAnimator.REVERSE);
        blink1.setRepeatCount(Animation.INFINITE);
        blink2.setRepeatCount(Animation.INFINITE);
        blink1.start();
        blink2.start();

        if(robot.getName() != null){
            name.setText("Connected to " + robot.getName());
        }

        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),TrackActivity.class);
                startActivity(intent);
            }
        });

        destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),DestinationActivity.class);
                startActivity(intent);
            }
        });

        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ScheduleActivity.class);
                startActivity(intent);
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),InfoActivity.class);
                startActivity(intent);
            }
        });

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Error", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        System.out.println(token);
                        Log.d("Token", token);
                        //Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

        roboFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SettingActivity.class);
                startActivity(intent);
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

    /*private void notification(String title, String text){
        long[] pattern = {100,300,300,300};

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);
        builder.setSmallIcon(R.drawable.ico);
        builder.setContentTitle(title);
        builder.setContentText(text);
        builder.setSound(noti);
        builder.setVibrate(pattern);
        builder.setPriority(Notification.PRIORITY_MAX);
        builder.setAutoCancel(true);

        Intent notificationIntent = new Intent(this,TrackActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent content = PendingIntent.getActivity(this,0,notificationIntent,0);
        builder.setContentIntent(content);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0,builder.build());
    }*/
}
