package com.example.profile;

import android.content.Context;
import android.location.LocationManager;
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

public class NotificationFragment extends Fragment implements View.OnClickListener {

    private static final int REQUEST_LOCATION = 1;
    Button button;
    TextView textView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        View v = inflater.inflate(R.layout.activity_notification, container, false);

        textView = (TextView) v.findViewById(R.id.text_request);
        button = (Button) v.findViewById(R.id.button_notification);

        button.setOnClickListener(this);

        return v;

    }

    public  NotificationFragment() {}

    @Override
    public void onClick(View view) {

        //Some url endpoint that you may have
//        String myUrl = "http://myApi.com/get_some_data";
//        //String to place our result in
//        String result;
//        //Instantiate new instance of our class
//        HttpGetRequest getRequest = new HttpGetRequest();
//        //Perform the doInBackground method, passing in our url
//        result = getRequest.execute(myUrl).get();

    }
}
