package com.nandi.yngsagp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.nandi.yngsagp.R;
import com.nandi.yngsagp.bean.SuperBean;

import java.util.List;

/**
 * @author qingsong  on 2017/10/26.
 */


public class SuperAdapter extends RecyclerView.Adapter<SuperAdapter.MyViewHolder> {
    private Context mContext;
    public SuperAdapter.OnItemClickListener mOnItemClickListener;
    private List<SuperBean> listBeans;

    public SuperAdapter(Context context, List<SuperBean> listBeans) {
        mContext = context;
        this.listBeans = listBeans;
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(SuperAdapter.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public SuperAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //此处动态加载ViewHolder的布局文件并返回holder
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_super_list, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SuperAdapter.MyViewHolder holder, final int position) {
        holder.tvTime.setText(listBeans.get(position).getFindTime());
        holder.tvAddress.setText(listBeans.get(position).getAddress());
        switch (listBeans.get(position).getIsDispose()){
            case 0:
                holder.tvType.setText("已完成");
                break;
            case 1:
                holder.tvType.setText("未处理");
                break;
            case 2:
                holder.tvType.setText("已上报");
                break;
            case 3:
                holder.tvType.setText("处置中");
                break;
            case 4:
                holder.tvType.setText("调查中");
                break;
        }
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
        TextView tvTime;
        TextView tvAddress;
        TextView tvType;
        TextView toNext;


        MyViewHolder(View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvType = itemView.findViewById(R.id.tv_type);
            toNext = itemView.findViewById(R.id.toNext);
        }
    }
}
