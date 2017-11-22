package com.eworld.harasfal;

/**
 * Created by celsoribeiro on 11/03/15.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eworld.harasfal.Asyncs.DownloadVideoImageFile;
import com.eworld.harasfal.Asyncs.PostRequestAsyncTask;
import com.eworld.harasfal.Classes.Item;
import com.eworld.harasfal.Classes.Pessoa;
import com.eworld.harasfal.Interface.OnPostExecute;
import com.eworld.harasfal.Utils.ConnectionDetector;
import com.eworld.harasfal.Utils.ConvertDate;
import com.eworld.harasfal.Utils.GetFont;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;

/**
 * Created by celsoribeiro on 19/02/15.
 */
public class Item_Full extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    //WebService
    public static com.eworld.harasfal.Classes.Item Item;
    ConnectionDetector cd;
    PostRequestAsyncTask async2;
    SharedPreferences preferences;
    Pessoa pessoa;
    public static String Titulo;
    String linkDownload;
    boolean IsvideoCompartilhar;
    private boolean PossuiOrcamento;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_full);
        Intent i = getIntent();
        PossuiOrcamento = i.getBooleanExtra("PossuiOrcamento",false);
        SetToolbar();
        GetPessoa();
        cd = new ConnectionDetector(Item_Full.this);
       //CreateView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        CreateView();
    }

    private void CreateView() {

        final ItemViewHolder itemViewHolder = new ItemViewHolder();
        if (Item.getTitulo() != null) {
            itemViewHolder.Titulo.setText(Item.getTitulo());
            //itemViewHolder.Titulo.setTypeface(GetFont.RegularFont(this));
        }
        if (Item.getData() != null) {
            itemViewHolder.Data.setText("Postado em " + ConvertDate.ConvertJsonDate(Item.getData()));
            //itemViewHolder.Data.setTypeface(GetFont.RegularFont(this));
        }
        if (Item.getDescricao() != null) {
            itemViewHolder.descricao.setText(Item.getDescricao());
            //itemViewHolder.descricao.setTypeface(GetFont.RegularFont(this));
            itemViewHolder.descricao.setVisibility(View.VISIBLE);
        } else {
            itemViewHolder.descricao.setVisibility(View.INVISIBLE);
        }
        if (Item.getObservacao() != null) {
            itemViewHolder.filiacao.setText(Item.getObservacao());
            //itemViewHolder.filiacao.setTypeface(GetFont.RegularFont(this));

        }

        //IMAGEM
        if ((Item.getFotos() != null && Item.getFotos().size() > 0)) {
            Picasso.with(this).load(Item.getFotos().get(0).getLink()).placeholder(R.drawable.semimagem).into(itemViewHolder.promoimagem);

        } else {
            if (Item.isVideo())
                itemViewHolder.promoimagem.setImageResource(R.drawable.videoplaceholder);
        }
        //Botoes

        if (Item.getFotos() != null && Item.getFotos().size() > 1) {
            itemViewHolder.btn_fotos.setVisibility(View.VISIBLE);
        } else {
            itemViewHolder.btn_fotos.setVisibility(View.INVISIBLE);
        }
        if (Item.isVideo())
            itemViewHolder.btn_play.setVisibility(View.VISIBLE);
        else itemViewHolder.btn_play.setVisibility(View.INVISIBLE);

        //Listeners
        itemViewHolder.promoimagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Item.getFotos() != null && Item.getFotos().toString() != "[]") {
                    String linkfoto = Item.getFotos().get(0).getLink();
                    Intent i;
                    if (Item.getFotos().size() > 1) {
                        i = new Intent(Item_Full.this, ImagesGallery.class);
                        ImagesGallery.Fotos = Item.getFotos();
                    } else {
                        i = new Intent(Item_Full.this, ImageFull.class);
                        i.putExtra("Image", linkfoto);
                    }

                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                } else {
                    if (Item.isVideo()) {
                        Intent i = new Intent(Item_Full.this, PlayerFull.class);
                        PlayerFull.UrlVideo = Item.getVideos().get(0).getLink();
                        startActivity(i);
                    }
                }


            }
        });
        itemViewHolder.btn_fotos.setTypeface(GetFont.RegularFont(this));
        itemViewHolder.btn_fotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Item_Full.this, ImagesGallery.class);
                ImagesGallery.Fotos = Item.getFotos();
                startActivity(i);
            }
        });
        itemViewHolder.btn_play.setTypeface(GetFont.RegularFont(this));
        itemViewHolder.btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Item_Full.this, PlayerFull.class);
                PlayerFull.UrlVideo = Item.getVideos().get(0).getLink();
                startActivity(i);
            }
        });

        // Curtir e compartilhar
        if (!Item.getQtdeComentarios().equals("0"))
            itemViewHolder.contadorcomentarios.setText(Item.getQtdeComentarios());
        if (!Item.getQtdeCurtidas().equals("0"))
            itemViewHolder.contadorlike.setText(Item.getQtdeCurtidas());

        if (Item.isCurtido()) {
            itemViewHolder.btn_curtir.setImageResource(R.drawable.heartmarcado);
            itemViewHolder.btn_curtir.setEnabled(false);


        } else {
            itemViewHolder.btn_curtir.setImageResource(R.drawable.heart);
            itemViewHolder.btn_curtir.setEnabled(true);
        }
        itemViewHolder.btn_curtir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Boolean isInternetPresent = cd.isConnectingToInternet();
                if (isInternetPresent) {
                    async2 = new PostRequestAsyncTask(Item_Full.this, "Item/CurtirItem");
                    async2.setOnPostExecute(new OnPostExecute() {
                        @Override
                        public void OnPostExecute() {
                            if (async2.getResponse().equals("true")) {
                                Item.setCurtido(true);
                                itemViewHolder.btn_curtir.setImageResource(R.drawable.heartmarcado);
                                itemViewHolder.btn_curtir.setEnabled(false);

                            }

                        }
                    });
                    async2.AddParam("ItemId", Item.getItemId());
                    async2.AddParam("Identificador", pessoa.getIdentificador());
                    async2.execute();
                } else {
                    Toast.makeText(Item_Full.this, "Vocẽ não possui conexão com internet, não é possivel executar esta ação.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        itemViewHolder.btn_comentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Item_Full.this, ComentariosActivity.class);
                i.putExtra("ItemId", Item.getItemId());
                startActivity(i);
            }
        });
        itemViewHolder.btn_compartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linkDownload = "";

                //Define aqui se é video ou imagem a compartilhar
                if (Item.getVideos() != null && Item.getVideos().size() > 0) {
                        linkDownload = Item.getVideos().get(0).getLink();
                        IsvideoCompartilhar = true;

                } else {
                    if (Item.getFotos() != null && Item.getFotos().size() > 0) {
                            linkDownload = Item.getFotos().get(0).getLink();
                            IsvideoCompartilhar = false;

                    }
                }

                android.app.AlertDialog alerta;
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Item_Full.this);
                if (IsvideoCompartilhar) {
                    builder.setTitle("Deseja compartilhar o video?");
                    builder.setMessage("O video será salvo em seu aparelho");
                } else {
                    builder.setTitle("Deseja compartilhar a imagem?");
                    builder.setMessage("A imagem será salva em seu aparelho");
                }
                // builder.setMessage("Audio será salvo em :/sdcard/audios_compartilhados/"+getResources().getString(R.string.app_name)+"/");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (ContextCompat.checkSelfPermission(Item_Full.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                            DownloadVideoImageFile df = new DownloadVideoImageFile(Item_Full.this);
                            df.Titulo = Item.getTitulo();
                            df.execute(linkDownload);
                        } else {

                            ActivityCompat.requestPermissions(Item_Full.this,
                                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                        }

                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
                alerta = builder.create();
                alerta.show();
            }
        });

        itemViewHolder.btn_orcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pessoa.isCliente()) {
                } else {
                    AlertDialog alerta;
                    AlertDialog.Builder builder = new AlertDialog.Builder(Item_Full.this);
                    builder.setTitle("Cadastre-se para solicitar um orçamento");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(Item_Full.this, CadastroActivity.class);
                            startActivity(i);
                        }
                    });
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });
                    alerta = builder.create();
                    alerta.show();
                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Clique novamente para compartilhar", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Sem essa permissão não é possivel compartilhar", Toast.LENGTH_LONG).show();
        }
    }

    private void SetToolbar() {
        TextView tite, site;
        tite = (TextView) findViewById(R.id.tite);
        tite.setTypeface(GetFont.RegularFont(this));
        tite.setText(Titulo);
       /* site = (TextView) findViewById(R.id.site);
        site.setTypeface(GetFont.RegularFont(this));
        site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
        ImageView reply = (ImageView) findViewById(R.id.reply);
        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
    }

    public class ItemViewHolder {
        protected TextView Titulo;
        protected TextView Data,contadorlike,contadorcomentarios;
        protected LinearLayout barraorcamento;
        protected ImageButton btn_curtir, btn_compartilhar, btn_comentar,btn_orcar;
        protected TextView descricao, filiacao, btn_play, btn_fotos;
        protected ImageView promoimagem;

        public ItemViewHolder() {

            btn_curtir = (ImageButton) findViewById(R.id.gostei);
            btn_comentar = (ImageButton) findViewById(R.id.comentar);
            btn_compartilhar = (ImageButton) findViewById(R.id.compartilhar);
            Titulo = (TextView) findViewById(R.id.titulo);
            contadorlike = (TextView) findViewById(R.id.contadorlike);
            contadorcomentarios = (TextView) findViewById(R.id.contadorcomentarios);
            filiacao = (TextView) findViewById(R.id.filiacao);
            btn_play = (TextView) findViewById(R.id.playvideo);
            btn_orcar = (ImageButton) findViewById(R.id.orcar);
            btn_fotos = (TextView) findViewById(R.id.verfotos);
            Data = (TextView) findViewById(R.id.data);
            descricao = (TextView) findViewById(R.id.descricao);
            promoimagem = (ImageView) findViewById(R.id.promoimagem);
            barraorcamento = (LinearLayout) findViewById(R.id.barraorcar);
            if (PossuiOrcamento)
                barraorcamento.setVisibility(View.VISIBLE);
            else barraorcamento.setVisibility(View.GONE);

        }

    }

    private void GetPessoa() {

        preferences = getSharedPreferences(getResources().getString(R.string.sharedpreferences), 0);
        String json = preferences.getString("Pessoa", null);
        Type typee = new TypeToken<Pessoa>() {
        }.getType();
        pessoa = (Pessoa) new GsonBuilder().create().fromJson(json, typee);


    }

}

