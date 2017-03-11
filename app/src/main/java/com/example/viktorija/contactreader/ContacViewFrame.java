package com.example.viktorija.contactreader;

import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class ContacViewFrame extends AppCompatActivity {

    boolean isNextFragment = true;
    Bundle bundle;
    private int ContactID;
    //Button id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        setContentView(R.layout.activity_contact_view_frame);

        ContactID = bundle.getInt("ContactIdFromManager");
        //id = (Button) findViewById(R.id.UpdateInfoButton);
       // id.setText(Integer.toString(ContactID));


       // ContactID = android.provider.AlarmClock.EXTRA_MESSAGE;



        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentContactViewInfo fragment = new FragmentContactViewInfo();
        fragmentTransaction.add(R.id.frame, fragment);
        fragment.setArguments(bundle);
        fragmentTransaction.commit();
        // or - getFragmentManager().beginTransaction().add(R.id.frameLayout, new FirstFragment()).commit();

    }

    public void onButtonPush(View view) {
        Button updateInfo = (Button) findViewById(R.id.UpdateInfoButton);
        Bundle idBundle = new Bundle();

       //idBundle.getInt("ContactIdFromManager");
        idBundle.putInt("ContactIdFromManager", ContactID);
        if (isNextFragment) {
            updateInfo.setText("Update");
            FragmentContactViewInfo fragment1 = new FragmentContactViewInfo();
            fragment1.setArguments(idBundle);
            getFragmentManager().beginTransaction().replace(R.id.frame, fragment1).commit();
        } else {
            updateInfo.setText("Info");
            FragmentContactUpdateInfo fragment2 = new FragmentContactUpdateInfo();
            fragment2.setArguments(idBundle);
            getFragmentManager().beginTransaction().replace(R.id.frame, fragment2).commit();
        }
        isNextFragment = !isNextFragment;
    }
}
