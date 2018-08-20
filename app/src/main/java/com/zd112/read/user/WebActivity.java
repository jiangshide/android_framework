package com.zd112.read.user;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.zd112.framework.BaseActivity;
import com.zd112.framework.utils.ImageUtils;
import com.zd112.framework.utils.LogUtils;
import com.zd112.framework.utils.SystemUtils;
import com.zd112.framework.utils.ViewUtils.ViewInject;
import com.zd112.framework.view.NavigationTopView;
import com.zd112.framework.view.jsbridge.BridgeWebView;
import com.zd112.read.MainActivity;
import com.zd112.read.MyApplication;
import com.zd112.read.R;
import com.zd112.read.user.data.ShareInfoData;
import com.zd112.read.user.data.UserTokenData;
import com.zd112.read.utils.Constant;

import java.io.File;
import java.io.IOException;

public class WebActivity extends BaseActivity {

    @ViewInject(R.id.topView)
    private NavigationTopView topView;
    @ViewInject(R.id.webViewProgressBar)
    private ProgressBar webViewProgressBar;
    @ViewInject(R.id.webView)
    private BridgeWebView webView;

    private int RESULT_CODE = 0;
    private ValueCallback<Uri> mUploadMessage;
    private int source;
    public String mCameraPhotoPath;
    public ValueCallback<Uri[]> mFilePathCallback;
    private final static int INPUT_FILE_REQUEST_CODE = 1;
    private final static int FILECHOOSER_RESULTCODE = 2;

    private final String BRIDGE_NAME = "etongdai";
    private String jsReg = "window.originalPostMessage = window.postMessage," +
            "window.postMessage = function(data) { console.log('postmessage');" +
            BRIDGE_NAME + ".postMessage(String(data));console.log('postmessage11');" +
            "}";

    @Override
    protected void initView(Bundle savedInstanceState) {
        setView(R.layout.activity_web, this);
        source = getIntent().getIntExtra(Constant.SOURCE, 0);
        String title = getIntent().getStringExtra("title");
        topView.setVisibility(!TextUtils.isEmpty(title) ? View.VISIBLE : View.GONE);
        topView.setTitle(title);
    }

