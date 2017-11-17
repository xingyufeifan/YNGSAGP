package com.nandi.yngsagp.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.nandi.yngsagp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by qingsong on 2017/11/15.
 */

public class DangerReportFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.userDanger)
    EditText userDanger;
    @BindView(R.id.phoneDanger)
    EditText phoneDanger;
    @BindView(R.id.timeDanger)
    TextView timeDanger;
    @BindView(R.id.addressDanger)
    EditText addressDanger;
    @BindView(R.id.locationDanger)
    EditText locationDanger;
    @BindView(R.id.lonDanger)
    EditText lonDanger;
    @BindView(R.id.latDanger)
    EditText latDanger;
    @BindView(R.id.typeDanger)
    EditText typeDanger;
    @BindView(R.id.factorDanger)
    EditText factorDanger;
    @BindView(R.id.personDanger)
    EditText personDanger;
    @BindView(R.id.houseDanger)
    EditText houseDanger;
    @BindView(R.id.moneyDanger)
    EditText moneyDanger;
    @BindView(R.id.areaDanger)
    EditText areaDanger;
    @BindView(R.id.otherDanger)
    EditText otherDanger;
    @BindView(R.id.text_layout)
    LinearLayout textLayout;
    @BindView(R.id.media_layout)
    LinearLayout mediaLayout;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_upload)
    Button btnUpload;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danger_report, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews();
        setListener();
        return view;
    }


    private void setListener() {
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

    private void initViews() {
        tabLayout.addTab(tabLayout.newTab().setText("文本信息"), 0, true);
        tabLayout.addTab(tabLayout.newTab().setText("媒体信息"), 1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
    @OnClick({R.id.timeDanger, R.id.btn_save, R.id.btn_upload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.timeDanger:
                //时间选择器
                TimePickerView pvTime = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        timeDanger.setText(getTime(date));
                    }
                }).setSubmitText("确定")
                        .setCancelText("取消")
                        .build();
                //pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();
                break;
            case R.id.btn_save:
                break;
            case R.id.btn_upload:
                break;
        }
    }
}
