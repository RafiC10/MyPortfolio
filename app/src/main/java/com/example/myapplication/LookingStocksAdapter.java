package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LookingStocksAdapter extends RecyclerView.Adapter<LookingStocksAdapter.LLookViewHolder> {
    private ArrayList<LookingStock> look;
    private View.OnClickListener onItemClickListener1;

    public LookingStocksAdapter(ArrayList<LookingStock> look){
        this.look = look;
    }

    @NonNull
    @Override
    public LLookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View lookView = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.recyletlook,parent,false);
        return new LLookViewHolder((lookView));
    }

    public void setOnItemClickListener1(View.OnClickListener onItemClickListener1) {
        this.onItemClickListener1 =onItemClickListener1;
    }

    @Override
    public void onBindViewHolder(@NonNull LLookViewHolder holder, int position) {
        LookingStock currentStock = look.get(position);
        holder.NameLookStock.setText(currentStock.getName());
        holder.PriceNowLookStock.setText(String.valueOf(currentStock.getPriceNow()));
    }
    @Override
    public int getItemCount() { return look.size(); }


    public class LLookViewHolder extends RecyclerView.ViewHolder{
        public TextView NameLookStock;
        public TextView PriceNowLookStock;
        public LLookViewHolder(@NonNull View itemView) {
            super(itemView);
            NameLookStock =itemView.findViewById(R.id.TvNameStockLook);
            PriceNowLookStock =itemView.findViewById(R.id.tvPriceCurrentLook);
            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener1);

        }}
















}

