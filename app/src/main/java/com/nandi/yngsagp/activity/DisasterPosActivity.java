package com.nandi.yngsagp.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.nandi.yngsagp.Constant;
import com.nandi.yngsagp.R;
import com.nandi.yngsagp.bean.DisasterListABean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class DisasterPosActivity extends AppCompatActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.head_rl)
    RelativeLayout headRl;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.userShow)
    TextView userShow;
    @BindView(R.id.phoneShow)
    TextView phoneShow;
    @BindView(R.id.timeShow)
    TextView timeShow;
    @BindView(R.id.locationShow)
    TextView locationShow;
    @BindView(R.id.addressShow)
    EditText addressShow;
    @BindView(R.id.lonShow)
    EditText lonShow;
    @BindView(R.id.latShow)
    EditText latShow;
    @BindView(R.id.disNumShow)
    TextView disNumShow;
    @BindView(R.id.typeShow)
    Spinner typeShow;
    @BindView(R.id.factorShow)
    EditText factorShow;
    @BindView(R.id.injurdShow)
    EditText injurdShow;
    @BindView(R.id.deathShow)
    EditText deathShow;
    @BindView(R.id.missShow)
    EditText missShow;
    @BindView(R.id.farmShow)
    EditText farmShow;
    @BindView(R.id.houseShow)
    EditText houseShow;
    @BindView(R.id.moneyShow)
    EditText moneyShow;
    @BindView(R.id.mobileShow)
    EditText mobileShow;
    @BindView(R.id.nameShow)
    EditText nameShow;
    @BindView(R.id.ll_dReport)
    LinearLayout llDReport;
    @BindView(R.id.otherShow)
    EditText otherShow;
    @BindView(R.id.et_handle)
    EditText etHandle;
    @BindView(R.id.ll_handle)
    LinearLayout llHandle;
    @BindView(R.id.text_layout)
    LinearLayout textLayout;
    @BindView(R.id.rv_photo_uploaded)
    RecyclerView rvPhotoUploaded;
    @BindView(R.id.tv_video_uploaded)
    TextView tvVideoUploaded;
    @BindView(R.id.tv_audio_uploaded)
    TextView tvAudioUploaded;
    @BindView(R.id.rv_photo)
    RecyclerView rvPhoto;
    @BindView(R.id.tv_video)
    TextView tvVideo;
    @BindView(R.id.tv_audio)
    TextView tvAudio;
    @BindView(R.id.iv_take_photo)
    Button ivTakePhoto;
    @BindView(R.id.iv_take_video)
    Button ivTakeVideo;
    @BindView(R.id.iv_take_audio)
    Button ivTakeAudio;
    @BindView(R.id.ll_add_media)
    LinearLayout llAddMedia;
    @BindView(R.id.media_layout)
    LinearLayout mediaLayout;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_upload)
    Button btnUpload;
    @BindView(R.id.ll_1)
    LinearLayout ll1;
    private DisasterListABean disasterListBean;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster_pos);
        ButterKnife.bind(this);
        initData();
        initView();
        setListener();
    }

    private void initView() {
        progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("正在获取数据");
        tabLayout.addTab(tabLayout.newTab().setText("文本信息"), 0, true);
        tabLayout.addTab(tabLayout.newTab().setText("媒体信息"), 1);
        userShow.setText((CharSequence) disasterListBean.getPersonel());
        disNumShow.setText((CharSequence) disasterListBean.getDisasterNum());
        phoneShow.setText((CharSequence) disasterListBean.getPhoneNum());
        timeShow.setText((CharSequence) disasterListBean.getFindTime());
        locationShow.setText((CharSequence) disasterListBean.getCurrentLocation());
        addressShow.setText((CharSequence) disasterListBean.getAddress());
        typeShow.setSelection(Integer.parseInt(disasterListBean.getDisasterType()));
        factorShow.setText((CharSequence) disasterListBean.getFactor());
        injurdShow.setText((CharSequence) disasterListBean.getInjurdNum());
        deathShow.setText((CharSequence) disasterListBean.getDeathNum());
        missShow.setText((CharSequence) disasterListBean.getMissingNum());
        farmShow.setText((CharSequence) disasterListBean.getFarmland());
        houseShow.setText((CharSequence) disasterListBean.getHouseNum());
        moneyShow.setText((CharSequence) disasterListBean.getLossProperty());
        lonShow.setText((CharSequence) disasterListBean.getLongitude());
        latShow.setText((CharSequence) disasterListBean.getLatitude());
        otherShow.setText((CharSequence) disasterListBean.getOtherThing());
        mobileShow.setText((CharSequence) disasterListBean.getMonitorPhone());
        nameShow.setText((CharSequence) disasterListBean.getMonitorName());
        if ("0".equals((CharSequence) disasterListBean.getPersonType())) {
            llDReport.setVisibility(View.GONE);
        }
        int isDispose = disasterListBean.getIsDispose();
        System.out.println("isDisPose = " + isDispose);
        if (0 == isDispose) {
            tvTitle.setText("已处理灾情");
            ll1.setVisibility(View.GONE);
            llAddMedia.setVisibility(View.GONE);
        } else {
            tvTitle.setText("未处理灾情");
            llHandle.setVisibility(View.GONE);
        }
    }

    private void setListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    textLayout.setVisibility(View.VISIBLE);
                    mediaLayout.setVisibility(View.INVISIBLE);
                } else {
                    textLayout.setVisibility(View.INVISIBLE);
                    mediaLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void initData() {
        disasterListBean = (DisasterListABean) getIntent().getSerializableExtra(Constant.DISASTER);
        setRequest();
    }

    private void setRequest() {
        progressDialog.show();
        OkHttpUtils.get().url(getString(R.string.local_base_url)+"dangerous/findMedia/"+disasterListBean.getId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showShort("网络连接失败");
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject object=new JSONObject(response);
                            JSONArray data = object.getJSONArray("data");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @OnClick({R.id.iv_take_photo, R.id.iv_take_video, R.id.iv_take_audio, R.id.btn_save, R.id.btn_upload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_take_photo:
                break;
            case R.id.iv_take_video:
                break;
            case R.id.iv_take_audio:
                break;
            case R.id.btn_save:
                break;
            case R.id.btn_upload:
                break;
        }
    }
}
