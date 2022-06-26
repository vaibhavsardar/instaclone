package com.example.instaclone;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import com.example.instaclone.Add.Add;
import com.example.instaclone.Home.MainActivity;
import com.example.instaclone.Profile.Account;

public class BottonNavHelper{
    //BottomNavigationView navigationView =(BottomNavigationView)

    public static void funNav(Context context, MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.home:
               context.startActivity(new Intent(context, MainActivity.class));
                break;
            case R.id.search:
                context.startActivity(new Intent(context, Search.class));
                break;
            case R.id.add:
                context.startActivity(new Intent(context, Add.class));
                break;
            case R.id.fav:
                context.startActivity(new Intent(context, Favorite.class));
                break;
            case R.id.account:
                context.startActivity(new Intent(context, Account.class));
                break;
        }

    }
}
