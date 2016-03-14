package com.kizema.johnsnow.colornotes.helper;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.kizema.johnsnow.colornotes.R;

public class ColorHelper {

    private static Context context;

    public static void init(Context c){
        context = c;
    }

    public static Drawable getColorDrawable(){
        return  context.getResources().getDrawable(R.drawable.filled_button).mutate();
    }
}
