package com.hlh.rxjavasamples.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hlh.rxjavasamples.R;
import com.hlh.rxjavasamples.adapter.ItemAdapter;
import com.hlh.rxjavasamples.api.Request;
import com.hlh.rxjavasamples.model.GankBeautyEntity;
import com.hlh.rxjavasamples.model.GankBeautyEntityResult;
import com.hlh.rxjavasamples.model.ItemImage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 该类作用：体验RxJava的map
 * User: Hlh(tatian91@163.com)
 * Date: 2016-07-04
 * Time: 10:27
 */
public class Tab2 extends BaseFragment {

    // 标志位，标志已经初始化完成。
    public boolean isPrepared = false;
    public int page = 0;
    ItemAdapter mAdapter = new ItemAdapter();

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.pageTv)
    TextView mPageTv;
    @Bind(R.id.previousPageBt)
    AppCompatButton mPreviousPageBt;
    @Bind(R.id.nextPageBt)
    AppCompatButton mNextPageBt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_tab2, container, false);
        ButterKnife.bind(this, view);
        isPrepared = true;
        bindData();
        loadData();
        return view;
    }

    /**
     * 绑定数据
     */
    private void bindData() {
        //RecyclerView
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        //SwipeRefreshLayout
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        mSwipeRefreshLayout.setEnabled(false);
    }

    /** 上一页 请求数据 **/
    @OnClick(R.id.previousPageBt)
    void previousPage() {
        --page;
        loadData();
        if(page==1)
            mPreviousPageBt.setEnabled(false);
    }

    /** 下一页 请求数据**/
    @OnClick(R.id.nextPageBt)
    void nextPage() {
        ++page;
        loadData();
        if(page == 2)
            mPreviousPageBt.setEnabled(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    protected void loadData() {
        if (!isPrepared || !isVisable)
            return;
        //请求数据
        mSwipeRefreshLayout.setRefreshing(true);
        unsubscribe();
        mSubscription = Request.getGankApi()
                .getBeauties(15, page)
                //这里很关键，Map转换，通过getBeauties()获取到数据，转化成List<ItemImage> ,其实转化的就是每一个图片的描述，将createAt改成了日期
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
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ItemImage>>() {
                    @Override
                    public void onCompleted() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(List<ItemImage> itemImages) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mAdapter.setImages(itemImages);
                    }
                });
    }
}
