package com.example.profile;

import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;

import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.Media;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.profile.httpRequestHelpers.httpPostRequest;

import org.json.JSONObject;

import static android.support.constraint.Constraints.TAG;

public class SendEmail extends Activity implements OnClickListener{

        TextView uploads;
        Button btnSend, btnAttachment;
        String email, subject, message, attachmentFile;
        Uri URI = null;
        private static final int PICK_FROM_GALLERY = 101;
        int columnIndex;
        int uploadCount = 0;

        @Override
        public void onCreate( Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.validate_professional);
            btnSend = (Button) findViewById(R.id.send);
            btnAttachment = (Button) findViewById(R.id.upload);

            uploads = (TextView) findViewById(R.id.uploadCount);

            btnSend.setOnClickListener(this);
/*            btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        email = "ainy.afzal@mail.utoronto.ca";
                        subject = "QuickHealth Verify Doctor";
                        message = "Verify uploaded documents and click link to verify the doctor: <link>";

                        final Intent emailIntent = new Intent(
                                android.content.Intent.ACTION_SEND);
                        emailIntent.setType("plain/text");
                        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                                new String[] { email });
                        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                                subject);
                        if (URI != null) {
                            emailIntent.putExtra(Intent.EXTRA_STREAM, URI);
                        }
                        emailIntent
                                .putExtra(android.content.Intent.EXTRA_TEXT, message);
                        this.startActivity(Intent.createChooser(emailIntent,
                                "Sending email..."));

                    } catch (Throwable t) {
                        Toast.makeText(this,
                                "Request failed try again: " + t.toString(),
                                Toast.LENGTH_LONG).show();
                    }

                }
            });*/
            btnAttachment.setOnClickListener(this);
        }
    public void setContentView(int activity_profile) {
    }

        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == PICK_FROM_GALLERY && resultCode == 1) {
                /**
                 * Get Path
                 */
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = this.getContentResolver().query(selectedImage,filePathColumn, null, null, null);
                cursor.moveToFirst();
                columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                attachmentFile = cursor.getString(columnIndex);
                Log.e("Attachment Path:", attachmentFile);
                URI = Uri.parse("file://" + attachmentFile);
                cursor.close();
                uploadCount++;
                String upload_str = Integer.toString(uploadCount);
                uploads.setText("Documents Uploaded " + upload_str);
            }
        }

        @Override
        public void onClick(View v) {

            if (v == btnAttachment) {
                openGallery();

            }
            if (v == btnSend) {
                try {
                    email = "ainy.afzal@mail.utoronto.ca";
                    subject = "QuickHealth Verify Doctor";
                    message = "Verify uploaded documents and click link to verify the doctor: <link>";

                    final Intent emailIntent = new Intent(
                            android.content.Intent.ACTION_SEND);
                    emailIntent.setType("plain/text");
                    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                            new String[] { email });
                    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                            subject);
                    if (URI != null) {
                        emailIntent.putExtra(Intent.EXTRA_STREAM, URI);
                    }
                    emailIntent
                            .putExtra(android.content.Intent.EXTRA_TEXT, message);
                    this.startActivity(Intent.createChooser(emailIntent,
                            "Sending email..."));

                } catch (Throwable t) {
                    Toast.makeText(this,
                            "Request failed try again: " + t.toString(),
                            Toast.LENGTH_LONG).show();
                }
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
