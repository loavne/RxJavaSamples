package com.hlh.rxjavasamples.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hlh.rxjavasamples.R;
import com.hlh.rxjavasamples.model.ItemImage;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * User: Hlh(tatian91@163.com)
 * Date: 2016-07-06
 * Time: 14:17
 */
public class ItemAdapter extends RecyclerView.Adapter{
    private List<ItemImage> mImageList;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        ItemImage image = mImageList.get(position);
        Glide.with(holder.itemView.getContext()).load(image.imageUrl).into(itemViewHolder.imageIv);
        itemViewHolder.descriptionTv.setText(image.description);
    }

    @Override
    public int getItemCount() {
        return mImageList == null ? 0 : mImageList.size();
    }

    public void setImages(List<ItemImage> images) {
        this.mImageList = images;
        notifyDataSetChanged();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.imageIv)
        ImageView imageIv;
        @Bind(R.id.descriptionTv)
        TextView descriptionTv;
        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
