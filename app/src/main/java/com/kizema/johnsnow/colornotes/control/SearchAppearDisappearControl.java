package com.kizema.johnsnow.colornotes.control;

import android.widget.ImageView;

import com.kizema.johnsnow.colornotes.R;

public class SearchAppearDisappearControl extends AppearDisappearControl {

    public SearchAppearDisappearControl(ImageView ivSubject) {
        super(ivSubject, true);
    }

    @Override
    public int getSecondSTateDrawableId() {
        return R.drawable.ic_cross;
    }

    @Override
    public int getFirstStateDrawableId() {
        return R.drawable.ic_search;
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