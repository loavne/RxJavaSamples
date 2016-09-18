package com.hlh.ibeauty.api;

import com.hlh.ibeauty.model.BeautyEntityResult;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * User: Hlh(tatian91@163.com)
 * Date: 2016-07-08
 * Time: 12:01
 */
public interface Gank {
    @GET("data/福利/{number}/{page}")
    Observable<BeautyEntityResult> getBeauties(@Path("number") int number, @Path("page") int page);
}