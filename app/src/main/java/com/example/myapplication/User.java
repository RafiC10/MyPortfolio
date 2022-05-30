package com.example.myapplication;


import java.io.Serializable;
import java.util.ArrayList;

public class User {
    //עצם מסוג יוזר אשר יש לו שם שם, כתובת מייל,ArrayList של מניות לצפייה ושל מניות להשקעה
    private String nameUser;//שם של משתמש
    private String gmail;//כתובת מייל
    private String password;//סיסמה



    public User(String nameUser, String gmail, String password) {
        //בנאי
        this.nameUser = nameUser;
        this.gmail = gmail;
        this.password=password;

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
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
