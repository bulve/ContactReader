package com.example.viktorija.contactreader;



import android.app.Activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class Contact_Manager extends Activity {

    EditText nameTxt, phoneTxt, emailTxt, addressTxt;
    Button clearBtn;

    List<Contact> Contacts = new ArrayList<Contact>();
    ListView contactListView;


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


        TabHost tabHost = (TabHost) findViewById(R.id.host);

        tabHost.setup();
        TabHost.TabSpec
                tabSpec = tabHost.newTabSpec("creator");
                tabSpec.setContent(R.id.tabCreator);
                tabSpec.setIndicator("Creator");
                tabHost.addTab(tabSpec);

                tabSpec = tabHost.newTabSpec("contact");
                tabSpec.setContent(R.id.tabContact);
                tabSpec.setIndicator("Contact's");
                tabHost.addTab(tabSpec);


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

                    Contact contact = new Contact(dbHandler.getContactCount(), String.valueOf(nameTxt.getText()), String.valueOf(phoneTxt.getText()),  String.valueOf(emailTxt.getText()),  String.valueOf(addressTxt.getText()));
                   // addContact(0, nameTxt.getText().toString(), phoneTxt.getText().toString(), emailTxt.getText().toString(), addressTxt.getText().toString());

                    dbHandler.addContact(contact);
                    Contacts.add(contact);
                    populateList();
                    Toast.makeText(getApplicationContext(), nameTxt.getText().toString() + "Id = "+ dbHandler.getContactCount() + "contact has been added to your list", Toast.LENGTH_SHORT).show();
                }

            }

        });


        nameTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addBtn.setEnabled(!nameTxt.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
     List<Contact> allContactsAvalable = dbHandler.getAllContacts();
        int countContact = dbHandler.getContactCount();
        for(int i= 0; i < countContact; i++){
            Contacts.add(allContactsAvalable.get(i));

        }
        if(!allContactsAvalable.isEmpty())
            populateList();

    }



    private void populateList(){
        ArrayAdapter<Contact> adapter = new ContactListAdapter();
        contactListView.setAdapter(adapter);
    }


/* dabar nenaudojams
    private void addContact(int id, String name, String number, String email, String address) {

        if(email.isEmpty()){
            email = "No email";
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
                number.setText(currentContact.getNumber());

                TextView email = (TextView) view.findViewById(R.id.contactEmail);
                email.setText(currentContact.getEmail());

                TextView address = (TextView) view.findViewById(R.id.contactAddress);
                address.setText(currentContact.getAddress());


            Button deleteButton = (Button) view.findViewById(R.id.contactDelete);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHandler.deleteContact(dbHandler.getContact(currentContact.getId()));
                        populateList();

                }
            });





                 return view;




            }

        }
    private void deleteContact(){


    }





    }

