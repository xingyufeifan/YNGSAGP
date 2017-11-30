package com.nandi.yngsagp.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.nandi.yngsagp.Constant;
import com.nandi.yngsagp.OkHttpCallback;
import com.nandi.yngsagp.R;
import com.nandi.yngsagp.utils.InputUtil;
import com.nandi.yngsagp.utils.OkHttpHelper;
import com.nandi.yngsagp.utils.SharedUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.iv_clean_phone)
    ImageView ivCleanPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.clean_password)
    ImageView cleanPassword;
    @BindView(R.id.iv_show_pwd)
    ImageView ivShowPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.root)
    RelativeLayout root;
    @BindView(R.id.ic_mobile)
    ImageView icMobile;
    @BindView(R.id.ic_password)
    ImageView icPassword;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.ll_input_mobile)
    LinearLayout llInputMobile;
    @BindView(R.id.ll_input_psd)
    LinearLayout llInputPsd;
    private int screenHeight = 0;//屏幕高度
    private int keyHeight = 0; //软件盘弹起后所占高度
    private String pwd;
    private String mobile;
    private ProgressDialog progressDialog;
    private Context mContext;
    private boolean isCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        ButterKnife.bind(this);
        checkPremission();
        intiView();
        initListener();
    }


    private void intiView() {
        if (SharedUtils.containsShare(mContext, Constant.MOBILE)) {
            etMobile.setText((String) SharedUtils.getShare(mContext, Constant.MOBILE, ""));

        }
        if (SharedUtils.containsShare(mContext, Constant.PASSWORD)) {
            etPassword.setText((String) SharedUtils.getShare(mContext, Constant.PASSWORD, ""));
        }
        screenHeight = this.getResources().getDisplayMetrics().heightPixels; //获取屏幕高度
        keyHeight = screenHeight / 4;//弹起高度为屏幕高度的1/3
        //dialog
        progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("正在登录...");
        if (!TextUtils.isEmpty(etMobile.getText()) && ivCleanPhone.getVisibility() == View.GONE) {
            icMobile.setImageResource(R.mipmap.ic_mobile);
            ivCleanPhone.setVisibility(View.VISIBLE);
        } else {
            ivCleanPhone.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(etPassword.getText()) && cleanPassword.getVisibility() == View.GONE) {
            icPassword.setImageResource(R.mipmap.ic_psw);
            cleanPassword.setVisibility(View.VISIBLE);
        } else {
            cleanPassword.setVisibility(View.GONE);
        }
    }

    private void initListener() {
        ivCleanPhone.setOnClickListener(this);
        cleanPassword.setOnClickListener(this);
        ivShowPwd.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCheck = isChecked;
            }
        });
        etMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && ivCleanPhone.getVisibility() == View.GONE) {
                    icMobile.setImageResource(R.mipmap.ic_mobile);
                    ivCleanPhone.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    icMobile.setImageResource(R.mipmap.ic_mobile1);
                    ivCleanPhone.setVisibility(View.GONE);
                }
            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && cleanPassword.getVisibility() == View.GONE) {
                    icPassword.setImageResource(R.mipmap.ic_psw);
                    cleanPassword.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    icPassword.setImageResource(R.mipmap.ic_psw1);
                    cleanPassword.setVisibility(View.GONE);
                }
                if (s.toString().isEmpty())
                    return;
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    s.delete(temp.length() - 1, temp.length());
                    etPassword.setSelection(s.length());
                }
            }
        });
        root.addOnLayoutChangeListener(new ViewGroup.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
              /* old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
              现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起*/
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.smoothScrollTo(0, scrollView.getHeight());
                        }
                    }, 0);


                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                    //键盘收回后，logo恢复原来大小，位置同样回到初始位置
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.smoothScrollTo(0, scrollView.getHeight());
                        }
                    }, 0);


                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        pwd = etPassword.getText().toString();
        mobile = etMobile.getText().toString();
        int id = v.getId();
        switch (id) {
            case R.id.iv_clean_phone:
                etMobile.setText("");
                break;
            case R.id.clean_password:
                etPassword.setText("");
                break;
            case R.id.iv_show_pwd:
                if (etPassword.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ivShowPwd.setImageResource(R.mipmap.ic_visibly);
                } else {
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ivShowPwd.setImageResource(R.mipmap.ic_visibly_up);
                }
                if (!TextUtils.isEmpty(pwd))
                    etPassword.setSelection(pwd.length());
                break;
            case R.id.btn_login:
                if (TextUtils.isEmpty(mobile)) {
                    showToast("请输入账号");
                } else if (TextUtils.isEmpty(pwd)) {
                    showToast("请输入密码");
                } else {
                    loginPost();
                    progressDialog.show();
                }
                break;
        }
    }

    private void loginPost() {
        OkHttpHelper.sendHttpGet(this, getResources().getString(R.string.local_base_url) + "appdocking/login/" + mobile + "/" + pwd, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                System.out.println("response = " + response);

                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonMeta = new JSONObject(jsonObject.optString("meta"));
                    boolean isSuccess = jsonMeta.optBoolean("success");
                    if (isSuccess) {
                        initJson(jsonObject);
                        finish();
                        ToNextActivity(MainActivity.class);
                    } else {
                        String message = jsonMeta.optString("message");
                        etPassword.setText("");
                        showToast(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Exception error) {
                showToast("网络连接失败" + error.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    private void initJson(JSONObject jsonObject) throws JSONException {
        JSONObject jsonData = new JSONObject(jsonObject.optString("data"));
        String area_id = jsonData.optString("area_id");
        SharedUtils.putShare(mContext, Constant.AREA_ID, area_id);
        String address = jsonData.optString("address");
        SharedUtils.putShare(mContext, Constant.ADDRESS, address);
        String name = jsonData.optString("nickname");
        SharedUtils.putShare(mContext, Constant.NAME, name);
        String personType = jsonData.optString("role");
        SharedUtils.putShare(mContext, Constant.PERSON_TYPE, personType);
        SharedUtils.putShare(mContext, Constant.MOBILE, mobile);
        if (isCheck) {
            SharedUtils.putShare(mContext, Constant.PASSWORD, pwd);
            SharedUtils.putShare(mContext, Constant.IS_LOGIN, true);
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

    //权限申请
    private void checkPremission() {
        List<PermissionItem> permissionItems = new ArrayList<PermissionItem>();
        permissionItems.add(new PermissionItem(Manifest.permission.CAMERA, "照相机", R.drawable.permission_ic_camera));
        permissionItems.add(new PermissionItem(Manifest.permission.ACCESS_FINE_LOCATION, "定位", R.drawable.permission_ic_location));
        permissionItems.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, "读写SD卡", R.drawable.permission_ic_storage));
        permissionItems.add(new PermissionItem(Manifest.permission.RECORD_AUDIO, "录音", R.drawable.permission_ic_micro_phone));
        HiPermission.create(this)
                .title("权限申请")
                .permissions(permissionItems)
                .filterColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, getTheme()))//图标的颜色
                .msg("为了您更好的使用体验，开启这些权限吧！\n一定要确认啰！")
                .style(R.style.PermissionDefaultBlueStyle)
                .checkMutiPermission(new PermissionCallback() {
                    @Override
                    public void onClose() {
                        checkPremission();
                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onDeny(String permission, int position) {
                    }

                    @Override
                    public void onGuarantee(String permission, int position) {

                    }
                });


    }

}
