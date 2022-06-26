package com.example.instaclone.Add;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.instaclone.Model.Like;
import com.example.instaclone.Model.Post;
import com.example.instaclone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NextActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView ivProfile,ivPost;
    public EditText etCaption;

    public String imgUri;

    public List<String> stringList;


    private StorageReference mStorageRef;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        ivProfile =(ImageView)findViewById(R.id.id_ivprofile);
        ivPost =(ImageView)findViewById(R.id.id_ivpost);
        etCaption =(EditText)findViewById(R.id.id_etcaption);




        toolbar =(Toolbar)findViewById(R.id.id_toolbar_Next);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference();

        ///databaseReference.child("l").setValue(stringList);

        mStorageRef = FirebaseStorage.getInstance().getReference("Images/userpost/"+ FirebaseAuth.getInstance().getCurrentUser().getUid());


        Intent intent = getIntent();
        imgUri =intent.getStringExtra("pic");

        ivPost.setImageURI(Uri.parse(imgUri));



    }

    public void share(View view){
        Bitmap bitmap =null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),Uri.parse(imgUri));
        } catch (IOException e) {
            e.printStackTrace();
        }
        UploadTask uploadTask = mStorageRef.child("post"+ Calendar.getInstance().getTime()).putBytes(getByteArrayFormat(bitmap,80));

        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {


                        stringList = new ArrayList<>();
                        //stringList.add(0,"");

                        Post newpost = new Post(etCaption.getText().toString(),"comm","date",stringList,databaseReference.child("posts").push().getKey(),uri.toString(),"", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        //Toast.makeText(getApplicationContext(),"profile update successful",Toast.LENGTH_SHORT).show();
                        Log.d("TAGsardar", "new post... "+uri.toString());

                        setPostData(newpost);

                    }
                });

                if (task.isSuccessful()) {

                    Toast.makeText(getApplicationContext(),"new post upload successful",Toast.LENGTH_SHORT).show();
                    //finish();
                } else {
                    // Handle failures
                    // ...
                }

            }
        });

    }

    public  void setPostData(Post post){

        databaseReference.child("posts").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(databaseReference.child("posts").push().getKey()).setValue(post);


    }

    public byte[] getByteArrayFormat(Bitmap bitmap , int quality){

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,quality,stream);

        return stream.toByteArray();
    }
}