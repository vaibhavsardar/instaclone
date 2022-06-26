package com.example.instaclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.instaclone.Home.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Search extends AppCompatActivity {
    public BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        bottomNavigationView =(BottomNavigationView) findViewById(R.id.id_bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                BottonNavHelper.funNav(Search.this,menuItem);
                return false;
            }
        });

        //startActivity(new Intent(this,PostViewActivity.class));

    }
}