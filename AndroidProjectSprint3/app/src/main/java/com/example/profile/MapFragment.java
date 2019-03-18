package com.example.profile;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.profile.directionhelpers.FetchURL;
import com.example.profile.directionhelpers.TaskLoadedCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MapFragment extends Fragment implements OnMapReadyCallback, TaskLoadedCallback, LocationListener {
    private static final String TAG = "MapActivity";
    private GoogleMap mMap;
    private MarkerOptions place1, place2;
    Button getDirection;
    private Polyline currentPolyline;
    //    private Boolean mLocationPermissionGranted = false;
    private LocationManager locationManager;
    private Marker destination, current_location;

    private Location onlyOneLocation;
    private final int REQUEST_FINE_LOCATION = 1234;

    private FusedLocationProviderClient client;

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//////        View v = inflater.inflate(R.layout.activity_map_, container, false);
////        super.onCreate(savedInstanceState);
//////        setContentView(R.layout.map_activity);
////        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
////            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
////        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
////        locationManager.requestLocationUpdates("gps", 0, 0, (android.location.LocationListener) this);
////
////
////        getDirection = v.findViewById(R.id.btnGetDirection);
////        getDirection.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Log.d("place 1", place1.toString());
////                Log.d("place 2", place2.toString());
////
////                new FetchURL(getActivity()).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");
////            }
////        });
////        if (ActivityCompat.checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
////            return v;
////        }
////
////        // square one: 43.5913458, -79.6461564
////        // south common: 43.5446645, -79.6826318
////        place1 = new MarkerOptions().position(new LatLng(43.547868, -79.660944)).title("Location 1");
////        place2 = new MarkerOptions().position(new LatLng(43.5915729, -79.6456884)).title("Location 2");
//////        com.google.android.gms.maps.MapFragment mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById()
//////                .findFragmentById(R.id.mapNearBy);
//////        mapFragment.getMapAsync(this);
////        SupportMapFragment mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapNearBy);
////        mMapFragment.getMapAsync(this);
////        return v;
//    }



    public  MapFragment() {}

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }

    @Override
    public void onLocationChanged(Location location) {
        onlyOneLocation = location;
        current_location.remove();
//        if (currentPolyline!=null && (location.getLatitude() != current_location.getPosition().latitude) && (location.getLongitude() != current_location.getPosition().longitude)) {
//            currentPolyline.remove();
//        }
        MarkerOptions newLoc = new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Destination");

        current_location = mMap.addMarker(newLoc);
        place2 = newLoc;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("mylog", "Added Markers");
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        destination = mMap.addMarker(place1);
        current_location = mMap.addMarker(place2);
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_FINE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("gps", "Location permission granted");
                    try {
                        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                        locationManager.requestLocationUpdates("gps", 0, 0, (android.location.LocationListener) this);
                    } catch (SecurityException ex) {
                        Log.d("gps", "Location permission did not work!");
                    }
                }
                break;
        }
    }
}
