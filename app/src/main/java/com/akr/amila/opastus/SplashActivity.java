package com.akr.amila.opastus;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.pusher.pushnotifications.PushNotifications;

public class SplashActivity extends AppCompatActivity {

    ImageView op;
    ImageView braile;
    ImageView body;
    //ImageView wheel1;
    //ImageView wheel2;
    Animation fromBottom;
    Animation fromTop;
    Animation fromLeft;
    Animation toRight;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        PushNotifications.start(getApplicationContext(), "3d7e945d-e5a3-4438-9490-f59f354fa29b");
        PushNotifications.addDeviceInterest("hello");

        op = findViewById(R.id.opastus);
        braile = findViewById(R.id.brail);
        body = findViewById(R.id.oBody);
        //wheel1 = findViewById(R.id.wheel1);
        //wheel2 = findViewById(R.id.wheel2);
        fromBottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        fromTop = AnimationUtils.loadAnimation(this,R.anim.fromtop);
        fromLeft = AnimationUtils.loadAnimation(this,R.anim.fromleft);
        toRight = AnimationUtils.loadAnimation(this,R.anim.toright);

        op.setAnimation(fromBottom);
        braile.setAnimation(fromBottom);
        body.setAnimation(fromLeft);
        /*ObjectAnimator rotate1 = ObjectAnimator.ofFloat(wheel1,"rotation",0f,760f);
        rotate1.setDuration(2000);
        ObjectAnimator rotate2 = ObjectAnimator.ofFloat(wheel2,"rotation",0f,760f);
        rotate2.setDuration(2000);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(rotate1);
        animatorSet.playTogether(rotate2);
        animatorSet.start();*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.super.getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        }, 3000);
    }
}
