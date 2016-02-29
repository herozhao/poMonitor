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
 * ������̳����
 * 
 * @author ����
 * 
 */
public class RedNetAnalyseF extends BaseAnalyse {

	public RedNetAnalyseF(String webName, boolean isKeep) {
		super(webName, isKeep);
		// url
		seedUrl = "http://s.rednet.cn/?";
		// ����
		params = "scope=4&page_size=50&orderby=1&page=1";
	}

	@Override
	public int getPageCount(String key, boolean isLatest) {
		// �Ժ��ֽ��б������
		key = URLencode.escape(key);
		System.out.println(key);

		// ����Ҫ�����Ĺؼ���
		String searchKeyStr = "&q=" + key;

		// ����������Χ
		String searchTimeStr = "&date_range=";

		if (isLatest) {
			// �������һ������
			searchTimeStr += "2";
		} else {
			// ������������
			searchTimeStr += "0";

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
			doc = Jsoup.parse(url, 1000);
			// System.out.println(doc.html());

			// ��ȡ������������ַ���
			Elements listEle = doc.getElementsByAttributeValue("class",
					"bold-font");
			String countStr = listEle.get(0).text().trim();
			System.out.println(countStr);

			// ת��Ϊint
			count = Integer.parseInt(countStr);
			System.out.println(count);

			// ������ҳ��
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
			// �����������õ�����
			URL url = new URL(Strurl);
			Document doc = Jsoup.parse(url, 5000);
			// ��ȡÿһ��
			Elements listEle = doc.getElementsByAttributeValue("class",
					"module");

			// ����������
			for (Element e : listEle) {
				NewsEntity RedEntity = new NewsEntity();

				// ��ȡtitle
				Elements titleE = e.getElementsByAttributeValue("class",
						"title");
				String titleAndTime = titleE.tagName("a").text();
				String[] strArray = titleAndTime.split("���");
				String title = strArray[0];
				title = title.replaceAll("<b>", "");
				title = title.replaceAll("</b>", "");

				// ��ȡʱ��
				System.out.println(titleAndTime + "~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println(title);
				String timeStr = strArray[1];
				System.out.println(timeStr);
				String time = "";
				try {
					time = timeStr.substring(timeStr.length() - 16);
				} catch (Exception e2) {
					// ����ȡʱ��������������������ʽΪ����Сʱǰ���������Լ�����ʱ��
					long timel = System.currentTimeMillis();
					Date date = new Date(timel);
					time = date.getYear() + 1900 + "-" + date.getMonth() + "-"
							+ date.getDate();
				}
				time = time.substring(0, 10);

				String web = "����_��̳";

				// ��ȡurl
				Elements urlE = e.getElementsByAttributeValue("class", "link");
				String getUrl = urlE.text();

				// ��ȡcontent
				Elements contentE = e.getElementsByAttributeValue("class",
						"content");
				String content = contentE.text();

				System.out.println("title:" + title);
				System.out.println("web:" + web);
				System.out.println("time:" + time);
				System.out.println("content:" + content);
				System.out.println("url:" + getUrl);

				// �˴���ȡ����url��������������Ҫ����
				if (getUrl.contains("��")) {
					getUrl = getUrl.replaceAll("��", "&pi");
					getUrl = urlChange(getUrl);
					System.out.println(getUrl + "~~~~~~~~~~");
				}

				// �������
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
