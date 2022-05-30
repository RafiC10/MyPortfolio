package com.example.myapplication;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * The type User.
 */
public class User {
    //עצם מסוג יוזר אשר יש לו שם שם, כתובת מייל,ArrayList של מניות לצפייה ושל מניות להשקעה
    private String nameUser;//שם של משתמש
    private String gmail;//כתובת מייל
    private String password;//סיסמה


    /**
     * Instantiates a new User.
     *
     * @param nameUser the name user
     * @param gmail    the gmail
     * @param password the password
     */
    public User(String nameUser, String gmail, String password) {
        //בנאי
        this.nameUser = nameUser;
        this.gmail = gmail;
        this.password=password;

    }

    /**
     * Gets name user.
     *
     * @return the name user
     */
    public String getNameUser() {
        return nameUser;
    }

    /**
     * Sets name user.
     *
     * @param nameUser the name user
     */
    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    /**
     * Gets gmail.
     *
     * @return the gmail
     */
    public String getGmail() {
        return gmail;
    }

    /**
     * Sets gmail.
     *
     * @param gmail the gmail
     */
    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
