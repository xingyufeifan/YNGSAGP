package com.nandi.yngsagp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nandi.yngsagp.utils.OkHttpHelper;
import com.zhy.http.okhttp.OkHttpUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OkHttpHelper.sendHttpGet(this, "http://gank.io/api/random/data/Android/20", new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                System.out.println(response);
            }

            @Override
            public void onError(Exception error) {

            }
        });
    }
}
