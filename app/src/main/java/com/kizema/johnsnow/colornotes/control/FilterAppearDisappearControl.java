package com.kizema.johnsnow.colornotes.control;

import android.widget.ImageView;

import com.kizema.johnsnow.colornotes.R;

public class FilterAppearDisappearControl extends AppearDisappearControl{

    public FilterAppearDisappearControl(ImageView ivSubject) {
        super(ivSubject, true);
    }

    @Override
    public int getOpenedDrawableId() {
        return R.drawable.ic_filter_shown;
    }

    @Override
    public int getClosedDrawableId() {
        return  R.drawable.ic_no_fileter;
    }

    @Override
    public int getColor() {
        return getColor(R.color.ABIcon);
    }

    @Override
    public int getPressedColor() {
        return getColor(R.color.ABIconPressed);
    }

}
