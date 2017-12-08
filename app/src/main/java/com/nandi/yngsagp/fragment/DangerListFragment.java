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
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.nandi.yngsagp.Constant;
import com.nandi.yngsagp.OkHttpCallback;
import com.nandi.yngsagp.R;
import com.nandi.yngsagp.activity.DangerPosActivity;
import com.nandi.yngsagp.adapter.DisposAdapter;
import com.nandi.yngsagp.bean.SuperBean;
import com.nandi.yngsagp.utils.AppUtils;
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

public class DangerListFragment extends Fragment {
    public static final int DANGER_REQUEST_CODE = 202;
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
    @BindView(R.id.tv_ay_error)
    ImageView tvAyError;
    @BindView(R.id.tv_no_error)
    ImageView tvNoError;
    private DisposAdapter dangerAdapter;
    private DisposAdapter dangerUAdapter;
    private int isDisPose = 0;//是否处理 0：未处理
    private int pageA = 1; //已处理页数
    private int pageU = 1;  //未处理页数
    private String areaId;
    private List<SuperBean> dangerListA;
    private List<SuperBean> dangerListU;
    private String role;
    private JSONObject jsonObject;
    private JSONObject jsonMeta;
    private JSONArray jsonData;
    private boolean isSuccess;
    private String message;
    private ProgressDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danger_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews();
        setListener();
        return view;
    }


    private void requestA(final RefreshLayout refreshlayouts) {
        String url = getString(R.string.local_base_url) + "dangerous/findDangers/" + areaId + "/" + isDisPose + "/2/1/15/" + role;
        OkHttpHelper.sendHttpGet(getActivity(), url, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                System.out.println("response = " + response);
                refreshlayouts.finishRefresh();
                try {
                    initJson(response);
                    if (isSuccess) {
                        dangerListA.clear();
                        dangerListA.addAll(JsonFormat.stringToList(jsonData.toString(), SuperBean.class));
                        dangerAdapter.notifyDataSetChanged();
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

            @Override
            public void onError(Exception error) {
                refreshlayouts.finishRefresh();
                ToastUtils.showShort("数据刷新失败...");
            }
        });

    }

    private void requestU(final RefreshLayout refreshlayouts) {
        String url = getString(R.string.local_base_url) + "dangerous/findDangers/" + areaId + "/" + isDisPose + "/2/1/15/" + role;
        OkHttpHelper.sendHttpGet(getActivity(), url, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    refreshlayouts.finishRefresh();
                    initJson(response);
                    if (isSuccess) {
                        dangerListU.clear();
                        dangerListU.addAll(JsonFormat.stringToList(jsonData.toString(), SuperBean.class));
                        dangerUAdapter.notifyDataSetChanged();
                        pageU = 1;
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

            @Override
            public void onError(Exception error) {
                refreshlayouts.finishRefresh();
                ToastUtils.showShort("数据刷新失败...");
            }
        });

    }

    private void initJson(String response) throws JSONException {
        jsonObject = new JSONObject(response);
        jsonMeta = new JSONObject(jsonObject.optString("meta"));
        isSuccess = jsonMeta.optBoolean("success");
        message = jsonMeta.optString("message");
        if (isSuccess) {
            jsonData = new JSONArray(jsonObject.optString("data"));
        }
    }


    private void loadMoreA(final RefreshLayout refreshlayouts) {
        pageA += 1;
        String url = getString(R.string.local_base_url) + "dangerous/findDangers/" + areaId + "/" + isDisPose + "/2/" + pageA + "/15/" + role;
        OkHttpHelper.sendHttpGet(getActivity(), url, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                System.out.println("response = " + response);
                refreshlayouts.finishLoadmore();
                try {
                    initJson(response);
                    if (isSuccess) {
                        if ("[]".equals(jsonData.toString())) {
                            ToastUtils.showShort("没有更多数据了");
                        }
                        dangerListA.addAll(JsonFormat.stringToList(jsonData.toString(), SuperBean.class));
                        dangerAdapter.notifyDataSetChanged();
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

            @Override
            public void onError(Exception error) {
                ToastUtils.showShort("加载失败，请重试");
                refreshlayouts.finishLoadmore();
            }
        });

    }

    private void loadMoreU(final RefreshLayout refreshlayouts) {
        pageU += 1;
        String loadUrl = getString(R.string.local_base_url) + "dangerous/findDangers/" + areaId + "/" + isDisPose + "/2/" + pageU + "/15/" + role;
        OkHttpHelper.sendHttpGet(getActivity(), loadUrl, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    refreshlayouts.finishLoadmore();
                    initJson(response);
                    if (isSuccess) {
                        if ("[]".equals(jsonData.toString())) {
                            ToastUtils.showShort("没有更多数据了");
                        }
                        dangerListU.addAll(JsonFormat.stringToList(jsonData.toString(), SuperBean.class));
                        dangerUAdapter.notifyDataSetChanged();
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

            @Override
            public void onError(Exception error) {
                ToastUtils.showShort("加载失败，请重试");
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
        dangerAdapter.setOnItemClickListener(new DisposAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), DangerPosActivity.class);
                intent.putExtra(Constant.DISASTER, dangerListA.get(position));
                startActivityForResult(intent, DANGER_REQUEST_CODE);
            }
        });
        dangerUAdapter.setOnItemClickListener(new DisposAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), DangerPosActivity.class);
                intent.putExtra(Constant.DISASTER, dangerListU.get(position));
                startActivityForResult(intent, DANGER_REQUEST_CODE);
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
    }

    private void initViews() {
        progressDialog = new ProgressDialog(getActivity(), ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("正在加载数据...");
        dangerListA = new ArrayList<>();
        dangerListU = new ArrayList<>();
        dangerShow.setLayoutManager(new LinearLayoutManager(getActivity()));
        dangerNShow.setLayoutManager(new LinearLayoutManager(getActivity()));
        dangerAdapter = new DisposAdapter(getActivity(), dangerListA);
        dangerUAdapter = new DisposAdapter(getActivity(), dangerListU);
        dangerShow.setAdapter(dangerAdapter);
        dangerNShow.setAdapter(dangerUAdapter);
        tabLayout.addTab(tabLayout.newTab().setText("未处理险情"), 0, true);
        tabLayout.addTab(tabLayout.newTab().setText("已处理险情"), 1);
        areaId = (String) SharedUtils.getShare(getActivity(), Constant.AREA_ID, "0");
        role = (String) SharedUtils.getShare(getActivity(), Constant.PERSON_TYPE, "2");
        requestAPos();
        requestUPos();
    }

    private void requestAPos() {
        progressDialog.show();
        String url = getString(R.string.local_base_url) + "dangerous/findDangers/" + areaId + "/1/2/1/15/" + role;
        OkHttpHelper.sendHttpGet(getActivity(), url, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    progressDialog.dismiss();
                    dangerListA.clear();
                    initJson(response);
                    if (isSuccess) {
                        tvAyError.setVisibility(View.GONE);
                        dangerListA.addAll(JsonFormat.stringToList(jsonData.toString(), SuperBean.class));
                        dangerAdapter.notifyDataSetChanged();

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

            @Override
            public void onError(Exception error) {
                tvAyError.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
            }
        });
    }

    private void requestUPos() {
        progressDialog.show();
        String url = getString(R.string.local_base_url) + "dangerous/findDangers/" + areaId + "/0/2/1/15/" + role;
        OkHttpHelper.sendHttpGet(getActivity(), url, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    progressDialog.dismiss();
                    dangerListU.clear();
                    initJson(response);
                    if (isSuccess) {
                        dangerListU.addAll(JsonFormat.stringToList(jsonData.toString(), SuperBean.class));
                        dangerUAdapter.notifyDataSetChanged();
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

            @Override
            public void onError(Exception error) {
                tvNoError.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == DANGER_REQUEST_CODE) {
            requestUPos();
            requestAPos();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
