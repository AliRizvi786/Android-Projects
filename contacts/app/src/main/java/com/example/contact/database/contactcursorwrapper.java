package com.example.contact.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.contact.Contact;

import java.util.UUID;

public class contactcursorwrapper extends CursorWrapper {
    public contactcursorwrapper(Cursor cursor){
        super(cursor);
    }
    public Contact getcontact() {
        String uuidString = getString(getColumnIndex(Contactdatabaseschema.ContactsTable.Cols.UUID));
        String firstname1 = getString(getColumnIndex(Contactdatabaseschema.ContactsTable.Cols.firstname));
        String lastname1 = getString(getColumnIndex(Contactdatabaseschema.ContactsTable.Cols.lastname));
        String pincode1 = getString(getColumnIndex(Contactdatabaseschema.ContactsTable.Cols.pincode));
        String number1 = getString(getColumnIndex(Contactdatabaseschema.ContactsTable.Cols.number));
        String relation1 = getString(getColumnIndex(Contactdatabaseschema.ContactsTable.Cols.relation));
        Contact contact= new Contact(UUID.fromString(String.valueOf(UUID.fromString(uuidString))));
        contact.setFirstname(firstname1);
        contact.setLastname(lastname1);
        contact.setPincode(pincode1);
        contact.setNumber(number1);
        contact.setRelation(relation1);
        return contact;



    }
}
