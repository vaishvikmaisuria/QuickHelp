package com.example.profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.profile.httpRequestHelpers.httpGetRequest;
import com.example.profile.httpRequestHelpers.httpPostRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class SearchingActivity extends AppCompatActivity implements View.OnClickListener {
    private Button dismissButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching);



        dismissButton = findViewById(R.id.dismissButton);
        dismissButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        String endpoint = "https://quick-health.herokuapp.com";
        String result0 = null;
        String result = null;
        String id = "";
        JSONObject JO = null;
        String helperid = "";
        Boolean flag = true;

        Map<String, String> postData = new HashMap<>();
        Map<String, String> postBody = new HashMap<>();
        Map<String, String> pb = new HashMap<>();

        // get the sos id
        try {
            id = sosData.getUsersos().get("_id").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // request body for getting specific SOS
        postBody.put("id", id);

        httpPostRequest task2 = new httpPostRequest(postBody);

        try {
            result0 = task2.execute( endpoint + "/sos/specificSOS").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            JO = new JSONObject(result0.substring(7,result0.length()-1));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            helperid = JO.get("helperID").toString();
        } catch (JSONException e) {
            flag = false;

        }

        // if helperID is valid
        if (flag) {
            postData.put("id", id);

            httpPostRequest task = new httpPostRequest(postData);

            try {
                result = task.execute( endpoint + "/sos/deleteSOS").get();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            // request body for removeSOS
            pb.put("doctorID", helperid);

            httpPostRequest removeTask = new httpPostRequest(pb);

            try {
                result = removeTask.execute( endpoint + "/user/removeSOS").get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                    e.printStackTrace();
            }

        } else{
            Log.d("SearchingActivity","helperID nonexistent");
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
