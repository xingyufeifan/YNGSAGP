package com.nandi.yngsagp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.blankj.utilcode.util.ToastUtils;
import com.nandi.yngsagp.Constant;
import com.nandi.yngsagp.OkHttpCallback;
import com.nandi.yngsagp.R;
import com.nandi.yngsagp.utils.OkHttpHelper;
import com.nandi.yngsagp.utils.SharedUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by qingsong on 2017/11/16.
 */

public class ModifyFragment extends Fragment {

    @BindView(R.id.modify_user)
    TextInputEditText modifyUser;
    @BindView(R.id.modify_psw)
    TextInputEditText modifyPsw;
    @BindView(R.id.modify_npsw)
    TextInputEditText modifyNpsw;
    @BindView(R.id.modify_anpsw)
    TextInputEditText modifyAnpsw;
    Unbinder unbinder;
    @BindView(R.id.modify_clear)
    Button modifyClear;
    @BindView(R.id.modify_sure)
    Button modifySure;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify, container, false);
        unbinder = ButterKnife.bind(this, view);
        modifyUser.setText((String) SharedUtils.getShare(getActivity(), Constant.MOBILE,""));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.modify_clear, R.id.modify_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.modify_clear:
                modifyUser.setText("");
                modifyPsw.setText("");
                modifyAnpsw.setText("");
                modifyNpsw.setText("");
                break;
            case R.id.modify_sure:
               String url= "http://localhost:8080/yncmd/appdocking/updateAppUser/{username}/{password}/{newPassword}/{type}";
                if (textInput()){
                    OkHttpHelper.sendHttpGet(getActivity(), url, new OkHttpCallback() {
                        @Override
                        public void onSuccess(String response) {

                        }

                        @Override
                        public void onError(Exception error) {

                        }
                    });
                }
                break;
        }
    }

    private boolean textInput() {
        if (TextUtils.isEmpty(modifyPsw.getText())){
           modifyPsw.setError("原密码不能为空");
           return false;
        }else if (TextUtils.isEmpty(modifyNpsw.getText())){
            modifyNpsw.setError("新密码不能为空");
            return false;
        }else if (TextUtils.isEmpty(modifyAnpsw.getText())){
            modifyAnpsw.setError("新密码不能为空");
            return false;
        }else if (modifyAnpsw.getText().toString().trim().equals(modifyNpsw.getText().toString().trim())){
            ToastUtils.showShort("两次密码输入不一致");
            return false;
        }
        return true;
    }
}
