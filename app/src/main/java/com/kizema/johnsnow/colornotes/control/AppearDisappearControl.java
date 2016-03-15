package com.kizema.johnsnow.colornotes.control;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.kizema.johnsnow.colornotes.App;
import com.kizema.johnsnow.colornotes.helper.ColorFilterStateDrawable;

import java.util.HashMap;
import java.util.Map;

public abstract class AppearDisappearControl {

    private Drawable closedDrawable, openedDrawable;

    private ImageView ivSubject;
    private boolean firstState;

    public AppearDisappearControl(ImageView ivSubject, boolean firstState){
        this.ivSubject = ivSubject;
        this.firstState = firstState;

        Map<Integer, Integer> map = new HashMap<>();
        map.put(android.R.attr.state_pressed, getPressedColor());
        closedDrawable = new ColorFilterStateDrawable(
                ivSubject.getContext().getResources().getDrawable(getSecondSTateDrawableId()),
                getColor(),
                map);

        openedDrawable = new ColorFilterStateDrawable(
                ivSubject.getContext().getResources().getDrawable(getFirstStateDrawableId()),
                getColor(),
                map);


        if(!firstState){
            ivSubject.setImageDrawable(closedDrawable);
        } else {
            ivSubject.setImageDrawable(openedDrawable);
        }
    }

    public void setOnClickListener(final View.OnClickListener listener){
        ivSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstState) {
                    ivSubject.setImageDrawable(closedDrawable);
                } else {
                    ivSubject.setImageDrawable(openedDrawable);
                }

                firstState = !firstState;
                listener.onClick(v);
            }
        });
    }

    public boolean isFirstState(){
        return firstState;
    }

    public abstract int getSecondSTateDrawableId();
    public abstract int getFirstStateDrawableId();
    public abstract int getColor();
    public abstract int getPressedColor();

    public int getColor(int color){
        return App.getAppContext().getResources().getColor(color);
    }
}
