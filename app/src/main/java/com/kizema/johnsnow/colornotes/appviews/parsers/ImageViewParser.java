package com.kizema.johnsnow.colornotes.appviews.parsers;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.kizema.johnsnow.colornotes.R;

import java.util.HashMap;
import java.util.Map;

public class ImageViewParser extends ViewParser {

    public interface TrueImageViewProperties extends TrueViewProperties{
        void setDrawableColor(int color);
        void setStateDrawable(int defColor, Map<Integer, Integer> map);
        void mutate();
    }

    public ImageViewParser(ImageView view) {
        super(view);
    }

    public void parse(AttributeSet attrs){
        super.parse(attrs);

        int color = -1;
        int colorPressed = -1;
        int colorSelected = -1;
        boolean mutate = false;

        TypedArray custom = view.getContext().getTheme().obtainStyledAttributes(
                attrs, R.styleable.AppViews, 0, 0);
        try {
            color = custom.getColor(R.styleable.AppViews_drawableColor, Color.WHITE);
            colorPressed = custom.getColor(R.styleable.AppViews_drawableStatePressed, -1);
            colorSelected = custom.getColor(R.styleable.AppViews_drawableStateSelected, -1);

            mutate = custom.getBoolean(R.styleable.AppViews_mutate, false);
        } finally {
            custom.recycle();
        }

        if (colorPressed != -1 || colorSelected != -1){
            setStateDrawable(color, colorPressed, colorSelected);
        }
        else {
            if (color != -1) {
                setTrueDrawableColorPrivate(color);
            }
        }

        if (mutate){
            ((TrueImageViewProperties) view ).mutate();
        }

    }

    private void setTrueDrawableColorPrivate(int color){
        ((TrueImageViewProperties) view ).setDrawableColor(color);
    }

    private void setStateDrawable(int defColor, int pressedColor, int selectedColor){
        Map<Integer, Integer> map = new HashMap();

        if (pressedColor != -1) {
            map.put(android.R.attr.state_pressed, pressedColor);
        }

        if (selectedColor != -1) {
            map.put(android.R.attr.state_selected, selectedColor);
        }

        ((TrueImageViewProperties) view ).setStateDrawable(defColor, map);
    }

}

