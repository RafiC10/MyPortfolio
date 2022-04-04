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
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class PortActivity2 extends AppCompatActivity //implements View.OnClickListener
 {
    TextView worthOfTheStockNow,GenralChange;
     EditText nameE,priceE,amountE,comissionE;//for the dialog
    Boolean EveryThingIsFine =false;
    Dialog dialog;
    Double TheRealPrice = -0.2;
    double calculateWorthOfTheStockNow = 0;
    double calculateGenralChange = 0;
    FirebaseDatabase database;
    DatabaseReference MyRefToUsers, MyRefToStocks, refToDelete;
    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    OnClickListener onItemClickListener;
    ArrayList<InvestStock> invest = new ArrayList<>();
    RecyclerView recyclerViewOfInvest;
    InvestStocksAdapter investStocksAdapter;
    static Switch wifiSwitch;
    static WifiManager wifimanager;
    String keyOfInvestStock;
    MyReceiver myReceiver;
    static Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_port2);
        worthOfTheStockNow =findViewById(R.id.worthOfTheStockNowInPort2);
        GenralChange=findViewById(R.id.tvChangeGenralChangeInPort2);
        intent = getIntent();
        myReceiver = new MyReceiver();
        wifimanager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiSwitch = (Switch) findViewById(R.id.wifi_switch);
        wifiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    wifimanager.setWifiEnabled(true);
                    wifiSwitch.setText("וייפי דלוק");
                } else {
                    wifimanager.setWifiEnabled(false);
                    wifiSwitch.setText("וויפי כבוי");
                }
            }
        });
        database = FirebaseDatabase.getInstance();
        MyRefToUsers = database.getReference("Users");
        refToDelete = FirebaseDatabase.getInstance().getReference("ToInvest");
        MyRefToStocks = database.getReference("ToInvest");
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        readFromData();
        readFromDataStock();
        onItemClickListener = new OnClickListener() {
            public void onClick(View view) {
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                int poisition = viewHolder.getAdapterPosition();
                InvestStock userItem = invest.get(poisition);
                keyOfInvestStock = userItem.getKey();
                createInfromationDialog(invest, poisition);

            }
        };

        recyclerViewOfInvest = findViewById(R.id.recyclerOfInvestStocks);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewOfInvest.setLayoutManager(layoutManager);
        investStocksAdapter = new InvestStocksAdapter(invest);
        recyclerViewOfInvest.setAdapter(investStocksAdapter);
        investStocksAdapter.setOnItemClickListener(onItemClickListener);

    }
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter=new IntentFilter((WifiManager.WIFI_STATE_CHANGED_ACTION));
        registerReceiver(myReceiver,intentFilter);
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser.getUid() != null) {
            MyRefToUsers.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User currentUser = dataSnapshot.getValue(User.class);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                }
            });
        }
    }
    @Override
    protected void onStop(){
        super.onStop();
        unregisterReceiver(myReceiver);
    }

    private void readFromData() {
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        MyRefToUsers.child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menustatics) {
            startActivity(new Intent(PortActivity2.this, StatisticsActivity.class));
        }
        if (item.getItemId() == R.id.menuadd) {
            startActivity(new Intent(PortActivity2.this, AddActivity.class));
        }
        if (item.getItemId() == R.id.menulook) {
            startActivity(new Intent(PortActivity2.this, LookActivity.class));
        }

        return true;
    }

    private void readFromDataStock() {
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        MyRefToStocks.child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    InvestStock investStock = ds.getValue(InvestStock.class);
                    invest.add(new InvestStock(investStock.getName(), investStock.getBuyingPrice(), investStock.getAmount()
                            , investStock.getComission(), investStock.getPriceNow(), investStock.getTotalWorthOfStock(), investStock.getKey()));
                    recyclerViewOfInvest.setAdapter(investStocksAdapter);
                    calculateWorthOfTheStockNow += (investStock.priceNow * investStock.getAmount());
                    calculateGenralChange += (investStock.getBuyingPrice() * investStock.getAmount());
                }
                if (calculateWorthOfTheStockNow != 0 && calculateGenralChange != 0) {
                    worthOfTheStockNow.setText(String.valueOf((int) calculateWorthOfTheStockNow)+"$");
                    double wortht = (((calculateWorthOfTheStockNow / calculateGenralChange)-1)*100);
                    int aaa = ((int) calculateWorthOfTheStockNow -(int) calculateGenralChange);
                    GenralChange.setText(String.valueOf((int) wortht)+"% / " + String.valueOf(aaa)+"$");
                    if ( calculateWorthOfTheStockNow - calculateGenralChange >0){
                        GenralChange.setTextColor(Color.parseColor("#07D500"));
                    }
                    else {
                        GenralChange.setTextColor(Color.parseColor("#CA0314"));
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    public void createInfromationDialog(ArrayList<InvestStock> invest, int poisition) {
        InvestStock userItem = invest.get(poisition);
        keyOfInvestStock = userItem.getKey();
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_stock1);
        Button btnsa = dialog.findViewById(R.id.btnsave1);
        Button btnde = dialog.findViewById(R.id.btndelete1);
        Button btntos = dialog.findViewById(R.id.btntos);
        nameE = dialog.findViewById(R.id.etsockedit);
        priceE = dialog.findViewById(R.id.buying_priceedit);
        amountE = dialog.findViewById(R.id.amountedit);
        comissionE = dialog.findViewById(R.id.comissionedit);
        nameE.setText(String.valueOf(userItem.getName()));
        priceE.setText(String.valueOf(userItem.getBuyingPrice()));
        amountE.setText(String.valueOf(userItem.getAmount()));
        comissionE.setText(String.valueOf(userItem.getComission()));
        dialog.setCancelable(true);
        dialog.show();
        btntos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btntos==v&&nameE.getText().length()>0&&amountE.getText().length()>0&&priceE.getText().length()>0&&comissionE.getText().length()>0){
                String ap = "https://financialmodelingprep.com/api/v3/quote-short/" + nameE.getText().toString() + "?apikey=d477f4211cca3f702244eaf9a9539b0d";
                DownLoadText t = new DownLoadText();
                t.execute(ap.toString());
            }
                else if (btntos == v&&(amountE.getText().length()==0||priceE.getText().length()==0||comissionE.getText().length()==0)) {
                    Toast.makeText(PortActivity2.this, "יש בעיה בנתונים", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteansupdae1();
                startActivity(new Intent(PortActivity2.this, PortActivity2.class));
            }
        });
        btnsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnsa == v&& EveryThingIsFine ==true) {
                deleteansupdae1();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                MyRefToStocks = database.getReference("ToInvest").child(currentUser.getUid()).push();
                InvestStock investStock = new InvestStock(nameE.getText().toString(), Double.valueOf(priceE.getText().toString()), Double.valueOf(amountE.getText().toString()), Double.valueOf(comissionE.getText().toString()),
                        TheRealPrice, (double) (Double.valueOf(amountE.getText().toString()) * TheRealPrice), MyRefToStocks.getKey());
                MyRefToStocks.setValue(investStock);
                startActivity(new Intent(PortActivity2.this, PortActivity2.class));

            }
                if (btnsa == v&& EveryThingIsFine !=true) {
                    Toast.makeText(PortActivity2.this, "אתה צריך ללחוץ על בדיקת נתונים", Toast.LENGTH_LONG).show();

                }}
        });
    }
    private void deleteansupdae1() {

        refToDelete = FirebaseDatabase.getInstance().getReference("ToInvest").child(firebaseUser.getUid()).child(String.valueOf(keyOfInvestStock));
        refToDelete.removeValue();

    }
     class DownLoadText extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            String line = "";
            HttpURLConnection urlConnection = null;
            URL url = null;
            try {
                URL myURL = new URL(params[0]);
                URLConnection ucon = myURL.openConnection();
                InputStream in = ucon.getInputStream();
                byte[] buffer = new byte[4096];
                in.read(buffer);
                line = new String(buffer);
            } catch (Exception e) {
                line = e.getMessage();
            }
            return line;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            String s = result;
            String[] parts = s.split("volume");
            s = parts[0];
            s = s.replaceAll("[^\\d.]", "");
            if (!s.isEmpty()){
                TheRealPrice = Double.valueOf(s);
                EveryThingIsFine =true;
                Toast.makeText(PortActivity2.this, "עכשיו אתה יכול ללחוץ לחץ לעידכון" , Toast.LENGTH_LONG).show();
            }
            else  {
                Toast.makeText(PortActivity2.this, "שם מנייה לא נכון", Toast.LENGTH_LONG).show();
            }

        }

    }
}