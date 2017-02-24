package com.example.viktorija.contactreader;

/**
 * Created by Viktorija on 2016-11-10.
 */

public class Contact {

    private String name, number, email, address;

    public Contact(String name, String number, String email, String address){
        this.name = name;
        this.number = number;
        this.email = email;
        this.address = address;
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
