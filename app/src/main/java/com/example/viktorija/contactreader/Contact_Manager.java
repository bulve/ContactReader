package com.example.viktorija.contactreader;

import android.provider.ContactsContract;
import android.support.annotation.MainThread;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Contact_Manager extends AppCompatActivity {

    EditText nameTxt, phoneTxt, emailTxt, addressTxt;
    List<Contact> Contacts = new ArrayList<Contact>();
    ListView contactListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact__manager);

        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.setContentView(R.layout.activity_contact__manager);



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

                final Button addBtn = (Button) findViewById(R.id.Cbutton);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nameTxt.getText().toString().isEmpty() && phoneTxt.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Contact not created (missing contact name and number)", Toast.LENGTH_SHORT).show();
                }else {
                    addContact(nameTxt.getText().toString(), phoneTxt.getText().toString(), emailTxt.getText().toString(), addressTxt.getText().toString());
                    populateList();
                    Toast.makeText(getApplicationContext(), nameTxt.getText().toString() + "contact has been added to your list", Toast.LENGTH_SHORT).show();
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
    }

    private void populateList(){
        ArrayAdapter<Contact> adapter = new ContactListAdapter();
        contactListView.setAdapter(adapter);
    }

    private void addContact(String name, String number, String email, String address) {
        if(email.isEmpty()){
            email = "No email";
        }
        if(address.isEmpty()){
            address = "No address";
        }
        Contacts.add(new Contact(name, number, email, address));
    }

    private class ContactListAdapter extends ArrayAdapter<Contact> {
        public ContactListAdapter() {
            super(Contact_Manager.this, R.layout.listview_item, Contacts);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent){
            if(view == null)
                    view = getLayoutInflater().inflate(R.layout.listview_item, parent, false);

                Contact currentContact = Contacts.get(position);

                TextView name = (TextView) view.findViewById(R.id.contactName);
                name.setText(currentContact.getName());

                TextView number = (TextView) view.findViewById(R.id.contactNumber);
                number.setText(currentContact.getNumber());

                TextView email = (TextView) view.findViewById(R.id.contactEmail);
                email.setText(currentContact.getEmail());

                TextView address = (TextView) view.findViewById(R.id.contactAddress);
                address.setText(currentContact.getAddress());

                return view;

            }

        }



    }

