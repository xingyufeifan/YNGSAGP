package com.nandi.yngsagp.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.nandi.yngsagp.R;
import com.nandi.yngsagp.bean.DisasterListBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DisasterPosActivity extends AppCompatActivity {
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
    @BindView(R.id.disNumShow)
    TextView disNumShow;
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
    private DisasterListBean disasterListBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster_pos);
        ButterKnife.bind(this);
        initView();
        initData();

    }

    private void initView() {
        tabLayout.addTab(tabLayout.newTab().setText("文本信息"), 0, true);
        tabLayout.addTab(tabLayout.newTab().setText("媒体信息"), 1);
        ArrayList<DisasterListBean> list = (ArrayList<DisasterListBean>) getIntent().getSerializableExtra("list");
        int position = getIntent().getIntExtra("position", 0);
        int isDisPose = getIntent().getIntExtra("isDisPose", 0);
        System.out.println("isDisPose = " + isDisPose);
        if (0 == isDisPose) {
            tvTitle.setText("已处理灾情");
            ll1.setVisibility(View.GONE);
        }else{
            tvTitle.setText("未处理灾情");
        }
        disasterListBean = list.get(position);
        disasterListBean.getDisasterNum();
        initLisener();
    }

    private void initLisener() {
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

        userShow.setText((CharSequence)disasterListBean.getPersonel());
        disNumShow.setText((CharSequence)disasterListBean.getDisasterNum());
        phoneShow.setText((CharSequence)disasterListBean.getPhoneNum());
        timeShow.setText((CharSequence)disasterListBean.getFindTime());
        locationShow.setText((CharSequence)disasterListBean.getCurrentLocation());
        addressShow.setText((CharSequence)disasterListBean.getAddress());
        typeShow.setSelection(Integer.parseInt(disasterListBean.getDisasterType()));
        factorShow.setText((CharSequence) disasterListBean.getFactor());
        injurdShow.setText((CharSequence) disasterListBean.getInjurdNum());
        deathShow.setText((CharSequence) disasterListBean.getDeathNum());
        missShow.setText((CharSequence) disasterListBean.getMissingNum());
        farmShow.setText((CharSequence) disasterListBean.getFarmland());
        houseShow.setText((CharSequence) disasterListBean.getHouseNum());
        moneyShow.setText((CharSequence)disasterListBean.getLossProperty());
        lonShow.setText((CharSequence)disasterListBean.getLongitude());
        latShow.setText((CharSequence)disasterListBean.getLatitude());
        otherShow.setText((CharSequence) disasterListBean.getOtherThing());
        mobileShow.setText((CharSequence) disasterListBean.getMonitorPhone());
        nameShow.setText((CharSequence) disasterListBean.getMonitorName());
        if ("0".equals((CharSequence)disasterListBean.getPersonType())){
            llDReport.setVisibility(View.GONE);
        }
    }
}
