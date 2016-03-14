package com.example.johnsnow.fragmentnote.appviews.parsers;

import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.example.johnsnow.fragmentnote.R;

import java.util.HashMap;
import java.util.Map;


public class ViewParser{
    protected View view;

    public interface TrueViewProperties{
        void setBackgroundStateDrawable(boolean backgroundIsColor, int defColor, Map<Integer, Integer> map);
    }

    public ViewParser(View view){
        this.view = view;
    }

    public void parse(AttributeSet attrs) {
        int backgroundColor = -1;
        int backgroundPressedColor = -1;
        int backgroundSelectedColor = -1;

        boolean backgroundIsColor = true;

        TypedArray custom = view.getContext().getTheme().obtainStyledAttributes(
                attrs, R.styleable.AppViews, 0, 0);
        try {
            backgroundIsColor = custom.getBoolean(R.styleable.AppViews_backgroundIsColor, true);
            backgroundColor = custom.getColor(R.styleable.AppViews_backgroundStateColor, -1);
            backgroundPressedColor = custom.getColor(R.styleable.AppViews_backgroundStatePressed, -1);
            backgroundSelectedColor = custom.getColor(R.styleable.AppViews_backgroundStateSelected, -1);
        } finally {
            custom.recycle();
        }

        if (backgroundColor != -1 || backgroundPressedColor != -1 || backgroundSelectedColor != -1){
            setBackgroundStateDrawable(backgroundIsColor, backgroundColor, backgroundPressedColor, backgroundSelectedColor);
        }
    }

    private void setBackgroundStateDrawable(boolean backgroundIsColor, int defColor, int pressedColor, int selectedColor){
        Map<Integer, Integer> map = new HashMap();

        if (pressedColor != -1) {
            map.put(android.R.attr.state_pressed, pressedColor);
        }

        if (selectedColor != -1) {
            map.put(android.R.attr.state_selected, selectedColor);
        }

        ((TrueViewProperties) view ).setBackgroundStateDrawable(backgroundIsColor, defColor, map);
    }


}
