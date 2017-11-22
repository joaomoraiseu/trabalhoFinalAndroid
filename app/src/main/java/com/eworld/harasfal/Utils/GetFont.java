package com.eworld.harasfal.Utils;

import android.content.Context;
import android.graphics.Typeface;
/**
 * Created by evox on 30/01/17.
 */

public class GetFont {

    public static Typeface RegularFont(Context context){
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "Co Headline Corp Regular.ttf");
        return tf;

    }
    public static Typeface LightFont(Context context){
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "Co Headline Corp Light.ttf");
        return tf;
    }
}
