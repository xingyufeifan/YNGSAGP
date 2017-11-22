package com.nandi.yngsagp.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.nandi.yngsagp.Constant;
import com.nandi.yngsagp.OkHttpCallback;
import com.nandi.yngsagp.R;
import com.nandi.yngsagp.activity.DangerPosActivity;
import com.nandi.yngsagp.adapter.DangerAPosAdapter;
import com.nandi.yngsagp.adapter.DangerUPosAdapter;
import com.nandi.yngsagp.adapter.DisasterAPosAdapter;
import com.nandi.yngsagp.bean.DangerListABean;
import com.nandi.yngsagp.bean.DangerListUBean;
import com.nandi.yngsagp.utils.JsonFormat;
import com.nandi.yngsagp.utils.OkHttpHelper;
import com.nandi.yngsagp.utils.SharedUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

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

public class DangerListFragment extends Fragment {
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
    private DangerAPosAdapter dangerAdapter;
    private DangerUPosAdapter dangerUAdapter;
    private int isDisaster = 2;
    private int isDisPose = 0;
    private int page = 1;
    private int rows = 15;
    private String areaId;
    private List<DangerListABean> dangerListA;
    private List<DangerListUBean> dangerListU;
    private String role;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danger_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews();
        setListener();
        return view;
    }


    private void request(final RefreshLayout refreshlayouts) {
        page = 1;
        String url = "http://192.168.10.195:8080/yncmd/dangerous/findDangers/" + areaId + "/" + isDisPose + "/" + isDisaster + "/" + page + "/" + rows + "/" + role;
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
                        if ("1".equals(isDisPose)) {
                            System.out.println(isDisPose + "success");
                            dangerListU = JsonFormat.stringToList(jsonData.toString(), DangerListUBean.class);
                            dangerUAdapter = new DangerUPosAdapter(getActivity(), dangerListU);
                            dangerNShow.setAdapter(dangerUAdapter);
                        } else {
                            System.out.println(isDisPose + "success");
                            dangerListA = JsonFormat.stringToList(jsonData.toString(), DangerListABean.class);
                            dangerAdapter = new DangerAPosAdapter(getActivity(), dangerListA);
                            dangerShow.setAdapter(dangerAdapter);
                        }
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


    private void loadMore(final RefreshLayout refreshlayouts) {
        page += 1;
        String url = "http://192.168.10.195:8080/yncmd/dangerous/findDangers/" + areaId + "/" + isDisPose + "/" + isDisaster + "/" + page + "/" + rows + "/" + role;
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
                        if ("[]".equals(jsonData.toString())) {
                            ToastUtils.showShort("没有更多数据了");
                        }
                        if ("0".equals(isDisPose)) {
                            dangerListA.addAll(JsonFormat.stringToList(jsonData.toString(), DangerListABean.class));
                            dangerAdapter.notifyDataSetChanged();

                        } else {
                            dangerListU.addAll(JsonFormat.stringToList(jsonData.toString(), DangerListUBean.class));
                            dangerUAdapter.notifyDataSetChanged();
                        }
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

    private void setAdapter() {

        dangerAdapter.setOnItemClickListener(new DisasterAPosAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), DangerPosActivity.class);
                intent.putExtra("list", (Serializable) dangerListA);
                intent.putExtra("position", position);
                intent.putExtra("isDisPose", isDisPose);
                startActivity(intent);
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
                if (position != isDisPose) {
                    page = 1;
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
                loadMore(refreshlayout);
                System.out.println("refreshLayout");
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                request(refreshlayout);

            }
        });
        refreshNLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                loadMore(refreshlayout);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                request(refreshlayout);
            }
        });

    }

    private void initViews() {
        dangerShow.setLayoutManager(new LinearLayoutManager(getActivity()));
        dangerNShow.setLayoutManager(new LinearLayoutManager(getActivity()));
        tabLayout.addTab(tabLayout.newTab().setText("已处理险情"), 0, true);
        tabLayout.addTab(tabLayout.newTab().setText("未处理险情"), 1);
        areaId = (String) SharedUtils.getShare(getActivity(), Constant.AREA_ID, "0");
        role = (String) SharedUtils.getShare(getActivity(), Constant.PERSON_TYPE, "2");
        requestAPos();
        requestUPos();
    }

    private void requestAPos() {
        String url = "http://192.168.10.195:8080/yncmd/dangerous/findDangers/" + areaId + "/0/2" + "/" + page + "/" + rows + "/" + role;
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
                        Log.d("TAG", "onSuccess: ---------------------------------------");
                        dangerListA = JsonFormat.stringToList(jsonData.toString(), DangerListABean.class);
                        dangerAdapter = new DangerAPosAdapter(getActivity(), dangerListA);
                        dangerShow.setAdapter(dangerAdapter);
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
        String url = "http://192.168.10.195:8080/yncmd/dangerous/findDangers/" + areaId + "/1/2" + "/" + page + "/" + rows + "/" + role;
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
                        dangerListU = JsonFormat.stringToList(jsonData.toString(), DangerListUBean.class);
                        dangerUAdapter = new DangerUPosAdapter(getActivity(), dangerListU);
                        dangerNShow.setAdapter(dangerUAdapter);
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
