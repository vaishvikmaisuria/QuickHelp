package com.example.profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class Profile extends AppCompatActivity {
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = (TextView) findViewById(R.id.nameTextView);

        updateFields(User.User1);



    }

    private void updateFields(JSONObject user) {
        try {
            name.setText(User.User1.get("firstName").toString());
        }catch (JSONException e){
            return;
        }

    }

}
