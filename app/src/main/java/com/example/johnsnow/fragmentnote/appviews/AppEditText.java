package com.example.johnsnow.fragmentnote.appviews;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.EditText;

import com.example.johnsnow.fragmentnote.R;
import com.example.johnsnow.fragmentnote.appviews.parsers.TextViewParser;
import com.example.johnsnow.fragmentnote.appviews.parsers.ViewParser;
import com.example.johnsnow.fragmentnote.helper.ColorFilterStateDrawable;
import com.example.johnsnow.fragmentnote.helper.Setter;

import java.util.Map;

public class AppEditText extends EditText implements ViewParser.TrueViewProperties {

    private TextViewParser viewParser;

    public AppEditText(Context context) {
        super(context);

        init();
    }

    public AppEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
        init(attrs);
    }

    public AppEditText(Context context, AttributeSet attrs, int defStyleAttr) {
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

    public void setFont(int fontNumber) {
        viewParser.setFontType(fontNumber);
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
