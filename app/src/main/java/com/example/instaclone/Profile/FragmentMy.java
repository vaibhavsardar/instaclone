package com.example.instaclone.Profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.instaclone.Model.Post;
import com.example.instaclone.PostViewActivity;
import com.example.instaclone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentMy extends Fragment {
    GridView gridView;
    GridViewAdapter gridViewAdapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public ArrayList<Post> postArrayList;

    public FragmentMy() {
        // Required empty public constructor
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        setUpGridView();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewfeed =inflater.inflate(R.layout.fragment_my, container, false);
        gridView =(GridView) viewfeed.findViewById(R.id.myfeedGideView);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Post post = postArrayList.get(i);
                Intent viewpostintent = new Intent((Account)getActivity(),PostViewActivity.class);
                viewpostintent.putExtra("post",post);
                getActivity().startActivity(viewpostintent);
            }
        });


        return viewfeed;
    }


    public void setUpGridView(){
               postArrayList = new ArrayList<Post>();

        Query query = databaseReference.child("posts").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for(DataSnapshot dataSnapshot :snapshot.getChildren()){
                    //Log.d("TAGsardar", "get key... "+snapshot.child("posts").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("-MLsEHQ2clVTXoKL4BQP").getValue());
                    //Log.d("TAGsardar", "get key... "+dataSnapshot.getValue(Post.class).getPost_url());

                    postArrayList.add(dataSnapshot.getValue(Post.class));
                }

                ArrayList<String> posturlList = new ArrayList<>();

                for(int i =0;i<postArrayList.size();i++){
                    posturlList.add(postArrayList.get(i).getPost_url());
                }

                gridView.setAdapter(new GridViewAdapter(getActivity(),posturlList));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}