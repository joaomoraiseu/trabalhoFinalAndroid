package com.eworld.harasfal;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eworld.harasfal.Adapters.Comentarios_AdapterRecyclerView;
import com.eworld.harasfal.Asyncs.GetRequestAsyncTask;
import com.eworld.harasfal.Asyncs.PostRequestAsyncTask;
import com.eworld.harasfal.Classes.Comentario;
import com.eworld.harasfal.Classes.Pessoa;
import com.eworld.harasfal.Interface.OnPostExecute;
import com.eworld.harasfal.Interface.RecyclerViewOnClickListenerHack;
import com.eworld.harasfal.Interface.RecyclerViewOnClickListenerHack2;
import com.eworld.harasfal.Interface.RecyclerViewOnClickListenerHack3;
import com.eworld.harasfal.Utils.ConnectionDetector;
import com.eworld.harasfal.Utils.GetFont;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ComentariosActivity extends AppCompatActivity implements OnPostExecute, RecyclerViewOnClickListenerHack, RecyclerViewOnClickListenerHack2, RecyclerViewOnClickListenerHack3 {


    private RecyclerView mRecyclerView;
    private Comentarios_AdapterRecyclerView adapterListView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Comentario> Comentarios;
    Pessoa pessoa;
    ConnectionDetector cd;
    Type type2;
    GetRequestAsyncTask async;
    PostRequestAsyncTask async2;
    PostRequestAsyncTask async3;
    private Integer AnuncioPosition = 0;
    TextView tite,empty_view;
    String ItemId;
    int qtdcurtidas;
    int qtddescurtidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comentarios_view);
        SetToolbar();
        GetPessoa();
        Intent i = getIntent();
        ItemId = i.getStringExtra("ItemId");

        cd = new ConnectionDetector(ComentariosActivity.this);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(ComentariosActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.enviarcomentario);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pessoa.isCliente())
                    createInput();
                else {
                    AlertDialog alerta;
                    AlertDialog.Builder builder = new AlertDialog.Builder(ComentariosActivity.this);
                    builder.setTitle("Cadastre-se para comentar");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(ComentariosActivity.this, CadastroActivity.class);
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
        Boolean isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            GetComments();

        } else {

            Toast.makeText(ComentariosActivity.this, "Vocẽ não possui conexão com internet, não é possivel carregar os btn_comentar.", Toast.LENGTH_LONG).show();
        }

    }

    private void createInput() {
        final EditText input = new EditText(ComentariosActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);

        AlertDialog.Builder builder = new AlertDialog.Builder(ComentariosActivity.this);
        builder.setTitle("Enviar Comentario");
        builder.setView(input);
        builder.setCancelable(true);
        builder.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                if (input.getText().toString().length() > 0) {

                    SendComment(input.getText().toString());

                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        }).show();
    }

    private void GetComments() {
        async = new GetRequestAsyncTask(ComentariosActivity.this, "Item/GetComentarios", null);
        async.setOnPostExecute(this);
        async.EnableProgressdialog();
        async.AddFirstParam("ItemId", ItemId);
        async.AddParam("Identificador", pessoa.getIdentificador());
        //async.AddParam("Tipo","1");
        //async.AddParam("Texto","Hi Jhon!");
        async.execute();
    }

    private void SendComment(String Texto) {
        async2 = new PostRequestAsyncTask(ComentariosActivity.this, "Item/ComentarItem");
        async2.AddParam("ItemId", ItemId);
        async2.AddParam("Identificador", pessoa.getIdentificador());
        async2.AddParam("Texto", Texto);
        async2.setOnPostExecute(new OnPostExecute() {
            @Override
            public void OnPostExecute() {
                if (async2.getResponse().equals("true"))
                    GetComments();
            }
        });
        async2.execute();
    }

    private void CurtirDescurtirComments(final int position, final boolean curtiu) {

        final Comentario c = Comentarios.get(position);
        qtdcurtidas = Integer.parseInt(c.getQtdeCurtidas());
        qtddescurtidas = Integer.parseInt(c.getQtdeDescurtidas());
        final PostRequestAsyncTask async = new PostRequestAsyncTask(ComentariosActivity.this, "Item/LikeOuDislikeComentario");
        async.setOnPostExecute(new OnPostExecute() {
            @Override
            public void OnPostExecute() {
                if (async.getResponse().equals("true")) {
                    if (curtiu) {
                        qtdcurtidas++;
                        Comentarios.get(position).setQtdeCurtidas(String.valueOf(qtdcurtidas));
                    } else {
                        qtddescurtidas++;
                        Comentarios.get(position).setQtdeDescurtidas(String.valueOf(qtddescurtidas));
                    }

                    adapterListView.notifyItemChanged(position);
                }

            }
        });
        async.AddParam("ComentarioId", c.getComentarioId());
        async.AddParam("Curtiu", String.valueOf(curtiu));
        async.execute();

    }

    private void DeleteComment(String ComentarioId) {
        async3 = new PostRequestAsyncTask(ComentariosActivity.this, "Item/ExcluirComentario");
        async3.AddParam("ComentarioId", ComentarioId);
        async3.setOnPostExecute(new OnPostExecute() {
            @Override
            public void OnPostExecute() {
                if (async3.getResponse().equals("true"))
                    GetComments();
            }
        });
        async3.execute();
    }

    private void createListView() {
       /* if (Comentarios.size() == 0)
            tite.setText("Seja o primeiro a comentar");
        if (Comentarios.size() == 1)
            tite.setText(Comentarios.size() + " Comentário");
        if (Comentarios.size() > 1)
            tite.setText(Comentarios.size() + " Comentários");*/
        if (Comentarios.size() > 0)
            empty_view.setVisibility(View.INVISIBLE);
        else  empty_view.setVisibility(View.VISIBLE);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        adapterListView = new Comentarios_AdapterRecyclerView(Comentarios, ComentariosActivity.this);
        adapterListView.setRecyclerViewOnClickListenerHack(this);
        adapterListView.setRecyclerViewOnClickListenerHack2(this);
        adapterListView.setRecyclerViewOnClickListenerHack3(this);
        mRecyclerView.setAdapter(adapterListView);
        mRecyclerView.scrollToPosition(AnuncioPosition);

    }


    @Override
    public void OnPostExecute() {
        if (async.getResponse().equals("false")) {
            //if(test.equals("false")){
            Toast.makeText(this, "Ocorreu um problema, tente novamente...", Toast.LENGTH_LONG).show();
        } else {
            type2 = new TypeToken<ArrayList<Comentario>>() {
            }.getType();
            Comentarios = (ArrayList<Comentario>) new GsonBuilder().create().fromJson(async.getResponse(), type2);
            createListView();
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void GetPessoa() {
        SharedPreferences preferences = getSharedPreferences(getResources().getString(R.string.sharedpreferences), 0);
        String json = preferences.getString("Pessoa", null);
        type2 = new TypeToken<Pessoa>() {
        }.getType();
        pessoa = (Pessoa) new GsonBuilder().create().fromJson(json, type2);

    }


    private void SetToolbar() {

        tite = (TextView) findViewById(R.id.tite);
        tite.setTypeface(GetFont.RegularFont(this));

        empty_view = (TextView) findViewById(R.id.empty_view);
        empty_view.setTypeface(GetFont.RegularFont(this));
        //tite.setText(Titulo);
        ImageView reply = (ImageView) findViewById(R.id.reply);
        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
    }


    @Override
    public void onClickListener(View view, int position) {
        final Comentario c = Comentarios.get(position);
        AnuncioPosition = position;
        AlertDialog.Builder builder = new AlertDialog.Builder(ComentariosActivity.this);
        builder.setTitle("Deletar comentario");
        builder.setCancelable(true);
        builder.setPositiveButton("Deletar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                DeleteComment(c.getComentarioId());
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        }).show();

    }

    @Override
    public void onClickListener_2(View view, int position) {
        AnuncioPosition = position;
        CurtirDescurtirComments(position, true);
    }

    @Override
    public void onClickListener_3(View view, int position) {
        AnuncioPosition = position;
        CurtirDescurtirComments(position, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetPessoa();
    }

}
