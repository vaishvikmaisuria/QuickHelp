package com.example.profile;

public class sosRequest {

    private int id;
    private String Header;
    private String Desc;
    private String sosID;
    private Double Longitude;
    private Double Latitude;


    public sosRequest(int id, String header, String desc, String sosId, Double longitude, Double latitude) {
        this.id = id;
        Header = header;
        Desc = desc;
        sosID = sosId;
        Longitude = longitude;
        Latitude = latitude;

    }

    public Double getLongitude() {
        return Longitude;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public String getSosID() {
        return sosID;
    }
    public int getId() {
        return id;
    }

    public String getHeader() {
        return Header;
    }

    public String getDesc() {
        return Desc;
    }
}
