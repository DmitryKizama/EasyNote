package com.example.johnsnow.fragmentnote.control;

import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.johnsnow.fragmentnote.helper.UIhelper;

public class NoteItemTouchListener implements View.OnTouchListener {
    private final int OFFSET = UIhelper.getPixel(0);//luft
    private static final int MOVE_DIST = UIhelper.getPixel(80);
    private final int DURATION = 200;

    private boolean swipeBegin = false;
    private boolean ignoreTouch = false;
    private boolean retry = false;

    private int extraX = 0;

    private View lastView;
    private PointF down = new PointF();

    private Condition condition;

    private OnNoteItemTouchInActionListener onNoteItemTouchInActionListener;

    public interface OnNoteItemTouchInActionListener{
        void setTouchInAction(boolean inAction);
    }

    private enum Condition {
        CROSS_VISIBLE, NORM, UPDATE_VISIBLE;
    }

    public NoteItemTouchListener(OnNoteItemTouchInActionListener onNoteItemTouchInActionListener){
        this.onNoteItemTouchInActionListener = onNoteItemTouchInActionListener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        float x = event.getRawX();
        float y = event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                down.x = x;
                down.y = y;

                swipeBegin = false;
                ignoreTouch = false;
                retry = false;

                int dx = (int) lastView.getTranslationX();

                if (dx == -MOVE_DIST) {
                    condition = Condition.CROSS_VISIBLE;
                } else if (dx == MOVE_DIST) {
                    condition = Condition.UPDATE_VISIBLE;
                } else {
                    condition = Condition.NORM;
                }

                Log.v("ANT", "condition :: " + condition);

                return true;
            case MotionEvent.ACTION_MOVE:

                Log.e("ANT", "down.y :" + down.y + "  y :"+y + "      down.x :"+down.x+"  x :" + x);
                Log.e("ANT", "Math.abs(down.y - y) :" + Math.abs(down.y - y) +
                        "      Math.abs(down.x - x) :"+Math.abs(down.x - x));

                if (retry){
                    swipeBegin = false;
                }

                if (!swipeBegin && Math.abs(down.y - y) > Math.abs(down.x - x)){
                    Log.e("ANT", "DONE");
                    ignoreTouch = true;
                    setScrollEnabled(true);
                    return false;
                }

                if (Math.abs(down.y - y) == Math.abs(down.x - x) && Math.abs(down.x - x) == 0){
                    retry = true;
                } else {
                    retry = false;
                }

                if (ignoreTouch){
                    return false;
                }

                Log.v("ANT", "ACTION_MOVE");
//                if (Math.abs(down.x - x) > OFFSET) {
                    swipeBegin = true;
                    setScrollEnabled(false);
//                }

                if (swipeBegin) {
                    switch (condition) {
                        case UPDATE_VISIBLE: {

                            float ddx = 0;
                            if (x - down.x >= 0) {
                                ddx = MOVE_DIST;
                            } else if (down.x - x > OFFSET) {
                                ddx = MOVE_DIST - (down.x - x - OFFSET);
                            }

                            if (ddx < -MOVE_DIST) {
                                ddx = -MOVE_DIST;
                            }

                            moveItems(ddx);
                            break;
                        }

                        case NORM: {

                            float ddx = 0;
                            if (x - down.x >= OFFSET) {
                                ddx = x - down.x - OFFSET;
                            } else if (down.x - x > OFFSET) {
                                ddx = -(down.x - x - OFFSET);
                            }

                            if (ddx < -MOVE_DIST) {
                                ddx = -MOVE_DIST;
                            }

                            if (ddx > MOVE_DIST) {
                                ddx = MOVE_DIST;
                            }

                            moveItems(ddx);
                            break;
                        }

                        case CROSS_VISIBLE: {

                            float ddx = 0;
                            if (x - down.x >= OFFSET) {
                                ddx = -MOVE_DIST + x - down.x - OFFSET;
                            } else if (down.x - x > OFFSET) {
                                ddx = -MOVE_DIST;
                            }

                            if (ddx > MOVE_DIST) {
                                ddx = MOVE_DIST;
                            }

                            moveItems(ddx);
                            break;
                        }
                    }

                    return false;
                }

            case MotionEvent.ACTION_CANCEL:
                Log.v("ANT", "ACTION_CANCEL");
            case MotionEvent.ACTION_UP:
                Log.v("ANT", "ACTION_UP");
                if (swipeBegin) {
                     resetItems((int) lastView.getTranslationX());
                     setScrollEnabled(true);
                }
        }

        return false;
    }

    private void moveItems(float dx) {
        Log.v("ANT", "lastView.setTranslationX(dx)   dx :: " + dx);
        lastView.setTranslationX(dx);
    }

    /**
     * ACTION_DOWN received
     */
    public void onDownOccured(View v) {
        if (lastView != null && lastView != v) {
            resetItems(0);
        }

        this.lastView = v;
    }


    public void resetItems(int dist) {
        if (dist > MOVE_DIST / 2) {
            lastView.animate().translationX(MOVE_DIST).setDuration(DURATION).start();
        } else if (dist < -MOVE_DIST / 2) {
            lastView.animate().translationX(-MOVE_DIST).setDuration(DURATION).start();
        } else {
            lastView.animate().translationX(0).setDuration(DURATION).start();
        }


    }


    public void setScrollEnabled(boolean enabled) {
        //TODO
        onNoteItemTouchInActionListener.setTouchInAction(enabled);
//        scrollEnabled = enabled;
//        swipeContainer.setEnabled(enabled);
    }
}
