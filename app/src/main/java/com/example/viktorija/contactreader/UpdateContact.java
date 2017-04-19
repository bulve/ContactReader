package com.example.viktorija.contactreader;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateContact extends Activity {
    TextView contact_name, contact_number, contact_email,contact_address;
    ImageView contact_image_uri;
    private int contactID;

    Uri contactImageUri;

    DatabaseHandler dbHandler;
    Contact contact;
    Resources resources;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);

        Bundle bundle = getIntent().getExtras();
        contactID = bundle.getInt("ContactIdFromManager");

        dbHandler = new DatabaseHandler(this);
        contact = new Contact();

        resources = this.getResources();
         final Uri contactImageUriOnUpdate = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(resources.getResourcePackageName(R.drawable.useruser))
                .appendPath(resources.getResourceTypeName(R.drawable.useruser))
                .appendPath(resources.getResourceEntryName(R.drawable.useruser))
                .build();



        contact = dbHandler.getContact(contactID);

        //Set image on update
        contact_image_uri = (ImageView) findViewById(R.id.imageUriOnInfo);
//        contact_image_uri.setImageURI(contact.getImageUri());


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


               // To update image setting a new image or the same
              contact.setImageUri(contactImageUriOnUpdate);  //I down know for sure ;/ or Uri.parse(String.valueOf(contact_image_uri));

                dbHandler.updateContact(contact);

                Toast.makeText(getApplicationContext(), "Contact has been updated ", Toast.LENGTH_SHORT).show();

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

/*        contact_image_uri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select image for your contact"), 1);
            }
        });



    }

    public void onActivityResult(int requestCode, int respondCode, Intent data){
        if(respondCode == RESULT_OK){
            if(requestCode == 1){
                contactImageUri = data.getData();
                contact_image_uri.setImageURI(data.getData());
            }
        }*/
    }
}
