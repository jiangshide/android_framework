package com.zd112.read.user.data;

import com.google.gson.annotations.SerializedName;
import com.zd112.framework.BaseData;

import java.io.Serializable;

public class ShareInfoData extends BaseData {
    @SerializedName("body")
    public Res res;

    public class Res implements Serializable {
        @SerializedName("imgUrl")
        public String imgUrl;
        @SerializedName("content")
        public String content;
        @SerializedName("title")
        public String title;
        @SerializedName("pageUrl")
        public String pageUrl;
    }

    @SerializedName("type")
    public String type;
    @SerializedName("scene")
    public int scene;
    @SerializedName("shareInfos")
    public Res shareInfos;
}
