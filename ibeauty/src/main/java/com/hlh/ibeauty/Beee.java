package com.hlh.ibeauty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hlh.ibeauty.adapter.BeautyAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * User: Hlh(tatian91@163.com)
 * Date: 2016-07-11
 * Time: 15:54
 */
public class Beee extends BaseFragment{

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private BeautyAdapter mAdapter;

    @Override
    protected void loadData() {
        //没有初始化完成，或者不可见时，结束
        if(!isPrepared || !isVisable)
            return;
        //


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_beauty, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        isPrepared = true;
        //设置瀑布流
        mAdapter = new BeautyAdapter();
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                layoutManager.invalidateSpanAssignments(); //防止第一行到顶部有空白区域
            }
        });
        loadData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
