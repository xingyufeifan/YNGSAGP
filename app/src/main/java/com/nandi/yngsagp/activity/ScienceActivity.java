package com.nandi.yngsagp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.nandi.yngsagp.Constant;
import com.nandi.yngsagp.R;
import com.nandi.yngsagp.adapter.ScienceAdapter;
import com.nandi.yngsagp.bean.ScienceBean;
import com.nandi.yngsagp.utils.AppUtils;
import com.nandi.yngsagp.utils.JsonFormat;
import com.nandi.yngsagp.utils.SharedUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * @author qingsong 科普宣传列表
 */

public class ScienceActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.scienceList)
    RecyclerView scienceList;
    ScienceAdapter scienceAdapter;
    ArrayList<ScienceBean> scienceBean;
    Context mContext;
    @BindView(R.id.refreshNLayout)
    SmartRefreshLayout refreshNLayout;
    @BindView(R.id.tv_no_error)
    ImageView tvNoError;
    private String sessionId;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_science);
        mContext = this;
        ButterKnife.bind(this);
        initData();
        scienceRequest(0);
    }

    private void initData() {
        progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("正在获取数据");
        scienceBean = new ArrayList<>();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sessionId = (String) SharedUtils.getShare(mContext, Constant.SESSION_ID, "");
        refreshNLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                scienceRequest(1);
            }
        });
        tvNoError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scienceRequest(0);
            }
        });

    }

    /**
     * 请求数据
     */
    private void scienceRequest(final int requestId) {
        if (requestId ==0){
            progressDialog.show();
        }
        String url = getString(R.string.local_base_url) + "appDangerous/searchproPaganda";
        OkHttpUtils.get().url(url)
                .addHeader("sessionID", sessionId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        tvNoError.setVisibility(View.VISIBLE);
                        if (requestId == 1) {
                            refreshNLayout.finishRefresh();
                            scienceList.setVisibility(View.GONE);
                        }else {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (requestId == 1) {
                            refreshNLayout.finishRefresh();
                        }else {
                            progressDialog.dismiss();
                        }
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonMeta = new JSONObject(jsonObject.optString("meta"));
                            boolean isSuccess = jsonMeta.optBoolean("success");
                            if (isSuccess) {
                                tvNoError.setVisibility(View.GONE);
                                String data = jsonObject.optString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                scienceBean.clear();
                                scienceBean.addAll(JsonFormat.stringToList(jsonArray.toString(), ScienceBean.class));
                                initAdapter();
                            } else {
                                String message = jsonMeta.optString("message");
//                                showToast(message);
                                if ("exit".equals(message)) {
                                    AppUtils.startLogin(ScienceActivity.this);
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

    /**
     * 设置页面初始化
     */
    private void initAdapter() {

        scienceAdapter = new ScienceAdapter(mContext, scienceBean);
        scienceList.setLayoutManager(new LinearLayoutManager(this));
        scienceList.setAdapter(scienceAdapter);
        scienceAdapter.setOnItemClickListener(new ScienceAdapter.OnItemViewClickListener() {
            @Override
            public void onScienceClick(int position) {
                if ("1".equals(scienceBean.get(position).getState())) {
                    scienceBean.get(position).setState("2");
                    scienceAdapter.notifyDataSetChanged();
                }
                Intent intent = new Intent(mContext, ScienceDataActivity.class);
                intent.putExtra("data", scienceBean.get(position));
                startActivity(intent);
            }
        });
    }
}
