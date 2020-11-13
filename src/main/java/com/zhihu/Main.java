package com.zhihu;

import com.zhihu.utils.ClipboardUtils;
import com.zhihu.utils.FileUtils;
import com.zhihu.utils.ZhihuUtils;

public class Main {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
//        m1();
        m2("赚钱");
    }

    private static void m2(String postion ) {
        String articleUrl = ClipboardUtils.getSysClipboardText();
        ZhihuUtils.saveArticle("F:\\知乎\\" + postion, articleUrl,
            (saveName, saveSuccess) -> {
//                WindowAlert.display("提示", saveName);
            });
//        ZhihuDownload.saveCollect("D:\\2020-10-21\\测试", "https://www.zhihu.com/question/20784865/answer/1544077523");
    }

    private static void m1() {
        FileUtils.readTextFileLines(
            "I:\\Android\\gxt\\app\\src\\main\\java\\com\\hebky\\oa\\iit\\activity\\CertApplyActivity.java",
            linStr -> {
                if (linStr.contains("allWebService.callMethod")) {
                    System.out.println("linStr = " + linStr);
                }
            });
    }
}