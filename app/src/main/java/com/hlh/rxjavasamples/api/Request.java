package com.hlh.rxjavasamples.api;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * User: Hlh(tatian91@163.com)
 * Date: 2016-07-04
 * Time: 16:16
 */
public class Request {
    private static OkHttpClient mOkHttpClient = new OkHttpClient();
    private static Converter.Factory mConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory mCallAdapter = RxJavaCallAdapterFactory.create();

    public static ZBApi getZBApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://zhuangbi.info/")
                .client(mOkHttpClient)
                .addConverterFactory(mConverterFactory)
                .addCallAdapterFactory(mCallAdapter)
                .build();
        ZBApi zbApi = retrofit.create(ZBApi.class);
        return zbApi;
    }

    public static GankApi getGankApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/api/")
                .addCallAdapterFactory(mCallAdapter)
                .addConverterFactory(mConverterFactory)
                .client(mOkHttpClient)
                .build();
        GankApi gankApi = retrofit.create(GankApi.class);
        return gankApi;
    }
}
