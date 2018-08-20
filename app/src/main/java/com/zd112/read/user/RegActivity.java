package com.zd112.read.user;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zd112.framework.BaseActivity;
import com.zd112.framework.utils.ValidateUtils;
import com.zd112.framework.utils.ViewUtils.ViewInject;
import com.zd112.framework.view.CusButton;
import com.zd112.read.R;

public class RegActivity extends BaseActivity {

    @ViewInject(R.id.userRegInput)
    private EditText userRegInput;
    @ViewInject(R.id.userReg)
    private CusButton userReg;
    @ViewInject(R.id.userRegGoLogin)
    private CusButton userRegGoLogin;
    @ViewInject(R.id.userRegInputTips)
    private TextView userRegInputTips;
    private String inputName;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setView(R.layout.main_user_reg, this);
    }

    @Override
    protected void setListener() {
        userReg.setOnClickListener(this);
        userRegGoLogin.setOnClickListener(this);
        userRegInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                userRegInputTips.setText(null);
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.userReg:
                getInputName();
                break;
            case R.id.userRegGoLogin:
                break;
        }
    }

    private void getInputName() {
        inputName = userRegInput.getEditableText().toString().trim();
        if (TextUtils.isEmpty(inputName) || !TextUtils.isDigitsOnly(inputName) || !ValidateUtils.isMobile(inputName)) {
            userRegInputTips.setText(getString(R.string.user_input_phone));
            return;
        }
    }

}
