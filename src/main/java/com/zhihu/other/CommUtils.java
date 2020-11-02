package com.zhihu.other;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CommUtils {

    /**
     * @param html html
     * @return 格式化后的html
     */
    public static String formatHtml(String html) {
        if (StringUtils.isNotBlank(html)) {
            try {
                Document doc = Jsoup.parseBodyFragment(html);
                html = doc.body().html();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return html;
    }

    /**
     * 替换html里的图片链接
     *
     * @param html html
     * @return 格式化后的html
     */
    public static String replaceHtmlPicSrc(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("img[src]");//获取到的值为所有的<embed src="...">
        for (Element element : elements) {
            String picUrl = element.attr("data-original");
            if (StringUtils.isBlank(picUrl)) {
                picUrl = element.attr("data-actualsrc");
            }
            element.attr("src", picUrl);
        }
        return doc.html();
    }

    /**
     * @param html html
     * @return html标题
     */
    public static String getHtmlTitle(String html) {
        return Jsoup.parse(html).title();
    }

    /**
     * 去除知乎html循环请求
     *
     * @param html html
     * @return 格式化后的html
     */
    public static String removeLoopRequest(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("script[src]");//获取到的值为所有的<embed src="...">
        for (Element element : elements) {
            element.attr("src", "");
        }
        return doc.html();
    }


    /**
     * @param urlData 包含网址url的文本
     * @return 正则提取到的url
     */
    public static String getUrl(String urlData) {
        return filterSpecialStr(
            "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]", urlData);
    }

    /**
     * @param html html文本
     * @return 调整后的html
     */
    public static String removeOther(String html) {
        Document doc = Jsoup.parse(html);
        //1.增加标题
        String title = doc.selectFirst("h1").text();
        if (StringUtils.isBlank(title)) {
            title = doc.selectFirst("H1").text();
        }
        System.out.println("获取到的数据title:" + title);
        doc.title(title);
        doc.select("div[class=Question-mainColumn]").append(title);
        //2.去除标题空格
        doc.select("div[class=QuestionHeader]").remove();
        //3.
        return doc.toString();
    }

    /**
     * 参数1 regex:我们的正则字符串 参数2 就是一大段文本，这里用data表示
     */
    private static String filterSpecialStr(String regex, String data) {
        //sb存放正则匹配的结果
        StringBuilder sb = new StringBuilder();
        //编译正则字符串
        Pattern p = Pattern.compile(regex);
        //利用正则去匹配
        Matcher matcher = p.matcher(data);
        //如果找到了我们正则里要的东西
        while (matcher.find()) {
            //保存到sb中，"\r\n"表示找到一个放一行，就是换行
            sb.append(matcher.group()).append("\r\n");
        }
        return sb.toString();
    }
}