package com.eworld.harasfal.Asyncs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


import com.eworld.harasfal.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


public class DownloadVideoImageFile extends AsyncTask<String, Integer, String> {
    public  static String Titulo;
    public  static String sharePath;
    File file;
    Context context;
    private ProgressDialog progressdialog;

    public DownloadVideoImageFile(Context context) {
        this.context = context;
        progressdialog = new ProgressDialog(context);
        progressdialog.setMessage("Baixando... ");
        progressdialog.setIndeterminate(true);
        progressdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressdialog.setCancelable(true);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try{

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            //intent.setType("video/*");
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.putExtra(Intent.EXTRA_TEXT, Titulo+"\n"+context.getResources().getString(R.string.descricao));
            context.startActivity(Intent.createChooser(intent,"Compartilhar via"));

        }catch (Exception e){
            e.printStackTrace();
        }
        progressdialog.dismiss();
    }

    @Override
    protected String doInBackground(String... f_url) {
        int count;
        FileDirGenerator(f_url[0]);

        try {
            //criando a pasta
            //
            URL url = new URL(f_url[0]);
            URLConnection conexion = url.openConnection();
            conexion.connect();
            // this will be useful so that you can show a tipical 0-100% progress bar
            int lenghtOfFile = conexion.getContentLength();
            // downlod the file
            InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = null;

            if (!file.exists())
                output = new FileOutputStream(file);
            else Toast.makeText(context,"Arquivo ja existe",Toast.LENGTH_LONG).show();

            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                publishProgress((int) (total * 100 / lenghtOfFile));
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressdialog.setIndeterminate(false);
        progressdialog.setMax(100);
        progressdialog.setProgress(values[0]);
    }
    private String AdjustName(String urlvideo){
        String[] separated = urlvideo.split("/");
        return separated[6];

    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressdialog.show();

    }
    public void FileDirGenerator(String url) {

        String NomeApp = context.getResources().getString(R.string.app_name);
        String dir = NomeApp+"/"+"Compartilhamentos"+"/";
        String Path = Environment.getExternalStorageDirectory().toString() + "/" +dir;
        /// cria diretorio
        file = new File(Path);
        if (!file.exists())
            file.mkdirs();
        String extension = url.substring(url.lastIndexOf("."));
        String timesptamp = getTimeStamp(); // gerar timestamp uma vez só
        String Filename = timesptamp+extension;
        ///cria arquivo
        file = new File(Path +Filename);


    }
    public String getTimeStamp() {
        Long tsLong = System.currentTimeMillis() / 1000;
        return tsLong.toString();
    }
}
