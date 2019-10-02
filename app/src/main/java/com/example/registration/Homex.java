package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Homex extends AppCompatActivity implements View.OnClickListener {
    TextView logout, profile;
    CardView event, workshop;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    String userKey = "";
    FirebaseUser user;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homex);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        logout = (TextView) findViewById(R.id.logout);
        progressDialog = new ProgressDialog(this);
        event = (CardView) findViewById(R.id.event);
        profile = (TextView)findViewById(R.id.profile);
        workshop = (CardView) findViewById(R.id.workshop);
        getWindow().setBackgroundDrawableResource(R.drawable.home1);
        logout.setOnClickListener(this);
        event.setOnClickListener(this);
        workshop.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        final String emailID = user.getEmail();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot userSnapshot: dataSnapshot.getChildren()){
                    userKey =userSnapshot.getKey();
                    if(!userKey.isEmpty()) {
                        if ((userSnapshot.child("email").getValue().toString()).equals(emailID)) {
                            profile.setText(userSnapshot.child("name").getValue().toString());
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View v){
        if(v==profile){
            Intent iprofile = new Intent(Homex.this,HomeActivity.class);
            startActivity(iprofile);
        }
        if(v==logout){
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(Homex.this,MainActivity.class);
                progressDialog.setMessage("Logging out...");
                progressDialog.show();
                startActivity(i);
            }
        if(v==event){
                Intent ievent = new Intent(Homex.this,EventActivity.class);
                startActivity(ievent);
            }
        if(v==workshop){
                Intent iworkshop = new Intent(Homex.this, WorkshopActivity.class);
                startActivity(iworkshop);
            }
    }
}