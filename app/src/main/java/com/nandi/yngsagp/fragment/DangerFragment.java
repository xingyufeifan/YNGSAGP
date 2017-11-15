package com.nandi.yngsagp.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nandi.yngsagp.R;
import com.nandi.yngsagp.adapter.DangerAdapter;
import com.nandi.yngsagp.adapter.DisasterAdapter;
import com.nandi.yngsagp.bean.DangerUBean;
import com.nandi.yngsagp.bean.DisasterUBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by qingsong on 2017/11/15.
 */

public class DangerFragment extends Fragment {

    @BindView(R.id.disaster_show)
    RecyclerView disasterShow;
    @BindView(R.id.disaster_refresh)
    SwipeRefreshLayout disasterRefresh;
    Unbinder unbinder;
    private RecyclerView.Adapter mAdapter;
    private List<DangerUBean> beanList;

    public static DangerFragment newInstance(String info) {
        Bundle args = new Bundle();
        DangerFragment fragment = new DangerFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_disaster, container, false);
        unbinder = ButterKnife.bind(this, view);
        initRecycler();
        refreshDisaster();
        return view;
    }

    private void initRecycler() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayout.VERTICAL);
        disasterShow.setLayoutManager(mLayoutManager);
        initData();
        mAdapter = new DangerAdapter(getContext(), beanList);
        disasterShow.setAdapter(mAdapter);
    }

    /**
     * 刷新当前界面
     */
    private void refreshDisaster() {
        disasterRefresh.setColorSchemeResources(R.color.colorPrimary);
        disasterRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRecycler();
                disasterRefresh.setRefreshing(false);
            }
        });
    }

    private void initData() {
        beanList = new ArrayList<>();
        beanList.add(new DangerUBean("滑坡", "合肥市蜀山区天柱路88号", "2017/7/16 14:12:23"));
        beanList.add(new DangerUBean("地陷", "南京市秦淮区贡院西街53号", "2017/8/1 9:23:00"));
        beanList.add(new DangerUBean("崩塌", "苏州市沧浪新城杨素路18号", "2017/9/12 7:10:23"));
        beanList.add(new DangerUBean("泥石流", "金华市双龙南街801号", "2017/9/20 15:02:23"));
        beanList.add(new DangerUBean("不稳定", "上饶市茶圣路169号", "2017/9/25 8:54:23"));
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
