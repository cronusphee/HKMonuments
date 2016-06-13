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

import com.ericphee.hkmonuments.Geofence.GeofenceStore;
import com.ericphee.hkmonuments.databases.DatabaseAccess;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ERic Phee on 5/15/2016.
 */
public class ActivitySplashScreen extends Activity{

    /**
     * Geofences Array
     */
    ArrayList<Geofence> mGeofences;

    /**
     * Geofence Coordinates
     */
    ArrayList<LatLng> mGeofenceCoordinates;

    private GeofenceStore mGeofenceStore;

    private List<MonumentsVo> monumentsVoList  = null;

    private static final Integer MAP_RADIUS = 100;



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
                        mGeofences = new ArrayList<Geofence>();
                        mGeofenceCoordinates = new ArrayList<LatLng>();
                        databaseAccess.open();
                        monumentsVoList  = databaseAccess.getMonuments();
                        databaseAccess.close();
                        for (MonumentsVo vo : monumentsVoList) {
                            mGeofenceCoordinates.add(new LatLng(vo.getLatitude(), vo.getLongitude()));
                            mGeofences.add(new Geofence.Builder()
                                    .setRequestId(vo.getName())
                                    .setCircularRegion(vo.getLatitude(), vo.getLongitude(), MAP_RADIUS)
                                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                                    .setLoiteringDelay(30000)
                                    .setTransitionTypes(
                                            Geofence.GEOFENCE_TRANSITION_ENTER
                                                    | Geofence.GEOFENCE_TRANSITION_DWELL
                                                    | Geofence.GEOFENCE_TRANSITION_EXIT).build());
                        }

                        mGeofenceStore = new GeofenceStore(ActivitySplashScreen.this, mGeofences);
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


    @Override
    protected void onStart() {
        super.onStart();
    }



}
