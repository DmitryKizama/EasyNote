package com.example.johnsnow.fragmentnote.appviews;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.johnsnow.fragmentnote.R;
import com.example.johnsnow.fragmentnote.appviews.parsers.TextViewParser;
import com.example.johnsnow.fragmentnote.appviews.parsers.ViewParser;
import com.example.johnsnow.fragmentnote.helper.ColorFilterStateDrawable;
import com.example.johnsnow.fragmentnote.helper.Setter;

import java.util.Map;

public class AppTextView extends TextView implements ViewParser.TrueViewProperties{

    private TextViewParser viewParser;

    public AppTextView(Context context) {
        super(context);

        init();
    }

    public AppTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
        init(attrs);
    }

    public AppTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
        init(attrs);
    }

    private void init(){
        viewParser = new TextViewParser(this);
    }

    private void init(AttributeSet attrs){
        viewParser.parse(attrs);
    }

    public void setFont(int fontNumber){
        viewParser.setFontType(fontNumber);
    }

    public void setTextColorState(int textColorNorm, int textColorSelected, int textColorPressed){
        if (textColorNorm != -1 && (textColorSelected != -1 || textColorPressed != -1) ){
            viewParser.setTextColorState(textColorNorm, textColorSelected, textColorPressed);
        }
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
