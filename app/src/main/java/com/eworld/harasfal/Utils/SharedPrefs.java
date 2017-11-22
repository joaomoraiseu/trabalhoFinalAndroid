package com.eworld.harasfal.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.eworld.harasfal.Classes.Pessoa;
import com.eworld.harasfal.Classes.Unidade;
import com.eworld.harasfal.R;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by evox on 09/02/17.
 */

public class SharedPrefs {

    public static Unidade GetUnidade(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(context.getResources().getString(R.string.sharedpreferences), 0);
        String json = preferences.getString("Unidade", null);
        Type typee = new TypeToken<Unidade>() {
        }.getType();
        Unidade un = (Unidade) new GsonBuilder().create().fromJson(json, typee);
        return  un;

    }
    public static Pessoa GetPessoa(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(context.getResources().getString(R.string.sharedpreferences), 0);
        String json = preferences.getString("Pessoa", null);
        Type typee = new TypeToken<Pessoa>() {
        }.getType();
        Pessoa pessoa = (Pessoa) new GsonBuilder().create().fromJson(json, typee);
        return  pessoa;

    }

    public static String GetRegulamento(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(context.getResources().getString(R.string.sharedpreferences), 0);
        String json = preferences.getString("Regulamento", null);
        return  json;

    }


    public static void SetRegulamento(Context context,String regulamento) {
        SharedPreferences preferences = context.getSharedPreferences(context.getResources().getString(R.string.sharedpreferences), 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Regulamento", regulamento);
        editor.commit();

    }
       // Badge.ORCAMENTO_ID_BADGE.removeAll(Collections.singleton(id));

}
