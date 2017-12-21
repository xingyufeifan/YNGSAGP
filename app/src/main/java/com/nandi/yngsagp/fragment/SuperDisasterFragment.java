package com.nandi.yngsagp.fragment;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.nandi.yngsagp.Constant;
import com.nandi.yngsagp.R;
import com.nandi.yngsagp.activity.SuperDisasterActivity;
import com.nandi.yngsagp.adapter.SuperAdapter;
import com.nandi.yngsagp.bean.SuperBean;
import com.nandi.yngsagp.utils.AppUtils;
import com.nandi.yngsagp.utils.JsonFormat;
import com.nandi.yngsagp.utils.SharedUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;


/**
 * @author qingsong on 2017/11/15.
 */

public class SuperDisasterFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.disasterAlready)
    LinearLayout disasterAlready;
    @BindView(R.id.disaster_show)
    RecyclerView disasterShow;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.disasterN_show)
    RecyclerView disasterNShow;
    @BindView(R.id.refreshNLayout)
    SmartRefreshLayout refreshNLayout;
    @BindView(R.id.disasterNo)
    LinearLayout disasterNo;
    @BindView(R.id.tv_ay_error)
    ImageView tvAyError;
    @BindView(R.id.tv_no_error)
    ImageView tvNoError;
    private SuperAdapter disasterAdapter;
    private SuperAdapter disasterNAdapter;

    private int isDisPose = 0;
    private int pageA = 1;
    private int pageN = 1;
    private int rows = 15;
    private String areaId;
    private List<SuperBean> disasterListA;
    private List<SuperBean> disasterListN;
    private String role;
    private JSONArray jsonData;
    private boolean isSuccess;
    private String message;
    private ProgressDialog progressDialog;
    private String sessionId;

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

        String url = getString(R.string.local_base_url) + "appDangerous/findDangers/" + areaId + "/" + isDisPose + "/1/1/15/" + role;
        OkHttpUtils.get().url(url)
                .addHeader("sessionID", sessionId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showShort("数据刷新失败...");
                        refreshlayouts.finishRefresh();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            refreshlayouts.finishRefresh();
                            initJson(response);
                            if (isSuccess) {
                                disasterListA.clear();
                                disasterListA.addAll(JsonFormat.stringToList(jsonData.toString(), SuperBean.class));
                                disasterAdapter.notifyDataSetChanged();
                                pageA = 1;
                            } else {
                                if ("exit".equals(message)) {
                                    AppUtils.startLogin(getActivity());
                                } else {
                                    ToastUtils.showShort(message);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void requestN(final RefreshLayout refreshlayouts) {

        String url = getString(R.string.local_base_url) + "appDangerous/findDangers/" + areaId + "/" + isDisPose + "/1/1/15/" + role;
        OkHttpUtils.get().url(url)
                .addHeader("sessionID", sessionId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        refreshlayouts.finishRefresh();
                        ToastUtils.showShort("数据刷新失败...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            refreshlayouts.finishRefresh();
                            initJson(response);
                            if (isSuccess) {
                                disasterListN.clear();
                                disasterListN.addAll(JsonFormat.stringToList(jsonData.toString(), SuperBean.class));
                                disasterNAdapter.notifyDataSetChanged();
                                pageN = 1;
                            } else {
                                if ("exit".equals(message)) {
                                    AppUtils.startLogin(getActivity());
                                } else {
                                    ToastUtils.showShort(message);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void initJson(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONObject jsonMeta = new JSONObject(jsonObject.optString("meta"));
        isSuccess = jsonMeta.optBoolean("success");
        message = jsonMeta.optString("message");
        if (isSuccess) {
            jsonData = new JSONArray(jsonObject.optString("data"));
        }
    }


    private void loadMoreA(final RefreshLayout refreshlayouts) {
        pageA += 1;
        String url = getString(R.string.local_base_url) + "appDangerous/findDangers/" + areaId + "/" + isDisPose + "/1/" + pageA + "/15/" + role;
        OkHttpUtils.get().url(url)
                .addHeader("sessionID", sessionId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        refreshlayouts.finishLoadmore();
                        ToastUtils.showShort("加载失败，请重试");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            refreshlayouts.finishLoadmore();
                            initJson(response);
                            if (isSuccess) {
                                if ("[]".equals(jsonData.toString())) {
                                    ToastUtils.showShort("没有更多数据了");
                                }
                                disasterListA.addAll(JsonFormat.stringToList(jsonData.toString(), SuperBean.class));
                                disasterAdapter.notifyDataSetChanged();
                            } else {
                                if ("exit".equals(message)) {
                                    AppUtils.startLogin(getActivity());
                                } else {
                                    ToastUtils.showShort(message);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void loadMoreN(final RefreshLayout refreshlayouts) {
        pageN += 1;
        String url = getString(R.string.local_base_url) + "appDangerous/findDangers/" + areaId + "/" + isDisPose + "/1/" + pageN + "/15/" + role;
        OkHttpUtils.get().url(url)
                .addHeader("sessionID", sessionId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        refreshlayouts.finishLoadmore();
                        ToastUtils.showShort("加载失败，请重试");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            refreshlayouts.finishLoadmore();
                            initJson(response);
                            if (isSuccess) {
                                if ("[]".equals(jsonData.toString())) {
                                    ToastUtils.showShort("没有更多数据了");
                                }
                                disasterListN.addAll(JsonFormat.stringToList(jsonData.toString(), SuperBean.class));
                                disasterNAdapter.notifyDataSetChanged();
                            } else {
                                if ("exit".equals(message)) {
                                    AppUtils.startLogin(getActivity());
                                } else {
                                    ToastUtils.showShort(message);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    private void setListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (0 == position) {

                    disasterAlready.setVisibility(View.INVISIBLE);
                    disasterNo.setVisibility(View.VISIBLE);
                } else {
                    disasterAlready.setVisibility(View.VISIBLE);
                    disasterNo.setVisibility(View.INVISIBLE);
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
        tvAyError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestAPos();
            }
        });
        tvNoError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestUPos();
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
                loadMoreN(refreshlayout);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                requestN(refreshlayout);
            }
        });
        disasterAdapter.setOnItemClickListener(new SuperAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), SuperDisasterActivity.class);
                intent.putExtra(Constant.DISASTER, disasterListA.get(position));
                startActivity(intent);
            }
        });
        disasterNAdapter.setOnItemClickListener(new SuperAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), SuperDisasterActivity.class);
                intent.putExtra(Constant.DISASTER, disasterListN.get(position));
                startActivity(intent);
            }
        });

    }

    private void initViews() {
        sessionId = (String) SharedUtils.getShare(getActivity(), Constant.SESSION_ID, "");
        progressDialog = new ProgressDialog(getActivity(), ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("正在加载数据...");
        disasterListA = new ArrayList<>();
        disasterListN = new ArrayList<>();
        disasterShow.setLayoutManager(new LinearLayoutManager(getActivity()));
        disasterNShow.setLayoutManager(new LinearLayoutManager(getActivity()));
        disasterNAdapter = new SuperAdapter(getActivity(), disasterListN);
        disasterAdapter = new SuperAdapter(getActivity(), disasterListA);
        disasterShow.setAdapter(disasterAdapter);
        disasterNShow.setAdapter(disasterNAdapter);
        tabLayout.addTab(tabLayout.newTab().setText("未处理灾情"), 0, true);
        tabLayout.addTab(tabLayout.newTab().setText("已处理灾情"), 1);
        role = (String) SharedUtils.getShare(getActivity(), Constant.PERSON_TYPE, "0");
        areaId = (String) SharedUtils.getShare(getActivity(), Constant.AREA_ID, "0");
        requestAPos();
        requestUPos();
    }

    private void requestAPos() {
        progressDialog.show();
        String url = getString(R.string.local_base_url) + "appDangerous/findDangers/" + areaId + "/1/1/1/15/" + role;
        OkHttpUtils.get().url(url)
                .addHeader("sessionID", sessionId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        tvAyError.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            progressDialog.dismiss();
                            disasterListA.clear();
                            initJson(response);
                            if (isSuccess) {
                                tvAyError.setVisibility(View.GONE);
                                disasterListA.addAll(JsonFormat.stringToList(jsonData.toString(), SuperBean.class));
                                disasterAdapter.notifyDataSetChanged();
                            } else {
                                if ("exit".equals(message)) {
                                    AppUtils.startLogin(getActivity());
                                } else {
                                    ToastUtils.showShort(message);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void requestUPos() {
        progressDialog.show();
        String url = getString(R.string.local_base_url) + "appDangerous/findDangers/" + areaId + "/0/1/1/15/" + role;
        OkHttpUtils.get().url(url)
                .addHeader("sessionID", sessionId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        tvNoError.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        disasterListN.clear();
                        try {
                            progressDialog.dismiss();
                            initJson(response);
                            if (isSuccess) {
                                disasterListN.addAll(JsonFormat.stringToList(jsonData.toString(), SuperBean.class));
                                disasterNAdapter.notifyDataSetChanged();
                                tvNoError.setVisibility(View.GONE);
                            } else {
                                if ("exit".equals(message)) {
                                    AppUtils.startLogin(getActivity());
                                } else {
                                    ToastUtils.showShort(message);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
