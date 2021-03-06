package com.example.myapplication;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 * The type Invest stocks adapter.
 */
public class InvestStocksAdapter extends RecyclerView.Adapter<InvestStocksAdapter.InvestLookViewHolder> {
    //בנייה וסידור של ה RecyclerView של מניות להשקעה בדף החשבון
    private ArrayList<InvestStock>invest;//מערך המניות להשקעה של המשתמש
    private View.OnClickListener onItemClickListener;//ליסינר ללחיצה על מנת שיהיה ניתן לפתוח דיאלוג מידע

    /**
     * Instantiates a new Invest stocks adapter.
     *
     * @param invest the invest
     */
    public InvestStocksAdapter(ArrayList<InvestStock> invest){
        this.invest = invest;
    }//גודל

    /**
     * Sets on item click listener.
     *
     * @param onItemClickListener the on item click listener
     */
    public void setOnItemClickListener(View.OnClickListener onItemClickListener) { this.onItemClickListener =onItemClickListener ; }
    public int getItemCount() { return invest.size(); }
    @NonNull
    @Override
    public InvestLookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //יצירת ViewHolder
        View lookView = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.ecycletocks,parent,false);
        return new InvestLookViewHolder((lookView));
    }
    @Override
    public void onBindViewHolder(@NonNull InvestLookViewHolder holder, int position) {
        //הצבת הנתונים של המניות במקום התאים להם ושינוי צבע לפי הגדרה האם הם עלוו או ידרו
        InvestStock currentStock = invest.get(position);
        holder.NameStock.setText(currentStock.getName());
        holder.PriceNowStock.setText(String.valueOf(currentStock.getPriceNow()));
        holder.GenerallyChngeStock.setText(String.valueOf( (int) (((currentStock.getPriceNow()/currentStock.getBuyingPrice())-1)*100)) + "%");
        if ( (int) (((currentStock.getPriceNow()/currentStock.getBuyingPrice())-1)*100)>0){
            holder.GenerallyChngeStock.setTextColor(Color.parseColor("#07D500"));
        }
        else {
            holder.GenerallyChngeStock.setTextColor(Color.parseColor("#CA0314"));
        }
    }

    /**
     * The type Invest look view holder.
     */
    public class InvestLookViewHolder extends RecyclerView.ViewHolder{
        /**
         * The Name stock.
         */
//הגדרת הנתונים של ה RecyclerView ואפשרות ללחיצה עליהם
            public TextView NameStock;//שם מנייה
        /**
         * The Price now stock.
         */
        public TextView PriceNowStock;//שווי מניה נוכחי
        /**
         * The Generally chnge stock.
         */
        public TextView GenerallyChngeStock;//שינוי כללי

        /**
         * Instantiates a new Invest look view holder.
         *
         * @param itemView the item view
         */
        public InvestLookViewHolder(@NonNull View itemView) {
            super(itemView);
            NameStock=itemView.findViewById(R.id.TvNameStockInRecycler);
            PriceNowStock=itemView.findViewById(R.id.tvPriceCurrentInRecycler);
            GenerallyChngeStock=itemView.findViewById(R.id.tvChangeGenralChangeInRecycler);
            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);
        }
    }
}
