package com.hlh.rxjavasamples.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hlh.rxjavasamples.R;
import com.hlh.rxjavasamples.adapter.ZBAdapter;
import com.hlh.rxjavasamples.api.Request;
import com.hlh.rxjavasamples.model.ZBImage;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * RxJava基本使用
 * User: Hlh(tatian91@163.com)
 * Date: 2016-07-04
 * Time: 10:27
 */
public class Tab1 extends BaseFragment {
    public static final String TAG = "tag";

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    ZBAdapter mZBAdapter = new ZBAdapter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_tab1, container, false);
        ButterKnife.bind(this, view);
        isPrepared = true;
        //绑定控件
        initData();
        //调用加载数据
        loadData();
        return view;
    }

    private void initData() {
        //设置表格格式，一行2列
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.setAdapter(mZBAdapter);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipeRefreshLayout.setEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    protected void loadData() {
        if (!isPrepared || !isVisable)
            return;
        //请求数据
//        mSwipeRefreshLayout.setRefreshing(true);
        showLoadingDialog();
        mSubscription = Request.getZBApi()
                .search("飞")               //查询关键字，暂时固定
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ZBImage>>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted: ");
//                        mSwipeRefreshLayout.setRefreshing(false);
                        dismissLoadingDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: ");
//                        mSwipeRefreshLayout.setRefreshing(false);
                        dismissLoadingDialog();
                    }

                    @Override
                    public void onNext(List<ZBImage> zbImages) {
                        Log.i(TAG, "onNext: " + zbImages.size());
//                        mSwipeRefreshLayout.setRefreshing(false);
                        mZBAdapter.setImages(zbImages);
                        dismissLoadingDialog();
                    }
                });
    }
}
