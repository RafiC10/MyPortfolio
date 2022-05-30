package com.example.myapplication;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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
public class AddActivity extends AppCompatActivity implements View.OnClickListener
{//מחלקת מסך אשר אחרית על הוספת מניות להשקעה
    EditText NameOfStock,BuyingPriceOfStock, AmountOfStock, ComissionOfStock;//מילוי פרטי המנייה (שם,מחיר קנייה,כמות,עמלה)
    Button btnCheckDataInAdd;//כפתור בדיקת נתונים
    Button btnAddToPortfolioInAdd;//כפתור הוספה



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
    }
    public boolean onCreateOptionsMenu(Menu menu) {//הצגת ה menu
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true; }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {//מעביר מסכים לפי ה menu
        if (item.getItemId() == R.id.menuport) {
            startActivity(new Intent(AddActivity.this, PortfolioActivity.class)); }
        if (item.getItemId() == R.id.menustatics) {
            startActivity(new Intent(AddActivity.this, StatisticsActivity.class)); }
        if (item.getItemId() == R.id.menulook) {
            startActivity(new Intent(AddActivity.this, LookActivity.class));}
        return true; }

    @Override
    public void onClick(View view) {
        //תגובה ללחיצה אם נלחץ בדיקת נתונים אז הוא שולח בקשה לקבל מחיר המנייה
        // אם הכל עובד טוב היוזר יקבל התראה על כך שהוא יכול ללחוץ על הוספה
        // במידה ולא היוזר יקבל התראה על כך שיש בעיה בנתונים
        if (btnCheckDataInAdd == view && AmountOfStock.getText().length() > 0 && BuyingPriceOfStock.getText().length() > 0 && ComissionOfStock.getText().length() > 0) {
            String ap = "https://financialmodelingprep.com/api/v3/quote-short/" + NameOfStock.getText().toString() + "?apikey=d477f4211cca3f702244eaf9a9539b0d";
            DownLoadData t = new DownLoadData(AddActivity.this);
            t.execute(ap.toString());
        }
        else if (btnCheckDataInAdd == view && (AmountOfStock.getText().length() == 0 || BuyingPriceOfStock.getText().length() == 0 || ComissionOfStock.getText().length() == 0)) {
            Toast.makeText(AddActivity.this, "יש בעיה בנתונים", Toast.LENGTH_LONG).show();
        }
        if (btnAddToPortfolioInAdd == view && DownLoadData.EveryThingIsFine == true) {
            //אם הכל היה נכון והיוזר לחץ על הופסה תיתוסף המנייה והיוזר יועבר למסך החשבון
            FirebaseController.addToDatabaseToInvest(NameOfStock.getText().toString(), Double.valueOf(BuyingPriceOfStock.getText().toString()), Double.valueOf(AmountOfStock.getText().toString()), Double.valueOf(ComissionOfStock.getText().toString()));
            Intent intent = new Intent(this, PortfolioActivity.class);
            startActivity(intent);
        }
        if (btnAddToPortfolioInAdd == view && DownLoadData.EveryThingIsFine!= true) {
            //אם היוזר לחץ על הוספה לפני שלחץ על בדיקת נתונים הוא יקבל על כך התראה
            Toast.makeText(AddActivity.this, "אתה צריך ללחוץ על אישור", Toast.LENGTH_LONG).show();
        }
    }

}