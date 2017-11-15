package com.nandi.yngsagp.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.nandi.yngsagp.adapter.DisasterPagerAdapter;
import com.nandi.yngsagp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DisasterListActivity extends BaseActivity {
    @BindView(R.id.disTab)
    TabLayout disTab;
    @BindView(R.id.disView)
    ViewPager disView;
    private ArrayList<String> titleList;
    private DisasterPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster_list);
        ButterKnife.bind(this);
        initData();
        adapter = new DisasterPagerAdapter(getSupportFragmentManager(), titleList);
        disView.setAdapter(adapter);
        disTab.setupWithViewPager(disView, true);

    }

    private void initData() {
        titleList = new ArrayList<String>();
        titleList.add("已处理");
        titleList.add("未处理");

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
