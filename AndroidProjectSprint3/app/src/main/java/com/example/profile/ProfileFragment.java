package com.example.profile;

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
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.BreakIterator;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView name;
    private TextView username;
    ImageView profileImage;
    private static final int PICK_FROM_GALLERY = 101;
    String realPath;
    private Bitmap photo;
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

        updateFields(User.User1);

        Button button = (Button) v.findViewById(R.id.button8);
        Button emergencyInfo = (Button) v.findViewById(R.id.button7);
        Button personalInfo = (Button) v.findViewById(R.id.button6);
        Button updateMember = (Button) v.findViewById(R.id.button9);

        button.setOnClickListener(this);
        personalInfo.setOnClickListener(this);
        updateMember.setOnClickListener(this);
        emergencyInfo.setOnClickListener(this);

        profileImage.setOnClickListener(this);

        return v;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri selectedImage = data.getData();

            // the path to the image in the phone
            realPath = ImageFilePath.getPath(this.getActivity(), selectedImage);

            // creating the bitmap of the image
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
            photo = BitmapFactory.decodeFile(picturePath);

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

    private void updateFields(JSONObject user) {
        try {
            name.setText(User.User1.get("firstName").toString());
            username.setText(User.User1.get("username").toString());
        }catch (JSONException e){
            return;
        }

    }
    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("return-data", true);
        startActivityForResult(
                Intent.createChooser(intent, "Complete action using"),
                PICK_FROM_GALLERY);
    }
}
