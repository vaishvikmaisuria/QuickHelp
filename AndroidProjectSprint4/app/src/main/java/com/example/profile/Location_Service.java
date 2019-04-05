package com.example.profile;



import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import java.net.URISyntaxException;

import io.reactivex.annotations.Nullable;

import static com.example.profile.App.CHANNEL_ID;

public class Location_Service extends Service {
    private static final String TAG = "Location_Service";
    private HandlerThread handlerThread;
    private Handler handler;
    private Looper looper;
    private LocationListener listener;
    private LocationManager locationManager;
    private int x = 20000;
    private boolean isRunning;
    public static final String CHANNEL2_ID = "sosServiceChannel";
    public static final String CHANNEL6_ID = "chatBServiceChannel";
    private Socket lSocket;

    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public void onCreate() {
        this.isRunning = false;
        handlerThread = new HandlerThread("MyHandlerThread");
        handlerThread.start();
        looper = handlerThread.getLooper();
        handler = new Handler(looper);

    }

    private Runnable myTask = new Runnable() {
        @SuppressLint("MissingPermission")
        @Override
        public void run() {
            // do something in here
            Log.i("INFO", "SOCKET BACKGROUND SERVICE IS RUNNING");

            try {
                // instantiate socket connection
                lSocket = GSocket.getInstance();
                lSocket.on("SOS",sosListener);
                lSocket.on("MESSAGE",onNewMessage);

                // establish the socket connection

            } catch (Exception e) {
                e.printStackTrace();
            }



        }
    };

    @Override
    public void onDestroy() {
        this.isRunning = false;
        if(locationManager != null){
            locationManager.removeUpdates(listener);
        }
        User.onS = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Location Service")
                .setContentText("Tracking your location")
                .setSmallIcon(R.drawable.circle)
                .setContentIntent(pendingIntent)
                .build();

        if( !this.isRunning) {
            this.isRunning = true;
            this.handler.post(myTask);
        }
        User.onS = true;

        startForeground(1,notification);

        return START_STICKY;
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL2_ID,
                    "SOS Service Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );



            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    public void sosNotification (String Username){
        createNotificationChannel();


        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL2_ID)
                .setContentTitle("SOS CALL")
                .setContentText( Username + " needs help Check alerts for more details")
                .setSmallIcon(R.drawable.circle)
                .setContentIntent(pendingIntent)
                .build();

        NotificationManager amanager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        amanager.notify(2,notification);

    }


    private void createNotificationChannel3(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL6_ID,
                    "CHAT Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );



            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    public void sosNotification2( String msg){
        createNotificationChannel3();


        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL6_ID)
                .setContentTitle("CHAT MESSAGE FROM PATIENT")
                .setContentText(msg)
                .setSmallIcon(R.drawable.ic_chat_icon2)
                .setContentIntent(pendingIntent)
                .build();

        NotificationManager amanager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        amanager.notify(2,notification);

    }


    Emitter.Listener sosListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            try {
                JSONObject data = (JSONObject) args[0];
                sosNotification(data.getString("username"));
                User.setacceptedUser(data.getString("firstName"));
            }catch (Exception e) {}
        }
    };


    Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

                try {
                    JSONObject data = (JSONObject) args[0];
                    String userId = data.getString("from");
                    String message = data.getString("message");
                    User.items.add(userId + ":" + " " + message);
                    sosNotification2(userId + ":" + " " + message);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }



    };





}
