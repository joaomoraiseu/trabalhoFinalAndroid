package com.eworld.harasfal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;


public class Splash_Screen extends AppCompatActivity implements Runnable {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash__screen);
        Handler handler = new Handler();
        handler.postDelayed(this, 2000);
    }

    @Override
    public void run() {
            Intent i = new Intent(this, Valida_CarregaDados.class);
            startActivity(i);
            finish();
        }

    @Override
    protected void onPause() {
        super.onPause();
        //OneSignal.onPaused();
        //AppEventsLogger.deactivateApp(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        //OneSignal.onResumed();
        //AppEventsLogger.activateApp(this);
    }
}
