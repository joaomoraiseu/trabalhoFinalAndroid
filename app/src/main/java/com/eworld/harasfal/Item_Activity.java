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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eworld.harasfal.Adapters.Item_AdapterRecyclerView;
import com.eworld.harasfal.Asyncs.DownloadVideoImageFile;
import com.eworld.harasfal.Asyncs.GetRequestAsyncTask;
import com.eworld.harasfal.Asyncs.PostRequestAsyncTask;
import com.eworld.harasfal.Classes.DadosEmpresa;
import com.eworld.harasfal.Classes.Item;
import com.eworld.harasfal.Classes.Pessoa;
import com.eworld.harasfal.Interface.OnPostExecute;
import com.eworld.harasfal.Interface.RecyclerViewOnClickListenerHack;
import com.eworld.harasfal.Interface.RecyclerViewOnClickListenerHack2;
import com.eworld.harasfal.Interface.RecyclerViewOnClickListenerHack3;
import com.eworld.harasfal.Interface.RecyclerViewOnClickListenerHack4;
import com.eworld.harasfal.Utils.ConnectionDetector;
import com.eworld.harasfal.Utils.GetFont;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by celsoribeiro on 19/02/15.
 */
public class Item_Activity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback, OnPostExecute, RecyclerViewOnClickListenerHack,RecyclerViewOnClickListenerHack2,RecyclerViewOnClickListenerHack3,RecyclerViewOnClickListenerHack4 {

    //WebService
    SharedPreferences.Editor editor;
    private SharedPreferences preferences;
    //
    private ArrayList<Item> Itens;
    private RecyclerView mRecyclerView;

