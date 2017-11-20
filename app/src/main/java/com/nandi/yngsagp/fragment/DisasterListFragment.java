package com.nandi.yngsagp.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.nandi.yngsagp.OkHttpCallback;
import com.nandi.yngsagp.R;
import com.nandi.yngsagp.adapter.DisasterAdapter;
import com.nandi.yngsagp.bean.DisasterListBean;
import com.nandi.yngsagp.utils.OkHttpHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by qingsong on 2017/11/15.
 */

public class DisasterListFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.disasterAlready)
    LinearLayout disasterAlready;
    @BindView(R.id.disaster_show)
    RecyclerView disasterShow;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private DisasterAdapter disasterAdapter;
    private int isDisaster = 1;
    private int isDisPose = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_disaster_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews();
        setListener();
        return view;
    }


    private void request() {
        String url = "http://192.168.10.195:8080/yncmd/dangerous/findDangers/13987786880/" + isDisPose + "/" + isDisaster;
        OkHttpHelper.sendHttpGet(getActivity(), url, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                System.out.println("response = " + response);
                Gson gson = new Gson();
                DisasterListBean monitorData = gson.fromJson(response, DisasterListBean.class);
                setAdapter(monitorData);
                refreshLayout.finishRefresh();
            }

            @Override
            public void onError(Exception error) {
                refreshLayout.finishRefresh();

            }
        });

    }

    private void requestFirst() {
        String url = "http://192.168.10.195:8080/yncmd/dangerous/findDangers/13987786880/" + isDisPose + "/" + isDisaster;
        OkHttpHelper.sendHttpGet(getActivity(), url, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                System.out.println("response = " + response);
                Gson gson = new Gson();
                DisasterListBean monitorData = gson.fromJson(response, DisasterListBean.class);
                setAdapter(monitorData);
            }

            @Override
            public void onError(Exception error) {

            }
        });

    }
    private void loadMore() {
        String url = "http://192.168.10.195:8080/yncmd/dangerous/findDangers/13987786880/" + isDisPose + "/" + isDisaster;
        OkHttpHelper.sendHttpGet(getActivity(), url, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                System.out.println("response = " + response);
                Gson gson = new Gson();
                DisasterListBean monitorData = gson.fromJson(response, DisasterListBean.class);
                disasterAdapter.notifyDataSetChanged();

                refreshLayout.finishLoadmore(true);
            }

            @Override
            public void onError(Exception error) {
                refreshLayout.finishLoadmore();
            }
        });

    }

    private void setAdapter(DisasterListBean disasterListBean) {
        disasterShow.setLayoutManager(new LinearLayoutManager(getActivity()));
        disasterAdapter = new DisasterAdapter(getActivity(), disasterListBean);
        disasterShow.setAdapter(disasterAdapter);

    }

    private void setListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                isDisPose = position;
                requestFirst();
                ToastUtils.showShort("我点了" + position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    private void initViews() {
        tabLayout.addTab(tabLayout.newTab().setText("已处理灾情"), 0, true);
        tabLayout.addTab(tabLayout.newTab().setText("未处理灾情"), 1);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                request();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
             loadMore();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
