package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;

/**
 * The type Statistics activity.
 */
public class StatisticsActivity extends AppCompatActivity {
    /**
     * The Pie chart.
     */
    PieChart pieChart;//גרף העוגה עצמו
    /**
     * The Total of stocks now.
     */
    int totalOfStocksNow =0;//שווי מניות נוכחי
    /**
     * The My ref stock invest.
     */
    DatabaseReference myRefStockInvest;//הפניות לדאטה בייס על מנת לייבא משם את נתוני המניות לתוך הגרף
    /**
     * The Entries.
     */
    ArrayList<PieEntry> entries = new ArrayList<>();//יArrayList של המניות בצורה שיהיה ניתן להשיג בגרף העוגה


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        pieChart = findViewById(R.id.graphInStatisc);
        myRefStockInvest = FirebaseDatabase.getInstance().getReference("ToInvest");
        readFromDataStock();
    }
    private void readFromDataStock() {//פעולה אשר קוראת הנתונים מהדאטה בייס ומציבה אותם ב entries וחישוב שווי המניות הנוכחי
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        myRefStockInvest.child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    InvestStock investStock = ds.getValue(InvestStock.class);
                    entries.add(new PieEntry((float) (investStock.getTotalWorthOfStock()), investStock.getName()));
                    totalOfStocksNow +=(investStock.getTotalWorthOfStock());
                }
                loadPieChartData();

            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    private void loadPieChartData() {//בניית גרף העוגה עצמו וכל הנתנוים מסביב ועיצובם
        Legend l = pieChart.getLegend();//הנתנוים למעלה (שמות המניות)
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);//מיקומם
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setTextSize(60);
        l.setDrawInside(true);
        l.setEnabled(true);


        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {//צבעים עבור הגרף
            colors.add(color);
        }
        PieDataSet dataSet = new PieDataSet(entries ,  "");
        dataSet.setColors(colors);
        dataSet.setValueTextSize(85);
        PieData data = new PieData(dataSet);//נתוני המניות (האחוזים)
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(25);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);//בניית הגרף עצמו והבאת נתונים אליו
        pieChart.invalidate();
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(0);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("All of the stocks   " + totalOfStocksNow +"$" );//הכיתוב באמצע
        pieChart.setCenterTextSize(26);
        pieChart.getDescription().setEnabled(false);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//הצגת ה menu
        getMenuInflater().inflate(R.menu.menu1,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {//מעביר מסכים לפי ה menu
        if (item.getItemId() == R.id.menuport)
        {
            startActivity(new Intent(StatisticsActivity.this, PortfolioActivity.class));
        }
        if (item.getItemId() == R.id.menuadd)
        {
            startActivity(new Intent(StatisticsActivity.this, AddActivity.class));
        }
        if (item.getItemId() == R.id.menulook) {
            startActivity(new Intent(StatisticsActivity.this, LookActivity.class));
        }
        return true;
    }}