package com.simcoder.bimbo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Driver;
import java.util.ArrayList;
import java.util.Locale;

public class ProductStore extends AppCompatActivity {


    EditText search_edit_text;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;

    FirebaseUser FirebaseUser;


    ArrayList<String> productID;
    ArrayList<String> productName;
    ArrayList<String>productImage;
    ArrayList<String>productTime;
    ArrayList<String>CategoryName;
    ArrayList<String> TraderName;
    ArrayList<String>TraderID;
    ArrayList<String>Ratings;
    ProductSearchAdapter ProductSearchAdapter;


    public ProductStore() {
        super();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.productstore);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView = (RecyclerView)findViewById(R.id.myrecyclerview);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        productID = new ArrayList<>();
        productName = new ArrayList<>();
        productImage= new ArrayList<>();
        productTime = new ArrayList<>();
        CategoryName = new ArrayList<>();
        TraderName = new ArrayList<>();
        TraderID = new ArrayList<>();
        Ratings = new ArrayList<>();




        search_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                   setAdapter(s.toString());

                }
            }
        });



    }
           String traderImages;
            private void setAdapter(final  String searchedString){

   databaseReference.child("Product").addListenerForSingleValueEvent(new ValueEventListener() {
       @Override
       public void onDataChange(DataSnapshot dataSnapshot) {

           int counter =0;
       for (DataSnapshot snapshot: dataSnapshot.getChildren()){
       String productID = snapshot.getKey();

           String productNames = snapshot.child(productID).child("productName").getValue(String.class);
           String productImages = snapshot.child(productID).child("productImage").getValue(String.class);
           String categoryIDs= snapshot.child(productID).child("categoryID").getValue(String.class);
           String categoryNames = snapshot.child(productID).child(categoryIDs).child("categoryName").getValue(String.class);
           String productTimes = snapshot.child(productID).child("productTime").getValue(String.class);
           String traderIDs = snapshot.child(productID).child("traderID").getValue(String.class);
           final DatabaseReference traderImage = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(traderIDs).child("profileImageUrl");
           traderImage.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                   traderImages =  dataSnapshot.getValue().toString();
               }

               @Override
               public void onCancelled(DatabaseError databaseError) {

               }
           });

           String traderNames = snapshot.child(productID).child("traderName").getValue(String.class);


           if (productName.contains(searchedString)){

               productName.add(productNames);
               productImage.add(productImages);
               CategoryName.add(categoryNames);
               productTime.add(productTimes);
               TraderName.add(traderNames);
               TraderID.add(traderIDs);
               counter++;
           }
           else if((productName.contains(searchedString))){

               productName.add(productNames);
               productImage.add(productImages);
               CategoryName.add(categoryNames);
               productTime.add(productTimes);
               TraderName.add(traderNames);
               TraderID.add(traderIDs);
               counter++;
           }

           if (counter ==15){
                break;

           }

            ProductSearchAdapter = new ProductSearchAdapter(ProductStore.this, traderPic , productImage,  productID,   productName, productImage,   productTime, ArrayList<String>categoryName, ArrayList<String> TraderName,  ArrayList<String>TraderID,   ArrayList<String>Ratings,productPrice)
    }}

       @Override
       public void onCancelled(DatabaseError databaseError) {

       }
   });}}

