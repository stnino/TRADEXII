package com.simcoder.bimbo;
import com.google.gson.JsonObject;
import com.simcoder.bimbo.historyRecyclerView.HistoryAdapter;
import com.simcoder.bimbo.historyRecyclerView.HistoryObject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.simcoder.tradex.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//THIS ACTIVITY IS SHOWN AFTER THE TRIP HAS BEEN DONE.IT THEN POPS UP AND SAYS
//THIS IS THE DETAILS , THIS IS THE PRICE, NOW PAY! BOTH CUSTOMER AND DRIVER OR IN OUR CASE USER SEE IT TOO
public class HistoryActivity extends AppCompatActivity {

    // HERE IN A SIMPLIFIED VIEW HE OR SHE CAN GET TO SEE ALL THE HISTORY OF HIS OR HER TRANSACTIONS WHETHER HE IS A CUSTOMER OR A DRIVER/SELLER
    // THIS IS JUST A SIMPLE HISTORY

    // BUT WHY IS THE PAYMENT HERE?
    //BECAUSE IF HE IS A CUSTOMER, HE WILL HAVE TO PAY
    private String customerOrDriver, userId;

    private RecyclerView mHistoryRecyclerView;
    private RecyclerView.Adapter mHistoryAdapter;
    private RecyclerView.LayoutManager mHistoryLayoutManager;

    private TextView mBalance;

    private Double Balance = 0.0;
    private Button mPayout;
    private EditText mPayoutEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mBalance = findViewById(R.id.balance);
        mPayout =findViewById(R.id.payout);
        mPayoutEmail = findViewById(R.id.payoutEmail);





        mHistoryRecyclerView = findViewById(R.id.historyRecyclerView);
        mHistoryRecyclerView.setNestedScrollingEnabled(false);
        mHistoryRecyclerView.setHasFixedSize(true);
        mHistoryLayoutManager = new LinearLayoutManager(HistoryActivity.this);
        mHistoryRecyclerView.setLayoutManager(mHistoryLayoutManager);
        mHistoryAdapter = new HistoryAdapter(getDataSetHistory(), HistoryActivity.this);
        mHistoryRecyclerView.setAdapter(mHistoryAdapter);


