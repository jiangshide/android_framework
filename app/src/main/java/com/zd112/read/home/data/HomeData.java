package com.zd112.read.home.data;

import com.google.gson.annotations.SerializedName;
import com.zd112.framework.BaseData;

import java.io.Serializable;
import java.util.List;

public class HomeData extends BaseData {

    @SerializedName("res")
    public List<Res> res;

    public class Res implements Serializable {
        @SerializedName("Id")
        public int Id;
        @SerializedName("Name")
        public String Name;
        @SerializedName("Pkg")
        public String Pkg;
        @SerializedName("FriendId")
        public String FriendId;
        @SerializedName("Channel")
        public String Channel;
        @SerializedName("Status")
        public int Status;
    }
}
