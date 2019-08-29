package com.quickhealth.quickhealth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.quickhealth.quickhealth.httpRequestHelpers.httpGetRequest;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Button viewmap;

    private Button sendloco;

    private Button sossignalpage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewmap = (Button) findViewById(R.id.view_map);
        sendloco = (Button) findViewById(R.id.send_location);
        sossignalpage = (Button) findViewById(R.id.sos_page);

        sossignalpage.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                openSOSActivity();
            }
        });


        sendloco.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                rungetRequest();
            }
        });


        viewmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }
        });

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void rungetRequest(){
        Toast.makeText(this, "get request", Toast.LENGTH_SHORT).show();
        //Some url endpoint that you may have
        String myUrl = "https://quick-health.herokuapp.com/work";
        //String to place our result in
        String result;
        //Instantiate new instance of our class
        httpGetRequest getRequest = new httpGetRequest();
        //Perform the doInBackground method, passing in our url
        try {
            result = getRequest.execute(myUrl).get();
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void openActivity(){
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public void openSOSActivity(){
        Intent intent = new Intent(this, SOSsignal.class);
        startActivity(intent);
    }

}
