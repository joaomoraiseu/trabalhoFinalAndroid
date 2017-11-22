package com.eworld.harasfal;

/**
 * Created by celsoribeiro on 11/03/15.
 */

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eworld.harasfal.Adapters.Chat_AdapterRecyclerView;
import com.eworld.harasfal.Asyncs.GetRequestAsyncTask;
import com.eworld.harasfal.Asyncs.PostRequestAsyncTask;
import com.eworld.harasfal.Classes.DadosEmpresa;
import com.eworld.harasfal.Classes.Mensagem;
import com.eworld.harasfal.Classes.Pessoa;
import com.eworld.harasfal.Interface.OnPostExecute;
import com.eworld.harasfal.Utils.ConnectionDetector;
import com.eworld.harasfal.Utils.GetFont;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by celsoribeiro on 19/02/15.
 */
public class Chat_Activity extends AppCompatActivity implements Runnable, OnPostExecute {
    private RecyclerView mRecyclerView;
    private Chat_AdapterRecyclerView adapterListView;
    private RecyclerView.LayoutManager mLayoutManager;
    // private ArrayList<Mensagem> itens;
    //
    //WebService
    private SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private String Mensagem;
    Pessoa pessoa;
    Type type2;
    GetRequestAsyncTask async;
    PostRequestAsyncTask async2;
    //
    private ArrayList<com.eworld.harasfal.Classes.Mensagem> Mensagens;
   // private ArrayList<com.evoxtech.realortoclinica.Classes.Mensagem> cash_recebermsg;
    ConnectionDetector cd;
    private TextView Ola;
    Handler handler;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_view);
        //SetToolbar();
        Ola = (TextView) findViewById(R.id.ola);
        cd = new ConnectionDetector(this);
        GetInfoCache();
        GetPessoa();
        final EditText sendtext = (EditText) findViewById(R.id.sendtext);
        final Button btn_enviar = (Button) findViewById(R.id.send);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isInternetPresent = cd.isConnectingToInternet();
                if (isInternetPresent) {
                    if (sendtext.getText().toString().length() > 0) {

                        if (pessoa.isCliente()) {
                            Mensagem = sendtext.getText().toString();
                            async2 = new PostRequestAsyncTask(Chat_Activity.this, "Mensagem/EnviarMensagem");
                            async2.EnableProgressdialog();
                            async2.AddParam("UnidadeId", DadosEmpresa.UnidadeID);
                            async2.AddParam("Identificador", pessoa.getIdentificador());
                            async2.AddParam("Texto",Mensagem);
                            async2.setOnPostExecute(new OnPostExecute() {
                                @Override
                                public void OnPostExecute() {
                                    if(async2.getResponse().equals("true"))
                                        RequestGetMensagens();
                                }
                            });
                            async2.execute();
                            sendtext.setText("");
                            hideKeyboard(Chat_Activity.this);
                            try {
                                mRecyclerView.scrollToPosition(Mensagens.size() - 1);
                            } catch (Exception e) {
                            }

                        } else {
                            AlertDialog alerta;
                            AlertDialog.Builder builder = new AlertDialog.Builder(Chat_Activity.this);
                            builder.setTitle("Cadastre-se para enviar mensagens");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(Chat_Activity.this, CadastroActivity.class);
                                    startActivity(i);
                                }
                            });
                            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface arg0, int arg1) {

                                }
                            });
                            alerta = builder.create();
                            alerta.show();
                            //if (!LOJA.isDadosCliente() && !dadoscliente) {

                        }


                    }
                } else {
                    Toast.makeText(Chat_Activity.this, "Vocẽ não possui conexão com internet, não é possivel enviar mensagens.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (Mensagens != null)
            createListView();
        handler = new Handler();
        run();
        /*else
            Toast.makeText(Chat_Activity.this, "Vocẽ não possui conexão com internet", Toast.LENGTH_LONG).show();*/
    }


    private void createListView() {

        //Criamos nossas listas que preencherão o ListView
        try {
            adapterListView = new Chat_AdapterRecyclerView(Mensagens, pessoa.getLinkFoto(), Chat_Activity.this);
            mRecyclerView.setAdapter(adapterListView);
            mRecyclerView.scrollToPosition(Mensagens.size() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void GetInfoCache() {

        preferences = getSharedPreferences(getResources().getString(R.string.sharedpreferences), 0);
        editor = preferences.edit();
        String loja_json = preferences.getString("Mensagens", null);
        type2 = new TypeToken<ArrayList<Mensagem>>() {
        }.getType();
        Mensagens = (ArrayList<Mensagem>) new GsonBuilder().create().fromJson(loja_json, type2);
    }

    private void GetPessoa() {
        String json = preferences.getString("Pessoa", null);
        Type type = new TypeToken<Pessoa>() {
        }.getType();
        pessoa = (Pessoa) new GsonBuilder().create().fromJson(json, type);

    }


    public String GetTimeNow() {

        //DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        DateFormat df = new SimpleDateFormat("HH:mm");
        String hour = df.format(Calendar.getInstance().getTime());
        return hour;


    }


    public static void hideKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (activity.getCurrentFocus() != null && inputManager != null) {
                inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                inputManager.showSoftInputFromInputMethod(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }



    private void SetToolbar() {
        TextView tite,site;
        tite = (TextView) findViewById(R.id.tite);
        tite.setTypeface(GetFont.RegularFont(this));
        tite.setText("Chat");
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


    @Override
    protected void onResume() {
        super.onResume();
        GetPessoa();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(this);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        run();
    }

    @Override
    public void run() {

        handler.removeCallbacks(this);
        Boolean isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            RequestGetMensagens();
        }

        handler.postDelayed(this, 5000);

    }

private void RequestGetMensagens(){
    async = new GetRequestAsyncTask(Chat_Activity.this, "Mensagem/GetMensagens","Mensagens");
    async.setOnPostExecute(this);
    async.AddFirstParam("UnidadeId", DadosEmpresa.UnidadeID);
    async.AddParam("Identificador", pessoa.getIdentificador());
    //async.AddParam("Texto","Hi Jhon!");
    async.execute();
}
    @Override
    public void OnPostExecute() {
        if (async.getResponse() != null) {
            if (async.getResponse().equals("false")) {
                //if(test.equals("false")){
                Snackbar.make(findViewById(android.R.id.content), "Ocorreu um problema ao carregar, tente novamente...", Snackbar.LENGTH_LONG).show();
            } else {
                try {
                    ArrayList<Mensagem> retorno = (ArrayList<Mensagem>) new GsonBuilder().create().fromJson(async.getResponse(), type2);
                    if (Mensagens == null) {
                        Mensagens = retorno;
                        createListView();
                        //SetMensagensinCache(async.getResponse());
                    } else {
                        if (retorno.size() != Mensagens.size()) {
                            Mensagens = retorno;
                            createListView();
                            //SetMensagensinCache(async.getResponse());
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }
    }
}
