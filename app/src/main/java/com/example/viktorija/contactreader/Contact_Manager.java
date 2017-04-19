package com.example.viktorija.contactreader;




import android.Manifest;
import android.app.Activity;

import android.content.ContentResolver;

import android.content.Intent;


import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;


import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;



public class Contact_Manager extends Activity {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 0;
    private static final int MY_PERMISSIONS_REQUEST_MANAGE_DOCUMENTS = 0;

    EditText nameTxt, phoneTxt, emailTxt, addressTxt;

    ImageView contactImageView;
    List<Contact> Contacts = new ArrayList<Contact>();
    ListView contactListView;
    SearchView searchView;

    String SearchString;


   Uri contactImageUri;
    Resources resources;



    DatabaseHandler dbHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact__manager);


        askForPremission();

        resources = this.getResources();
        Uri contactImageUriDefault = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(resources.getResourcePackageName(R.drawable.useruser))
                .appendPath(resources.getResourceTypeName(R.drawable.useruser))
                .appendPath(resources.getResourceEntryName(R.drawable.useruser))
                .build();
        contactImageUri = contactImageUriDefault;
        SearchString = null;


        dbHandler = new DatabaseHandler(getApplicationContext());



        nameTxt = (EditText) findViewById(R.id.editTextName);
        phoneTxt = (EditText) findViewById(R.id.editTextNumber);
        emailTxt = (EditText) findViewById(R.id.editTextEmail);
        addressTxt = (EditText) findViewById(R.id.editTextAddress);

        contactListView = (ListView) findViewById(R.id.listView);
        contactImageView = (ImageView) findViewById(R.id.imageViewContact) ;

        searchView = (SearchView) findViewById(R.id.searchViewManager);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchString = searchView.getQuery().toString();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        TabHost tabHost = (TabHost) findViewById(R.id.host);

        tabHost.setup();
        TabHost.TabSpec
                tabSpec = tabHost.newTabSpec("contact");
                tabSpec.setContent(R.id.tabContact);
                tabSpec.setIndicator("Contact's");
                tabHost.addTab(tabSpec);

                tabSpec = tabHost.newTabSpec("creator");
                tabSpec.setContent(R.id.tabCreator);
                tabSpec.setIndicator("Creator");
                tabHost.addTab(tabSpec);



        contactImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select image for your contact"), 1);
                }else{
                    Toast.makeText(getApplicationContext(), "You can not select image because of permission", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }

            }
        });

        final Button clearBtn = (Button) findViewById(R.id.ClearButton);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameTxt.setText("");
                phoneTxt.setText("");
                emailTxt.setText("");
                addressTxt.setText("");

            }
        });
                final Button addBtn = (Button) findViewById(R.id.Cbutton);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nameTxt.getText().toString().isEmpty() && phoneTxt.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Contact not created (missing contact name and/or number)", Toast.LENGTH_SHORT).show();
                }else {
                     Contact contact = new Contact(dbHandler.getContactCount(), String.valueOf(nameTxt.getText()), Integer.valueOf(String.valueOf(phoneTxt.getText())),  String.valueOf(emailTxt.getText()),  String.valueOf(addressTxt.getText()), contactImageUri);



                    dbHandler.addContact(contact);
                    Contacts.add(contact);
                    populateList();
                    nameTxt.setText(""); phoneTxt.setText(""); emailTxt.setText(""); addressTxt.setText("");




                           // = Uri.parse("android.resource://com.example.viktorija.contactreader/drawable/user.png");
                   // Intent intent = new Intent(getApplicationContext(), Contact_Manager.class);
                    Toast.makeText(getApplicationContext(), "New contact " + nameTxt.getText().toString() + " has been added to your list", Toast.LENGTH_SHORT).show();
                   // startActivity(intent);
                }

            }

        });

       List<Contact> allContactsAvailable = dbHandler.getAllContacts();
        int countContact = dbHandler.getContactCount();
        for(int i= 0; i < countContact; i++){


            Contacts.add(allContactsAvailable.get(i));
        }
        if(!allContactsAvailable.isEmpty())
            populateList();

    }

    public void onActivityResult(int requestCode, int respondCode, Intent data){
        if(respondCode == RESULT_OK){
            if(requestCode == 1){

                contactImageUri =  data.getData();
                contactImageView.setImageURI(data.getData());
            }
        }
    }



    public void populateList(){
        ArrayAdapter<Contact> adapter = new ContactListAdapter();
        contactListView.setAdapter(adapter);
    }



    private class ContactListAdapter extends ArrayAdapter<Contact> {
        public ContactListAdapter() {
            super(Contact_Manager.this, R.layout.listview_item, Contacts);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent){
            if(view == null)
                view = getLayoutInflater().inflate(R.layout.listview_item, parent, false);


            final Contact currentContact = Contacts.get(position);

            TextView name = (TextView) view.findViewById(R.id.contactName);
            name.setText(currentContact.getName());

            TextView number = (TextView) view.findViewById(R.id.contactNumber);
            number.setText(String.valueOf(currentContact.getNumber()));



            ImageView contactImageView = (ImageView) view.findViewById(R.id.imageViewContactListView);
            contactImageView.setImageURI(currentContact.getImageUri());


            RelativeLayout viewContact = (RelativeLayout) view.findViewById(R.id.listViewRealitiveLayout);

            viewContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),InfoContact.class);
                    int ContactId = Integer.valueOf(currentContact.getId());

                    intent.putExtra("ContactIdFromManager", ContactId);
                    startActivity(intent);
                }
            });
            viewContact.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),UpdateContact.class);
                    int ContactId = Integer.valueOf(currentContact.getId());

                    intent.putExtra("ContactIdFromManager", ContactId);
                    startActivity(intent);

                    return false;
                }
            });
            return view;
        }

    }
        private void askForPremission() {


            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {



                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);


                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.

                }

            }



        }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_MANAGE_DOCUMENTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }






    }

