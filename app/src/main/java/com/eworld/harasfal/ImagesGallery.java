package com.eworld.harasfal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.eworld.harasfal.Adapters.Gallery_AdapterRecyclerView;
import com.eworld.harasfal.Classes.Foto;
import com.eworld.harasfal.Interface.RecyclerViewOnClickListenerHack;

import java.util.ArrayList;


public class ImagesGallery extends AppCompatActivity {

    public static ArrayList<Foto> Fotos;

    ImageView fullimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_galery);


        createListView();
        /*ImageView im1 =(ImageView)findViewById(R.id.image1);
        ImageView im2 =(ImageView)findViewById(R.id.image2);
        ImageView im3 =(ImageView)findViewById(R.id.image3);
        switch (Fotos.size()){
            case 1:
                Picasso.with(this).load(Fotos.get(0).getLink()).into(im1);
                im2.setVisibility(View.GONE);
                im3.setVisibility(View.GONE);
            case 2:
                Picasso.with(this).load(Fotos.get(0).getLink()).into(im1);
                Picasso.with(this).load(Fotos.get(1).getLink()).into(im2);
                im3.setVisibility(View.GONE);
                break;
            case 3:
                Picasso.with(this).load(Fotos.get(0).getLink()).into(im1);
                Picasso.with(this).load(Fotos.get(1).getLink()).into(im2);
                Picasso.with(this).load(Fotos.get(2).getLink()).into(im3);
                break;
        }
        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ImagesGallery.this, ImageFull.class);
                i.putExtra("Image", Fotos.get(0).getLink());
                startActivity(i);
            }
        });
        im2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ImagesGallery.this, ImageFull.class);
                i.putExtra("Image", Fotos.get(1).getLink());
                startActivity(i);
            }
        });
        im3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ImagesGallery.this, ImageFull.class);
                i.putExtra("Image", Fotos.get(2).getLink());
                startActivity(i);
            }
        });*/
    }

    private void createListView() {
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        Gallery_AdapterRecyclerView adapterListView = new Gallery_AdapterRecyclerView(Fotos, this);
        adapterListView.setRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
            @Override
            public void onClickListener(View view, int position) {
                Intent i = new Intent(ImagesGallery.this, ImageFull.class);
                i.putExtra("Image", Fotos.get(position).getLink());
                startActivity(i);
            }
        });
        mRecyclerView.setAdapter(adapterListView);


    }

}
