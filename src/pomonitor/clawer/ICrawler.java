package pomonitor.clawer;

import pomonitor.clawer.newsanalyse.Ianalyse;

public interface ICrawler {

	/**
	 * 添加需要爬取的网站的analyse
	 * 
	 * @param analyse
	 */
	public void addAnalyse(Ianalyse analyse);

	/**
	 * 开始爬取所有网站
	 * 
	 * @param analyse
	 * @param controller
	 */
	public void clawerAll(String keyWords, boolean isLatest);

	/**
	 * 爬取一个网站
	 * 
	 * @param analyse
	 * @param controller
	 * @param keyWords
	 * @param isLatest
	 */
	public void clawerOneWeb(Ianalyse analyse, String keyWords, boolean isLatest);

}
