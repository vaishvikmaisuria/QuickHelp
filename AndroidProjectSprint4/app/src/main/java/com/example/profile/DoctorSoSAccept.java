package com.example.profile;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class DoctorSoSAccept extends AppCompatActivity {

    Button accept,decline;
    TextView docAcceptTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos_doctor_accept);

        accept = findViewById(R.id.Accept);
        decline = findViewById(R.id.Decline);
        docAcceptTitle = findViewById(R.id.DocAcceptTitle);
        if( User.acceptedUser != null){
            docAcceptTitle.setText("Accept sos Signaal from " + User.acceptedUser + " ?");
        }else{
            docAcceptTitle.setText("Error");
        }


        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotomain(getRootView());

            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoDocMenu(getRootView());
            }
        });
    }
    private View getRootView() {
        final ViewGroup contentViewGroup = (ViewGroup) findViewById(android.R.id.content);
        View rootView = null;

        if(contentViewGroup != null)
            rootView = contentViewGroup.getChildAt(0);

        if(rootView == null)
            rootView = getWindow().getDecorView().getRootView();

        return rootView;
    }

    public void gotomain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void gotoDocMenu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
