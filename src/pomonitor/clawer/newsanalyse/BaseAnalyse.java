package pomonitor.clawer.newsanalyse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class BaseAnalyse implements Ianalyse {

	// 需要爬取的url
	protected String seedUrl;

	// 参数
	protected String params;

	// 获得的搜索总结果
	private int pageCount;

	// 当前所分析网站的名字
	protected String webName;

	// 是否将当抓取到的url存入库中

	protected boolean isKeep;

	// 此方法作废，前期使用
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
		// 获得需要爬取的总页数
		pageCount = getPageCount(key, isLatest);

		for (int i = 1; i <= pageCount; i++) {
			try {
				// 每次睡眠，为了防止被网站屏蔽
				Thread.sleep(1000);
				System.out.println(webName + "站：总共有：" + pageCount + "当前是：" + i
						+ "页");
				// 根据页数，构造新的url
				String newUrl = urlAnalyse(i);
				// 获得当前页的总对象
				HashMap<String, Object> pageMap = analyseAnyPage(newUrl);

				// 添加入网站的总对象里面
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
