package com.nandi.yngsagp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.ImageUtils;
import com.nandi.yngsagp.R;
import com.nandi.yngsagp.bean.PhotoPath;

import java.util.List;

/**
 * Created by ChenPeng on 2017/9/30.
 */

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PicViewHolder> {
    private Context context;
    private List<PhotoPath> list;
    private OnItemViewClickListener listener;

    public PictureAdapter(Context context, List<PhotoPath> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemViewClickListener(OnItemViewClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemViewClickListener {
        void onPictureClick(int position);

        void onDeleteClick(int position);
    }

    @Override
    public PicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_picture, null);
        return new PicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PicViewHolder holder, final int position) {
        Bitmap bitmap = ImageUtils.getBitmap(list.get(position).getPath(), 100, 100);
        holder.ivImage.setImageBitmap(bitmap);
        if (listener != null) {
            holder.ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onPictureClick(position);
                }
            });
            holder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class PicViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        ImageView ivDelete;

        public PicViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.iv_item_picture);
            ivDelete = (ImageView) itemView.findViewById(R.id.iv_item_delete);
        }
    }
}
