package com.zhihu.utils;

import com.google.gson.Gson;
import com.zhihu.other.BaseUrl;
import com.zhihu.other.CollectionEnt;
import com.zhihu.other.CollectionEnt.DataBean;
import com.zhihu.other.CommUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import java.io.File;
import java.io.IOException;
import java.util.List;
import okhttp3.Call;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

public class ZhihuUtils {

    private static String okhttpTag = "zhihuTag";

    public static void main(String[] args) {
        // TODO Auto-generated method stub
    }


    /**
     * 保存知乎文章到本地
     *
     * @param filePath 保存路径
     * @param articleUrl 文章url
     */
    public static void saveArticle(String filePath, String articleUrl, SaveCallback saveCallback) {
        if (!articleUrl.startsWith("http") || !articleUrl.startsWith("wwww.")) {
            articleUrl = CommUtils.getUrl(articleUrl);
        }
        FileUtils.makeDirs(filePath);
        boolean saveSuccess = false;
        String htmlTitle = "";
        try {
            String response = OkHttpUtils.get().url(articleUrl).tag(okhttpTag)
                .build().execute().body().string();
            //1.获取标题
            htmlTitle = FileUtils.getConversionFileName(CommUtils.getHtmlTitle(response))
                + DateUtil.getTimeStamp();
            //2.去除循环请求
            String html = CommUtils.removeLoopRequest(response);
            html = CommUtils.removeOther(html);
            //3.解决图片不显示问题
            html = CommUtils.replaceHtmlPicSrc(html);
            //4.格式化html输出
            html = CommUtils.formatHtml(html);
            saveSuccess = FileUtils.writeFile(
                filePath + File.separator + htmlTitle + ".html", html);
            if (saveSuccess) {
                System.out.println("保存成功");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        saveCallback.callback("保存" + (saveSuccess ? "成功" : "失败"), saveSuccess);
    }

    /**
     * 保存知乎收藏到本地
     *
     * 使用: saveCollect("D:\\2020-10-21\\文档", BaseUrl.getCollectionUrl("162366996"));
     *
     * @param filePath 保存路径
     * @param collectionUrl 收藏url
     */
    public static void saveCollect(String filePath, String collectionUrl) {
        if (!collectionUrl.startsWith("https://www.") || !collectionUrl.startsWith("www.")) {
            collectionUrl = BaseUrl.getCollectionUrl(collectionUrl);
        }
        FileUtils.makeDirs(filePath);
        OkHttpUtils.get().url(collectionUrl).tag(okhttpTag)
            .build().execute(new Callback<CollectionEnt>() {
            @Override
            public CollectionEnt parseNetworkResponse(Response response, int i) throws Exception {
                //收藏
                if (response.body() != null) {
                    String string = response.body().string();
                    return new Gson().fromJson(string, CollectionEnt.class);
                }
                return null;
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                e.printStackTrace();
                call.cancel();
                XLog.file("异常了" + e.getMessage());
            }

            @Override
            public void onResponse(CollectionEnt collectionEnt, int i) {
                //如果不是结尾页面就进行循环请求
                List<DataBean> data = collectionEnt.getData();
                for (DataBean dataBean : data) {
                    String ttitle;
                    if (StringUtils.isBlank((ttitle = dataBean.getContent().gettitle()))) {
                        ttitle = dataBean.getContent().getQuestion().getTitle();
                        if (StringUtils.isBlank(ttitle)) {
                            XLog.showLogArgs("标题为空:  " + dataBean.getContent().toString());
                            return;
                        }
                    }
                    ttitle = FileUtils.getConversionFileName(ttitle) + DateUtil.getTimeStamp();
                    String htmlContent = CommUtils.formatHtml(dataBean.getContent().getContent());
                    boolean saveSuccess = FileUtils
                        .writeFile(filePath + File.separator + ttitle + ".html",
                            ttitle + "\n\n\n" + htmlContent);
                    if (saveSuccess) {
                        System.out.println("保存成功:" + ttitle);
                    }
                }

                if (collectionEnt.getPaging().isIs_end()) {
                    OkHttpUtils.getInstance().cancelTag(okhttpTag);
                    return;
                }
                //循环请求下一页
                saveCollect(filePath, collectionEnt.getPaging().getNext());
            }
        });
    }

    public interface SaveCallback {

        void callback(String saveName, boolean saveSuccess);
    }

}