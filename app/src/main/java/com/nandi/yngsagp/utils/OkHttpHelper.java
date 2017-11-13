package com.nandi.yngsagp.utils;

import android.content.Context;

import com.nandi.yngsagp.OkHttpCallback;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by qingsong on 2017/11/13.
 */

public class OkHttpHelper {
    /**
     * 用于发送 Get 请求的封装方法
     *
     * @param context  Activity 的实例
     * @param url      请求的地址
     * @param callback 用于网络回调的接口
     */
    public static void sendHttpGet(Context context, String url, final OkHttpCallback callback) {
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                callback.onError(e);
            }

            @Override
            public void onResponse(String response, int id) {
                callback.onSuccess(response);
            }
        });

    }
}

