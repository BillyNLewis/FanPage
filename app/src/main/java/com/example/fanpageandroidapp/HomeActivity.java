package com.example.fanpageandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //direct user to different screen/activity
        Button registerBtn = (Button) findViewById(R.id.registerBtn);
        Button loginBtn = (Button) findViewById(R.id.loginBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //"Intent" is used to switch screens
                Intent startIntent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(startIntent);

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //"Intent" is used to switch screens
                Intent startIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(startIntent);

            }
        });
    }
}