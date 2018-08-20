package com.zd112.read.user.data;

import com.google.gson.annotations.SerializedName;
import com.zd112.framework.BaseData;

import java.io.Serializable;

public class ValidateCodeData extends BaseData {
    @SerializedName("body")
    public Res res;

    public class Res implements Serializable {
        @SerializedName("indentify")
        public String indentify;
        @SerializedName("uuid")
        public String uuid;
    }
}
