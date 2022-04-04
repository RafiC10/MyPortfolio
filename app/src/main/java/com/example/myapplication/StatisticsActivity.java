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

public class StatisticsActivity extends AppCompatActivity {
    private PieChart pieChart;
    FirebaseDatabase database;
    int totalOfStocksNow =0;
    int totalOfStockThen = 0;
    DatabaseReference myRef,myRefStock;
    ArrayList<PieEntry> entries = new ArrayList<PieEntry>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        pieChart = findViewById(R.id.graphInStatisc);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");
        myRefStock = database.getReference("ToInvest");
        readFromDataStock();
    }
    private void readFromDataStock() {
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        myRefStock.child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    InvestStock investStock = ds.getValue(InvestStock.class);

                    entries.add(new PieEntry((float) (investStock.getTotalWorthOfStock()), investStock.getName()));
                    totalOfStocksNow +=(investStock.priceNow *investStock.getAmount());
                    totalOfStockThen += (investStock.getBuyingPrice() * investStock.getAmount());
                }
                setupPieChart();
                loadPieChartData();
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    private void loadPieChartData() {

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries ,  "");
        dataSet.setColors(colors);
        dataSet.setValueTextSize(45);
        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(25);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

    }
    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(0);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("All of the stocks : " + totalOfStocksNow +"$" );
        pieChart.setCenterTextSize(25);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setTextSize(60);
        l.setDrawInside(true);
        l.setEnabled(true);

    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuport)
        {
            startActivity(new Intent(StatisticsActivity.this, PortActivity2.class));
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