package com.example.profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class additional_Details extends AppCompatActivity {

    private Button updatebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional__details);


        updatebtn = (Button) findViewById(R.id.button_update);

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gohelpseverity(v);
            }
        });
    }

    public void gohelpseverity(View view) {

        Intent intent = new Intent(this, help_severity.class);
        startActivity(intent);
    }
}
