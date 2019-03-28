package com.example.profile;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.profile.httpRequestHelpers.httpPostRequest;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView name;
    private TextView username;
    ImageView profileImage;
    private static final int PICK_FROM_GALLERY = 101;
    String endpoint = "https://quick-health.herokuapp.com";
    String realPath;
    private Bitmap photo;
    private Switch aSwitch;
    String picturePath;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.activity_profile, container, false);

//        setContentView(R.layout.activity_profile);
        profileImage = v.findViewById(R.id.imageView);
        name = (TextView) v.findViewById(R.id.nameTextView);
        username = (TextView) v.findViewById(R.id.username);
        aSwitch = (Switch) v.findViewById(R.id.switch1);

        updateFields(User.User1);

        Button button = (Button) v.findViewById(R.id.button8);
        Button emergencyInfo = (Button) v.findViewById(R.id.button7);
        Button personalInfo = (Button) v.findViewById(R.id.button6);
        Button updateMember = (Button) v.findViewById(R.id.button9);
        Button logoutB = (Button) v.findViewById(R.id.logout);
        button.setOnClickListener(this);
        personalInfo.setOnClickListener(this);
        updateMember.setOnClickListener(this);
        emergencyInfo.setOnClickListener(this);

        profileImage.setOnClickListener(this);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Intent intent2 = new Intent(getActivity().getApplicationContext(),Location_Service.class);
                    ContextCompat.startForegroundService(getActivity(), intent2);
                }
                else{
                    Intent intentOn = new Intent(getActivity().getApplicationContext(),Location_Service.class);
                    getActivity().stopService(intentOn);
                }
            }
        });

        logoutB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        // makes sure Permissions are granted as of latest Android versions
        TedPermission.with(getActivity())
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();

        return v;


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri selectedImage = data.getData();

            // the path to the image in the phone
            realPath = ImageFilePath.getPath(this.getActivity(), selectedImage);

            // store photo path in database
            storePhotoPath(realPath);

            photo = BitmapFactory.decodeFile(realPath);

            // crops image, makes into a circle and sets as profile picture
            performCrop();

        } else {
            Toast.makeText(this.getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }

    }
    // crops image, makes into a circle and sets as profile picture
    private void performCrop() {
        Bitmap croppedBmp = Bitmap.createBitmap(photo, 0, 0, photo.getWidth(), photo.getWidth());
        profileImage.setImageBitmap(getCircleBitmap(croppedBmp));
    }

    // makes a image circular
    private Bitmap getCircleBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }

    public  ProfileFragment() {}

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button8: {
                Intent intent = new Intent(getActivity(), Medical_History2.class);
                startActivity(intent);
                break;
            }
            case R.id.button6: {
                Intent intent = new Intent(getActivity(), PersonalInfo.class);
                startActivity(intent);
                break;
            }
            case R.id.button9: {
                // is app compact
                Intent intent = new Intent(getActivity(), ValidateDoctor.class);
                startActivity(intent);
                break;
            }
            case R.id.button7: {
                Intent intent = new Intent(getActivity(), EmergencyInfo.class);
                startActivity(intent);
            }
            case R.id.imageView: {
                openGallery();

            }
        }
    }

    /**
     *  Load user fields
     *
     */
    private void updateFields(JSONObject user) {
        try {
            name.setText(User.User1.get("firstName").toString());
            username.setText(User.User1.get("username").toString());

            String path = getPhotoPath().toString();

            if (path != "null") {
                photo = BitmapFactory.decodeFile(path);
                performCrop();
            }

        }catch (JSONException e){
            return;
        }

    }
    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("return-data", true);
        Log.d("Open Gallery", "In here");
        startActivityForResult(
                Intent.createChooser(intent, "Complete action using"),
                PICK_FROM_GALLERY);
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

    /*
     *   Store photo path
     */
    public void storePhotoPath(String path) {
        try {

            // Get current logged in user's id
            String id = User.getUser().get("_id").toString();

            // Create request body
            Map<String, String> body = new HashMap<>();
            body.put("id", id);
            body.put("path", path);


            // Create request to fetch user info
            httpPostRequest task = new httpPostRequest(body);

            String result = task.execute(endpoint + "/user/profilePicture").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*
     *
     */
    public String getPhotoPath() {
        String photoPath = null;

        try {
            // Get current logged in user's id
            String id = User.getUser().get("_id").toString();

            // Create request body
            Map<String, String> body = new HashMap<>();
            body.put("id", id);

            // Create request to fetch user info
            httpPostRequest task = new httpPostRequest(body);

            String result = task.execute(endpoint + "/user/getProfilePicture").get();
            JSONObject json = new JSONObject(result);
            photoPath = json.get("path").toString();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return photoPath;
    }

    private void logout(){
        User.setUser(null);
        User.acceptedDocter = null;
        User.acceptedDocter = null;
        User.acceptedUser = null;
        User.items = new ArrayList<String>();
        Intent intentOn = new Intent(getActivity().getApplicationContext(),Location_Service.class);
        getActivity().stopService(intentOn);
        Intent intentOn1 = new Intent(getActivity().getApplicationContext(),SOS_Service.class);
        getActivity().stopService(intentOn1);
        if(GSocket.getInstance() != null){
            GSocket.getInstance().disconnect();
            GSocket.instance = null;
        }

        Intent intentOn2 = new Intent(getActivity(), Login.class);
        startActivity(intentOn2);


    }
}
