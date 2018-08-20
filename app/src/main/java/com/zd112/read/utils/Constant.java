package com.zd112.read.utils;

import android.Manifest;
import android.text.TextUtils;

import com.zd112.framework.utils.EncryptUtils;

public class Constant {

    public static String[] PERMISSIONS = new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS};

    public static final int TAB_HOME = 0;
    public static final int TAB_BOOK = 1;
    public static final int TAB_TOP = 11;
    public static final int TAB_FIND = 2;

    public static String SOURCE = "source";

    public static String POSITION_WORDS = "positionWords";

    /**
     * SharePreference with params
     */
    public static String GUIDE = "guide";
    public static String GUIDE_HOME = "guideHome";
    public static String GUIDE_LEND = "guideLend";
    public static String GUIDE_ACCOUNT = "guideAccount";

    public static String USERNAME = "username";
    public static String UUID = "uuid";
    public static String IDENTIFY = "identify";
    public static String USER = "user";
    public static String GESTURE_PSW = "gesturePsw";
    public static String GESTURE_PSW_STATE = "gesturePswState";

    public static String OPEN_SCREE_AD = "openScreeAd";

    public static String getEncryptPsw(String psw) {
        if (!TextUtils.isEmpty(psw)) {
            psw = EncryptUtils.encryptMd5(psw + "s^H8").toUpperCase();
            psw = EncryptUtils.encryptMd5(psw.substring(0, 5) + psw).toUpperCase();
        }
        return psw;
    }

    public static long SPLASH_TIME = 5;
    public static long COUNT_DOWN = 60;
}
