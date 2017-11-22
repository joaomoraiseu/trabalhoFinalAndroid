package com.eworld.harasfal.Asyncs;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.eworld.harasfal.Classes.DadosEmpresa;
import com.eworld.harasfal.Interface.OnPostExecute;
import com.eworld.harasfal.Utils.Loads;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by evox on 03/11/16.
 */

public class PostRequestAsyncTask extends AsyncTask<Void, Void, String> {
    HttpURLConnection urlConnection;
    AlertDialog progressDialog;

    com.eworld.harasfal.Interface.OnPostExecute OnPostExecute;
    String requestBody;
    Context context;
    String metodo;
    String Response;
    Uri.Builder builder;
    boolean EnableAlert;

    Map<String, String> params;

    public PostRequestAsyncTask(Context context, String metodo) {
        this.context = context;
        this.metodo = metodo;
        builder = new Uri.Builder();
        params = new HashMap<>();
        progressDialog = Loads.ProgressDialog(context);
/*
        try {
            url = new URL(DadosEmpresa.URL+metodo);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }
    protected void onPreExecute() {

        if(EnableAlert)
        progressDialog.show();

    }
    @Override
    protected String doInBackground(Void... voids) {
        // encode parameters
        try {
        Iterator entries = params.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            builder.appendQueryParameter(entry.getKey().toString(), entry.getValue().toString());
            entries.remove();
        }
        requestBody = builder.build().getEncodedQuery();


            URL url = new URL(DadosEmpresa.URL+metodo);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("token", "Basic ZXZveDpwbGF0YWZvcm1hLTIwMTY=");
            //urlConnection.setRequestProperty("Content-Type", "application/json");
            OutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
            writer.write(requestBody);
            writer.flush();
            writer.close();
            outputStream.close();

            InputStream inputStream;
            // get stream
            if (urlConnection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                inputStream = urlConnection.getInputStream();
            } else {
                inputStream = urlConnection.getErrorStream();
            }
            // parse stream
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String temp, response = "";
            while ((temp = bufferedReader.readLine()) != null) {
                response += temp;
            }

            return response;
        } catch (IOException e) {
            return e.toString();
        }
    }

    public void AddParam(String key, String value) {

        params.put(key,value);

    }

    @Override
    protected void onPostExecute(String result) {
        if (result == null) {
            result = "ERRO";
        }
        setResponse(result);
        if(EnableAlert)
        progressDialog.dismiss();
        if(OnPostExecute!=null)
        this.OnPostExecute.OnPostExecute();

    }

    public void setResponse(String response) {
        Response = response;
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
