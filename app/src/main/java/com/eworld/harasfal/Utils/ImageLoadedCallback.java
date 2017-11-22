package com.eworld.harasfal.Utils;

import android.widget.ProgressBar;

import com.squareup.picasso.Callback;

/**
 * Created by evox on 18/02/16.
 */
public class ImageLoadedCallback implements Callback {
    public ProgressBar progressBar;

    public  ImageLoadedCallback(ProgressBar progBar){
        progressBar = progBar;
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError() {

    }
}