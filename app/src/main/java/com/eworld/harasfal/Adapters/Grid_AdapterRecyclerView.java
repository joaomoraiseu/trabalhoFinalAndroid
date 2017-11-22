package com.eworld.harasfal.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eworld.harasfal.Classes.Item;
import com.eworld.harasfal.Interface.RecyclerViewOnClickListenerHack;
import com.eworld.harasfal.R;
import com.eworld.harasfal.Utils.GetFont;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by celsoribeiro on 05/02/15.
 */

public class Grid_AdapterRecyclerView extends RecyclerView.Adapter<Grid_AdapterRecyclerView.ItemViewHolder> {
    private ArrayList<Item> itens;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private Context context;

    public Grid_AdapterRecyclerView(ArrayList<Item> itens, Context cont) {
        this.itens = itens;
        this.context = cont;
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r) {
        mRecyclerViewOnClickListenerHack = r;
    }

    @Override
    public int getItemCount() {
        return itens.size();

    }

    @Override
    public void onBindViewHolder(final ItemViewHolder ItemViewHolder, int i) {
        final Item item = itens.get(i);

        if (item.getTitulo() != null) {

            ItemViewHolder.Titulo.setText(item.getTitulo());
            //ItemViewHolder.Titulo.setTypeface(GetFont.RegularFont(context));
            //ItemViewHolder.Descricao.setVisibility(View.VISIBLE);
        }
        if (item.getObservacao() != null) {

            ItemViewHolder.Filiacao.setText(item.getObservacao());
            ItemViewHolder.Filiacao.setVisibility(View.VISIBLE);
            //ItemViewHolder.Descricao.setTypeface(GetFont.RegularFont(context));
            //ItemViewHolder.Descricao.setVisibility(View.VISIBLE);
        }
        else
            ItemViewHolder.Filiacao.setVisibility(View.INVISIBLE);


        if (item.getFotos() != null && item.getFotos().size() > 0)
            Picasso.with(context).load(item.getFotos().get(0).getLink()).placeholder(R.drawable.semimagem).into(ItemViewHolder.imageview);
        else {
            if (item.isVideo())
                ItemViewHolder.imageview.setImageResource(R.drawable.videoplaceholder);
            else
                ItemViewHolder.imageview.setImageResource(R.drawable.semimagem);
        }

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
                    inflate(R.layout.grid_cardview, viewGroup, false);
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
            Filiacao = (TextView) v.findViewById(R.id.filiacao);
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




