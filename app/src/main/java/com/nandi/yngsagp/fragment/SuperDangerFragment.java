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
import com.nandi.yngsagp.Constant;
import com.nandi.yngsagp.OkHttpCallback;
import com.nandi.yngsagp.R;
import com.nandi.yngsagp.adapter.SuperDangerAdapter;
import com.nandi.yngsagp.adapter.SuperDisasterAdapter;
import com.nandi.yngsagp.bean.SuperDangerBean;
import com.nandi.yngsagp.bean.SuperDisasterBean;
import com.nandi.yngsagp.utils.JsonFormat;
import com.nandi.yngsagp.utils.OkHttpHelper;
import com.nandi.yngsagp.utils.SharedUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by qingsong on 2017/11/15.
 */

public class SuperDangerFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.disasterAlready)
    LinearLayout disasterAlready;
    @BindView(R.id.danger_show)
    RecyclerView dangerShow;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.dangerN_show)
    RecyclerView dangerNShow;
    @BindView(R.id.refreshNLayout)
    SmartRefreshLayout refreshNLayout;
    @BindView(R.id.disasterNo)
    LinearLayout disasterNo;
    private SuperDangerAdapter superAdapter;
    private SuperDangerAdapter superUAdapter;
    private int isDisaster = 2;
    private int isDisPose = 0;
    private int pageA = 1;
    private int pageU = 1;
    private int rows = 15;
    private String areaId;
    private List<SuperDangerBean> superListA;
    private List<SuperDangerBean> superListU;
    private String role;
    private JSONObject jsonObject;
    private JSONObject jsonMeta;
    private JSONArray jsonData;
    private boolean isSuccess;
    private String message;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.super_disaster_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews();
        setListener();
        return view;
    }


    private void requestA(final RefreshLayout refreshlayouts) {
        String url = "http://192.168.10.195:8080/yncmd/dangerous/findDangers/" + areaId + "/" + isDisPose + "/2/1/" + rows + "/" + role;
        OkHttpHelper.sendHttpGet(getActivity(), url, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                System.out.println("response = " + response);
                try {
                  initJson(response);
                    if (isSuccess) {
                        superListA.clear();
                        superListA.addAll(JsonFormat.stringToList(jsonData.toString(), SuperDangerBean.class));
                        superAdapter.notifyDataSetChanged();
                        pageA = 1;
                        refreshlayouts.finishRefresh();
                    } else {
                        ToastUtils.showShort(message);
                        refreshlayouts.finishRefresh();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception error) {
                refreshlayouts.finishRefresh();

            }
        });

    }

    private void requestU(final RefreshLayout refreshlayouts) {
        String url = "http://192.168.10.195:8080/yncmd/dangerous/findDangers/" + areaId + "/" + isDisPose + "/2/1/" + rows + "/" + role;
        OkHttpHelper.sendHttpGet(getActivity(), url, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    initJson(response);
                    if (isSuccess) {
                        superListU.clear();
                        superListU.addAll(JsonFormat.stringToList(jsonData.toString(), SuperDangerBean.class));
                        superUAdapter.notifyDataSetChanged();
                        pageU = 1;
                        refreshlayouts.finishRefresh();
                    } else {
                        ToastUtils.showShort(message);
                        refreshlayouts.finishRefresh();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception error) {
                refreshlayouts.finishRefresh();

            }
        });

    }

    private void initJson(String response) throws JSONException {
        jsonObject = new JSONObject(response);
        jsonMeta = new JSONObject(jsonObject.optString("meta"));
        jsonData = new JSONArray(jsonObject.optString("data"));
        isSuccess = jsonMeta.optBoolean("success");
        message = jsonMeta.optString("message");
    }


    private void loadMoreA(final RefreshLayout refreshlayouts) {
        pageA += 1;
        String url = "http://192.168.10.195:8080/yncmd/dangerous/findDangers/" + areaId + "/" + isDisPose + "/2/" + pageA + "/" + rows + "/" + role;
        OkHttpHelper.sendHttpGet(getActivity(), url, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                System.out.println("response = " + response);
                try {
                    initJson(response);
                    if (isSuccess) {
                        if ("[]".equals(jsonData.toString())) {
                            ToastUtils.showShort("没有更多数据了");
                        }
                        superListA.addAll(JsonFormat.stringToList(jsonData.toString(), SuperDangerBean.class));
                        superAdapter.notifyDataSetChanged();
                        refreshlayouts.finishLoadmore();
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
                refreshlayouts.finishLoadmore();
            }
        });

    }
    private void loadMoreU(final RefreshLayout refreshlayouts) {
        pageU += 1;
        String url = "http://192.168.10.195:8080/yncmd/dangerous/findDangers/" + areaId + "/" + isDisPose + "/2/" + pageU + "/" + rows + "/" + role;
        OkHttpHelper.sendHttpGet(getActivity(), url, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    initJson(response);
                    if (isSuccess) {
                        if ("[]".equals(jsonData.toString())) {
                            ToastUtils.showShort("没有更多数据了");
                        }
                        superListU.addAll(JsonFormat.stringToList(jsonData.toString(), SuperDangerBean.class));
                        superUAdapter.notifyDataSetChanged();
                        refreshlayouts.finishLoadmore();
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
                refreshlayouts.finishLoadmore();
            }
        });

    }


    private void setListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (0 == position) {
                    disasterAlready.setVisibility(View.VISIBLE);
                    disasterNo.setVisibility(View.GONE);
                } else {
                    disasterAlready.setVisibility(View.GONE);
                    disasterNo.setVisibility(View.VISIBLE);
                }
                isDisPose = position;
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                loadMoreA(refreshlayout);
                System.out.println("refreshLayout");
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                requestA(refreshlayout);

            }
        });
        refreshNLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                loadMoreU(refreshlayout);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                requestU(refreshlayout);
            }
        });

    }

    private void initViews() {
        superListA = new ArrayList<>();
        superListU = new ArrayList<>();
        dangerShow.setLayoutManager(new LinearLayoutManager(getActivity()));
        dangerNShow.setLayoutManager(new LinearLayoutManager(getActivity()));
        superAdapter = new SuperDangerAdapter(getActivity(), superListA);
        superUAdapter = new SuperDangerAdapter(getActivity(), superListU);
        dangerShow.setAdapter(superAdapter);
        dangerNShow.setAdapter(superUAdapter);
        tabLayout.addTab(tabLayout.newTab().setText("已处理险情"), 0, true);
        tabLayout.addTab(tabLayout.newTab().setText("未处理险情"), 1);
        areaId = (String) SharedUtils.getShare(getActivity(), Constant.AREA_ID, "0");
        role = (String) SharedUtils.getShare(getActivity(), Constant.PERSON_TYPE, "2");
        requestAPos();
        requestUPos();
    }

    private void requestAPos() {
        String url = "http://192.168.10.195:8080/yncmd/dangerous/findDangers/" + areaId + "/0/2/1/" + rows + "/" + role;
        OkHttpHelper.sendHttpGet(getActivity(), url, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                System.out.println(response);
                try {
                    initJson(response);
                    if (isSuccess) {
                        superListA.addAll(JsonFormat.stringToList(jsonData.toString(), SuperDangerBean.class));
                        superAdapter.notifyDataSetChanged();
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

    private void requestUPos() {
        String url = "http://192.168.10.195:8080/yncmd/dangerous/findDangers/" + areaId + "/1/2/1/" + rows + "/" + role;
        OkHttpHelper.sendHttpGet(getActivity(), url, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                   initJson(response);
                    if (isSuccess) {
                        superListU.addAll(JsonFormat.stringToList(jsonData.toString(), SuperDangerBean.class));
                        superUAdapter.notifyDataSetChanged();
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
