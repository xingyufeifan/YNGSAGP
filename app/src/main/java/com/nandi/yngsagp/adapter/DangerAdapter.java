package com.nandi.yngsagp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nandi.yngsagp.R;
import com.nandi.yngsagp.bean.DangerUBean;
import com.nandi.yngsagp.bean.DisasterUBean;

import java.util.List;

/**
 * Created by qingsong on 2017/10/26.
 */


public class DangerAdapter extends RecyclerView.Adapter<DangerAdapter.MyViewHolder> {
    private Context mContext;
    private List<DangerUBean> bean;

    public DangerAdapter(Context context, List bean) {
        mContext = context;
        this.bean = bean;
    }


    @Override
    public DangerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //此处动态加载ViewHolder的布局文件并返回holder
        View view = LayoutInflater.from(mContext).inflate(R.layout.disaster_item, null);
        DangerAdapter.MyViewHolder holderA = new DangerAdapter.MyViewHolder(view);
        return holderA;
    }

    @Override
    public void onBindViewHolder(DangerAdapter.MyViewHolder holder, int position) {
        holder.textPlace.setText(bean.get(position).getPlace());
        holder.textType.setText(bean.get(position).getType());
        holder.textToTime.setText(bean.get(position).getToTime());
    }

    @Override
    public int getItemCount() {
        //生成的item的数量
        return bean.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textPlace;
        public TextView textType;
        public TextView textToTime;


        public MyViewHolder(View itemView) {
            super(itemView);
            textPlace = (TextView) itemView.findViewById(R.id.place);
            textType = (TextView) itemView.findViewById(R.id.type);
            textToTime = (TextView) itemView.findViewById(R.id.toTime);
        }
    }
}
