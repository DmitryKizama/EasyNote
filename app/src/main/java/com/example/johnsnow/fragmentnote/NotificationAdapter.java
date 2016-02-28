package com.example.johnsnow.fragmentnote;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<String> notifications;
    private OnNotifClickListener listener;

    public interface OnNotifClickListener{
        void onNotifLongClick(String word, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv;

        public ViewHolder(View v) {
            super(v);
            tv = (TextView) v.findViewById(R.id.tv);
        }
    }

    public NotificationAdapter(Context context) {
        notifications = new ArrayList<>();
    }

    public void addBottom(String word){
        notifications.add(word);
        notifyItemInserted(notifications.size() - 1);
    }

    public void deleteElementAtPos(int position){
        if (position >= notifications.size()) {
            return;
        }
        notifications.remove(position);
        notifyItemRemoved(position);
    }

    public void update(int position, String newText) {
        if (position >= notifications.size()) {
            return;
        }
        notifications.set(position, newText);
        notifyItemChanged(position);
    }

    public void setOnNotifClickListener(OnNotifClickListener listener){
            this.listener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View parentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(parentView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String notification = notifications.get(position);

        holder.tv.setText(notification);

        holder.tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null) {
                    listener.onNotifLongClick(notification, position);
                }
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        if (notifications == null){
            return 0;
        }

        return notifications.size();
    }
}
