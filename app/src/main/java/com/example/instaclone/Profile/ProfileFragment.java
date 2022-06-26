package com.example.instaclone.Profile;

import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.instaclone.FirebaseMethods;
import com.example.instaclone.Model.Post;
import com.example.instaclone.R;
import com.example.instaclone.Model.User;
import com.example.instaclone.UserAccountData;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileFragment extends Fragment {

    TextView tvFollowercount,tvFollowingcount,tvPostcount,tvDisplayname,tvDiscription,tvWebsite,titleProfileName;

    ViewPager2 viewPager;
    TabLayout tabLayout;
    public View view;
    public ImageView profilepic;
    public FirebaseAuth mAuth;


    String demouri ="https://firebasestorage.googleapis.com/v0/b/instaclone-3b5b8.appspot.com/o/Images%2Fuserpost%2FeJySEq9YjEUdLvPfcEyhyfsoT5n1%2Fprofilepic?alt=media&token=103cd8f4-871b-4daa-a1ec-d6dfc2d08405";

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private StorageReference mStorageRef;


    public FragmentActivity fragmentActivity;
    public ProfileFragment(FragmentActivity a) {
        this.fragmentActivity = a;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference();

        mAuth = FirebaseAuth.getInstance();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        view =inflater.inflate(R.layout.fragment_profile, container, false);

        initView();
        //fun();

        tabLayout.setOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                tab.getIcon().setColorFilter(ContextCompat.getColor(getActivity(), R.color.black), PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tabcolunselected), PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(ContextCompat.getColor(getActivity(), R.color.black), PorterDuff.Mode.SRC_IN);

            }
        });


        mStorageRef =FirebaseStorage.getInstance().getReference("Images/userpost/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+"/profilepic");

        Glide.with(getActivity())
                .load(mStorageRef)
                //.load("https://firebasestorage.googleapis.com/v0/b/instaclone-3b5b8.appspot.com/o?name=Images%2Fuserpost%2FeJySEq9YjEUdLvPfcEyhyfsoT5n1%2Fprofilepic&uploadType=resumable&upload_id=ABg5-UxtRuFMyVbgaGQwHybxYHHa-WnFJdEz2d0aDhwwVa7MO7sArwF67HP5jvwX_TNJckauXKSod-9OtPDcVvhtO35f2KUOFA&upload_protocol=resumable")
                .into(profilepic);

        Log.d("TAGsardar", "url ... "+mStorageRef.getDownloadUrl());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String userID =mAuth.getCurrentUser().getUid();
                FirebaseMethods firebaseMethods = new FirebaseMethods();

                User user = firebaseMethods.getUserData(snapshot,userID);
                UserAccountData accountData = firebaseMethods.getUseraccoData(snapshot,userID);

                tvPostcount.setText(""+accountData.getPosts());
                tvFollowercount.setText(""+accountData.getFollowers());
                tvFollowingcount.setText(""+accountData.getFollowing());
                tvDisplayname.setText(accountData.getDisplay_name());

                titleProfileName.setText(snapshot.child("user_account_data").child(userID).getValue(UserAccountData.class).getUsername());


                Glide.with(getActivity()).load(Uri.parse(accountData.getProfilepic())).into(profilepic);

                tvDiscription.setText(accountData.getDiscription());
                tvWebsite.setText(accountData.getWebsite());
                Toast.makeText(getActivity(),"..",Toast.LENGTH_SHORT).show();

                if(tvDisplayname.getText().equals("")){
                    tvDisplayname.setVisibility(View.GONE);
                }
                if(tvDiscription.getText().equals("")){
                    tvDiscription.setVisibility(View.GONE);
                }
                if(tvWebsite.getText().equals("")){
                    tvWebsite.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

    public void initView(){
        viewPager =(ViewPager2)view.findViewById(R.id.profileViewPager);
        tabLayout =(TabLayout) view.findViewById(R.id.profileTabview);

        tvPostcount =(TextView)view.findViewById(R.id.id_tvPostcount);
        tvFollowercount =(TextView)view.findViewById(R.id.id_tvFollowercount);
        tvFollowingcount =(TextView)view.findViewById(R.id.id_tvFollowingcount);
        tvDisplayname =(TextView)view.findViewById(R.id.id_tvDisplayname);
        tvDiscription =(TextView)view.findViewById(R.id.id_tvDiscription);
        tvWebsite =(TextView)view.findViewById(R.id.id_tvWebsite);

        titleProfileName =(TextView)view.findViewById(R.id.id_titleprofileName);


        profilepic =(ImageView)view.findViewById(R.id.id_ivProfilepic);

         viewPager.setAdapter(new ProfileViewPagerAdpter((Account)getActivity()));


        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {


            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {


                if( position ==0) {
                    tab.setIcon(R.drawable.ic_grid);
                        tab.getIcon().setColorFilter(ContextCompat.getColor(getActivity(), R.color.black), PorterDuff.Mode.SRC_IN);

                } else if (position ==1) {
                    tab.setIcon(R.drawable.ic_tag);


                }

            }
        }).attach();
    }



}

class ProfileViewPagerAdpter extends FragmentStateAdapter {

    public ProfileViewPagerAdpter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        if( position == 0){
            fragment =new FragmentMy();
        }
        else if (position == 1){
            fragment = new FragmentTag();
        }
        return fragment;    }

    @Override
    public int getItemCount() {
        return 2;
    }
}