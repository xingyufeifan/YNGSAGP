package com.nandi.yngsagp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nandi.yngsagp.R;
import com.nandi.yngsagp.bean.MediaInfo;

import java.util.List;

/**
 * Created by ChenPeng on 2017/11/24.
 */

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MediaViewHolder> {
    private Context context;
    private List<MediaInfo> list;
    private OnItemViewClickListener listener;

    public void setOnItemClickListener(OnItemViewClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemViewClickListener {
        void onMediaClick(int position);
    }

    public MediaAdapter(Context context, List<MediaInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MediaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_media_text, null);
        return new MediaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MediaViewHolder holder, final int position) {
        holder.tvName.setText(list.get(position).getFileName());
        if (listener != null) {
            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onMediaClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MediaViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;

        public MediaViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_media_name);
        }
    }
}
