package com.zhihu;

import com.panel.ZhihuDownload;
import com.panel.custom.WindowAlert;
import com.zhihu.utils.ZhihuUtils;

public class Main {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
//        m1();
        m2();
    }

    private static void m2() {
        ZhihuUtils.saveArticle("D:/test/", "https://www.zhihu.com/question/35124114/answer/1535412316",
            (saveName, saveSuccess) -> {
//                WindowAlert.display("提示", saveName);
            });
//        ZhihuDownload.saveCollect("D:\\2020-10-21\\测试", "https://www.zhihu.com/question/20784865/answer/1544077523");
    }

    private static void m1() {

    }
}