    private Item_AdapterRecyclerView adapterListView;
    ConnectionDetector cd;
    Type type2;
    GetRequestAsyncTask async;
    PostRequestAsyncTask async2;
    Pessoa pessoa;
    private Integer AnuncioPosition = 0;
    public static String Tipo,Titulo;
    String linkDownload;
    boolean IsvideoCompartilhar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_view);
        SetToolbar();
        cd = new ConnectionDetector(Item_Activity.this);
        GetInfoCache();
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        cd = new ConnectionDetector(getBaseContext());
        Boolean isInternetPresent = cd.isConnectingToInternet();
        if(isInternetPresent) {

            async = new GetRequestAsyncTask(Item_Activity.this,"Item/GetItem","Itens-"+Tipo);
            async.setOnPostExecute(this);
            async.EnableProgressdialog();
            async.AddFirstParam("UnidadeId", DadosEmpresa.UnidadeID);
            async.AddParam("identificador",pessoa.getIdentificador());
            async.AddParam("Tipo",Tipo);
            async.execute();

        }else{
            if(Itens!=null)
                createListView();
            else
                Toast.makeText(Item_Activity.this, "Vocẽ não possui conexão com internet", Toast.LENGTH_LONG).show();
        }

    }


    private void createListView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        adapterListView = new Item_AdapterRecyclerView(Itens, this);
        adapterListView.setRecyclerViewOnClickListenerHack(this);
        adapterListView.setRecyclerViewOnClickListenerHack2(this);
        adapterListView.setRecyclerViewOnClickListenerHack3(this);
        adapterListView.setRecyclerViewOnClickListenerHack4(this);
        mRecyclerView.setAdapter(adapterListView);
        mRecyclerView.scrollToPosition(AnuncioPosition);


    }


    @Override
    public void OnPostExecute() {
        if(async.getResponse().equals("false")){
            //if(test.equals("false")){
            Toast.makeText(this, "Ocorreu um problema, tente novamente...", Toast.LENGTH_LONG).show();   }
        else{
            Itens = (ArrayList<Item>) new GsonBuilder().create().fromJson(async.getResponse(), type2);
            //SetItensinCache(async.getResponse());
            createListView();
        }

    }

    @Override
    public void onClickListener(View view, final int position) {
        Item item = Itens.get(position);
        AnuncioPosition = position;

        Boolean isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            async2 = new PostRequestAsyncTask(Item_Activity.this, "Item/CurtirItem");
            async2.setOnPostExecute(new OnPostExecute() {
                @Override
                public void OnPostExecute() {
                    if(async2.getResponse().equals("true")) {
                        Itens.get(position).setCurtido(true);
                        Gson gson = new Gson();
                        String json = gson.toJson(Itens);
                        createListView();
                    }

                }
            });
            async2.AddParam("ItemId", item.getItemId());
            async2.AddParam("Identificador", pessoa.getIdentificador());
            async2.execute();
        } else {
            Toast.makeText(Item_Activity.this, "Vocẽ não possui conexão com internet, não é possivel executar esta ação.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClickListener_2(View view, int position) {
        Item item = Itens.get(position);
        Intent i = new Intent(Item_Activity.this, ComentariosActivity.class);
        i.putExtra("ItemId", item.getItemId());
        startActivity(i);
    }
    @Override
    public void onClickListener_3(View view, int position) {
        final Item item = Itens.get(position);
        linkDownload = "";

        //Define aqui se é video ou imagem a compartilhar
        if(item.getVideos()!=null && item.getVideos().size()>0){

                linkDownload = item.getVideos().get(0).getLink();
                IsvideoCompartilhar = true;

        }else{
            if (item.getFotos() != null && item.getFotos().size()>0) {

                    linkDownload = item.getFotos().get(0).getLink();
                    IsvideoCompartilhar = false;

            }
        }

        android.app.AlertDialog alerta;
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        if(IsvideoCompartilhar){
            builder.setTitle("Deseja compartilhar o video?");
            builder.setMessage("O video será salvo em seu aparelho");
        }else{
            builder.setTitle("Deseja compartilhar a imagem?");
            builder.setMessage("A imagem será salva em seu aparelho");
        }
        // builder.setMessage("Audio será salvo em :/sdcard/audios_compartilhados/"+getResources().getString(R.string.app_name)+"/");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (ContextCompat.checkSelfPermission(Item_Activity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    DownloadVideoImageFile df = new DownloadVideoImageFile(Item_Activity.this);
                    df.Titulo = item.getTitulo();
                    df.execute(linkDownload);
                } else {

                    ActivityCompat.requestPermissions(Item_Activity.this,
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

    @Override
    public void onClickListener_4(View view, int position) {
        if (pessoa.isCliente()) {
        } else {
            AlertDialog alerta;
            AlertDialog.Builder builder = new AlertDialog.Builder(Item_Activity.this);
            builder.setTitle("Cadastre-se para solicitar um orçamento");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(Item_Activity.this, CadastroActivity.class);
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"Clique novamente para compartilhar a imagem",Toast.LENGTH_LONG).show();
        }else
        {
            Toast.makeText(this,"Sem essa permissão não é possivel compartilhar a imagem",Toast.LENGTH_LONG).show();
        }
    }

    private void GetInfoCache() {

        preferences = getSharedPreferences(getResources().getString(R.string.sharedpreferences), 0);
        editor = preferences.edit();
        String loja_json = preferences.getString("Itens-"+Tipo, null);
        String json = preferences.getString("Pessoa", null);
        type2 = new TypeToken<ArrayList<Item>>() {}.getType();
        Type type = new TypeToken<Pessoa>() {}.getType();
        Itens = (ArrayList<Item>) new GsonBuilder().create().fromJson(loja_json, type2);
        pessoa = (Pessoa) new GsonBuilder().create().fromJson(json, type);
    }





    private void SetToolbar() {
        TextView tite;
        tite = (TextView) findViewById(R.id.tite);
        tite.setTypeface(GetFont.RegularFont(this));
        tite.setText(Titulo);
        ImageView reply = (ImageView) findViewById(R.id.reply);
        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
    }


}
