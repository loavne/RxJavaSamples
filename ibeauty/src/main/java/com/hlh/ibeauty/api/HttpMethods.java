package com.hlh.ibeauty.api;

import android.content.Context;
import android.util.Log;

import com.hlh.ibeauty.model.BeautyEntityResult;
import com.hlh.ibeauty.utils.FileUtil;
import com.hlh.ibeauty.utils.NetUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * User: Hlh(tatian91@163.com)
 * Date: 2016-07-08
 * Time: 12:01
 */
public class HttpMethods {
    public static Context mContext;
    public static final String BASE_URL = "http://gank.io/api/";
    public static final String CACHE_DIR = "data";
    private static final int DEFAULT_TIME = 5;
    private final Retrofit mRetrofit;
    private final Gank mGank;

    //构造方法私有（可以创建带参数的）
    private HttpMethods() {
        //设置缓存路径
        File httpCacheDir = new File(FileUtil.getDiskCacheDir(mContext), CACHE_DIR);
        //设置缓存大小 50M
        Cache cache = new Cache(httpCacheDir, 50 * 1024 * 1024);
        //设置拦截器
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                //没网
                if(!NetUtil.isNetworkable(mContext)){
                    request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                    Log.i("Hlh", "暂无网络");
                }
                Response response = chain.proceed(request);
                if (NetUtil.isNetworkable(mContext)) {
                    // 有网络时 设置缓存超时时间0个小时
                    int maxAge = 0 * 60;
                    response.newBuilder().header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
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

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIME, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .cache(cache).build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mGank = mRetrofit.create(Gank.class);
    }

    //创建单例
    private static class SingletonHolder{
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance(Context context){
        mContext = context;
        return SingletonHolder.INSTANCE;
    }


    public void getBeauties(Subscriber<BeautyEntityResult> subscriber, int number, int page) {
        mGank.getBeauties(number, page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


}
