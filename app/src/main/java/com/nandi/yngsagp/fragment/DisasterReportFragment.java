package com.nandi.yngsagp.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bigkoo.pickerview.TimePickerView;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.github.chrisbanes.photoview.PhotoView;
import com.nandi.yngsagp.Constant;
import com.nandi.yngsagp.R;
import com.nandi.yngsagp.activity.BaseActivity;
import com.nandi.yngsagp.adapter.PictureAdapter;
import com.nandi.yngsagp.bean.AudioPath;
import com.nandi.yngsagp.bean.DisasterUBean;
import com.nandi.yngsagp.bean.PhotoPath;
import com.nandi.yngsagp.bean.VideoPath;
import com.nandi.yngsagp.greendao.GreedDaoHelper;
import com.nandi.yngsagp.utils.PictureUtils;
import com.nandi.yngsagp.utils.SharedUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;


/**
 * @author  qingsong on 2017/11/15.
 */

public class DisasterReportFragment extends Fragment {

    private static final int PICK_PHOTO = 1;
    private static final int TAKE_PHOTO = 2;
    private static final int TAKE_VIDEO = 3;
    Unbinder unbinder;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.btn_upload)
    Button btnUpload;
    @BindView(R.id.iv_take_photo)
    Button ivTakePhoto;
    @BindView(R.id.iv_take_video)
    Button ivTakeVideo;
    @BindView(R.id.iv_take_audio)
    Button ivTakeAudio;
    @BindView(R.id.rv_photo)
    RecyclerView rvPhoto;
    @BindView(R.id.text_layout)
    LinearLayout textLayout;
    @BindView(R.id.media_layout)
    LinearLayout mediaLayout;
    @BindView(R.id.tv_video)
    TextView tvVideo;
    @BindView(R.id.tv_audio)
    TextView tvAudio;
    @BindView(R.id.dReportUser)
    TextView dReportUser;
    private Context context;
    private File pictureFile;
    private PopupWindow popupWindow;
    private List<PhotoPath> photoPaths = new ArrayList<>();
    private PictureAdapter pictureAdapter;
    @BindView(R.id.dReportPhone)
    TextView dReportPhone;
    @BindView(R.id.dReportTime)
    TextView dReportTime;
    @BindView(R.id.dReportAddress)
    EditText dReportAddress;
    @BindView(R.id.dReportLocation)
    TextView dReportLocation;
    @BindView(R.id.dReportType)
    Spinner dReportType;
    @BindView(R.id.dReportFactor)
    EditText dReportFactor;
    @BindView(R.id.dReportInjurd)
    EditText dReportInjurd;
    @BindView(R.id.dReportDeath)
    EditText dReportDeath;
    @BindView(R.id.dReportMiss)
    EditText dReportMiss;
    @BindView(R.id.dReportFram)
    EditText dReportFram;
    @BindView(R.id.dReportHouse)
    EditText dReportHouse;
    @BindView(R.id.dReportMoney)
    EditText dReportMoney;
    @BindView(R.id.dReportLon)
    TextView dReportLon;
    @BindView(R.id.dReportLat)
    TextView dReportLat;
    @BindView(R.id.dReportMobile)
    EditText dReportMobile;
    @BindView(R.id.dReportName)
    EditText dReportName;
    @BindView(R.id.ll_dReport)
    LinearLayout llDReport;
    @BindView(R.id.root)
    RelativeLayout root;
    @BindView(R.id.dReportOther)
    EditText dReportOther;
    @BindView(R.id.btn_save)
    Button btnSave;
    private File videoFile;
    private MediaRecorder recorder;
    private String audioPath;
    private RequestCall build;
    private ProgressDialog progressDialog;
    private int typePos;
    private LocationClient locationClient;
    private MediaPlayer player;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_disaster_report, container, false);
        unbinder = ButterKnife.bind(this, view);
        context = getActivity();
        initData();
        initViews();
        setAdapter();
        setListener();
        return view;
    }

    private void setAdapter() {
        pictureAdapter = new PictureAdapter(context, photoPaths);
        rvPhoto.setLayoutManager(new GridLayoutManager(context, 3));
        rvPhoto.setAdapter(pictureAdapter);
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
        pictureAdapter.setOnItemViewClickListener(new PictureAdapter.OnItemViewClickListener() {
            @Override
            public void onPictureClick(int position) {
                enlargePhoto(photoPaths.get(position).getPath());
            }

            @Override
            public void onDeleteClick(int position) {
                photoPaths.remove(position);
                pictureAdapter.notifyDataSetChanged();
            }
        });
        dReportType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typePos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println("parent = " + parent);
            }
        });
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                build.cancel();
            }
        });
    }

    private void enlargePhoto(String path) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_enlarge_photo, null);
        PhotoView photoView = view.findViewById(R.id.photo_view);
        photoView.setImageBitmap(ImageUtils.getBitmap(path, 1280, 720));
        new android.support.v7.app.AlertDialog.Builder(context, R.style.Transparent)
                .setView(view)
                .show();
    }

    private void initViews() {
        progressDialog = new ProgressDialog(context, ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("正在上传...");
        tabLayout.addTab(tabLayout.newTab().setText("文本信息"), 0, true);
        tabLayout.addTab(tabLayout.newTab().setText("媒体信息"), 1);
        if ("2".equals(SharedUtils.getShare(context, Constant.PERSON_TYPE, "0"))) {
            llDReport.setVisibility(View.VISIBLE);
        }
        dReportUser.setText((CharSequence) SharedUtils.getShare(context, Constant.NAME, ""));
        dReportPhone.setText((CharSequence) SharedUtils.getShare(context, Constant.MOBILE, ""));
        dReportLocation.setText((CharSequence) SharedUtils.getShare(context, Constant.ADDRESS, ""));
    }

    private void initData() {
        DisasterUBean disasterUBean = GreedDaoHelper.queryDisaster();
        List<PhotoPath> queryPhoto = GreedDaoHelper.queryPhoto(1);
        VideoPath queryVideo = GreedDaoHelper.queryVideo(1);
        AudioPath queryAudio = GreedDaoHelper.queryAudio(1);
        if (disasterUBean != null) {
            dReportUser.setText(disasterUBean.getReportMan());
            dReportPhone.setText(disasterUBean.getPhone());
            dReportTime.setText(disasterUBean.getTime());
            dReportAddress.setText(disasterUBean.getAddress());
            dReportLocation.setText(disasterUBean.getLocation());
            typePos = Integer.parseInt(disasterUBean.getType());
            dReportType.setSelection(Integer.parseInt(disasterUBean.getType()), true);
            dReportFactor.setText(disasterUBean.getFactor());
            dReportInjurd.setText(disasterUBean.getInjured());
            dReportDeath.setText(disasterUBean.getDeath());
            dReportMiss.setText(disasterUBean.getMiss());
            dReportFram.setText(disasterUBean.getFarm());
            dReportHouse.setText(disasterUBean.getHouse());
            dReportMoney.setText(disasterUBean.getMoney());
            dReportLon.setText(disasterUBean.getLon());
            dReportLat.setText(disasterUBean.getLat());
            dReportOther.setText(disasterUBean.getOther());
            dReportMobile.setText(disasterUBean.getReportMobile());
            dReportName.setText(disasterUBean.getReportName());
        } else {
            locationClient = new LocationClient(getActivity().getApplicationContext());
            LocationClientOption option = new LocationClientOption();
            option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
            option.setCoorType("gcj02");
            //可选，默认gcj02，设置返回的定位结果坐标系
            option.setScanSpan(1000 * 2);
            //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
            option.setOpenGps(true);
            //可选，默认false,设置是否使用gps
            locationClient.setLocOption(option);
            locationClient.registerLocationListener(new LocationListener());
            locationClient.start();
        }
        if (queryPhoto != null && queryPhoto.size() > 0) {
            photoPaths.clear();
            photoPaths.addAll(queryPhoto);
        }
        if (queryVideo != null && !TextUtils.isEmpty(queryVideo.getPath())) {
            videoFile = new File(queryVideo.getPath());
            tvVideo.setText(videoFile.getAbsolutePath());
        }
        if (queryAudio != null) {
            audioPath = queryAudio.getPath();
            tvAudio.setText(audioPath);
        }

    }

    private class LocationListener extends BDAbstractLocationListener {

        @SuppressLint("SetTextI18n")
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            int locType = bdLocation.getLocType();
            if (locType == BDLocation.TypeOffLineLocation || locType == BDLocation.TypeGpsLocation || locType == BDLocation.TypeNetWorkLocation) {
                double lon = bdLocation.getLongitude();
                double lat = bdLocation.getLatitude();
                dReportLon.setText(lon + "");
                dReportLat.setText(lat + "");
                locationClient.stop();
            }
        }

        @Override
        public void onLocDiagnosticMessage(int locType, int diagnosticType, String diagnosticMessage) {
            if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_BETTER_OPEN_GPS) {
                ToastUtils.showShort("请打开GPS");
            } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_BETTER_OPEN_WIFI) {
                ToastUtils.showShort("建议打开WIFI提高定位经度");
            }
        }
    }

    @OnClick({R.id.iv_take_photo, R.id.iv_take_video, R.id.iv_take_audio, R.id.btn_save, R.id.btn_upload, R.id.dReportTime, R.id.tv_video, R.id.tv_audio})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_take_photo:
                if (photoPaths.size() < 5) {
                    choosePhoto();
                } else {
                    ToastUtils.showShort("最多只能添加5张照片");
                }
                break;
            case R.id.iv_take_video:
                takeVideo();
                break;
            case R.id.iv_take_audio:
                takeAudio();
                break;
            case R.id.btn_save:
                save();
                break;
            case R.id.btn_upload:
                if (messageIsTrue()) {
                    upload();
                }

                break;
            case R.id.dReportTime:
                //时间选择器
                TimePickerView pvTime = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        dReportTime.setText(getTime(date));
                    }
                }).setSubmitText("确定")
                        .setCancelText("取消")
                        .build();
                //pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();
                break;
            case R.id.tv_video:
                if (!TextUtils.isEmpty(tvVideo.getText())) {
                    clickText(1);
                }
                break;
            case R.id.tv_audio:
                if (!TextUtils.isEmpty(tvAudio.getText())) {
                    clickText(2);
                }
                break;
        }
    }

    private boolean messageIsTrue() {
        if (TextUtils.isEmpty(dReportTime.getText())) {
            ToastUtils.showShort("请选择时间");
            return false;
        } else if (TextUtils.isEmpty(dReportAddress.getText())) {
            dReportAddress.setError("请填写地址");
            ToastUtils.showShort("请填写地址");
            return false;
        } else if (TextUtils.isEmpty(dReportLocation.getText())) {
            dReportLocation.setError("请填写地址");
            ToastUtils.showShort("请填写地址");
            return false;
        } else if (TextUtils.isEmpty(dReportMoney.getText())) {
            dReportMoney.setError("请填写损失财产");
            ToastUtils.showShort("请填写损失财产");
            return false;
        } else if (0 == typePos) {
            ToastUtils.showShort("请选择灾种类型");
            return false;
        }
        return true;
    }

    private void clickText(final int type) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context).inflate(R.layout.click_popup_view, null);
        TextView tvPlay = view.findViewById(R.id.tv_play);
        TextView tvDelete = view.findViewById(R.id.tv_delete);
        tvPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type == 1) {
                    Uri uri = Uri.parse(tvVideo.getText().toString());
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(uri, "video/mp4");
                    startActivity(intent);
                } else {
                    playAudio(tvAudio.getText().toString());
                }
                popupWindow.dismiss();
            }
        });
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type == 1) {
                    if (videoFile.exists()) {
                        videoFile.delete();
                    }
                    tvVideo.setText("");
                    videoFile = null;
                } else {
                    File file = new File(audioPath);
                    if (file.exists()) {
                        file.delete();
                    }
                    tvAudio.setText("");
                    audioPath = null;
                }
                popupWindow.dismiss();
            }
        });
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        View rootView = LayoutInflater.from(context).inflate(R.layout.fragment_disaster_report, null);
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                popupWindow.dismiss();
                return false;
            }
        });
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    private void save() {
        DisasterUBean disasterUBean = new DisasterUBean();
        String reportMan = dReportUser.getText().toString().trim();
        String phone = dReportPhone.getText().toString().trim();
        String time = dReportTime.getText().toString().trim();
        String address = dReportAddress.getText().toString().trim();
        String location = dReportLocation.getText().toString().trim();
        String type = typePos + "";
        String factor = dReportFactor.getText().toString().trim();
        String injured = dReportInjurd.getText().toString().trim();
        String death = dReportDeath.getText().toString().trim();
        String miss = dReportMiss.getText().toString().trim();
        String farm = dReportFram.getText().toString().trim();
        String house = dReportHouse.getText().toString().trim();
        String money = dReportMoney.getText().toString().trim();
        String lon = dReportLon.getText().toString().trim();
        String lat = dReportLat.getText().toString().trim();
        String other = dReportOther.getText().toString().trim();
        String reportName = dReportName.getText().toString().trim();
        String reportMobile = dReportMobile.getText().toString().trim();
        disasterUBean.setReportMan(reportMan);
        disasterUBean.setPhone(phone);
        disasterUBean.setTime(time);
        disasterUBean.setAddress(address);
        disasterUBean.setLocation(location);
        disasterUBean.setType(type);
        disasterUBean.setFactor(factor);
        disasterUBean.setInjured(injured);
        disasterUBean.setDeath(death);
        disasterUBean.setMiss(miss);
        disasterUBean.setFarm(farm);
        disasterUBean.setHouse(house);
        disasterUBean.setMoney(money);
        disasterUBean.setLon(lon);
        disasterUBean.setLat(lat);
        disasterUBean.setOther(other);
        disasterUBean.setReportName(reportName);
        disasterUBean.setReportMobile(reportMobile);
        disasterUBean.setId(1L);
        GreedDaoHelper.insertDisaster(disasterUBean);
        List<PhotoPath> queryPhoto = GreedDaoHelper.queryPhoto(1);
        if (queryPhoto != null && queryPhoto.size() > 0) {
            for (PhotoPath photoPath : queryPhoto) {
                GreedDaoHelper.deletePhoto(photoPath);
            }
        }
        for (PhotoPath photoPath : photoPaths) {
            GreedDaoHelper.insertPhoto(photoPath);
        }
        VideoPath videoPath = new VideoPath();
        videoPath.setId(1L);
        videoPath.setType(1);
        videoPath.setPath(tvVideo.getText().toString());
        GreedDaoHelper.insertVideo(videoPath);
        AudioPath audio = new AudioPath();
        audio.setId(1L);
        audio.setType(1);
        audio.setPath(tvAudio.getText().toString());
        GreedDaoHelper.insertAudio(audio);
        ToastUtils.showShort("保存成功");
    }

    private void upload() {
        Map<String, String> map = new HashMap<>();
        String reportMan = dReportUser.getText().toString().trim();
        String phone = dReportPhone.getText().toString().trim();
        String time = dReportTime.getText().toString().trim();
        String address = dReportAddress.getText().toString().trim();
        String location = dReportLocation.getText().toString().trim();
        String type = typePos + "";
        String factor = dReportFactor.getText().toString().trim();
        String injured = dReportInjurd.getText().toString().trim();
        String death = dReportDeath.getText().toString().trim();
        String miss = dReportMiss.getText().toString().trim();
        String farm = dReportFram.getText().toString().trim();
        String house = dReportHouse.getText().toString().trim();
        String money = dReportMoney.getText().toString().trim();
        String lon = dReportLon.getText().toString().trim();
        String lat = dReportLat.getText().toString().trim();
        String other = dReportOther.getText().toString().trim();
        String reportName = dReportName.getText().toString().trim();
        String reportMobile = dReportMobile.getText().toString().trim();
        String areaId = (String) SharedUtils.getShare(context, Constant.AREA_ID, "0");
        String personType = (String) SharedUtils.getShare(context, Constant.PERSON_TYPE, "0");
        map.put("phoneNum", phone);
        map.put("personel", reportMan);
        map.put("findTime", time);
        map.put("currentLocation", location);
        map.put("address", address);
        map.put("disasterType", type);
        map.put("factor", factor);
        map.put("injurdNum", injured);
        map.put("deathNum", death);
        map.put("missingNum", miss);
        map.put("farmland", farm);
        map.put("houseNum", house);
        map.put("lossProperty", money);
        map.put("longitude", lon);
        map.put("latitude", lat);
        map.put("otherThing", other);
        map.put("monitorName", reportName);
        map.put("monitorPhone", reportMobile);
        map.put("areaId", areaId);
        map.put("personType", personType);
        setRequest(map);
    }

    @SuppressLint("SimpleDateFormat")
    private void setRequest(Map<String, String> map) {
        progressDialog.show();
        PostFormBuilder formBuilder = OkHttpUtils.post().url(getString(R.string.local_base_url) + "dangerous/add");
        for (PhotoPath photoPath : photoPaths) {
            if (photoPath != null) {
                formBuilder.addFile("file", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg", new File(photoPath.getPath()));
                Log.d("cp", "图片添加");
            }
        }
        if (!TextUtils.isEmpty(tvVideo.getText().toString())) {
            Log.d("cp", "视频添加");
            formBuilder.addFile("file", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".mp4", new File(videoFile.getAbsolutePath()));
        }
        if (!TextUtils.isEmpty(tvAudio.getText().toString())) {
            Log.d("cp", "音频添加");
            formBuilder.addFile("file", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".mp3", new File(audioPath));
        }
        formBuilder.params(map);
        formBuilder.addParams("type", "1");
        build = formBuilder.build();
        build.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if ("Canceled".equals(e.getMessage())) {
                    ToastUtils.showShort("取消上传！");
                } else {
                    progressDialog.dismiss();
                    ToastUtils.showShort("网络连接失败！");
                    Log.d("cp", e.getMessage());
                }
            }

            @Override
            public void onResponse(String response, int id) {
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    JSONObject meta = object.getJSONObject("meta");
                    String message = object.getString("data");
                    boolean success = meta.getBoolean("success");
                    if (success) {
                        ToastUtils.showShort(message);
                        clean();
                    } else {
                        ToastUtils.showShort(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void clean() {
        dReportTime.setText("");
        dReportAddress.setText("");
        dReportLocation.setText("");
        typePos = 0;
        dReportType.setSelection(0);
        dReportFactor.setText("");
        dReportInjurd.setText("");
        dReportDeath.setText("");
        dReportMiss.setText("");
        dReportFram.setText("");
        dReportHouse.setText("");
        dReportMoney.setText("");
        dReportLon.setText("");
        dReportLat.setText("");
        dReportOther.setText("");
        dReportMobile.setText("");
        dReportName.setText("");
        tvAudio.setText("");
        tvVideo.setText("");
        photoPaths.clear();
        pictureAdapter.notifyDataSetChanged();
        List<PhotoPath> photoPaths = GreedDaoHelper.queryPhoto(1);
        if (photoPaths != null && photoPaths.size() > 0) {
            GreedDaoHelper.deletePhotoList(photoPaths);
            for (PhotoPath photoPath : photoPaths) {
                FileUtils.deleteFile(new File(photoPath.getPath()));
            }
        }
        VideoPath videoPath = GreedDaoHelper.queryVideo(1);
        if (videoPath != null) {
            GreedDaoHelper.deleteVideo(videoPath);
            FileUtils.deleteFile(new File(videoPath.getPath()));
        }
        AudioPath audioPath = GreedDaoHelper.queryAudio(1);
        if (audioPath != null) {
            GreedDaoHelper.deleteAudio(audioPath);
            FileUtils.deleteFile(new File(audioPath.getPath()));
        }
    }

    private void playAudio(String s) {
        player=new MediaPlayer();
        try {
            player.setDataSource(s);
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        View view=LayoutInflater.from(context).inflate(R.layout.diaolog_play_audio,null);
        Button btnStart = view.findViewById(R.id.btn_dialog_play);
        Button btnPause = view.findViewById(R.id.btn_dialog_pause);
        new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(false)
                .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        player.stop();
                    }
                }).show();
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.start();
            }
        });
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player.isPlaying()) {
                    player.pause();
                }
            }
        });

    }

    private void takeAudio() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_recoder, null);
        final Button btnStart = (Button) view.findViewById(R.id.btn_start_recode);
        final Button btnStop = (Button) view.findViewById(R.id.btn_stop_recode);
        btnStop.setEnabled(false);
        final TextView tv = (TextView) view.findViewById(R.id.tv_time);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setCancelable(false)
                .setView(view)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (TextUtils.isEmpty(audioPath)) {
                            return;
                        }
                        File file2 = new File(audioPath);
                        if (file2.isFile() && file2.exists()) {
                            file2.delete();
                        }
                        if (recorder != null) {
                            recorder.stop();
                            recorder.release();
                            recorder = null;
                        }
                    }
                }).show();
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File audio = createFileDir("Audio");
                if (audio != null) {
                    audioPath = audio.getPath() + "/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".mp3";
                }else {
                    ToastUtils.showShort("文件夹创建失败");
                }
                recorder = new MediaRecorder();
                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                recorder.setOutputFile(audioPath);
                //设置编码格式
                try {
                    recorder.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                    ToastUtils.showShort("录音机使用失败！");
                }
                recorder.start();
                tv.setVisibility(View.VISIBLE);
                btnStop.setEnabled(true);
                btnStart.setEnabled(false);
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recorder.stop();
                recorder.release();
                recorder = null;
                tvAudio.setText(audioPath);
                dialog.dismiss();
            }
        });
    }

    private void takeVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        videoFile = new File(createFileDir("Video"), new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".mp4");
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {  //针对Android7.0，需要通过FileProvider封装过的路径，提供给外部调用
            uri = FileProvider.getUriForFile(context, "com.nandi.yngsagp.fileprovider", videoFile);//通过FileProvider创建一个content类型的Uri，进行封装
        } else { //7.0以下，如果直接拿到相机返回的intent值，拿到的则是拍照的原图大小，很容易发生OOM，所以我们同样将返回的地址，保存到指定路径，返回到Activity时，去指定路径获取，压缩图片
            uri = Uri.fromFile(videoFile);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
        startActivityForResult(intent, TAKE_VIDEO);
    }

    private void choosePhoto() {
        View view = LayoutInflater.from(context).inflate(R.layout.popup_view, null);
        TextView tvTake = (TextView) view.findViewById(R.id.tv_take_photo);
        TextView tvChoose = (TextView) view.findViewById(R.id.tv_choose_photo);
        tvTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                takePhoto();
            }
        });
        tvChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_PHOTO);
            }
        });
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        View rootView = LayoutInflater.from(context).inflate(R.layout.fragment_disaster_report, null);
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                popupWindow.dismiss();
                return false;
            }
        });
    }

    private File createFileDir(String dir) {
        String path = Environment.getExternalStorageDirectory() + "/" + dir;
        boolean orExistsDir = FileUtils.createOrExistsDir(path);
        if (orExistsDir) {
            return new File(path);
        } else {
            return null;
        }
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        pictureFile = new File(createFileDir("Photo"), new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg");
        Uri imageUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {  //针对Android7.0，需要通过FileProvider封装过的路径，提供给外部调用
            imageUri = FileProvider.getUriForFile(context, "com.nandi.yngsagp.fileprovider", pictureFile);//通过FileProvider创建一个content类型的Uri，进行封装
        } else { //7.0以下，如果直接拿到相机返回的intent值，拿到的则是拍照的原图大小，很容易发生OOM，所以我们同样将返回的地址，保存到指定路径，返回到Activity时，去指定路径获取，压缩图片
            imageUri = Uri.fromFile(pictureFile);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        Log.d("cp", "图片保存路径：" + pictureFile.getAbsolutePath());
        startActivityForResult(intent, TAKE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            switch (requestCode) {
                case TAKE_PHOTO:
                    Bitmap bitmap = BitmapFactory.decodeFile(pictureFile.getAbsolutePath());
                    PictureUtils.compressImageToFile(bitmap, pictureFile);
                    PhotoPath pathBean = new PhotoPath();
                    pathBean.setPath(pictureFile.getAbsolutePath());
                    pathBean.setType(1);
                    photoPaths.add(pathBean);
                    pictureAdapter.notifyDataSetChanged();
                    break;
                case PICK_PHOTO:
                    Uri uri = data.getData();
                    if (uri != null) {
                        String[] proj = {MediaStore.Images.Media.DATA};
                        // 好像是android多媒体数据库的封装接口，具体的看Android文档
                        Cursor cursor = getActivity().managedQuery(uri, proj, null, null, null);
                        // 按我个人理解 这个是获得用户选择的图片的索引值
                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        // 将光标移至开头 ，这个很重要，不小心很容易引起越界
                        cursor.moveToFirst();
                        // 最后根据索引值获取图片路径
                        String path = cursor.getString(column_index);
                        System.out.println("照片路径：" + path);
                        Bitmap bitmap1 = BitmapFactory.decodeFile(new File(path).getAbsolutePath());
                        PictureUtils.compressImageToFile(bitmap1, new File(path));
                        PhotoPath photoPath = new PhotoPath();
                        photoPath.setPath(path);
                        photoPath.setType(1);
                        photoPaths.add(photoPath);
                        pictureAdapter.notifyDataSetChanged();
                    }
                    break;
                case TAKE_VIDEO:
                    tvVideo.setText(videoFile.getAbsolutePath());
                    break;
            }
        }
    }
}
