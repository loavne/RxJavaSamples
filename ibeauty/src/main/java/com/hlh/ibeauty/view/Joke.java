package com.hlh.ibeauty.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hlh.ibeauty.BaseFragment;
import com.hlh.ibeauty.R;

/**
 * User: Hlh(tatian91@163.com)
 * Date: 2016-07-08
 * Time: 11:17
 */
public class Joke extends BaseFragment {
    @Override
    protected void loadData() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_beauty, null);
        return view;
    }
}
