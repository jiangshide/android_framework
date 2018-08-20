package com.zd112.read.user;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.zd112.framework.BuildConfig;
import com.zd112.framework.net.annotation.DataType;
import com.zd112.framework.utils.LogUtils;
import com.zd112.framework.utils.ShareParamUtils;
import com.zd112.read.user.data.UserData;
import com.zd112.read.utils.Constant;

import java.util.HashMap;

public class AppSessionEngine {

    public static UserData getUserInfo() {
        String user = ShareParamUtils.getString(Constant.USER);
        if (user == null) {
            return null;
        }
        try {
            return new Gson().fromJson(user, UserData.class);
        } catch (Exception e) {
            LogUtils.e(e);
            return null;
        }
    }

    public static void setUser(UserData user) {
        if (user == null) return;
        try {
            String json = new Gson().toJson(user);
            if (!TextUtils.isEmpty(json)) {
                ShareParamUtils.INSTANCE.putString(json, Constant.USER);
            }
        } catch (Exception e) {
            LogUtils.e(e);
        }
    }

    public static String getUseId() {
        UserData userInfo = AppSessionEngine.getUserInfo();
        if (userInfo == null || userInfo.res == null || userInfo.res.sftUserMdl == null) {
            return null;
        }
        return userInfo.res.sftUserMdl.useId + "";
    }


    public static String getSessionId() {
        UserData userData = getUserInfo();
        return (null != userData && !TextUtils.isEmpty(userData.res.sessionId)) ? userData.res.sessionId : null;
    }

    public static String getMobile() {
        UserData userData = getUserInfo();
        return (null != userData && !TextUtils.isEmpty(userData.res.sftUserMdl.useMobilePhones)) ? userData.res.sftUserMdl.useMobilePhones : null;
    }

    public static String getUserLoginName() {
        UserData userData = getUserInfo();
        return (null != userData && !TextUtils.isEmpty(userData.res.sftUserMdl.useLoginName)) ? userData.res.sftUserMdl.useLoginName : null;
    }

    public static String getUserName() {
        UserData userData = getUserInfo();
        return (null != userData && !TextUtils.isEmpty(userData.res.sftUserMdl.useName)) ? userData.res.sftUserMdl.useName : null;
    }

    public static String getToken() {
        UserData userData = getUserInfo();
        return (null != userData && !TextUtils.isEmpty(userData.res.token)) ? userData.res.token : null;
    }

    public static void clear() {
        clear(null);
    }

    public static void clear(HashMap<String, Integer> hashMap) {
        if (hashMap == null) {
            hashMap = new HashMap<>();
        }
        hashMap.put(Constant.GUIDE, DataType.typeBool);
        hashMap.put(Constant.GUIDE_HOME, DataType.typeBool);
        hashMap.put(Constant.GUIDE_LEND, DataType.typeBool);
        hashMap.put(Constant.GUIDE_ACCOUNT, DataType.typeBool);
        hashMap.put(Constant.USERNAME, DataType.typeString);
        hashMap.put(BuildConfig.NO_First, DataType.typeBool);
        ShareParamUtils.saveClear(hashMap);
    }
}
