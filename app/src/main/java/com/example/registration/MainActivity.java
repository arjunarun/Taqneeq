package com.example.registration;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;
        import androidx.appcompat.widget.Toolbar;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText emailId, pass;
    Button login;
    TextView tvSignUp;
    Toolbar tb;
    private ProgressDialog progressDialog;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setBackgroundDrawableResource(R.drawable.home2);
        tb = findViewById(R.id.app_bar);
        setSupportActionBar(tb);
        mFirebaseAuth = FirebaseAuth.getInstance();

        emailId = (EditText)findViewById(R.id.t2);
        pass = (EditText)findViewById(R.id.pass);
        login = (Button)findViewById(R.id.login);
        progressDialog = new ProgressDialog(this);
        tvSignUp = (TextView)findViewById(R.id.tvSignUp);
        if(mFirebaseAuth.getCurrentUser()!=null){
            //start profile
            finish();
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser!=null){
                    Toast.makeText(MainActivity.this, "You are logged in.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this,Homex.class);
                    startActivity(i);
                }
                else{
                    //Toast.makeText(LoginActivity.this, "Enter valid credentials.", Toast.LENGTH_SHORT).show();
                }
            }
        };
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email1 = emailId.getText().toString();
                String pass1 = pass.getText().toString();
                if(email1.isEmpty()){
                    emailId.setError("Please enter a valid email Id.");
                    emailId.requestFocus();
                }
                else if(pass1.isEmpty()){
                    pass.setError("Please enter the password");
                    pass.requestFocus();
                }
                else if(email1.isEmpty() && pass1.isEmpty()){
                    Toast.makeText(MainActivity.this, "Fields cannot be empty.", Toast.LENGTH_SHORT).show();
                }
                else if(!(email1.isEmpty() && pass1.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(email1, pass1).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Login Unsuccessful. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                finish();
                                Intent home = new Intent(MainActivity.this,Homex.class);
                                progressDialog.setMessage("Logging in...");
                                progressDialog.show();
                                startActivity(home);
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(MainActivity.this, "Error Occurred.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(signup);
            }
        });

    }
    @Override
    protected void onStart(){
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
