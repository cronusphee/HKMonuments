package com.ericphee.hkmonuments;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.widget.AdapterView.OnItemClickListener;

import com.ericphee.hkmonuments.databases.DatabaseAccess;

public class ActivityListMonuments extends AppCompatActivity implements
        OnItemClickListener {

    ListView listView;
    List<RowItem> rowItems;
    List<MonumentsVo> monumentsVoList = new ArrayList<MonumentsVo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_monuments);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        monumentsVoList = databaseAccess.getMonuments();
        databaseAccess.close();
        rowItems = new ArrayList<RowItem>();
        Collections.sort(monumentsVoList, new Comparator<MonumentsVo>() {
            @Override
            public int compare(MonumentsVo o1, MonumentsVo o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        for (MonumentsVo vo : monumentsVoList) {
            int resID = getResources().getIdentifier(vo.getImage(), "drawable", getPackageName());
            RowItem item = new RowItem(vo.getId(), resID, vo.getName());
            rowItems.add(item);
        }


        listView = (ListView) findViewById(R.id.list);
        CustomListViewAdapter cadapter = new CustomListViewAdapter(this,
                R.layout.list_item, rowItems);
        listView.setAdapter(cadapter);
        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
/*
        Toast toast = Toast.makeText(getApplicationContext(),
                "txtViewMonument.getId() " + monumentsVoList.get(position).getId() + ": " + rowItems.get(position),
                Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();


*/
        Intent intent = new Intent(ActivityListMonuments.this, ActivityMonumentDetail.class);
        intent.putExtra("monumentsID", monumentsVoList.get(position).getId());
        startActivity(intent);




    }
}
