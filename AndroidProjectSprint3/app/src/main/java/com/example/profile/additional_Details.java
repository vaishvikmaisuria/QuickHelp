package com.example.profile;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.profile.Retrofit.INodeJs;
import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.disposables.CompositeDisposable;

public class additional_Details extends AppCompatActivity {

    private Button updatebtn;
    private Button callbtn;
    JSONObject change;
    JSONObject send;
    private INodeJs myApi;
    private  final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onStop(){
        compositeDisposable.clear();
        super.onStop();
    }


    @Override
    protected void onDestroy(){
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional__details);

        try {
            change = initJSON();

        }catch (JSONException e){
            e.printStackTrace();
        }

        updatebtn = (Button) findViewById(R.id.button_update);

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gohelpseverity(v);
            }
        });

        callbtn = (Button) findViewById(R.id.button_call);

        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // used to call 911, but calling a friend instead
                // wen's number
                emergency_call("4164514846");
            }
        });
    }

    public void gohelpseverity(View view) {
        Intent intent = new Intent(this, help_severity.class);
        startActivity(intent);
    }

    public JSONObject initJSON() throws  JSONException{
        JSONObject x = new JSONObject();
        x.put("highBloodPressure",false);
        x.put("highCholesterol",false);
        x.put("kidneyDisease",false);
        x.put("thyroidProblems",false);
        x.put("jointReplacement",false);
        x.put("lungDisease",false);
        x.put("stroke",false);
        x.put("asthmas",false);
        x.put("heartProblem",false);

        return x;
    }

    private void emergency_call(final String phoneNumber) {

        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }
}
