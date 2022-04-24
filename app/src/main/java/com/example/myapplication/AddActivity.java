package com.example.myapplication;



import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
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


public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnCheckDataInAdd, btnAddToPortfolioInAdd;
    EditText NameOfStock,BuyingPriceOfStock, AmountOfStock,  ComissionOfStock;
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
            DownLoadData t = new DownLoadData(AddActivity.this);
            t.execute(ap.toString());
        }
        else if (btnCheckDataInAdd == view && (AmountOfStock.getText().length() == 0 || BuyingPriceOfStock.getText().length() == 0 || ComissionOfStock.getText().length() == 0)) {
            Toast.makeText(AddActivity.this, "יש בעיה בנתונים", Toast.LENGTH_LONG).show();
        }
        if (btnAddToPortfolioInAdd == view && DownLoadData.EveryThingIsFine == true) {
            double amountdouble = Double.valueOf(AmountOfStock.getText().toString());
            double buyingPricedo = Double.valueOf(BuyingPriceOfStock.getText().toString());
            double amlado = Double.valueOf(ComissionOfStock.getText().toString());
            FirebaseUser currentUser = mAuth.getCurrentUser();
            MyRefToStocks = FirebaseDatabase.getReference("ToInvest").child(currentUser.getUid()).push();
            InvestStock investStock = new InvestStock(NameOfStock.getText().toString(), buyingPricedo, amountdouble, amlado, DownLoadData.TheRealPrice, (double) (amountdouble * DownLoadData.TheRealPrice), MyRefToStocks.getKey());
            MyRefToStocks.setValue(investStock);
            Intent intent = new Intent(this, PortActivity2.class);
            startActivity(intent);
        }
        if (btnAddToPortfolioInAdd == view && DownLoadData.EveryThingIsFine!= true) {
            Toast.makeText(AddActivity.this, "אתה צריך ללחוץ על אישור", Toast.LENGTH_LONG).show();
        }
    }

}