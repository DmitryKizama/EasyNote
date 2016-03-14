package com.kizema.johnsnow.colornotes.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.kizema.johnsnow.colornotes.R;
import com.kizema.johnsnow.colornotes.model.Note;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NoteViewHolder>
        implements NoteViewHolder.OnItemListener{

    private List<Note> notifications;
    private OnNotifClickListener listener;

    public interface OnNotifClickListener {
        void onItemDown(View v, int pos);
        void onTouch(MotionEvent me);
        void onItemClicked(Note note, int pos);
        void itemRemoveClicked(Note note, int pos);
        void itemEditClicked(Note note, int pos);
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
    public void removeNoteAtPos(int pos) {
        listener.itemRemoveClicked(notifications.get(pos), pos);
    }

    @Override
    public void editNoteAtPos(int pos) {
        listener.itemEditClicked(notifications.get(pos), pos);
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
    }

    @Override
    public int getItemCount() {
        if (notifications == null) {
            return 0;
        }

        return notifications.size();
    }
}
