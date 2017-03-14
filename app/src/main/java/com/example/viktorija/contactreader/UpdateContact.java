package com.example.viktorija.contactreader;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class UpdateContact extends Activity {
    TextView contact_name, contact_number, contact_email,contact_address;
    ImageView contact_image_uri;
    private int contactID;

    DatabaseHandler dbHandler;
    Contact contact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);

        Bundle bundle = getIntent().getExtras();
        contactID = bundle.getInt("ContactIdFromManager");

        dbHandler = new DatabaseHandler(this);
        contact = new Contact();


        contact = dbHandler.getContact(contactID);

        //Set image on update
        //contact_image_uri = (ImageView) findViewById(R.id.imageUriOnInfo);
        //contact_image_uri.setImageURI(contact.getImageUri());

        contact_name = (TextView) findViewById(R.id.ContactNameUpdate);
        contact_name.setText(String.valueOf(contact.getName()));
        contact_number = (TextView) findViewById(R.id.ContactNumberUpdate);
        contact_number.setText(String.valueOf(Integer.valueOf(contact.getNumber())));
        contact_email = (TextView) findViewById(R.id.ContactEmailUpdate);
        contact_email.setText(String.valueOf(contact.getEmail()));
        contact_address = (TextView) findViewById(R.id.ContactAddressUpdate);
        contact_address.setText(String.valueOf(contact.getAddress()));

        final Button updateButton = (Button) findViewById(R.id.UpdateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contact.setName(contact_name.getText().toString());
                contact.setNumber(Integer.valueOf(contact_number.getText().toString()));
                contact.setEmail(contact_email.getText().toString());
                contact.setAddress(contact_address.getText().toString());

                //To update image setting a new image or the same
              //  contact.setImageUri(Uri.parse(contact_image_uri.toString()));  //I down know for sure ;/ or Uri.parse(String.valueOf(contact_image_uri));

                dbHandler.updateContact(contact);

                Intent intent = new Intent(getApplicationContext(), Contact_Manager.class);
                startActivity(intent);

            }
        });
        Button backButton = (Button) findViewById(R.id.BackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Contact_Manager.class);
                startActivity(intent);
            }
        });

            Button deleteButton = (Button) findViewById(R.id.DeleteContact);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHandler.deleteContact(dbHandler.getContact(contact.getId()));

                Intent intent = new Intent(getApplicationContext(),Contact_Manager.class);
                startActivity(intent);


            }
        });



    }
}
