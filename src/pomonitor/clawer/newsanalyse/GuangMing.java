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
 * ����������
 * 
 * @author ����ί
 * 
 */

public class GuangMing extends BaseAnalyse {

	public GuangMing(String webName, boolean isKeep) {
		super(webName, isKeep);
		// url
		// seedUrl="http://search.sina.com.cn/?c=news";
		seedUrl = "http://zhannei.baidu.com/cse/search?";
		// ����
		// params="&range=all&num=10&col=&source=&from=&country=&size=&a=&page=2";
		params = "p=0&s=6995449224882484381&nsid=0";
	}

	@Override
	public int getPageCount(String key, boolean isLatest) {
		key = URLEncoder.encode(key);

		// ����Ҫ�����Ĺؼ���
		String searchKeyStr = "&q=" + key;

		String searchTimeStr = "&sti=";

		if (isLatest) {
			// �����������
			searchTimeStr += "10080";
		} else {
			// ������������

		}

		// ����Ҫ�����Ĳ���
		params = params + searchKeyStr + searchTimeStr;

		// ����������url
		seedUrl = seedUrl + params;
		System.out.println(seedUrl);

		URL url;
		int count = 0;
		int pageCount = 0;
		try {
			url = new URL(seedUrl);
			Document doc = null;
			doc = Jsoup.parse(url, 3000);
			// �õ��ܹ���ʾ������������Ľڵ�
			Elements listEle = doc.getElementsByAttributeValue("class",
					"support-text-top");
			// �õ���������ַ���
			String countStr = listEle.get(0).text().trim();
			System.out.println(countStr);
			if (countStr.length() < 12) {
				countStr = countStr.substring(8, 10);
			} else {
				countStr = countStr.substring(8, 11);
			}
			// ������ַ���ת��Ϊ����ת����int����ʽ
			count = Integer.parseInt(countStr);
			System.out.println(count);
			// ������ҳ��
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
			// ��Jsoup����
			Document doc = Jsoup.parse(url, 5000);
			// �õ�ÿһ��
			Elements listEle = doc.getElementsByAttributeValue("class",
					"result f s0");
			// Elements listEle= doc.getElementsByAttributeValue("class",
			// "r-info r-info2");
			// ����ÿһ��
			for (Element e : listEle) {
				NewsEntity GuangMingEntity = new NewsEntity();
				Node h2Node = e.childNode(1);
				Node aNode = h2Node.childNode(1);
				// ȡ��ÿ�����ݵ�ÿ��url
				String getUrl = aNode.attr("href");
				System.out.println("url:" + getUrl);
				// ��������Ϊ��ȡ��title��web
				Elements titleE = e.getElementsByAttributeValue("class",
						"c-title");
				String titleAndWeb = titleE.tagName("a").text();
				String[] strArray = titleAndWeb.split("_");
				String title = strArray[0];
				title = title.replaceAll("<em>", "");
				title = title.replaceAll("</em>", "");
				// ����ע�͵���ȡ������Դ�Ĵ��룬�Ժ���Ҫ����д
				// String web= strArray[strArray.length-1];

				String web = "������";

				// ȡ�� time��url
				Elements timeE = e.getElementsByAttributeValue("class",
						"c-showurl");
				String time = timeE.text();
				time = time.substring(time.length() - 11, time.length());
				// ȡ����Ҫ����
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
