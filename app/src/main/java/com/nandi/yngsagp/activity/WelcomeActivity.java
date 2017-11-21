package com.nandi.yngsagp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nandi.yngsagp.Constant;
import com.nandi.yngsagp.R;
import com.nandi.yngsagp.utils.SharedUtils;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        boolean isLogin = (boolean) SharedUtils.getShare(this, Constant.IS_LOGIN, false);
        if (isLogin){
            skipActivity(MainActivity.class);
        }else {
            skipActivity(LoginActivity.class);
        }
    }

    private void skipActivity(final Class<? extends Activity> activity) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this,activity));
                finish();
            }
        },2000);
    }
}
