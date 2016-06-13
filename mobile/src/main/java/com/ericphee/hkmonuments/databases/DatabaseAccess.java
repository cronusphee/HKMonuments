package com.ericphee.hkmonuments.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ericphee.hkmonuments.MonumentsVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ERic Phee on 5/19/2016.
 */
public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;


    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {

        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {

        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all Monuments from the database.
     *
     * @return a List of quotes
     */
    public  List<MonumentsVo> getMonuments() {
        List<MonumentsVo> monumentsVoList = new ArrayList<MonumentsVo>();

        String selectQuery = "SELECT ID, NAME, REGIONS, ADDRESS, LATITUDE, LONGITUDE, DETAIL, IMAGE_ID  FROM Monuments ORDER BY REGIONS, NAME";


        Cursor cursor = database.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MonumentsVo monumentsVo = new MonumentsVo();
            monumentsVo.setId(cursor.getInt(0));
            monumentsVo.setName(cursor.getString(1));
            monumentsVo.setRegions(cursor.getString(2));
            monumentsVo.setAddress(cursor.getString(3));
            monumentsVo.setLatitude(cursor.getDouble(4));
            monumentsVo.setLongitude(cursor.getDouble(5));
            monumentsVo.setDetail(cursor.getString(6));
            monumentsVo.setImage(cursor.getString(7));

            monumentsVoList.add(monumentsVo);
            cursor.moveToNext();
        }
        cursor.close();
        return monumentsVoList;
    }

    /**
     * Read Monuments from the database by ID.
     *
     * @return a MonumentsVo
     */
    public  MonumentsVo getMonumentbyID(int monumentID) {
        MonumentsVo monumentsVo = null;
        String selectQuery = "SELECT ID, NAME, REGIONS, ADDRESS, LATITUDE, LONGITUDE, DETAIL, IMAGE_ID  FROM Monuments WHERE ID = " +monumentID;
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor != null){
            cursor.moveToFirst();
            monumentsVo = new MonumentsVo();
            monumentsVo.setId(cursor.getInt(0));
            monumentsVo.setName(cursor.getString(1));
            monumentsVo.setRegions(cursor.getString(2));
            monumentsVo.setAddress(cursor.getString(3));
            monumentsVo.setLatitude(cursor.getDouble(4));
            monumentsVo.setLongitude(cursor.getDouble(5));
            monumentsVo.setDetail(cursor.getString(6));
            monumentsVo.setImage(cursor.getString(7));
        }

        cursor.close();
        return monumentsVo;
    }


    /**
     * Read Monuments from the database by ID.
     *
     * @return a MonumentsVo
     */
    public Integer getMonumentIDbyTitle(String title) {
        int result = 0;
        String selectQuery = "SELECT ID FROM Monuments WHERE NAME = '" +title +"'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor != null){
            cursor.moveToFirst();
            result= cursor.getInt(0);
        }
        cursor.close();
        return result;
    }

    /**
     * Read initiation status from the database by ID.
     *
     * @return int
     */
    public Boolean getInitStatus() {
        Boolean result = false;
        String selectQuery = "SELECT INITIATION_STATUS FROM InitGeofence";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor != null){
            cursor.moveToFirst();
            if(cursor.getInt(0) > 0){
                result = true ;
            }
        }
        cursor.close();
        return result;
    }
    public void updateInitStatus() {
        String strSQL = "UPDATE InitGeofence SET INITIATION_STATUS = 1";
        database.execSQL(strSQL);
    }


}
