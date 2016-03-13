package com.example.johnsnow.fragmentnote.adapters;

import android.graphics.PointF;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.johnsnow.fragmentnote.R;
import com.example.johnsnow.fragmentnote.helper.UIhelper;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    private static final int MOVE_DIST = UIhelper.getPixel(80);
    private static final int OFFSET = UIhelper.getPixel(30);


    public TextView tv;

    //TODO use as select/ deselect drawable handle
    private RelativeLayout rlParent;

    private View ivRemove, ivEdit;
    private NotificationAdapter.OnNotifClickListener listener;

    private OnItemListener itemListener;

    public interface OnItemListener {
        void remove(int pos);

        void itemClicked(int pos);
    }

    public NoteViewHolder(View v, NotificationAdapter.OnNotifClickListener listener, OnItemListener itemListener) {
        super(v);

        this.listener = listener;
        this.itemListener = itemListener;

        v.setOnTouchListener(new NoteViewHolderTouchListener());
        rlParent = (RelativeLayout) v.findViewById(R.id.rlParent);
        tv = (TextView) v.findViewById(R.id.tv);
        ivRemove = v.findViewById(R.id.ivRemove);
        ivEdit = v.findViewById(R.id.ivEdit);


        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivRemove.getLayoutParams();
        params.leftMargin = UIhelper.getW() + MOVE_DIST;

        ViewGroup.LayoutParams p = itemView.getLayoutParams();
        p.width = UIhelper.getW() + MOVE_DIST * 2;
        itemView.setLayoutParams(p);
    }

    private class NoteViewHolderTouchListener implements View.OnTouchListener {
        private boolean onDown = false;

        private PointF down = new PointF();

        @Override
        public boolean onTouch(View w, MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    listener.onItemDown(itemView, getAdapterPosition());
//                        Log.d("ANT", " listener.onItemDown(parentView, getAdapterPosition());");

                    down.x = event.getX();
                    down.y = event.getY();
                    onDown = true;

//                        llContent.setSelected(true);

                    listener.onTouch(event);
                    return true;
                case MotionEvent.ACTION_MOVE:
//                        Log.d("ANT", " ACTION_MOVE");
                    listener.onTouch(event);

                    if (!(0 <= event.getX() && event.getX() <= itemView.getWidth()
                            && 0 <= event.getY() && event.getY() <= itemView.getHeight())) {
//                            Log.d("ANT", " ACTION_MOVE   OUT");
                        onDown = false;
//                            llContent.setSelected(false);
                        return true;
                    }

                    if (Math.abs(down.x - event.getX()) > OFFSET || Math.abs(down.y - event.getY()) > OFFSET) {
//                            Log.d("ANT", " ACTION_MOVE   OUT");
                        onDown = false;
//                            llContent.setSelected(false);
                        return true;
                    }

                    return true;
                case MotionEvent.ACTION_UP:
//                        Log.d("ANT", "ACTION_UP");
//                        llContent.setSelected(false);
                    listener.onTouch(event);

                    if (onDown) {

                        if (getAdapterPosition() == RecyclerView.NO_POSITION) {
                            break;
                        }

                        if (getAdapterPosition() < 0) {
                            itemListener.itemClicked(0);
                        } else {
                            itemListener.itemClicked(getAdapterPosition());
                        }
                    }
                    break;

                case MotionEvent.ACTION_CANCEL:
//                        Log.d("ANT", "ACTION_CANCEL");

//                        llContent.setSelected(false);
                    listener.onTouch(event);
                    return false;

            }

            return false;
        }
    }
}
