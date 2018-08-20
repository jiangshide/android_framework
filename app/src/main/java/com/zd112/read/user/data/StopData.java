package com.zd112.read.user.data;

import com.google.gson.annotations.SerializedName;
import com.zd112.framework.BaseData;

import java.io.Serializable;

public class StopData extends BaseData {
    @SerializedName("res")
    public Res res;

    public class Res implements Serializable {
        @SerializedName("Url")
        public String url;
    }
}
