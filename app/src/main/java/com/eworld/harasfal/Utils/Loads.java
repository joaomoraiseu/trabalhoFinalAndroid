package com.eworld.harasfal.Utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

import com.eworld.harasfal.R;

import dmax.dialog.SpotsDialog;
/**
 * Created by evox on 27/10/16.
 */

public class Loads {

    public static AlertDialog ProgressDialog(Context context) {
        AlertDialog progressdialog = new SpotsDialog(context, R.style.Custom);
        //ProgressDialog progressdialog = new ProgressDialog(context);
        //progressdialog.setMessage("Carregando... ");
        //progressdialog.setIndeterminate(true);
        //progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressdialog.setCancelable(false);

        return  progressdialog;
    }
    public static ProgressDialog ProgressDialog2(Context context) {
        ProgressDialog progressdialog = new ProgressDialog(context);
        //ProgressDialog progressdialog = new ProgressDialog(context);
        //progressdialog.setMessage("Carregando... ");
        //progressdialog.setIndeterminate(true);
        //progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressdialog.setCancelable(false);

        return  progressdialog;
    }
}
