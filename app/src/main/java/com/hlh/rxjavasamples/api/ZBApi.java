package com.hlh.rxjavasamples.api;

import com.hlh.rxjavasamples.model.ZBImage;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * User: Hlh(tatian91@163.com)
 * Date: 2016-07-04
 * Time: 16:15
 */
public interface ZBApi {
    @GET("search")
    Observable<List<ZBImage>> search(@Query("q") String query);
}
