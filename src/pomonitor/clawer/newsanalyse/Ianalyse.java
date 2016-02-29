package pomonitor.clawer.newsanalyse;

import java.util.HashMap;
import java.util.List;

public interface Ianalyse {
	/**
	 * �����ж���ҳ��Ҫ��ȡURL
	 * 
	 * @param key
	 *            : Ҫ�����Ĺؼ���
	 * @param isLatest
	 *            : �Ƿ�ֻ��ȡ����ģ�������ȡȫ��
	 * @return
	 */
	public int getPageCount(String key, boolean isLatest);

	/**
	 * ����ÿһҳ������
	 * 
	 * @return
	 */

	public HashMap<String, Object> analyseAnyPage(String url);

	/**
	 * key: Ҫ�����Ĺؼ��� isLatest: �Ƿ�ֻ��ȡ����ģ�������ȡȫ�� �������е�����
	 * 
	 * @return
	 */

	public HashMap<String, Object> analyseAllPage(String key, boolean isLatest);

	/**
	 * ÿ�ζ�urlҪ���еĴ���
	 * 
	 * @param i
	 * @return
	 */
	public String urlAnalyse(int i);

	/**
	 * ��ȡ��Ҫ��ȡ��վ������
	 * 
	 * @return
	 */
	public String getAnalyseWebName();

	/**
	 * �Ƿ񱣳ֵ�ǰץȡ����url�ڿ���
	 */
	public boolean isKeepUrl();

}
