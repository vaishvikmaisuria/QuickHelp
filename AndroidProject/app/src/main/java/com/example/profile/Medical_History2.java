package com.example.profile;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.profile.Classes.MedicalForm;
import com.example.profile.Retrofit.INodeJs;
import com.example.profile.Retrofit.RetrofitClient;
import com.example.profile.httpRequestHelpers.httpPostRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.jar.JarException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.support.constraint.Constraints.TAG;

public class Medical_History2 extends AppCompatActivity {
    TextInputLayout otherText;
    JSONObject change;
    JSONObject send;
    private INodeJs myApi;
    private  final CompositeDisposable compositeDisposable = new CompositeDisposable();

    Button sender;

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
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical__history2);

        otherText = (TextInputLayout) findViewById(R.id.textInputLayout);

        try {
            change = initJSON();

        }catch (JSONException e){
            e.printStackTrace();
        }

        sender = (Button) findViewById(R.id.button12);

        sender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    change.put("other", otherText.getEditText().getText().toString());
                    send = new JSONObject();
                    send.put("username",User.User1.get("username").toString());
                    send.put("medicalInfo",change);
                    httpPostRequest task = new httpPostRequest(null);
                    task.setJSON(send);
                    task.execute("https://quick-health.herokuapp.com/user/updateInfo");
                    Snackbar.make(getRootView(), "Updated", Snackbar.LENGTH_LONG).show();


                }catch (Exception e) {
                    Log.d(TAG, e.getLocalizedMessage());
                }

            }
        });






    }

    public void onCheckboxClicked(View view) throws JSONException {
        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.checkBox20:
                if (checked) {
                    //Snackbar.make(getRootView(), "Updated", Snackbar.LENGTH_LONG).show();
                    change.put("highBloodPressure",true);
                }
                break;
            case R.id.checkBox2:
                if (checked) {
                    change.put("highCholesterol",true);
                }
                break;

            case R.id.checkBox3:
                if (checked) {
                    change.put("kidneyDisease",true);
                }
                break;
            case R.id.checkBox4:
                if (checked) {
                    change.put("thyroidProblems",true);
                }
                break;
            case R.id.checkBox5:
                if (checked) {
                    change.put("jointReplacement",true);
                }
                break;
            case R.id.checkBox6:
                if (checked) {
                    change.put("lungDisease",true);
                }
                break;
            case R.id.checkBox7:
                if (checked) {
                    change.put("stroke",true);
                }
                break;
            case R.id.checkBox8:
                if (checked) {
                    change.put("asthmas",true);
                }
                break;
            case R.id.checkBox9:
                if (checked) {
                    change.put("heartProblem",true);
                }
                break;
        }
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
