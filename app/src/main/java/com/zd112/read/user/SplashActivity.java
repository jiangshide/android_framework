package com.zd112.read.user;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zd112.framework.BaseActivity;
import com.zd112.framework.net.helper.NetInfo;
import com.zd112.framework.service.UpdateService;
import com.zd112.framework.utils.PermissionUtils;
import com.zd112.framework.utils.ViewUtils.ViewInject;
import com.zd112.framework.view.CusButton;
import com.zd112.framework.view.DialogView;
import com.zd112.read.MainActivity;
import com.zd112.read.MyApplication;
import com.zd112.read.R;
import com.zd112.read.user.data.AdvertData;
import com.zd112.read.user.data.StopData;
import com.zd112.read.user.data.UpdateData;
import com.zd112.read.utils.Constant;

public class SplashActivity extends BaseActivity {
    @ViewInject(R.id.splashImg)
    private ImageView splashImg;
    @ViewInject(R.id.splashBtn)
    private CusButton splashBtn;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setView(R.layout.main_user_splash, this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        PermissionUtils.checkAndRequestMorePermissions(this, Constant.PERMISSIONS, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        request("stop", StopData.class);
    }

    @Override
    public void onSuccess(NetInfo info) {
        super.onSuccess(info);
        if (info.getResponseObj() instanceof StopData) {
            StopData.Res res = ((StopData) info.getResponseObj()).res;
            if (res != null && !TextUtils.isEmpty(res.url)) {
                MyApplication.application.webView(res.url);
            } else {
                request("update", UpdateData.class);
            }
        } else if (info.getResponseObj() instanceof UpdateData) {
            final UpdateData.Res res = ((UpdateData) info.getResponseObj()).res;
            if (null != res && !TextUtils.isEmpty(res.url)) {
                if (res.status == 0 || res.status == 2) {
                    startService(new Intent(getApplicationContext(), UpdateService.class).putExtra("url", res.url));
                } else {
                    loading(R.layout.update, new DialogView.DialogViewListener() {
                        @Override
                        public void onView(View view) {
                            ((TextView) view.findViewById(R.id.updateText)).setText(res.content);
                            view.findViewById(R.id.updateBtn).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ((CusButton) v).setText("正在下载...");
                                    startService(new Intent(getApplicationContext(), UpdateService.class).putExtra("url", res.url));
                                }
                            });
                        }
                    }).setOutsideClose(res.status != 1).setReturn(res.status == 1);
                    return;
                }
            }
            request("advert", AdvertData.class);
        } else if (info.getResponseObj() instanceof AdvertData) {
            AdvertData.Res res = ((AdvertData) info.getResponseObj()).res;
            if (res != null && !TextUtils.isEmpty(res.url)) {
                Glide.with(this).load(res.url).into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        splashImg.setImageDrawable(resource);
                        countDown(5).setView(splashBtn, "跳过", "s").goActivity(MainActivity.class).start();
                    }
                });
            } else {
                goHome();
            }
        }

    }

    @Override
    public void onFailure(NetInfo info) {
        super.onFailure(info);
        goHome();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    private void goHome() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
