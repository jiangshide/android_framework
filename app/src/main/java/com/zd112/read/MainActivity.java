package com.zd112.read;

import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.View;

import com.zd112.framework.BaseActivity;
import com.zd112.framework.net.callback.ProgressCallback;
import com.zd112.framework.net.helper.NetInfo;
import com.zd112.framework.utils.LogUtils;
import com.zd112.framework.utils.SystemUtils;
import com.zd112.read.book.BookFragment;
import com.zd112.read.find.FindFragment;
import com.zd112.read.home.HomeFragment;
import com.zd112.read.top.TopFragment;
import com.zd112.read.utils.Constant;

public class MainActivity extends BaseActivity {

    private Fragment[] fragments = {new HomeFragment(), new BookFragment(), new TopFragment(), new FindFragment(), new FindFragment()};
    private int id = 0;

    @Override
    protected void initView(Bundle savedInstanceState) {
        id = getIntent().getIntExtra(Constant.SOURCE, 0);
        setView(navigationBar.initView(id, new int[]{R.mipmap.tab_home, R.mipmap.tab_project, R.mipmap.ic_launcher_round, R.mipmap.tab_mine, R.mipmap.tab_more}, new int[]{R.mipmap.tab_home_selected, R.mipmap.tab_project_selected, R.mipmap.ic_launcher_round, R.mipmap.tab_mine_selected, R.mipmap.tab_more_selected}, getResArrStr(R.array.tab_main_title), R.color.font_gray, R.color.colorPrimary, this).setBgColor(getResColor(R.color.app_bg)), this);
        navigationBar.setIcon(R.mipmap.ic_launcher_round);
        navigationBar.showPort(1, 100);
    }

    @Override
    protected void setListener() {
        super.setListener();
        final NotificationManager notificationManager = SystemUtils.notificationManager(this);
        final NotificationCompat.Builder build = SystemUtils.notificationBuild(this, R.mipmap.ic_launcher_round, SystemUtils.getAppName(this) + "下载更新", "正在下载");
        download("http://10.20.6.50:8089/static/app/apkUrl/etongdai-jsd-android-yingxiao1001-v3.0.0.19-3020-1533198937-stage1-release.apk", new ProgressCallback() {
            @Override
            public void onResponseMain(String filePath, NetInfo info) {
                super.onResponseMain(filePath, info);
                String apkPath = info.getRetDetail();
                LogUtils.e("apkPath:", apkPath);
                build.setProgress(0, 0, false).setContentText("已经下载完成,正在安装...");
                notificationManager.notify(SystemUtils.notificationId, build.build());
                notificationManager.cancel(SystemUtils.notificationId);
                SystemUtils.installApkFile(MainActivity.this, apkPath);
            }

            @Override
            public void onProgressAsync(int percent, long bytesWritten, long contentLength, boolean done) {
                super.onProgressAsync(percent, bytesWritten, contentLength, done);
                LogUtils.e("---------percent:", percent, " | contentLength:", contentLength, " | done:", done);
                build.setProgress(100, percent, false);
                notificationManager.notify(SystemUtils.notificationId, build.build());
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        push(fragments[id]);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        int getId = goLogin(v.getId());
        if (getId >= 0) {
            push(fragments[v.getId()]);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v, Bundle bundle) {
        int getId = goLogin(v.getId());
        if (getId >= 0) {
            push(fragments[v.getId()], bundle);
        }
    }

    private int goLogin(int id) {
//        String userId = AppSessionEngine.getUseId();
//        if (TextUtils.isEmpty(userId) && id == 2) {
//            startActivity(new Intent(this, LoginActivity.class));
//            return -1;
//        }
        navigationBar.changeBarStatus(id);
        return id;
    }
}
