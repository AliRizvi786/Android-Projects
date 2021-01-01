package com.example.contact.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class contacthelper extends SQLiteOpenHelper {
    private static final  int Version=1;
    private static final String dataname="contactbase.db";
    public contacthelper(Context context){
        super(context,dataname,null,Version);

    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + Contactdatabaseschema.ContactsTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                Contactdatabaseschema.ContactsTable.Cols.UUID + ", " +
                Contactdatabaseschema.ContactsTable.Cols.firstname + ", " +
                Contactdatabaseschema.ContactsTable.Cols.lastname + ", " +
                Contactdatabaseschema.ContactsTable.Cols.number +", "+
                Contactdatabaseschema.ContactsTable.Cols.pincode +", "+
                Contactdatabaseschema.ContactsTable.Cols.relation +


                ")"
        );    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldversion,int newversion){

    }
}
