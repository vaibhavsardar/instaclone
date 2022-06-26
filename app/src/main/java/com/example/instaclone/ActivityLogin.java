package com.example.instaclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instaclone.Home.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivityLogin extends AppCompatActivity {

     FirebaseAuth mAuth;

    EditText EteMail;
    EditText Etpassword;
    Button LoginBtn;
    TextView textViewSignup;

    public String email;
    public String password;

    //public  static String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        EteMail =(EditText)findViewById(R.id.id_et_email);
        Etpassword =(EditText)findViewById(R.id.id_et_password);
        LoginBtn =(Button) findViewById(R.id.id_btn_login);
        textViewSignup =(TextView) findViewById(R.id.id_tv_signup);

        textViewSignup.setOnClickListener(new View. OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityLogin.this,RegisterActivity.class));
            }
        });

        Login();
    }

    public void Login(){



        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email =EteMail.getText().toString().toLowerCase().trim();
                password =Etpassword.getText().toString().trim();

                if(email.isEmpty()){
                    EteMail.setError("Email is required");
                    EteMail.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    EteMail.setError("Please enter a valid email");
                    EteMail.requestFocus();
                    return;
                }

                if(password.isEmpty()){
                    Etpassword.setError("Password is required");
                    Etpassword.requestFocus();
                    return;
                }

                if(password.length() < 6){
                    EteMail.setError("password length more should be more than 6 latter");
                    EteMail.requestFocus();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    //userId = mAuth.getCurrentUser().getUid();
                                    Toast.makeText(ActivityLogin.this, "login successful vaibhav",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ActivityLogin.this , MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(ActivityLogin.this, "Authentication failed.",Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });


    }
}