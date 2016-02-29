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
 * ���������Ž���
 * 
 * @author ����
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
		// ����Ҫ�����Ĺؼ���
		String searchKeyStr = "&q=" + key;

		String searchTimeStr = "&time_scope=";

		if (isLatest) {
			// �����������
			searchTimeStr += 7;
		} else {
			// ������������
			searchTimeStr += 0;
		}

		// ����Ҫ�����Ĳ���
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
		System.out.println("���У�" + pageCount + "ҳ");
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
					// ��ȡtitle
					Elements titleE = e.getElementsByAttributeValue("class",
							"news_title");
					String title = titleE.tagName("a").text();
					// ��ȡurl
					Node urlHref = titleE.first().childNode(1);
					String url = urlHref.attr("href");
					// ��ȡʱ��
					Elements timeE = e.getElementsByAttributeValue("class",
							"news_other");
					String time = timeE.html();
					time = time.split(" ")[1];
					// ��ȡ���
					Elements contentE = e.getElementsByAttributeValue("class",
							"news_content");
					String content = contentE.text();
					// ���
					System.out.println("title:" + title);
					// System.out.println("web:"+web);
					System.out.println("time:" + time);
					System.out.println("content:" + content);
					System.out.println("url:" + url);
					// ��������
					redEntity.setUrl(url);
					redEntity.setContent(content);
					redEntity.setTime(time);
					redEntity.setWeb(webName);
					redEntity.setTitle(title);
					// ����ȡ���Ķ������map
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
