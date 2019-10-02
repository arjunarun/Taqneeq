package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    EditText emailId, pass, name, cpass;
    EditText age, phone;
    Button register;
    TextView tvSignIn;
    Spinner stream;
    Toolbar tb;
    FirebaseAuth mFirebaseAuth;
    DatabaseReference databaseUser;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setBackgroundDrawableResource(R.drawable.home1);
        tb = findViewById(R.id.app_bar);
        setSupportActionBar(tb);
        mFirebaseAuth = FirebaseAuth.getInstance();
        databaseUser = FirebaseDatabase.getInstance().getReference("users");
        if(mFirebaseAuth.getCurrentUser()!=null){
            //start profile
            finish();
            startActivity(new Intent(getApplicationContext(), Homex.class));
        }
        progressDialog = new ProgressDialog(this);
        name = (EditText)findViewById(R.id.name1);
        //userType = (Spinner)findViewById(R.id.userType);
        age = (EditText) findViewById(R.id.age);
        phone = (EditText) findViewById(R.id.phone);
        emailId = (EditText)findViewById(R.id.t2);
        stream = (Spinner)findViewById(R.id.stream);
        pass = (EditText)findViewById(R.id.pass);
        cpass = (EditText)findViewById(R.id.cpass);
        register = (Button)findViewById(R.id.register);
        tvSignIn = (TextView)findViewById(R.id.tvSignIn);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email1 = emailId.getText().toString();
                String pass1 = pass.getText().toString();
                String cpass1 = cpass.getText().toString();
                if(email1.isEmpty()){
                    emailId.setError("Please enter a valid email Id.");
                    emailId.requestFocus();
                }
                else if(pass1.isEmpty()){
                    pass.setError("Please enter the password");
                    pass.requestFocus();
                }
                else if(email1.isEmpty() && pass1.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Fields cannot be empty.", Toast.LENGTH_SHORT).show();
                }
                else if(!(email1.isEmpty() && pass1.isEmpty())){
                    if(pass1.equals(cpass1)) {
                        mFirebaseAuth.createUserWithEmailAndPassword(email1, pass1).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Sign Up Unsuccessful! Please try Again.", Toast.LENGTH_SHORT).show();
                                } else {
                                    finish();
                                    addUser();
                                    progressDialog.setMessage("Please Wait...");
                                    progressDialog.show();
                                    startActivity(new Intent(LoginActivity.this, Homex.class));

                                }
                            }
                        });
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this, "Error Occurred.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
    private void addUser(){
        String name1 = name.getText().toString().trim();
        String age1 = age.getText().toString().trim();
        String userType1 = "Student";
        String phone1 = phone.getText().toString().trim();
        String email1 = emailId.getText().toString().trim();
        String stream1 = stream.getSelectedItem().toString();
        String password1 = pass.getText().toString().trim();
        if(!TextUtils.isEmpty(name1)){
            String id = databaseUser.push().getKey();
            Users users = new Users(id, name1, age1, phone1, email1, password1, userType1, stream1);
            databaseUser.child(id).setValue(users);
            Toast.makeText(LoginActivity.this, "User Registered", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(LoginActivity.this, "Fields can't be empty", Toast.LENGTH_SHORT).show();
        }
    }
}