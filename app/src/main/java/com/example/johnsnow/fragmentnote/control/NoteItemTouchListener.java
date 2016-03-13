package com.example.johnsnow.fragmentnote.control;

import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.johnsnow.fragmentnote.helper.UIhelper;

public class NoteItemTouchListener implements View.OnTouchListener {
    private final int OFFSET = UIhelper.getPixel(40);//luft
    private static final int MOVE_DIST = UIhelper.getPixel(80);
    private final int DURATION = 200;


    private boolean swipeBeginToLeft = false;
    private boolean swipeBeginToRight = false;

    private View lastView;
    private PointF down = new PointF();

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        float x = event.getRawX();
        float y = event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.v("ANT", "ACTION_DOWN");
                down.x = x;
                down.y = y;

                swipeBeginToRight = false;
                swipeBeginToLeft = false;
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.v("ANT", "ACTION_MOVE");

                if (swipeBeginToLeft) {
                    moveItems(down.x - x - OFFSET);
                    return false;
                }

                if (swipeBeginToRight) {
                    moveItems(MOVE_DIST - (x - down.x - OFFSET));
                    return false;
                }

                if (down.x - x > OFFSET) {
                    if (lastView.getTranslationX() < 0) {
                        return false;
                    }

                    swipeBeginToLeft = true;
                    setScrollEnabled(false);
                    moveItems(down.x - x - OFFSET);
                    return false;
                }

                if (x - down.x > OFFSET) {
                    if (lastView.getTranslationX() == 0) {
                        return false;
                    }

                    swipeBeginToRight = true;
                    setScrollEnabled(false);
                    moveItems(MOVE_DIST - (x - down.x - OFFSET));
                    return false;
                }

                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                Log.v("ANT", "ACTION_UP");
                if (swipeBeginToLeft) {
                    resetItems((int) (Math.abs(down.x - x) - OFFSET));
                }

                if (swipeBeginToRight) {
                    resetItems((int) (MOVE_DIST - Math.abs(down.x - x) - OFFSET));
                }

                setScrollEnabled(true);
        }

        return false;
    }

    private void moveItems(float dx) {
        if (dx > MOVE_DIST) {
            dx = MOVE_DIST;
        } else if (dx < 0) {
            dx = 0;
        }

        lastView.setTranslationX(-dx);
    }

    /**
     * ACTION_DOWN received
     */
    public void onDownOccured(View v) {
        if (lastView != null && lastView.getTranslationX() < 0 && lastView != v) {
            resetItems(0);
        }

        this.lastView = v;
    }


    public void resetItems(int dist) {
        if (dist > MOVE_DIST / 2) {
            lastView.animate().translationX(-MOVE_DIST).setDuration(DURATION).start();
        } else {
            lastView.animate().translationX(0).setDuration(DURATION).start();
        }

    }


    public void setScrollEnabled(boolean enabled){
        //TODO
//        scrollEnabled = enabled;
//        swipeContainer.setEnabled(enabled);
    }
}
