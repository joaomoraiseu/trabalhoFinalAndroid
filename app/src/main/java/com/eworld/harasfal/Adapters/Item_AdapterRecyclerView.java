package com.eworld.harasfal.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eworld.harasfal.Classes.Item;
import com.eworld.harasfal.ImageFull;
import com.eworld.harasfal.ImagesGallery;
import com.eworld.harasfal.Interface.RecyclerViewOnClickListenerHack;
import com.eworld.harasfal.Interface.RecyclerViewOnClickListenerHack2;
import com.eworld.harasfal.Interface.RecyclerViewOnClickListenerHack3;
import com.eworld.harasfal.Interface.RecyclerViewOnClickListenerHack4;
import com.eworld.harasfal.PlayerFull;
import com.eworld.harasfal.R;
import com.eworld.harasfal.Utils.ConvertDate;
import com.eworld.harasfal.Utils.GetFont;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by celsoribeiro on 05/02/15.
 */

public class Item_AdapterRecyclerView extends RecyclerView.Adapter<Item_AdapterRecyclerView.FeedViewHolder> {
    private ArrayList<Item> itens;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private RecyclerViewOnClickListenerHack2 mRecyclerViewOnClickListenerHack2;
    private RecyclerViewOnClickListenerHack3 mRecyclerViewOnClickListenerHack3;
    private RecyclerViewOnClickListenerHack4 mRecyclerViewOnClickListenerHack4;
    private Context context;
    private boolean PossuiOrcamento;


    public Item_AdapterRecyclerView(ArrayList<Item> itens, Context cont) {
        this.itens = itens;
        this.context = cont;
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r) {
        mRecyclerViewOnClickListenerHack = r;
    }

    public void setRecyclerViewOnClickListenerHack2(RecyclerViewOnClickListenerHack2 r) {
        mRecyclerViewOnClickListenerHack2 = r;
    }

    public void setRecyclerViewOnClickListenerHack3(RecyclerViewOnClickListenerHack3 r) {
        mRecyclerViewOnClickListenerHack3 = r;
    }

    public void setRecyclerViewOnClickListenerHack4(RecyclerViewOnClickListenerHack4 r4) {
        mRecyclerViewOnClickListenerHack4 = r4;
    }

