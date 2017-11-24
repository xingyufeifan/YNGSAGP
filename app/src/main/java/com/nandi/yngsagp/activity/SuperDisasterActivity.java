package com.nandi.yngsagp.activity;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.support.design.widget.TabLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.nandi.yngsagp.adapter.PhotoAdapter;
import com.nandi.yngsagp.adapter.PictureAdapter;
import com.nandi.yngsagp.bean.MediaInfo;
import com.nandi.yngsagp.bean.PhotoPath;
import com.nandi.yngsagp.bean.SuperBean;
import com.nandi.yngsagp.utils.PictureUtils;
import com.nandi.yngsagp.utils.SharedUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class SuperDisasterActivity extends AppCompatActivity {
    private static final int PICK_PHOTO = 1;
    private static final int TAKE_PHOTO = 2;
    private static final int TAKE_VIDEO = 3;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.head_rl)
    RelativeLayout headRl;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
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
    private File pictureFile;
    private File videoFile;
    private PopupWindow popupWindow;
    private String audioPath;
    private MediaRecorder recorder;
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
        rvPhoto.setLayoutManager(new GridLayoutManager(context, 3));
        rvPhoto.setAdapter(pictureAdapter);
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
        disposePerson.setText((CharSequence) SharedUtils.getShare(context,Constant.NAME,""));
        disposeMobile.setText((CharSequence) SharedUtils.getShare(context,Constant.MOBILE,""));
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
            llAddMedia.setVisibility(View.GONE);
            etHandle.setText("没得数据");
        } else {
            tvTitle.setText("未处理灾情");
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

    @OnClick({R.id.iv_take_photo, R.id.iv_take_video, R.id.iv_take_audio, R.id.tv_video_uploaded, R.id.tv_audio_uploaded, R.id.tv_video, R.id.tv_audio})
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
            case R.id.tv_video_uploaded:
                playNetVideo();
                break;
            case R.id.tv_audio_uploaded:
                playNetAudio();
                break;
            case R.id.tv_video:
                if (!TextUtils.isEmpty(tvVideo.getText())) {
                    clickText(1);
                }
                break;
            case R.id.tv_audio:
                if (!TextUtils.isEmpty(tvVideo.getText())) {
                    clickText(2);
                }
                break;
        }
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
                } else {
                    playAudio(audioPath);
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

    private void takeAudio() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_recoder, null);
        final Button btnStart = (Button) view.findViewById(R.id.btn_start_recode);
        final Button btnStop = (Button) view.findViewById(R.id.btn_stop_recode);
        btnStop.setEnabled(false);
        final TextView tv = (TextView) view.findViewById(R.id.tv_time);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(false)
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
                } else {
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
            imageUri = FileProvider.getUriForFile(context, "com.nandi.yngsagp.fileprovider", pictureFile);//通过FileProvider创建一个content类型的Uri，进行封装
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
            }
        }
    }
}
