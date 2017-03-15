package com.example.viktorija.contactreader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Viktorija on 2017-03-07
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "ContactWithImage",
            TABLE_CONTACTS = "ListContact",
            COLUMN_ID = "id",
            COLUMN_name = "name",
            COLUMN_number = "number",
            COLUMN_email = "email",
            COLUMN_address = "address",
            COLUMN_imageUri = "imageUri";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
        Bandyta bet nesuveike ;/

        String CREATE_CONTACTS_TABLE = "CREATE TABLE ID NOT EXISTS" +
                TABLE_CONTACTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_name
                + " TEXT," + COLUMN_number + " INTEGER" + COLUMN_email + " TEXT" + COLUMN_address + " TEXT" +")";
        db.execSQL(CREATE_CONTACTS_TABLE);

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "(" +
                COLUMN_ID + "INTEGER PRIMARY KEY," +
                COLUMN_name + " TEXT, " +
                COLUMN_number + " INTEGER, " +
                COLUMN_email + " TEXT, " +
                COLUMN_address + " TEXT " +
                ")");*/

        //sitas veikia

        /* senoji versija be image
             DATABASE_NAME = "AllContact",
             TABLE_CONTACTS = "ListContact",
                db.execSQL("create table " + TABLE_CONTACTS + "("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "number integer,"
                + "email text,"
                + "address text" + ");"); */

        db.execSQL("create table " + TABLE_CONTACTS + "("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "number integer,"
                + "email text,"
                + "address text,"
                + "imageUri text" + ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        onCreate(db);
    }
    public void addContact(Contact contact) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, contact.getId());
        values.put(COLUMN_name, contact.getName());
        values.put(COLUMN_number, contact.getNumber());
        values.put(COLUMN_email, contact.getEmail());
        values.put(COLUMN_address, contact.getAddress());
        //added for image
        values.put(COLUMN_imageUri, String.valueOf(contact.getImageUri()));

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_CONTACTS, null, values);

        db.close();
    }

    public Contact getContact(int id){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{COLUMN_ID, COLUMN_name, COLUMN_number, COLUMN_email, COLUMN_address, COLUMN_imageUri}, COLUMN_ID + "=?", new String[] {String.valueOf(id)}, null, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();

            Contact contact = new Contact(Integer.parseInt(
                    cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    Uri.parse(cursor.getString(5)));
        db.close();
        cursor.close();
            return contact;

    }
    public void deleteContact(Contact contact){
        SQLiteDatabase db = getReadableDatabase();
        db.delete(TABLE_CONTACTS, COLUMN_ID + "=?", new String[]{String.valueOf(contact.getId())});
        db.close();
    }
    public int getContactCount(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "  + TABLE_CONTACTS, null);

        int count = cursor.getCount();
        db.close();
        cursor.close();
        if(count > 0)
        return count;
        else
            return 0;
    }

    public int updateContact(Contact contact){
        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();


        values.put(COLUMN_name, contact.getName());
        values.put(COLUMN_number, contact.getNumber());
        values.put(COLUMN_email, contact.getEmail());
        values.put(COLUMN_address, contact.getAddress());
        //add image Uri ?

        return db.update(TABLE_CONTACTS, values, COLUMN_ID + "=?", new String[] {String.valueOf(contact.getId()) });

    }

    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<Contact>();
// Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        Uri.parse(cursor.getString(5)));
                contacts.add(contact);
            } while (cursor.moveToNext());
        }

// return contact list
        return contacts;
    }


}
