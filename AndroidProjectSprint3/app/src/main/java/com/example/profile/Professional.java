package com.example.profile;

import org.json.JSONException;
import org.json.JSONObject;

public class Professional extends User{

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
}
