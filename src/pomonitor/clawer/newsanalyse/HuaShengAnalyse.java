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
 * �������߽���
 * 
 * @author����ί
 * 
 */
public class HuaShengAnalyse extends BaseAnalyse {

	public HuaShengAnalyse(String webName, boolean isKeep) {
		super(webName, isKeep);
		// url
		seedUrl = "http://so.voc.com.cn/cse/search?";
		// ����
		params = "&p=0&s=7639422230623402302&nsid=0";
	}

	@Override
	public int getPageCount(String key, boolean isLatest) {
		// �Ժ��ֽ��б������
		key = URLencode.escape(key);
		System.out.println(key);

		// ����Ҫ�����Ĺؼ���
		String searchKeyStr = "&q=" + key;

		// ����������Χ
		String searchTimeStr = "&sti=";

		if (isLatest) {
			// �������һ������
			searchTimeStr += "10080";
		} else {
			// ������������

		}

		// ����Ҫ�����Ĳ���
		params = params + searchKeyStr + searchTimeStr;

		// ��������url
		seedUrl = seedUrl + params;
		System.out.println(seedUrl);

		URL url;
		int count = 0;
		int pageCount = 0;
		try {
			url = new URL(seedUrl);
			Document doc = null;
			doc = Jsoup.parse(url, 5000);
			// �����������õ���������
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
			// ȡ��ÿһ��������
			Elements listEle = doc.getElementsByAttributeValue("class",
					"result f s0");
			for (Element e : listEle) {

				NewsEntity HuashengEntity = new NewsEntity();
				// ȡ��ÿ�����ݵ�ÿ��url
				Elements url1 = e.getElementsByTag("a");
				String getUrl = url1.attr("href");
				System.out.println("url:" + getUrl);
				// ȡ��title
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
				// ȡ��time
				Elements timeE = e.getElementsByAttributeValue("class",
						"c-showurl");
				String time = timeE.text();
				time = time.substring(time.length() - 10, time.length());
				System.out.println("time:" + time);
				String web = "��������";
				// ȡ��content
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
