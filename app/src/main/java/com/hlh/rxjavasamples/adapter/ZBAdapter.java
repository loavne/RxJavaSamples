package com.hlh.rxjavasamples.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hlh.rxjavasamples.R;
import com.hlh.rxjavasamples.model.ZBImage;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * User: Hlh(tatian91@163.com)
 * Date: 2016-07-04
 * Time: 15:41
 */
public class ZBAdapter extends RecyclerView.Adapter {

    List<ZBImage> mZBImageList;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new ZBViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ZBViewHolder zbViewHolder = (ZBViewHolder) holder;
        ZBImage zbImage = mZBImageList.get(position);
        zbViewHolder.mDescriptionTv.setText(zbImage.description);
        //使用Glide加载图片
        Glide.with(holder.itemView.getContext()).load(zbImage.image_url).into(zbViewHolder.mImageIv);
    }

    @Override
    public int getItemCount() {
        return mZBImageList == null ? 0 : mZBImageList.size();
    }

    /**
     * 设置图片
     * @param list 图片源
     */
    public void setImages(List<ZBImage> list) {
        this.mZBImageList = list;
        notifyDataSetChanged();
    }

    class ZBViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.imageIv)
        ImageView mImageIv;
        @Bind(R.id.descriptionTv)
        TextView mDescriptionTv;
        public ZBViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
