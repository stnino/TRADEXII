package com.simcoder.bimbo;

import android.content.Context;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProductSearchAdapter extends RecyclerView.Adapter<ProductSearchAdapter.ProductSearchViewHolder> {
    Context context;
    ArrayList<String> productID;
    ArrayList<String> productName;
    ArrayList<String>productImage;
    ArrayList<String>productTime;
    ArrayList<String>categoryName;
    ArrayList<String> TraderName;
    ArrayList<String> productPrice;
    ArrayList<String>TraderID;
    ArrayList<String>Ratings;
    ArrayList<String>traderPic;
    ArrayList<String>productPic;


    public ProductSearchAdapter(Context context,  ArrayList<String>traderPic, ArrayList<String>productPic, ArrayList<String> productID,  ArrayList<String> productName, ArrayList<String>productImage,   ArrayList<String>productTime, ArrayList<String>categoryName, ArrayList<String> TraderName,  ArrayList<String>TraderID,   ArrayList<String>Ratings,ArrayList<String> productPrice) {
        this.context = context;
         this.productID = productID  ;
         this.productName= productName ;
        this.productImage =productImage;
        this.productTime =productTime;
        this.productPrice = productPrice;
        this.categoryName = categoryName;
         this.TraderName =TraderName;
        this.TraderID =TraderID;
        this.Ratings =Ratings;
        this.traderPic = traderPic;
        this.productPic = productPic;

    }

     class ProductSearchViewHolder extends RecyclerView.ViewHolder {
          ImageView TraderPic;
          ImageView productPic;
          TextView ProductName;
          TextView productCategory;
          TextView productPrice;
          TextView productTime;
         TextView TraderID;
         TextView TraderName;
         TextView Ratings;


         public ProductSearchViewHolder(@NonNull View itemView) {

             super(itemView);

         }

    }

    @NonNull
    @Override

    public ProductSearchAdapter.ProductSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.productsearchlayout, parent, false);



            return  new ProductSearchAdapter.ProductSearchViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ProductSearchViewHolder productSearchViewHolder, int i) {



                   productSearchViewHolder.productCategory.setText(categoryName.get(i));
                   productSearchViewHolder.ProductName.setText(productName.get(i));
                   productSearchViewHolder.productPrice.setText(productPrice.get(i));
                   productSearchViewHolder.productTime.setText(productTime.get(i));
                   productSearchViewHolder.TraderName.setText(TraderName.get(i));
                   productSearchViewHolder.Ratings.setText(Ratings.get(i));
                   productSearchViewHolder.Ratings.setText(Ratings.get(i));



        String productimageposition =  productImage.get(i);
        Glide.with(context).load(productimageposition).thumbnail(0.5f).into(productSearchViewHolder.productPic);
    }



    @Override
    public int getItemCount() {
        return 0;
    }
}
