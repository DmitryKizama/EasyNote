package com.example.johnsnow.fragmentnote.control;

import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.johnsnow.fragmentnote.helper.UIhelper;

public class NoteItemTouchListener implements View.OnTouchListener {
    private static final int MOVE_DIST = UIhelper.getPixel(80);
    private final int DURATION = 200;

    private boolean swipeBegin = false;
    private boolean ignoreTouch = false;
    private boolean retry = false;

    private View lastView;
    private PointF down = new PointF();

    private Condition condition;

    private OnNoteItemTouchInActionListener onNoteItemTouchInActionListener;

//    private VelocityTracker mVelocityTracker = null;
    int pointerId;

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

//                int index = event.getActionIndex();
//                pointerId = event.getPointerId(index);
//                if(mVelocityTracker == null) {
//                    // Retrieve a new VelocityTracker object to watch the velocity of a motion.
//                    mVelocityTracker = VelocityTracker.obtain();
//                } else {
//                    // Reset the velocity tracker back to its initial state.
//                    mVelocityTracker.clear();
//                }
//                mVelocityTracker.addMovement(event);

                return true;
            case MotionEvent.ACTION_MOVE:

                if (ignoreTouch){
                    return false;
                }

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

//                mVelocityTracker.addMovement(event);
//                // When you want to determine the velocity, call
//                // computeCurrentVelocity(). Then call getXVelocity()
//                // and getYVelocity() to retrieve the velocity for each pointer ID.
//                mVelocityTracker.computeCurrentVelocity(1000);

                swipeBegin = true;
                setScrollEnabled(false);

                if (swipeBegin) {
                    switch (condition) {
                        case UPDATE_VISIBLE: {

                            float ddx = 0;
                            if (x - down.x >= 0) {
                                ddx = MOVE_DIST;
                            } else if (down.x - x > 0) {
                                ddx = MOVE_DIST - (down.x - x);
                            }

                            if (ddx < -MOVE_DIST) {
                                ddx = -MOVE_DIST;
                            }

                            moveItems(ddx);
                            break;
                        }

                        case NORM: {

                            float ddx = 0;
                            if (x - down.x >= 0) {
                                ddx = x - down.x;
                            } else if (down.x - x > 0) {
                                ddx = -(down.x - x);
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
                            if (x - down.x >= 0) {
                                ddx = -MOVE_DIST + x - down.x;
                            } else if (down.x - x > 0) {
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
            case MotionEvent.ACTION_UP:

//                if (mVelocityTracker != null) {
//
//                    float Vx = VelocityTrackerCompat.getXVelocity(mVelocityTracker, pointerId);
////                    float Vy = VelocityTrackerCompat.getYVelocity(mVelocityTracker, pointerId);
//                    Log.e("RR", "X velocity: " + Vx);
//
//                    // Return a VelocityTracker object back to be re-used by others.
//                    mVelocityTracker.recycle();
//                    mVelocityTracker = null;
//
//                    int transX = (int) ( lastView.getTranslationX() + Vx / 1000 * MOVE_DIST);

                    if (swipeBegin) {
                        int transX = (int) ( lastView.getTranslationX());
                        resetItems(transX);
                        setScrollEnabled(true);
                    }
//                }
        }

        return false;
    }

    private void moveItems(float dx) {
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
        onNoteItemTouchInActionListener.setTouchInAction(enabled);
    }
}
