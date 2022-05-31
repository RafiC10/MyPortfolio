package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * The type Portfolio activity.
 */
public class PortfolioActivity extends AppCompatActivity implements FirebaseCallback {
    /**
     * The constant worthOfTheStockNow.
     */
//מחלקת מסך אשר אחרית על דף המניות להשקעה של היוזר והצגת המניות להשקעה,שווי התיק ושינוי כללי של התיק
    static TextView worthOfTheStockNow, /**
     * The Genral change.
     */
    GenralChange;//נתוני שווי תיק נוכחי ושיוני כללי של התיק
    /**
     * The Name e.
     */
    EditText nameE, /**
     * The Price e.
     */
    priceE, /**
     * The Amount e.
     */
    amountE, /**
     * The Comission e.
     */
    comissionE;//נתונים בשביל הדיאלוג
    /**
     * The Dialog.
     */
    Dialog dialog;//הדיאלוג של המידע של המנייה
    /**
     * The On item click listener.
     */
    OnClickListener onItemClickListener;//ליסינר ללחיצה
    /**
     * The Invest.
     */
    ArrayList<InvestStock> invest = new ArrayList<>();//יArrayList שבו נשמרים המניות להשקעה של המשתמש
    /**
     * The Recycler view of invest.
     */
    RecyclerView recyclerViewOfInvest;//יRecyclerView שבו יוצגו המניות להשקעה
    /**
     * The Invest stocks adapter.
     */
    InvestStocksAdapter investStocksAdapter;//יAdapter לRecyclerView
    /**
     * The Wifi switch.
     */
    static Switch wifiSwitch;//כפתור סוויץ' שבעזרתו מכבה ומדליק וייפי
    /**
     * The Wifimanager.
     */
    static WifiManager wifimanager;//יWifiManager על מנת שיהיה אפשר לכבות ולהדליק וייפי
    /**
     * The Key of invest stock.
     */
    String keyOfInvestStock;//סטרינג של מנייה של מנייה שאני לוחץ עליה על מנת שיהיה אפשר למחוק אותה
    /**
     * The My receiver.
     */
    MyReceiver myReceiver;//יReceiver שמכבה ומדליק את הוייפי ושולח על כך התראה
    /**
     * The Intent.
     */
    static Intent intent;//יIntent שמעובר לService עם מצב האינטרנרט
    /**
     * The Firebase controller.
     */
    FirebaseController firebaseController;//הפנייה לfirebaseController על מנת להשיג את הנתונים מהדאטהבייס


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_port2);
        worthOfTheStockNow = findViewById(R.id.worthOfTheStockNowInPort2);
        GenralChange = findViewById(R.id.tvChangeGenralChangeInPort2);
        intent = getIntent();
        myReceiver = new MyReceiver();
        DownLoadData.EveryThingIsFine=false;
        wifimanager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiSwitch = (Switch) findViewById(R.id.wifi_switch);
        wifiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//בודק אם הוייפי עובד ולי כך משנה את הכפתור
                if (isChecked) {
                    wifimanager.setWifiEnabled(true);
                    wifiSwitch.setText("וייפי דלוק");
                } else {
                    wifimanager.setWifiEnabled(false);
                    wifiSwitch.setText("וויפי כבוי");
                }
            }
        });
        firebaseController = new FirebaseController(this);
        firebaseController.readDataInvest(this, invest);
        onItemClickListener = new OnClickListener() {
            public void onClick(View view) {//תגובה ללחיצה על הRecyclerView שפןתח את הדילאוג ושולח לו נתנוים על איזה מנייה נלחצה
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                int poisition = viewHolder.getAdapterPosition();
                InvestStock userItem = invest.get(poisition);
                keyOfInvestStock = userItem.getKey();
                createInfromationDialog(invest, poisition);
            }
        };
    }
    public void onCallbackLook(ArrayList<LookingStock> look) {
    }
    @Override
    public void onCallbackInvest(ArrayList<InvestStock> invest) {//פומקציית ממשק מקבלת נתונים מהפיירבייס ומציגה אותם ברייסקיל ויו
        recyclerViewOfInvest = findViewById(R.id.recyclerOfInvestStocks);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewOfInvest.setLayoutManager(layoutManager);
        investStocksAdapter = new InvestStocksAdapter(invest);
        recyclerViewOfInvest.setAdapter(investStocksAdapter);
        investStocksAdapter.setOnItemClickListener(onItemClickListener);
    }
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter((WifiManager.WIFI_STATE_CHANGED_ACTION));
        registerReceiver(myReceiver, intentFilter);
    }
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(myReceiver);
    }

    public boolean onCreateOptionsMenu(Menu menu) {//מראה את ה menu
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {//מעביר מסכים לפי ה menu
        if (item.getItemId() == R.id.menustatics) {
            startActivity(new Intent(PortfolioActivity.this, StatisticsActivity.class)); }
        if (item.getItemId() == R.id.menuadd) {
            startActivity(new Intent(PortfolioActivity.this, AddActivity.class)); }
        if (item.getItemId() == R.id.menulook) {
            startActivity(new Intent(PortfolioActivity.this, LookActivity.class)); }
        return true; }

    /**
     * Create infromation dialog.
     *
     * @param invest    the invest
     * @param poisition the poisition
     */
    public void createInfromationDialog(ArrayList<InvestStock> invest, int poisition) {
        //RecyclerViewיצירת דיאלוג אשר יראה את פרטי המנייה שנלחצה ב
        InvestStock userItem = invest.get(poisition);
        keyOfInvestStock = userItem.getKey();
        DownLoadData.EveryThingIsFine=false;
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_stock1);
        Button btnCheckDataInD = dialog.findViewById(R.id.btntos);//כפתור בדיקת נתונים
        Button btnSaveInD = dialog.findViewById(R.id.btnsave1);//כפתור עידכון
        Button btnDeleteInD = dialog.findViewById(R.id.btndelete1);//כפתור מחיקת מנייה
        nameE = dialog.findViewById(R.id.etsockedit);//שם המנייה ( ניתן לעריכה)
        priceE = dialog.findViewById(R.id.buying_priceedit);//מחיר המנייה ( ניתן לעריכה)
        amountE = dialog.findViewById(R.id.amountedit);//כמות המניות ( ניתן לעריכה)
        comissionE = dialog.findViewById(R.id.comissionedit);//עמלת המנייה ( ניתן לעריכה)
        nameE.setText(String.valueOf(userItem.getName()));
        priceE.setText(String.valueOf(userItem.getBuyingPrice()));
        amountE.setText(String.valueOf(userItem.getAmount()));
        comissionE.setText(String.valueOf(userItem.getComission()));
        dialog.setCancelable(true);
        dialog.show();
        btnCheckDataInD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //אם נלחץ על ביקת נתונים תועבר בקשה לקבל מחיר המנייה אחרי בדיקה שכל הנתונים הגיונים
                if (btnCheckDataInD == v && nameE.getText().length() > 0 && amountE.getText().length() > 0 && priceE.getText().length() > 0 && comissionE.getText().length() > 0) {
                    String ap = "https://financialmodelingprep.com/api/v3/quote-short/" + nameE.getText().toString() + "?apikey=d477f4211cca3f702244eaf9a9539b0d";
                    DownLoadData t = new DownLoadData(PortfolioActivity.this);
                    t.execute(ap.toString());
                } else if (btnCheckDataInD == v && (amountE.getText().length() == 0 || priceE.getText().length() == 0 || comissionE.getText().length() == 0)) {
                    Toast.makeText(PortfolioActivity.this, "יש בעיה בנתונים", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnDeleteInD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//כפתןר אשר שלוח בקשה למחיקת המנייה
                FirebaseController.deleteInvestStock(keyOfInvestStock);
                startActivity(new Intent(PortfolioActivity.this, PortfolioActivity.class));
                DownLoadData.EveryThingIsFine=false;
            }
        });
            btnSaveInD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//בכפתור אשר ימחק את המנייה וייצר אחת חדשה עם הנתנים המעודכנים אחרי שווידא שהפרטים נכונים
                if (btnSaveInD == v && DownLoadData.EveryThingIsFine == true) {
                    FirebaseController.deleteInvestStock(keyOfInvestStock);
                    FirebaseController.addToDatabaseToInvest(nameE.getText().toString(), Double.valueOf(priceE.getText().toString()), Double.valueOf(amountE.getText().toString()), Double.valueOf(comissionE.getText().toString()));
                    startActivity(new Intent(PortfolioActivity.this, PortfolioActivity.class));
                    DownLoadData.EveryThingIsFine=false;
                }
                if (btnSaveInD == v && DownLoadData.EveryThingIsFine != true) {
                    //במידה ולא לחץ כל בדיקת נתנונים ישלח התראה שתגיד על כך
                    Toast.makeText(PortfolioActivity.this, "אתה צריך ללחוץ על בדיקת נתונים", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
