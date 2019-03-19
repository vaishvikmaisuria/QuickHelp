package com.example.profile;

public class sosRequest {

    private int id;
    private String Header;
    private String Desc;


    public sosRequest(int id, String header, String desc) {
        this.id = id;
        Header = header;
        Desc = desc;
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
