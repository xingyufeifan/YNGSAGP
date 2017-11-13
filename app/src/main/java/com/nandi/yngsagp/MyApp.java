package com.nandi.yngsagp;


import android.app.Application;
import android.support.annotation.NonNull;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;


/**
 * Created by qingsong on 2017/6/30.
 */

public class MyApp extends Application {
    public static final String TAG = "Gdims";
    private static final int Time = 10000;

    @Override
    public void onCreate() {
        super.onCreate();
    }


}
