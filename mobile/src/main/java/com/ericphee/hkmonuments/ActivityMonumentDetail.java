package com.ericphee.hkmonuments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ericphee.hkmonuments.databases.DatabaseAccess;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationServices;

public class ActivityMonumentDetail extends AppCompatActivity {
    private MonumentsVo monumentsVo = null;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static Context CONTEXT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CONTEXT = this;

        setContentView(R.layout.activity_monument_detail);
        Intent intent = getIntent();
        int monumentsID = intent.getIntExtra("monumentsID", 0);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        monumentsVo = databaseAccess.getMonumentbyID(monumentsID);
        if (monumentsVo != null) {
            ImageView ivMonumentDetailImg = (ImageView) findViewById(R.id.md_imageView);
            ivMonumentDetailImg.setImageResource(getResources().getIdentifier(monumentsVo.getImage(), "drawable", getPackageName()));

            TextView txtTitle = (TextView) findViewById(R.id.md_txt_title);
            txtTitle.setText(monumentsVo.getName());

            TextView txtAddress = (TextView) findViewById(R.id.md_txt_address);
            txtAddress.setText("Address : " + monumentsVo.getAddress());

            TextView txtDesc = (TextView) findViewById(R.id.md_txt_desc);
            txtDesc.setText(monumentsVo.getDetail());
            txtDesc.setMovementMethod(new ScrollingMovementMethod());


            // Locate the button in activity_main.xml
            ImageButton btnMap = (ImageButton) findViewById(R.id.md_btn_map);

            // Capture button clicks
            btnMap.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {

                    // Showing status
                    if(checkPlayServices()) { // Google Play Services are not available
                        // Start NewActivity.class
                        Intent activityMonumentMapsIntent = new Intent(ActivityMonumentDetail.this, ActivityMonumentMaps.class);
                        //activityMonumentMapsIntent.putExtra("monumentsID", monumentsVoList.get(position).getId());
                        activityMonumentMapsIntent.putExtra("latitude", monumentsVo.getLatitude());
                        activityMonumentMapsIntent.putExtra("longitude", monumentsVo.getLongitude());
                        activityMonumentMapsIntent.putExtra("locaName", monumentsVo.getName());
                        startActivity(activityMonumentMapsIntent);
                    }
                }
            });

        }


    }

    private boolean checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }

            return false;
        }

        return true;
    }
}
