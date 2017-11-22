package com.eworld.harasfal.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.eworld.harasfal.Classes.Foto;
import com.eworld.harasfal.ImageFull;
import com.eworld.harasfal.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
/**
 * Created by Celso on 09/08/2015.
 */public class CustomPagerAdapter extends android.support.v4.view.PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<Foto> Fotos;


    public CustomPagerAdapter(Context context, ArrayList<Foto> Fotos) {
        mContext = context;
        this.Fotos = Fotos;
        if (this.Fotos ==null)
            this.Fotos = new ArrayList<Foto>();
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return Fotos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View itemView = mLayoutInflater.inflate(R.layout.image_full, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.fullscreen);
        Picasso.with(mContext).load(Fotos.get(position).getLink()).placeholder(R.drawable.semimagem).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ImageFull.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("Image", Fotos.get(position).getLink());
                mContext.startActivity(i);
            }
        });
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

}
