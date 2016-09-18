package com.hlh.cache;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * User: Hlh(tatian91@163.com)
 * Date: 2016-07-11
 * Time: 16:04
 */
public interface RetrofitAPI {
    @GET("data/福利/{number}/{page}")
    Observable<BeautyEntityResult> getBeauties(@Path("number") int number, @Path("page") int page);
}
