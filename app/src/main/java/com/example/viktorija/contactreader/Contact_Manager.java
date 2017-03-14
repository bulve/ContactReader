package com.example.viktorija.contactreader;



import android.app.Activity;

import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;



public class Contact_Manager extends Activity {

    EditText nameTxt, phoneTxt, emailTxt, addressTxt;

    ImageView contactImageView;
    List<Contact> Contacts = new ArrayList<Contact>();
    ListView contactListView;

    Uri contactImageUri = Uri.parse("android.resource://com.example.viktorija.contactreader/drawable/user.png");



    DatabaseHandler dbHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact__manager);


        dbHandler = new DatabaseHandler(getApplicationContext());



        nameTxt = (EditText) findViewById(R.id.editTextName);
        phoneTxt = (EditText) findViewById(R.id.editTextNumber);
        emailTxt = (EditText) findViewById(R.id.editTextEmail);
        addressTxt = (EditText) findViewById(R.id.editTextAddress);

        contactListView = (ListView) findViewById(R.id.listView);
        contactImageView = (ImageView) findViewById(R.id.imageViewContact) ;

        TabHost tabHost = (TabHost) findViewById(R.id.host);

        tabHost.setup();
        TabHost.TabSpec
                tabSpec = tabHost.newTabSpec("contact");
                tabSpec.setContent(R.id.tabContact);
                tabSpec.setIndicator("Contact's", getResources().getDrawable(R.color.editTextColor));

                tabHost.addTab(tabSpec);

                tabSpec = tabHost.newTabSpec("creator");
                tabSpec.setContent(R.id.tabCreator);
                tabSpec.setIndicator("Creator");
                tabHost.addTab(tabSpec);



        contactImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select image for your contact"), 1);
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
                    Toast.makeText(getApplicationContext(), "New contact " + nameTxt.getText().toString() + " has been added to your list", Toast.LENGTH_SHORT).show();
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
                contactImageUri = data.getData();
                contactImageView.setImageURI(data.getData());
            }
        }
    }



    public void populateList(){
        ArrayAdapter<Contact> adapter = new ContactListAdapter();
        contactListView.setAdapter(adapter);
    }


/* dabar nenaudojams
    private void addContact(int id, String name, String number, String email, String address) {

        if(email.isEmpty()){
            email  = "No email";
        }
        if(address.isEmpty()){
            address = "No address";
        }
            //after
        Contact contact = new Contact(id, name, number, email, address);

         //after
        Contacts.add(new Contact(id, name, number, email, address));
    }*/

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

                /*TextView email = (TextView) view.findViewById(R.id.contactEmail);
                email.setText(currentContact.getEmail());

                TextView address = (TextView) view.findViewById(R.id.contactAddress);
                address.setText(currentContact.getAddress());*/

                //Seting up image on the  list view
                //ImageView contactImageView = (ImageView) view.findViewById(R.id.imageViewContactListView);
               // contactImageView.setImageURI(currentContact.getImageUri());


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






    }

