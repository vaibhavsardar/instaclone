package com.example.instaclone;

import com.example.instaclone.Model.User;
import com.google.firebase.database.DataSnapshot;

public class FirebaseMethods {

    public User getUserData(DataSnapshot dataSnapshot, String userID){
        User user = new User();

        for(DataSnapshot ds : dataSnapshot.getChildren()){

            if(ds.getKey().equals("users")){
                user.setEmail(ds.child(userID).getValue(User.class).getEmail());

                user.setPhone_number(ds.child(userID).getValue(User.class).getPhone_number());

                user.setUser_id(ds.child(userID).getValue(User.class).getUser_id());

                user.setUsername(ds.child(userID).getValue(User.class).getUsername());

            }
        }

        return user;
    }

    public UserAccountData getUseraccoData(DataSnapshot dataSnapshot,String userID){
        UserAccountData accountData = new UserAccountData();

        for(DataSnapshot ds : dataSnapshot.getChildren()){

            if(ds.getKey().equals("user_account_data")){
                accountData.setDiscription(ds.child(userID).getValue(UserAccountData.class).getDiscription());

                accountData.setDisplay_name(ds.child(userID).getValue(UserAccountData.class).getDisplay_name());

                accountData.setFollowers(ds.child(userID).getValue(UserAccountData.class).getFollowers());

                accountData.setFollowing(ds.child(userID).getValue(UserAccountData.class).getFollowing());

                accountData.setPosts(ds.child(userID).getValue(UserAccountData.class).getPosts());

                accountData.setProfilepic(ds.child(userID).getValue(UserAccountData.class).getProfilepic());

                accountData.setUsername(ds.child(userID).getValue(UserAccountData.class).getUsername());

                accountData.setWebsite(ds.child(userID).getValue(UserAccountData.class).getWebsite());
            }
        }
        return accountData;
    }
}
