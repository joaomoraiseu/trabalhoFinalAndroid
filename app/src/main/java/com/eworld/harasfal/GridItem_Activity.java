package com.eworld.harasfal;
/**
 * Created by celsoribeiro on 11/03/15.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eworld.harasfal.Adapters.Grid_AdapterRecyclerView;
import com.eworld.harasfal.Asyncs.GetRequestAsyncTask;
import com.eworld.harasfal.Classes.DadosEmpresa;
import com.eworld.harasfal.Classes.Item;
import com.eworld.harasfal.Classes.Pessoa;
import com.eworld.harasfal.Interface.OnPostExecute;
import com.eworld.harasfal.Interface.RecyclerViewOnClickListenerHack;
import com.eworld.harasfal.Utils.ConnectionDetector;
import com.eworld.harasfal.Utils.GetFont;
import com.eworld.harasfal.Utils.SharedPrefs;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by celsoribeiro on 19/02/15.
 */
public class GridItem_Activity extends AppCompatActivity implements OnPostExecute {

    //WebService
    SharedPreferences.Editor editor;
    private SharedPreferences preferences;
    //
    private ArrayList<Item> Itens;
    private RecyclerView mRecyclerView;

    private Grid_AdapterRecyclerView adapterListView2;
    ConnectionDetector cd;
    Type type2;
    GetRequestAsyncTask async;
    Pessoa pessoa;
    public static String Tipo,Titulo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_view);
        SetToolbar();
        cd = new ConnectionDetector(GridItem_Activity.this);
        GetInfoCache();
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        cd = new ConnectionDetector(getBaseContext());
        Boolean isInternetPresent = cd.isConnectingToInternet();
        if(isInternetPresent) {

            async = new GetRequestAsyncTask(GridItem_Activity.this,"Item/GetItem","Itens-"+Tipo);
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
                Toast.makeText(GridItem_Activity.this, "Vocẽ não possui conexão com internet", Toast.LENGTH_LONG).show();
        }

    }


    private void createListView() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        adapterListView2 = new Grid_AdapterRecyclerView(Itens, this);
        adapterListView2.setRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
            @Override
            public void onClickListener(View view, int position) {
                Item item = Itens.get(position);
                Intent i = new Intent(GridItem_Activity.this, Item_Full.class);
                Item_Full.Item = item;
                Item_Full.Titulo =Titulo;
                //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
        mRecyclerView.setAdapter(adapterListView2);


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

    private void GetInfoCache() {

        preferences = getSharedPreferences(getResources().getString(R.string.sharedpreferences), 0);
        editor = preferences.edit();
        String loja_json = preferences.getString("Itens-"+Tipo, null);
        type2 = new TypeToken<ArrayList<Item>>() {}.getType();
        Itens = (ArrayList<Item>) new GsonBuilder().create().fromJson(loja_json, type2);
        pessoa = SharedPrefs.GetPessoa(this);
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
