package com.video;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class Analysis {

    /**
     * 抖音
     */
    public static String ShortVideoAnalysisdouyin(String url) throws Exception {
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
        GetMethod getMethod = new GetMethod(url);
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
        getMethod.addRequestHeader("Content-Type", "application/json");
        getMethod.addRequestHeader("user-agent",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36");
        getMethod.setFollowRedirects(false);
        httpClient.executeMethod(getMethod);
        String location1 = getMethod.getResponseHeader("location").toString().substring(48, 67);
        getMethod.releaseConnection();
        GetMethod getMethod2 = new GetMethod(
            "https://www.iesdouyin.com/web/api/v2/aweme/iteminfo/?item_ids=" + location1);
        getMethod2.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
        getMethod2.addRequestHeader("Content-Type", "application/json");
        getMethod2.addRequestHeader("user-agent",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36");
        httpClient.executeMethod(getMethod2);
        String responseBodyAsString = getMethod2.getResponseBodyAsString();
        JSONObject obj = JSONObject.parseObject(responseBodyAsString);
        JSONArray jsonObject = obj.getJSONArray("item_list");
        JSONObject o = (JSONObject) jsonObject.get(0);
        JSONObject jsonObject1 = o.getJSONObject("video");
        JSONObject play_addr = jsonObject1.getJSONObject("play_addr");
        String url_list = play_addr.getJSONArray("url_list").get(0).toString()
            .replace("playwm", "play");
        GetMethod getMethod3 = new GetMethod(url_list);
        getMethod3.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
        getMethod3.addRequestHeader("Content-Type", "application/json");
        getMethod3.addRequestHeader("user-agent",
            "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1");
        getMethod3.setFollowRedirects(false);
        httpClient.executeMethod(getMethod3);
        String location = getMethod3.getResponseHeader("location").getValue();
        return location;
    }


    /**
     * 快手
     */
    public static String ShortVideoAnalysiskuaishou(String ShortVideoLink)
        throws HttpException, IOException {
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
        GetMethod getMethod = new GetMethod(ShortVideoLink);
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
        getMethod.addRequestHeader("Content-Type", ",application/json");
        getMethod.addRequestHeader("user-agent",
            "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1");
        getMethod.addRequestHeader("Cookie", "");//随便找一个填上
        httpClient.executeMethod(getMethod);
        String result = getMethod.getResponseBodyAsString();
        getMethod.releaseConnection();
        String pattern = "(https://txmov2.a.yximgs.com/upic/)(.*?)(mp4)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(result);
        String res = "";
        while (m.find()) {
            res = m.group(0);
            break;
        }

        return res;
    }

    /**
     * 微视
     */
    public static String ShortVideoAnalysisweishi(String ShortVideoLink)
        throws HttpException, IOException {
        String res = "";
        if (ShortVideoLink.contains("challenge")) {
            String pattern = "(feedid)(.*?)(&challegeid)";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(ShortVideoLink);

            while (m.find()) {
                res = m.group(0);
                break;
            }
            ShortVideoLink =
                "https://h5.weishi.qq.com/webapp/json/weishi/WSH5GetPlayPage?t=0.39937760778550624&g_tk="
                    + "&" + res;
        } else {
            String temp = ShortVideoLink.substring(37, 54);
            ShortVideoLink =
                "https://h5.weishi.qq.com/webapp/json/weishi/WSH5GetPlayPage?t=0.39937760778550624&g_tk="
                    + "&" + "feedid=" + temp;
        }

        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
        GetMethod getMethod = new GetMethod(ShortVideoLink);
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
        getMethod.addRequestHeader("Content-Type", ",application/json");
        getMethod.addRequestHeader("user-agent",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36");
        httpClient.executeMethod(getMethod);
        String result = getMethod.getResponseBodyAsString();
        getMethod.releaseConnection();
        String pattern = "(http://v.weishi.qq.com/v.weishi.qq.com)(.*?)(material_thumburl)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(result);
        String res2 = "";
        while (m.find()) {
            res2 = m.group(0);
            break;
        }
        String res3 = "";
        res3 = res2.substring(0, 174);
        return res3;
    }

    /**
     * 皮皮虾
     */
    public static String ShortVideoAnalysispipixia(String url)
        throws HttpException, IOException, JSONException {
        OkHttpClient okHttpClient = new OkHttpClient();
        String usedAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1";
        Request request = new Request.Builder()
            .url(url)
            .addHeader("user-agent", usedAgent)
            .build();
        Call call = okHttpClient.newCall(request);
        Response execute = call.execute();
        String s = execute.request().url().toString();
        s = s.substring(s.indexOf("item") + 5, s.indexOf("?"));
        String tempurl = "https://h5.pipix.com/bds/webapi/item/detail/?item_id=" + s;
        SoftReference<OkHttpClient> soft = new SoftReference<OkHttpClient>(okHttpClient);
        okHttpClient = soft.get();
        request = new Request.Builder()
            .url(tempurl)
            .addHeader("user-agent", usedAgent)
            .build();
        call = okHttpClient.newCall(request);
        execute = call.execute();
        s = execute.body().string();
        JSONObject obj = JSONObject.parseObject(s);
        JSONObject jsonObject = obj.getJSONObject("data");
        jsonObject = jsonObject.getJSONObject("item");
        jsonObject = jsonObject.getJSONObject("origin_video_download");
        JSONArray jsonArray = jsonObject.getJSONArray("url_list");
        String string = jsonArray.getJSONObject(0).getString("url");
        return string;
    }

    /**
     * 屁屁搞笑
     */
    public static String ShortVideoAnalysispipigaoxiao(String url)
        throws HttpException, IOException,

        JSONException {
        long pid = Long.valueOf(url.substring(33, 45));
        Map<String, Object> params = new HashMap<>();
        params.put("pid", pid);
        params.put("type", "post");
        params.put("mid", null);
        String paramsJson = JSON.toJSONString(params);
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(
            "http://share.ippzone.com/ppapi/share/fetch_content");
        postMethod.setRequestHeader("User-Agent",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36");
        postMethod.setRequestHeader("Content-Type", "text/plain;charset=UTF-8");
        postMethod.setRequestHeader("Referer", url);
        postMethod.setRequestHeader("Origin", "http://share.ippzone.com");
        byte[] requestBytes = paramsJson.getBytes("utf-8");
        InputStream inputStream = new ByteArrayInputStream(requestBytes, 0,
            requestBytes.length);
        RequestEntity requestEntity = new InputStreamRequestEntity(inputStream,
            requestBytes.length, "application/json; charset=utf-8");
        postMethod.setRequestEntity(requestEntity);
        httpClient.executeMethod(postMethod);
        byte[] responseBody = postMethod.getResponseBody();
        String s = new String(responseBody);

        JSONObject obj = JSONObject.parseObject(s);
        JSONObject jsonObject = obj.getJSONObject("data");
        jsonObject = jsonObject.getJSONObject("post");
//        jsonObject = jsonObject.getJSONObject("videos");
//        Iterator keys = jsonObject.keys();
//        Object key = null;
//        while (keys.hasNext()) {
//            key = keys.next();
//            break;
//        }
//        JSONObject jsonObject1 = jsonObject.getJSONObject(key.toString());
//        String url1 = jsonObject1.getString("url");
        String url1 = jsonObject.getJSONArray("videos")
            .getJSONObject(jsonObject.getJSONArray("videos").size() - 1).getString("url");
        return url1;
    }

    /*最右*/
//    public static String ShortVideoAnalysiszuiyou(String url) throws HttpException, IOException, JSONException {
//        String pattern1 = "\\d+[0,9]\\d+";
//        Pattern r = Pattern.compile(pattern1);
//        Matcher m = r.matcher(url);
//        String res1 = "";
//        while (m.find()) {
//            res1 = m.group(0);
//            break;
//        }
//        ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();
//        Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
//        HttpClient httpClient = new HttpClient();
//        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
//        GetMethod getMethod = new GetMethod("https://share.izuiyou.com/hybrid/share/post?zy_to=applink&to=applink&pid=" + res1);
//        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
//        getMethod.addRequestHeader("Content-Type", ",application/json");
//        getMethod.addRequestHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36");
//        httpClient.executeMethod(getMethod);
//        String result = getMethod.getResponseBodyAsString();
//        Document document = Jsoup.parse(result);
//        Elements video = document.getElementsByTag("script");
//        String tempscript = video.get(1).toString();
//        String script = Dao.unicodetoString(tempscript);
//        String pattern = "(\"videos\")(.*?)(})";
//        Pattern r2 = Pattern.compile(pattern);
//        Matcher m2 = r2.matcher(script);
//        String res2 = "";
//        while (m2.find()) {
//            res2 = m2.group(0);
//            break;
//        }
//        String pattern2 = "(\"url\":\")(.*?)(\",\")";
//        Pattern r3 = Pattern.compile(pattern2);
//        Matcher m3 = r3.matcher(res2);
//        String res3 = "";
//        while (m3.find()) {
//            res3 = m3.group(0);
//            break;
//        }
//        return (res3.substring(7, res3.length() - 3));
//    }

    /**
     * 火山
     */
    public static String ShortVideoAnalysishuoshan(String ShortVideoLink) throws Exception {
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
        GetMethod getMethod1 = new GetMethod(ShortVideoLink);
        getMethod1.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
        getMethod1.addRequestHeader("user-agent",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36");
        getMethod1
            .addRequestHeader("Cookie", "SLARDAR_WEB_ID=d3910f70-0d69-4aea-a04c-5639a144a4f3");//填写
        getMethod1.setFollowRedirects(false);
        httpClient.executeMethod(getMethod1);
        String location = getMethod1.getResponseHeader("location").getValue();
        getMethod1.releaseConnection();
        String pattern1 = "(item_id=)(.*?)(&tag)";
        Pattern r1 = Pattern.compile(pattern1);
        Matcher m1 = r1.matcher(location);
        String res1 = "";
        while (m1.find()) {
            res1 = m1.group(0);
            break;
        }
        GetMethod getMethod2 = new GetMethod(
            "https://share.huoshan.com/api/item/info?" + res1.substring(0, res1.length() - 4));
        getMethod2.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
        getMethod2.addRequestHeader("user-agent",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36");
        getMethod1.addRequestHeader("referer", location);
        httpClient.executeMethod(getMethod2);
        String response = getMethod2.getResponseBodyAsString();
        String pattern2 = "(https://api.huoshan.com)(.*?)(\"})";
        Pattern r2 = Pattern.compile(pattern2);
        Matcher m2 = r2.matcher(response);
        String res2 = "";
        while (m2.find()) {
            res2 = (m2.group(0).substring(0, m2.group(0).length() - 2))
                .replace("app_id=0", "app_id=1");
            break;
        }
        return res2;
    }

    public static String unicodetoString(String unicode) {
        if (unicode == null || "".equals(unicode)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;
        while ((i = unicode.indexOf("\\u", pos)) != -1) {
            sb.append(unicode.substring(pos, i));
            if (i + 5 < unicode.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(unicode.substring(i + 2, i + 6), 16));
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }
}