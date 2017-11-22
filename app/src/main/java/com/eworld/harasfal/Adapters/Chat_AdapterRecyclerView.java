package com.eworld.harasfal.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.devbrackets.android.exomedia.listener.OnErrorListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.eworld.harasfal.Classes.Mensagem;
import com.eworld.harasfal.ImageFull;
import com.eworld.harasfal.ImagesGallery;
import com.eworld.harasfal.Interface.RecyclerViewOnClickListenerHack;
import com.eworld.harasfal.PlayerFull;
import com.eworld.harasfal.R;
import com.eworld.harasfal.Utils.ConvertDate;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by celsoribeiro on 05/02/15.
 */

public class Chat_AdapterRecyclerView extends RecyclerView.Adapter<Chat_AdapterRecyclerView.EmpresaViewHolder> {
    private ArrayList<Mensagem> itens;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private Context context;
    private String Link_Picture;
    public static final int Plataforma = 0;
    private static final int Plataforma_Foto = 1;
    private static final int Plataforma_Video = 2;
    private static final int Cliente = 3;
    private static final int Cliente_Foto = 4;
    private static final int Cliente_Video = 5;

    //
    public Chat_AdapterRecyclerView(ArrayList<Mensagem> itens, String Link_Picture, Context cont) {
        this.itens = itens;
        this.Link_Picture = Link_Picture;
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
    public void onBindViewHolder(final EmpresaViewHolder empresaViewHolder, int i) {
        final Mensagem item = itens.get(i);
        //Imagem
        if (item.getTipoMidia().equals("1")) {

            if (item.getFotos() != null) {
                Picasso.with(context).load(item.getFotos().get(0).getLink()).placeholder(R.drawable.semimagem).into(empresaViewHolder.Imag);
                empresaViewHolder.Imag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i;
                        if (item.getFotos().size() > 1) {
                            i = new Intent(context, ImagesGallery.class);
                            ImagesGallery.Fotos = item.getFotos();

                        } else {
                            i = new Intent(context, ImageFull.class);
                            i.putExtra("Image", item.getFotos().get(0).getLink());
                        }
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                });
            }

        } // Video
        else if (item.getTipoMidia().equals("2")) {
            if (item.getVideos() != null) {

                SetThumbnail gt = new SetThumbnail(item.getVideos().get(0).getThumbnail(),empresaViewHolder);
                gt.execute();
                empresaViewHolder.Video_view.setVideoPath(item.getVideos().get(0).getLink());
                //empresaViewHolder.Video_view.pause();
                empresaViewHolder.btn_full.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context, PlayerFull.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        PlayerFull.UrlVideo = item.getVideos().get(0).getLink();
                        context.startActivity(i);
                    }
                });
                empresaViewHolder.Video_view.setOnErrorListener(new OnErrorListener() {
                    @Override
                    public boolean onError(Exception e) {
                        return false;
                    }
                });
                //empresaViewHolder.Video_view.getVideoControls().setCanHide(true);
                //empresaViewHolder.Video_view.start();

            }
        } else { // Texto
            empresaViewHolder.Texto.setText(item.getTexto());
            empresaViewHolder.Data.setText(ConvertDate.ConvertJsonDate(item.getDataEnvio()) + " às " + ConvertDate.ConvertJsonHour(item.getDataEnvio()));
            if (item.getOrigemId().equals("0")) {

            } else if (item.getOrigemId().equals("1")) {

                if (Link_Picture != null && !Link_Picture.equals(""))
                    Picasso.with(context).load(Link_Picture).placeholder(R.drawable.iconuserdefault).into(empresaViewHolder.profile);

            }
        }


    }
    public class SetThumbnail extends AsyncTask<Void, Void, String> {
        String src;
        Bitmap myBitmap;
        EmpresaViewHolder empresaViewHolder;
        public SetThumbnail(String src, EmpresaViewHolder empresaViewHolder) {
            this.src = src;
            this.empresaViewHolder = empresaViewHolder;
        }

        @Override
        protected void onPostExecute(String s) {
            empresaViewHolder.Video_view.setPreviewImage(myBitmap);
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                myBitmap = BitmapFactory.decodeStream(input);

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return null;
        }
    } // Author:
    @Override
    public int getItemViewType(int position) {
        Mensagem item = itens.get(position);

        if (item.getOrigemId().equals("0")) {
            if (item.getTipoMidia().equals("1"))
                return Plataforma_Foto;
            else if (item.getTipoMidia().equals("2"))
                return Plataforma_Video;
            else
                return Plataforma;
        } else {
            if (item.getTipoMidia().equals("1"))
                return Cliente_Foto;
            else if (item.getTipoMidia().equals("2"))
                return Cliente_Video;
            else
                return Cliente;
        }


    }


    @Override
    public EmpresaViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView;
        int res = 0;
        switch (viewType) {
            case Plataforma:
                res = R.layout.chat_cardview0;
                break;
            case Plataforma_Foto:
                res = R.layout.chat_cardview0_image;
                break;
            case Plataforma_Video:
                res = R.layout.chat_cardview0_video;
                break;
            case Cliente:
                res = R.layout.chat_cardview1;
                break;
            case Cliente_Foto:
                res = R.layout.chat_cardview1_image;
                break;
            case Cliente_Video:
                res = R.layout.chat_cardview1_video;
                break;
        }
        itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(res, viewGroup, false);


        EmpresaViewHolder EmpresaViewHolderObject = new EmpresaViewHolder(itemView);

        return EmpresaViewHolderObject;
    }

    public class EmpresaViewHolder extends RecyclerView.ViewHolder {
        protected TextView Texto, Data;
        protected ImageView profile, Imag;
        protected ImageButton btn_full;
        protected VideoView Video_view;

        public EmpresaViewHolder(View v) {
            super(v);
            Texto = (TextView) v.findViewById(R.id.msg);
            Data = (TextView) v.findViewById(R.id.data);
            profile = (ImageView) v.findViewById(R.id.profile);
            Imag = (ImageView) v.findViewById(R.id.imag);
            btn_full = (ImageButton) v.findViewById(R.id.btn_full);
            Video_view = (VideoView) v.findViewById(R.id.video_view);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRecyclerViewOnClickListenerHack != null) {
                        mRecyclerViewOnClickListenerHack.onClickListener(v, getLayoutPosition());
                    }
                }
            });

        }


    }

}




