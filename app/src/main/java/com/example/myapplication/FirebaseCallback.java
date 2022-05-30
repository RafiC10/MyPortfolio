package com.example.myapplication;

import java.util.ArrayList;

public interface FirebaseCallback {
    void onCallbackLook(ArrayList <LookingStock> look);
    void onCallbackInvest (ArrayList <InvestStock> invest);
}
