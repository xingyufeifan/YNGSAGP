package com.nandi.yngsagp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.nandi.yngsagp.Constant;
import com.nandi.yngsagp.R;
import com.nandi.yngsagp.bean.ScienceBean;
import com.nandi.yngsagp.utils.AppUtils;
import com.nandi.yngsagp.utils.SharedUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * @author qingsong 科普宣传展示
 */

public class ScienceDataActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.science_title)
    TextView scienceTitle;
    @BindView(R.id.time_nickname)
    TextView timeNickname;
    @BindView(R.id.science_content)
    TextView scienceContent;
    @BindView(R.id.science_file)
    TextView scienceFile;
    private ScienceBean data;
    private String sessionId;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_science_data);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null) {
            data = intent.getParcelableExtra("data");
        }
        initView();
    }


    private void initView() {
        if ("".equals(data.getMediaId())){
            scienceFile.setVisibility(View.GONE);
        }
        sessionId = (String) SharedUtils.getShare(ScienceDataActivity.this, Constant.SESSION_ID, "");
        progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("正在获取数据");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        scienceFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile();
            }
        });

        scienceTitle.setText(data.getTitle());
        timeNickname.setText(data.getTime());
        scienceContent.setText("\u3000\u3000" + data.getContent());
        scienceContent.setMovementMethod(ScrollingMovementMethod.getInstance());
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

    private void downloadFile() {
        File fileDir = createFileDir("docFile");
        File file = new File(fileDir, data.getMediaName());
        if (!file.exists()) {
            progressDialog.show();
            OkHttpUtils.get().url(getString(R.string.local_base_url) + "appDangerous/propagandaMediadown/" + data.getMediaId())
                    .addHeader("sessionID", sessionId)
                    .build()
                    .execute(new FileCallBack(fileDir.getPath(), data.getMediaName()) {
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
        Intent intent = AppUtils.openFile(absolutePath, context);
        startActivity(intent);
    }

}
