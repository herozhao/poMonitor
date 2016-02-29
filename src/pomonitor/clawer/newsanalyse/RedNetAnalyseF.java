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
import pomonitor.entity.NewsEntity;

/**
 * 红网论坛解析
 * 
 * @author 赵龙
 * 
 */
public class RedNetAnalyseF extends BaseAnalyse {

	public RedNetAnalyseF(String webName, boolean isKeep) {
		super(webName, isKeep);
		// url
		seedUrl = "http://s.rednet.cn/?";
		// 参数
		params = "scope=4&page_size=50&orderby=1&page=1";
	}

	@Override
	public int getPageCount(String key, boolean isLatest) {
		// 对汉字进行编码加密
		key = URLencode.escape(key);
		System.out.println(key);

		// 设置要搜索的关键字
		String searchKeyStr = "&q=" + key;

		// 构造搜索范围
		String searchTimeStr = "&date_range=";

		if (isLatest) {
			// 搜索最近一周新闻
			searchTimeStr += "2";
		} else {
			// 搜索所有新闻
			searchTimeStr += "0";

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
			doc = Jsoup.parse(url, 1000);
			// System.out.println(doc.html());

			// 获取搜索结果数量字符串
			Elements listEle = doc.getElementsByAttributeValue("class",
					"bold-font");
			String countStr = listEle.get(0).text().trim();
			System.out.println(countStr);

			// 转换为int
			count = Integer.parseInt(countStr);
			System.out.println(count);

			// 计算总页数
			pageCount = count / 50 + 1;
			if (pageCount > 20)
				pageCount = 20;
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
			// 发送请求并且拿到内容
			URL url = new URL(Strurl);
			Document doc = Jsoup.parse(url, 5000);
			// 获取每一条
			Elements listEle = doc.getElementsByAttributeValue("class",
					"module");

			// 遍历所有条
			for (Element e : listEle) {
				NewsEntity RedEntity = new NewsEntity();

				// 获取title
				Elements titleE = e.getElementsByAttributeValue("class",
						"title");
				String titleAndTime = titleE.tagName("a").text();
				String[] strArray = titleAndTime.split("版块");
				String title = strArray[0];
				title = title.replaceAll("<b>", "");
				title = title.replaceAll("</b>", "");

				// 获取时间
				System.out.println(titleAndTime + "~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println(title);
				String timeStr = strArray[1];
				System.out.println(timeStr);
				String time = "";
				try {
					time = timeStr.substring(timeStr.length() - 16);
				} catch (Exception e2) {
					// 若获取时间错误，则表明这条新闻形式为“几小时前”，，则自己构造时间
					long timel = System.currentTimeMillis();
					Date date = new Date(timel);
					time = date.getYear() + 1900 + "-" + date.getMonth() + "-"
							+ date.getDate();
				}
				time = time.substring(0, 10);

				String web = "红网_论坛";

				// 获取url
				Elements urlE = e.getElementsByAttributeValue("class", "link");
				String getUrl = urlE.text();

				// 获取content
				Elements contentE = e.getElementsByAttributeValue("class",
						"content");
				String content = contentE.text();

				System.out.println("title:" + title);
				System.out.println("web:" + web);
				System.out.println("time:" + time);
				System.out.println("content:" + content);
				System.out.println("url:" + getUrl);

				// 此处获取到的url包含π杂音，需要处理
				if (getUrl.contains("π")) {
					getUrl = getUrl.replaceAll("π", "&pi");
					getUrl = urlChange(getUrl);
					System.out.println(getUrl + "~~~~~~~~~~");
				}

				// 构造对象
				RedEntity.setUrl(getUrl);
				RedEntity.setContent(content);
				RedEntity.setTime(time);
				RedEntity.setWeb(webName);
				RedEntity.setTitle(title);
				map.put(getUrl, RedEntity);
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
		urlStr = urlStr.replaceAll("page=[0-9]*", replaceStr);
		return urlStr;
	}

	public String urlChange(String oldUrl) {
		oldUrl = oldUrl.replaceAll("redirect&", "viewthread");
		oldUrl = oldUrl.replaceAll("goto=findpost", "");
		oldUrl = oldUrl.replaceAll("ptid", "tid");
		System.out.println(oldUrl + "!!!!!!!!!!!!!!!");
		return oldUrl;

	}
}
