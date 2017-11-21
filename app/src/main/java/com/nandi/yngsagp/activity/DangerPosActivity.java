package com.nandi.yngsagp.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.nandi.yngsagp.R;
import com.nandi.yngsagp.bean.DangerListBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DangerPosActivity extends AppCompatActivity {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.ll_1)
    LinearLayout ll1;
    @BindView(R.id.text_layout)
    LinearLayout textLayout;
    @BindView(R.id.media_layout)
    LinearLayout mediaLayout;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.head_rl)
    RelativeLayout headRl;
    @BindView(R.id.userDangerShow)
    TextView userDangerShow;
    @BindView(R.id.phoneDangerShow)
    TextView phoneDangerShow;
    @BindView(R.id.timeDangerShow)
    TextView timeDangerShow;
    @BindView(R.id.addressDangerShow)
    EditText addressDangerShow;
    @BindView(R.id.locationDangerShow)
    EditText locationDangerShow;
    @BindView(R.id.lonDangerShow)
    TextView lonDangerShow;
    @BindView(R.id.latDangerShow)
    TextView latDangerShow;
    @BindView(R.id.xqNumShow)
    EditText xqNumShow;
    @BindView(R.id.typeDangerShow)
    Spinner typeDangerShow;
    @BindView(R.id.factorDangerShow)
    EditText factorDangerShow;
    @BindView(R.id.personDangerShow)
    EditText personDangerShow;
    @BindView(R.id.houseDangerShow)
    EditText houseDangerShow;
    @BindView(R.id.moneyDangerShow)
    EditText moneyDangerShow;
    @BindView(R.id.areaDangerShow)
    EditText areaDangerShow;
    @BindView(R.id.dReportMobileShow)
    EditText dReportMobileShow;
    @BindView(R.id.dReportNameShow)
    EditText dReportNameShow;
    @BindView(R.id.ll_dReport)
    LinearLayout llDReport;
    @BindView(R.id.otherDangerShow)
    EditText otherDangerShow;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_upload)
    Button btnUpload;
    private DangerListBean dangerListBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danger_pos);
        ButterKnife.bind(this);
        initView();
        initData();

    }

    private void initView() {
        tabLayout.addTab(tabLayout.newTab().setText("文本信息"), 0, true);
        tabLayout.addTab(tabLayout.newTab().setText("媒体信息"), 1);
        ArrayList<DangerListBean> list = (ArrayList<DangerListBean>) getIntent().getSerializableExtra("list");
        int position = getIntent().getIntExtra("position", 0);
        int isDisPose = getIntent().getIntExtra("isDisPose", 0);
        System.out.println("isDisPose = " + isDisPose);
        if (0 == isDisPose) {
            tvTitle.setText("已处理险情");
            ll1.setVisibility(View.GONE);
        } else {
            tvTitle.setText("未处理险情");
        }
        dangerListBean = list.get(position);
        dangerListBean.getDisasterNum();
        initListener();
    }

    private void initListener() {
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
        xqNumShow.setText(dangerListBean.getDisasterNum());
        userDangerShow.setText(dangerListBean.getPersonel());
        phoneDangerShow.setText(dangerListBean.getPhoneNum());
        timeDangerShow.setText(dangerListBean.getHappenTime());
        locationDangerShow.setText(dangerListBean.getCurrentLocation());
        addressDangerShow.setText(dangerListBean.getAddress());
        typeDangerShow.setSelection(Integer.parseInt(dangerListBean.getDisasterType()));
        factorDangerShow.setText(dangerListBean.getFactor());
        personDangerShow.setText(dangerListBean.getPersonNum());
        houseDangerShow.setText(dangerListBean.getHouseNum());
        areaDangerShow.setText(dangerListBean.getArea());
        moneyDangerShow.setText(dangerListBean.getPotentialLoss());
        lonDangerShow.setText(dangerListBean.getLongitude());
        latDangerShow.setText(dangerListBean.getLatitude());
        otherDangerShow.setText(dangerListBean.getOtherThing());
        dReportMobileShow.setText(dangerListBean.getMonitorPhone());
        dReportNameShow.setText(dangerListBean.getMonitorName());
        if ("0".equals(dangerListBean.getPersonType())){
            llDReport.setVisibility(View.GONE);
        }

    }
}
