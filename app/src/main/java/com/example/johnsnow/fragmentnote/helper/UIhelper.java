package com.example.johnsnow.fragmentnote.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class UIhelper {
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
                inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            }
        }
    }
}
