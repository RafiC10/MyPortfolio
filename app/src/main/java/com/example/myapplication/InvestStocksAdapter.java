package com.example.myapplication;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class InvestStocksAdapter extends RecyclerView.Adapter<InvestStocksAdapter.InvestLookViewHolder> {
    private ArrayList<InvestStock>invest;
    private View.OnClickListener onItemClickListener;

    public InvestStocksAdapter(ArrayList<InvestStock> invest){
        this.invest = invest;

    }

    @NonNull
    @Override
    public InvestLookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View lookView = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.ecycletocks,parent,false);
        return new InvestLookViewHolder((lookView));
    }

    public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
        this.onItemClickListener =onItemClickListener ;
    }

    @Override
    public void onBindViewHolder(@NonNull InvestLookViewHolder holder, int position) {
        InvestStock currentStock = invest.get(position);
        holder.NameStock.setText( currentStock.getName());
        holder.PriceNowStock.setText(String.valueOf(currentStock.getPriceNow()));
        holder.GenerallyChngeStock.setText(String.valueOf( (int) (((currentStock.getPriceNow()/currentStock.getBuyingPrice())-1)*100)) + "%");
        if ( (int) (((currentStock.getPriceNow()/currentStock.getBuyingPrice())-1)*100)>0){
            holder.GenerallyChngeStock.setTextColor(Color.parseColor("#07D500"));
        }
        else {
            holder.GenerallyChngeStock.setTextColor(Color.parseColor("#CA0314"));
        }


    }

    @Override
    public int getItemCount() {
        return invest.size();
    }

    public class InvestLookViewHolder extends RecyclerView.ViewHolder{
        public TextView NameStock;
        public TextView PriceNowStock;
        public TextView GenerallyChngeStock;
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
