package com.example.viktorija.contactreader;



import android.app.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;




public class InfoContact extends Activity {

    TextView contact_name, contact_number, contact_email,contact_address;
    TextView emailTextViewVisibility, addressTextViewVisibility;
    ImageView contact_image_uri;
    private int contactID;
    DatabaseHandler dbHandler;
    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_contact);

       // MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);






        Bundle bundle = getIntent().getExtras();
        contactID = bundle.getInt("ContactIdFromManager");

        dbHandler = new DatabaseHandler(this);
        contact = new Contact();






        contact = dbHandler.getContact(contactID);

        //contact_image_uri = (ImageView) findViewById(R.id.imageUriOnUpdate);
       // contact_image_uri.setImageURI(contact.getImageUri());

        contact_name = (TextView) findViewById(R.id.ContactNameInfo);
        contact_name.setText(String.valueOf(contact.getName()));

        contact_number = (TextView) findViewById(R.id.ContactNumberInfo);
        contact_number.setText(String.valueOf(contact.getNumber()));

        contact_email = (TextView) findViewById(R.id.ContactEmailInfo);
        emailTextViewVisibility = (TextView) findViewById(R.id.textViewForContactEmail);

        if(!contact.getEmail().isEmpty()) {
            contact_email.setText(String.valueOf(contact.getEmail()));
        }else {
            emailTextViewVisibility.setVisibility(View.GONE);
            contact_email.setVisibility(View.GONE);
        }
        contact_address = (TextView) findViewById(R.id.ContactAddressInfo);
        addressTextViewVisibility = (TextView) findViewById(R.id.textViewForContactAddress);

        if(!contact.getAddress().isEmpty())
            contact_address.setText(String.valueOf(contact.getAddress()));
        else {
            addressTextViewVisibility.setVisibility(View.GONE);
            contact_address.setVisibility(View.GONE);
        }
        contact_image_uri = (ImageView) findViewById(R.id.imageUriOnInfo);
        contact_image_uri.setImageURI(contact.getImageUri());

        contact_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("geo:0,0?q=" + Uri.encode(contact.getAddress()));
                //String uri = "http://maps.google.com/maps?saddr="+ contact.getAddress();
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });


        contact_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = contact_number.getText().toString();
                phoneCall(number);
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


    }

    private void phoneCall(String number){
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null)));
    }
    /*private void openEmail(String email){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { email });
        startActivity(Intent.createChooser(intent, ""));
    }*/



}
