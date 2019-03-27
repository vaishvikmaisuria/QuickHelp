package com.example.profile;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

public class GSocket {
    static public Socket instance;


    public static Socket getInstance(){
        if (instance == null){
            try {
                instance = IO.socket("https://quickhelp-chat.herokuapp.com");
                instance.connect();
                return instance;
            } catch (Exception e) {

            }
        }
        return instance;
    }
}