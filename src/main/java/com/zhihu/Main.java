package com.zhihu;

import com.zhihu.utils.ZhihuUtils;

public class Main {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
//        m1();
        m2();
    }

    private static void m2() {
        ZhihuUtils.saveArticle("F:\\知乎\\文档", "营销策划的大咖们每天会浏览哪些网站？ - 詹伟平的回答 - 知乎\n"
                + "https://www.zhihu.com/question/36457936/answer/1441316644",
            (saveName, saveSuccess) -> {
//                WindowAlert.display("提示", saveName);
            });
//        ZhihuDownload.saveCollect("D:\\2020-10-21\\测试", "https://www.zhihu.com/question/20784865/answer/1544077523");
    }

    private static void m1() {

    }
}