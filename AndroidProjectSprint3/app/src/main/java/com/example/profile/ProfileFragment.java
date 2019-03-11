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

public class ProfileFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        
        View v = inflater.inflate(R.layout.activity_profile, container, false);


        Button button = (Button) v.findViewById(R.id.button8);

        button.setOnClickListener(this);

        return v;

    }

    public  ProfileFragment() {}

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), Medical_History2.class);
        startActivity(intent);
    }
}
