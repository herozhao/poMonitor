package pomonitor.clawer.newsanalyse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import ucar.nc2.util.net.URLencode;
import pomonitor.clawer.newsanalyse.BaseAnalyse;
import pomonitor.entity.NewsEntity;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * 华声在线解析
 * 
 * @author闻市委
 * 
 */
public class HuaShengAnalyse extends BaseAnalyse {

	public HuaShengAnalyse(String webName, boolean isKeep) {
		super(webName, isKeep);
		// url
		seedUrl = "http://so.voc.com.cn/cse/search?";
		// 参数
		params = "&p=0&s=7639422230623402302&nsid=0";
	}

	@Override
	public int getPageCount(String key, boolean isLatest) {
		// 对汉字进行编码加密
		key = URLencode.escape(key);
		System.out.println(key);

		// 设置要搜索的关键字
		String searchKeyStr = "&q=" + key;

		// 构造搜索范围
		String searchTimeStr = "&sti=";

		if (isLatest) {
			// 搜索最近一周新闻
			searchTimeStr += "10080";
		} else {
			// 搜索所有新闻

		}

		// 连接要搜索的参数
		params = params + searchKeyStr + searchTimeStr;

		// 构造完整url
		seedUrl = seedUrl + params;
		System.out.println(seedUrl);

		URL url;
		int count = 0;
		int pageCount = 0;
		try {
			url = new URL(seedUrl);
			Document doc = null;
			doc = Jsoup.parse(url, 5000);
			// 用文章类名得到文章总数
			Elements listEle = doc.getElementsByAttributeValue("class",
					"support-text-top");
			String countStr = listEle.text();
			if (countStr.length() < 12) {
				countStr = countStr.substring(countStr.length() - 3,
						countStr.length() - 1);
			} else {
				countStr = countStr.substring(countStr.length() - 4,
						countStr.length() - 1);
			}

			System.out.println(countStr);
			count = Integer.parseInt(countStr);
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
			Document doc = Jsoup.parse(url, 5000);
			// 取出每一条的内容
			Elements listEle = doc.getElementsByAttributeValue("class",
					"result f s0");
			for (Element e : listEle) {

				NewsEntity HuashengEntity = new NewsEntity();
				// 取出每条内容的每个url
				Elements url1 = e.getElementsByTag("a");
				String getUrl = url1.attr("href");
				System.out.println("url:" + getUrl);
				// 取出title
				Elements titleE = e.getElementsByAttributeValue("class",
						"c-title");
				String Ititle = titleE.text();
				// Matcher date = p.matcher(title);
				String reExpression = "[_-]+";
				String expression = "\\s+";
				Pattern pattern = Pattern.compile(expression);
				Matcher m = pattern.matcher(Ititle);
				Ititle = m.replaceAll("-");
				Pattern p = Pattern.compile(reExpression);
				String[] titles = p.split(Ititle);
				String title = titles[0];
				// String expression="\\s+";
				// Pattern pattern = Pattern.compile(expression);
				// String[] titles1= p.split(Ititles);
				// String title=titles1[0];
				System.out.println("title:" + title);
				// 取出time
				Elements timeE = e.getElementsByAttributeValue("class",
						"c-showurl");
				String time = timeE.text();
				time = time.substring(time.length() - 10, time.length());
				System.out.println("time:" + time);
				String web = "华声在线";
				// 取出content
				Elements contentE = e.getElementsByAttributeValue("class",
						"c-abstract");
				String content = contentE.text();
				System.out.println(content);
				System.out.println("web:" + web);

				HuashengEntity.setUrl(getUrl);
				HuashengEntity.setContent(content);
				HuashengEntity.setTime(time);
				HuashengEntity.setWeb(webName);
				HuashengEntity.setTitle(title);
				map.put(getUrl, HuashengEntity);
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
