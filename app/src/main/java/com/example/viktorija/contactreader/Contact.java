package com.example.viktorija.contactreader;

import android.app.Activity;
import android.net.Uri;



/**
 * Created by Viktorija on 2016-11-10.
 */

public class Contact extends Activity{

    private String name, email, address;
    private Uri image;
    private int number;
    private int id;

    public Contact(){

    }

    public Contact( int id, String name, int number, String email, String address, Uri image){
        this.id = id;
        this.name = name;
        this.number = number;
        this.email = email;
        this.address = address;
        this.image = image;
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

    public void setNumber(int number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
    public int getNumber(){
        return number;
    }
    public String getEmail(){
        return email;
    }
    public String getAddress(){
        return address;
    }

    public Uri getImageUri(){
        return image;
    }
    public void setImageUri(Uri image){
        this.image = image;
    }
}
