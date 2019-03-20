package com.example.profile;

import org.json.JSONObject;

public class User {
    static public JSONObject User1;

    public User(){
        // all users have potential to become a professional
        Professional Doctor1 = new Professional(User1);
    }

    public static JSONObject getUser(){
        return User1;
    }
    public static void  setUser(JSONObject x){
        User1 = x;
    }
}
