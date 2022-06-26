package com.example.instaclone.Add;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.instaclone.BottonNavHelper;
import com.example.instaclone.Home.MainActivity;
import com.example.instaclone.Profile.FragmentMy;
import com.example.instaclone.Profile.FragmentTag;

import com.example.instaclone.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

//add new post
public class Add extends AppCompatActivity {
    public BottomNavigationView bottomNavigationView;

    public ViewPager2 viewPager;
    public TabLayout tabLayout;
    public AddViewpagerAdapter viewpagerAdapter;

    //public Activity activity =this;
    public Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        viewPager = (ViewPager2) findViewById(R.id.viewpager_add);
        tabLayout = (TabLayout) findViewById(R.id.addTabview);

           viewpagerAdapter = new AddViewpagerAdapter(this,this);

        viewPager.setAdapter(viewpagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0)
                    tab.setText("GALLERY");
                else if (position == 1)
                    tab.setText("PHOTO");
            }

        }).attach();

        initView();


//        bottomNavigationView =(BottomNavigationView) findViewById(R.id.id_bottom_nav);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                BottonNavHelper.funNav(Add.this,menuItem);
//                return false;
//            }
//        });



    }

    public void Next(View view){
        Intent intent = new Intent(this,NextActivity.class);
        intent.putExtra("pic",FragmentGallery.imuri.toString());
        startActivity(intent);
    }

    public void initView() {

    }





}

class AddViewpagerAdapter extends FragmentStateAdapter {
    Activity activity;

    public AddViewpagerAdapter(FragmentActivity fragmentActivity,Activity c) {
        super(fragmentActivity);
        this.activity =c;
    }

    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new FragmentGallery(activity);
        } else if (position == 1) {
            fragment = new FragmentPhoto();
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}