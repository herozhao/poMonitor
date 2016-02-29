package pomonitor.clawer.newsanalyse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import pomonitor.entity.NewsEntity;

/**
 * 新浪新闻解析
 * 
 * @author 赵龙
 * 
 */

public class SinaAnalyse extends BaseAnalyse {

	public SinaAnalyse(String webName, boolean isKeep) {
		super(webName, isKeep);
		// url
		seedUrl = "http://search.sina.com.cn/?c=news";
		// 参数
		params = "&range=all&num=20&col=&source=&from=&country=&size=&a=&page=2";
	}

	@Override
	public int getPageCount(String key, boolean isLatest) {
		key = URLEncoder.encode(key);

		// 设置要搜索的关键字
		String searchKeyStr = "&q=" + key;

		String searchTimeStr = "&time=";

		if (isLatest) {
			// 搜索最近新闻
			searchTimeStr += "w";
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
			Elements listEle = doc.getElementsByAttributeValue("class", "l_v2");

			// 拿到结果数量字符串
			String countStr = listEle.get(0).html();
			countStr = countStr.substring(6, countStr.length() - 1);
			System.out.println(countStr);
			// 将结果字符串转化为可以转化成int的形式
			countStr = countStr.replace(",", "");
			count = Integer.parseInt(countStr);
			System.out.println(count);
			// 计算总页数
			pageCount = count / 20 + 1;
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
					"r-info r-info2");
			// 遍历每一天
			for (Element e : listEle) {
				try {
					NewsEntity sinaEntity = new NewsEntity();

					Node h2Node = e.childNode(1);
					Node aNode = h2Node.childNode(0);
					// 拿到url
					String getUrl = aNode.attr("href");
					// 拿到title
					String title = aNode.childNode(0).outerHtml();
					title = title.replaceAll("<span style=\"color:#C03\">", "");
					title = title.replaceAll("</span>", "");
					// title=e.select("a").text();
					Elements Title = e.getElementsByTag("h2");
					title = Title.select("a").text();
					// 取时间和来源的字符串
					String timeAndWeb = h2Node.childNode(2).childNode(0)
							.outerHtml();
					String[] strs = timeAndWeb.split(" ");
					String time = strs[1] + " " + strs[2];
					time = time.substring(0, 10);
					System.out.println("time" + time);
					String web = "新浪";
					// 取content内容
					Node pNode = e.childNode(3);
					String content = pNode.outerHtml();
					content = content.replaceAll("<span style=\"color:#C03\">",
							"");
					content = content.replaceAll("</span>", "");
					content = content.substring(19, (content.length() - 4));

					System.out.println("url:" + getUrl);
					System.out.println("title:" + title);
					System.out.println("web:" + web);
					System.out.println("time:" + time);

					// 构建对象
					sinaEntity.setUrl(getUrl);
					sinaEntity.setContent(content);
					sinaEntity.setTime(time);
					sinaEntity.setWeb(webName);
					sinaEntity.setTitle(title);
					map.put(getUrl, sinaEntity);
				} catch (Exception ee) {
					ee.printStackTrace();
					System.out.println(ee);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return map;
		}
		return map;
	}

	@Override
	public String urlAnalyse(int i) {
		// 替换url
		String urlStr = seedUrl;
		String replaceStr = "page=" + i;
		urlStr = urlStr.replaceAll("page=[0-9]*", replaceStr);
		return urlStr;
	}

}
