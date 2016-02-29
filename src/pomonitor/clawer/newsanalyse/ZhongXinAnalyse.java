package pomonitor.clawer.newsanalyse;

import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import pomonitor.util.UrlSender;
import pomonitor.entity.NewsEntity;

/**
 * 中新网新闻解析
 * 
 * @author 赵龙
 * 
 */
public class ZhongXinAnalyse extends BaseAnalyse {

	public ZhongXinAnalyse(String webName, boolean isKeep) {
		super(webName, isKeep);
		seedUrl = "http://sou.chinanews.com.cn/search.do";
		params = "ps=50&start=0&channel=all&sort=pubtime";
	}

	@Override
	public int getPageCount(String key, boolean isLatest) {
		// 设置要搜索的关键字
		String searchKeyStr = "&q=" + key;

		String searchTimeStr = "&time_scope=";

		if (isLatest) {
			// 搜索最近新闻
			searchTimeStr += 7;
		} else {
			// 搜索所有新闻
			searchTimeStr += 0;
		}

		// 连接要搜索的参数
		params = params + searchKeyStr + searchTimeStr;

		int count = 0;
		int pageCount = 0;
		String resultHrml = UrlSender.sendPost(seedUrl, params);
		Document doc = Jsoup.parse(resultHrml);
		Elements listEle = doc.getElementsByAttributeValue("style",
				"color: red");
		String countStr = listEle.text().trim();
		count = Integer.parseInt(countStr);
		System.out.println(count);
		pageCount = count / 50 + 1;
		System.out.println("共有：" + pageCount + "页");
		return pageCount;
	}

	@Override
	public HashMap<String, Object> analyseAnyPage(String params) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			String resultHrml = UrlSender.sendPost(seedUrl, params);
			Document doc = Jsoup.parse(resultHrml);
			Elements listEleText = doc.getElementsByAttributeValue("id",
					"news_list");
			Element ele = listEleText.get(0);
			Elements listEle = ele.getElementsByTag("table");
			for (Element e : listEle) {
				try {
					NewsEntity redEntity = new NewsEntity();
					// 获取title
					Elements titleE = e.getElementsByAttributeValue("class",
							"news_title");
					String title = titleE.tagName("a").text();
					// 获取url
					Node urlHref = titleE.first().childNode(1);
					String url = urlHref.attr("href");
					// 获取时间
					Elements timeE = e.getElementsByAttributeValue("class",
							"news_other");
					String time = timeE.html();
					time = time.split(" ")[1];
					// 获取简介
					Elements contentE = e.getElementsByAttributeValue("class",
							"news_content");
					String content = contentE.text();
					// 输出
					System.out.println("title:" + title);
					// System.out.println("web:"+web);
					System.out.println("time:" + time);
					System.out.println("content:" + content);
					System.out.println("url:" + url);
					// 添加入对象
					redEntity.setUrl(url);
					redEntity.setContent(content);
					redEntity.setTime(time);
					redEntity.setWeb(webName);
					redEntity.setTitle(title);
					// 将获取到的对象放入map
					map.put(url, redEntity);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public String urlAnalyse(int i) {
		i = (i - 1) * 50;
		String urlStr = params;
		String replaceStr = "start=" + i;
		urlStr = urlStr.replaceAll("start=[0-9]*", replaceStr);
		return urlStr;
	}

}
