package com.example.contact;

import java.util.UUID;

public class Contact {
    String firstname;
    String lastname;
    String number;
    String pincode;
    String relation;
    UUID id;
    public Contact(){
        this(UUID.randomUUID());
        firstname="";
        lastname="";
        number="";
        pincode="";
        relation="";
    }
    public Contact(UUID id){
        this.id=id;
        firstname="";
        lastname="";
        number="";
        pincode="";
        relation="";
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getNumber() {
        return number;
    }

    public String getPincode() {
        return pincode;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getRelation() {
        return relation;
    }
}
