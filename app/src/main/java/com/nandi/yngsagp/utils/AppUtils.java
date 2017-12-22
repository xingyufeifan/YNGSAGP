package com.nandi.yngsagp.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.blankj.utilcode.util.ToastUtils;
import com.nandi.yngsagp.Constant;
import com.nandi.yngsagp.activity.LoginActivity;
import com.nandi.yngsagp.greendao.GreedDaoHelper;


/**
 * Created by qingsong on 2017/9/19.
 */

public class AppUtils {
    public static String getVerCode(Context context) {
        int versionNumber = -1;
        try {
            versionNumber = context.getPackageManager().getPackageInfo("com.nandi.yngsagp", 0).versionCode;
            System.out.println("当前版本" + versionNumber);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("version", e.getMessage());
        }
        return versionNumber + "";
    }

    public static void startLogin(Activity context) {
        ToastUtils.showShort("该账号已在其他地方登录");
        SharedUtils.removeShare(context, Constant.IS_LOGIN);
        SharedUtils.removeShare(context, Constant.PASSWORD);
        GreedDaoHelper.deleteAll();
        CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.unbindAccount(new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                Log.d("cp", "解绑账号成功");
            }

            @Override
            public void onFailed(String s, String s1) {
                Log.d("cp", "解绑账号失败");
            }
        });
        pushService.turnOffPushChannel(new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                Log.d("cp", "关闭推送通道成功");
            }

            @Override
            public void onFailed(String s, String s1) {
                Log.d("cp", "关闭推送通道失败");
            }
        });
        context.startActivity(new Intent(context, LoginActivity.class));
        context.finish();
    }
}
