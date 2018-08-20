package com.zd112.read.user.data;

import com.google.gson.annotations.SerializedName;
import com.zd112.framework.BaseData;

import java.io.Serializable;

public class AdvertData extends BaseData {

    @SerializedName("res")
    public Res res;

    public class Res implements Serializable {
        @SerializedName("Name")
        public String name;
        @SerializedName("Url")
        public String url;
    }
}
