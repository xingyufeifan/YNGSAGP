package com.nandi.yngsagp.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
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
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.nandi.yngsagp.Constant;
import com.nandi.yngsagp.R;
import com.nandi.yngsagp.adapter.MediaAdapter;
import com.nandi.yngsagp.adapter.PhotoAdapter;
import com.nandi.yngsagp.adapter.PictureAdapter;
import com.nandi.yngsagp.bean.DisasterPoint;
import com.nandi.yngsagp.bean.MediaInfo;
import com.nandi.yngsagp.bean.PhotoPath;
import com.nandi.yngsagp.bean.SuperBean;
import com.nandi.yngsagp.fragment.DisasterListFragment;
import com.nandi.yngsagp.greendao.GreedDaoHelper;
import com.nandi.yngsagp.utils.AppUtils;
import com.nandi.yngsagp.utils.GPSUtils;
import com.nandi.yngsagp.utils.InputUtil;
import com.nandi.yngsagp.utils.PictureUtils;
import com.nandi.yngsagp.utils.SharedUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class DisasterPosActivity extends AppCompatActivity {
    private static final int PICK_PHOTO = 1;
    private static final int TAKE_PHOTO = 2;
    private static final int TAKE_VIDEO = 3;
    private static final int FILE_SELECT_CODE = 4;
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
    TextView lonShow;
    @BindView(R.id.latShow)
    TextView latShow;
    @BindView(R.id.disNumShow)
    TextView disNumShow;
    @BindView(R.id.typeShow)
    Spinner typeShow;
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
    @BindView(R.id.disposePerson)
    TextView disposePerson;
    @BindView(R.id.disposeMobile)
    TextView disposeMobile;
    @BindView(R.id.et_handle)
    EditText etHandle;
    @BindView(R.id.ll_handle)
    LinearLayout llHandle;
    @BindView(R.id.text_layout)
    LinearLayout textLayout;
    @BindView(R.id.rv_photo_uploaded)
    RecyclerView rvPhotoUploaded;
    @BindView(R.id.rv_photo)
    RecyclerView rvPhoto;
    @BindView(R.id.tv_video)
    TextView tvVideo;
    @BindView(R.id.tv_audio)
    TextView tvAudio;
    @BindView(R.id.iv_take_photo)
    ImageView ivTakePhoto;
    @BindView(R.id.iv_take_video)
    ImageView ivTakeVideo;
    @BindView(R.id.iv_take_audio)
    ImageView ivTakeAudio;
    @BindView(R.id.ll_add_media)
    LinearLayout llAddMedia;
    @BindView(R.id.media_layout)
    LinearLayout mediaLayout;
    @BindView(R.id.btn_error)
    Button btnError;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.ll_1)
    LinearLayout ll1;
    @BindView(R.id.rv_video_updated)
    RecyclerView rvVideoUpdated;
    @BindView(R.id.rv_audio_updated)
    RecyclerView rvAudioUpdated;
    @BindView(R.id.pos_all_layout)
    LinearLayout posAllLayout;
    @BindView(R.id.sp_factor_type)
    Spinner spFactorType;
    @BindView(R.id.rv_file_updated)
    RecyclerView rvFileUpdated;
    @BindView(R.id.iv_check_file)
    ImageView ivCheckFile;
    @BindView(R.id.tv_file)
    TextView tvFile;
    @BindView(R.id.sp_disaster_point)
    Spinner spDisasterPoint;

    private SuperBean listBean;
    private ProgressDialog progressDialog;
    private List<MediaInfo> photoInfos = new ArrayList<>();
    private List<MediaInfo> videoInfos = new ArrayList<>();
    private List<MediaInfo> audioInfos = new ArrayList<>();
    private List<MediaInfo> fileInfos = new ArrayList<>();
    private List<PhotoPath> photoPaths = new ArrayList<>();
    private Activity context;
    private PhotoAdapter photoAdapter;
    private MediaAdapter videoAdapter;
    private MediaAdapter audioAdapter;
    private MediaAdapter fileAdapter;
    private PictureAdapter pictureAdapter;
    private File pictureFile;
    private File videoFile;
    private PopupWindow popupWindow;
    private String audioPath;
    private MediaRecorder recorder;
    private MediaPlayer player;
    private RequestCall build;
    private int typePos;
    private String sessionId;
    private List<String> disasterName = new ArrayList<>();
    private int currentDisasterPosition;
    private List<DisasterPoint> disasterPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster_pos);
        ButterKnife.bind(this);
        context = this;
        initData();
        initView();
        setListener();
    }

    private void initData() {
        sessionId = (String) SharedUtils.getShare(context, Constant.SESSION_ID, "");
        progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("正在获取数据");
        listBean = (SuperBean) getIntent().getSerializableExtra(Constant.DISASTER);
        disasterPoints = GreedDaoHelper.getDisasterPointList();
        for (int i = 0; i < disasterPoints.size(); i++) {
            disasterName.add(disasterPoints.get(i).getDisName());
            if (listBean.getDisastersName().equals(disasterPoints.get(i).getDisName())) {
                currentDisasterPosition = i;
            }

        }
        Log.d("chenpeng","disaster:"+disasterPoints.toString());
        setRequest();
    }

    private void initView() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, disasterName);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDisasterPoint.setAdapter(arrayAdapter);
        spDisasterPoint.setSelection(currentDisasterPosition);
        photoAdapter = new PhotoAdapter(context, photoInfos);
        videoAdapter = new MediaAdapter(context, videoInfos);
        audioAdapter = new MediaAdapter(context, audioInfos);
        fileAdapter = new MediaAdapter(context, fileInfos);
        rvPhotoUploaded.setLayoutManager(new GridLayoutManager(context, 3));
        rvVideoUpdated.setLayoutManager(new LinearLayoutManager(context));
        rvAudioUpdated.setLayoutManager(new LinearLayoutManager(context));
        rvFileUpdated.setLayoutManager(new LinearLayoutManager(context));
        rvPhotoUploaded.setAdapter(photoAdapter);
        rvVideoUpdated.setAdapter(videoAdapter);
        rvAudioUpdated.setAdapter(audioAdapter);
        rvFileUpdated.setAdapter(fileAdapter);
        pictureAdapter = new PictureAdapter(context, photoPaths);
        rvPhoto.setLayoutManager(new GridLayoutManager(context, 3));
        rvPhoto.setAdapter(pictureAdapter);
        tabLayout.addTab(tabLayout.newTab().setText("文本信息"), 0, true);
        tabLayout.addTab(tabLayout.newTab().setText("媒体信息"), 1);
        userShow.setText(listBean.getPersonel());
        disNumShow.setText(listBean.getDisasterNum());
        phoneShow.setText(listBean.getPhoneNum());
        timeShow.setText(listBean.getFindTime());
        locationShow.setText(listBean.getCurrentLocation());
        addressShow.setText(listBean.getAddress());
        typeShow.setSelection(Integer.parseInt(listBean.getDisasterType()) + 1);

        spFactorType.setSelection(Integer.parseInt((String) listBean.getFactor()));
        injurdShow.setText((CharSequence) listBean.getInjurdNum());
        deathShow.setText((CharSequence) listBean.getDeathNum());
        missShow.setText((CharSequence) listBean.getMissingNum());
        farmShow.setText((CharSequence) listBean.getFarmland());
        houseShow.setText((CharSequence) listBean.getHouseNum());
        moneyShow.setText(listBean.getLossProperty());
        lonShow.setText(GPSUtils.gpsInfoConvert(Double.parseDouble(listBean.getLongitude())));
        latShow.setText(GPSUtils.gpsInfoConvert(Double.parseDouble(listBean.getLatitude())));
        otherShow.setText((CharSequence) listBean.getOtherThing());
        mobileShow.setText((CharSequence) listBean.getMonitorPhone());
        nameShow.setText((CharSequence) listBean.getMonitorName());
        if ("1".equals(listBean.getPersonType())) {
            llDReport.setVisibility(View.GONE);
        }
        int isDispose = listBean.getIsDispose();
        if (0 == isDispose) {
            tvTitle.setText("未处理灾情");
            disposePerson.setText((CharSequence) SharedUtils.getShare(context, Constant.NAME, ""));
            disposeMobile.setText((CharSequence) SharedUtils.getShare(context, Constant.MOBILE, ""));
        } else {
            tvTitle.setText("已处理灾情");
            etHandle.setText(listBean.getOpinion());
            disposePerson.setText(listBean.getDisposePerson());
            disposeMobile.setText(listBean.getDisposeMobile());
            ll1.setVisibility(View.GONE);
            llAddMedia.setVisibility(View.GONE);
            addressShow.setEnabled(false);
            typeShow.setEnabled(false);
            spFactorType.setEnabled(false);
            spDisasterPoint.setEnabled(false);
            injurdShow.setEnabled(false);
            deathShow.setEnabled(false);
            missShow.setEnabled(false);
            farmShow.setEnabled(false);
            houseShow.setEnabled(false);
            moneyShow.setEnabled(false);
            otherShow.setEnabled(false);
            etHandle.setEnabled(false);
            mobileShow.setEnabled(false);
            nameShow.setEnabled(false);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (InputUtil.isShouldHideInput(v, ev)) {
                InputUtil.hideSoftInput(v.getWindowToken(), context);
            }
        }
        return super.dispatchTouchEvent(ev);
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
        photoAdapter.setOnItemViewClickListener(new PhotoAdapter.OnItemViewClickListener() {
            @Override
            public void onPictureClick(int position) {
                enlargePhoto(photoInfos.get(position));
            }
        });
        videoAdapter.setOnItemClickListener(new MediaAdapter.OnItemViewClickListener() {
            @Override
            public void onMediaClick(int position) {
                playNetVideo(videoInfos.get(position));
            }
        });
        audioAdapter.setOnItemClickListener(new MediaAdapter.OnItemViewClickListener() {
            @Override
            public void onMediaClick(int position) {
                playNetAudio(audioInfos.get(position));
            }
        });
        fileAdapter.setOnItemClickListener(new MediaAdapter.OnItemViewClickListener() {
            @Override
            public void onMediaClick(int position) {
                downloadFile(fileInfos.get(position));
            }
        });
        pictureAdapter.setOnItemViewClickListener(new PictureAdapter.OnItemViewClickListener() {
            @Override
            public void onPictureClick(int position) {
                enlargePicture(photoPaths.get(position).getPath());
            }

            @Override
            public void onDeleteClick(int position) {
                photoPaths.remove(position);
                pictureAdapter.notifyDataSetChanged();
            }
        });
        typeShow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        posAllLayout.setOnClickListener(null);
        etHandle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //触摸的是EditText并且当前EditText可以滚动则将事件交给EditText处理；否则将事件交由其父类处理
                if ((v.getId() == R.id.et_handle && canVerticalScroll(etHandle))) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
                return false;
            }
        });
        otherShow.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //触摸的是EditText并且当前EditText可以滚动则将事件交给EditText处理；否则将事件交由其父类处理
                if ((v.getId() == R.id.otherShow && canVerticalScroll(otherShow))) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
                return false;
            }
        });
    }


    /**
     * EditText竖直方向是否可以滚动
     *
     * @param editText 需要判断的EditText
     * @return true：可以滚动  false：不可以滚动
     */
    private boolean canVerticalScroll(EditText editText) {
        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() - editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        if (scrollDifference == 0) {
            return false;
        }

        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }

    private void enlargePicture(String path) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_enlarge_photo, null);
        PhotoView photoView = view.findViewById(R.id.photo_view);
        photoView.setImageBitmap(ImageUtils.getBitmap(path, 1280, 720));
        new android.support.v7.app.AlertDialog.Builder(context, R.style.Transparent)
                .setView(view)
                .show();
    }

    private void enlargePhoto(MediaInfo mediaInfo) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_enlarge_photo, null);
        PhotoView photoView = view.findViewById(R.id.photo_view);
        Glide.with(context).load(getString(R.string.local_base_url) + "dangerous/download/" + mediaInfo.getFileName() + "/" + mediaInfo.getType())
                .placeholder(R.drawable.downloading).error(R.drawable.download_pass).into(photoView);
        new AlertDialog.Builder(context, R.style.Transparent)
                .setView(view)
                .show();
    }


    private void setRequest() {
        progressDialog.show();
        OkHttpUtils.get().url(getString(R.string.local_base_url) + "appDangerous/findMedias/" + listBean.getId())
                .addHeader("sessionID", sessionId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showShort("网络连接失败");
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        progressDialog.dismiss();
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray data = object.getJSONArray("data");
                            JSONObject meta = object.getJSONObject("meta");
                            boolean success = meta.getBoolean("success");
                            String message = meta.getString("message");
                            Log.d("chenpeng", response);
                            if (success) {
                                photoInfos.clear();
                                videoInfos.clear();
                                audioInfos.clear();
                                fileInfos.clear();
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject jsonObject = data.getJSONObject(i);
                                    if ("1".equals(jsonObject.getString("type"))) {
                                        MediaInfo photo = new MediaInfo();
                                        photo.setFileName(jsonObject.getString("fileName"));
                                        photo.setType(jsonObject.getString("type"));
                                        photoInfos.add(photo);
                                    } else if ("2".equals(jsonObject.getString("type"))) {
                                        MediaInfo video = new MediaInfo();
                                        video.setFileName(jsonObject.getString("fileName"));
                                        video.setType(jsonObject.getString("type"));
                                        videoInfos.add(video);
                                    } else if ("3".equals(jsonObject.getString("type"))) {
                                        MediaInfo audio = new MediaInfo();
                                        audio.setFileName(jsonObject.getString("fileName"));
                                        audio.setType(jsonObject.getString("type"));
                                        audioInfos.add(audio);
                                        Log.i("QS", "onResponse: "+audioInfos.size());
                                    } else if ("4".equals(jsonObject.getString("type"))) {
                                        MediaInfo file = new MediaInfo();
                                        file.setFileName(jsonObject.getString("fileName"));
                                        file.setType(jsonObject.getString("type"));
                                        fileInfos.add(file);
                                    }
                                }
                                photoAdapter.notifyDataSetChanged();
                                videoAdapter.notifyDataSetChanged();
                                audioAdapter.notifyDataSetChanged();
                                fileAdapter.notifyDataSetChanged();
                            } else {
                                if ("exit".equals(message)) {
                                    AppUtils.startLogin(context);
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

    @OnClick({R.id.iv_take_photo, R.id.iv_take_video, R.id.iv_take_audio, R.id.btn_error, R.id.btn_confirm, R.id.tv_video, R.id.tv_audio, R.id.iv_check_file, R.id.tv_file})
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
            case R.id.btn_error:
                if (messageIsTrue()) {
                    showDialog("0");
                }
                break;
            case R.id.btn_confirm:
                if (messageIsTrue()) {
                    showDialog("1");
                }
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
            case R.id.iv_check_file:
                checkFile();
                break;
            case R.id.tv_file:
                if (!TextUtils.isEmpty(tvFile.getText())) {
                    clickText(3);
                }
                break;
        }
    }

    private void checkFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");//过滤文件类型（所有）
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, "请选择文件！"), FILE_SELECT_CODE);
        } catch (ActivityNotFoundException ex) {
            ToastUtils.showShort("未找到文件管理器");
        }
    }

    private void showDialog(final String s) {
        new AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage("确认上传当前填写信息？")
                .setPositiveButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        upload(s);
                        dialogInterface.dismiss();
                    }
                }).show();
    }

    private void upload(String i) {
        Map<String, String> map = new HashMap<>();
        String disastersId=disasterPoints.get(spDisasterPoint.getSelectedItemPosition()).getDisId();
        String reportMan = userShow.getText().toString().trim();
        String phone = phoneShow.getText().toString().trim();
        String address = addressShow.getText().toString().trim();
        String location = locationShow.getText().toString().trim();
        String type = typePos - 1 + "";
        String factor = spFactorType.getSelectedItemPosition() + "";
        String injured = TextUtils.isEmpty(injurdShow.getText().toString().trim()) ? "0" : injurdShow.getText().toString().trim();
        String death = TextUtils.isEmpty(deathShow.getText().toString().trim()) ? "0" : deathShow.getText().toString().trim();
        String miss = TextUtils.isEmpty(missShow.getText().toString().trim()) ? "0" : missShow.getText().toString().trim();
        String farm = TextUtils.isEmpty(farmShow.getText().toString().trim()) ? "0" : farmShow.getText().toString().trim();
        String house = TextUtils.isEmpty(houseShow.getText().toString().trim()) ? "0" : houseShow.getText().toString().trim();
        String money = moneyShow.getText().toString().trim();
        if (money.isEmpty() || money.equals(".")) {
            money = "0";
        }
        String lon = listBean.getLongitude();
        String lat = listBean.getLatitude();
        String other = otherShow.getText().toString().trim();
        String reportName = nameShow.getText().toString().trim();
        String reportMobile = mobileShow.getText().toString().trim();
        String handle = etHandle.getText().toString().trim();
        String disMobile = disposeMobile.getText().toString().trim();
        String disPerson = disposePerson.getText().toString().trim();
        String areaId = (String) SharedUtils.getShare(context, Constant.AREA_ID, "0");
        String personType = (String) SharedUtils.getShare(context, Constant.PERSON_TYPE, "0");
        map.put("phoneNum", phone);
        map.put("personel", reportMan);
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
        map.put("opinion", handle);
        map.put("disposeMobile", disMobile);
        map.put("disposePerson", disPerson);
        map.put("id", listBean.getId() + "");
        map.put("disastersId",disastersId);
        if ("0".equals(i)) {
            map.put("isDispose", "3");
        } else {
            map.put("isDispose", "1");
        }
        map.put("isDanger", i);//0误报 1确认灾情
        map.put("disasterNum", listBean.getDisasterNum().trim());
        setUploadRequest(map);
    }

    private void setUploadRequest(Map<String, String> map) {
        progressDialog.setMessage("正在上传");
        progressDialog.show();
        PostFormBuilder formBuilder = OkHttpUtils.post().url(getString(R.string.local_base_url) + "appDangerous/update").addHeader("sessionID", sessionId);
        for (PhotoPath photoPath : photoPaths) {
            if (photoPath != null) {
                formBuilder.addFile("file", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg", new File(photoPath.getPath()));
                Log.d("cp", "音频添加");
            }
        }
        if (!TextUtils.isEmpty(tvVideo.getText().toString())) {
            formBuilder.addFile("file", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".mp4", new File(videoFile.getAbsolutePath()));
            Log.d("cp", "音频添加");
        }
        if (!TextUtils.isEmpty(tvAudio.getText().toString())) {
            Log.d("cp", "音频添加");
            formBuilder.addFile("file", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".ogg", new File(audioPath));
        }
        if (!TextUtils.isEmpty(tvFile.getText().toString())) {
            File file = new File(tvFile.getText().toString());
            formBuilder.addFile("file", file.getName(), file);
        }
        formBuilder.params(map);
        formBuilder.addParams("type", "1");
        build = formBuilder.build();
        build.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                String message = e.getMessage();
                if ("Canceled".equals(message) || "Socket closed".equals(message)) {
                    ToastUtils.showShort("取消上传！");
                } else {
                    progressDialog.dismiss();
                    ToastUtils.showShort("网络连接失败！");
                }
            }

            @Override
            public void onResponse(String response, int id) {
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    JSONObject meta = object.getJSONObject("meta");
                    String data = object.getString("data");
                    String message = meta.getString("message");
                    boolean success = meta.getBoolean("success");
                    if (success) {
                        ToastUtils.showShort(data);
                        clean();
                    } else {
                        if ("exit".equals(message)) {
                            AppUtils.startLogin(context);
                        } else {
                            ToastUtils.showShort(data);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void clean() {
        for (PhotoPath photoPath : photoPaths) {
            if (photoPath != null) {
                FileUtils.deleteFile(new File(photoPath.getPath()));
            }
        }
        if (videoFile != null) {
            FileUtils.deleteFile(videoFile);
        }
        if (audioPath != null) {
            FileUtils.deleteFile(new File(audioPath));
        }
        for (MediaInfo photoInfo : photoInfos) {
            if (photoInfo != null) {
                File file = new File(Environment.getExternalStorageDirectory() + "/Photo", photoInfo.getFileName());
                if (file.exists()) {
                    FileUtils.deleteFile(file);
                }
            }
        }
        for (MediaInfo videoInfo : videoInfos) {
            if (videoInfo != null) {
                File file = new File(Environment.getExternalStorageDirectory() + "/Video", videoInfo.getFileName());
                if (file.exists()) {
                    FileUtils.deleteFile(file);
                }
            }
        }
        for (MediaInfo audioInfo : audioInfos) {
            if (audioInfo != null) {
                File file = new File(Environment.getExternalStorageDirectory() + "/Audio", audioInfo.getFileName());
                if (file.exists()) {
                    FileUtils.deleteFile(file);
                }
            }
        }
        setResult(DisasterListFragment.DISASTER_REQUEST_CODE);
        finish();
    }

    private void clickText(final int type) {
        View view = LayoutInflater.from(context).inflate(R.layout.click_popup_view, null);
        TextView tvPlay = view.findViewById(R.id.tv_play);
        TextView tvDelete = view.findViewById(R.id.tv_delete);
        tvPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type == 1) {
                    playMedia(videoFile, "video/mp4");
                } else if (type == 2) {
                    playAudio(audioPath);
                } else {
                    openFile(tvFile.getText().toString());
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
                } else if (type == 2) {
                    File file = new File(audioPath);
                    if (file.exists()) {
                        file.delete();
                    }
                    tvAudio.setText("");
                    audioPath = null;
                } else {
                    tvFile.setText("");
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

    private void takeAudio() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_recoder, null);
        final CheckBox btnStart = view.findViewById(R.id.btn_start_recode);
        final Chronometer chronometer = view.findViewById(R.id.chronometer);
        final ImageView close = view.findViewById(R.id.dialog_close);
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                String time = chronometer.getText().toString();
            }
        });
        final TextView tv = view.findViewById(R.id.tv_time);
        final AlertDialog show = new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(false)
                .show();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(audioPath)) {
                    File file2 = new File(audioPath);
                    if (file2.isFile() && file2.exists()) {
                        file2.delete();
                    }
                }
                chronometer.stop();// 停止计时
                File file2 = new File(audioPath);
                if (recorder != null) {
                    recorder.stop();
                    recorder.release();
                    recorder = null;
                }
                show.dismiss();
            }
        });

        btnStart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tv.setText("正在录音...");
                    File audio = createFileDir("Audio");
                    if (audio != null) {
                        audioPath = audio.getPath() + "/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".ogg";
                    } else {
                        ToastUtils.showShort("文件夹创建失败");
                    }
                    recorder = new MediaRecorder();
                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                    recorder.setOutputFile(audioPath);
                    chronometer.start();// 开始计时
                    //设置编码格式
                    try {
                        recorder.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                        ToastUtils.showShort("录音机使用失败！");
                    }
                    recorder.start();
                } else {
                    recorder.stop();
                    recorder.release();
                    recorder = null;
                    tvAudio.setText(audioPath);
                    show.dismiss();
                }
            }
        });
    }

    private void takeVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        videoFile = new File(createFileDir("Video"), new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".mp4");
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {  //针对Android7.0，需要通过FileProvider封装过的路径，提供给外部调用
            uri = FileProvider.getUriForFile(context, "com.nandi.yngsagps.fileprovider", videoFile);//通过FileProvider创建一个content类型的Uri，进行封装
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
        TextView tvTake = view.findViewById(R.id.tv_take_photo);
        TextView tvChoose = view.findViewById(R.id.tv_choose_photo);
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
        View rootView = LayoutInflater.from(context).inflate(R.layout.activity_disaster_pos, null);
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                popupWindow.dismiss();
                return false;
            }
        });
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        pictureFile = new File(createFileDir("Photo"), new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg");
        Uri imageUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {  //针对Android7.0，需要通过FileProvider封装过的路径，提供给外部调用
            imageUri = FileProvider.getUriForFile(context, "com.nandi.yngsagps.fileprovider", pictureFile);//通过FileProvider创建一个content类型的Uri，进行封装
        } else { //7.0以下，如果直接拿到相机返回的intent值，拿到的则是拍照的原图大小，很容易发生OOM，所以我们同样将返回的地址，保存到指定路径，返回到Activity时，去指定路径获取，压缩图片
            imageUri = Uri.fromFile(pictureFile);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        Log.d("cp", "图片保存路径：" + pictureFile.getAbsolutePath());
        startActivityForResult(intent, TAKE_PHOTO);
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

    private void playNetAudio(MediaInfo mediaInfo) {
        File fileDir = createFileDir("Audio");
        File file = new File(fileDir, mediaInfo.getFileName());
        if (!file.exists()) {
            progressDialog.show();
            OkHttpUtils.get().url(getString(R.string.local_base_url) + "appDangerous/download/" + mediaInfo.getFileName() + "/" + mediaInfo.getType())
                    .addHeader("sessionID", sessionId)
                    .build()
                    .execute(new FileCallBack(fileDir.getPath(), mediaInfo.getFileName()) {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            ToastUtils.showShort("获取数据失败");
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onResponse(File response, int id) {
                            progressDialog.dismiss();
                            playAudio(response.getAbsolutePath());
                        }
                    });
        } else {
            playAudio(file.getAbsolutePath());
        }
    }

    private void downloadFile(MediaInfo mediaInfo) {
        File fileDir = createFileDir("docFile");
        File file = new File(fileDir, mediaInfo.getFileName());
        if (!file.exists()) {
            progressDialog.show();
            OkHttpUtils.get().url(getString(R.string.local_base_url) + "appDangerous/download/" + mediaInfo.getFileName() + "/" + mediaInfo.getType())
                    .addHeader("sessionID", sessionId)
                    .build()
                    .execute(new FileCallBack(fileDir.getPath(), mediaInfo.getFileName()) {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            ToastUtils.showShort("获取数据失败");
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onResponse(File response, int id) {
                            progressDialog.dismiss();
                            openFile(response.getAbsolutePath());
                        }
                    });
        } else {
            openFile(file.getAbsolutePath());
        }
    }

    private void openFile(String absolutePath) {
        Intent intent = AppUtils.openFile(absolutePath,context);
        startActivity(intent);
    }

    private void playAudio(String s) {
        player = new MediaPlayer();
        try {
            player.setDataSource(s);
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final View view = LayoutInflater.from(context).inflate(R.layout.diaolog_play_audio, null);
        final CheckBox btnStart = view.findViewById(R.id.btn_dialog_play);
        final RelativeLayout roat = view.findViewById(R.id.roat);
        final TextView tvPlayer = view.findViewById(R.id.tv_player);
        final ImageView close = view.findViewById(R.id.dialog_close);
        final ObjectAnimator mCircleAnimator = ObjectAnimator.ofFloat(roat, "rotation", 0.0f, 360.0f);
        mCircleAnimator.setDuration(3000);
        mCircleAnimator.setInterpolator(new LinearInterpolator());
        mCircleAnimator.setRepeatCount(-1);
        mCircleAnimator.setRepeatMode(ObjectAnimator.RESTART);
        final AlertDialog show = new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(false)
                .show();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player != null) {
                    if (player.isPlaying()) {
                        mCircleAnimator.end();
                        player.stop();
                    }
                    player.release();
                }
                show.dismiss();
            }
        });
        btnStart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tvPlayer.setText("正在播放");
                    if (mCircleAnimator.isRunning()) {
                        mCircleAnimator.resume();
                        player.start();
                    } else {
                        mCircleAnimator.start();
                        player.start();
                    }
                } else {
                    if (player.isPlaying()) {
                        mCircleAnimator.pause();
                        player.pause();
                        tvPlayer.setText("已经暂停");
                    }
                }
            }
        });
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                tvPlayer.setText("开始播放");
                btnStart.setChecked(false);
                mCircleAnimator.end();
            }
        });

    }

    private void playMedia(File response, String type) {
        Intent it = new Intent(Intent.ACTION_VIEW);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {  //针对Android7.0，需要通过FileProvider封装过的路径，提供给外部调用
            uri = FileProvider.getUriForFile(context, "com.nandi.yngsagps.fileprovider", response);//通过FileProvider创建一个content类型的Uri，进行封装
        } else { //7.0以下，如果直接拿到相机返回的intent值，拿到的则是拍照的原图大小，很容易发生OOM，所以我们同样将返回的地址，保存到指定路径，返回到Activity时，去指定路径获取，压缩图片
            uri = Uri.fromFile(response);
        }
        it.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        it.setDataAndType(uri, type);
        startActivity(it);
    }

    private void playNetVideo(MediaInfo mediaInfo) {
        File fileDir = createFileDir("Video");
        File file = new File(fileDir, mediaInfo.getFileName());
        if (!file.exists()) {
            progressDialog.show();
            OkHttpUtils.get().url(getString(R.string.local_base_url) + "appDangerous/download/" + mediaInfo.getFileName() + "/" + mediaInfo.getType())
                    .addHeader("sessionID", sessionId)
                    .build()
                    .execute(new FileCallBack(fileDir.getPath(), mediaInfo.getFileName()) {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            ToastUtils.showShort("获取数据失败");
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onResponse(File response, int id) {
                            progressDialog.dismiss();
                            playMedia(response, "video/mp4");
                        }
                    });
        } else {
            playMedia(file, "video/mp4");
        }
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
                        Cursor cursor = managedQuery(uri, proj, null, null, null);
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
                case FILE_SELECT_CODE:
                    Uri fileUri = data.getData();
                    if (fileUri != null) {
                        String path = AppUtils.getPath(context, fileUri);
                        if (path != null) {
                            String end = path.substring(path.lastIndexOf(".") + 1, path.length()).toLowerCase(Locale.getDefault());
                            if ("ppt".equals(end)||"pptx".equals(end)||"xls".equals(end)
                                    ||"xlsx".equals(end)||"doc".equals(end)||"docx".equals(end)
                                    ||"pdf".equals(end)||"txt".equals(end)){
                                tvFile.setText(path);
                            }else {
                                ToastUtils.showShort("不支持上传此类型文件");
                            }
                        }
                    }
                    break;
            }
        }
    }

    private boolean messageIsTrue() {
        if (TextUtils.isEmpty(addressShow.getText().toString().trim())) {
            addressShow.setError("请填写详细地址");
            ToastUtils.showShort("请填写详细地址");
            return false;
        } else if (TextUtils.isEmpty(deathShow.getText().toString().trim())) {
            deathShow.setError("请填写死亡人数");
            ToastUtils.showShort("请填写死亡人数");
            return false;
        } else if (TextUtils.isEmpty(moneyShow.getText().toString().trim())) {
            moneyShow.setError("请填写财产损失");
            ToastUtils.showShort("请填写财产损失");
            return false;
        } else if (TextUtils.isEmpty(etHandle.getText().toString().trim())) {
            etHandle.setError("请填写处置意见");
            ToastUtils.showShort("请填写处置意见");
            return false;
        }
        return true;
    }
}
