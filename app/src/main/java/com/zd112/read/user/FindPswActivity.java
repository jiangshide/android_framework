package com.zd112.read.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.zd112.framework.BaseActivity;
import com.zd112.framework.BaseData;
import com.zd112.framework.net.helper.NetInfo;
import com.zd112.framework.utils.ShareParamUtils;
import com.zd112.framework.utils.ValidateUtils;
import com.zd112.framework.utils.ViewUtils.ViewInject;
import com.zd112.framework.view.CusButton;
import com.zd112.framework.view.DialogView;
import com.zd112.read.R;
import com.zd112.read.user.data.ValidateCodeData;
import com.zd112.read.utils.Constant;

import java.util.HashMap;

public class FindPswActivity extends BaseActivity implements DialogView.DialogOnClickListener {

    @ViewInject(R.id.userFindPswInput)
    private EditText userFindPswInput;
    @ViewInject(R.id.userFindPswCheckCode)
    private EditText userFindPswCheckCode;
    @ViewInject(R.id.userFindPswCheckCodeImg)
    private ImageView userFindPswCheckCodeImg;
    @ViewInject(R.id.userFindPswCheckCodeImgLoading)
    private ProgressBar userFindPswCheckCodeImgLoading;
    @ViewInject(R.id.userFindPswNext)
    private CusButton userFindPswNext;
    private String validateCode;
    private String uuid;
    private String mobile;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setView(R.layout.main_user_find_psw, this);
    }

    @Override
    protected void setListener() {
        userFindPswNext.setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        userFindPswInput.setText(ShareParamUtils.getString(Constant.USERNAME));
        loadCodeImg();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.userFindPswNext:
                submit();
                break;
        }
    }

    private void loadCodeImg() {
        userFindPswCheckCodeImg.setVisibility(View.GONE);
        userFindPswCheckCodeImgLoading.setVisibility(View.VISIBLE);
        HashMap<String, String> params = new HashMap<>();
//        request(Constant.USER_SYSTEM_IDENTIFY, params, ValidateCodeData.class, this, this);
    }

    private void submit() {
        mobile = userFindPswInput.getEditableText().toString().trim();
        if (TextUtils.isEmpty(mobile) && !ValidateUtils.isMobile(mobile)) {
            loading(getString(R.string.user_input_phone)).setOnlySure();
            return;
        }
        validateCode = userFindPswCheckCode.getEditableText().toString().trim();
        if (TextUtils.isEmpty(validateCode)) {
            loading(getString(R.string.input_pic_validate_code)).setOnlySure();
            return;
        }
        HashMap<String, String> params = new HashMap<>();
//        params.put(Constant.CHECK_TYPE, Constant.CHECK_TYPE_ONE);
//        params.put(Constant.USER_MOBILE, mobile);
//        params.put(Constant.VALIDATE_CODE, validateCode);
//        params.put(Constant.UUID, uuid);
//        request(Constant.USER_INFO_CHECK, params, UserInfoCheckData.class, this,);
    }

    @Override
    public void onSuccess(NetInfo info) {
        super.onSuccess(info);
        Object object = info.getResponseObj();
        if (object != null) {
            if (object instanceof ValidateCodeData) {
            } else {
                loading(((BaseData) object).msg).setOnlySure();
            }
        } else {
            loading(getString(R.string.net_failure));
        }
    }

    private void showValidateCode(boolean isShow) {
        if (isShow) {
            userFindPswCheckCodeImg.setVisibility(View.VISIBLE);
            userFindPswCheckCodeImgLoading.setVisibility(View.GONE);
            return;
        }
        userFindPswCheckCodeImg.setVisibility(View.GONE);
        userFindPswCheckCodeImgLoading.setVisibility(View.VISIBLE);
    }

    public void userFindPswCheckCodeImg(View view) {
        loadCodeImg();
    }

    @Override
    public void onDialogClick(boolean isClick) {
        loadCodeImg();
    }
}
