package pomonitor.clawer;

import pomonitor.clawer.newsanalyse.Ianalyse;

public interface ICrawler {

	/**
	 * �����Ҫ��ȡ����վ��analyse
	 * 
	 * @param analyse
	 */
	public void addAnalyse(Ianalyse analyse);

	/**
	 * ��ʼ��ȡ������վ
	 * 
	 * @param analyse
	 * @param controller
	 */
	public void clawerAll(String keyWords, boolean isLatest);

	/**
	 * ��ȡһ����վ
	 * 
	 * @param analyse
	 * @param controller
	 * @param keyWords
	 * @param isLatest
	 */
	public void clawerOneWeb(Ianalyse analyse, String keyWords, boolean isLatest);

}
