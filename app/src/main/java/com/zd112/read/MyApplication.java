package com.zd112.read;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.zd112.framework.BaseApplication;
import com.zd112.framework.BaseData;
import com.zd112.framework.utils.LogUtils;
import com.zd112.read.user.WebActivity;

public class MyApplication extends BaseApplication {

    public static MyApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        init();
    }

    @Override
    public void newGlobalError(Context context, BaseData baseData) {
        LogUtils.e("newGlobalError:", baseData.msg);
    }

    public void webView(String url) {
        webView(null, url);
    }

    public void webView(String title, String url) {
        webView(title, url, 0);
    }

    public void webView(String title, String url, boolean isBack) {
        webView(title, url, 0, isBack);
    }

    public void webView(String title, String url, int source) {
        webView(title, url, source, true);
    }

    public void webView(String title, String url, int source, boolean isBack) {
        Intent intent = new Intent();
        if (!TextUtils.isEmpty(title)) {
            intent.putExtra("title", title);
        }
        intent.putExtra("url", url);
        intent.putExtra("source", source);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(this, WebActivity.class);
        startActivity(intent);
    }

    public void init() {

        /**
         * the jpush
         */
//        JPushInterface.setDebugMode(BuildConfig.DEBUG);    // 设置开启日志,发布时请关闭日志
//        JPushInterface.init(this);
//        etdApplication = this;
    }
}


