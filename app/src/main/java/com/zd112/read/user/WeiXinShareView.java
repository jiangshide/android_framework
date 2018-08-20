package com.zd112.read.user;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zd112.framework.BaseActivity;
import com.zd112.framework.BaseData;
import com.zd112.framework.net.callback.Callback;
import com.zd112.framework.net.helper.NetInfo;
import com.zd112.framework.utils.ImageUtils;
import com.zd112.framework.utils.LogUtils;
import com.zd112.framework.utils.ValidateUtils;
import com.zd112.framework.view.DialogView;
import com.zd112.read.MainActivity;
import com.zd112.read.MyApplication;
import com.zd112.read.R;
import com.zd112.read.user.data.ShareInfoData;

import java.io.IOException;
import java.util.HashMap;

public class WeiXinShareView implements View.OnClickListener, Callback {

    private Activity activity;
    private ShareInfoData shareInfoData;
    public final static int INVITE_FRIENDS = 0;
    public final static int SHARE_FRIEND = 1;

    public WeiXinShareView(Activity activity) {
        this.activity = activity;
    }

    private void loadShareInfo() {
        HashMap<String, String> params = new HashMap<>();
        params.put("sessionId", AppSessionEngine.getUseId());
        params.put("useId", AppSessionEngine.getSessionId());
//        ((BaseActivity) activity).request("", params, ShareInfoData.class, this, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.moreShareInfoDel:
                ((BaseActivity) activity).cancelLoading();
                break;
            case R.id.moreShareInfoMsg:
//                Intent intent = new Intent();
//                intent.setClass(activity, ContactActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable(Constant.SHARE_INFO_DATA, shareInfoData);
//                intent.putExtras(bundle);
//                activity.startActivity(intent);
//                ((BaseActivity) activity).cancelLoading();
                break;
            case R.id.moreShareInfoWx:
                openWeiXin(INVITE_FRIENDS, shareInfoData.res.imgUrl, shareInfoData.res.pageUrl, shareInfoData.res.title, shareInfoData.res.content);
                ((BaseActivity) activity).cancelLoading();
                break;
            case R.id.moreShareInfoFriend:
                openWeiXin(SHARE_FRIEND, shareInfoData.res.imgUrl, shareInfoData.res.pageUrl, shareInfoData.res.title, shareInfoData.res.content);
                ((BaseActivity) activity).cancelLoading();
                break;
        }
    }

    @Override
    public void onSuccess(NetInfo info) throws IOException {
        Object object = info.getResponseObj();
        if (object != null) {
            if (object instanceof ShareInfoData) {
                shareInfoData = (ShareInfoData) object;
                if (activity != null) {
                    shareInfoData.res.pageUrl += "&nikename=" + AppSessionEngine.getMobile();
                    showShareInfoView(shareInfoData);
                }
            } else {
                LogUtils.e("msg:", ((BaseData) object).msg);
            }
        } else {
            LogUtils.e(activity.getString(R.string.net_failure));
        }
    }

    @Override
    public void onFailure(NetInfo info) throws IOException {

    }

    private void showShareInfoView(final ShareInfoData shareInfoData) {
        ((MainActivity) activity).loading(R.layout.dialog_shareinfo, new DialogView.DialogViewListener() {
            @Override
            public void onView(View view) {
                TextView moreShareInfoName = view.findViewById(R.id.moreShareInfoName);
                moreShareInfoName.setText(ValidateUtils.formatMobile(AppSessionEngine.getMobile()));
                view.findViewById(R.id.moreShareInfoDel).setOnClickListener(WeiXinShareView.this);
                ImageView moreShareInfoQrImg = view.findViewById(R.id.moreShareInfoQrImg);
                ImageUtils.showQRImg(activity, R.mipmap.ic_launcher, shareInfoData.res.pageUrl, moreShareInfoQrImg);
                view.findViewById(R.id.moreShareInfoMsg).setOnClickListener(WeiXinShareView.this);
                view.findViewById(R.id.moreShareInfoWx).setOnClickListener(WeiXinShareView.this);
                view.findViewById(R.id.moreShareInfoFriend).setOnClickListener(WeiXinShareView.this);
            }
        }).setGravity(Gravity.BOTTOM).setAnim(R.style.bottomAnim);
    }

    @SuppressLint("HandlerLeak")
    public void openWeiXin(final int scene, String imgUrl, final String pageUrl, final String title, final String content) {
        if (!MyApplication.application.isWeiXin(activity)) {
            ((BaseActivity) activity).loading(activity.getString(R.string.weixin_init)).setOnlySure();
            return;
        }
        new ImageUtils(imgUrl, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bitmap bitmap = null;
                switch (msg.what) {
                    case ImageUtils.SUCCESS:
                        bitmap = (Bitmap) msg.obj;
                        break;
                    case ImageUtils.FALSE:
                        LogUtils.e("img~false:");
                        break;
                }
                MyApplication.application.wxScene(scene, pageUrl, title, content, bitmap);
            }
        }).start();
    }
}
