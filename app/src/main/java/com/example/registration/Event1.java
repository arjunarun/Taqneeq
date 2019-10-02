package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Event1 extends AppCompatActivity {
    EditText name, email, phone;
    Button register;
    TextView logout;
    FirebaseAuth mAuth;
    DatabaseReference databaseUser1, databaseUser2;
    private ProgressDialog progressDialog;
    String userKey= "";
    FirebaseUser user;
    String emailx, namex, phonex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event1);
        getWindow().setBackgroundDrawableResource(R.drawable.home1);
        register = (Button)findViewById(R.id.reg);

        /*name = (EditText)findViewById(R.id.n);
        email = (EditText)findViewById(R.id.e);
        phone = (EditText)findViewById(R.id.p);*/

        mAuth = FirebaseAuth.getInstance();
        databaseUser1 = FirebaseDatabase.getInstance().getReference("laser");
        databaseUser2 = FirebaseDatabase.getInstance().getReference("users");

        logout = (TextView)findViewById(R.id.logout);

        progressDialog = new ProgressDialog(this);
        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(Event1.this,MainActivity.class);
                progressDialog.setMessage("Logging out...");
                progressDialog.show();
                startActivity(i);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();
                user = FirebaseAuth.getInstance().getCurrentUser();
                final String emailID = user.getEmail();

                databaseUser2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot: dataSnapshot.getChildren()){
                            userKey = userSnapshot.getKey();
                            if(!userKey.isEmpty()){
                                emailx = userSnapshot.child("email").getValue().toString();
                                namex = userSnapshot.child("name").getValue().toString();
                                phonex = userSnapshot.child("phone").getValue().toString();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                String id = databaseUser1.push().getKey();
                Reg reg = new Reg(id, namex, emailx, phonex);
                databaseUser1.child(id).setValue(reg);
                Toast.makeText(Event1.this, "User Registered", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
