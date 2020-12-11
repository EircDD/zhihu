package com.zhihu;

import com.other.Constant;
import com.zhihu.utils.ClipboardUtils;
import com.zhihu.utils.ZhihuUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import java.io.File;
import okhttp3.Call;

public class Main {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
//        zhihu("社会");
        System.out.println("args = " );
        OkHttpUtils.get().url("https://down.caimoge.org/new/1435/%E7%A5%9E%E5%A2%93[www.caimoge.org].txt").build()
            .buildCall(new FileCallBack("F:/cc/", "cc.txt") {
                @Override
                public void onError(Call call, Exception e, int i) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(File file, int i) {
                    System.out.println("file.getPath() = " + file.getPath());
                }
            });
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