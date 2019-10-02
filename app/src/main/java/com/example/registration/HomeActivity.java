package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {
    Button logout;
    TextView eid, uname, age, stream, phone;
    Toolbar tb;
    String userKey = "";
    private FirebaseAuth mAuth;
    FirebaseUser user;
    private ProgressDialog progressDialog;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.home3);
        setContentView(R.layout.activity_home);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        tb = findViewById(R.id.app_bar);
        setSupportActionBar(tb);
        uname = (TextView) findViewById(R.id.t1);
        eid = (TextView)findViewById(R.id.t2);
        phone = (TextView)findViewById(R.id.t3);
        stream = (TextView)findViewById(R.id.t4);
        age = (TextView)findViewById(R.id.t5);
        logout = (Button)findViewById(R.id.logout);
        progressDialog = new ProgressDialog(this);


        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        final String emailID = user.getEmail();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot userSnapshot: dataSnapshot.getChildren()){
                    userKey = userSnapshot.getKey();
                    if(!userKey.isEmpty()){
                        if((userSnapshot.child("email").getValue().toString()).equals(emailID)){
                            uname.setText(userSnapshot.child("name").getValue().toString());
                            eid.setText(userSnapshot.child("email").getValue().toString());
                            phone.setText(userSnapshot.child("phone").getValue().toString());
                            stream.setText(userSnapshot.child("stream").getValue().toString());
                            age.setText(userSnapshot.child("age").getValue().toString());
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        if(mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        }
        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(HomeActivity.this,MainActivity.class);
                progressDialog.setMessage("Logging out...");
                progressDialog.show();
                startActivity(i);
            }
        });
    }
}
