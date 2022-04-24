package com.example.myapplication;


import java.io.Serializable;
import java.util.ArrayList;

public class User {
    private String nameUser;
    private String gmail;
    private ArrayList<InvestStock> ToInvest;
    private ArrayList<LookingStock>toLook;

    public User() {
    }



    public User(ArrayList<LookingStock> toLook, ArrayList<InvestStock> toInvest, String nameUser, String gmail) {
        this.nameUser = nameUser;
        this.gmail = gmail;
        ToInvest = toInvest;
        toLook = toLook;

    }

    public User(String nameUser, String gmail) {
        this.nameUser = nameUser;
        this.gmail = gmail;

    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public ArrayList<InvestStock> getToInvest() {
        return ToInvest;
    }

    public void setToInvest(ArrayList<InvestStock> toInvest) {
        ToInvest = toInvest;
    }

    public ArrayList<LookingStock> getToLook() {
        return toLook;
    }

    public void setToLook(ArrayList<LookingStock> toLook) {
        this.toLook = toLook;
    }

}
