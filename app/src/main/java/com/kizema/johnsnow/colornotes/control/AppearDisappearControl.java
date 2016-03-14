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
    private boolean opened;

    public AppearDisappearControl(ImageView ivSubject, boolean opened){
        this.ivSubject = ivSubject;
        this.opened = opened;

        Map<Integer, Integer> map = new HashMap<>();
        map.put(android.R.attr.state_pressed, getPressedColor());
        closedDrawable = new ColorFilterStateDrawable(
                ivSubject.getContext().getResources().getDrawable(getOpenedDrawableId()),
                getColor(),
                map);

        openedDrawable = new ColorFilterStateDrawable(
                ivSubject.getContext().getResources().getDrawable(getClosedDrawableId()),
                getColor(),
                map);


        if(!opened){
            ivSubject.setImageDrawable(closedDrawable);
        } else {
            ivSubject.setImageDrawable(openedDrawable);
        }
    }

    public void setOnClickListener(final View.OnClickListener listener){
        ivSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (opened) {
                    ivSubject.setImageDrawable(closedDrawable);
                } else {
                    ivSubject.setImageDrawable(openedDrawable);
                }

                opened = !opened;
                listener.onClick(v);
            }
        });
    }

    public abstract int getOpenedDrawableId();
    public abstract int getClosedDrawableId();
    public abstract int getColor();
    public abstract int getPressedColor();

    public int getColor(int color){
        return App.getAppContext().getResources().getColor(color);
    }
}
