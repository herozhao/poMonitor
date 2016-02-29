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

import ucar.nc2.util.net.URLencode;
import pomonitor.util.UrlSender;
import pomonitor.entity.NewsEntity;

/**
 * 新华网新闻解析
 * 
 * @author 闻市委
 * 
 */

public class XinHuaAnalyse extends BaseAnalyse {

	public XinHuaAnalyse(String webName, boolean isKeep) {
		super(webName, isKeep);
		// url
		seedUrl = "http://news.chinaso.com/newssearch.htm?";
		// 参数
		params = "page=1";
	}

	@Override
	public int getPageCount(String key, boolean isLatest) {
		key = URLencode.escape(key);

		// 设置要搜索的关键字
		String searchKeyStr = "&q=" + key;

		String searchTimeStr = "&startTime=";
		String sarchTimeEnd = "&endTime=";

		if (isLatest) {
			// 搜索最近新闻
			searchTimeStr += "1";
			sarchTimeEnd += "now";
		} else {
			// 搜索所有新闻

		}

		// 连接要搜索的参数
		params = params + searchKeyStr + searchTimeStr + sarchTimeEnd;

		// 整合搜索的url
		seedUrl = seedUrl + params;
		System.out.println(seedUrl);

		URL url;
		int count = 0;
		int pageCount = 0;
		try {

			// 从URL直接加载HTML 文档
			url = new URL(seedUrl);
			Document doc = null;
			doc = Jsoup.parse(url, 1000);
			Elements listEle = doc.getElementsByAttributeValue("class",
					"toolTab_xgxwts");
			String countStr = listEle.get(0).text().trim();
			if (countStr.length() < 12) {
				countStr = countStr.substring(8, 10);
			} else {
				countStr = countStr.substring(8, 11);
			}

			count = Integer.parseInt(countStr);
			pageCount = count / 15 + 1;
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
			Document doc = Jsoup.parse(url, 3000);
			Elements listEle1 = doc.getElementsByTag("ol");
			Element listEle2 = listEle1.get(0);
			// 抽取块内容
			Elements listEle = listEle2.getElementsByAttributeValueContaining(
					"class", "reIte");
			for (Element e : listEle) {

				NewsEntity XinHuaEntity = new NewsEntity();
				// 解析url
				Elements getUrl1 = e.getElementsByTag("a");
				Elements getUrl2 = getUrl1.val("a");
				String getUrl = getUrl2.attr("href");
				// 解析title
				Elements titlE = e.getElementsByTag("h2");
				String title = titlE.text();
				// 得到time和web
				Elements timeE = e.getElementsByAttributeValue("class",
						"snapshot");
				// String timeAndweb=timeE.tagName("span").text();
				Elements timeAndweb = timeE.tagName("span");
				String timee = timeAndweb.text();
				String time = timee.substring(0, 10);
				System.out.println("title" + title);
				System.out.println("time " + time);
				String web = "新华网";
				// 拿到p标签里的内容
				Elements content1 = e.getElementsByTag("p");
				// 拿第一个p标签里的text
				Elements content2 = content1.val("p");
				String content = content2.text();
				XinHuaEntity.setUrl(getUrl);
				XinHuaEntity.setContent(content);
				XinHuaEntity.setTime(time);
				XinHuaEntity.setWeb(webName);
				XinHuaEntity.setTitle(title);
				map.put(getUrl, XinHuaEntity);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public String urlAnalyse(int i) {
		String urlStr = seedUrl;
		String replaceStr = "page=" + i;
		urlStr = urlStr.replaceAll("page=[1-9]*", replaceStr);
		return urlStr;
	}

}
