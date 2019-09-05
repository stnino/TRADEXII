package com.simcoder.bimbo;

import android.content.Context;
import android.content.Intent;
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

    ArrayList<String>productTime;
    ArrayList<String>categoryName;
    ArrayList<String> TraderName;
    ArrayList<String> productPrice;
    ArrayList<String>TraderID;
    ArrayList<String>Ratings;
    ArrayList<String>traderPic;
    ArrayList<String>productPic;




    public ProductSearchAdapter(Context context,  ArrayList<String>traderPic, ArrayList<String>productPic, ArrayList<String> productID,  ArrayList<String> productName,    ArrayList<String>productTime, ArrayList<String>categoryName, ArrayList<String> TraderName,  ArrayList<String>TraderID,   ArrayList<String>Ratings,ArrayList<String> productPrice) {
        this.context = context;
         this.productID = productID ;
         this.productName= productName ;

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
          ImageView TraderPichere ;
          ImageView productPichere;
          TextView ProductNamehere;
          // Also product of product Category
          TextView productCategoryhere;
          TextView productPricehere;
          TextView productTimehere;
          TextView TraderIDhere;

          TextView TraderNamehere;
          TextView Ratingshere;
          TextView productIDhere;


         public ProductSearchViewHolder(@NonNull View itemView) {

             super(itemView);
              TraderPichere =(ImageView)itemView.findViewById(R.id.traderPicture);
              productPichere =(ImageView)itemView.findViewById(R.id.productPicss) ;
              ProductNamehere = (TextView)itemView.findViewById(R.id.productnamegiven);
             // Also product of product Category
              productCategoryhere = (TextView)itemView.findViewById(R.id.displaycategory);
              productPricehere = (TextView)itemView.findViewById(R.id.productprice);
              productTimehere = (TextView)itemView.findViewById(R.id.productTime);
              TraderIDhere = (TextView)itemView.findViewById(R.id.traderIDgiven);

              TraderNamehere =(TextView)itemView.findViewById(R.id.tradername);
              Ratingshere = (TextView)itemView.findViewById(R.id.ratings);
              productIDhere = (TextView)itemView.findViewById(R.id.productIDhere);
         }

    }

    @NonNull
    @Override

    public ProductSearchAdapter.ProductSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.productsearchlayout, parent, false);



            return  new ProductSearchAdapter.ProductSearchViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ProductSearchViewHolder productSearchViewHolder,final int i) {



                   productSearchViewHolder.productCategoryhere.setText(categoryName.get(i));
                   productSearchViewHolder.ProductNamehere.setText(productName.get(i));
                   productSearchViewHolder.productPricehere.setText(productPrice.get(i));
                   productSearchViewHolder.productTimehere.setText(productTime.get(i));
                   productSearchViewHolder.TraderNamehere.setText(TraderName.get(i));
                   productSearchViewHolder.Ratingshere.setText(Ratings.get(i));
                   productSearchViewHolder.productIDhere.setText(productID.get(i));

                    productSearchViewHolder.TraderIDhere.setText(TraderID.get(i));






        String productimageposition =  productPic.get(i);
        Glide.with(context).load(productimageposition).thumbnail(0.5f).into(productSearchViewHolder.productPichere);

        String traderimageposition =  traderPic.get(i);
        Glide.with(context).load(traderimageposition).thumbnail(0.5f).into(productSearchViewHolder.TraderPichere);




        productSearchViewHolder.productPichere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent passpictures = new Intent(context ,ViewProductActivity.class);
                passpictures.putExtra("",categoryName.get(i));
                passpictures.putExtra("",productName.get(i) );
                passpictures.putExtra("",productPrice.get(i) );
                passpictures.putExtra("", TraderName.get(i));
                passpictures.putExtra("", Ratings.get(i));
                passpictures.putExtra("", TraderID.get(i));
                passpictures.putExtra("", productID.get(i));


            }
        });
    }



    @Override
    public int getItemCount() {
        return 0;
    }
}
