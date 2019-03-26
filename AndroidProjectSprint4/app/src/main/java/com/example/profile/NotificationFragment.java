package com.example.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.profile.httpRequestHelpers.fetchData;
import com.example.profile.httpRequestHelpers.httpPostRequest;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment implements View.OnClickListener {

    private static final int REQUEST_LOCATION = 1;
    Button button;
    public static TextView textView;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private Socket mSocket;
    JSONObject sendSOS;

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

        mSocket = GSocket.getInstance();
        fetchData process = new fetchData();
        process.execute();


        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onAcceptClick(int position) {

                Log.d("sos request id",String.valueOf(adapter.requestList.get(position).getSosID()));
                Log.d("Location", String.valueOf(adapter.requestList.get(position).getHeader()));
                try {
                    Log.d("Doctor ID", User.getUser().get("_id").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                sendSOS = new JSONObject();

                try {
                    sendSOS.put("doctorID",User.getUser().get("_id").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    sendSOS.put("sosID",String.valueOf(adapter.requestList.get(position).getSosID()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                 // Ibrahim's work
                try {
                    JSONObject acceptReq = new JSONObject();
                    acceptReq.put("doctorId", User.getUser().getString("username"));
                    acceptReq.put("patientId", adapter.requestList.get(position).getPatientUser());
                    mSocket.emit("acceptSOS",acceptReq);
                }catch (JSONException e) {
                    e.printStackTrace();
                }

                //link to patch the sos collection
                httpPostRequest task = new httpPostRequest(null);
                task.setJSON(sendSOS);
                task.execute("https://quick-health.herokuapp.com/sos/updateSOSDoctor");

                //link to patch the user collection
                httpPostRequest task2 = new httpPostRequest(null);
                task2.setJSON(sendSOS);
                task2.execute("https://quick-health.herokuapp.com/user/updateSOSDoctor");

//                //Ibrahim's work
//                try {
//                    JSONObject acceptReq = new JSONObject();
//                    acceptReq.put("doctorId", User.getUser().getString("username"));
//                    acceptReq.put("patientId", String.valueOf(adapter.requestList.get(position).getSosID()));
//                    mSocket.emit("acceptSOS",acceptReq);
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }


                sosData.setLocationlong(adapter.requestList.get(position).getLongitude());
                sosData.setLocationlat(adapter.requestList.get(position).getLatitude());
                ChangeView();

            }
        });

        textView = (TextView) v.findViewById(R.id.text_request);
        button = (Button) v.findViewById(R.id.button_notification);

        button.setOnClickListener(this);


        return v;

    }

    public void ChangeView(){
        // Create new fragment and transaction
        Fragment newFragment = new MapFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);

// Commit the transaction
        transaction.commit();
    }

    public NotificationFragment() {
    }

    public JSONObject initJSON() throws  JSONException{
        JSONObject x = new JSONObject();
        x.put("doctorID","");
        x.put("sosID","");
        return x;
    }

    @Override
    public void onClick(View view) {

        fetchData process = new fetchData();
        process.execute();

        recyclerView.setAdapter(adapter);
    }
}

