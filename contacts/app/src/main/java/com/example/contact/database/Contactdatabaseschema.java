package com.example.contact.database;

public class Contactdatabaseschema {
//    public static Object ContactsTable;

    public static final class ContactsTable {
        public static final String NAME = "contacts";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String firstname = "firstname";
            public static final String lastname = "lastname";
            public static final String number = "number";
            public static final String pincode = "pincode";
            public static final String relation = "relation";



        }
    }
}