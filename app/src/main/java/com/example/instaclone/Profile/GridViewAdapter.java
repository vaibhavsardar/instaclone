package com.example.instaclone.Profile;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.instaclone.R;

import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<String> posturl;
    //public int imgs[] ={R.drawable.img1,R.drawable.img2,R.drawable.img3,R.drawable.img4,R.drawable.img5,R.drawable.img6,R.drawable.img10,R.drawable.img11,R.drawable.img12,R.drawable.img13,R.drawable.img14,R.drawable.img15};

    public GridViewAdapter(Context context,ArrayList<String> url){
        this.mContext =context;
        this.posturl = url;
    }

    @Override
    public int getCount() {
        return posturl.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ImageView imageView;

        if (view ==null){
            imageView =new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(358,358));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        } else {
            imageView =(ImageView) view;
        }

//        imgUri = ContentUris.withAppendedId(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,files.get(i).getId());
        Glide.with(mContext).asBitmap()
                .load(Uri.parse(posturl.get(i)))
                .into(imageView);


        return imageView;
    }
}
