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

/**
 * The type Looking stocks adapter.
 */
public class LookingStocksAdapter extends RecyclerView.Adapter<LookingStocksAdapter.LLookViewHolder> {
    //בנייה וסידור של ה RecyclerView של מניות להשקעה בדף המניות לצפייה
    private ArrayList<LookingStock> look;//מערך המניות לצפייה של המשתמש
    private View.OnClickListener onItemClickListener;//ליסינר ללחיצה על מנת שיהיה ניתן לפתוח דיאלוג מידע

    /**
     * Instantiates a new Looking stocks adapter.
     *
     * @param look the look
     */
    public LookingStocksAdapter(ArrayList<LookingStock> look){
        this.look = look;
    }
    public int getItemCount() { return look.size(); }

    /**
     * Sets on item click listener 1.
     *
     * @param onItemClickListener1 the on item click listener 1
     */
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

    /**
     * The type L look view holder.
     */
    public class LLookViewHolder extends RecyclerView.ViewHolder{
        /**
         * The Name look stock.
         */
//הגדרת הנתונים של ה RecyclerView ואפשרות ללחיצה עליהם
        public TextView NameLookStock //שם המנייה
                , /**
         * The Price now look stock.
         */
        PriceNowLookStock;//מחיר נוכחי

        /**
         * Instantiates a new L look view holder.
         *
         * @param itemView the item view
         */
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

