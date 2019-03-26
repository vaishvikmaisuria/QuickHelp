package com.example.profile;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.text.Selection;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.profile.httpRequestHelpers.httpPostRequest;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;
import static com.example.profile.User.User1;
import static com.example.profile.User.getUser;


public class ValidateDoctor extends Activity implements OnClickListener {
    TextView uploads;
    EditText certifications;
    Button btnSend, btnAttachment;
    String email, subject, message, fromemail;
    private static final int PICK_FROM_GALLERY = 101;
    String realPath;
    String key;

    ArrayList<String> image_paths = new ArrayList<String>();
    private Button verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.validate_professional);

        btnSend = (Button) findViewById(R.id.send_email);
        verify = (Button) findViewById(R.id.verify);
        btnAttachment = (Button) findViewById(R.id.upload);
        uploads = (TextView) findViewById(R.id.uploadCount);
        certifications = (EditText) findViewById(R.id.certificationKey);

        try {
            key = User.User1.get("key").toString();

            if ((boolean)User.getUser().get("professional")) {
                certifications.setText(User.User1.get("key").toString());
                certifications.setEnabled(false);
            } else {
                btnSend.setOnClickListener(this);
                btnAttachment.setOnClickListener(this);
                verify.setOnClickListener(this);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // makes sure Permissions are granted as of latest Android versions
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            // stores paths of image that was selected
            // ImageFilePah is a class that is used to get the path of the selected file
            realPath = ImageFilePath.getPath(ValidateDoctor.this, uri);
            image_paths.add(realPath);
            String upload_str = Integer.toString(image_paths.size());
            uploads.setText("Documents " + upload_str);

        } else {
            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {

        if (v == btnAttachment) {
            openGallery();
        }
        if (v == btnSend) {
            if(image_paths.size() > 0){
                sendEmail();
            }else{
                Toast.makeText(ValidateDoctor.this, "Please upload your medical certifications.", Toast.LENGTH_LONG).show();

            }

        }
        if (v == verify) {
            try {
                validateProfessional();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    /*
        function that allows user to open the phone gallery and select an image
     */
    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("return-data", true);
        startActivityForResult(
                Intent.createChooser(intent, "Complete action using"),
                PICK_FROM_GALLERY);
    }
    /*
        Creates an asyncronous task that invokes the mail server to send the email
        Email contains images that professional chose and is sent to a admin of the app for approval
        Email is sent using the QuickHealth gmail account, with credentials hardcoded into this function

        right now, only ainy is sent the email, but email can easily be sent to a group of people, or the whole team
     */
    public void sendEmail(){

        try {

            class SendMailTask extends AsyncTask {

                private ProgressDialog statusDialog;
                private Activity sendMailActivity;
                private int success;

                public SendMailTask(Activity activity) {
                    sendMailActivity = activity;
                    success = 0;
                }

                @Override
                protected Object doInBackground(Object... args) {
                    try {

                        // creating the email with its contents
                        Mail m = new Mail("quickhealthgroup@gmail.com", "Password1s#");
                        email = "ainy.afzal@mail.utoronto.ca";
                        fromemail = "quickhealthgroup@gmail.com";
                        subject = "QuickHealth Verify Doctor";
                        String user_email = User1.get("email").toString();
                        message = "Verify uploaded documents. If the user is a valid medical professional, send an email to " + user_email + " with this key: " + key;
                        String[] toArr = {email}; // This is an array, you can add more emails, just separate them with a coma
                        m.setTo(toArr); // load array to setTo function
                        m.setFrom(fromemail); // who is sending the email
                        m.setSubject(subject);
                        m.setBody(message);

                        // adding all selected images to the email
                        for(int i = 0; i < image_paths.size(); i ++){
                            m.addAttachment(image_paths.get(i));  // path to file you want to attach
                        }

                        // sending the email
                        m.send();

                    } catch (Exception e) {
                        publishProgress(e.getMessage());
                        Log.e("SendMailTask", e.getMessage(), e);
                    }
                    return null;
                }

                @Override
                public void onPostExecute(Object result) {

                    //emptying array of images
                    image_paths = new ArrayList<String>();
                }
            }
            SendMailTask sent = new SendMailTask(ValidateDoctor.this);

            sent.execute();
            uploads.setText("Documents 0");
            // informing user of success
            Toast.makeText(ValidateDoctor.this, "The request has been sent.", Toast.LENGTH_LONG).show();

        } catch(Exception e) {
            // some other problem
            System.out.println(e);
            Toast.makeText(ValidateDoctor.this, "There was an error sending your request. Please try again or contact an admin.", Toast.LENGTH_LONG).show();
        }

    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            //Toast.makeText(ValidateDoctor.this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            //Toast.makeText(ValidateDoctor.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    public void validateProfessional() throws JSONException {
        String entered = certifications.getText().toString();
        if(entered.equals(key)){
            // the correct key
            // unable to edit it anymore
            certifications.setEnabled(false);

            try {

                JSONObject send = new JSONObject();
                send.put("username",User.User1.get("username"));
                send.put("key",entered);
                httpPostRequest task = new httpPostRequest(null);
                task.setJSON(send);
                task.execute("https://quick-health.herokuapp.com/user/addDoctor/");
                Snackbar.make(getRootView(), "Updated", Snackbar.LENGTH_LONG).show();
            }catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
        }else{
            Toast.makeText(ValidateDoctor.this, "This key is not valid.", Toast.LENGTH_LONG).show();
        }
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


