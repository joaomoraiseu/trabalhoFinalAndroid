package com.eworld.harasfal.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eworld.harasfal.Classes.Foto;
import com.eworld.harasfal.Interface.RecyclerViewOnClickListenerHack;
import com.eworld.harasfal.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by celsoribeiro on 05/02/15.
 */

public class Gallery_AdapterRecyclerView2 extends RecyclerView.Adapter<Gallery_AdapterRecyclerView2.ItemViewHolder> {
    private ArrayList<String>Fotos;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private Context context;

    public Gallery_AdapterRecyclerView2(ArrayList<String> itens, Context cont) {
        this.Fotos = itens;
        this.context = cont;
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r) {
        mRecyclerViewOnClickListenerHack = r;
    }

    @Override
    public int getItemCount() {
        return Fotos.size();

    }

    @Override
    public void onBindViewHolder(final ItemViewHolder ItemViewHolder, int i) {
        final String item = Fotos.get(i);


        if (item!= null)
            Picasso.with(context).load(item).placeholder(R.drawable.semimagem).into(ItemViewHolder.imageview);


    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ItemViewHolder itemViewHolderObject;

            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.gallery_cardview, viewGroup, false);
            itemViewHolderObject = new ItemViewHolder(itemView);

        return itemViewHolderObject;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        protected TextView Titulo,Filiacao,txtlote,Numero;
        protected ImageView imageview;
        //protected ProgressBar progressBar;


        public ItemViewHolder(View v) {
            super(v);

            Titulo = (TextView) v.findViewById(R.id.titulo);
            imageview = (ImageView) v.findViewById(R.id.img_thumbnail);
            //Compartilhar = (Button) v.findViewById(R.id.btn_compartilhar);
            //progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRecyclerViewOnClickListenerHack != null) {
                        mRecyclerViewOnClickListenerHack.onClickListener(v, getLayoutPosition());
                    }
                }
            });
            //Compartilhar.setOnClickListener(this);
            //vTitle = (TextView) v.findViewById(R.id.title);
        }

    }



    }