    @Override
    protected void setListener() {
        topView.setOnLeftClick(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void processLogic(Bundle savedInstanceState) {
        init();
    }

    class JsMessage {
        @JavascriptInterface
        public void postMessage(String message) {
            LogUtils.e("postMessage:" + message);
            if (!TextUtils.isEmpty(message)) {
                if (message.equals("toInvest")) {
                    ((MyApplication) getApplication()).cleanJump(MainActivity.class, Constant.TAB_BOOK);
                } else if (message.equals("toLogin")) {//跳转登录页面
                    startActivity(new Intent(WebActivity.this, LoginActivity.class));
                } else if (message.equals("toRegister")) {//跳转注册页面
                    startActivity(new Intent(WebActivity.this, RegActivity.class));
                } else if (message.equals("toRedPacket")) {//跳转红包页面
                    startActivity(new Intent(WebActivity.this, RegActivity.class));
                } else if (message.equals("toRealNameAccount")) {//跳转xxx
                    ((MyApplication) getApplication()).cleanJump(MainActivity.class, Constant.TAB_FIND);
                } else if (message.contains(":")) {//邀请朋友
                    try {
                        ShareInfoData shareInfoData = new Gson().fromJson(message, ShareInfoData.class);
                        if (shareInfoData != null && shareInfoData.shareInfos != null) {
                            WeiXinShareView weiXinShareView = new WeiXinShareView(WebActivity.this);
                            if (shareInfoData.type.equals("inviteFriends")) {
                                weiXinShareView.openWeiXin(WeiXinShareView.INVITE_FRIENDS, shareInfoData.shareInfos.imgUrl, shareInfoData.shareInfos.pageUrl, shareInfoData.shareInfos.title, shareInfoData.shareInfos.content);
                            } else if (shareInfoData.type.equals("unLoginInFr")) {
                                weiXinShareView.openWeiXin(WeiXinShareView.SHARE_FRIEND, shareInfoData.shareInfos.imgUrl, shareInfoData.shareInfos.pageUrl, shareInfoData.shareInfos.title, shareInfoData.shareInfos.content);
                            } else {
                                loading(shareInfoData.type).setOnlySure();
                            }
                        } else {
                            loading("获取数据为空！").setOnlySure();
                        }
                    } catch (Exception e) {
                        loading("数据解析失败！").setOnlySure();
                    }
                } else {
//                    loading(message).setOnlySure();
                    LogUtils.e("err:", message);
                }
            }
        }
    }

    @SuppressLint("JavascriptInterface")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void init() {
        final String url = getIntent().getStringExtra("url");
        LogUtils.e("url:", url);

        webView.setVerticalScrollbarOverlay(true);

        WebSettings settings = webView.getSettings();
        settings.setDefaultFontSize(12);
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);
        settings.setSupportMultipleWindows(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(false);
        settings.setAllowUniversalAccessFromFileURLs(false);
        settings.setUserAgentString(webView.getSettings().getUserAgentString() + " etongdaiapp/" + SystemUtils.getAppVersion(this));
        loadJs();
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtils.e("url:", url);
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadJs();
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType, String capture) {
                this.openFileChooser(uploadMsg);
            }

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType) {
                this.openFileChooser(uploadMsg);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                pickFile();
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                webViewProgressBar.setVisibility(View.VISIBLE);
                webViewProgressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    webViewProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                LogUtils.e("consoleMessage:", consoleMessage.message(), " | line:", consoleMessage.lineNumber());
                return super.onConsoleMessage(consoleMessage);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                LogUtils.e("url:", url, " | message:", message, " | result:", result);
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                LogUtils.e("url:", url, " | message:", message, " | defaultValue:", defaultValue, " | result:", result);
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                LogUtils.e("title:", title);
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                if (mFilePathCallback != null) {
                    mFilePathCallback.onReceiveValue(null);
                }
                mFilePathCallback = filePathCallback;

                /**
                 Open Declaration   String android.provider.MediaStore.ACTION_IMAGE_CAPTURE = "android.media.action.IMAGE_CAPTURE"
                 Standard Intent action that can be sent to have the camera application capture an image and return it.
                 The caller may pass an extra EXTRA_OUTPUT to control where this image will be written. If the EXTRA_OUTPUT is not present, then a small sized image is returned as a Bitmap object in the extra field. This is useful for applications that only need a small image. If the EXTRA_OUTPUT is present, then the full-sized image will be written to the Uri value of EXTRA_OUTPUT. As of android.os.Build.VERSION_CODES.LOLLIPOP, this uri can also be supplied through android.content.Intent.setClipData(ClipData). If using this approach, you still must supply the uri through the EXTRA_OUTPUT field for compatibility with old applications. If you don't set a ClipData, it will be copied there for you when calling Context.startActivity(Intent).
                 See Also:EXTRA_OUTPUT
                 标准意图，被发送到相机应用程序捕获一个图像，并返回它。通过一个额外的extra_output控制这个图像将被写入。如果extra_output是不存在的，
                 那么一个小尺寸的图像作为位图对象返回。如果extra_output是存在的，那么全尺寸的图像将被写入extra_output URI值。
                 */
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        //设置MediaStore.EXTRA_OUTPUT路径,相机拍照写入的全路径
                        photoFile = createImageFile();
                        takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
                    } catch (Exception ex) {
                        // Error occurred while creating the File
                        Log.e("WebViewSetting", "Unable to create Image File", ex);
                    }

                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile));
                        System.out.println(mCameraPhotoPath);
                    } else {
                        takePictureIntent = null;
                    }
                }

                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.setType("image/*");
                Intent[] intentArray;
                if (takePictureIntent != null) {
                    intentArray = new Intent[]{takePictureIntent};
                    System.out.println(takePictureIntent);
                } else {
                    intentArray = new Intent[0];
                }

                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "选择图片");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);
                return true;
            }
        });
        webView.loadUrl(url);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void loadJs() {
        UserTokenData userTokenData = new UserTokenData();
        String msg = null;
        String useId = AppSessionEngine.getUseId();
        if (!TextUtils.isEmpty(useId)) {
            userTokenData.token = AppSessionEngine.getToken();
            userTokenData.useId = useId;
            userTokenData.userId = userTokenData.useId;
            userTokenData.phone = AppSessionEngine.getMobile();
            String usetInfoJson = new Gson().toJson(userTokenData);
            msg = "rn_userInfo=" + usetInfoJson;
        }
        webView.addJavascriptInterface(new JsMessage(), BRIDGE_NAME);
        LogUtils.e("msg:", msg);
        if (!TextUtils.isEmpty(msg)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                webView.evaluateJavascript(msg, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        LogUtils.e("value:", value);
                    }
                });
            } else {
                webView.loadUrl("javascript:" + msg);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript(jsReg, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    LogUtils.e("value:", value);
                }
            });
            /**
             * 注入声明对象
             */
            webView.evaluateJavascript("window.WebViewBridge={}", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    LogUtils.e("value:", value);
                }
            });
            webView.evaluateJavascript("window.WebViewBridge.useId=" + useId, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    LogUtils.e("value:", value);
                }
            });
        } else {
            webView.loadUrl("javascript:" + jsReg);
            webView.loadUrl("javascript:" + "window.WebViewBridge={}");
            webView.loadUrl("javascript:" + "window.WebViewBridge.useId=" + useId);
        }
    }

    public void pickFile() {
        Intent chooserIntent = new Intent(Intent.ACTION_GET_CONTENT);
        chooserIntent.setType("image/*");
        startActivityForResult(chooserIntent, RESULT_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage) {
                return;
            }
            Uri result = data == null || resultCode != RESULT_OK ? null
                    : data.getData();
            if (result != null) {
                String imagePath = ImageUtils.getPath(this, result);
                if (!TextUtils.isEmpty(imagePath)) {
                    result = Uri.parse("file:///" + imagePath);
                }
            }
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        } else if (requestCode == INPUT_FILE_REQUEST_CODE && mFilePathCallback != null) {
            // 5.0的回调
            Uri[] results = null;

            // Check that the response is a good one
            if (resultCode == Activity.RESULT_OK) {
                if (data == null) {
                    // If there is not data, then we may have taken a photo
                    if (mCameraPhotoPath != null) {
                        LogUtils.d("camera_photo_path", mCameraPhotoPath);
                        results = new Uri[]{Uri.parse(mCameraPhotoPath)};
                    }
                } else {
                    String dataString = data.getDataString();
                    LogUtils.d("camera_dataString", dataString);
                    if (dataString != null) {
                        results = new Uri[]{Uri.parse(dataString)};
                    }
                }
            }

            mFilePathCallback.onReceiveValue(results);
            mFilePathCallback = null;
        } else {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case NavigationTopView.leftId:
                canBack();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            canBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void canBack() {
        if (webView.canGoBack()) {
            webView.goBack();
            return;
        }
        if (source == Constant.TAB_BOOK) {
            MyApplication.application.cleanJump(MainActivity.class, Constant.TAB_HOME);
        } else {
            finish();
        }
    }

    @SuppressLint("SdCardPath")
    private File createImageFile() {
        //mCameraPhotoPath="/mnt/sdcard/tmp.png";
        File file = new File(Environment.getExternalStorageDirectory() + "/", "tmp.png");
        mCameraPhotoPath = file.getAbsolutePath();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
