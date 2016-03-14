package com.kizema.johnsnow.colornotes.appviews;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.kizema.johnsnow.colornotes.R;
import com.kizema.johnsnow.colornotes.appviews.parsers.ViewParser;
import com.kizema.johnsnow.colornotes.helper.ColorFilterStateDrawable;
import com.kizema.johnsnow.colornotes.helper.Setter;

import java.util.Map;

public class AppLinearLayout extends LinearLayout implements ViewParser.TrueViewProperties{

    private ViewParser viewParser;

    public AppLinearLayout(Context context) {
        super(context);

        init();
    }

    public AppLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
        init(attrs);
    }

    public AppLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
        init(attrs);
    }

    private void init(){
        viewParser = new ViewParser(this);
    }

    private void init(AttributeSet attrs){
        viewParser.parse(attrs);
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
