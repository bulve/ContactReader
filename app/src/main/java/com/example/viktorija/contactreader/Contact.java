package com.example.viktorija.contactreader;

import android.app.Activity;

/**
 * Created by Viktorija on 2016-11-10.
 */

public class Contact extends Activity{

    private String name, number, email, address;
    private int id;

    public Contact(){

    }

    public Contact( int id, String name, String number, String email, String address){
        this.id = id;
        this.name = name;
        this.number = number;
        this.email = email;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
    public String getNumber(){
        return number;
    }
    public String getEmail(){
        return email;
    }
    public String getAddress(){
        return address;
    }
}
