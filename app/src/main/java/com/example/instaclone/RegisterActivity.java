package com.example.instaclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.instaclone.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText EteMail;
    EditText Fullname;
    EditText Etpassword;
    Button SignUpbtn;

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String userID;

    public String email;
    public String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference();

        EteMail =(EditText)findViewById(R.id.id_et_signupMail);
        Fullname =(EditText)findViewById(R.id.id_et_fullName);
        Etpassword =(EditText)findViewById(R.id.id_et_signupPassword);
        SignUpbtn =(Button) findViewById(R.id.id_btn_signUp);

        signUp();
    }

    public void signUp(){
        SignUpbtn.setOnClickListener(new View.OnClickListener() {
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

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            userID = mAuth.getCurrentUser().getUid();
                            addnewUser(email,"","python","","");
                            Toast.makeText(RegisterActivity.this, "sign up successful ="+userID,Toast.LENGTH_SHORT).show();


                        } else {
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();

                        }
                    }
                });

                //Toast.makeText(getApplicationContext(),"signUp successful",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void addnewUser(String email, String username, String discription, String website, String profilepic){

        User user = new User(email,990015,userID,username);
        databaseReference.child("users").child(userID).setValue(user);

        UserAccountData userAccountData = new UserAccountData(discription,username,0,0,0,profilepic,username,website);
        databaseReference.child("user_account_data").child(userID).setValue(userAccountData);
    }
}