package com.simcoder.bimbo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ProductSearchAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<String> productID;
    ArrayList<String> productName;
    ArrayList<String>productImage;
    ArrayList<String>productTime;
    ArrayList<String>CategoryName;
    ArrayList<String> TraderName;
    ArrayList<String>TraderID;
    ArrayList<String>Ratings;

    public ProductSearchAdapter(Context context, ArrayList<String> productID,  ArrayList<String> productName, ArrayList<String>productImage,   ArrayList<String>productTime, ArrayList<String>CategoryName, ArrayList<String> TraderName,  ArrayList<String>TraderID,   ArrayList<String>Ratings) {
        this.context = context;
         this.productID = productID  ;
         this.productName= productName ;
        this.productImage =productImage;
        this.productTime =productTime;
        this.CategoryName = CategoryName;
         this.TraderName =TraderName;
        this.TraderID =TraderID;
        this.Ratings =Ratings;

    }

     class ProductSearchViewHolder extends RecyclerView.ViewHolder {
         public ProductSearchViewHolder(@NonNull View itemView) {
             
             super(itemView);
         }
     }

    @NonNull
    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
