package com.example.instaclone.Add;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.instaclone.Profile.EditProfileActivity;
import com.example.instaclone.Profile.GridViewAdapter;
import com.example.instaclone.R;

import java.util.ArrayList;

public class FragmentGallery extends Fragment  implements AdapterView.OnItemSelectedListener {

    public GridView gridView;


    public ArrayList<MediaFile> dirFile;
    public Spinner spinner;
    public ArrayList<String> allDirList;
    public String selectedDir = "Camera";
    public ArrayList<MediaFile> imgFiles;
    ImageView iv;
    public static  Uri imuri;
    public View view;
    public Activity mContext;

    public FragmentGallery(Activity context) {
        this.mContext = context;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imgFiles = getSongs(getContext());
        allDirList(imgFiles);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_gallery, container,false);
//
        spinner = view.findViewById(R.id.spinner_gallery);

        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, allDirList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        this.iv =(ImageView) view.findViewById(R.id.ivM);



        Log.d("com.vaibhav", "dirn1 " + selectedDir);
        gridView = view.findViewById(R.id.gridView_gallery);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("com.vaibhav", "on item cl " +i);
                imuri = ContentUris.withAppendedId(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,makeDir(imgFiles, selectedDir).get(i).getId());
//                Glide.with(getContext()).asBitmap()
//                        .load(imuri)
//                        .into(iv);
                iv.setImageURI(imuri);


                Intent intent =getActivity().getIntent();
                if(intent.getStringExtra("coming") != null){
                    if (intent.getStringExtra("coming").equals("fromEditactivity")){
                        Toast.makeText(getActivity(),"coming form fromEditactivity",Toast.LENGTH_SHORT).show();
                        Log.d("sardar", "coming form fromEditactivity " +i);

                        sendpicResult(imuri);
                    }

                }




            }
        });
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedDir = allDirList.get(i).toString();
        GridViewAdapterAdd gridViewAdapterAdd = new GridViewAdapterAdd(getContext(), makeDir(imgFiles, selectedDir),view);
        gridView.setAdapter(gridViewAdapterAdd);

        imuri = ContentUris.withAppendedId(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,makeDir(imgFiles, selectedDir).get(0).getId());
//        Glide.with(getContext()).asBitmap()
//                .load(imuri)
//                .into(iv);
        iv.setImageURI(imuri);


        Log.d("com.vaibhav", "dirn insise " + selectedDir);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public ArrayList<MediaFile> getSongs(Context context) {
        ArrayList<MediaFile> imgTemp = new ArrayList<>();

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        };

        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null) {
            Log.d("com.vaibhav", "not null cursor..");

            while (cursor.moveToNext()) {

                long id = cursor.getLong(0);
                String buketName = cursor.getString(2);


//                Log.d("com.vaibhav","not null title.."+title);
//                Log.d("com.vaibhav","not null artist.."+artist);
                // Log.d("com.vaibhav","dataaa "+cursor.getString(1));
                // Log.d("com.vaibhav","buket name  ."+cursor.getString(2));


                MediaFile mediaFile = new MediaFile(id, buketName);
                imgTemp.add(mediaFile);
                //Log.d("com.vaibhav","not null album.."+musicFiles.getBuket());

            }
            cursor.close();
        }
        //Log.d("com.vaibhav","not null teplist."+imgTemp.get(2).getBuket());
        return imgTemp;
    }

    public ArrayList<MediaFile> makeDir(ArrayList<MediaFile> filesArrayList, String dirName) {
        dirFile = new ArrayList<>();

        for (int i = 0; i < filesArrayList.size(); i++) {
            Log.d("com.vaibhav", "dirn " + dirName);

            if (filesArrayList.get(i).getBuket().equals(dirName)) {
                //Log.d("com.vaibhav","not null title.. "+filesArrayList.get(i).getBuket());
                dirFile.add(filesArrayList.get(i));
            }
        }

        return dirFile;
    }

    public void allDirList(ArrayList<MediaFile> files) {
        allDirList = new ArrayList<>();

        for (int i = 0; i < files.size(); i++) {
            //Log.d("com.vaibhav","not null title.. "+filesArrayList.get(i).getBuket());

            if (!allDirList.contains(files.get(i).getBuket())) {
                allDirList.add(files.get(i).getBuket().toString());

            }

        }
    }


    public  void sendpicResult(Uri uri){

        Intent intent = new Intent((Add)getActivity(), EditProfileActivity.class);
        intent.putExtra("picuri",uri.toString());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Toast.makeText(mContext,"..ccc",Toast.LENGTH_SHORT).show();
        startActivity(intent);
        Log.d("com.vaibhav1", "on item "+uri);
        getActivity().finish();

    }

}