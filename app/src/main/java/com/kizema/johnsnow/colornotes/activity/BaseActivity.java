package com.kizema.johnsnow.colornotes.activity;

import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.kizema.johnsnow.colornotes.helper.UIHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * All activity shoud extend this activity
 */
public class BaseActivity extends FragmentActivity {

    private boolean isKeyboardOpened = false;
    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;

    List<OnSoftKeyboardListener> onSoftKeyboardListeners = new ArrayList<>();

    public interface OnSoftKeyboardListener {
        void onSoftKeyboardShown();

        void onSoftKeyboardHidden();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBase();
    }

    private void initBase() {

        onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                final View container = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
                int heightDiff = container.getRootView().getHeight() - container.getHeight();
                if (heightDiff >= UIHelper.getH() / 4 && !isKeyboardOpened) {

                    Rect r = new Rect();
                    View view = getWindow().getDecorView();
                    view.getWindowVisibleDisplayFrame(r);

                    if (r.bottom != UIHelper.getH()) {
                        isKeyboardOpened = true;
                        UIHelper.setKeyboardHeight(UIHelper.getH() - r.bottom);
                    }

                    onSoftKeyboardShown();
                    isKeyboardOpened = true;

                    if (BaseActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT &&
                            (UIHelper.getH() / 5) * 3 > heightDiff) {

                        Rect viewRect = new Rect();
                        container.getWindowVisibleDisplayFrame(viewRect);
                        int screenHeight = container.getRootView().getHeight();
                        int heightDifference = screenHeight - (viewRect.bottom);

                        UIHelper.setKeyboardHeight(heightDifference);
                    }
                } else if (heightDiff < UIHelper.getH() / 4 && isKeyboardOpened) {
                    onSoftKeyboardHidden();
                    isKeyboardOpened = false;
                }
            }
        };
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
    }


    /**
     * Add soft keyboard listener
     * If you use this method, do not forget to call removeOnSoftKeyboardListener
     * @param onSoftKeyboardListener
     */
    public void addOnSoftKeyboardListener(OnSoftKeyboardListener onSoftKeyboardListener) {
        onSoftKeyboardListeners.add(onSoftKeyboardListener);
    }

    public void removeOnSoftKeyboardListener(OnSoftKeyboardListener onSoftKeyboardListener) {
        onSoftKeyboardListeners.remove(onSoftKeyboardListener);
    }

    /**
     * Soft keyboard shown event
     */
    private void onSoftKeyboardShown() {
        for (OnSoftKeyboardListener onSoftKeyboardListener : onSoftKeyboardListeners) {
            onSoftKeyboardListener.onSoftKeyboardShown();
        }
    }

    /**
     * Soft keyboard hidden event
     */
    private void onSoftKeyboardHidden() {
        for (OnSoftKeyboardListener onSoftKeyboardListener : onSoftKeyboardListeners) {
            onSoftKeyboardListener.onSoftKeyboardHidden();
        }
    }



}
