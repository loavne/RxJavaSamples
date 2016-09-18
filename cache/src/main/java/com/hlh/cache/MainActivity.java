package com.hlh.cache;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private TextView mTgt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTgt = (TextView) findViewById(R.id.tgt);
        init();
    }

    private void init() {
        File cacheFile = new File(this.getExternalCacheDir(),"JBBBBB");
        Cache cache = new Cache(cacheFile,1024*1024*50);
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!HttpUtils.isNetworkConnected(MainActivity.this)) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (HttpUtils.isNetworkConnected(MainActivity.this)) {
                    int maxAge = 0 * 60;
                    // 有网络时 设置缓存超时时间0个小时
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };
        OkHttpClient client = new OkHttpClient.Builder().cache(cache)
                .addInterceptor(interceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);


        retrofitAPI.getBeauties(10, 2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BeautyEntityResult>() {
                    @Override
                    public void onCompleted() {
                        Log.i("hlh", "onCompleted: ----------");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("hlh", "onError: ----------" + e.toString());
                    }

                    @Override
                    public void onNext(BeautyEntityResult beautyEntityResult) {
                        Log.i("hlh", "onNext: ----------" + beautyEntityResult.results.size());
                        mTgt.setText( beautyEntityResult.results.get(0).url);
                    }
                });
    }
}