    public void setPossuiOrcamento(boolean p) {
        PossuiOrcamento = p;
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    @Override
    public void onBindViewHolder(final FeedViewHolder feedViewHolder, int i) {
        final int position = i;
        // final int newHeight = context.getResources().getDisplayMetrics().heightPixels / 2;
        final Item item = itens.get(position);

        if (item.getTitulo() != null)
            feedViewHolder.Titulo.setText(item.getTitulo());
        if (item.getData() != null)
            feedViewHolder.Data.setText("Postado em " + ConvertDate.ConvertJsonDate(item.getData()));
        if (item.getDescricao() != null) {
            feedViewHolder.Descricao.setText(item.getDescricao());
            feedViewHolder.Descricao.setVisibility(View.VISIBLE);
        } else {
            feedViewHolder.Descricao.setVisibility(View.INVISIBLE);
        }

        //IMAGEM
        if ((item.getFotos() != null && item.getFotos().size() > 0)) {
            Picasso.with(context).load(item.getFotos().get(0).getLink()).placeholder(R.drawable.semimagem).into(feedViewHolder.promoimagem);

        } else {
            if (item.isVideo())
                feedViewHolder.promoimagem.setImageResource(R.drawable.videoplaceholder);
        }
        //Botoes

        if (item.getFotos() != null && item.getFotos().size() > 1) {
            feedViewHolder.btn_fotos.setVisibility(View.VISIBLE);
        } else {
            feedViewHolder.btn_fotos.setVisibility(View.INVISIBLE);
        }
        if (item.isVideo())
            feedViewHolder.btn_play.setVisibility(View.VISIBLE);
        else feedViewHolder.btn_play.setVisibility(View.INVISIBLE);

        //Listeners
        feedViewHolder.promoimagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getFotos() != null && item.getFotos().toString() != "[]") {
                    String linkfoto = item.getFotos().get(0).getLink();
                    Intent i;
                    if (item.getFotos().size() > 1) {
                        i = new Intent(context, ImagesGallery.class);
                        ImagesGallery.Fotos = item.getFotos();
                    } else {
                        i = new Intent(context, ImageFull.class);
                        i.putExtra("Image", linkfoto);
                    }

                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                } else {
                    if (item.isVideo()) {
                        Intent i = new Intent(context, PlayerFull.class);
                        PlayerFull.UrlVideo = item.getVideos().get(0).getLink();
                        context.startActivity(i);
                    }
                }


            }
        });
        feedViewHolder.btn_fotos.setTypeface(GetFont.RegularFont(context));
        feedViewHolder.btn_fotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ImagesGallery.class);
                ImagesGallery.Fotos = item.getFotos();
                context.startActivity(i);
            }
        });
        feedViewHolder.btn_play.setTypeface(GetFont.RegularFont(context));
        feedViewHolder.btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PlayerFull.class);
                PlayerFull.UrlVideo = item.getVideos().get(0).getLink();
                context.startActivity(i);
            }
        });
        // Curtir e compartilhar
        if (!item.getQtdeComentarios().equals("0"))
            feedViewHolder.contadorcomentarios.setText(item.getQtdeComentarios());
        if (!item.getQtdeCurtidas().equals("0"))
            feedViewHolder.contadorlike.setText(item.getQtdeCurtidas());
        if (item.isCurtido()) {
             feedViewHolder.btn_curtir.setImageResource(R.drawable.heartmarcado);
             feedViewHolder.btn_curtir.setEnabled(false);


        } else {
            feedViewHolder.btn_curtir.setImageResource(R.drawable.heart);
            feedViewHolder.btn_curtir.setEnabled(true);

        }
        feedViewHolder.btn_curtir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mRecyclerViewOnClickListenerHack.onClickListener(v, position);
            }
        });

        feedViewHolder.comentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerViewOnClickListenerHack2.onClickListener_2(v, position);
            }
        });
        feedViewHolder.btn_compartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerViewOnClickListenerHack3.onClickListener_3(v, position);
            }
        });
        feedViewHolder.btn_orcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRecyclerViewOnClickListenerHack4 != null)
                    mRecyclerViewOnClickListenerHack4.onClickListener_4(v, position);
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);

    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_cardview, viewGroup, false);

        FeedViewHolder FeedViewHolderObject = new FeedViewHolder(itemView);

        return FeedViewHolderObject;
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder {
        protected TextView Titulo, Descricao,btn_play, btn_fotos;
        protected TextView Data,contadorlike,contadorcomentarios;;
        protected LinearLayout barraorcamento;
        protected ImageButton btn_curtir, btn_compartilhar, comentarios,btn_orcar;
        protected ImageView promoimagem, promoimagem2, promoimagem3;
        protected ProgressBar progressBar;

        public FeedViewHolder(View v) {
            super(v);

            Titulo = (TextView) v.findViewById(R.id.titulo);
            Data = (TextView) v.findViewById(R.id.data);
            contadorlike = (TextView) v.findViewById(R.id.contadorlike);
            contadorcomentarios = (TextView) v.findViewById(R.id.contadorcomentarios);
            Descricao = (TextView) v.findViewById(R.id.descricao);
            btn_curtir = (ImageButton) v.findViewById(R.id.gostei);
            comentarios = (ImageButton) v.findViewById(R.id.comentar);
            btn_orcar = (ImageButton) v.findViewById(R.id.orcar);
            btn_compartilhar = (ImageButton) v.findViewById(R.id.compartilhar);
            btn_play = (TextView) v.findViewById(R.id.playvideo);
            btn_fotos = (TextView) v.findViewById(R.id.verfotos);
            promoimagem = (ImageView) v.findViewById(R.id.promoimagem);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
            barraorcamento = (LinearLayout) v.findViewById(R.id.barraorcar);
            if (PossuiOrcamento)
                barraorcamento.setVisibility(View.VISIBLE);
            else barraorcamento.setVisibility(View.GONE);

        }

    }


}





