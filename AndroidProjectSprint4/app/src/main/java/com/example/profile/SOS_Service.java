package com.example.profile;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import static com.example.profile.App.CHANNEL_ID;

public class SOS_Service extends Service {
    public static final String CHANNEL3_ID = "sosAServiceChannel";
    public static final String CHANNEL4_ID = "sosBServiceChannel";
    public static final String CHANNEL5_ID = "chatServiceChannel";
    private HandlerThread handlerThread;
    private Handler handler;
    private Looper looper;
    private boolean isRunning;
    private Socket lSocket;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        this.isRunning = false;
        handlerThread = new HandlerThread("SOSHandlerThread");
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
                lSocket.on("SOSaccepted", sosListener);
                lSocket.on("MESSAGE",onNewMessage);


                // establish the socket connection

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL3_ID)
                .setContentTitle("SoS Service")
                .setContentText("awaiting doctodr response")
                .setSmallIcon(R.drawable.circle)
                .setContentIntent(pendingIntent)
                .build();

        if( !this.isRunning) {
            this.isRunning = true;
            this.handler.post(myTask);
        }

        startForeground(1,notification);
        return START_STICKY;


    }


    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL3_ID,
                    "SOS Service Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );



            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    private void createNotificationChannel2(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL4_ID,
                    "SOS Service Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );



            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    private void createNotificationChannel3(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL5_ID,
                    "CHAT Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );



            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    public void sosNotification (String Username){
        createNotificationChannel2();


        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL4_ID)
                .setContentTitle("SOS CALL ACCEPTED")
                .setContentText( Username + " has accepted your sos call come chat!")
                .setSmallIcon(R.drawable.circle)
                .setContentIntent(pendingIntent)
                .build();

        NotificationManager amanager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        amanager.notify(2,notification);

    }

    public void sosNotification2( String msg){
        createNotificationChannel3();


        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL5_ID)
                .setContentTitle("CHAT MESSAGE FROM DOCTOR")
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
                sosNotification(data.getString("doctorID"));
                User.setacceptedDoctor(data.getString("doctorID"));
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


    };
}
