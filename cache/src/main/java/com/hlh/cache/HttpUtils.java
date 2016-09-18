package com.hlh.cache;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * User: Hlh(tatian91@163.com)
 * Date: 2016-07-11
 * Time: 16:02
 */
public class HttpUtils {
    /**
     * 判断网络是否可用
     *
     * @param context Context对象
     */
    public static Boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.isAvailable());
    }
}
