package com.omdhanwant.youstream.utils;


import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

public class FontUtility {

    public static Typeface getBoldFont(Context context) {
        AssetManager am = context.getApplicationContext().getAssets();
        return Typeface.createFromAsset(am, "fonts/OpenSans-Bold.ttf");
    }

    public static Typeface getExtraBoldFont(Context context) {
        AssetManager am = context.getApplicationContext().getAssets();
        return Typeface.createFromAsset(am, "fonts/OpenSans-ExtraBold.ttf");
    }

    public static Typeface getSemiBoldFont(Context context) {
        AssetManager am = context.getApplicationContext().getAssets();
        return Typeface.createFromAsset(am, "fonts/OpenSans-SemiBold.ttf");
    }

    public static Typeface getSemiBoldItalicFont(Context context) {
        AssetManager am = context.getApplicationContext().getAssets();
        return Typeface.createFromAsset(am, "fonts/OpenSans-SemiBoldItalic.ttf");
    }

    public static Typeface getRegularFont(Context context) {
        AssetManager am = context.getApplicationContext().getAssets();
        return Typeface.createFromAsset(am, "fonts/OpenSans-Regular.ttf");
    }

    public static Typeface getLightFont(Context context) {
        AssetManager am = context.getApplicationContext().getAssets();
        return Typeface.createFromAsset(am, "fonts/OpenSans-Light.ttf");
    }

}
