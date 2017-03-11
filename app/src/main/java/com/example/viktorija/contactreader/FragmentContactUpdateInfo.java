package com.example.viktorija.contactreader;

import android.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class FragmentContactUpdateInfo extends Fragment {
    TextView contact_name, contact_number, contact_email,contact_address;
    private int contactID;
    DatabaseHandler dbHandler;
    Contact contact;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dbHandler = new DatabaseHandler(getActivity());
        contact = new Contact();

        contactID = getArguments().getInt("ContactIdFromManager");


        View view = inflater.inflate(R.layout.fragment_contact_update_info, container, false);

        contact = dbHandler.getContact(contactID);
        contact_name = (TextView) view.findViewById(R.id.ContactNameUpdate);
        contact_name.setText(String.valueOf(contact.getName()));
        contact_number = (TextView) view.findViewById(R.id.ContactNumberUpdate);

        contact_number.setText(String.valueOf(Integer.valueOf(contact.getNumber())));

        contact_email = (TextView) view.findViewById(R.id.ContactEmailUpdate);
        contact_email.setText(String.valueOf(contact.getEmail()));
        contact_address = (TextView) view.findViewById(R.id.ContactAddressUpdate);
        contact_address.setText(String.valueOf(contact.getAddress()));

        final Button updateButton = (Button) view.findViewById(R.id.UpdateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contact.setName(String.valueOf(contact_name));
                contact.setNumber(Integer.valueOf(String.valueOf(contact_number)));
                contact.setEmail(String.valueOf(contact_email));
                contact.setAddress(String.valueOf(contact_address));
                dbHandler.updateContact(contact);
                //Intent intent = new Intent(getActivity(),Contact_Manager.class);
                //startActivity(intent);

            }
        });




        return view;
    }








}
