package com.example.profile;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.profile.httpRequestHelpers.httpPostRequest;

import org.json.JSONException;
import org.json.JSONObject;

import static android.support.constraint.Constraints.TAG;

public class ValidateProfessional extends AppCompatActivity {

    private Button send;
    TextView uploads;
    private JSONObject change;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        setContentView(R.layout.validate_professional);

        /*Fragment selectedFragment =  new SendEmail();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                                           selectedFragment).commit();*/




       /*
        uploads = (TextView) findViewById(R.id.uploadCount);

        updateFields(User.User1);



        send = (Button) findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploads = (TextView) findViewById(R.id.uploadCount);

                try {
                    change = initJSON();
                    JSONObject send = new JSONObject();
                    // idk what this is for
                    send.put("uploadCount",User.User1.get("uploadCount"));
                    send.put("uploadInfo",change);
                    System.out.println(change);
                    httpPostRequest task = new httpPostRequest(null);
                    task.setJSON(send);
                    task.execute("https://quick-health.herokuapp.com/user/uploadInfo/");
                    Snackbar.make(getRootView(), "Updated", Snackbar.LENGTH_LONG).show();


                }catch (Exception e) {
                    Log.d(TAG, e.getLocalizedMessage());
                }

            }
        });*/

    }

/*
    private void updateFields(JSONObject user) {
        try {
            uploads.setText(user.get("uploadCount").toString());
        }catch (JSONException e){
            return;
        }
    }

    public JSONObject initJSON() throws  JSONException{
        JSONObject x = new JSONObject();
        x.put("uploadCount","Documents Uploaded 0");
        return x;

    }

    private View getRootView() {
        final ViewGroup contentViewGroup = (ViewGroup) findViewById(android.R.id.content);
        View rootView = null;

        if(contentViewGroup != null)
            rootView = contentViewGroup.getChildAt(0);

        if(rootView == null)
            rootView = getWindow().getDecorView().getRootView();

        return rootView;
    }
*/


}
