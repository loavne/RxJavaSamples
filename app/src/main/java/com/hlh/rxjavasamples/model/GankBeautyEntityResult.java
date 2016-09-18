package com.hlh.rxjavasamples.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * User: Hlh(tatian91@163.com)
 * Date: 2016-07-06
 * Time: 14:50
 */
public class GankBeautyEntityResult {
    public boolean error;
    public @SerializedName("results")
    List<GankBeautyEntity> beauties;
}
