package com.example.profile;

import org.json.JSONException;
import org.json.JSONObject;

public class Professional extends User{

    public Professional (JSONObject user){
        // first time professional is created, they are uncertified
        try {
            user.put("professional",false);

        }catch (JSONException e){
        }
    }

    /*
        when the user is verified as a professional, their professional status can be set
     */
    public static void  setProfessional(JSONObject user){

        try {
            user.put("professional",true);
        }catch (JSONException e){
            return;
        }
    }
    public static Boolean  isProfessional(JSONObject user){
        try {
            return (Boolean)user.get("professional");

        }catch (JSONException e){
            return false;
        }

    }
}
