package com.example.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.BreakIterator;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView name;
    private TextView username;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.activity_profile, container, false);

        setContentView(R.layout.activity_profile);
        name = (TextView) v.findViewById(R.id.nameTextView);
        username = (TextView) v.findViewById(R.id.username);

        updateFields(User.User1);

        Button button = (Button) v.findViewById(R.id.button8);
        Button personalInfo = (Button) v.findViewById(R.id.button6);

        button.setOnClickListener(this);
        personalInfo.setOnClickListener(this);

        return v;

    }

    private void setContentView(int activity_profile) {
    }

    public  ProfileFragment() {}

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button8: {
                Intent intent = new Intent(getActivity(), Medical_History2.class);
                startActivity(intent);
            }
            case R.id.button6: {
                Intent intent = new Intent(getActivity(), PersonalInfo.class);
                startActivity(intent);
            }
        }
    }

    private void updateFields(JSONObject user) {
        try {
            name.setText(User.User1.get("firstName").toString());
            username.setText(User.User1.get("username").toString());
        }catch (JSONException e){
            return;
        }

    }
}
