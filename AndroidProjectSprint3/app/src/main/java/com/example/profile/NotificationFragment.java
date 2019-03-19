package com.example.profile;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.profile.httpRequestHelpers.httpGetRequest;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NotificationFragment extends Fragment implements View.OnClickListener {

    private static final int REQUEST_LOCATION = 1;
    Button button;
    TextView textView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<sosRequest> listItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        View v = inflater.inflate(R.layout.activity_notification, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        listItems = new ArrayList<>();

        listItems.add(
                new sosRequest(0, "First Dummy Request",
                        "Dont Do anything")
        );

//        for(int i =0; i<=10; i++){
//            sosRequest listitem =  new sosRequest(i, "heading" + (i+1),
//                        "Dummy text That states the type of injury"
//            );
//            listItems.add(listitem);
//        }

        adapter = new MyAdapter(listItems, getActivity() );

        recyclerView.setAdapter(adapter);

        textView = (TextView) v.findViewById(R.id.text_request);
        button = (Button) v.findViewById(R.id.button_notification);

        button.setOnClickListener(this);

        return v;

    }

    public  NotificationFragment() {}

    @Override
    public void onClick(View view) {

        // Some url endpoint that you may have
        String myUrl = "http://localhost:3000/sos/allSOS";
        //String to place our result in
        String result = "none";
        //Instantiate new instance of our class
        httpGetRequest getRequest = new httpGetRequest();
        //Perform the doInBackground method, passing in our url
        try {
            result = getRequest.execute(myUrl).get();
            textView.setText(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

//        textView.setText("NONN");

    }
}
