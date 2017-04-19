package com.example.viktorija.contactreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

public class FrontPageActivity extends AppCompatActivity {


    LinearLayout frontPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_front_page);

        frontPage = (LinearLayout) findViewById(R.id.front_page_button);


        frontPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Contact_Manager.class);
                startActivity(intent);
            }
        });



    }

}
