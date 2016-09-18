package com.hlh.ibeauty.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Hlh(tatian91@163.com)
 * Date: 2016-07-08
 * Time: 11:11
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mList = new ArrayList<>();
    private String tabTitles[] = new String[]{"美女", "搞笑", "搞笑"};

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.mList = list;
    }

    @Override
    public Fragment getItem(int position) {
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
