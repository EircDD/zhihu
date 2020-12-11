package com.other;

import com.zhihu.utils.FileUtils;
import java.io.File;

public class Constant {

    /**
     * 配置文件路径 C:\Users\HP\MyZhiHuConfig
     */
    public static final String CONFIG_FILE_PATH =
        FileUtils.getSysPath("user.home") + "MyZhiHuConfig" + File.separator + "config.json";

    /**
     * 默认保存路径
     */
    public static final String DEF_SAVE_PATH = "F:/知乎/";

    /**
     * 暂时禁用收藏链接
     */
    public static final String ENABLE_TXT_TFCOLLECTIONLINK = "暂时禁用收藏链接";

    public static final String CONFIG_KEY_SAVE_PATH = "defSavePath";
    public static final String CONFIG_KEY_CATEGORY = "";
}
