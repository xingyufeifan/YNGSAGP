package com.nandi.yngsagp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nandi.yngsagp.R;
import com.nandi.yngsagp.bean.MediaInfo;

import java.util.List;
 /**
 * Created by ChenPeng on 2017/11/22.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    private Context context;
    private List<MediaInfo> list;
    private OnItemViewClickListener listener;

    public PhotoAdapter(Context context, List<MediaInfo> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemViewClickListener(OnItemViewClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemViewClickListener {
        void onPictureClick(int position);
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_photo_view,null);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, final int position) {
        Glide.with(context).load(context.getString(R.string.local_base_url) + "dangerous/download/" + list.get(position).getFileName() + "/" + list.get(position).getType())
                .placeholder(R.drawable.downloading).error(R.drawable.download_pass).into(holder.ivImage);
        if (listener != null) {
            holder.ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onPictureClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;

        PhotoViewHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_photo);
        }
    }
}
