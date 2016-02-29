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

;

/**
 * 搜狐新闻解析
 * 
 * @author 闻市委
 * 
 */

public class SouHuAnalyse extends BaseAnalyse {
	public SouHuAnalyse(String webName, boolean isKeep) {
		super(webName, isKeep);
		// url

		seedUrl = "http://news.sogou.com/news?";
		// 参数
		params = "mode=1&media=&time=0&clusterId=&sort=0&page=1&p=42230304&dp=1";
	}

	@Override
	public int getPageCount(String key, boolean isLatest) {
		key = URLencode.escape(key);
		int count = 0;
		int pageCount = 0;
		// 设置要搜索的关键字
		String key1 = "site%3Asohu.com+";
		String searchKeyStr = "&query=" + key1 + key;
		// 连接要搜索的参数
		params = params + searchKeyStr;
		// 整合搜索的url
		seedUrl = seedUrl + params;
		System.out.println(seedUrl);
		if (isLatest) {
			// 搜索最近新闻
			pageCount = 10;
			return pageCount;

		} else {
			// 搜索所有 新闻
			// URL url;

			// try {

			// 从URL直接加载HTML 文档
			/*
			 * = new URL(seedUrl); Document doc = null; doc = Jsoup.parse(url,
			 * 1000); Elements listEle =
			 * doc.getElementsByAttributeValue("class","mun"); String countStr =
			 * listEle.get(0).text().trim(); countStr = countStr.substring(3,
			 * 8); countStr = countStr.replaceAll(",", "");
			 * 
			 * count = Integer.parseInt(countStr);
			 */
			pageCount = 100;
			/*
			 * } catch (MalformedURLException e) { e.printStackTrace(); } catch
			 * (IOException e) { e.printStackTrace(); } }
			 */
			return pageCount;
		}
	}

	@Override
	public HashMap<String, Object> analyseAnyPage(String Strurl) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			URL url = new URL(Strurl);
			Document doc = Jsoup.parse(url, 3000);
			Elements listEle = doc.getElementsByAttributeValue("class", "news151102");

			for (Element e : listEle) {

				NewsEntity SouHuEntity = new NewsEntity();
				// 解析url
				Elements getUrl1 = e.getElementsByAttributeValueContaining(
						"class", "vrTitle");
				 Elements getUrl2=getUrl1.select("a");
				String getUrl = getUrl2.attr("href");
				System.out.println(getUrl);
				// 解析title
				String title = getUrl1.text();
				System.out.println(title);
				// 得到time
				Elements timE=e.getElementsByAttributeValue("class", "news-from");
				String time=timE.text();
				int len=time.length();
				if(len==0)
				{
					break;
				}
				else{
				time=time.substring(time.length()-16,time.length());
				time=time.substring(0, 10);
			    System.out.println("时间:"+time);
				}
				String web = "搜狐";
				// 取得content
				Elements contenT = e.getElementsByAttributeValue("class", "news-txt");
				String content = contenT.text();
				System.out.println(content);
				SouHuEntity.setUrl(getUrl);
				SouHuEntity.setContent(content);
				SouHuEntity.setTime(time);
				SouHuEntity.setWeb(web);
				SouHuEntity.setTitle(title);
				map.put(getUrl, SouHuEntity);
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
