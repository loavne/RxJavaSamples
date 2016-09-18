package com.hlh.ibeauty.utils;

import android.content.Context;
import android.os.Environment;

/**
 * User: Hlh(tatian91@163.com)
 * Date: 2016-07-11
 * Time: 14:49
 */
public class FileUtil {

    /**
     * 获取缓存路径
     * @param context
     * @return
     */
    public static String getDiskCacheDir(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }
}
