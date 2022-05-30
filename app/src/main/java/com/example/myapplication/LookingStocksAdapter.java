package com.example.myapplication;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LookingStocksAdapter extends RecyclerView.Adapter<LookingStocksAdapter.LLookViewHolder> {
    //בנייה וסידור של ה RecyclerView של מניות להשקעה בדף המניות לצפייה
    private ArrayList<LookingStock> look;//מערך המניות לצפייה של המשתמש
    private View.OnClickListener onItemClickListener;//ליסינר ללחיצה על מנת שיהיה ניתן לפתוח דיאלוג מידע

    public LookingStocksAdapter(ArrayList<LookingStock> look){
        this.look = look;
    }
    public int getItemCount() { return look.size(); }
    public void setOnItemClickListener1(View.OnClickListener onItemClickListener1) {
        this.onItemClickListener=onItemClickListener1; }
    @NonNull
    @Override
    public LLookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //יצירת ViewHolder
        View lookView = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.recyletlook,parent,false);
        return new LLookViewHolder((lookView)); }


    @Override
    public void onBindViewHolder(@NonNull LLookViewHolder holder, int position) {
        //הצבת הנתונים במקומם
        LookingStock currentStock = look.get(position);
        holder.NameLookStock.setText(currentStock.getName());
        holder.PriceNowLookStock.setText(String.valueOf(currentStock.getPriceNow()));
    }
    public class LLookViewHolder extends RecyclerView.ViewHolder{
    //הגדרת הנתונים של ה RecyclerView ואפשרות ללחיצה עליהם
        public TextView NameLookStock //שם המנייה
                ,PriceNowLookStock;//מחיר נוכחי

        public LLookViewHolder(@NonNull View itemView) {
            super(itemView);
            NameLookStock =itemView.findViewById(R.id.TvNameStockLook);
            PriceNowLookStock =itemView.findViewById(R.id.tvPriceCurrentLook);
            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);
//            itemView.findViewById(R.id.nnnnnnnnn).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    String ap = "https://financialmodelingprep.com/api/v3/quote-short/" + NameLookStock.getText().toString() + "?apikey=d477f4211cca3f702244eaf9a9539b0d";
//                    DownLoadData t = new DownLoadData(FirebaseController.context);
//                    t.execute(ap.toString());
//                    Handler handler = new Handler();
//                    handler. postDelayed(new Runnable() {
//                        public void run() {
//                            FirebaseController.deleteLookStock();
//                            FirebaseController.addToDatabaseToLook(NameLookStock.getText().toString());
//                        }
//                    }, 5000);
//
//                }
//            });
        }}
}

