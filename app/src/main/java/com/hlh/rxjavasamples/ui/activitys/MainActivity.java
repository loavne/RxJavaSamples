package com.hlh.rxjavasamples.ui.activitys;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.hlh.rxjavasamples.R;
import com.hlh.rxjavasamples.adapter.MyFragmentPagerAdapter;
import com.hlh.rxjavasamples.ui.fragments.Tab1;
import com.hlh.rxjavasamples.ui.fragments.Tab2;
import com.hlh.rxjavasamples.ui.fragments.Tab3;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    private MyFragmentPagerAdapter mAdapter;
    private List<Fragment> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mList = new ArrayList<>();
        mList.add(new Tab1());
        mList.add(new Tab2());
        mList.add(new Tab3());

        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mList);

        mViewPager.setAdapter(mAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
    }
}
