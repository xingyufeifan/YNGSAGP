package com.nandi.yngsagp.activity;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nandi.yngsagp.Constant;
import com.nandi.yngsagp.R;
import com.nandi.yngsagp.fragment.DangerListFragment;
import com.nandi.yngsagp.fragment.DangerReportFragment;
import com.nandi.yngsagp.fragment.DisasterListFragment;
import com.nandi.yngsagp.fragment.DisasterReportFragment;
import com.nandi.yngsagp.fragment.ModifyFragment;
import com.nandi.yngsagp.utils.SharedUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    private DisasterReportFragment disasterReportFragment;
    private DangerReportFragment dangerReportFragment;
    private DisasterListFragment disasterListFragment;
    private DangerListFragment dangerListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initViews();

    }

    private void initData() {
        mobile = (String) SharedUtils.getShare(context, Constant.MOBILE, "");
        address = (String) SharedUtils.getShare(context, Constant.ADDRESS, "");
        type = (String) SharedUtils.getShare(context, Constant.TYPE, "");
        name = (String) SharedUtils.getShare(context, Constant.NAME, "");
    }

    private void initViews() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        View headerView = navView.getHeaderView(0);
        TextView tvName = headerView.findViewById(R.id.tv_name);
        TextView tvAccount = headerView.findViewById(R.id.tv_account);
        TextView tvAddress = headerView.findViewById(R.id.tv_address);
        TextView tvDuty =  headerView.findViewById(R.id.tv_duty);
        tvName.setText(name);
        tvAccount.setText(mobile);
        tvAddress.setText(address);
        tvDuty.setText("0".equals(type) ? "监测员" : "审核员");
        if ("0".equals(type)) {
            navView.getMenu().getItem(2).setVisible(false);
            navView.getMenu().getItem(3).setVisible(false);
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
        navView.getMenu().getItem(0).setChecked(true);
        tvTitle.setText("灾情直报");
        disasterReportFragment = new DisasterReportFragment();
        dangerReportFragment = new DangerReportFragment();
        disasterListFragment = new DisasterListFragment();
        dangerListFragment = new DangerListFragment();

        addFragment(disasterReportFragment);
        addFragment(dangerReportFragment);
        addFragment(disasterListFragment);
        addFragment(dangerListFragment);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (id) {
            case R.id.nav_disaster_edit:
                if (!navView.getMenu().getItem(0).isChecked()) {
                    tvTitle.setText("灾情直报");
                    showFragment(disasterReportFragment);
                    hideFragment(dangerReportFragment);
                    hideFragment(disasterListFragment);
                    hideFragment(dangerListFragment);

                }
                break;
            case R.id.nav_danger_edit:
                if (!navView.getMenu().getItem(1).isChecked()) {
                    tvTitle.setText("险情速报");
                    hideFragment(disasterReportFragment);
                    showFragment(dangerReportFragment);
                    hideFragment(disasterListFragment);
                    hideFragment(dangerListFragment);
                }
                break;
            case R.id.nav_disaster_handle:
                if (!navView.getMenu().getItem(2).isChecked()) {
                    tvTitle.setText("灾情处置");
                    hideFragment(disasterReportFragment);
                    hideFragment(dangerReportFragment);
                    showFragment(disasterListFragment);
                    hideFragment(dangerListFragment);
                }
                break;
            case R.id.nav_danger_handle:
                if (!navView.getMenu().getItem(3).isChecked()) {
                    tvTitle.setText("险情处置");
                    hideFragment(disasterReportFragment);
                    hideFragment(dangerReportFragment);
                    hideFragment(disasterListFragment);
                    showFragment(dangerListFragment);
                }
                break;
            case R.id.nav_modify_password:
                ToNextActivity(ModifyActivity.class);
                break;
            case R.id.nav_clear:
                break;
            case R.id.nav_login_out:
                loginOut();
                break;
        }
        return true;
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
                        // TODO: 2017/11/16 清空数据
                        SharedUtils.removeShare(context,Constant.IS_LOGIN );
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
