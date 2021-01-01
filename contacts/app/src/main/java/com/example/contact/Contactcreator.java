package com.example.contact;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.contact.database.Contactdatabaseschema;
import com.example.contact.database.contactcursorwrapper;
import com.example.contact.database.contacthelper;

import java.util.ArrayList;
import java.util.UUID;

public class Contactcreator {
    public static Contactcreator contacts;








    private Context context;
    private SQLiteDatabase database;
    public static Contactcreator getContact(Context context){
        if(contacts==null){
            contacts=new Contactcreator(context);
        }
        return contacts;

    }
    public Contactcreator(Context context){
        context=context.getApplicationContext();
        database=new contacthelper(context).getWritableDatabase();
        for(int i=0;i<50;i++){
            Contact c=new Contact();
            c.setFirstname("A"+i);
            c.setLastname("B"+i);
            c.setNumber(c.number+i);
            c.setPincode("91");
            if(i%2==0){
            c.setRelation("Home");}
            else{
                c.setRelation("Office");
            }
addcontact(c);        }
    }
    public Contact getcontactusingid(UUID cid){
//        for(int i=0;i<list.size();i++){
//            if(list.get(i).id.equals(cid)){
//                return list.get(i);
//            }
//        }
        contactcursorwrapper cursor=contactquery(Contactdatabaseschema.ContactsTable.Cols.UUID+"=?",new String[]{cid.toString()});
        try {
            if(cursor.getCount()==0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getcontact();
        }
        finally {
            cursor.close();
        }


    }
    private static ContentValues getContentValues(Contact con){
        ContentValues values=new ContentValues();
        values.put(Contactdatabaseschema.ContactsTable.Cols.firstname,con.getFirstname());
        values.put(Contactdatabaseschema.ContactsTable.Cols.lastname,con.getLastname());
        values.put(Contactdatabaseschema.ContactsTable.Cols.pincode,con.getPincode());
        values.put(Contactdatabaseschema.ContactsTable.Cols.number,con.getNumber());
        values.put(Contactdatabaseschema.ContactsTable.Cols.relation,con.getRelation());
        values.put(Contactdatabaseschema.ContactsTable.Cols.UUID,con.id.toString());

        return values;

    }
    public void addcontact(Contact contact){
        ContentValues values=getContentValues(contact);
        database.insert(Contactdatabaseschema.ContactsTable.NAME,null,values);
    }
    public void updatecontact(Contact contact){
        String ustring=contact.id.toString();
        ContentValues values=getContentValues(contact);
        database.update(Contactdatabaseschema.ContactsTable.NAME,values, Contactdatabaseschema.ContactsTable.Cols.UUID+"=?",new
                String[] {ustring});
    }

    private contactcursorwrapper contactquery(String wherclause,String[] whereargs){
        Cursor cursor=database.query(Contactdatabaseschema.ContactsTable.NAME,null,wherclause,whereargs,null,null,null);
        return new contactcursorwrapper(cursor);
    }
    public ArrayList<Contact> getcontactslist(){
        ArrayList<Contact> conlist=new ArrayList<Contact>();
        contactcursorwrapper cursor=contactquery(null,null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                conlist.add(cursor.getcontact());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return conlist;

    }
}
