package com.nandi.yngsagp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nandi.yngsagp.R;
import com.nandi.yngsagp.bean.SuperBean;

import java.util.List;

/**
 * Created by qingsong on 2017/10/26.
 */


public class DangerAPosAdapter extends RecyclerView.Adapter<DangerAPosAdapter.MyViewHolder> {
    private Context mContext;
    public DisasterAPosAdapter.OnItemClickListener mOnItemClickListener;
    private List<SuperBean> listBeans;

    public DangerAPosAdapter(Context context, List<SuperBean> listBeans) {
        mContext = context;
        this.listBeans = listBeans;
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(DisasterAPosAdapter.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public DangerAPosAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //此处动态加载ViewHolder的布局文件并返回holder
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_disaster_list, null);
        DangerAPosAdapter.MyViewHolder holderA = new DangerAPosAdapter.MyViewHolder(view);
        return holderA;
    }

    @Override
    public void onBindViewHolder(DangerAPosAdapter.MyViewHolder holder, final int position) {
        holder.textDisNum.setText(listBeans.get(position).getDisasterNum());
//        holder.textAddress.setText(disasterListBean.getData().get(position).getPersonel());
//        holder.textToTime.setText(disasterListBean.getData().get(position).getFindTime());
        if (mOnItemClickListener != null) {
            holder.toNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        //生成的item的数量
        return listBeans.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textDisNum;
        public TextView textAddress;
        public TextView textToTime;
        public TextView toNext;


        public MyViewHolder(View itemView) {
            super(itemView);
            textDisNum = itemView.findViewById(R.id.disNum);
            textAddress = itemView.findViewById(R.id.address);
            textToTime = itemView.findViewById(R.id.toTime);
            toNext = itemView.findViewById(R.id.toNext);
        }
    }
}
