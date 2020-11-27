package com.bilibili;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bilibili.BilibiUserEnt.DataBean.ListBean.VlistBean;
import com.zhihu.utils.DateUtil;
import com.zhihu.utils.FileUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.request.RequestCall;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okhttp3.Call;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class BliBli {

    private static String savePath = "D:/banfo/";
    private static String USER_AGENT = " Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.183 Safari/537.36 Edg/86.0.622.63";

    public static List<VlistBean> QueryPagings(int pageIndex, int pageStart, int pageEnd) {
        List<VlistBean> queryVlists = new ArrayList<>();
        String userUrl =
            "https://api.bilibili.com/x/space/arc/search?mid=37663924&ps=30&tid=0&pn=" + pageIndex
                + "&keyword=&order=pubdate&jsonp=jsonp";
        try {
            String response = OkHttpUtils.get().url(userUrl).build().execute().body().string();
            BilibiUserEnt bilibiUserEnt = JSONObject.parseObject(response, BilibiUserEnt.class);
            List<VlistBean> vlist = bilibiUserEnt.getData().getList().getVlist();
            for (int i = 0, size = vlist.size(); i < size; i++) {
                String url = null;
                if (pageEnd == 0) {
                    //下载本页所有个视频
                    url = "https://www.bilibili.com/video/" + vlist.get(i).getBvid();
                } else if (i >= pageStart && i < pageEnd) {
                    //下载范围内视频
                    url = "https://www.bilibili.com/video/" + vlist.get(i).getBvid();
                } else if (pageStart == pageEnd && i < pageStart) {
                    //下载单独一个视频
                    url = "https://www.bilibili.com/video/" + vlist.get(i).getBvid();
                } else {
                    continue;
                }
                vlist.get(i).setPlayUrl(url);
                queryVlists.add(vlist.get(i));
                return queryVlists;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return queryVlists;
    }

    /**
     * 下载音频文件
     */
    public static void downloadAudio(List<VlistBean> vlistBeans) {
        for (int i = 0; i < vlistBeans.size(); i++) {
            vlistBeans.get(i).getPlayUrl();
            //1.解析获取音频信息
            //2.下载音频

        }
    }

    public static void downloadAudio(String playUrl) {

        try {
            GetBuilder getBuilder = OkHttpUtils.get();
            getBuilder.addHeader("accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
            getBuilder.addHeader("cache-control", "max-age=0");
            getBuilder.addHeader("cookie",
                "finger=158939783; _uuid=9A988F39-CF82-BD39-5D46-F3A51C04197185541infoc; buvid3=FD5A4E4D-8D8D-4817-B62F-531455D72D8D143090infoc; CURRENT_FNVAL=80; blackside_state=1; rpdid=|(J~Jmu|kmRm0J'uY|uum~m~u; DedeUserID=322724573; DedeUserID__ckMd5=0536924c40043d95; SESSDATA=2fa88cd4%2C1617847203%2Cb217e*a1; bili_jct=c2ab889637abdc97e149240b13503474; PVID=1; sid=khc9ymgh; bsource=search_google");
            getBuilder.addHeader("user-agent", USER_AGENT);
            RequestCall build1 = getBuilder.url(playUrl).build();
            String html = build1.execute().body().string();
            build1.cancel();
            Document document = Jsoup.parse(html);
            String title = document.getElementsByTag("title").first().text();
//            System.out.println("获取到的数据:" + html);
            //            // 截取视频信息<script>window.__playinfo__=
            Pattern pattern = Pattern.compile("(?<=<script>window.__playinfo__=).*?(?=</script>)");
            Matcher matcher = pattern.matcher(html);
            if (matcher.find()) {
                String group = matcher.group();
//                System.out.println("group = " + group);
                JSONArray audioInfoArr = JSONObject.parseObject(group).getJSONObject("data")
                    .getJSONObject("dash").getJSONArray("audio");
                String audioBaseUrl = audioInfoArr.getJSONObject(0).getString("baseUrl");
                String audioBaseRange = audioInfoArr.getJSONObject(0).getJSONObject("SegmentBase")
                    .getString("Initialization");

                Map<String, String> htmlParsers = new HashMap<>();
                htmlParsers.put("Referer", playUrl);
                htmlParsers.put("Range",
                    "bytes=0-" + audioSize(getBuilder, playUrl, audioBaseUrl, audioBaseRange));
//                htmlParsers.put("Range", "bytes=" + audioSize(getBuilder,playUrl,audioBaseUrl,audioBaseRange));
                htmlParsers.put("User-Agent", USER_AGENT);
                RequestCall build2 = getBuilder.url(audioBaseUrl).headers(htmlParsers).build();
                build2.execute(
                    new FileCallBack(savePath, title + ".mp3") {
                        @Override
                        public void onError(Call call, Exception e, int i) {
                            e.printStackTrace();
                            build2.cancel();
                            FileUtils
                                .writeFile(savePath + "cc.txt", playUrl + "   " + title + "\n");
                        }

                        @Override
                        public void onResponse(File file, int i) {
                            build2.cancel();
                            System.out.println("下载成功:" + file.getAbsolutePath());
                        }
                    });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String audioSize(GetBuilder getBuilder,
        String playUrl, String audioBaseUrl, String audioBaseRange) {
        Response response = null;
        try {
            RequestCall build = getBuilder.url(audioBaseUrl).build();
            response = build.execute().networkResponse();
            build.cancel();
            return response.header("Content-Length");
        } catch (IOException e) {
            e.printStackTrace();
            HttpResponse audioRes = HttpRequest.get(audioBaseUrl)
                .header("Referer", playUrl)
                .header("Range", "bytes=0-" + audioBaseRange)
                .header("User-Agent", USER_AGENT)
                .timeout(2000)
                .execute();
            System.out
                .println("获取到的数据:audioRes.headers().toString() " + audioRes.headers().toString());
            return audioRes.header("Content-Range").split("/")[1];
        }
    }

    /**
     * @param pageIndex 索引页
     * @return 某一页的数据
     */
    public static List<VlistBean> QueryPaging(int pageIndex) {
        String userUrl =
            "https://api.bilibili.com/x/space/arc/search?mid=37663924&ps=30&tid=0&pn=" + pageIndex
                + "&keyword=&order=pubdate&jsonp=jsonp";
        try {
            String response = OkHttpUtils.get().url(userUrl).build().execute().body().string();
//            System.out.println("返回结果集: " +response);
            BilibiUserEnt bilibiUserEnt = JSONObject.parseObject(response, BilibiUserEnt.class);
            return bilibiUserEnt.getData().getList().getVlist();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @param pageIndex 请求页面索引
     */
    private static void renameFile(int pageIndex) {
        List<VlistBean> vlistBeans = BliBli.QueryPaging(pageIndex);
        for (int i = 0; i < vlistBeans.size(); i++) {
//            System.out.println("i = " + vlistBeans.get(i).getTitle());
            String filePath =
                "D:\\banfo1\\" + pageIndex + "\\" + vlistBeans.get(i).getTitle() + ".mp4";
            if (FileUtils.exists(filePath)) {
                FileUtils.renameFile(filePath,
                    DateUtil.timeStamp2Date(vlistBeans.get(i).getCreated() + "", "yyMMddHHmm") + "_"
                        + FileUtils.getFileName(filePath));
//                DateUtil.timeStamp2Date(vlistBeans.get(i).getCreated()+"", "yyMMddHHmm");
            } else {
                System.out.println("文件不存在:" + vlistBeans.get(i).getTitle());
            }
        }
    }
}