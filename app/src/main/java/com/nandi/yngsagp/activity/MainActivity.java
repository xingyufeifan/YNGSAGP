package com.nandi.yngsagp.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.nandi.yngsagp.Constant;
import com.nandi.yngsagp.OkHttpCallback;
import com.nandi.yngsagp.R;
import com.nandi.yngsagp.fragment.DangerListFragment;
import com.nandi.yngsagp.fragment.DangerReportFragment;
import com.nandi.yngsagp.fragment.DisasterListFragment;
import com.nandi.yngsagp.fragment.DisasterReportFragment;
import com.nandi.yngsagp.fragment.SuperDangerFragment;
import com.nandi.yngsagp.fragment.SuperDisasterFragment;
import com.nandi.yngsagp.greendao.GreedDaoHelper;
import com.nandi.yngsagp.utils.AppUtils;
import com.nandi.yngsagp.utils.DownloadUtils;
import com.nandi.yngsagp.utils.OkHttpHelper;
import com.nandi.yngsagp.utils.SharedUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.my_toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.main_container)
    LinearLayout container;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private String mobile;
    private String address;
    private String type;
    private String name;
    private String areaId;
    private DisasterReportFragment disasterReportFragment;
    private DangerReportFragment dangerReportFragment;
    private DisasterListFragment disasterListFragment;
    private DangerListFragment dangerListFragment;
    private SuperDisasterFragment superDisasterFragment;
    private SuperDangerFragment superDangerFragment;
    private CloudPushService pushService;
    private String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initViews();
        checkUpdate();
        bindAccount();
    }

    private void bindAccount() {
        pushService = PushServiceFactory.getCloudPushService();
        pushService.turnOnPushChannel(new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                Log.d("cp", "开启推送通道成功");
            }

            @Override
            public void onFailed(String s, String s1) {
                Log.d("cp", "开启推送通道失败");
            }
        });
        if ("1".equals(type)){
            account = mobile;
        }else{
            account = areaId;
        }
        pushService.bindAccount(account, new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                Log.d("cp", "绑定账号成功");
            }

            @Override
            public void onFailed(String s, String s1) {
                Log.d("cp", "绑定账号失败");
            }
        });
    }

    private void initData() {
        mobile = (String) SharedUtils.getShare(context, Constant.MOBILE, "");
        address = (String) SharedUtils.getShare(context, Constant.ADDRESS, "");
        type = (String) SharedUtils.getShare(context, Constant.PERSON_TYPE, "");
        name = (String) SharedUtils.getShare(context, Constant.NAME, "");
        areaId= (String) SharedUtils.getShare(context,Constant.AREA_ID,"");
    }

    private void checkUpdate() {
        String url = "http://192.168.10.195:8080/yncmd/version/findNewVersionNumber/" + AppUtils.getVerCode(this);
        OkHttpHelper.sendHttpGet(context, url, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonMeta = new JSONObject(jsonObject.optString("meta"));
                    boolean isSuccess = jsonMeta.optBoolean("success");
                    if (isSuccess) {
                        String data = jsonObject.optString("data");
                        if ("200".equals(data)) {
                            showNoticeDialog();
                        } else if ("300".equals(data)) {
                            showToast("没得新版本");
                        }

                    } else {
                        String message = jsonMeta.optString("message");
                        showToast(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception error) {

            }
        });


    }

    /*下载APK*/
    private void showNoticeDialog() {
        Dialog dialog;
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("版本更新");
        builder.setMessage("");
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new DownloadUtils(context).downloadAPK("http://192.168.10.195:8080/yncmd/version/down", "app-release.apk");
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

    }

    private void initViews() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        View headerView = navView.getHeaderView(0);
        TextView tvName = headerView.findViewById(R.id.tv_name);
        TextView tvAccount = headerView.findViewById(R.id.tv_account);
        TextView tvAddress = headerView.findViewById(R.id.tv_address);
        TextView tvDuty = headerView.findViewById(R.id.tv_duty);
        tvName.setText(name);
        tvAccount.setText(mobile);
        tvAddress.setText(address);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
        disasterReportFragment = new DisasterReportFragment();
        dangerReportFragment = new DangerReportFragment();
        disasterListFragment = new DisasterListFragment();
        dangerListFragment = new DangerListFragment();
        superDisasterFragment = new SuperDisasterFragment();
        superDangerFragment = new SuperDangerFragment();
        if ("1".equals(type)) {
            tvTitle.setText("灾情直报");
            navView.getMenu().getItem(2).setChecked(true);
            navView.getMenu().getItem(0).setVisible(false);
            navView.getMenu().getItem(1).setVisible(false);
            navView.getMenu().getItem(4).setVisible(false);
            navView.getMenu().getItem(5).setVisible(false);
            addFragment(disasterReportFragment);
            addFragment(dangerReportFragment);
            tvDuty.setText("监测员");
        } else if ("2".equals(type)) {
            tvTitle.setText("灾情处置");
            navView.getMenu().getItem(0).setChecked(true);
            navView.getMenu().getItem(4).setVisible(false);
            navView.getMenu().getItem(5).setVisible(false);
            addFragment(disasterListFragment);
            addFragment(dangerListFragment);
            addFragment(disasterReportFragment);
            addFragment(dangerReportFragment);
            tvDuty.setText("审核员");
        } else if ("3".equals(type)) {
            tvTitle.setText("灾情数据");
            navView.getMenu().getItem(4).setChecked(true);
            navView.getMenu().getItem(0).setVisible(false);
            navView.getMenu().getItem(1).setVisible(false);
            navView.getMenu().getItem(2).setVisible(false);
            navView.getMenu().getItem(3).setVisible(false);
            addFragment(superDisasterFragment);
            addFragment(superDangerFragment);
            tvDuty.setText("县级");
        } else if ("4".equals(type)) {
            tvTitle.setText("灾情数据");
            navView.getMenu().getItem(4).setChecked(true);
            navView.getMenu().getItem(0).setVisible(false);
            navView.getMenu().getItem(1).setVisible(false);
            navView.getMenu().getItem(2).setVisible(false);
            navView.getMenu().getItem(3).setVisible(false);
            addFragment(superDisasterFragment);
            addFragment(superDangerFragment);
            tvDuty.setText("市级");
        } else if ("5".equals(type)) {
            tvTitle.setText("灾情数据");
            navView.getMenu().getItem(4).setChecked(true);
            navView.getMenu().getItem(0).setVisible(false);
            navView.getMenu().getItem(1).setVisible(false);
            navView.getMenu().getItem(2).setVisible(false);
            navView.getMenu().getItem(3).setVisible(false);
            addFragment(superDisasterFragment);
            addFragment(superDangerFragment);
            tvDuty.setText("省级");
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (id) {
            case R.id.nav_disaster_edit:
                if (!navView.getMenu().getItem(2).isChecked()) {
                    tvTitle.setText("灾情直报");
                    showFragment(disasterReportFragment);
                    hideFragment(dangerReportFragment);
                    hideFragment(disasterListFragment);
                    hideFragment(dangerListFragment);
                }
                break;
            case R.id.nav_danger_edit:
                if (!navView.getMenu().getItem(3).isChecked()) {
                    tvTitle.setText("险情速报");
                    hideFragment(disasterReportFragment);
                    showFragment(dangerReportFragment);
                    hideFragment(disasterListFragment);
                    hideFragment(dangerListFragment);
                }
                break;
            case R.id.nav_disaster_handle:
                if (!navView.getMenu().getItem(0).isChecked()) {
                    tvTitle.setText("灾情处置");
                    hideFragment(disasterReportFragment);
                    hideFragment(dangerReportFragment);
                    showFragment(disasterListFragment);
                    hideFragment(dangerListFragment);
                }
                break;
            case R.id.nav_danger_handle:
                if (!navView.getMenu().getItem(1).isChecked()) {
                    tvTitle.setText("险情处置");
                    hideFragment(disasterReportFragment);
                    hideFragment(dangerReportFragment);
                    hideFragment(disasterListFragment);
                    showFragment(dangerListFragment);
                }
                break;
            case R.id.nav_disaster_data:
                if (!navView.getMenu().getItem(4).isChecked()) {
                    tvTitle.setText("灾情数据");
                    hideFragment(superDangerFragment);
                    showFragment(superDisasterFragment);
                }
                break;
            case R.id.nav_danger_data:
                if (!navView.getMenu().getItem(5).isChecked()) {
                    tvTitle.setText("险情数据");
                    showFragment(superDangerFragment);
                    hideFragment(superDisasterFragment);
                }
                break;
            case R.id.nav_modify_password:
                startActivity(new Intent(context,ModifyActivity.class));
                break;
            case R.id.nav_clear:
                clear();
                break;
            case R.id.nav_login_out:
                loginOut();
                break;
        }
        return true;
    }

    private void clear() {
        new AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage("确定要清除所有的缓存数据吗？")
                .setPositiveButton("清除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FileUtils.deleteFilesInDir(Environment.getExternalStorageDirectory() + "/Photo");
                        FileUtils.deleteFilesInDir(Environment.getExternalStorageDirectory() + "/Video");
                        FileUtils.deleteFilesInDir(Environment.getExternalStorageDirectory() + "/Audio");
                        ToastUtils.showShort("清除成功");
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }

    private void addFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.main_container, fragment);
        transaction.commit();
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.show(fragment);
        transaction.commit();
    }

    private void hideFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.hide(fragment);
        transaction.commit();
    }

    private void loginOut() {
        new AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage("确定要注销登录吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        clean();
                        startActivity(new Intent(context, LoginActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }

    private void clean() {
        SharedUtils.removeShare(context, Constant.IS_LOGIN);
        SharedUtils.removeShare(context, Constant.PASSWORD);
        GreedDaoHelper.deleteAll();
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
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(context)
                    .setTitle("提示")
                    .setMessage("确定要退出程序吗？")
                    .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
        }
    }
}
