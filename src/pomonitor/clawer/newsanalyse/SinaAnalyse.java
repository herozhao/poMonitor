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
 * �������Ž���
 * 
 * @author ����
 * 
 */

public class SinaAnalyse extends BaseAnalyse {

	public SinaAnalyse(String webName, boolean isKeep) {
		super(webName, isKeep);
		// url
		seedUrl = "http://search.sina.com.cn/?c=news";
		// ����
		params = "&range=all&num=20&col=&source=&from=&country=&size=&a=&page=2";
	}

	@Override
	public int getPageCount(String key, boolean isLatest) {
		key = URLEncoder.encode(key);

		// ����Ҫ�����Ĺؼ���
		String searchKeyStr = "&q=" + key;

		String searchTimeStr = "&time=";

		if (isLatest) {
			// �����������
			searchTimeStr += "w";
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
			Elements listEle = doc.getElementsByAttributeValue("class", "l_v2");

			// �õ���������ַ���
			String countStr = listEle.get(0).html();
			countStr = countStr.substring(6, countStr.length() - 1);
			System.out.println(countStr);
			// ������ַ���ת��Ϊ����ת����int����ʽ
			countStr = countStr.replace(",", "");
			count = Integer.parseInt(countStr);
			System.out.println(count);
			// ������ҳ��
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
			// ��Jsoup����
			Document doc = Jsoup.parse(url, 5000);
			// �õ�ÿһ��
			Elements listEle = doc.getElementsByAttributeValue("class",
					"r-info r-info2");
			// ����ÿһ��
			for (Element e : listEle) {
				try {
					NewsEntity sinaEntity = new NewsEntity();

					Node h2Node = e.childNode(1);
					Node aNode = h2Node.childNode(0);
					// �õ�url
					String getUrl = aNode.attr("href");
					// �õ�title
					String title = aNode.childNode(0).outerHtml();
					title = title.replaceAll("<span style=\"color:#C03\">", "");
					title = title.replaceAll("</span>", "");
					// title=e.select("a").text();
					Elements Title = e.getElementsByTag("h2");
					title = Title.select("a").text();
					// ȡʱ�����Դ���ַ���
					String timeAndWeb = h2Node.childNode(2).childNode(0)
							.outerHtml();
					String[] strs = timeAndWeb.split(" ");
					String time = strs[1] + " " + strs[2];
					time = time.substring(0, 10);
					System.out.println("time" + time);
					String web = "����";
					// ȡcontent����
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

					// ��������
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
		// �滻url
		String urlStr = seedUrl;
		String replaceStr = "page=" + i;
		urlStr = urlStr.replaceAll("page=[0-9]*", replaceStr);
		return urlStr;
	}

}
