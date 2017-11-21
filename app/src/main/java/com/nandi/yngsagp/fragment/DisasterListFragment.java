package com.nandi.yngsagp.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.nandi.yngsagp.Constant;
import com.nandi.yngsagp.OkHttpCallback;
import com.nandi.yngsagp.R;
import com.nandi.yngsagp.activity.DisasterPosActivity;
import com.nandi.yngsagp.adapter.DisasterAdapter;
import com.nandi.yngsagp.bean.DisasterListBean;
import com.nandi.yngsagp.utils.JsonFormat;
import com.nandi.yngsagp.utils.OkHttpHelper;
import com.nandi.yngsagp.utils.SharedUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

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
    private int page = 1;
    private int rows = 15;
    private String areaId;
    private List<DisasterListBean> disasterList;


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
        page = 1;
        String url = "http://192.168.10.195:8080/yncmd/dangerous/findDangers/" + areaId + "/" + isDisPose + "/" + isDisaster + "/" + page + "/" + rows;
        OkHttpHelper.sendHttpGet(getActivity(), url, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                System.out.println("response = " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonMeta = new JSONObject(jsonObject.optString("meta"));
                    JSONArray jsonData = new JSONArray(jsonObject.optString("data"));
                    boolean isSuccess = jsonMeta.optBoolean("success");
                    String message = jsonMeta.optString("message");
                    if (isSuccess) {
                        disasterList = JsonFormat.stringToList(jsonData.toString(), DisasterListBean.class);
                        setAdapter();
                        refreshLayout.finishRefresh();
                    } else {
                        ToastUtils.showShort(message);
                        refreshLayout.finishRefresh();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception error) {
                refreshLayout.finishRefresh();

            }
        });

    }

    private void requestFirst() {
        String url = "http://192.168.10.195:8080/yncmd/dangerous/findDangers/" + areaId + "/" + isDisPose + "/" + isDisaster + "/" + page + "/" + rows;
        OkHttpHelper.sendHttpGet(getActivity(), url, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonMeta = new JSONObject(jsonObject.optString("meta"));
                    JSONArray jsonData = new JSONArray(jsonObject.optString("data"));
                    System.out.println("jsonData = " + jsonData.toString());
                    boolean isSuccess = jsonMeta.optBoolean("success");
                    String message = jsonMeta.optString("message");
                    if (isSuccess) {
                        disasterList = JsonFormat.stringToList(jsonData.toString(), DisasterListBean.class);
                        setAdapter();
                    } else {

                        ToastUtils.showShort(message);
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

    private void loadMore() {
        page += 1;
        String url = "http://192.168.10.195:8080/yncmd/dangerous/findDangers/" + areaId + "/" + isDisPose + "/" + isDisaster + "/" + page + "/" + rows;
        OkHttpHelper.sendHttpGet(getActivity(), url, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                System.out.println("response = " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonMeta = new JSONObject(jsonObject.optString("meta"));
                    JSONArray jsonData = new JSONArray(jsonObject.optString("data"));
                    System.out.println("jsonData = " + jsonData.toString());
                    boolean isSuccess = jsonMeta.optBoolean("success");
                    String message = jsonMeta.optString("message");
                    if (isSuccess) {
                        if ("[]".equals(jsonData.toString())){
                          ToastUtils.showShort("没有更多数据了");
                        }
                        disasterList.addAll(JsonFormat.stringToList(jsonData.toString(), DisasterListBean.class));
                        disasterAdapter.notifyDataSetChanged();
                        refreshLayout.finishLoadmore();
                    } else {
                        refreshLayout.finishLoadmore();
                        ToastUtils.showShort(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Exception error) {
                refreshLayout.finishLoadmore();
            }
        });

    }

    private void setAdapter() {
        disasterShow.setLayoutManager(new LinearLayoutManager(getActivity()));
        disasterAdapter = new DisasterAdapter(getActivity(), disasterList);
        disasterAdapter.setOnItemClickListener(new DisasterAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), DisasterPosActivity.class);
                intent.putExtra("list", (Serializable) disasterList);
                intent.putExtra("position", position);
                intent.putExtra("isDisPose", isDisPose);
                startActivity(intent);
            }
        });
        disasterShow.setAdapter(disasterAdapter);

    }

    private void setListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                if (position !=isDisPose){
                    page = 1;
                }
                isDisPose = position;
                requestFirst();
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
        areaId = (String) SharedUtils.getShare(getActivity(), Constant.AREA_ID, "0");
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
        requestFirst();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
