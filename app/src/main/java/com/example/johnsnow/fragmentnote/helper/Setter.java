package com.example.johnsnow.fragmentnote.helper;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

/**
 * Created by somename on 14.03.2016.
 */
public class Setter {
    public static void setBackground(View v, Drawable drawable){
        if (Build.VERSION.SDK_INT >= 16) {
            v.setBackground(drawable);
        } else {
            v.setBackgroundDrawable(drawable);
        }
    }
}
