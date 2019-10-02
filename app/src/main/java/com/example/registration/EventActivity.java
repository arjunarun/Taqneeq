package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class EventActivity extends AppCompatActivity implements View.OnClickListener {
    TextView logout;
    CardView e1, e2, e3;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.home1);
        setContentView(R.layout.activity_event);
        progressDialog = new ProgressDialog(this);
        logout = (TextView)findViewById(R.id.logout);
        e1 = (CardView) findViewById(R.id.e1);
        e2 = (CardView) findViewById(R.id.e2);
        e3 = (CardView) findViewById(R.id.e3);
        logout.setOnClickListener(this);
        e1.setOnClickListener(this);
        e2.setOnClickListener(this);
        e3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(v==logout){
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(EventActivity.this,MainActivity.class);
                progressDialog.setMessage("Logging out...");
                progressDialog.show();
                startActivity(i);
            }
        if(v==e1){
                Intent ie1= new Intent(EventActivity.this, Event1.class);
                startActivity(ie1);
            }
        if(v==e2){
                Intent ie1= new Intent(EventActivity.this, Event2.class);
                startActivity(ie1);
            }
        if(v==e3){
                Intent ie1= new Intent(EventActivity.this, Event3.class);
                startActivity(ie1);
            }
    }
}
