package com.example.profile;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
//import android.view.View;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.profile.httpRequestHelpers.httpPostRequest;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class SOSFragment extends Fragment implements View.OnClickListener {

    private static final int REQUEST_LOCATION = 1;
    Button button;
    TextView textView;
    LocationManager locationManager;
    String lattitude, longitude;
    private Socket lSocket;
    private ProgressBar spinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        View v = inflater.inflate(R.layout.activity_sos__signal, container, false);

        textView = (TextView) v.findViewById(R.id.text_location);
        button = (Button) v.findViewById(R.id.button_location);
        lSocket = GSocket.getInstance();
        spinner = (ProgressBar) v.findViewById(R.id.spin);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            try {
                getLocation();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        button.setOnClickListener(this);

        return v;
    }

    public  SOSFragment() {}

    public void onClick(View view) {


        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
                spinner.setVisibility(View.VISIBLE);
            }

            public void onFinish() {
                spinner.setVisibility(View.INVISIBLE);
            }
        }.start();

        //Some url endpoint that you may have
        String myUrl = "https://quick-health.herokuapp.com";
        //String myUrl = "http://localhost:3000";

        //String to place our result in
        String result = null;

        //Create the Json Object
        //JSONObject sosLocation = new JSONObject();
        Map<String, String> postData = new HashMap<>();
        String name = null;
        try {
            name = User.User1.get("username").toString();
        } catch (JSONException e) {
            spinner.setVisibility(View.INVISIBLE);
            e.printStackTrace();
        }
        postData.put("uid", name);
        postData.put("longitude", longitude);
        postData.put("latitude", lattitude);

        //Instantiate new instance of our class
        httpPostRequest task = new httpPostRequest(postData);

        //Perform the doInBackground method, passing in our url

        try {
            result = task.execute(myUrl + "/sos/newSOS").get();
        } catch (InterruptedException e) {
            spinner.setVisibility(View.INVISIBLE);
            e.printStackTrace();
        } catch (ExecutionException e) {
            spinner.setVisibility(View.INVISIBLE);
            e.printStackTrace();
        }

        JSONObject JO = null;
        try {
            JO = new JSONObject(result);
        } catch (JSONException e) {
            spinner.setVisibility(View.INVISIBLE);
            e.printStackTrace();
        }

        // this adds the json object to sosDAta
        sosData.setUsersos(JO);


        // this adds the string result
        sosData.setUser(result);

        Intent intent2 = new Intent(getActivity().getApplicationContext(),SOS_Service.class);
        ContextCompat.startForegroundService(getActivity(), intent2);
        Intent intent = new Intent(getActivity(), additional_Details.class);
        startActivity(intent);

    }

    private void toAdditionalDetails() {
        Intent intent = new Intent(getActivity(), additional_Details.class);
        startActivity(intent);
        spinner.setVisibility(View.INVISIBLE);
    }



    private void getLocation() throws JSONException {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                try{
                    JSONObject sosObj = new JSONObject();
                    sosObj.put("username", User.User1.getString("username"));
                    sosObj.put("firstName", User.User1.get("firstName").toString());
                    sosObj.put("latitude", lattitude);
                    sosObj.put("longitude",longitude);

                    lSocket.emit("SOS",sosObj);
                }catch (Exception e){}
                Log.d("lattitude", lattitude);
                Log.d("lattitude", longitude);
                spinner.setVisibility(View.INVISIBLE);

                textView.setText("Your current location is" + "\n" + "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude);

            } else if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                try{
                    JSONObject sosObj = new JSONObject();
                    sosObj.put("username", User.User1.getString("username"));
                    sosObj.put("firstName", User.User1.get("firstName").toString());
                    sosObj.put("latitude", lattitude);
                    sosObj.put("longitude",longitude);

                    lSocket.emit("SOS",sosObj);
                }catch (Exception e){}
                Log.d("lattitude", lattitude);
                Log.d("lattitude", longitude);
                textView.setText("Your current location is" + "\n" + "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude);


            } else if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();


                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                try{
                    JSONObject sosObj = new JSONObject();
                    sosObj.put("username", User.User1.getString("username"));
                    sosObj.put("firstName", User.User1.get("firstName").toString());
                    sosObj.put("latitude", lattitude);
                    sosObj.put("longitude",longitude);

                    lSocket.emit("SOS",sosObj);
                }catch (Exception e){
                    spinner.setVisibility(View.INVISIBLE);
                }
//



                textView.setText("Your current location is" + "\n" + "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude);


            } else {

                Toast.makeText(getActivity(), "Unble to Trace your location", Toast.LENGTH_SHORT).show();

            }
            // Ibrahim
            User.setLatti(lattitude);
            User.setLongi(longitude);

        }
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
