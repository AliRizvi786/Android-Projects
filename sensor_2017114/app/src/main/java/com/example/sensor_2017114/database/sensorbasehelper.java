package com.example.sensor_2017114.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class sensorbasehelper extends SQLiteOpenHelper {
    private static final int Version=1;
    private static final String databasename="sensorbase.db";
    public sensorbasehelper(@Nullable Context context) {
        super(context, databasename, null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+sensorschema.Accelerometertable.NAME+"("+"_id integer primary key autoincrement,"+ sensorschema.Accelerometertable.Cols.timestamp+", "+ sensorschema.Accelerometertable.Cols.X+", "+ sensorschema.Accelerometertable.Cols.Y+", "+ sensorschema.Accelerometertable.Cols.Z+")");
        sqLiteDatabase.execSQL("create table "+sensorschema.GPStable.NAME+"("+"_id integer primary key autoincrement,"+ sensorschema.GPStable.Cols.timestamp+", "+ sensorschema.GPStable.Cols.latitude +", "+ sensorschema.GPStable.Cols.longitude +")");
        sqLiteDatabase.execSQL("create table "+sensorschema.Wifitable.NAME+"("+"_id integer primary key autoincrement,"+ sensorschema.Wifitable.Cols.timestamp +", "+ sensorschema.Wifitable.Cols.Strength +", "+ sensorschema.Wifitable.Cols.Accesspoint+")");
        sqLiteDatabase.execSQL("create table "+sensorschema.Mictable.NAME+"("+"_id integer primary key autoincrement,"+ sensorschema.Mictable.Cols.timestamp +", "+ sensorschema.Mictable.Cols.filename +")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
