package com.hlh.ibeauty.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * User: Hlh(tatian91@163.com)
 * Date: 2016-07-08
 * Time: 11:57
 */
public class BeautyEntityResult {
    public boolean error;
    public @SerializedName("results")
    List<BeautyEntity> results;
}
