package com.eworld.harasfal.Asyncs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.eworld.harasfal.Classes.DadosEmpresa;
import com.eworld.harasfal.Interface.OnPostExecute;
import com.eworld.harasfal.R;
import com.eworld.harasfal.Utils.Loads;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by evox on 26/10/16.
 */

public class GetRequestAsyncTask extends AsyncTask<Void, Void, String> {


    String metodo;
    String Response,Cache;
    com.eworld.harasfal.Interface.OnPostExecute OnPostExecute;
    AlertDialog progressDialog;
    Context context;
    String UrlString;
    HttpURLConnection urlConnection;
    boolean EnableAlert;


    public GetRequestAsyncTask(Context context, String metodo,String Cache) {
        this.context = context;
        this.metodo = metodo;
        this.Cache = Cache;
        UrlString = DadosEmpresa.URL+metodo;
        progressDialog = Loads.ProgressDialog(context);

    }

    protected void onPreExecute() {

        if(EnableAlert)
        progressDialog.show();

    }

    protected String doInBackground(Void... urls) {

        try {
            URL url = new URL(UrlString);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("token", "Basic ZXZveDpwbGF0YWZvcm1hLTIwMTY=");

            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();
                //Response =stringBuilder.toString();
                return stringBuilder.toString();
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }


    @Override
    protected void onPostExecute(String result) {
       // Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        if(EnableAlert)
        progressDialog.dismiss();
        setResponse(result);
        if(OnPostExecute!=null)
        this.OnPostExecute.OnPostExecute();

    }


    public void AddFirstParam(String key, String value) {


        UrlString +="?"+key+"="+value;

        //urlConnection.addRequestProperty(nome,value);

    }
    public void AddParam(String key, String value) {


        UrlString +="&"+key+"="+value;
        //urlConnection.addRequestProperty(nome,value);

    }

    public void setResponse(String response) {
        Response = response;
        if(Cache!=null && Cache!="") {
            SharedPreferences preferences = context.getSharedPreferences(context.getResources().getString(R.string.sharedpreferences), 0);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Cache, response);
            editor.commit();
        }
    }

    public String getResponse() {
        return Response;
    }

    public void setOnPostExecute(OnPostExecute onPostExecute) {
        OnPostExecute = onPostExecute;
    }

    public void EnableProgressdialog(){
        EnableAlert =true;

    }

}
