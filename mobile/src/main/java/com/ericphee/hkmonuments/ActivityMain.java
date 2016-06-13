package com.ericphee.hkmonuments;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;


public class ActivityMain extends Activity implements AdapterView.OnItemClickListener {

    ListView listView;
//    String[] menu = {"Declared monuments In Hong Kong", "Show nearest monument", "Show All in Map", "Setting"};
    String[] menu = {"Declared monuments In Hong Kong", "Show All in Map","About"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.text_template, menu);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView temp = (TextView) view;
        //Toast.makeText(ActivityMain.this, temp.getText() + " Position: " +position, Toast.LENGTH_SHORT).show();
        if (position == 0) {
            Intent intent = new Intent(ActivityMain.this, ActivityListMonuments.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        } else  if (position ==1) {
            Intent intent = new Intent(ActivityMain.this, ActivityGeoFencesMaps.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }else  if (position ==2) {
            Intent intent = new Intent(ActivityMain.this, ActivityAbout.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }
    }


}
