package com.hlh.ibeauty.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hlh.ibeauty.R;
import com.hlh.ibeauty.adapter.BeautyAdapter;
import com.hlh.ibeauty.adapter.MyFragmentPagerAdapter;
import com.hlh.ibeauty.api.HttpMethods;
import com.hlh.ibeauty.model.BeautyEntityResult;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.fab)
    FloatingActionButton mFab;
    @Bind(R.id.nav_view)
    NavigationView mNavView;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private MyFragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        init();
    }

    private void init() {
        //悬浮体
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //暂时没想好
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavView.setNavigationItemSelectedListener(this);

//        List<Fragment> fragments = new ArrayList<>();
//        fragments.add(new Beauty());
//        fragments.add(new Joke());
//        fragments.add(new Joke());
//        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
//        mViewPager.setAdapter(mAdapter);
//        mTabLayout.setupWithViewPager(mViewPager);

        //获取数据
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(MainActivity.this, StaggeredGridLayoutManager.GAP_HANDLING_NONE));
        final BeautyAdapter adapter = new BeautyAdapter();
        mRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        mRecyclerView.setAdapter(adapter);
        //请求
        Subscriber<BeautyEntityResult> mSubscriber = new Subscriber<BeautyEntityResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i("hlh", "onNext: dddd" + e.getMessage());
            }

            @Override
            public void onNext(BeautyEntityResult beautyEntityResult) {
                Log.i("hlh", "onNext: ddddddddddddddddddddddddddddddd");
                adapter.setImages(beautyEntityResult.results);
            }
        };
        HttpMethods.getInstance(MainActivity.this).getBeauties(mSubscriber, 20, 2);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_mycollect) {

        } else if (id == R.id.nav_change) {

        } else if (id == R.id.nav_settings) {

        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
