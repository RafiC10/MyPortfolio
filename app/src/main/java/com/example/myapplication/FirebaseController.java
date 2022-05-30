package com.example.myapplication;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The type Firebase controller.
 */
public class FirebaseController {
    /**
     * The Calculate worth of the stock now.
     */
    double calculateWorthOfTheStockNow = 0;//משתנה בשביל חישוב שווי תיק
    /**
     * The Calculate genral change.
     */
    double calculateGenralChange = 0;//משתנה בשביל חישוב שנינוי תיק
    /**
     * The Context.
     */
    static Context context;//על מנת לישלוח התראות
    private static FirebaseUser currentUser;//הפנייה לדאטה בייס משתמש נוכחי על מנת להוציא משם את הנתונים שלו

    /**
     * Instantiates a new Firebase controller.
     *
     * @param context the context
     */
    public FirebaseController(Context context) {
        this.context = context;
    }

    /**
     * Gets current user.
     *
     * @return the current user
     */
    public static FirebaseUser getCurrentUser() {
        if (currentUser == null)
             currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return currentUser;
    }

    /**
     * Add to database to look.
     *
     * @param nameOfLook the name of look
     */
    public static void addToDatabaseToLook(String nameOfLook) {//הוספת מנייה לצפייה
//        הפעולה תקבל שם תיקח את המחיר מהמשתנה הסטטי של מחיר ב DownLoadData ותיצור key למנייה
//        ותוסיף ל דfirebase ב ToLook
        DatabaseReference MyRefToStocks = (FirebaseDatabase.getInstance().getReference("ToLook")
                .child(getCurrentUser().getUid())).push();
        LookingStock lookingStock = new LookingStock(nameOfLook.toString(),DownLoadData.TheRealPrice, MyRefToStocks.getKey());
        MyRefToStocks.setValue(lookingStock);
        Toast.makeText(context, "עודכן בהצלחה!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Add to database to invest.
     *
     * @param name          the name
     * @param buyingPricedo the buying pricedo
     * @param amountdouble  the amountdouble
     * @param amlado        the amlado
     */
    public static void addToDatabaseToInvest(String name,Double buyingPricedo,Double amountdouble,Double amlado) {
        //הוספת מנייה השקעה
        //הפעולה תקבל שם,מחיר קנייה,כמות,ועמלה ותיקח את המחיר מהמשתנה הסטטי של מחיר ב DownLoadData ותיצור key למנייה
        //ותוסיף ל דfirebase ב ToInvest
        DatabaseReference MyRefToStocks = (FirebaseDatabase.getInstance().getReference("ToInvest").
                child(getCurrentUser().getUid())).push();
        InvestStock investStock = new InvestStock(name, buyingPricedo, amountdouble, amlado, DownLoadData.TheRealPrice, (double) (amountdouble * DownLoadData.TheRealPrice), MyRefToStocks.getKey());
        MyRefToStocks.setValue(investStock);
        Toast.makeText(context, "עודכן בהצלחה!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Delete look stock.
     *
     * @param keyOfLookStock the key of look stock
     */
    public static void deleteLookStock(String keyOfLookStock) {//מחיקת מנייה לצפייה על ידי יצירת הפנייה לאותו מקום לפי key שהיא מקבלת ומחיקתו
        DatabaseReference refToDelete = FirebaseDatabase.getInstance().getReference("ToLook")
                .child(getCurrentUser().getUid()).child(String.valueOf(keyOfLookStock));
        refToDelete.removeValue();
        Toast.makeText(context, "נמחק בהצלחה!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Delete invest stock.
     *
     * @param keyOfLookStock the key of look stock
     */
    public static void deleteInvestStock(String keyOfLookStock) {//מחיקת מנייה להשקעה על ידי יצירת הפנייה לאותו מקום לפי key שהיא מקבלת ומחיקתו
        DatabaseReference refToDelete = FirebaseDatabase.getInstance().getReference("ToInvest")
                .child(getCurrentUser().getUid()).child(String.valueOf(keyOfLookStock));
        refToDelete.removeValue();
        Toast.makeText(context, "נמחק בהצלחה!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Read data look.
     *
     * @param firebaseallback the firebaseallback
     * @param look            the look
     */
    public void readDataLook(FirebaseCallback firebaseallback,ArrayList<LookingStock> look) {
        //מעבר על מניות להשקעה של משתמש והעברת למערךוסידורם לפי סדר אלפאביתי המערך יועבר למשתמש באמתעות ה ממשק של FirebaseCallback
        FirebaseDatabase.getInstance().getReference("ToLook").child(getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    LookingStock lookingStock = ds.getValue(LookingStock.class);
                    look.add(new LookingStock(lookingStock.getName(),lookingStock.getPriceNow(),lookingStock.getKey()));
                    firebaseallback.onCallbackLook(look);
                    }
                Collections.sort(look, new Comparator<LookingStock>() {
                    @Override
                    public int compare(LookingStock lookingStock, LookingStock t1) {
                        return lookingStock.name.compareToIgnoreCase(t1.name);
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    /**
     * Read data invest.
     *
     * @param firebaseallback the firebaseallback
     * @param invest          the invest
     */
    public void readDataInvest(FirebaseCallback firebaseallback,ArrayList<InvestStock> invest) {
        FirebaseDatabase.getInstance().getReference("ToInvest").child(getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    InvestStock investStock = ds.getValue(InvestStock.class);
                    invest.add(new InvestStock(investStock.getName(), investStock.getBuyingPrice(), investStock.getAmount(),investStock.getComission()
                            , investStock.getPriceNow(), investStock.getTotalWorthOfStock(), investStock.getKey()));
                    firebaseallback.onCallbackInvest(invest);
                    calculateWorthOfTheStockNow += (investStock.priceNow * investStock.getAmount());//חישוב שווי כל המניות כרגע
                    calculateGenralChange += (investStock.getBuyingPrice() * investStock.getAmount());//חישוב שווי כל המניות בקנייתם
                }
                Collections.sort(invest, new Comparator<InvestStock>() {
                    @Override
                    public int compare(InvestStock investStock, InvestStock t1) {
                        return investStock.name.compareToIgnoreCase(t1.name);
                    }
                });
                if (calculateWorthOfTheStockNow != 0 && calculateGenralChange != 0) {//בדיקה שיש בכלל מניות ועידכון הנתונים של שווי התיק ושינויו וצביעתם בירוק/אדום
                    PortfolioActivity.worthOfTheStockNow.setText(String.valueOf((int) calculateWorthOfTheStockNow) + "$");
                    double wortht = (((calculateWorthOfTheStockNow / calculateGenralChange) - 1) * 100);
                    int aaa = ((int) calculateWorthOfTheStockNow - (int) calculateGenralChange);
                    PortfolioActivity.GenralChange.setText(String.valueOf((int) wortht) + "% / " + String.valueOf(aaa) + "$");
                    if (calculateWorthOfTheStockNow - calculateGenralChange > 0) {
                        PortfolioActivity.GenralChange.setTextColor(Color.parseColor("#07D500"));
                    } else {
                        PortfolioActivity.GenralChange.setTextColor(Color.parseColor("#CA0314"));
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }
}

