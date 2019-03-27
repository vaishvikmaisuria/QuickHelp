package com.example.profile;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
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

public class PersonalInfo extends AppCompatActivity{

    private Button sender;
    EditText fName;
    EditText lName;
    EditText bloodType;
    private JSONObject change;
    private EditText address;
    private EditText occupation;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        setContentView(R.layout.personal_info);
        fName = (EditText) findViewById(R.id.fName);
        lName = (EditText) findViewById(R.id.lName);
        bloodType = (EditText) findViewById(R.id.bloodType);
        address = (EditText) findViewById(R.id.Address);
        occupation = (EditText) findViewById(R.id.Occupation);

        updateFields(User.User1);

        sender = (Button) findViewById(R.id.button12);

        sender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fName = (EditText) findViewById(R.id.fName);
                lName = (EditText) findViewById(R.id.lName);
                bloodType = (EditText) findViewById(R.id.bloodType);
                address = (EditText) findViewById(R.id.Address);
                occupation = (EditText) findViewById(R.id.Occupation);

                try {
                    change = initJSON();
                    JSONObject send = new JSONObject();
                    send.put("username",User.User1.get("username"));
                    send.put("userInfo",change);
                    System.out.println(change);
                    httpPostRequest task = new httpPostRequest(null);
                    task.setJSON(send);
                    task.execute("https://quick-health.herokuapp.com/user/userInfo/");
                    Snackbar.make(getRootView(), "Updated", Snackbar.LENGTH_LONG).show();

                }catch (Exception e) {
                    Log.d(TAG, e.getLocalizedMessage());
                }

            }
        });

    }

    private void updateFields(JSONObject user) {
        try {
            fName.setText(user.get("firstName").toString());
            lName.setText(user.get("lastName").toString());
            bloodType.setText(user.get("bloodType").toString());
            address.setText(user.get("Address").toString());
            occupation.setText(user.get("Occupation").toString());;
        }catch (JSONException e){
            return;
        }
    }

    public JSONObject initJSON() throws  JSONException{
        JSONObject x = new JSONObject();
        x.put("firstName",fName.getText());
        x.put("bloodType",bloodType.getText());
        x.put("lastName",lName.getText());
        x.put("Address", address.getText());
        x.put("Occupation", occupation.getText());
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


}
