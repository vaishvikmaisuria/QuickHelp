package com.example.profile.httpRequestHelpers;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.toolbox.JsonArrayRequest;
import com.example.profile.NotificationFragment;
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
                String Location = "Location: " + JO.get("longitude") + ", " + JO.get("latitude") + "\n";
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

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
//        NotificationFragment.textView.setText(this.dataParsed);
    }
}
