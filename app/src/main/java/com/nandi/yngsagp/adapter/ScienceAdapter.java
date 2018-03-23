package com.nandi.yngsagp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.nandi.yngsagp.R;
import com.nandi.yngsagp.bean.MediaInfo;
import com.nandi.yngsagp.bean.ScienceBean;
import com.nandi.yngsagp.utils.TimeUtils;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Qingsong on 2017/11/24.
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
    public void onBindViewHolder(final MediaViewHolder holder, final int position) {
        if ("1".equals(list.get(position).getState())) {
            holder.tvPerson.setText("新");
            holder.tvPerson.setVisibility(View.VISIBLE);
        } else if ("3".equals(list.get(position).getState())) {
            holder.tvPerson.setText("自");
            holder.tvPerson.setVisibility(View.VISIBLE);
        }else if ("2".equals(list.get(position).getState())){
           holder.tvPerson.setVisibility(View.GONE);
        }
        holder.tvName.setText(list.get(position).getTitle());
        long stamp = TimeUtils.getTimeStamp(list.get(position).getTime(), "yyyy-MM-dd HH:mm:ss");
        String format = TimeUtils.getTimeStateNew(String.valueOf(stamp));
        holder.tvTime.setText(format);
        holder.nickName.setText("来自："+list.get(position).getNickname());
        if (listener != null) {
            holder.all.setOnClickListener(new View.OnClickListener() {
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
        TextView nickName;
        LinearLayout all;

        public MediaViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_title);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvPerson = itemView.findViewById(R.id.tv_person);
            all = itemView.findViewById(R.id.all_ll);
            nickName = itemView.findViewById(R.id.tv_nickName);
        }
    }
}
