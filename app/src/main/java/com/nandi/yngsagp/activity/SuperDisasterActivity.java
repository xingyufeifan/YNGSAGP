package com.nandi.yngsagp.activity;

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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
import com.nandi.yngsagp.adapter.PhotoAdapter;
import com.nandi.yngsagp.adapter.PictureAdapter;
import com.nandi.yngsagp.bean.MediaInfo;
import com.nandi.yngsagp.bean.PhotoPath;
import com.nandi.yngsagp.bean.SuperBean;
import com.nandi.yngsagp.utils.SharedUtils;
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
import butterknife.OnClick;
import okhttp3.Call;

public class SuperDisasterActivity extends AppCompatActivity {

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
    @BindView(R.id.userShow)
    TextView userShow;
    @BindView(R.id.phoneShow)
    TextView phoneShow;
    @BindView(R.id.timeShow)
    TextView timeShow;
    @BindView(R.id.locationShow)
    TextView locationShow;
    @BindView(R.id.addressShow)
    TextView addressShow;
    @BindView(R.id.lonShow)
    TextView lonShow;
    @BindView(R.id.latShow)
    TextView latShow;
    @BindView(R.id.disNumShow)
    TextView disNumShow;
    @BindView(R.id.typeShow)
    Spinner typeShow;
    @BindView(R.id.factorShow)
    TextView factorShow;
    @BindView(R.id.injurdShow)
    TextView injurdShow;
    @BindView(R.id.deathShow)
    TextView deathShow;
    @BindView(R.id.missShow)
    TextView missShow;
    @BindView(R.id.farmShow)
    TextView farmShow;
    @BindView(R.id.houseShow)
    TextView houseShow;
    @BindView(R.id.moneyShow)
    TextView moneyShow;
    @BindView(R.id.mobileShow)
    TextView mobileShow;
    @BindView(R.id.nameShow)
    TextView nameShow;
    @BindView(R.id.ll_dReport)
    LinearLayout llDReport;
    @BindView(R.id.otherShow)
    TextView otherShow;
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
    @BindView(R.id.tv_video_uploaded)
    TextView tvVideoUploaded;
    @BindView(R.id.tv_audio_uploaded)
    TextView tvAudioUploaded;
    @BindView(R.id.media_layout)
    LinearLayout mediaLayout;
    @BindView(R.id.text_layout)
    LinearLayout textLayout;
    private SuperBean disasterListBean;
    private ProgressDialog progressDialog;
    private MediaInfo videoInfo = new MediaInfo();
    private MediaInfo audioInfo = new MediaInfo();
    private List<MediaInfo> photoInfos = new ArrayList<>();
    private List<PhotoPath> photoPaths = new ArrayList<>();
    private Context context;
    private PhotoAdapter photoAdapter;
    private PictureAdapter pictureAdapter;
    private File audioNetFile;
    private File videoNetFile;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster_super);
        ButterKnife.bind(this);
        context = this;
        initData();
        initView();
        setListener();
    }

    private void initView() {
        photoAdapter = new PhotoAdapter(context, photoInfos);
        rvPhotoUploaded.setLayoutManager(new GridLayoutManager(context, 3));
        rvPhotoUploaded.setAdapter(photoAdapter);
        pictureAdapter = new PictureAdapter(context, photoPaths);

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
        disposePerson.setText((CharSequence) SharedUtils.getShare(context, Constant.NAME, ""));
        disposeMobile.setText((CharSequence) SharedUtils.getShare(context, Constant.MOBILE, ""));
        if ("1".equals((CharSequence) disasterListBean.getPersonType())) {
            llDReport.setVisibility(View.GONE);
        }
        ToastUtils.showShort(disasterListBean.getForecastLevel());
        if ("1".equals(disasterListBean.getLevel())) {
            level.setText("小型");
        } else if ("2".equals(disasterListBean.getLevel())) {
            level.setText("中型");
        } else if ("3".equals(disasterListBean.getLevel())) {
            level.setText("大型");
        } else if ("4".equals(disasterListBean.getLevel())) {
            level.setText("特大型");
        }
        if (disasterListBean.getForecastLevel().equals(null)) {
            foreLevel.setText(disasterListBean.getForecastLevel());
        } else if ("1".equals(disasterListBean.getForecastLevel())) {
            foreLevel.setText("小型");
        } else if ("2".equals(disasterListBean.getForecastLevel())) {
            foreLevel.setText("中型");
        } else if ("3".equals(disasterListBean.getForecastLevel())) {
            foreLevel.setText("大型");
        } else if ("4".equals(disasterListBean.getForecastLevel())) {
            foreLevel.setText("特大型");
        }
        int isDispose = disasterListBean.getIsDispose();
        System.out.println("isDisPose = " + isDispose);
        if (0 == isDispose) {
            tvTitle.setText("已处理灾情");
            etHandle.setText("没得数据");
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
        photoAdapter.setOnItemViewClickListener(new PhotoAdapter.OnItemViewClickListener() {
            @Override
            public void onPictureClick(int position) {
                enlargePhoto(photoInfos.get(position));
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
    }

    private void enlargePicture(String path) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_enlarge_photo, null);
        PhotoView photoView = (PhotoView) view.findViewById(R.id.photo_view);
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

    private void initData() {
        progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("正在获取数据");

        disasterListBean = (SuperBean) getIntent().getSerializableExtra(Constant.DISASTER);
        setRequest();
    }

    private void setRequest() {
        progressDialog.show();
        OkHttpUtils.get().url(getString(R.string.local_base_url) + "dangerous/findMedia/" + disasterListBean.getId())
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
                                    MediaInfo mediaInfo = new MediaInfo();
                                    mediaInfo.setFileName(jsonObject.getString("fileName"));
                                    mediaInfo.setType(jsonObject.getString("type"));
                                    photoInfos.add(mediaInfo);
                                } else if ("2".equals(jsonObject.getString("type"))) {
                                    videoInfo.setFileName(jsonObject.getString("fileName"));
                                    videoInfo.setType(jsonObject.getString("type"));
                                } else if ("3".equals(jsonObject.getString("type"))) {
                                    audioInfo.setFileName(jsonObject.getString("fileName"));
                                    audioInfo.setType(jsonObject.getString("type"));
                                }
                            }
                            photoAdapter.notifyDataSetChanged();
                            tvVideoUploaded.setText(videoInfo.getFileName());
                            tvAudioUploaded.setText(audioInfo.getFileName());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @OnClick({R.id.tv_video_uploaded, R.id.tv_audio_uploaded})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.tv_video_uploaded:
                playNetVideo();
                break;
            case R.id.tv_audio_uploaded:
                playNetAudio();
                break;

        }
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

    private void playNetAudio() {
        if (audioNetFile == null) {
            progressDialog.show();
            OkHttpUtils.get().url(getString(R.string.local_base_url) + "dangerous/download/" + audioInfo.getFileName() + "/" + audioInfo.getType())
                    .build()
                    .execute(new FileCallBack(Environment.getExternalStorageDirectory() + "/Audio", audioInfo.getFileName()) {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            ToastUtils.showShort("获取数据失败");
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onResponse(File response, int id) {
                            audioNetFile = response;
                            progressDialog.dismiss();
                            playAudio(response.getAbsolutePath());
                        }
                    });
        } else {
            playMedia(audioNetFile, "audio/mp3");
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
        View view = LayoutInflater.from(context).inflate(R.layout.diaolog_play_audio, null);
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

    private void playNetVideo() {
        if (videoNetFile == null) {
            progressDialog.show();
            OkHttpUtils.get().url(getString(R.string.local_base_url) + "dangerous/download/" + videoInfo.getFileName() + "/" + videoInfo.getType())
                    .build()
                    .execute(new FileCallBack(Environment.getExternalStorageDirectory() + "/Audio", audioInfo.getFileName()) {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            ToastUtils.showShort("获取数据失败");
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onResponse(File response, int id) {
                            progressDialog.dismiss();
                            videoNetFile = response;
                            playMedia(response, "video/mp4");
                        }
                    });
        } else {
            playMedia(videoNetFile, "video/mp4");
        }
    }

}
