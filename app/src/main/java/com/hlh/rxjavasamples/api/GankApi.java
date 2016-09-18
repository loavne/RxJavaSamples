package com.hlh.rxjavasamples.api;

import com.hlh.rxjavasamples.model.GankBeautyEntityResult;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * User: Hlh(tatian91@163.com)
 * Date: 2016-07-06
 * Time: 14:31
 */
public interface GankApi {
    @GET("data/福利/{number}/{page}")
    Observable<GankBeautyEntityResult> getBeauties(@Path("number") int number, @Path("page") int page);
}
