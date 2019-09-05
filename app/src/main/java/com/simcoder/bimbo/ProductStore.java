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


    ArrayList<String> productIDs;
    ArrayList<String> productName;
    ArrayList<String>productPic;
    ArrayList<String>productTime;
    ArrayList<String>CategoryName;
    ArrayList<String> TraderName;
    ArrayList<String>TraderID;
    ArrayList<String>TraderPic;
    ArrayList<String>Ratings;
    ArrayList<String>ProductPrice;

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

        productIDs = new ArrayList<>();
        productName = new ArrayList<>();
        productPic= new ArrayList<>();
        productTime = new ArrayList<>();
        CategoryName = new ArrayList<>();
        TraderName = new ArrayList<>();
        TraderID = new ArrayList<>();
        TraderPic = new ArrayList<>();
        Ratings = new ArrayList<>();
        ProductPrice = new ArrayList<>();



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

                }else{
                    productIDs.clear();
                    productName.clear();
                    productPic.clear();
                    productTime.clear();
                    CategoryName.clear();
                    TraderName.clear();
                    TraderID.clear();
                    TraderPic.clear();
                    Ratings.clear();
                    ProductPrice.clear();
                    recyclerView.removeAllViews();

                }
            }
        });



    }
           String traderImages;
            private void setAdapter(final  String searchedString){


                databaseReference.child("Product").addListenerForSingleValueEvent(new ValueEventListener() {
       @Override
       public void onDataChange(DataSnapshot dataSnapshot) {
          // clears the list for every brand new search



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
                   traderImages = dataSnapshot.getValue().toString();
               }

               @Override
               public void onCancelled(DatabaseError databaseError) {

               }
           });
           String traderNames = snapshot.child(productID).child("traderName").getValue(String.class);

           if (productNames.toLowerCase().contains(searchedString)){

               productName.add(productNames);
               productPic.add(productImages);
               CategoryName.add(categoryNames);
               productTime.add(productTimes);
               TraderName.add(traderNames);
               TraderID.add(traderIDs);
               counter++;
           }
           else if((productNames.toLowerCase().contains(searchedString))){

               productName.add(productNames);
               productPic.add(productImages);
               CategoryName.add(categoryNames);
               productTime.add(productTimes);
               TraderName.add(traderNames);
               TraderID.add(traderIDs);
               counter++;
           }

           if (counter ==15){
                break;
// IN OTHER ACTIVITIES WE CAN SET THE COUNTER AND THEN SWITCH TO ANOTHER ADAPTER TO POPULATE AND WE CAN BUILD MORE ADAPTERS IF THERE EXISTS SOME
               // BUT I NEED TO UNDERSTAND MORE ON THE UI FRAMEWORK , BECAUSE THE COMPLEXITY MAKES IT INNOVATIVE
               // NO ORDINARY CODES, ///
           }

            ProductSearchAdapter = new ProductSearchAdapter(ProductStore.this, TraderPic , productPic,  productIDs,   productName,    productTime, CategoryName, TraderName,TraderID,Ratings,ProductPrice );
              recyclerView.setAdapter(ProductSearchAdapter);
     }}

       @Override
       public void onCancelled(DatabaseError databaseError) {

       }
   });}}

