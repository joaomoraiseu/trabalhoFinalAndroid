package com.eworld.harasfal;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.eworld.harasfal.Asyncs.PostRequestAsyncTask;
import com.eworld.harasfal.Classes.DadosEmpresa;
import com.eworld.harasfal.Classes.Pessoa;
import com.eworld.harasfal.Interface.OnPostExecute;
import com.eworld.harasfal.Push.Badge;
import com.eworld.harasfal.Utils.Animattion;
import com.eworld.harasfal.Utils.ConnectionDetector;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.onesignal.OneSignal;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Runnable {

    private SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Toolbar toolbar;
    NavigationView navigationView;
    Pessoa pessoa;
    PostRequestAsyncTask async2;
    ConnectionDetector cd;
    ImageView alert0, alert1, alert2, alert3, alert4, alert5, badger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        cd = new ConnectionDetector(MenuActivity.this);
        SetToolbar();
        AdjustMenuButtons();

        Button botaoSite = (Button) findViewById(R.id.site);
        botaoSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, WebViewActivity.class);
                startActivity(i);
            }
        });
        run();

    }

    private void AdjustMenuButtons() {
        FrameLayout btn_1 = (FrameLayout) findViewById(R.id.btn_1);
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, Item_Activity.class);
                Item_Activity.Tipo = "1";
                Item_Activity.Titulo = "Feed";
                startActivity(i);


            }
        });

        FrameLayout btn_3 = (FrameLayout) findViewById(R.id.btn_3);
        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, Item_Activity.class);
                Item_Activity.Tipo = "2";
                Item_Activity.Titulo = "Plantel";
                startActivity(i);

            }
        });
        FrameLayout btn_5 = (FrameLayout) findViewById(R.id.btn_6);
        btn_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, Chat_Activity.class);
                startActivity(i);
                //finish();
            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            AlertDialog alerta;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Fechar o aplicativo?");
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {

                }
            });
            alerta = builder.create();
            alerta.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return true;
    }

    @Override
    public void run() {
        //CheckAlerts();
        Handler handler = new Handler();
        handler.postDelayed(this, 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetPessoa();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        GetPessoa();

    }


    private void SetToolbar() {

    }


    private void GetPessoa() {

        preferences = getSharedPreferences(getResources().getString(R.string.sharedpreferences), 0);
        editor = preferences.edit();
        String json = preferences.getString("Pessoa", null);
        Type typee = new TypeToken<Pessoa>() {
        }.getType();
        pessoa = (Pessoa) new GsonBuilder().create().fromJson(json, typee);


    }

}
