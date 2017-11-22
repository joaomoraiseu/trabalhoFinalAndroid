package com.eworld.harasfal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.eworld.harasfal.Adapters.Gallery_AdapterRecyclerView2;
import com.eworld.harasfal.Interface.RecyclerViewOnClickListenerHack;

import java.util.ArrayList;


public class ImagesGallery2 extends AppCompatActivity {

    public static ArrayList<String> Fotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_galery);
        createListView();

    }

    private void createListView() {
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        Gallery_AdapterRecyclerView2 adapterListView = new Gallery_AdapterRecyclerView2(Fotos, this);
        adapterListView.setRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
            @Override
            public void onClickListener(View view, int position) {
                Intent i = new Intent(ImagesGallery2.this, ImageFull.class);
                i.putExtra("Image", Fotos.get(position));
                startActivity(i);
            }
        });
        mRecyclerView.setAdapter(adapterListView);


    }

}
