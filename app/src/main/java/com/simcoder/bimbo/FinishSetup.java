package com.simcoder.bimbo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
;

public class FinishSetup extends AppCompatActivity {

    private ImageButton AboutImage;
    private EditText AboutTitle;
    private EditText AboutDescription;





    private DatabaseReference mDatabase;

    private ProgressDialog mProgress;
    private FirebaseAuth Auth;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mDatabaseUser;
    String DriverID;
    EditText setpassportno;
    EditText setoperation;
    EditText setdescription;
    EditText setresidence;
    TextView setinformation;
    TextView setofficelocationinfo;


    public FinishSetup() {
        super();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finishsetup);


        Auth = FirebaseAuth.getInstance();
        mCurrentUser = Auth.getCurrentUser();

        DriverID = mCurrentUser.getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Driver");
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child("Driver").child(DriverID);


        setpassportno = (EditText) findViewById(R.id.setpassportno);
        setoperation = (EditText) findViewById(R.id.setoperation);
        setdescription = (EditText) findViewById(R.id.setdescription);
        setresidence = (EditText) findViewById(R.id.setresidence);
        setinformation = (TextView) findViewById(R.id.setinformation);
        setofficelocationinfo = (TextView) findViewById(R.id.setofficelocationinfo);


        FloatingActionButton setfloatingbutton = (FloatingActionButton) findViewById(R.id.setfloatingbutton);


        mProgress = new ProgressDialog(this);


        setfloatingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }
        });

    }


    private void startPosting() {
        final String setpassportnos = setpassportno.getText().toString();
        final String setoperations = setoperation.getText().toString();
        final String setdescriptions = setdescription.getText().toString();
        final String setresidences = setresidence.getText().toString();


        if (!TextUtils.isEmpty(setpassportnos) && !TextUtils.isEmpty(setoperations) && !TextUtils.isEmpty(setoperations) && !TextUtils.isEmpty(setdescriptions) && !TextUtils.isEmpty(setresidences)) {
            mProgress.setMessage("SETTING YOU UP SHORTLY");

            mProgress.show();

            final DatabaseReference newPost = mDatabase.push();


            mDatabaseUser.addValueEventListener(new ValueEventListener() {
                @Override

                public void onDataChange(final DataSnapshot dataSnapshot) {

                    newPost.child("passportno.").setValue(setpassportnos);
                    newPost.child("operations").setValue(setoperations);

                    newPost.child("descriptions").setValue(setdescriptions);

                    newPost.child("residences").setValue(setresidences).addOnCompleteListener(new OnCompleteListener<Void>() {


                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                startActivity(new Intent(FinishSetup.this, DriverMapActivity.class));

                            }
                        }
                    });


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            mProgress.dismiss();


        }
    }


}





