package com.nandi.yngsagp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nandi.yngsagp.R;
import com.nandi.yngsagp.bean.DisasterListBean;

import java.util.List;

/**
 * Created by qingsong on 2017/10/26.
 */


public class DisasterAdapter extends RecyclerView.Adapter<DisasterAdapter.MyViewHolder> {
    private Context mContext;
    private DisasterListBean disasterListBean;

    public DisasterAdapter(Context context, DisasterListBean ListBean) {
        mContext = context;
        this.disasterListBean = ListBean;
    }


    @Override
    public DisasterAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //此处动态加载ViewHolder的布局文件并返回holder
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_disaster_list, null);
        DisasterAdapter.MyViewHolder holderA = new DisasterAdapter.MyViewHolder(view);
        return holderA;
    }

    @Override
    public void onBindViewHolder(DisasterAdapter.MyViewHolder holder, int position) {
        holder.textDisNum.setText(disasterListBean.getData().get(position).getCurrentLocation());
        holder.textAddress.setText((String)disasterListBean.getData().get(position).getAddress());
        holder.textToTime.setText(disasterListBean.getData().get(position).getFindTime());
    }

    @Override
    public int getItemCount() {
        //生成的item的数量
        return disasterListBean.getData().size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textDisNum;
        public TextView textAddress;
        public TextView textToTime;


        public MyViewHolder(View itemView) {
            super(itemView);
            textDisNum = itemView.findViewById(R.id.disNum);
            textAddress = itemView.findViewById(R.id.address);
            textToTime = itemView.findViewById(R.id.toTime);
        }
    }
}
