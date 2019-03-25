package com.example.profile;

import org.json.JSONObject;

public class sosData {

    static public String sos1;
    static public JSONObject Usersos;
    static public Double locationlong;
    static public Double locationlat;

    public static JSONObject getUsersos() {
        return Usersos;
    }

    public static void setUsersos(JSONObject usersos) {
        Usersos = usersos;
    }

    public static Double getLocationlong() {
        return locationlong;
    }

    public static Double getLocationlat() {
        return locationlat;
    }

    public static void setLocationlong(Double locationlong) {
        sosData.locationlong = locationlong;
    }

    public static void setLocationlat(Double locationlat) {
        sosData.locationlat = locationlat;
    }

    public static String getSos1(){
        return sos1;
    }
    public static void  setUser(String x){
        sos1 = x;
    }

}


