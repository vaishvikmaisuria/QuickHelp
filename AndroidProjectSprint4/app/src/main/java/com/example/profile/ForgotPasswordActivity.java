package com.example.profile;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.profile.Retrofit.INodeJs;
import com.example.profile.Retrofit.RetrofitClient;

import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText recovery_email;
    private Button reset_password;
    private INodeJs myApi;
    private  final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        recovery_email = (EditText) findViewById(R.id.txtResetEmail);
        reset_password = (Button) findViewById(R.id.btRecover);

        Retrofit retrofit = RetrofitClient.getInstance();
        myApi = retrofit.create(INodeJs.class);
        spinner = (ProgressBar) findViewById(R.id.spin);

        reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                forgotPass(recovery_email.getText().toString());
                //Snackbar.make(getRootView(),usernameET.getText().toString()+ " " +passwordET.getText() , Snackbar.LENGTH_LONG).show();
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

    private void forgotPass(String email) {
        spinner.setVisibility(View.VISIBLE);
        compositeDisposable.add(myApi.forgotPassword(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String s) throws Exception {
                                   JSONObject response = new JSONObject(s);
                                   if(response.has("error")){
                                       Snackbar.make(getRootView(), response.get("error").toString(), Snackbar.LENGTH_LONG).show();
                                       spinner.setVisibility(View.INVISIBLE);
                                   }else {
                                       User.setUser(response);
                                       gotologin(getRootView());
                                       spinner.setVisibility(View.INVISIBLE);

                                   }
                               }
                           }
                )
        );
    }

    public void gotologin(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}
