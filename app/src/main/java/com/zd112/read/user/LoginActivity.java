package com.zd112.read.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zd112.framework.BaseActivity;
import com.zd112.framework.BaseData;
import com.zd112.framework.net.helper.NetInfo;
import com.zd112.framework.utils.ShareParamUtils;
import com.zd112.framework.utils.ViewUtils.ViewInject;
import com.zd112.framework.view.CusButton;
import com.zd112.framework.view.CusEditText;
import com.zd112.framework.view.NavigationTopView;
import com.zd112.read.MainActivity;
import com.zd112.read.R;
import com.zd112.read.user.data.UserData;
import com.zd112.read.utils.Constant;

import java.util.HashMap;

public class LoginActivity extends BaseActivity implements TextWatcher {

    @ViewInject(R.id.topView)
    private NavigationTopView topView;
    @ViewInject(R.id.userLoginInput)
    private CusEditText userLoginInput;
    @ViewInject(R.id.userLoginInputPsw)
    private CusEditText userLoginInputPsw;
    @ViewInject(R.id.userLoginTips)
    private TextView userLoginTips;
    @ViewInject(R.id.userLogin)
    private CusButton userLogin;
    @ViewInject(R.id.userLoginGoReg)
    private CusButton userLoginGoReg;
    private boolean isPswHide;
    private String userLoginName;
    private String userLoginPsw;
    private UserData userData;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setView(R.layout.main_user_login, this);
    }

    @Override
    protected void setListener() {
        userLogin.setOnClickListener(this);
        userLoginGoReg.setOnClickListener(this);
        userLoginInput.addTextChangedListener(this);
        userLoginInputPsw.addTextChangedListener(this);
        topView.setOnLeftClick(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        userLoginInput.setText(ShareParamUtils.getString(Constant.USERNAME));
    }

    public void userLoginInputImg(View view) {
        if (!isPswHide) {
            userLoginInputPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            ((ImageView) view).setImageResource(R.mipmap.user_eye_open);
            isPswHide = true;
        } else {
            isPswHide = false;
            userLoginInputPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
            ((ImageView) view).setImageResource(R.mipmap.user_eye_off);
        }
    }

    public void userLogin(String identify, String uuid) {
        String psw = Constant.getEncryptPsw(userLoginPsw);
        HashMap<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(identify)) {
            params.put(Constant.IDENTIFY, identify);
            params.put(Constant.UUID, uuid);
        }
//        params.put(Constant.USER_LOGIN_NAME, userLoginName);
//        params.put(Constant.USE_LOGIN_PSWD, psw);
//        request(Constant.USER_LOGIN, params, UserData.class, this, true);
    }

    private void userToken(String useId, String sessionId) {
        HashMap<String, String> params = new HashMap<>();
//        params.put(Constant.USE_ID, useId);
//        params.put(Constant.SESSION_ID, sessionId);
//        request(Constant.USER_TOKEN, params, TokenData.class, this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case NavigationTopView.leftId:
                int source = getIntent().getIntExtra(Constant.SOURCE, -1);
                if (source != -1) {
                    startActivity(new Intent(this, MainActivity.class));
                }
                this.finish();
                break;
            case R.id.userLogin:
                userLoginName = userLoginInput.getEditableText().toString().trim();
                if (TextUtils.isEmpty(userLoginName)) {
                    loading(getString(R.string.user_input_name)).setOnlySure();
                    return;
                }
                userLoginPsw = userLoginInputPsw.getEditableText().toString().trim();
                if (TextUtils.isEmpty(userLoginPsw)) {
                    loading(getString(R.string.user_input_psw)).setOnlySure();
                    return;
                }
//                new ValidateCodeView().init(this, new ValidateCodeView.OnValidateCodeListener() {
//                    @Override
//                    public void onValidateCode(String result, String uuid) {
//                        userLogin(result, uuid);
//                    }
//                });
                userLogin(null, null);
                break;
            case R.id.userLoginGoReg:
                userLoginGoReg();
                break;
        }
    }

    public void userLoginGoReg() {
        startActivity(new Intent(this, RegActivity.class));
    }

    public void userLoginFindPsw(View view) {
        startActivity(new Intent(this, FindPswActivity.class));
    }

    @Override
    public void onSuccess(NetInfo info) {
        super.onSuccess(info);
        Object object = info.getResponseObj();
        if (object != null) {
            if (object instanceof UserData) {
                userData = (UserData) object;
                if (userData.res != null && userData.res.sftUserMdl != null) {
                    userToken(userData.res.sftUserMdl.useId, userData.res.sessionId);
                } else {
                    loading(userData.msg).setOnlySure();
                }
            } else {
                userLoginTips.setText(((BaseData) object).msg);
            }
        } else {
            cancelLoading();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        navigationBar.changeBarListener(Constant.TAB_BOOK);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        userLoginTips.setText(null);
    }
}
