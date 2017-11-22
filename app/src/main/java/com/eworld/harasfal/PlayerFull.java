package com.eworld.harasfal;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.devbrackets.android.exomedia.listener.OnCompletionListener;
import com.devbrackets.android.exomedia.listener.OnErrorListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;

/**
 * Created by evox on 02/09/16.
 */
public class PlayerFull extends AppCompatActivity {

    public static String UrlVideo;
    public static String NomeVideo;
    VideoView emVideoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playerfull);
        setupVideoView();
        //SetToolbar();
    }

    private void setupVideoView() {
        emVideoView = (VideoView) findViewById(R.id.video_view);
        emVideoView.getVideoControls().setCanHide(true);
        //emVideoView.getVideoControls().setLoading(false);
        //emVideoView.getVideoControls().setVisibility(View.VISIBLE);
        emVideoView.setVideoPath(UrlVideo);
        //getSupportActionBar().setTitle(item.getNome());
        emVideoView.getVideoControls().setDescription(NomeVideo);
        emVideoView.start();
        //emVideoView.setVisibility(View.GONE);
        emVideoView.setOnErrorListener(new OnErrorListener() {
            @Override
            public boolean onError(Exception e) {
                errormessage();
                return false;
            }
        });

        emVideoView.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion() {
                //setupVideoView();
                emVideoView.setVideoPath(UrlVideo);
            }
        });
    }

    private void errormessage() {
        new AlertDialog.Builder(this)
                .setTitle("Erro")
                .setMessage("Não foi possivel carregar o video. Verifique sua conexão com internet.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

   /* private void SetToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_left_white);
        setSupportActionBar(toolbar);

        //toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //
    }*/
}
