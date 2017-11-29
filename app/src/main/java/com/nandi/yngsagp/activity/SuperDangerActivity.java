package com.nandi.yngsagp.activity;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.nandi.yngsagp.bean.MediaInfo;
import com.nandi.yngsagp.bean.SuperBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class SuperDangerActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.head_rl)
    RelativeLayout headRl;
    @BindView(R.id.foreLevel)
    TextView foreLevel;
    @BindView(R.id.level)
    TextView level;
    @BindView(R.id.userDangerShow)
    TextView userDangerShow;
    @BindView(R.id.phoneDangerShow)
    TextView phoneDangerShow;
    @BindView(R.id.timeDangerShow)
    TextView timeDangerShow;
    @BindView(R.id.addressDangerShow)
    TextView addressDangerShow;
    @BindView(R.id.locationDangerShow)
    TextView locationDangerShow;
    @BindView(R.id.lonDangerShow)
    TextView lonDangerShow;
    @BindView(R.id.latDangerShow)
    TextView latDangerShow;
    @BindView(R.id.xqNumShow)
    TextView xqNumShow;
    @BindView(R.id.typeDangerShow)
    Spinner typeDangerShow;
    @BindView(R.id.factorDangerShow)
    TextView factorDangerShow;
    @BindView(R.id.personDangerShow)
    TextView personDangerShow;
    @BindView(R.id.houseDangerShow)
    TextView houseDangerShow;
    @BindView(R.id.moneyDangerShow)
    TextView moneyDangerShow;
    @BindView(R.id.areaDangerShow)
    TextView areaDangerShow;
    @BindView(R.id.dReportMobileShow)
    TextView dReportMobileShow;
    @BindView(R.id.dReportNameShow)
    TextView dReportNameShow;
    @BindView(R.id.ll_dReport)
    LinearLayout llDReport;
    @BindView(R.id.otherDangerShow)
    TextView otherDangerShow;
    @BindView(R.id.disposePerson)
    TextView disposePerson;
    @BindView(R.id.disposeMobile)
    TextView disposeMobile;
    @BindView(R.id.et_handle)
    TextView etHandle;
    @BindView(R.id.ll_handle)
    LinearLayout llHandle;
    @BindView(R.id.rv_photo_uploaded)
    RecyclerView rvPhotoUploaded;
    @BindView(R.id.text_layout)
    LinearLayout textLayout;
    @BindView(R.id.media_layout)
    LinearLayout mediaLayout;
    @BindView(R.id.rv_video_updated)
    RecyclerView rvVideoUpdated;
    @BindView(R.id.rv_audio_updated)
    RecyclerView rvAudioUpdated;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    private SuperBean listBean;
    private ProgressDialog progressDialog;
    private MediaInfo videoInfo = new MediaInfo();
    private MediaInfo audioInfo = new MediaInfo();
    private List<MediaInfo> photoInfos = new ArrayList<>();
    private List<MediaInfo> videoInfos = new ArrayList<>();
    private List<MediaInfo> audioInfos = new ArrayList<>();
    private Context context;
    private PhotoAdapter photoAdapter;
    private MediaAdapter videoAdapter;
    private MediaAdapter audioAdapter;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danger_super);
        ButterKnife.bind(this);
        context = this;
        initData();
        initView();
        setListener();
    }

    private void initView() {
        tabLayout.addTab(tabLayout.newTab().setText("文本信息"), 0, true);
        tabLayout.addTab(tabLayout.newTab().setText("媒体信息"), 1);
        photoAdapter = new PhotoAdapter(context, photoInfos);
        videoAdapter = new MediaAdapter(context, videoInfos);
        audioAdapter = new MediaAdapter(context, audioInfos);
        rvPhotoUploaded.setLayoutManager(new GridLayoutManager(context, 3));
        rvVideoUpdated.setLayoutManager(new LinearLayoutManager(context));
        rvAudioUpdated.setLayoutManager(new LinearLayoutManager(context));
        rvPhotoUploaded.setAdapter(photoAdapter);
        rvVideoUpdated.setAdapter(videoAdapter);
        rvAudioUpdated.setAdapter(audioAdapter);
        userDangerShow.setText((CharSequence) listBean.getPersonel());
        xqNumShow.setText((CharSequence) listBean.getDisasterNum());
        phoneDangerShow.setText((CharSequence) listBean.getPhoneNum());
        timeDangerShow.setText((CharSequence) listBean.getHappenTime());
        locationDangerShow.setText((CharSequence) listBean.getCurrentLocation());
        addressDangerShow.setText((CharSequence) listBean.getAddress());
        typeDangerShow.setSelection(Integer.parseInt(listBean.getDisasterType()));
        factorDangerShow.setText((CharSequence) listBean.getFactor());
        personDangerShow.setText((CharSequence) listBean.getPersonNum());
        areaDangerShow.setText((CharSequence) listBean.getFarmland());
        houseDangerShow.setText((CharSequence) listBean.getHouseNum());
        moneyDangerShow.setText((CharSequence) listBean.getPotentialLoss());
        lonDangerShow.setText((CharSequence) listBean.getLongitude());
        latDangerShow.setText((CharSequence) listBean.getLatitude());
        otherDangerShow.setText((CharSequence) listBean.getOtherThing());
        dReportMobileShow.setText((CharSequence) listBean.getMonitorPhone());
        dReportNameShow.setText((CharSequence) listBean.getMonitorName());
        if ("2".equals((CharSequence) listBean.getPersonType())) {
            llDReport.setVisibility(View.GONE);
        }
        ToastUtils.showShort(listBean.getForecastLevel());
        if ("1".equals(listBean.getLevel())) {
            level.setText("小型");
        } else if ("2".equals(listBean.getLevel())) {
            level.setText("中型");
        } else if ("3".equals(listBean.getLevel())) {
            level.setText("大型");
        } else if ("4".equals(listBean.getLevel())) {
            level.setText("特大型");
        }
        if (listBean.getForecastLevel().equals(null)) {
            foreLevel.setText(listBean.getForecastLevel());
        } else if ("1".equals(listBean.getForecastLevel())) {
            foreLevel.setText("小型");
        } else if ("2".equals(listBean.getForecastLevel())) {
            foreLevel.setText("中型");
        } else if ("3".equals(listBean.getForecastLevel())) {
            foreLevel.setText("大型");
        } else if ("4".equals(listBean.getForecastLevel())) {
            foreLevel.setText("特大型");
        }
        int isDispose = listBean.getIsDispose();
        System.out.println("isDisPose = " + isDispose);
        if (0 == isDispose) {
            tvTitle.setText("未处理险情");
            llHandle.setVisibility(View.GONE);
        } else {
            tvTitle.setText("已处理险情");
            disposePerson.setText((CharSequence) listBean.getDisposePerson());
            disposeMobile.setText((CharSequence) listBean.getDisposeMobile());
            etHandle.setText((CharSequence) listBean.getOpinion());
        }
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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

    private void initData() {
        progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("正在获取数据");
        listBean = (SuperBean) getIntent().getSerializableExtra(Constant.DISASTER);
        setRequest();
    }

    private void setRequest() {
        progressDialog.show();
        OkHttpUtils.get().url(getString(R.string.local_base_url) + "dangerous/findMedia/" + listBean.getId())
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
                                }
                            }
                            photoAdapter.notifyDataSetChanged();
                            videoAdapter.notifyDataSetChanged();
                            audioAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

    private void playNetAudio(MediaInfo mediaInfo) {
        File fileDir = createFileDir("Audio");
        File file = new File(fileDir, mediaInfo.getFileName());
        if (!file.exists()) {
            progressDialog.show();
            OkHttpUtils.get().url(getString(R.string.local_base_url) + "dangerous/download/" + mediaInfo.getFileName() + "/" + mediaInfo.getType())
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
            uri = Uri.parse(response.getAbsolutePath());
        } else { //7.0以下，如果直接拿到相机返回的intent值，拿到的则是拍照的原图大小，很容易发生OOM，所以我们同样将返回的地址，保存到指定路径，返回到Activity时，去指定路径获取，压缩图片
            uri = Uri.parse("file://" + response.getAbsolutePath());
        }
        it.setDataAndType(uri, type);
        startActivity(it);
    }

    private void playNetVideo(MediaInfo mediaInfo) {
        File fileDir = createFileDir("Video");
        File file = new File(fileDir, mediaInfo.getFileName());
        if (!file.exists()) {
            progressDialog.show();
            OkHttpUtils.get().url(getString(R.string.local_base_url) + "dangerous/download/" + mediaInfo.getFileName() + "/" + mediaInfo.getType())
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
}
