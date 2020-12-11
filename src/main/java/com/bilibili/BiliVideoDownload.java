package com.bilibili;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okhttp3.Call;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * https://blog.csdn.net/XiumingLee/article/details/106014889
 *
 * @author Xiuming Lee
 * @description
 */
public class BiliVideoDownload {

    /**
     * FFMPEG位置 `windows电脑下是ffmpeg.exe`
     */
    private static String FFMPEG_PATH = "E:\\ProgramFiles\\ffmpeg";

    private static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36";

    private static VideoInfo VIDEO_INFO = new VideoInfo();
    private static String SAVE_PATH;

    public static void main(String[] args) {
        /* 视频地址 */
        String VIDEO_URL = "http://www.bilibili.com/video/BV1uy4y1S7bN";
        htmlParser(VIDEO_URL);
    }


    /**
     * 解析HTML获取相关信息
     */
    private static void htmlParser(String videoUrl) {
        HttpResponse res = HttpRequest.get(videoUrl).timeout(2000).execute();
        String html = res.body();
        System.out.println("获取到的数据:" + html);
        Document document = Jsoup.parse(html);
        Element title = document.getElementsByTag("title").first();
        // 视频名称
        VIDEO_INFO.videoName = title.text();
        // 截取视频信息<script>window.__playinfo__=
        Pattern pattern = Pattern.compile("(?<=<script>window.__playinfo__=).*?(?=</script>)");
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            String group = matcher.group();
//            System.out.println("group = " + group);
            VIDEO_INFO.videoInfo = JSONObject.parseObject(group);
        } else {
            System.err.println("未匹配到视频信息，退出程序！");
            return;
        }
        getVideoInfo(videoUrl);
    }

    /**
     * 解析视频和音频的具体信息
     */
    private static void getVideoInfo(String videoUrl) {
        // 获取视频的基本信息
        JSONObject videoInfo = VIDEO_INFO.videoInfo;
//        JSONArray videoInfoArr = videoInfo.getJSONObject("data").getJSONObject("dash")
//            .getJSONArray("video");
//        VIDEO_INFO.videoBaseUrl = videoInfoArr.getJSONObject(0).getString("baseUrl");
//        VIDEO_INFO.videoBaseRange = videoInfoArr.getJSONObject(0).getJSONObject("SegmentBase")
//            .getString("Initialization");
//        HttpResponse videoRes = HttpRequest.get(VIDEO_INFO.videoBaseUrl)
//            .header("Referer", videoUrl)
//            .header("Range", "bytes=" + VIDEO_INFO.videoBaseRange)
//            .header("User-Agent", USER_AGENT)
//            .timeout(2000)
//            .execute();
//        VIDEO_INFO.videoSize = videoRes.header("Content-Range").split("/")[1];

        // 获取音频基本信息
        JSONArray audioInfoArr = videoInfo.getJSONObject("data").getJSONObject("dash")
            .getJSONArray("audio");
        VIDEO_INFO.audioBaseUrl = audioInfoArr.getJSONObject(0).getString("baseUrl");
        VIDEO_INFO.audioBaseRange = audioInfoArr.getJSONObject(0).getJSONObject("SegmentBase")
            .getString("Initialization");
        HttpResponse audioRes = HttpRequest.get(VIDEO_INFO.audioBaseUrl)
            .header("Referer", videoUrl)
            .header("Range", "bytes=" + VIDEO_INFO.audioBaseRange)
            .header("User-Agent", USER_AGENT)
            .timeout(2000)
            .execute();
        VIDEO_INFO.audioSize = audioRes.header("Content-Range").split("/")[1];

        downloadFile(videoUrl);
    }

    /**
     * 下载音视频
     */
    private static void downloadFile(String videoUrl) {
        // 保存音视频的位置
//        SAVE_PATH = "D:\\tmp\\2020-11-25" + File.separator + VIDEO_INFO.videoName;
//        File fileDir = new File(SAVE_PATH);
//        if (!fileDir.exists()) {
//            fileDir.mkdirs();
//        }

        // 下载视频
//        File videoFile = new File(SAVE_PATH + File.separator + VIDEO_INFO.videoName + "_video.mp4");
//        if (!videoFile.exists()) {
//            System.out.println("--------------开始下载视频文件--------------");
//            HttpResponse videoRes = HttpRequest.get(VIDEO_INFO.videoBaseUrl)
//                .header("Referer", videoUrl)
//                .header("Range", "bytes=0-" + VIDEO_INFO.videoSize)
//                .header("User-Agent", USER_AGENT)
//                .execute();
//            videoRes.writeBody(videoFile);
//            System.out.println("--------------视频文件下载完成--------------");
//        }

        // 下载音频
//        File audioFile = new File(SAVE_PATH + File.separator + VIDEO_INFO.videoName + "_audio.mp4");
//        if (!audioFile.exists()) {
//            System.out.println("--------------开始下载音频文件--------------");
//            HttpResponse audioRes = HttpRequest.get(VIDEO_INFO.audioBaseUrl)
//                .header("Referer", videoUrl)
//                .header("Range", "bytes=0-" + VIDEO_INFO.audioSize)
//                .header("User-Agent", USER_AGENT)
//                .execute();
//            audioRes.writeBody(audioFile);
//            System.out.println("--------------音频文件下载完成--------------");
//        }
        Map<String, String> htmlParsers = new HashMap<>();
        htmlParsers.put("Referer", videoUrl);
        htmlParsers.put("Range", "bytes=" + VIDEO_INFO.audioSize);
        htmlParsers.put("User-Agent", USER_AGENT);
        OkHttpUtils.get().url(VIDEO_INFO.audioBaseUrl).headers(htmlParsers).build().execute(
            new FileCallBack("D:/", VIDEO_INFO.videoName + ".mp3") {
                @Override
                public void onError(Call call, Exception e, int i) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(File file, int i) {
                    System.out.println("获取到的数据:" + file.getAbsolutePath());
                }
            });

//        mergeFiles(videoFile, audioFile);

    }


    private static void mergeFiles(File videoFile, File audioFile) {
        System.out.println("--------------开始合并音视频--------------");
        String outFile = SAVE_PATH + File.separator + VIDEO_INFO.videoName + ".mp4";
        List<String> commend = new ArrayList<>();
        commend.add(FFMPEG_PATH);
        commend.add("-i");
        commend.add(videoFile.getAbsolutePath());
        commend.add("-i");
        commend.add(audioFile.getAbsolutePath());
        commend.add("-vcodec");
        commend.add("copy");
        commend.add("-acodec");
        commend.add("copy");
        commend.add(outFile);

        ProcessBuilder builder = new ProcessBuilder();
        builder.command(commend);
        try {
            builder.inheritIO().start().waitFor();
            System.out.println("--------------音视频合并完成--------------");
        } catch (InterruptedException | IOException e) {
            System.err.println("音视频合并失败！");
            e.printStackTrace();
        }

    }

}

