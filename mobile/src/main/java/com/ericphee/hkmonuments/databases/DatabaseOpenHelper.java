package com.ericphee.hkmonuments.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by ERic Phee on 5/19/2016.
 */
public class DatabaseOpenHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "hk_monuments.sqlite";
    private static final int DATABASE_VERSION = 1;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



}



