package com.eworld.harasfal.Asyncs;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.eworld.harasfal.Classes.DadosEmpresa;
import com.eworld.harasfal.Interface.OnPostExecute;
import com.eworld.harasfal.Utils.Loads;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by evox on 20/02/17.
 */

public class AtualizarDadosAsyncTask extends AsyncTask<Void, Void, String> {

    URL connectURL;
    String Response;
    ByteArrayInputStream fileInputStream = null;
    String FileName;
    AlertDialog progressDialog;
    boolean EnableAlert;
    Context context;
    String  Identificador,Nome,Email,Celular;
    HttpURLConnection conn;
    com.eworld.harasfal.Interface.OnPostExecute OnPostExecute;

    public AtualizarDadosAsyncTask(Context context,String metodo,String iFileName, ByteArrayInputStream fStream) {
        try {

            FileName = iFileName;
            //connectURL = new URL(DadosEmpresa.URL+"Servico/RepairRequest");
            connectURL = new URL(DadosEmpresa.URLAtulaliza+metodo+"?UnidadeId="+DadosEmpresa.UnidadeID);
            this.context = context;
           /* if (fStream==null){
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                byte[] bitmapdata = bytes.toByteArray();
                fStream = new ByteArrayInputStream(bitmapdata);

            }*/
            fileInputStream = fStream;
            progressDialog = Loads.ProgressDialog(context);
        } catch (Exception ex) {
            Log.i("RequestAsyncTask", "URL Malformatted");
        }
    }

    protected void onPreExecute() {

        if (EnableAlert)
            progressDialog.show();

    }

    @Override
    protected void onPostExecute(String result) {
        if (result == null) {
            result = "ERRO";
        }
        setResponse(result);
        if (EnableAlert)
            progressDialog.dismiss();
        if (OnPostExecute != null)
            this.OnPostExecute.OnPostExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "****";
        //String Tag="fSnd";
        try {

            conn = (HttpURLConnection) connectURL.openConnection();
            // Allow Inputs
            conn.setDoInput(true);
            // Allow Outputs
            conn.setDoOutput(true);
            // Don't use a cached copy.
            conn.setUseCaches(false);
            // Use a post method.
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            //conn.setRequestProperty("token", "Basic ZXZveDpwbGF0YWZvcm1hLTIwMTY=");

            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=Identificador" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(Identificador);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=Nome" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(Nome);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=Email" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(Email);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=Celular" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(Celular);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=NomeEntidade" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes("");
            dos.writeBytes(lineEnd);


            if(fileInputStream!=null) {

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + FileName + "\"" + lineEnd);
                dos.writeBytes(lineEnd);

                int bytesAvailable = fileInputStream.available();

                int maxBufferSize = 1024;
                int bufferSize = Math.min(bytesAvailable, maxBufferSize);
                byte[] buffer = new byte[bufferSize];

                // read file and write it into form...
                int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // close streams
                fileInputStream.close();
            }
            dos.flush();

            InputStream is = conn.getInputStream();
            // retrieve the response from server
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
            String s = b.toString();
            dos.close();
            return s;
        } catch (MalformedURLException ex) {
            return ex.toString();
            //Log.e(Tag, "URL error: " + ex.getMessage(), ex);
        } catch (IOException ioe) {
            return ioe.toString();
            //Log.e(Tag, "IO error: " + ioe.getMessage(), ioe);
        }
    }

    public void setOnPostExecute(OnPostExecute onPostExecute) {
        OnPostExecute = onPostExecute;
    }

    public void EnableProgressdialog() {
        EnableAlert = true;

    }

    public void setIdentificador(String identificador) {
        Identificador = identificador;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setCelular(String celular) {
        Celular = celular;
    }

   /* public void setEntidade(String entidade) {
        Entidade = entidade;
    }*/

    public void setResponse(String response) {
        Response = response;
    }

    public String getResponse() {
        return Response;
    }

}