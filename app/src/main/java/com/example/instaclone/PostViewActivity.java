package com.example.instaclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instaclone.Model.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class PostViewActivity extends AppCompatActivity {

    ImageView ivPost,ivProfilepic,ivLikefill,ivLike;
    TextView tvDisplayname;
    Post post;

    Toolbar toolbar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);

        toolbar =(Toolbar)findViewById(R.id.id_toolbar_post);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");


        ivPost =(ImageView) findViewById(R.id.id_ivpostview);
        ivProfilepic =(ImageView) findViewById(R.id.id_iv_profilepic);
        tvDisplayname =(TextView) findViewById(R.id.id_profileName);
        ivLikefill =(ImageView) findViewById(R.id.id_ivlikefill);
        ivLike =(ImageView) findViewById(R.id.id_ivlike);



        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String diaplayname = snapshot.child("user_account_data").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("display_name").getValue().toString();
                String profileurl = snapshot.child("user_account_data").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("profilepic").getValue().toString();


                tvDisplayname.setText(diaplayname.toString());
                Glide.with(PostViewActivity.this).load(Uri.parse(profileurl)).into(ivProfilepic);
                //Log.d("TAGsardar", "diaplayname... "+diaplayname);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Intent intent = getIntent();

        post =(Post) intent.getSerializableExtra("post");

        Glide.with(this).load(Uri.parse(post.getPost_url())).into(ivPost);
    }

    public void Like(View view){

        List<String> ueridLike = new ArrayList<>();

        if(ivLike.getVisibility() == View.VISIBLE){

            ivLike.setVisibility(View.INVISIBLE);
            ivLikefill.setVisibility(View.VISIBLE);

            ueridLike.add(FirebaseAuth.getInstance().getCurrentUser().getUid());

            //Post post1 = new Post(ueridLike);
            post.setLikes(ueridLike);

            databaseReference.child("posts").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(post.getPost_id()).setValue(post);

        } else if(ivLikefill.getVisibility() == View.VISIBLE) {

            ivLikefill.setVisibility(View.INVISIBLE);
            ivLike.setVisibility(View.VISIBLE);


        }

//        if(view.getId() == R.id.id_ivlikefill){
//            ivLike.setVisibility(View.VISIBLE);
//            ivLikefill.setVisibility(View.INVISIBLE);
//
//        } else if(view.getId() == R.id.id_ivlike){
//
//            ivLikefill.setVisibility(View.VISIBLE);
//            ivLike.setVisibility(View.INVISIBLE);
//
//        }

    }
}