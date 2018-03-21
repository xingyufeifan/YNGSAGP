package com.nandi.yngsagp.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class ScienceActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.scienceList)
    RecyclerView scienceList;
    ScienceAdapter scienceAdapter;
    List<ScienceBean>scienceBean =  new ArrayList<>();
    Context mContext;
    private String sessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_science);
        mContext = this;
        ButterKnife.bind(this);
        initAdapter();
        scienceRequest();
    }

    private void scienceRequest() {
        String url = getString(R.string.local_base_url) + "appDangerous/searchproPaganda";
        OkHttpUtils.get().url(url)
                .addHeader("sessionID", sessionId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("qs", "onResponse: "+response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonMeta = new JSONObject(jsonObject.optString("meta"));
                            boolean isSuccess = jsonMeta.optBoolean("success");
                            if (isSuccess) {
                              String  data = jsonObject.optString("data");
                                JSONArray jsonArray  = new JSONArray(data);
                                scienceBean.addAll(JsonFormat.stringToList(jsonArray.toString(), ScienceBean.class));
                                scienceAdapter.notifyDataSetChanged();
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

    private void initAdapter() {
        sessionId = (String) SharedUtils.getShare(mContext, Constant.SESSION_ID, "");
        scienceAdapter =  new ScienceAdapter(mContext,scienceBean);
        scienceList.setLayoutManager(new LinearLayoutManager(this));
        scienceList.setAdapter(scienceAdapter);
    }
}
