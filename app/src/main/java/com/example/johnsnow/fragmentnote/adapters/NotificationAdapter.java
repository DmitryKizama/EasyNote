package com.example.johnsnow.fragmentnote.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.johnsnow.fragmentnote.R;
import com.example.johnsnow.fragmentnote.model.Note;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NoteViewHolder>
        implements NoteViewHolder.OnItemListener{

    private List<Note> notifications;
    private OnNotifClickListener listener;

    public interface OnNotifClickListener {
        void onNotifLongClick(Note note, int position);

        void onItemDown(View v, int pos);
        void onTouch(MotionEvent me);
        void onItemClicked(Note thread, int pos);
//        void itemRemoved(int pos, int amount);
    }

    public NotificationAdapter(Context context, List<Note> listNotes) {
        notifications = listNotes;
        notifyDataSetChanged();
    }

    public void addBottom(Note note) {
        notifications.add(note);
        notifyItemInserted(notifications.size() - 1);
    }

    public void deleteElementAtPos(int position) {
        if (position >= notifications.size()) {
            return;
        }
        notifications.remove(position);
        notifyItemRemoved(position);
    }

    public void update(int position, Note newNote) {
        if (position >= notifications.size()) {
            return;
        }
        notifications.set(position, newNote);
        notifyItemChanged(position);
    }

    public void setOnNotifClickListener(OnNotifClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void remove(int pos) {
        //todo item at pos removed
    }

    @Override
    public void itemClicked(int pos) {
        listener.onItemClicked(notifications.get(pos), pos);
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View parentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new NoteViewHolder(parentView, listener, this);
    }

    @Override
    public void onBindViewHolder(final NoteViewHolder holder, final int position) {
        final Note notification = notifications.get(position);

        holder.tv.setText(notification.getName());

//        holder.tv.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                if (listener != null) {
//                    listener.onNotifLongClick(notification, holder.getAdapterPosition());//holder.getAdapterPosition - give back position
//                }
//                return true;
//            }
//        });

    }

    @Override
    public int getItemCount() {
        if (notifications == null) {
            return 0;
        }

        return notifications.size();
    }
}
