package com.nandi.yngsagp.activity;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
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

import com.nandi.yngsagp.R;
import com.nandi.yngsagp.fragment.DangerFragment;
import com.nandi.yngsagp.fragment.DisasterFragment;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
        tvTitle.setText("灾情直报");
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        DisasterFragment disasterFragment = new DisasterFragment();
        transaction.add(R.id.main_container, disasterFragment);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_disaster_edit:
                tvTitle.setText("灾情直报");
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                DisasterFragment disasterFragment = new DisasterFragment();
                transaction.replace(R.id.main_container, disasterFragment);
                transaction.commit();
                break;
            case R.id.nav_danger_edit:
                tvTitle.setText("险情速报");
                FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                DangerFragment dangerFragment = new DangerFragment();
                transaction1.replace(R.id.main_container, dangerFragment);
                transaction1.commit();
                break;
            case R.id.nav_disaster_handle:
                break;
            case R.id.nav_danger_handle:
                break;
            case R.id.nav_modify_password:
                break;
            case R.id.nav_clear:
                break;
            case R.id.nav_login_out:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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
