package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    WifiManager wifimanager;


    @Override
    public void onReceive(Context context, Intent intent) {
       wifimanager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        int wifiStateExtra =intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,WifiManager.WIFI_STATE_UNKNOWN);
        switch (wifiStateExtra){
            case WifiManager.WIFI_STATE_ENABLED:
                PortActivity2.wifiSwitch.setChecked(true);
                PortActivity2.wifiSwitch.setText("וויפי דלוק");

                break;
            case WifiManager.WIFI_STATE_DISABLED:
                PortActivity2.wifiSwitch.setChecked(false);
              PortActivity2.wifiSwitch.setText("וייפי כבוי");
                Toast.makeText(context, "אין אינטרנט אנא הדלק", Toast.LENGTH_SHORT).show();
                break;
        }

    }

}