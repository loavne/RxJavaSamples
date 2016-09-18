package com.hlh.rxjavasamples.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Hlh(tatian91@163.com)
 * Date: 2016-07-04
 * Time: 09:47
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mList = new ArrayList<>();
    private String tabTitles[] = new String[]{"基本使用", "map变换", "zip组合"};

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.mList = list;
    }

    @Override
    public Fragment getItem(int position) {
//        return Tab1.newInstance(position + 1);
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
