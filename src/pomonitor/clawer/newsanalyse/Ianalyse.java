package pomonitor.clawer.newsanalyse;

import java.util.HashMap;
import java.util.List;

public interface Ianalyse {
	/**
	 * 计算有多少页需要爬取URL
	 * 
	 * @param key
	 *            : 要搜索的关键字
	 * @param isLatest
	 *            : 是否只爬取最近的，否则爬取全部
	 * @return
	 */
	public int getPageCount(String key, boolean isLatest);

	/**
	 * 分析每一页的数据
	 * 
	 * @return
	 */

	public HashMap<String, Object> analyseAnyPage(String url);

	/**
	 * key: 要搜索的关键字 isLatest: 是否只爬取最近的，否则爬取全部 分析所有的数据
	 * 
	 * @return
	 */

	public HashMap<String, Object> analyseAllPage(String key, boolean isLatest);

	/**
	 * 每次对url要进行的处理
	 * 
	 * @param i
	 * @return
	 */
	public String urlAnalyse(int i);

	/**
	 * 获取需要爬取网站的名字
	 * 
	 * @return
	 */
	public String getAnalyseWebName();

	/**
	 * 是否保持当前抓取到的url在库中
	 */
	public boolean isKeepUrl();

}
