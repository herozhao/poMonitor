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
 * �»������Ž���
 * 
 * @author ����ί
 * 
 */

public class XinHuaAnalyse extends BaseAnalyse {

	public XinHuaAnalyse(String webName, boolean isKeep) {
		super(webName, isKeep);
		// url
		seedUrl = "http://news.chinaso.com/newssearch.htm?";
		// ����
		params = "page=1";
	}

	@Override
	public int getPageCount(String key, boolean isLatest) {
		key = URLencode.escape(key);

		// ����Ҫ�����Ĺؼ���
		String searchKeyStr = "&q=" + key;

		String searchTimeStr = "&startTime=";
		String sarchTimeEnd = "&endTime=";

		if (isLatest) {
			// �����������
			searchTimeStr += "1";
			sarchTimeEnd += "now";
		} else {
			// ������������

		}

		// ����Ҫ�����Ĳ���
		params = params + searchKeyStr + searchTimeStr + sarchTimeEnd;

		// ����������url
		seedUrl = seedUrl + params;
		System.out.println(seedUrl);

		URL url;
		int count = 0;
		int pageCount = 0;
		try {

			// ��URLֱ�Ӽ���HTML �ĵ�
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
			// ��ȡ������
			Elements listEle = listEle2.getElementsByAttributeValueContaining(
					"class", "reIte");
			for (Element e : listEle) {

				NewsEntity XinHuaEntity = new NewsEntity();
				// ����url
				Elements getUrl1 = e.getElementsByTag("a");
				Elements getUrl2 = getUrl1.val("a");
				String getUrl = getUrl2.attr("href");
				// ����title
				Elements titlE = e.getElementsByTag("h2");
				String title = titlE.text();
				// �õ�time��web
				Elements timeE = e.getElementsByAttributeValue("class",
						"snapshot");
				// String timeAndweb=timeE.tagName("span").text();
				Elements timeAndweb = timeE.tagName("span");
				String timee = timeAndweb.text();
				String time = timee.substring(0, 10);
				System.out.println("title" + title);
				System.out.println("time " + time);
				String web = "�»���";
				// �õ�p��ǩ�������
				Elements content1 = e.getElementsByTag("p");
				// �õ�һ��p��ǩ���text
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
