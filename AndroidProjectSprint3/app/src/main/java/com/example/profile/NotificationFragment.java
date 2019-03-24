package com.example.profile;

import android.content.Context;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.profile.directionhelpers.FetchURL;
import com.example.profile.httpRequestHelpers.fetchData;
import com.example.profile.httpRequestHelpers.httpGetRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NotificationFragment extends Fragment implements View.OnClickListener {

    private static final int REQUEST_LOCATION = 1;
    Button button;
    public static TextView textView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private RequestQueue mQueue;


    public static List<sosRequest> listItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        View v = inflater.inflate(R.layout.activity_notification, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        listItems = new ArrayList<>();
        adapter = new MyAdapter(listItems, getActivity());

        fetchData process = new fetchData();
        process.execute();


        recyclerView.setAdapter(adapter);

        textView = (TextView) v.findViewById(R.id.text_request);
        button = (Button) v.findViewById(R.id.button_notification);

        button.setOnClickListener(this);


        return v;

    }

    public NotificationFragment() {
    }

    @Override
    public void onClick(View view) {

        fetchData process = new fetchData();
        process.execute();

        recyclerView.setAdapter(adapter);
    }
}

