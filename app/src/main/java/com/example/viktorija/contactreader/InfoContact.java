package com.example.viktorija.contactreader;



import android.app.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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




        // contact_name = (TextView) view.findViewById(R.id.ContactNameInfo);
        //contact_name.setText(Integer.toString(contactID));

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








        contact_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = contact_number.getText().toString();
                phoneCall(number);
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
