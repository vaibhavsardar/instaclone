package com.example.instaclone.Profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.instaclone.Add.Add;
import com.example.instaclone.Home.MainActivity;
import com.example.instaclone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;

public class EditProfileActivity extends AppCompatActivity {

    public FileInputStream fileInputStream;


    Toolbar toolbar;
    ImageView ivProfilepicedit;

    private StorageReference mStorageRef;

    public  FirebaseAuth mAuth;
    public  String userID;
    public String uri, currenturl;

    public Bitmap bitmap ;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    EditText tvdisplaynameEdit,tvUsernameEdit,tvWebsiteEdit,tvBioEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mAuth = FirebaseAuth.getInstance();

        userID = mAuth.getCurrentUser().getUid();

        ivProfilepicedit =(ImageView)findViewById(R.id.id_iveditProfilepic);

        tvdisplaynameEdit =(EditText) findViewById(R.id.id_editT_name);
        tvUsernameEdit =(EditText) findViewById(R.id.id_editT_name);
        tvWebsiteEdit =(EditText) findViewById(R.id.id_editT_website);
        tvBioEdit =(EditText) findViewById(R.id.id_editT_bio);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference();

        mStorageRef = FirebaseStorage.getInstance().getReference("Images/userpost/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+"/profilepic");


        toolbar =(Toolbar)findViewById(R.id.id_toolbar_editProfile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                Intent intent = getIntent();
                if (intent.getFlags() != Intent.FLAG_ACTIVITY_NEW_TASK) {

                    String currenturl = (String) snapshot.child("user_account_data").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("profilepic").getValue();

                    Log.d("sardar3", "current pic url .." + currenturl);

                    Glide.with(getApplicationContext()).load(Uri.parse(currenturl)).into(ivProfilepicedit);

                }
                tvdisplaynameEdit.setText(snapshot.child("user_account_data").child(userID).child("display_name").getValue().toString());
                tvUsernameEdit.setText(snapshot.child("user_account_data").child(userID).child("username").getValue().toString());
                tvWebsiteEdit.setText(snapshot.child("user_account_data").child(userID).child("website").getValue().toString());
                tvBioEdit.setText(snapshot.child("user_account_data").child(userID).child("discription").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(EditProfileActivity.this,"EPAcrivity..."+error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });



        Intent intent = getIntent();

        if (intent.getFlags() == Intent.FLAG_ACTIVITY_NEW_TASK){
            uri = intent.getStringExtra("picuri");

            Toast.makeText(this,".."+Uri.parse(uri),Toast.LENGTH_SHORT).show();
            //imRef.pu(getBitmap(uri));

            Log.d("sardar3","get intent done");

            try {
                 bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),Uri.parse(uri));
            } catch (IOException e) {
                e.printStackTrace();
            }


            Glide.with(getApplicationContext())
                .load(Uri.parse(uri))
                .into(ivProfilepicedit);
            //ivProfilepicedit.setImageURI(Uri.parse(uri));
        }


    }



    public void closeActivity(View view){
        finish();
    }

    public void changeProfile(View view){
        Intent intent = new Intent(this , Add.class);
        intent.putExtra("coming","fromEditactivity");
        startActivity(intent);
        finish();
    }

    public void saveEdit(View view){

        databaseReference.child("user_account_data").child(userID).child("display_name").setValue(tvdisplaynameEdit.getText().toString());
        databaseReference.child("user_account_data").child(userID).child("username").setValue(tvUsernameEdit.getText().toString());
        databaseReference.child("user_account_data").child(userID).child("website").setValue(tvWebsiteEdit.getText().toString());
        databaseReference.child("user_account_data").child(userID).child("discription").setValue(tvBioEdit.getText().toString());


        if(uri != null){
            UploadTask uploadTask = mStorageRef.putBytes(getByteArrayFormat(bitmap,50));
           //// UploadTask uploadTask = mStorageRef.putFile(Uri.parse(uri));
//            Glide.with(this).
//                        .load(mStorageRef)
//                        .into(pro);
//            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//
//                    Log.d("TAGsardar", "url ...2 "+taskSnapshot.getStorage().getDownloadUrl().getResult().toString());
//
//                    Toast.makeText(getApplicationContext(),"profile update successful",Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//            });

            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                    task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.d("TAGsardar", "url ...2 "+uri.toString());
                            databaseReference.child("user_account_data").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("profilepic").setValue(uri.toString());

                            Toast.makeText(getApplicationContext(),"profile update successful",Toast.LENGTH_SHORT).show();

                        }
                    });
//                    if (task.isSuccessful()) {
//                        Toast.makeText(getApplicationContext(),"profile update successful",Toast.LENGTH_SHORT).show();
//                        finish();
//                    } else {
//
//
//                    }

                }
            });

        }

        finish();
    }

    public Bitmap getBitmap(String uri){

        File imgFile = new File(uri);
        //FileInputStream fileInputStream = null;
        Bitmap bitmap =null;

        try {
            fileInputStream = new FileInputStream(imgFile);
            bitmap = BitmapFactory.decodeStream(fileInputStream);

            Log.d("sardar","bitmap done.......");

        } catch (FileNotFoundException e){
            Log.d("sardar","get bm file not found"+e.getMessage());
        } finally {
//            try {
//                fileInputStream.close();
//            } catch(IOException e){
//                Log.d("sardar","get bm file not found2 "+e.getMessage());
//            }
        }


        return bitmap ;

    }

    public byte[] getByteArrayFormat(Bitmap bitmap ,int quality){

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,quality,stream);

        return stream.toByteArray();
    }


}