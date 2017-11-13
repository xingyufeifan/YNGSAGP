package com.nandi.yngsagp;

import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Created by qingsong on 2017/11/13.
 */

public interface OkHttpCallback {
    void onSuccess(String response);
    void onError(Exception error);
}
