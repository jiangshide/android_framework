package com.zd112.read.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zd112.framework.BaseActivity;
import com.zd112.framework.utils.ShareParamUtils;
import com.zd112.framework.utils.ViewUtils.ViewInject;
import com.zd112.framework.view.CusViewPager;
import com.zd112.read.R;
import com.zd112.read.utils.Constant;

public class FirstActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
    @ViewInject(R.id.firstFirstView)
    private CusViewPager firstFirstView;
    @ViewInject(R.id.userFirstJump)
    private ImageView userFirstJump;
    @ViewInject(R.id.userFirstBtnL)
    private LinearLayout userFirstBtnL;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setView(R.layout.main_user_first, this);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        firstFirstView.setResImg(R.mipmap.guide_one, R.mipmap.guide_two, R.mipmap.guide_three);
        firstFirstView.addOnPageChangeListener(this);
    }

    public void userFirstJump(View view) {
        ShareParamUtils.INSTANCE.putBoolean(Constant.GUIDE, true);
        startActivity(new Intent(this, SplashActivity.class));
    }

    public void userFirstExperience(View view) {
        ShareParamUtils.INSTANCE.putBoolean(Constant.GUIDE, true);
        startActivity(new Intent(this, SplashActivity.class));
    }

    public void userFirstGoLogin(View view) {
        ShareParamUtils.INSTANCE.putBoolean(Constant.GUIDE, true);
        startActivity(new Intent(this, RegActivity.class));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 2) {
            userFirstJump.setVisibility(View.GONE);
            userFirstBtnL.setVisibility(View.VISIBLE);
        } else {
            userFirstJump.setVisibility(View.VISIBLE);
            userFirstBtnL.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
