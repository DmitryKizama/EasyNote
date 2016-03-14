package com.example.johnsnow.fragmentnote.appviews;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.johnsnow.fragmentnote.R;
import com.example.johnsnow.fragmentnote.appviews.parsers.ImageViewParser;
import com.example.johnsnow.fragmentnote.helper.ColorFilterStateDrawable;
import com.example.johnsnow.fragmentnote.helper.Setter;

import java.util.Map;

public class AppImageView extends ImageView implements ImageViewParser.TrueImageViewProperties {

    private ImageViewParser viewParser;

    public AppImageView(Context context) {
        super(context);

        init();
    }

    public AppImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
        init(attrs);
    }

    public AppImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
        init(attrs);
    }

    private void init(){
        viewParser = new ImageViewParser(this);
    }

    private void init(AttributeSet attrs){
        viewParser.parse(attrs);
    }

    @Override
    public void mutate(){
        if (getDrawable() != null){
            getDrawable().mutate();
        }

        if (getBackground() != null){
            getBackground().mutate();
        }
    }

    @Override
    public void setDrawableColor(int color) {
        if (getDrawable() != null){
            getDrawable().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        }
}

    @Override
    public void setStateDrawable(int defColor, Map<Integer, Integer> map) {
        setImageDrawable(new ColorFilterStateDrawable(getDrawable(), defColor, map));
    }

    public void setStateDrawable(int defColor, Drawable drawable, Map<Integer, Integer> map) {
        setImageDrawable(new ColorFilterStateDrawable(drawable, defColor, map));
    }

    public void setBackgroundStateDrawable(Drawable background, int defColor, Map<Integer, Integer> map) {
        Setter.setBackground(this, new ColorFilterStateDrawable(background, defColor, map));
    }

    @Override
    public void setBackgroundStateDrawable(boolean backgroundIsColor, int defColor, Map<Integer, Integer> map) {
        Drawable dr;
        if (backgroundIsColor) {
            dr = getContext().getResources().getDrawable(R.drawable.filled_button).mutate();
        } else {
            dr = getBackground().mutate();
        }

        if (dr == null){
            return;
        }

        Setter.setBackground(this, new ColorFilterStateDrawable(dr, defColor, map));
    }
}
