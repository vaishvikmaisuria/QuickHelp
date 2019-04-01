package com.example.profile.httpRequestHelpers;

import android.content.Context;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.toolbox.JsonArrayRequest;
import com.example.profile.NotificationFragment;
import com.example.profile.User;
import com.example.profile.sosRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.http.Url;

public class fetchData extends AsyncTask<String, Void, String> {
    String Data = "";
    String dataParsed = "";
    String singleParsed = "";

    @Override
    protected String doInBackground(String... strings) {

        try {
            URL myUrl = new URL("https://quick-health.herokuapp.com/sos/allSOS");
            HttpURLConnection httpURLConnection = (HttpURLConnection) myUrl.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";

            while(line != null){
                line = reader.readLine();
                Data = Data + line;
            }
            Data = Data.substring(10, Data.length()-5);
//            Log.d("data", Data);
            JSONArray JA = new JSONArray(Data);
            NotificationFragment.listItems.clear();

            for (int i = 0; i < JA.length(); i++){
                JSONObject JO = (JSONObject) JA.get(i);
                double tempdist = (distance(Double.valueOf(User.getLatti()), Double.valueOf(JO.get("latitude").toString()), Double.valueOf(User.getLongi()),  Double.valueOf(JO.get("longitude").toString()), 0.0, 0.0 ))/1000;
                Log.d("User Distance", Double.toString(tempdist));
                Log.d("SOS signal Distance", JO.toString());
                String Location = "Distance: " + String.format("%.4f", tempdist) + " km" + "\n";
                String Type = "injuryDetails: " + JO.get("injuryDetails") + "\n";
                String ID = (String) JO.get("_id");
                Double longitude = (double) JO.get("longitude");
                Double latitude = (double) JO.get("latitude");
                String patient = JO.getString("userID");
                sosRequest listitem =  new sosRequest(i, Location,Type, ID, longitude, latitude, patient);

                NotificationFragment.listItems.add(listitem);


                dataParsed = dataParsed + singleParsed + "\n";
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * @returns Distance in Meters
     */
    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
//        NotificationFragment.textView.setText(this.dataParsed);
    }
}
