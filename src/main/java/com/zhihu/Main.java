package com.zhihu;

import com.zhihu.utils.ZhihuUtils;

public class Main {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        m1();
//        m2();
    }

    private static void m2() {
        ZhihuUtils.saveArticle("D:/test/", "有什么你想匿名说出的秘密？ - 知乎\n"
                + "https://www.zhihu.com/question/375882427/answer/1545417627",
            (saveName, saveSuccess) -> {
//                WindowAlert.display("提示", saveName);
            });
//        ZhihuDownload.saveCollect("D:\\2020-10-21\\测试", "https://www.zhihu.com/question/20784865/answer/1544077523");
    }

    private static void m1() {

    }
}