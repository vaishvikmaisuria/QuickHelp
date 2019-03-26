package com.example.profile;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;

import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;


import org.json.JSONObject;
import java.util.ArrayList;




public class ChatFragment extends Fragment implements View.OnClickListener {

    private ListView itemlist;
    private EditText writtenMessage;
    private ImageButton sendMsg;
    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;
    private Boolean hasConnection = false;
    private Socket mSocket;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.activity_chat, container, false);

        sendMsg = v.findViewById(R.id.sendMessageButton);
        itemlist = v.findViewById(R.id.messageListView);
        writtenMessage = v.findViewById(R.id.messageTextField);


        if(!hasConnection){
            try{
                mSocket = GSocket.getInstance();

                mSocket.on("MESSAGE", onNewMessage);
                JSONObject q = new JSONObject();
                q.put("username",User.User1.getString("username"));
                mSocket.emit("USER_LOGIN",q);
                hasConnection = Boolean.TRUE;
            } catch (Exception e) {}
        }

        sendMsg.setOnClickListener(this);

        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,User.items);
        itemlist.setAdapter(adapter);

        return v;

    }



    public  ChatFragment() {}

    @Override
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.sendMessageButton: {
                try {
                    String userId = User.User1.get("username").toString();
                    String message = writtenMessage.getText().toString();
                    User.items.add(userId + ":" + " " + message);
                    JSONObject sendObj = new JSONObject();
                    sendObj.put("username",userId);
                    sendObj.put("message",message);
                    mSocket.emit("MESSAGE",sendObj);
                }catch(Exception e){
                    e.printStackTrace();
                }
                writtenMessage.setText("");
                adapter.notifyDataSetChanged();
                break;
            }





        }
    }


    Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject data = (JSONObject) args[0];
                        String userId = data.getString("username");
                        String message = data.getString("message");
                        User.items.add(userId + ":" + " " + message);
                        adapter.notifyDataSetChanged();

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    Emitter.Listener onNewUser = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

        }
    };

}