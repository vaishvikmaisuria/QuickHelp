package com.example.profile;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.profile.Retrofit.INodeJs;
import com.example.profile.Retrofit.RetrofitClient;

import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Register extends AppCompatActivity {
    private INodeJs myApi;
    private  final CompositeDisposable compositeDisposable = new CompositeDisposable();
    EditText username, password,email,firstName,lastName,confirm;
    Button submit;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Retrofit retrofit = RetrofitClient.getInstance();
        myApi = retrofit.create(INodeJs.class);
        username = (EditText) findViewById(R.id.regUsername);
        password = (EditText) findViewById(R.id.regPassword);
        email = (EditText) findViewById(R.id.regEmail);
        firstName = (EditText) findViewById(R.id.regFirst);
        lastName = (EditText) findViewById(R.id.regLast);
        confirm = (EditText) findViewById(R.id.regConfirm);
        submit = (Button) findViewById(R.id.regButton);
        spinner = (ProgressBar) findViewById(R.id.spin);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().equals(confirm.getText().toString())){
                    RegisterUser(email.getText().toString(),username.getText().toString(),firstName.getText().toString(),
                            lastName.getText().toString(),password.getText().toString());
                }else{

                    Snackbar.make(v,"Password and Confirm Password do not match", Snackbar.LENGTH_LONG).show();
                }

            }
        });

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

    public void gotologin(View view) {


        Intent intent = new Intent(this, Login.class);
        startActivity(intent);

    }


    private void RegisterUser(String email,String username,String firstName,String lastName,String password){
        spinner.setVisibility(View.VISIBLE);
        compositeDisposable.add(myApi.registerUser(email,username,firstName,lastName,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String s) throws Exception {

                                   JSONObject response = new JSONObject(s);
                                   if(response.has("error")){
                                       //Log.d(LOG_TAG, "Register button clicked!");
                                       Snackbar.make(getRootView(), response.get("error").toString(), Snackbar.LENGTH_LONG).show();
                                       spinner.setVisibility(View.INVISIBLE);
                                   }else {
                                       Snackbar.make(getRootView(), "Registeration Complete", Snackbar.LENGTH_LONG).show();
                                       gotologin(getRootView());
                                   }
                               }
                           }
                ));


    }
}
