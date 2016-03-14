package com.kizema.johnsnow.colornotes.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kizema.johnsnow.colornotes.R;
import com.kizema.johnsnow.colornotes.appviews.AppCircleImageView;
import com.kizema.johnsnow.colornotes.model.UserColor;

import java.util.List;

public class ColorsAdapter extends RecyclerView.Adapter<ColorsAdapter.ViewHolder>{

    private List<UserColor> userColors;
    private OnColorCLick listener;

    private View lastView;

    public interface OnColorCLick {
        void OnColorPicked(UserColor color);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private AppCircleImageView cb;

        public ViewHolder(View itemView) {
            super(itemView);
            cb = (AppCircleImageView) itemView.findViewById(R.id.ibCB);
        }
    }

    public ColorsAdapter(Context context, List<UserColor> userColors) {
        this.userColors = userColors;
        notifyDataSetChanged();
    }

    public void OnColorCLick(OnColorCLick listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View parentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.color_item, parent, false);
        return new ViewHolder(parentView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final UserColor userColor = userColors.get(position);
        holder.cb.setColor(userColor.getColorCode());
        holder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastView != null) {
                    lastView.setSelected(false);
                }

                holder.cb.setSelected(true);
                lastView = holder.cb;
                listener.OnColorPicked(userColor);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (userColors == null) {
            return 0;
        }

        return userColors.size();
    }
}