        customerOrDriver = getIntent().getExtras().getString("customerOrDriver");
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        getUserHistoryIds();
         // IF IT IS THE DRIVER THE BALANCE SHOULD BE VISIBLE
        if(customerOrDriver.equals("Drivers")){
            mBalance.setVisibility(View.VISIBLE);
        }
      mPayout.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              payoutRequest();
          }
      });
    }

     ProgressDialog progressDialog;

    // IF CUSTOMERORDRIVER == "CUSTOMER ",
    // THERE SHOULD BE A PAYOUT BUTTON VISIBLE, ELSE THE PAYOUT BUTTON SHOULD NOT BE,
    //WHY SHOULD I PAY FOR MY OWN RIDE
    private void payoutRequest() {
       progressDialog = new ProgressDialog(this);
       progressDialog.setTitle("Processing your payout");
       progressDialog.setMessage("Please wait");
       progressDialog.setCancelable(false);
       progressDialog.show();

       final OkHttpClient client = new OkHttpClient();
        JsonObject postData = new JsonObject();
        try{
            postData.put("uid", FirebaseAuth.getInstance().getUid());
            postData.put("email", mPayoutEmail.getText().toString());

        }
        catch (Exception e){
           e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType, postData.toString());
            //WE WILD DEAL WITH THE PAYMENT AND URL STUFF
         final Request request = new Request.Builder().url("https://us0centrall=uberapp-408c8.cloudfunctions.net/payout").post(body).
                 addHeader("Content-Type", "application/json")
                 .addHeader("cache-control", "no-cache")
                 .addHeader("Authorization", "Your Token")
                 .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               int responseCode = response.code();
               if (response.isSuccessful()){

                    switch (responseCode){
                        case 200:
                            Snackbar.make(findViewById(R.id.layout), "Payout Successful", Snackbar.LENGTH_LONG).show();
                           break;
                        case 500:
                            Snackbar.make(findViewById(R.id.layout), "Payout Successful", Snackbar.LENGTH_LONG).show();
                            break;
                        default:
                            Snackbar.make(findViewById(R.id.layout), "Payout Successful", Snackbar.LENGTH_LONG).show();
                            break;

                    }
               }

              progressDialog.dismiss();
            }


        });

    }
      // BUT WE ARE RECORDING TRIP DETAILS RIGHT
     // WHAT IS THIS THING DOING
    //IS IT GETTING HISTORIES OF EVERYTHING ..HOW DOES IT DIFFER FROM HISTORYSINGLE ACTIVITY

    private void getUserHistoryIds() {
        DatabaseReference userHistoryDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(customerOrDriver).child(userId).child("history");
       //OKAY SO IT IS POPULATING THE WHOLE HISTORY AND SHOWING ALL THE HISTORY OF THAT CUSTOMER OR USER HERE

        userHistoryDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot history : dataSnapshot.getChildren()){

                        if (history.getKey() !=null)
                           // HISTORY HAS TRUE TO IT, HMM..THIS IS A BIG PROBLEM
                            //SO WE MUST ADD THE HISTORY OF THE DETAILS HERE SO CUSTOMER CAN PULL FROM
                            //WHICH LEADS US TO FETCH RIDE INFORMATION

                        FetchRideInformation(history.getKey());
                            //I DONT THINK IT SHOULD QUERY BASED ON HISTORY KEY, IT SHOULD QUERY ON HISTORY CUSTOMERID OR DRIVER ID
                        // SO IT IS (HISTORY).(CUSTOMER/DRIVER).(CUSTOMERID/DRIVERID)

                        //WE WILL USE THE HISTORY.GETKEY WHEN REQUIRING TO MAKE PARITICULAR PAYMENT
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void FetchRideInformation(String rideKey) {

        //WAIT ...THE PROBLEM IS THAT.. IT CHECKS FOR THE CUSTOMER OR DRIVER AT GETUSERHISTORYIDS AND SINCE THERY ARE BOTH THE SAME KEY, IT RETRIEVES THE INFORMATION FROM THERE,
        //IT BE BETTER TO PUT HISTORY IN ONE LOCATION. ONLY HISTORY SINGLE ACTIVITY CAN ANSWER MY QUESTION

             //IT SHOULD NOT BE RIDE KEY, IT SHOULD BE CUSTOMER ID , SINCE WE ARE QUERYING BASED ON CUSTOMER REQUEST

         DatabaseReference historyDatabase = FirebaseDatabase.getInstance().getReference().child("history").child(rideKey);
        historyDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    //RIDEKEY ==RIDE_ID
                    //BUT WHERE DID HE PASS IT TO HISTORY SINGLE ACTIVITY

                    String rideId = dataSnapshot.getKey();
                    Long timestamp = 0L;
                    String distance = "";
                    Double ridePrice = 0.0;
                    Double productPrice =0.0;
                     String ProductType ="";
                     String ProductName ="";
                     Double DiscountRate = 0.0;
                     int DistanceperDollar =0;
                     int  DistanceperTime = 0;

                    // I have to add goods prices here Double Agreed Prices

                    if(dataSnapshot.child("timestamp").getValue() != null){
                        timestamp = Long.valueOf(dataSnapshot.child("timestamp").getValue().toString());
                    }

                    if(dataSnapshot.child("customerPaid").getValue() != null && dataSnapshot.child("driverPaidOut").getValue() == null){

                        if(dataSnapshot.child("distance").getValue() != null){
                            distance = dataSnapshot.child("distance").getValue().toString();

                            ridePrice = (Double.valueOf(dataSnapshot.child("price").getValue().toString()) * 0.4);
                            //I HAVE TO GET THE PRICES, PRODUCT TYPE AND PRODUCT NAME
                            Balance += ridePrice;
                            Balance += productPrice;
                            Balance -= DiscountRate;
                          int  distancefare=   Integer.parseInt(distance);
                            DistanceperDollar = distancefare/1;
                               String Dollarpertrip = String.valueOf(DistanceperDollar);



                            // I have to add the goods prices to balance
                            mBalance.setText("Balance: " + String.valueOf(Balance) + " $");

                       //WE WILL JUST ADD PRICES AND ALL THAT PRODUCT TO MAKE IT MORE BETTER
                            // this will be added to the value to make the product

                        }
                    }


                    HistoryObject obj = new HistoryObject(rideId, getDate(timestamp));
                    resultsHistory.add(obj);
                    mHistoryAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
     private String getDate(Long time) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(time*1000);
        String date = DateFormat.format("MM-dd-yyyy hh:mm", cal).toString();
        return date;
    }

    private ArrayList resultsHistory = new ArrayList<HistoryObject>();
    private ArrayList<HistoryObject> getDataSetHistory() {
        return resultsHistory;
    }


}
