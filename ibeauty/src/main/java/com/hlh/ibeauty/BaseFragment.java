package com.hlh.ibeauty;

import android.support.v4.app.Fragment;

import rx.Subscription;

/**
 * User: Hlh(tatian91@163.com)
 * Date: 2016-07-04
 * Time: 09:37
 */
public abstract class BaseFragment extends Fragment {
    //是否可见
    protected boolean isVisable;
    // 标志位，标志已经Fragment初始化完成。
    public boolean isPrepared = false;

    //被观察者对象
    protected Subscription mSubscription;

    /**
     * 实现Fragment数据的缓加载
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisable = true;
            onVisiable();
        } else {
            isVisable = false;
            onInVisiable();
        }
    }

    protected void onInVisiable() {
    }

    protected void onVisiable() {
        //加载
        loadData();
    }

    protected abstract void loadData();

    /**
     * 取消订阅（RxJava）
     */
    protected void unsubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
