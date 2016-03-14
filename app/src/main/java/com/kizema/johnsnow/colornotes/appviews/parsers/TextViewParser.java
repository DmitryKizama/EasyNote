package com.kizema.johnsnow.colornotes.appviews.parsers;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.kizema.johnsnow.colornotes.R;


public class TextViewParser extends ViewParser {

    public static final int HelveticaNeueFont = 0;
    public static final int HelveticaNeueBoldFont = 1;
    public static final int HelveticaNeueUltraLightFont = 2;
    public static final int HelveticaNeueLightFont = 3;
    public static final int HelveticaNeueThinFont = 4;
    public static final int HelveticaNeueMediumFont = 5;


    public TextViewParser(TextView view) {
        super(view);
    }

    public void parse(AttributeSet attrs){
        super.parse(attrs);


        int fontType=-1;

        int textColorNorm = -1, textColorSelected = -1, textColorPressed = -1;

        TypedArray custom = view.getContext().getTheme().obtainStyledAttributes(
                attrs, R.styleable.AppViews, 0, 0);
        try {
            fontType = custom.getInteger(R.styleable.AppViews_viewFont, 0);

            textColorNorm = custom.getInteger(R.styleable.AppViews_textColorState, -1);
            textColorSelected = custom.getInteger(R.styleable.AppViews_textColorSelected, -1);
            textColorPressed = custom.getInteger(R.styleable.AppViews_textColorPressed, -1);
        } finally {
            custom.recycle();
        }

        if (fontType != -1){
            setFontType(fontType);
        }

        if (textColorNorm != -1 && (textColorSelected != -1 || textColorPressed != -1) ){
            setTextColorState(textColorNorm, textColorSelected, textColorPressed);
        }
    }

    public void setTextColorState(int textColorNorm, int textColorSelected, int textColorPressed){
        ColorStateList myList = null;

        if (textColorSelected != -1 && textColorPressed != -1){
            int[][] states = new int[][] {
                    new int[] { android.R.attr.state_pressed},  // pressed
                    new int[] { android.R.attr.state_selected},  // selected
                    new int[] {}
            };

            int[] colors = new int[] {
                    textColorPressed,
                    textColorSelected,
                    textColorNorm
            };

            myList = new ColorStateList(states, colors);
        } else {
            if (textColorSelected != -1){
                int[][] states = new int[][] {
                        new int[] { android.R.attr.state_selected},  // selected
                        new int[] {}
                };

                int[] colors = new int[] {
                        textColorSelected,
                        textColorNorm
                };

                myList = new ColorStateList(states, colors);
            } else
            if (textColorPressed != -1){
                int[][] states = new int[][] {
                        new int[] { android.R.attr.state_pressed},  // selected
                        new int[] {}
                };

                int[] colors = new int[] {
                        textColorPressed,
                        textColorNorm
                };

                myList = new ColorStateList(states, colors);
            }
        }

        if (myList != null) {
            ((TextView) view).setTextColor(myList);
        }
    }

    public void setFontType(int fontType){
//        switch (fontType){
//            case HelveticaNeueFont://HelveticaNeue
//                FontSetter.setHelveticaNeueFont((TextView)view);
//                break;
//            case HelveticaNeueBoldFont://HelveticaNeueBold
//                FontSetter.setHelveticaNeueBoldFont((TextView)view);
//                break;
//            case HelveticaNeueUltraLightFont://HelveticaNeueUltraLight
//                FontSetter.setHelveticaNeueUltraLightFont((TextView)view);
//                break;
//            case HelveticaNeueLightFont://HelveticaNeueLight
//                FontSetter.setHelveticaNeueLightFont((TextView)view);
//                break;
//            case HelveticaNeueThinFont://HelveticaNeueThin
//                FontSetter.setHelveticaNeueThinFont((TextView)view);
//                break;
//            case HelveticaNeueMediumFont:
//                FontSetter.setHelveticaNeueMediumFont((TextView)view);
//                break;
//        }
    }
}
