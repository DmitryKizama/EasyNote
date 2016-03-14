package com.kizema.johnsnow.colornotes.adapters;

import android.graphics.PointF;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kizema.johnsnow.colornotes.R;
import com.kizema.johnsnow.colornotes.helper.UIHelper;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    private static final int MOVE_DIST = UIHelper.getPixel(80);
    private static final int OFFSET = UIHelper.getPixel(30);
    private static final int OFFSET_CLICK = UIHelper.getPixel(5);



    public TextView tv;

    private RelativeLayout rlParent;

    private View ivRemove, ivEdit;
    private NotificationAdapter.OnNotifClickListener listener;

    private OnItemListener itemListener;

    public interface OnItemListener {
        void removeNoteAtPos(int pos);
        void editNoteAtPos(int pos);

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

        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteViewHolder.this.itemListener.editNoteAtPos(getAdapterPosition());
            }
        });

        ivRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteViewHolder.this.itemListener.removeNoteAtPos(getAdapterPosition());
            }
        });

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivRemove.getLayoutParams();
        params.leftMargin = UIHelper.getW() + MOVE_DIST;

        ViewGroup.LayoutParams p = itemView.getLayoutParams();
        p.width = UIHelper.getW() + MOVE_DIST * 2;
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

                    down.x = event.getX();
                    down.y = event.getY();
                    onDown = true;

                    rlParent.setSelected(true);

                    listener.onTouch(event);

                    return true;
                case MotionEvent.ACTION_MOVE:
//                        Log.d("ANT", " ACTION_MOVE");
                    listener.onTouch(event);

                    if (Math.abs(down.x - event.getX()) > OFFSET_CLICK ||
                            Math.abs(down.y - event.getY()) > OFFSET_CLICK){
                        onDown = false;
                    }

                    if (!(0 <= event.getX() && event.getX() <= itemView.getWidth()
                            && 0 <= event.getY() && event.getY() <= itemView.getHeight())) {
//                            Log.d("ANT", " ACTION_MOVE   OUT");
                        onDown = false;
                        rlParent.setSelected(false);
                        return true;
                    }

                    if (Math.abs(down.x - event.getX()) > OFFSET || Math.abs(down.y - event.getY()) > OFFSET) {
//                            Log.d("ANT", " ACTION_MOVE   OUT");
                        onDown = false;
                        rlParent.setSelected(false);
                        return true;
                    }

                    return true;
                case MotionEvent.ACTION_UP:
//                        Log.d("ANT", "ACTION_UP");
                    rlParent.setSelected(false);
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
                    rlParent.setSelected(false);
                    listener.onTouch(event);
                    return false;

            }

            return false;
        }
    }
}
