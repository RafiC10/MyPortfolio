package com.example.myapplication;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.text.NoCopySpan;
import android.view.View;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/**
 * The type My service.
 */
public class MyService extends Service {
    /**
     * The constant CHANNEL_1_ID.
     */
//שולח Notification למשתמש בכל פעם שהוא מוסיף מנייה לצפייה
    //בNotification יהיה שם המנייה ומחירה הנוכחי על מנת לחסוך לו את החיפוש
    public static final String CHANNEL_1_ID = "channel1";//שם הערוץ בו נשלחים ההתראות מסוג זה
    private NotificationManagerCompat notificationManager;//Notificationאחראי על שליחת ה

    /**
     * Instantiates a new My service.
     */
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = NotificationManagerCompat.from(this);
        createNotificationChannels();

    }

    private void createNotificationChannels() {
        //פעולה אשר מעצבת את הNotification ואומרת לו להישלח דרךChannel 1
        // וגם מוסיפה תיאור ל Channel 1 על מנת שיהיה כתוב למשתמש מה הערוץ הזה שולח
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("שליחת התראות אחרי הוספה של מניות לצפייה");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);

        }
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.greengraf)
                .setContentTitle("Stock  - " + LookActivity.nameForService)
                .setContentText("Stock Price right now is - " + LookActivity.priceForService)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1, notification);

    }
}