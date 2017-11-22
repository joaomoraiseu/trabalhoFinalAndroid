package com.eworld.harasfal.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.eworld.harasfal.Classes.Comentario;
import com.eworld.harasfal.Interface.RecyclerViewOnClickListenerHack;
import com.eworld.harasfal.Interface.RecyclerViewOnClickListenerHack2;
import com.eworld.harasfal.Interface.RecyclerViewOnClickListenerHack3;
import com.eworld.harasfal.R;
import com.eworld.harasfal.Utils.ConvertDate;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by celsoribeiro on 05/02/15.
 */

public class Comentarios_AdapterRecyclerView extends RecyclerView.Adapter<Comentarios_AdapterRecyclerView.ComentarioViewHolder> {
    private ArrayList<Comentario> Comentarios;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private RecyclerViewOnClickListenerHack2 mRecyclerViewOnClickListenerHack2;
    private RecyclerViewOnClickListenerHack3 mRecyclerViewOnClickListenerHack3;
    private Context context;


    public Comentarios_AdapterRecyclerView(ArrayList<Comentario> comentarios, Context cont) {

        this.Comentarios = comentarios;
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

    @Override
    public int getItemCount() {
        return Comentarios.size();
    }

    @Override
    public void onBindViewHolder(final ComentarioViewHolder ViewHolder, int i) {
        final int position = i;
        final Comentario com = Comentarios.get(position);

        if (com.isComentarioProprio())
            ViewHolder.Delete.setVisibility(View.VISIBLE);
        else
            ViewHolder.Delete.setVisibility(View.INVISIBLE);

        if (com.getNomePessoa() != null)
            ViewHolder.Nome.setText(com.getNomePessoa());
        if (com.getTexto() != null)
            ViewHolder.Texto.setText(com.getTexto());
        if (com.getDataComentario() != null)
            ViewHolder.data.setText(ConvertDate.ConvertJsonDate(com.getDataComentario()) + " às " + ConvertDate.ConvertJsonHour(com.getDataComentario()));
        if (com.getQtdeCurtidas() != null && com.getQtdeCurtidas() != "0") {
            ViewHolder.contadorlike.setText(com.getQtdeCurtidas());
            ViewHolder.contadorlike.setVisibility(View.VISIBLE);
        } else {
            ViewHolder.contadorlike.setVisibility(View.INVISIBLE);
        }
        if (com.getQtdeDescurtidas() != null && com.getQtdeDescurtidas() != "0") {
            ViewHolder.contadordislike.setText(com.getQtdeDescurtidas());
            ViewHolder.contadordislike.setVisibility(View.VISIBLE);
        } else {
            ViewHolder.contadordislike.setVisibility(View.INVISIBLE);
        }
        if (com.getOrigemId().equals("0"))
            ViewHolder.Foto.setImageResource(R.drawable.ic);
        else {
            if (com.getLinkFoto() != null) {
                try {
                    Picasso.with(context).load(com.getLinkFoto()).placeholder(R.drawable.iconuserdefault).into(ViewHolder.Foto);
                } catch (Exception e) {
                }
            }else ViewHolder.Foto.setImageResource(R.drawable.iconuserdefault);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);

    }

    @Override
    public ComentarioViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.comentario_cardview, viewGroup, false);

        ComentarioViewHolder ViewHolderObject = new ComentarioViewHolder(itemView);

        return ViewHolderObject;
    }

    public class ComentarioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView Nome;
        protected TextView Texto, contadorlike, contadordislike, data;
        protected ImageView Foto;
        protected ImageButton Delete, curtir, descurtir;

        public ComentarioViewHolder(View v) {
            super(v);

            Nome = (TextView) v.findViewById(R.id.nome);
            Texto = (TextView) v.findViewById(R.id.texto);
            data = (TextView) v.findViewById(R.id.data);
            curtir = (ImageButton) v.findViewById(R.id.curtir);
            descurtir = (ImageButton) v.findViewById(R.id.descurtir);
            Delete = (ImageButton) v.findViewById(R.id.delete);
            contadorlike = (TextView) v.findViewById(R.id.contadorlike);
            contadordislike = (TextView) v.findViewById(R.id.contadordislike);
            Foto = (ImageView) v.findViewById(R.id.fotos);
            curtir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRecyclerViewOnClickListenerHack2 != null) {
                        mRecyclerViewOnClickListenerHack2.onClickListener_2(v, getLayoutPosition());
                    }
                }
            });
            descurtir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRecyclerViewOnClickListenerHack3 != null) {
                        mRecyclerViewOnClickListenerHack3.onClickListener_3(v, getLayoutPosition());
                    }
                }
            });
            Delete.setOnClickListener(this);

            //promoimagem.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        @Override
        public void onClick(View v) {
            if (mRecyclerViewOnClickListenerHack != null) {
                mRecyclerViewOnClickListenerHack.onClickListener(v, getLayoutPosition());
            }

        }
    }


}





