package com.eworld.harasfal;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.eworld.harasfal.Asyncs.GetRequestAsyncTask;
import com.eworld.harasfal.Asyncs.PostRequestAsyncTask;
import com.eworld.harasfal.Classes.DadosEmpresa;
import com.eworld.harasfal.Classes.Pessoa;
import com.eworld.harasfal.Interface.OnPostExecute;
import com.eworld.harasfal.Push.NotificationOpenedHandler;
import com.eworld.harasfal.Utils.ConnectionDetector;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.onesignal.OneSignal;

import java.lang.reflect.Type;

public class Valida_CarregaDados extends AppCompatActivity {
    //WebService
    private SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ConnectionDetector cd;
    PostRequestAsyncTask async2;
    GetRequestAsyncTask async;
    Type type2;
    Pessoa pessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen );
        OneSignal.startInit(this).setNotificationOpenedHandler(new NotificationOpenedHandler()).init();
        //OneSignal.startInit(this).init();
        GetPessoa();
        cd = new ConnectionDetector(getApplicationContext());
        Boolean isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            async2 = new PostRequestAsyncTask(Valida_CarregaDados.this, "Pessoa/Login");
            async2.AddParam("UnidadeId", DadosEmpresa.UnidadeID);
            async2.AddParam("Identificador", getIdentificador());
            async2.AddParam("Dispositivo", "1");
            async2.AddParam("OneSignalId", "");
            async2.setOnPostExecute(new OnPostExecute() {
                @Override
                public void OnPostExecute() {
                    if (async2.getResponse().equals("false")) {
                        if (pessoa != null) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent i = new Intent(Valida_CarregaDados.this, MenuActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            }, 1000);
                        } else {
                            Toast.makeText(Valida_CarregaDados.this, "Ocorreu um problema, tente novamente....", Toast.LENGTH_LONG).show();


                        }

                    } else {
                        SetPessoa();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent i = new Intent(Valida_CarregaDados.this, MenuActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }, 1000);
                    }

                }
            });
            async2.execute();

        } else {
            if (pessoa != null) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(Valida_CarregaDados.this, MenuActivity.class);
                        startActivity(i);
                        finish();
                    }
                }, 1000);
            } else {

                //Toast.makeText(Valida_CarregaDados.this, "Vocẽ não possui conexão com internet, não é possivel carregar os dados.", Toast.LENGTH_LONG);

                AlertDialog.Builder builder = new AlertDialog.Builder(Valida_CarregaDados.this);
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setCancelable(false);
                builder.setTitle("Conexão falhou!");
                builder.setMessage("Vocẽ não possui conexão com internet, não é possivel carregar os dados.");
                builder.setNegativeButton("Configurações", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 0);
                        finish();
                    }
                });
                builder.setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                }).show();
            }
        }

    }



    private void GetPessoa() {
        preferences = getSharedPreferences(getResources().getString(R.string.sharedpreferences), 0);
        editor = preferences.edit();
        String json = preferences.getString("Pessoa", null);
        type2 = new TypeToken<Pessoa>() {
        }.getType();
        pessoa = (Pessoa) new GsonBuilder().create().fromJson(json, type2);

    }

    private void SetPessoa() {

        pessoa = (Pessoa) new GsonBuilder().create().fromJson(async2.getResponse(), type2);
        Gson gson = new Gson();
        String p = gson.toJson(pessoa);
        editor.putString("Pessoa", p);
        editor.commit();

    }


    public String getIdentificador() {

        String android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        if (android_id != null)
            return android_id;

        return getTimeStamp();

    }

    public String getTimeStamp() {
        Long tsLong = System.currentTimeMillis() / 1000;
        return tsLong.toString();
    }


}
