package com.hlh.rxjavasamples.ui.fragments;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.hlh.rxjavasamples.R;

import cn.pedant.SweetAlert.SweetAlertDialog;
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

    SweetAlertDialog  mSweetAlertDialog;


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
        Log.i("TAG", "onVisiable: ");
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

    public void showLoadingDialog() {
        if (mSweetAlertDialog == null) {
            mSweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
            mSweetAlertDialog.getProgressHelper().setBarColor(R.color.colorAccent);
            mSweetAlertDialog.setTitleText("Loading");
            mSweetAlertDialog.setCancelable(false);
        }
        mSweetAlertDialog.show();
    }

    public void dismissLoadingDialog() {
        if(mSweetAlertDialog != null)
            mSweetAlertDialog.dismiss();
    }
}
