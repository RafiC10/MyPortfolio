package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver
{//יReceiver השולט על כיבוי והדלקה של הוייפי

    WifiManager wifimanager;//מידע  על הוויפי

    @Override
    public void onReceive(Context context, Intent intent) {
        //בודק אם יש או אין אינטרנט ולפי כך מתאים את כפתור הסוייץ' במידה ואין הוא גם ישלח התראה למשתמש
       wifimanager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        int wifiStateExtra =intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,WifiManager.WIFI_STATE_UNKNOWN);
        switch (wifiStateExtra){
            case WifiManager.WIFI_STATE_ENABLED:
                PortfolioActivity.wifiSwitch.setChecked(true);
                PortfolioActivity.wifiSwitch.setText("וויפי דלוק");
                break;
            case WifiManager.WIFI_STATE_DISABLED:
                PortfolioActivity.wifiSwitch.setChecked(false);
              PortfolioActivity.wifiSwitch.setText("וייפי כבוי");
                Toast.makeText(context, "אין אינטרנט אנא הדלק", Toast.LENGTH_SHORT).show();
                break;
        }

    }

}