package com.bilibili;

import com.alibaba.fastjson.JSONObject;
import com.bilibili.BilibiUserEnt.DataBean.ListBean.VlistBean;
import com.zhy.http.okhttp.OkHttpUtils;
import java.io.IOException;
import java.util.List;

public class VideoInfo {  // 真实项目中不推荐直接使用`public`哦😯

    public String videoName;
    public JSONObject videoInfo;
    public String videoBaseUrl;
    public String audioBaseUrl;
    public String videoBaseRange;
    public String audioBaseRange;
    public String videoSize;
    public String audioSize;
}