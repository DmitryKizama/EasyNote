package com.kizema.johnsnow.colornotes.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class UIHelper {
    private static int height;
    private static int width;
    private static DisplayMetrics metrics;

    private static Context appContext;
    public static int keyboardHeight; //comment


    public static void init(Context c){
        appContext = c;

        height = ((WindowManager) appContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
        width = ((WindowManager) appContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();

        metrics = appContext.getResources().getDisplayMetrics();
        keyboardHeight = getPixel(215);
    }


    public static void hideKeyboard(Activity activity, IBinder binder) {
        if (activity != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (binder != null && inputManager != null) {
                inputManager.hideSoftInputFromWindow(binder, 0);//HIDE_NOT_ALWAYS
                inputManager.showSoftInputFromInputMethod(binder, 0);
            }
        }
    }

    public static void showKeyboardForDialog(Activity activity, View view, Dialog dialog) {
        if (activity != null && view != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                view.requestFocus();
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        }
    }

    public static void showKeyboard(Activity activity, View view) {
        if (activity != null && view != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                view.requestFocus();
                activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//                inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            }
        }
    }

    public static int getW() {
        return width;
    }

    public static int getH() {
        return height;
    }

    public static int getPixel(float dpi){
        return (int)(metrics.density * dpi);
    }

    public static float getPixelF(float dpi){
        return metrics.density * dpi;
    }

    public static int getDPI(int px){
        return (int)(px / metrics.density);
    }


    public static void setKeyboardHeight(int h){
        keyboardHeight = h;
    }

    public static int getKeyboardHeight(){
        return keyboardHeight;
    }
}
