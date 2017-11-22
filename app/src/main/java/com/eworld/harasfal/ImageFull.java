package com.eworld.harasfal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;


public class ImageFull extends AppCompatActivity {
String Image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_full);
        Intent i =getIntent();
        Image =i.getStringExtra("Image");
        ImageView source = (ImageView)findViewById(R.id.fullscreen);
        try {
            Picasso.with(this).load(Image).placeholder(R.drawable.semimagem).into(source);
            PhotoViewAttacher mAttacher = new PhotoViewAttacher(source);
            //source.setImageResource(Image);
        }catch(Exception e){e.printStackTrace();}

    }

}
