package com.example.profile;

import org.json.JSONObject;

import java.util.ArrayList;

public class User {
    static public JSONObject User1;
    static public String acceptedUser = null;
    static public ArrayList<String> items = new ArrayList<>();
    static public String acceptedDocter = null;
    static public boolean onS = false;

    static public String latti1 = "holy";

    static public String longi1 = "holy";

    public static String getLatti() {
        return latti1;
    }

    public static String getLongi() {
        return longi1;
    }

    public static void setLatti(String latti) {
        latti1 = latti;
    }

    public static void setLongi(String longi) {
        longi1 = longi;
    }

    public User(){
        // all users have potential to become a professional
    }

    public static JSONObject getUser(){
        return User1;
    }
    public static void  setUser(JSONObject x){
        User1 = x;
    }
    public static void  setacceptedUser(String x ){acceptedUser = x;}
    public static void  setacceptedDoctor(String x ){acceptedDocter = x;}


}
