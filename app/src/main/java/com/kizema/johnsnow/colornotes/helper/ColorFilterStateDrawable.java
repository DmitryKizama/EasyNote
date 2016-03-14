package com.kizema.johnsnow.colornotes.helper;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

import java.util.Map;

public class ColorFilterStateDrawable extends StateListDrawable {

    private Map<Integer, Integer> stateColorsMap;
    private int defaultColor;

    /**
     *  Create state list drawable programmatically - just pass drawable, default color of drawable, and Map of (state , color)
     * @param drawable resourse for icons
     * @param defaultColor color for normal state
     * @param stateColorsMap map of for ex. android.R.attr.state_pressed and ColorManager.someColor
     *
     *                        Map<Integer, Integer> map = new HashMap<>();
    map.put(android.R.attr.state_pressed, ColorManager.rose);
     */
    public ColorFilterStateDrawable(Drawable drawable, int defaultColor, Map<Integer, Integer> stateColorsMap) {
        super();
        this.stateColorsMap = stateColorsMap;
        this.defaultColor = defaultColor;

        drawable.setColorFilter(defaultColor, PorterDuff.Mode.MULTIPLY);

        if (stateColorsMap != null) {
            for (int state : stateColorsMap.keySet()) {
                addState(new int[]{state}, drawable);
            }
        }

        addState(new int[] {}, drawable);
    }

    @Override
    protected boolean onStateChange(int[] states) {

        if (stateColorsMap == null) {
            super.setColorFilter(defaultColor, PorterDuff.Mode.MULTIPLY);
            return super.onStateChange(states);
        }

        boolean colorSet = false;

        for (int state : states) {

            for (int st : stateColorsMap.keySet()){

                if (state == st) {
                    super.setColorFilter(stateColorsMap.get(st), PorterDuff.Mode.MULTIPLY);
                    colorSet = true;
                    break;
                }
            }
        }

        if (!colorSet) {
            super.setColorFilter(defaultColor, PorterDuff.Mode.MULTIPLY);
        }

        return super.onStateChange(states);
    }

    @Override
    public boolean isStateful() {
        return true;
    }
}