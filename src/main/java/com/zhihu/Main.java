package com.zhihu;

import com.other.Constant;
import com.zhihu.utils.ClipboardUtils;
import com.zhihu.utils.ZhihuUtils;

public class Main {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        zhihu("社会");

    }

    private static void zhihu(String postion) {
        String articleUrl = ClipboardUtils.getSysClipboardText();
        ZhihuUtils.saveArticle(Constant.DEF_SAVE_PATH + postion, articleUrl,
            (saveName, saveSuccess) -> {
//                WindowAlert.display("提示", saveName);
            });
//        ZhihuDownload.saveCollect("D:\\2020-10-21\\测试", "https://www.zhihu.com/question/20784865/answer/1544077523");
    }

}