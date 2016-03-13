package com.example.johnsnow.fragmentnote.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.johnsnow.fragmentnote.R;
import com.example.johnsnow.fragmentnote.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<Note> notifications;
    private OnNotifClickListener listener;

    public interface OnNotifClickListener {
        void onNotifLongClick(Note note, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv;

        public ViewHolder(View v) {
            super(v);
            tv = (TextView) v.findViewById(R.id.tv);
        }
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View parentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(parentView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Note notification = notifications.get(position);

        holder.tv.setText(notification.getName());

        holder.tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null) {
                    listener.onNotifLongClick(notification, holder.getAdapterPosition());//holder.getAdapterPosition - give back position
                }
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        if (notifications == null) {
            return 0;
        }

        return notifications.size();
    }
}
