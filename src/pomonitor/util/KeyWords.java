package pomonitor.util;

/**
 * ��ȡ�ؼ���
 */
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class KeyWords {
	// private static String stHtml;

	public static List<String> KdWords(String stHtml) {
		List<String> keyAnddesc = new ArrayList<String>();
		Document doc = Jsoup.parse(stHtml);
		Elements e = doc.getElementsByTag("head");
		Elements kd = e.select("meta");
		String keys = null;
		String desc = null;
		for (Element k : kd) {
			if (k.attr("name").toLowerCase().equals("keywords")) {
				keys = k.attr("content");
				// ƥ���ַ����Ϳո񣬲���ͨ�����ָ�
				String[] keyword = keys.split("[\\s]*[\\pP]*[\\s]*");
				int i = keyword.length;

				for (int j = 0; j < i; j++) {
					if (!keyword[j].trim().equals(""))
						keyAnddesc.add(j, keyword[j].trim());
					// System.out.println(keyAnddesc.get(j));
				}
			} else if (k.attr("name").equals("description")) {
				desc = k.attr("content");
			}
		}
		return keyAnddesc;
	}

}
