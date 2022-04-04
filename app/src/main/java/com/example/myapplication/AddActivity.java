package com.example.myapplication;



import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnCheckDataInAdd, btnAddToPortfolioInAdd;
    Double TheRealPrice = -0.2;
    Boolean EveryThingIsFine = false;
    EditText NameOfStock, AmountOfStock, BuyingPriceOfStock, ComissionOfStock;
    private FirebaseDatabase FirebaseDatabase;
    private DatabaseReference MyRefToUsers, MyRefToStocks;
    private FirebaseAuth mAuth;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        NameOfStock = findViewById(R.id.tvTextNameOfStockInAdd);
        AmountOfStock = findViewById(R.id.tvTextAmountInAdd);
        BuyingPriceOfStock = findViewById(R.id.tvTextByingPriceInAdd);
        ComissionOfStock = findViewById(R.id.tvTextComissionInAdd);
        btnAddToPortfolioInAdd = (Button) findViewById(R.id.btnAddToPortfolioInAdd);
        btnAddToPortfolioInAdd.setOnClickListener(this);
        btnCheckDataInAdd = (Button) findViewById(R.id.btnCheckDataInAdd);
        btnCheckDataInAdd.setOnClickListener(this);
        FirebaseDatabase = FirebaseDatabase.getInstance();
        MyRefToUsers = FirebaseDatabase.getReference("Users");
        mAuth = FirebaseAuth.getInstance();


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuport) {
            startActivity(new Intent(AddActivity.this, PortActivity2.class));
        }
        if (item.getItemId() == R.id.menustatics) {
            startActivity(new Intent(AddActivity.this, StatisticsActivity.class));
        }
        if (item.getItemId() == R.id.menulook) {
            startActivity(new Intent(AddActivity.this, LookActivity.class));
        }
        return true;
    }


    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        if (btnCheckDataInAdd == view && AmountOfStock.getText().length() > 0 && BuyingPriceOfStock.getText().length() > 0 && ComissionOfStock.getText().length() > 0) {
            String ap = "https://financialmodelingprep.com/api/v3/quote-short/" + NameOfStock.getText().toString() + "?apikey=d477f4211cca3f702244eaf9a9539b0d";
            DownLoadText t = new DownLoadText();
            t.execute(ap.toString());
        }
        else if (btnCheckDataInAdd == view && (AmountOfStock.getText().length() == 0 || BuyingPriceOfStock.getText().length() == 0 || ComissionOfStock.getText().length() == 0)) {
            Toast.makeText(AddActivity.this, "יש בעיה בנתונים", Toast.LENGTH_LONG).show();
        }
        if (btnAddToPortfolioInAdd == view && EveryThingIsFine == true) {
            double amountdouble = Double.valueOf(AmountOfStock.getText().toString());
            double buyingPricedo = Double.valueOf(BuyingPriceOfStock.getText().toString());
            double amlado = Double.valueOf(ComissionOfStock.getText().toString());
            FirebaseUser currentUser = mAuth.getCurrentUser();
            MyRefToStocks = FirebaseDatabase.getReference("ToInvest").child(currentUser.getUid()).push();
            InvestStock investStock = new InvestStock(NameOfStock.getText().toString(), buyingPricedo, amountdouble, amlado, TheRealPrice, (double) (amountdouble * TheRealPrice), MyRefToStocks.getKey());
            MyRefToStocks.setValue(investStock);
            Intent intent = new Intent(this, PortActivity2.class);
            startActivity(intent);
        }
        if (btnAddToPortfolioInAdd == view && EveryThingIsFine != true) {
            Toast.makeText(AddActivity.this, "אתה צריך ללחוץ על אישור", Toast.LENGTH_LONG).show();
        }
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
            String EditResult = result;
            String[] parts = EditResult.split("volume");
            EditResult = parts[0];
            EditResult = EditResult.replaceAll("[^\\d.]", "");
            if (!EditResult.isEmpty()) {
                TheRealPrice = Double.valueOf(EditResult);
                EveryThingIsFine = true;
                Toast.makeText(AddActivity.this, "עכשיו אתה יכול ללחוץ על הוספה", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(AddActivity.this, "שם מנייה לא נכון", Toast.LENGTH_LONG).show();
            }
        }

    }

}