package com.nandi.yngsagp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nandi.yngsagp.R;
import com.nandi.yngsagp.bean.MediaInfo;
import com.nandi.yngsagp.bean.ScienceBean;
import com.nandi.yngsagp.utils.TimeUtils;

import java.util.List;

/**
 * Created by ChenPeng on 2017/11/24.
 */

public class ScienceAdapter extends RecyclerView.Adapter<ScienceAdapter.MediaViewHolder> {
    private Context context;
    private List<ScienceBean> list;
    private OnItemViewClickListener listener;

    public void setOnItemClickListener(OnItemViewClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemViewClickListener {
        void onScienceClick(int position);
    }

    public ScienceAdapter(Context context, List<ScienceBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MediaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itme_science, null);
        return new MediaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MediaViewHolder holder, final int position) {
        holder.tvName.setText(list.get(position).getTitle());
//        String format = TimeUtils.format(Long.parseLong(list.get(position).getTime()));
//        holder.tvTime.setText(format);
        holder.tvPerson.setText(list.get(position).getState());
        if (listener != null) {
            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onScienceClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size() > 0 ? list.size() : 0;
    }

    class MediaViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvTime;
        TextView tvPerson;

        public MediaViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_title);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvPerson = itemView.findViewById(R.id.tv_person);
        }
    }
}
