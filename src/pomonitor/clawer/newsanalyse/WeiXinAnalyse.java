package pomonitor.clawer.newsanalyse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
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
 * 搜狗微信解析
 * 
 * @author 闻市委
 * 
 */
public class WeiXinAnalyse extends BaseAnalyse {

	public WeiXinAnalyse(String webName, boolean isKeep) {
		super(webName, isKeep);
		// url
		seedUrl = " http://weixin.sogou.com/weixin?";
		// 参数
		params = "&interation=&type=2&interV=kKIOkrELjboJmLkElbYTkKIKmbELjbkRmLkElbk%3D_1893302304&page=2&ie=utf8&p=99041202&dp=1";
	}

	@Override
	public int getPageCount(String key, boolean isLatest) {
		// 对汉字进行编码加密
		key = URLencode.escape(key);
		System.out.println(key);

		// 设置要搜索的关键字
		String searchKeyStr = "&query=" + key;

		// 构造搜索范围
		String searchTimeStr = "&tsn=";

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
			doc = Jsoup.parse(url, 5000);
			count = 100;
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
			// System.out.println(doc.html());
			Elements listEle = doc.getElementsByAttributeValue("class",
					"txt-box");
			for (Element e : listEle) {
				NewsEntity WeixinEntity = new NewsEntity();
				// 取出每条内容的每个url
				Elements url1 = e.getElementsByTag("a");
				Elements getUrl2 = url1.val("a");
				String getUrl = getUrl2.attr("href");
				System.out.println(getUrl);
				// 取出title
				Elements titleE = e.getElementsByTag("h4");
				String title = titleE.text();
				System.out.println("title:" + title);

				Elements timeE = e.getElementsByAttributeValue("class", "s-p");
				// Elements timeE =e.select("script");
				// String time=timeE.text();
				Elements time1 = timeE.tagName("script");
				String time2 = time1.html();
				// 截取字符串
				String time3 = time2.substring(time2.length() - 22,
						time2.length() - 12);
				// java中将long转化为日期要求是13位
				String t = "000";
				time3 = time3 + t;
				long time4 = Long.valueOf(time3); // long类型的字符串转换
				Date time0 = new Date(time4);
				SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
				String time = sdf.format(time0);

				// 提取content
				Elements contentE = e.getElementsByTag("p");
				String content = contentE.text();
				// 网站web
				String web = "搜狗微信";

				WeixinEntity.setUrl(getUrl);
				WeixinEntity.setContent(content);
				WeixinEntity.setTime(time);
				WeixinEntity.setWeb(webName);
				WeixinEntity.setTitle(title);
				map.put(getUrl, WeixinEntity);
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
