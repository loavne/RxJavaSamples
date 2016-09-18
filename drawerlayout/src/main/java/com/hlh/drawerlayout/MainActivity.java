package com.hlh.drawerlayout;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.content)
    FrameLayout mContent;
    @Bind(R.id.navigationView)
    NavigationView mNavigationView;
    @Bind(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mToolbar.setTitle("测试");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        //初始化状态
        mDrawerToggle.syncState();
        //将DrawerLayout与DrawerToggle绑定
        mDrawerLayout.addDrawerListener(mDrawerToggle);
//        mDrawerLayout.setScrimColor(Color.TRANSPARENT); //去除侧边阴影

        //设置导航栏NavigationView的点击事件
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_item1:
                        mToolbar.setTitle("欢迎");
                        break;
                    case R.id.navigation_item2:
                        mToolbar.setTitle("关注");
                        break;
                    case R.id.navigation_item3:
                        mToolbar.setTitle("简书");
                        break;
                    case R.id.navigation_sub_item1:
                        mToolbar.setTitle("天天吃豆腐");
                        break;
                    case R.id.navigation_sub_item2:
                        mToolbar.setTitle("简书");
                        break;
                    case R.id.navigation_sub_item3:
                        mToolbar.setTitle("天天吃豆腐");
                        break;
                }

                //将选中设为点击状态
                item.setChecked(true);
                //关闭抽屉
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

    }
}
