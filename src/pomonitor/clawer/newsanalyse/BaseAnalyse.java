package pomonitor.clawer.newsanalyse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class BaseAnalyse implements Ianalyse {

	// ��Ҫ��ȡ��url
	protected String seedUrl;

	// ����
	protected String params;

	// ��õ������ܽ��
	private int pageCount;

	// ��ǰ��������վ������
	protected String webName;

	// �Ƿ񽫵�ץȡ����url�������

	protected boolean isKeep;

	// �˷������ϣ�ǰ��ʹ��
	public BaseAnalyse(String webName, boolean iskeep) {
		this.webName = webName;
		this.isKeep = iskeep;
	}

	@Override
	public String getAnalyseWebName() {
		return webName;
	}

	@Override
	public abstract int getPageCount(String key, boolean isLatest);

	@Override
	public abstract HashMap<String, Object> analyseAnyPage(String url);

	@Override
	public HashMap<String, Object> analyseAllPage(String key, boolean isLatest) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		// �����Ҫ��ȡ����ҳ��
		pageCount = getPageCount(key, isLatest);

		for (int i = 1; i <= pageCount; i++) {
			try {
				// ÿ��˯�ߣ�Ϊ�˷�ֹ����վ����
				Thread.sleep(1000);
				System.out.println(webName + "վ���ܹ��У�" + pageCount + "��ǰ�ǣ�" + i
						+ "ҳ");
				// ����ҳ���������µ�url
				String newUrl = urlAnalyse(i);
				// ��õ�ǰҳ���ܶ���
				HashMap<String, Object> pageMap = analyseAnyPage(newUrl);

				// �������վ���ܶ�������
				map.putAll(pageMap);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			;
		}

		return map;
	}

	@Override
	public abstract String urlAnalyse(int i);

	@Override
	public boolean isKeepUrl() {

		return isKeep;
	}

}
