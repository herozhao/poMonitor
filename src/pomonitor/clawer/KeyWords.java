package pomonitor.clawer;

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
				String[] keyword = keys.split("[\\pP]");
				int i = keyword.length;
				for (int j = 0; j < i; j++) {
					keyAnddesc.add(j, keyword[j]);
				}
			} else if (k.attr("name").equals("description")) {
				desc = k.attr("content");
			}
		}
		return keyAnddesc;
	}

}
