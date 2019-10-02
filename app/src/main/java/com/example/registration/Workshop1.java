package com.example.registration;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Workshop1 extends AppCompatActivity {
    EditText name, email, phone;
    Button register;
    TextView logout;
    FirebaseAuth mFirebaseAuth;
    DatabaseReference databaseUser;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop1);
        getWindow().setBackgroundDrawableResource(R.drawable.home1);
        register = (Button)findViewById(R.id.reg);
        logout = (TextView)findViewById(R.id.logout);
        progressDialog = new ProgressDialog(this);
        name = (EditText)findViewById(R.id.n);
        email = (EditText)findViewById(R.id.e);
        phone = (EditText)findViewById(R.id.p);
        mFirebaseAuth = FirebaseAuth.getInstance();
        databaseUser = FirebaseDatabase.getInstance().getReference("quad");
        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(Workshop1.this,MainActivity.class);
                progressDialog.setMessage("Logging out...");
                progressDialog.show();
                startActivity(i);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email1 = email.getText().toString().trim();
                String name1 = name.getText().toString().trim();
                String phone1 = phone.getText().toString().trim();
                if(email1.isEmpty()){
                    email.setError("Please enter a valid email Id.");
                    email.requestFocus();
                }
                else if(name1.isEmpty()){
                    name.setError("Please enter your name");
                    name.requestFocus();
                }
                else if(phone1.isEmpty()){
                    phone.setError("Please enter your phone number");
                    phone.requestFocus();
                }
                else if(email1.isEmpty() && name1.isEmpty() && phone1.isEmpty()){
                    Toast.makeText(Workshop1.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else if(!(email1.isEmpty() && name1.isEmpty() && phone1.isEmpty())){
                    if(!TextUtils.isEmpty(name1)){
                        String id = databaseUser.push().getKey();
                        Reg reg = new Reg(id, name1, email1, phone1);
                        databaseUser.child(id).setValue(reg);
                        Toast.makeText(Workshop1.this, "User Registered", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Workshop1.this, "Fields can't be empty", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
}
