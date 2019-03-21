package com.example.profile;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
//import android.view.View;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.profile.httpRequestHelpers.httpPostRequest;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class SOSFragment extends Fragment implements View.OnClickListener {

    private static final int REQUEST_LOCATION = 1;
    Button button;
    TextView textView;
    LocationManager locationManager;
    String lattitude, longitude;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        View v = inflater.inflate(R.layout.activity_sos__signal, container, false);

        textView = (TextView) v.findViewById(R.id.text_location);
        button = (Button) v.findViewById(R.id.button_location);


        button.setOnClickListener(this);

        return v;
    }

    public  SOSFragment() {}

    @Override
    public void onClick(View view) {

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


        Intent intent = new Intent(getActivity(), additional_Details.class);
        startActivity(intent);

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

                textView.setText("Your current location is" + "\n" + "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude);

            } else if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                textView.setText("Your current location is" + "\n" + "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude);


            } else if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                textView.setText("Your current location is" + "\n" + "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude);

            } else {

                Toast.makeText(getActivity(), "Unble to Trace your location", Toast.LENGTH_SHORT).show();

            }
            // Ibrahim

            //Some url endpoint that you may have
            String myUrl = "https://quick-health.herokuapp.com";
            //String myUrl = "http://localhost:3000";

            //String to place our result in
            String result = null;

            //Create the Json Object
            //JSONObject sosLocation = new JSONObject();
            Map<String, String> postData = new HashMap<>();
            String name = User.User1.get("firstName").toString();
            postData.put("uid", name);
            postData.put("longitude", longitude);
            postData.put("latitude", lattitude);

            //Instantiate new instance of our class
            httpPostRequest task = new httpPostRequest(postData);

            //Perform the doInBackground method, passing in our url

            try {
                result = task.execute(myUrl + "/sos/newSOS").get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            sosData.setUser(result);


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
