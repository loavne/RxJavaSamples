package com.hlh.rxjavasamples.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hlh.rxjavasamples.R;
import com.hlh.rxjavasamples.adapter.ItemAdapter;
import com.hlh.rxjavasamples.api.Request;
import com.hlh.rxjavasamples.model.GankBeautyEntity;
import com.hlh.rxjavasamples.model.GankBeautyEntityResult;
import com.hlh.rxjavasamples.model.ItemImage;
import com.hlh.rxjavasamples.model.ZBImage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * User: Hlh(tatian91@163.com)
 * Date: 2016-07-04
 * Time: 10:27
 */
public class Tab3 extends BaseFragment {

    // 标志位，标志已经初始化完成。
    public boolean isPrepared = false;

    ItemAdapter mAdapter = new ItemAdapter();

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void loadData() {
        if (!isPrepared || !isVisable)
            return;
        //请求数据
        mSwipeRefreshLayout.setRefreshing(true);
        unsubscribe();
        mSubscription = Observable.zip(
                //zip第一个参数，请求gank数据
                Request.getGankApi().getBeauties(10, 1)
                        .map(new Func1<GankBeautyEntityResult, List<ItemImage>>() {
                            @Override
                            public List<ItemImage> call(GankBeautyEntityResult gankBeautyEntityResult) {
                                //获取的结果集
                                List<GankBeautyEntity> gankBeauties = gankBeautyEntityResult.beauties;
                                //定义被转化的数量
                                List<ItemImage> listItem = new ArrayList<>(gankBeauties.size());
                                SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
                                SimpleDateFormat output = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
                                for (GankBeautyEntity entity : gankBeauties) {
                                    ItemImage item = new ItemImage();
                                    try {
                                        Date date = input.parse(entity.createdAt);
                                        item.description = output.format(date);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                        item.description = "unknown date";
                                    }
                                    item.imageUrl = entity.url;
                                    listItem.add(item);
                                }
                                return listItem;
                            }
                        }),
                //第二个参数，请求可爱图片
                Request.getZBApi().search("可爱"),
                //第三个参数整合前面两个请求的数据。Func2(a1,a2,a3)a1只zip的第一个参数结果，a2指zip的第二个参数结果，a3为整合a1,a2形成的
                new Func2<List<ItemImage>, List<ZBImage>, List<ItemImage>>() {
                    @Override
                    public List<ItemImage> call(List<ItemImage> itemImages, List<ZBImage> zbImages) {
                        List<ItemImage> lists = new ArrayList<ItemImage>();
                        for(int i = 0; i<zbImages.size(); i++) {
                            lists.add(itemImages.get(i));
                            ItemImage itemImage = new ItemImage();
                            itemImage.description = zbImages.get(i).description;
                            itemImage.imageUrl = zbImages.get(i).image_url;
                            lists.add(itemImage);
                        }
                        return lists;
                    }
                }
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ItemImage>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<ItemImage> itemImages) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mAdapter.setImages(itemImages);
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_tab1, container, false);
        ButterKnife.bind(this, view);
        isPrepared = true;
        bandData();
        loadData();
        return view;
    }

    private void bandData() {
        //RecyclerView
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        //SwipeRefreshLayout
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        mSwipeRefreshLayout.setEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
