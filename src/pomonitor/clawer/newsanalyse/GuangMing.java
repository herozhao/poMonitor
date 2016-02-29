package pomonitor.clawer.newsanalyse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.utils.URLEncodedUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import pomonitor.util.UrlSender;
import pomonitor.clawer.newsanalyse.BaseAnalyse;
import pomonitor.entity.NewsEntity;

;
/**
 * 光明网解析
 * 
 * @author 闻市委
 * 
 */

public class GuangMing extends BaseAnalyse {

	public GuangMing(String webName, boolean isKeep) {
		super(webName, isKeep);
		// url
		// seedUrl="http://search.sina.com.cn/?c=news";
		seedUrl = "http://zhannei.baidu.com/cse/search?";
		// 参数
		// params="&range=all&num=10&col=&source=&from=&country=&size=&a=&page=2";
		params = "p=0&s=6995449224882484381&nsid=0";
	}

	@Override
	public int getPageCount(String key, boolean isLatest) {
		key = URLEncoder.encode(key);

		// 设置要搜索的关键字
		String searchKeyStr = "&q=" + key;

		String searchTimeStr = "&sti=";

		if (isLatest) {
			// 搜索最近新闻
			searchTimeStr += "10080";
		} else {
			// 搜索所有新闻

		}

		// 连接要搜索的参数
		params = params + searchKeyStr + searchTimeStr;

		// 整合搜索的url
		seedUrl = seedUrl + params;
		System.out.println(seedUrl);

		URL url;
		int count = 0;
		int pageCount = 0;
		try {
			url = new URL(seedUrl);
			Document doc = null;
			doc = Jsoup.parse(url, 3000);
			// 拿到能够表示搜索结果数量的节点
			Elements listEle = doc.getElementsByAttributeValue("class",
					"support-text-top");
			// 拿到结果数量字符串
			String countStr = listEle.get(0).text().trim();
			System.out.println(countStr);
			if (countStr.length() < 12) {
				countStr = countStr.substring(8, 10);
			} else {
				countStr = countStr.substring(8, 11);
			}
			// 将结果字符串转化为可以转化成int的形式
			count = Integer.parseInt(countStr);
			System.out.println(count);
			// 计算总页数
			pageCount = count / 10 + 1;
			System.out.println(pageCount);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pageCount;
	}

	@Override
	public HashMap<String, Object> analyseAnyPage(String Strurl) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			URL url = new URL(Strurl);
			// 用Jsoup请求
			Document doc = Jsoup.parse(url, 5000);
			// 拿到每一条
			Elements listEle = doc.getElementsByAttributeValue("class",
					"result f s0");
			// Elements listEle= doc.getElementsByAttributeValue("class",
			// "r-info r-info2");
			// 遍历每一天
			for (Element e : listEle) {
				NewsEntity GuangMingEntity = new NewsEntity();
				Node h2Node = e.childNode(1);
				Node aNode = h2Node.childNode(1);
				// 取出每条内容的每个url
				String getUrl = aNode.attr("href");
				System.out.println("url:" + getUrl);
				// 将高亮变为空取出title和web
				Elements titleE = e.getElementsByAttributeValue("class",
						"c-title");
				String titleAndWeb = titleE.tagName("a").text();
				String[] strArray = titleAndWeb.split("_");
				String title = strArray[0];
				title = title.replaceAll("<em>", "");
				title = title.replaceAll("</em>", "");
				// 暂且注释掉获取具体来源的代码，以后需要再重写
				// String web= strArray[strArray.length-1];

				String web = "光明网";

				// 取出 time和url
				Elements timeE = e.getElementsByAttributeValue("class",
						"c-showurl");
				String time = timeE.text();
				time = time.substring(time.length() - 11, time.length());
				// 取出主要内容
				Elements contentE = e.getElementsByAttributeValue("class",
						"c-abstract");
				String content = contentE.text();

				System.out.println("title:" + title);
				System.out.println("web:" + web);
				System.out.println("time:" + time);
				System.out.println("content:" + content);
				GuangMingEntity.setUrl(getUrl);
				GuangMingEntity.setContent(content);
				GuangMingEntity.setTime(time);
				GuangMingEntity.setWeb(webName);
				GuangMingEntity.setTitle(title);
				map.put(getUrl, GuangMingEntity);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public String urlAnalyse(int i) {
		String urlStr = seedUrl;
		String replaceStr = "p=" + i;
		urlStr = urlStr.replaceAll("p=[0-9]*", replaceStr);
		return urlStr;
	}

}
