package com.hlh.ibeauty.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hlh.ibeauty.R;
import com.hlh.ibeauty.model.BeautyEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * User: Hlh(tatian91@163.com)
 * Date: 2016-07-06
 * Time: 14:17
 */
public class BeautyAdapter extends RecyclerView.Adapter {

    private List<BeautyEntity> mList;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        Glide.with(holder.itemView.getContext()).load(mList.get(position).url).into(itemViewHolder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setImages(List<BeautyEntity> images) {
        this.mList = images;
        notifyDataSetChanged();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.imageView)
        ImageView mImageView;
        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
