package com.example.profile;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.profile.httpRequestHelpers.httpPostRequest;

import org.json.JSONException;
import org.json.JSONObject;

import static android.support.constraint.Constraints.TAG;

public class EmergencyInfo extends AppCompatActivity {

    private EditText cell;
    private EditText name;
    private EditText relationship;
    private EditText address;
    private EditText work;
    private EditText home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_contact);

        name = (EditText) findViewById(R.id.name);
        relationship = (EditText) findViewById(R.id.relationship);
        address = (EditText) findViewById(R.id.address);
        work = (EditText) findViewById(R.id.work);
        home = (EditText) findViewById(R.id.home);
        cell = (EditText) findViewById(R.id.cell);

        updateFields(User.User1);

        Button sender = (Button) findViewById(R.id.updateEmr);
        sender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = (EditText) findViewById(R.id.name);
                relationship = (EditText) findViewById(R.id.relationship);
                address = (EditText) findViewById(R.id.address);
                work = (EditText) findViewById(R.id.work);
                home = (EditText) findViewById(R.id.home);
                cell = (EditText) findViewById(R.id.cell);

                try {
                    JSONObject change = initJSON();
                    JSONObject send = new JSONObject();
                    send.put("username",User.User1.get("username"));
                    send.put("contactInfo",change);
                    System.out.println(change);
                    httpPostRequest task = new httpPostRequest(null);
                    task.setJSON(send);
                    task.execute("https://quick-health.herokuapp.com/user/emergencyInfo/");
                    Snackbar.make(getRootView(), "Updated", Snackbar.LENGTH_LONG).show();

                }catch (Exception e) {
                    Log.d(TAG, e.getLocalizedMessage());
                }

            }
        });
    }
    public JSONObject initJSON() throws JSONException {
        JSONObject x = new JSONObject();
        x.put("contactName",name.getText());
        x.put("relationship",relationship.getText());
        x.put("work",work.getText());
        x.put("home", home.getText());
        x.put("cell", cell.getText());
        x.put("address", address.getText());

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

    private void updateFields(JSONObject user) {
        System.out.println(user.toString());
        try {
            name.setText(user.get("contactName").toString());
            relationship.setText(user.get("relationship").toString());
            work.setText(user.get("work").toString());
            address.setText(user.get("address").toString());
            home.setText(user.get("home").toString());
            cell.setText(user.get("cell").toString());

        }catch (JSONException e){
            return;
        }
    }
}
