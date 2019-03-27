package com.example.profile;


import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.profile.Retrofit.INodeJs;
import com.example.profile.httpRequestHelpers.httpPostRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

import static android.app.PendingIntent.getActivity;
import static android.support.constraint.Constraints.TAG;


public class additional_Details extends AppCompatActivity {

    private Button updatebtn;
    private Button callbtn;

    JSONObject change;
    JSONObject send;
    Boolean flag = true;
    private TextInputEditText textbox;
    Map<String, Boolean> map = new HashMap<String, Boolean>();
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

//        map.put("Broken Bones",false);
//        try {
//            change = initJSON();
//
//        }catch (JSONException e){
//            e.printStackTrace();
//        }

        textbox = (TextInputEditText) findViewById(R.id.textbox22);

        updatebtn = (Button) findViewById(R.id.button_update);

        updatebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                try {

                    send = new JSONObject();

                    if (map.get("Broken Bones")) {
                        Intent intent = new Intent(view.getContext(), self_help_brokenbone.class);
                        startActivity(intent);

                    }
                    if (map.get("Choking")) {
                        Intent intent = new Intent(view.getContext(), self_help_blocked_airway.class);
                        startActivity(intent);

                    }
                    if (map.get("Bleeding")) {
                        Intent intent = new Intent(view.getContext(), self_help_bleeding.class);
                        startActivity(intent);

                    } else if (map.get("Fire Related Injury")) {
                        Intent intent = new Intent(view.getContext(), self_help_burn.class);
                        startActivity(intent);
                    } else if (map.get("Joint Twist")) {
                        //textView10.setText("true");
                        Intent intent = new Intent(view.getContext(), self_help_brokenbone.class);
                        startActivity(intent);
                    }

                    if (map.get("Unconsciousness")) {
                        //textView10.setText("true");

                        Intent intent = new Intent(view.getContext(), self_help_unconscious.class);
                        startActivity(intent);
                    }
                    if (map.get("Heart Stroke")) {
                        //textView10.setText("true");
                        Intent intent = new Intent(view.getContext(), self_help_heart.class);
                        startActivity(intent);
                    }

                    if (map.get("Asthma")) {
                        //textView10.setText("true");
                        Intent intent = new Intent(view.getContext(), self_help_unconscious.class);
                        startActivity(intent);
                    }

                    if (map.get("Poisoning")) {
                        //textView10.setText("true");
                        Intent intent = new Intent(view.getContext(), self_help_unconscious.class);
                        startActivity(intent);
                    }
                    if (map.get("Drowning")) {
                        //textView10.setText("true");
                        Intent intent = new Intent(view.getContext(), self_help_unconscious.class);
                        startActivity(intent);
                    }

                    String Desc = "";


                    for (Map.Entry<String, Boolean> entry : map.entrySet()) {
                        if (entry.getValue()) {
                            Desc += " " + entry.getKey() + " ,";
                        }
                    }
                    if (Desc == null || Desc.length() == 0) {
                        Desc = textbox.getText().toString();
                        if (Desc == null || Desc.length() == 0) {
                            flag = false;
                        } else {
                            flag = true;
                        }
                    } else {
                        flag = true;
                    }


                    send.put("id", sosData.getSos1().substring(8, 32));
                    send.put("injuryDetails", Desc);
                    httpPostRequest task = new httpPostRequest(null);
                    task.setJSON(send);
                    //link to patch the sos collection
                    task.execute("https://quick-health.herokuapp.com/sos/updateSOS");

                } catch (Exception e) {
                    Log.d(TAG, e.getLocalizedMessage());
                }

                // go to the next page waiting for the doctor
                if (flag) {
                    gohelpseverity(view);
                } else {


                }


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
                Intent intent = new Intent(view.getContext(), SearchingActivity.class);
                startActivity(intent);
            }

            public void onCheckboxClicked(View view) throws JSONException {
                boolean checked = ((CheckBox) view).isChecked();

                switch (view.getId()) {
                    case R.id.checkBoxbrokenBones:
                        if (checked) {
                            //Snackbar.make(getRootView(), "Updated", Snackbar.LENGTH_LONG).show();
                            map.put("Broken Bones", true);
                        } else {
                            map.put("Broken Bones", false);
                        }
                        break;
                    case R.id.checkBoxChoking:
                        if (checked) {
                            map.put("Choking", true);

                        } else {
                            map.put("Choking", false);
                        }
                        break;

                    case R.id.checkBoxBleeding:
                        if (checked) {
                            map.put("Bleeding", true);


                        } else {
                            map.put("Bleeding", false);
                        }
                        break;
                    case R.id.checkBoxfire:
                        if (checked) {
                            map.put("Fire Related Injury", true);

                        } else {
                            map.put("Fire Related Injury", false);
                        }
                        break;
                    case R.id.checkBoxjointTwist:
                        if (checked) {
                            map.put("Joint Twist", true);

                        } else {
                            map.put("Joint Twist", false);
                        }
                        break;
                    case R.id.checkBoxUnconciousness:
                        if (checked) {
                            map.put("Unconsciousness", true);
                        } else {
                            map.put("Unconsciousness", false);
                        }
                        break;
                    case R.id.checkBoxHeartStroke:
                        if (checked) {
                            map.put("Heart Stroke", true);

                        } else {
                            map.put("Heart Stroke", false);
                        }
                        break;
                    case R.id.checkBoxAsthma:
                        if (checked) {
                            map.put("Asthma", true);

                        } else {
                            map.put("Asthma", false);
                        }
                        break;
                    case R.id.checkBoxPoisoning:
                        if (checked) {
                            map.put("Poisoning", true);

                        } else {
                            map.put("Poisoning", false);
                        }
                        break;

                    case R.id.checkBoxDrowning:
                        if (checked) {
                            map.put("Drowning", true);
                        } else {
                            map.put("Drowning", false);
                        }
                        break;


                }

            }

            public JSONObject initJSON() throws JSONException {
                JSONObject x = new JSONObject();
                x.put("injuryDetails", "");

                return x;
            }


            private void emergency_call(final String phoneNumber) {

                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
            }
        });
    };
}

