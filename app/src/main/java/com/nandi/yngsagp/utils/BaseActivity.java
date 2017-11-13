package com.nandi.yngsagp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by qingsong on 2017/11/13.
 */

public class BaseActivity extends AppCompatActivity {

    protected Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * activity跳转
     * @param activity
     */
    public void ToNextActivity(Class<? extends Activity> activity) {
        Intent intent = new Intent(context, activity);
        startActivity(intent);
    }

    /**
     * activity跳转
     * @param activity 目标activity
     * @param bundle  绑定的值
     */
    public void ToNextBundle(Class<? extends Activity> activity,Bundle bundle){
        Intent intent = new Intent(this, activity);
        intent.putExtras(bundle);
        startActivity(intent);
    }



    /**
     * 提示框
     * @param text 提示内容
     * @param time 持续时间
     */
    public void showToast(CharSequence text,int time){
        Toast.makeText(context, text, time ).show();
    }

    /**
     * 提示框
     * @param text 提示内容
     */
    public void showToast(CharSequence text){
        showToast(text,Toast.LENGTH_SHORT);
    }

    /**
     * log日志
     * @param tag 过滤标示
     * @param text  内容
     */
    public void  showLog(String tag ,String text){
        Log.d(tag, text);
    }
    /**
     * 信息提示对话框
     * @param text 提示内容
     */
    public void alert(String title,String text){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(text).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }

}

