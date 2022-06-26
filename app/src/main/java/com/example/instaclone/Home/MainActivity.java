package com.example.instaclone.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.instaclone.ActivityLogin;
import com.example.instaclone.BottonNavHelper;
import com.example.instaclone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    public BottomNavigationView bottomNavigationView;
    public static final String TAG ="com.instagram.vaibhav";

    public static FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    public static String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setupFirebaseAuth();
        permission();
        mAuth = FirebaseAuth.getInstance();


        bottomNavigationView =(BottomNavigationView) findViewById(R.id.id_bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                BottonNavHelper.funNav(MainActivity.this,menuItem);
                return false;
            }
        });



    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //userID = mAuth.getCurrentUser().getUid();

        checkCurrentUser(currentUser);

        //mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
//        if(authStateListener != null){
//            mAuth.removeAuthStateListener(authStateListener);
//        }
    }

//    private void setupFirebaseAuth(){
//
//        authStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                ///check if logged in
//                //checkCurrentUser(user);
//
//                if (user != null) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG, "Sign in success===="+user.getUid());
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "singout....");
//
//                }
//            }
//        };
//
//    }


    private void checkCurrentUser(FirebaseUser firebaseUser){
        Log.w(TAG, "checking is current user is loged or not");

        if(firebaseUser == null){
            Intent intent = new Intent(this , ActivityLogin.class);
            startActivity(intent);
        }

    }

    private void permission(){
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA},1);
        }else{
            Toast.makeText(this,"permisstion granted",Toast.LENGTH_SHORT).show();

        }

    }



}