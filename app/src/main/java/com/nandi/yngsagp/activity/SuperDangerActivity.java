package com.nandi.yngsagp.activity;

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

import com.nandi.yngsagp.Constant;
import com.nandi.yngsagp.R;
import com.nandi.yngsagp.bean.SuperBean;
import com.nandi.yngsagp.utils.SharedUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SuperDangerActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.head_rl)
    RelativeLayout headRl;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
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
    @BindView(R.id.disposePerson)
    TextView disposePerson;
    @BindView(R.id.disposeMobile)
    TextView disposeMobile;
    @BindView(R.id.et_handle)
    TextView etHandle;
    @BindView(R.id.ll_handle)
    LinearLayout llHandle;
    @BindView(R.id.text_layout)
    LinearLayout textLayout;
    @BindView(R.id.iv_take_photo)
    Button ivTakePhoto;
    @BindView(R.id.iv_take_video)
    Button ivTakeVideo;
    @BindView(R.id.iv_take_audio)
    Button ivTakeAudio;
    @BindView(R.id.rv_photo)
    RecyclerView rvPhoto;
    @BindView(R.id.tv_video)
    TextView tvVideo;
    @BindView(R.id.tv_audio)
    TextView tvAudio;
    @BindView(R.id.media_layout)
    LinearLayout mediaLayout;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_upload)
    Button btnUpload;
    @BindView(R.id.ll_1)
    LinearLayout ll1;
    private SuperBean dangerListBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danger_super);
        ButterKnife.bind(this);

    }

}
