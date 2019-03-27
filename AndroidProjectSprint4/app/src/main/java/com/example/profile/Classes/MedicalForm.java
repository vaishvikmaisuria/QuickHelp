package com.example.profile.Classes;

public class MedicalForm {

    private String username;
    private MedChanges medicalInfo;

    public MedicalForm(String user){
        this.username = user;
        this.medicalInfo = new MedChanges();
    }

}


