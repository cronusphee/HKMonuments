package com.ericphee.hkmonuments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ericphee.hkmonuments.databases.DatabaseAccess;

/**
 * Created by ERic Phee on 5/15/2016.
 */
public class ActivitySplashScreen extends Activity{

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    /** Called when the activity is first created. */
    Thread splashTread;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        StartAnimations();
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.splash);
        TextView tv = (TextView) findViewById(R.id.tvTitle);
        tv.clearAnimation();
        tv.startAnimation(anim);
        iv.clearAnimation();
        iv.startAnimation(anim);


        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 3500) {
                        sleep(100);
                        waited += 100;
                    }


                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(ActivitySplashScreen.this);
                    databaseAccess.open();
                    if (!databaseAccess.getInitStatus()){

                    }


                    Intent intent = new Intent(ActivitySplashScreen.this, ActivityMain.class);
                    //Intent intent = new Intent(ActivitySplashScreen.this, ActivityListMonuments.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    ActivitySplashScreen.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    ActivitySplashScreen.this.finish();
                }

            }
        };
        splashTread.start();

    }

    private void startGeofence() {

    }




}
