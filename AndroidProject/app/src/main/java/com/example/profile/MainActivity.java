package com.example.profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;



public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG =
            MainActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
    }


    public void gotologin(View view) {
        Log.d(LOG_TAG, "Login button clicked!");

        Intent intent = new Intent(this, Login.class);
        startActivity(intent);

    }



    public void gotorequest(View view) {
        Log.d(LOG_TAG, "Request button clicked!");

        Intent intent = new Intent(this, SOS_Signal.class);
        startActivity(intent);
    }

    public void gotoprofile(View view) {
        Log.d(LOG_TAG, "Profile button clicked!");

        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    public void gotoregister(View view) {
        Log.d(LOG_TAG, "Register button clicked!");

        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void gotomedicalhistory(View view) {
        Log.d(LOG_TAG, "Medical History button clicked!");

        Intent intent = new Intent(this, Medical_History.class);
        startActivity(intent);
    }

    public void gotomedicalhistory2(View view) {
        Log.d(LOG_TAG, "Medical History button clicked!");

        Intent intent = new Intent(this, Medical_History2.class);
        startActivity(intent);
    }
}
