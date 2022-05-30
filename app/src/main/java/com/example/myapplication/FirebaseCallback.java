package com.example.myapplication;

import java.util.ArrayList;

/**
 * The interface Firebase callback.
 */
public interface FirebaseCallback {
    /**
     * On callback look.
     *
     * @param look the look
     */
    void onCallbackLook(ArrayList <LookingStock> look);

    /**
     * On callback invest.
     *
     * @param invest the invest
     */
    void onCallbackInvest (ArrayList <InvestStock> invest);
}
