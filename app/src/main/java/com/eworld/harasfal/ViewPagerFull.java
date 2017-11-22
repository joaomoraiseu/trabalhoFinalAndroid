package com.eworld.harasfal;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.eworld.harasfal.Adapters.CustomPagerAdapter;
import com.eworld.harasfal.Classes.Foto;
import com.viewpagerindicator.CirclePageIndicator;
import java.util.ArrayList;


public class ViewPagerFull extends AppCompatActivity {

    ViewPager viewpager;
    public static ArrayList<Foto> Fotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_full);
        CustomPagerAdapter custompager = new CustomPagerAdapter(ViewPagerFull.this, Fotos);
        viewpager =(ViewPager)findViewById(R.id.viewpager);
        viewpager.setAdapter(custompager);
        viewpager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {

                page.setRotationY(position * -30);
            }
        });
        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.titles);
        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(6 * density);
        indicator.setStrokeWidth(density);
        indicator.setViewPager(viewpager);

    }



}
