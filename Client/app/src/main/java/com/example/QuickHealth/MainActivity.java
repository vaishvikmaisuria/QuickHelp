package com.example.QuickHealth;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.QuickHealth.Class.HTTPDataHandler;
import com.example.medicalform.R;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.lang.reflect.Type;
import java.security.cert.Extension;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private View view;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MongoClientURI mongoUri  = new MongoClientURI("mongodb://quickhelp:quickhelp1@ds123645.mlab.com:23645/quickhelp");
        MongoClient mongoClient = new MongoClient(mongoUri);
        DB db = mongoClient.getDB("MedicalHistoryForm");
        Set<String> collectionNames = db.getCollectionNames();

        String json = "{";
        
        public void onCheckboxClicked(View view) {
            boolean checked = ((CheckBox) this.view).isChecked();

            switch(this.view.getId()) {
                case R.id.checkBox1:
                    if (checked) {
                        json = json + "highBloodPressue: true";
                    }else {
                        json = json + "highBloodPressue: false";
                    }
                    break;
                case R.id.checkBox2:
                    if (checked) {
                        json = json + "highCholesterol: true";
                    }else {
                        json = json + "highCholesterol: false";
                    }
                    break;

                case R.id.checkBox3:
                    if (checked) {
                        json = json + "kidneyDisease: true";
                    }else {
                        json = json + "kidneyDisease: false";
                    }
                    break;
                case R.id.checkBox4:
                    if (checked) {
                        json = json + "thyroidProblems: true";
                    }
                    else {
                        json = json + "thyroidProblems: false";
                    }
                    break;
                case R.id.checkBox5:
                    if (checked) {
                        json = json + "jointReplacement: true";
                    }
                    else {
                        json = json + "jointReplacement: false";
                    }
                    break;
                case R.id.checkBox6:
                    if (checked) {
                        json = json + "lungDisease: true";
                    }
                    else {
                        json = json + "lungDisease: false";
                    }
                    break;
                case R.id.checkBox7:
                    if (checked) {
                        json = json + "stroke:  true";
                    }
                    else {
                        json = json + "stroke: false";
                    }
                    break;
                case R.id.checkBox8:
                    if (checked) {
                        json = json + "asthmas: true";
                    } else {
                        json = json + "asthmas: false";
                    }
                    break;

                case R.id.checkBox9:
                    if (checked) {
                        json = json + "heartProblem: true";
                    }
                    else {
                        json = json + "heartProblem: false";
                    }
                    break;
            }
        }
        Button save = (Button) findViewById(R.id.med_save);
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new PostData(json).execute(String.valueOf(mongoUri));
            }

        });


}
    class getData extends AsyncTask<String, Void, String> {
        ProgressDialog pd = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);

            Gson gson = new Gson();
            Type ListType = new TypeToken<List<User>>
            pd.dismiss();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("Please wait");
            pd.show();
        }

        @Override
        protected String doInBackground(String... params){
            String stream=null;
            String urlString = params[0];

            HTTPDataHandler http = new HTTPDataHandler();
            http.getHTTPData(urlString);
            return stream;
        }
    }

    class PostData extends AsyncTask<String, String, String> {
        String json = params[0];
        ProgressDialog pd = new ProgressDialog(MainActivity.this);
        public PostData(String json) {
        }
        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            pd.dismiss();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("Please wait...");
            pd.show();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... params){
            String urlString=params[0];

            HTTPDataHandler http = new HTTPDataHandler();
            http.PostHTTPData(urlString, json);
            return "";
        }
    }