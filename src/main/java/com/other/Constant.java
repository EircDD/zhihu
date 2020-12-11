package com.other;

import com.zhihu.utils.FileUtils;
import java.io.File;

public class Constant {

    /**
     * 配置文件路径 C:\Users\HP\MyZhiHuConfig
     */
    public static final String CONFIG_FILE_PATH =
        FileUtils.getSysPath("user.home") + "MyZhiHuConfig" + File.separator + "config.json";

    public static final String CONFIG_KEY_SAVE_PATH = "defSavePath";
    public static final String CONFIG_KEY_CATEGORY = "saveCategory";
    public static final String CONFIG_VALUE_CATEGORY = "文档;社会;历史人物传记;笑一笑;赚钱";

    /**
     * 默认保存路径
     */
    public static final String DEF_SAVE_PATH = "F:/知乎/";

    /**
     * 暂时禁用收藏链接
     */
    public static final String ENABLE_TXT_TFCOLLECTIONLINK = "暂时禁用收藏链接";


    public static boolean TAG_ADD_CATEGORY = true;


}
