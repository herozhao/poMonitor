package pomonitor.clawer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import pomonitor.clawer.newsanalyse.Ianalyse;
import pomonitor.entity.NewsEntity;
import pomonitor.util.MD5Util;

public class NewsCrawler implements ICrawler {

	// 用于存放新闻的map
	private Map<String, NewsEntity> newsMap;

	// 用于控制爬取的控制器
	private CrawlController controller;

	// 存放analyse的列表
	private ArrayList<Ianalyse> analyseList;

	private String fileStore;

	private Frontier frontier;

	public NewsCrawler(String fileStore) {
		newsMap = new HashMap<String, NewsEntity>();
		analyseList = new ArrayList<Ianalyse>();
		this.fileStore = fileStore;
		// 暂且将两者存入一个文件夹内，以后再做重构
		frontier = new Frontier(fileStore);
		frontier.init();
		controller = new CrawlController(fileStore, frontier);
	}

	@Override
	public void addAnalyse(Ianalyse analyse) {
		System.out.println("已添加分析器的网站名：" + analyse.getAnalyseWebName());
		analyseList.add(analyse);
	}

	@Override
	public void clawerAll(String keyWords, boolean isLatest) {
		for (Ianalyse analyse : analyseList) {
			try {
				clawerOneWeb(analyse, keyWords, isLatest);
			} catch (Exception e) {
				System.out.println(analyse.getAnalyseWebName() + "装载失败");
			}
		}

	}

	@Override
	public void clawerOneWeb(Ianalyse analyse, String keyWords, boolean isLatest) {
		HashMap map = analyse.analyseAllPage(keyWords, isLatest);
		String urlPath = fileStore + analyse.getAnalyseWebName() + "/";
		// this.controller= new CrawlController(urlPath);
		System.out.println(analyse.getAnalyseWebName() + "  的url开始装载");
		ArrayList<NewsEntity> list = change(map);
		System.out.println("总共url数目是： " + map.size());
		controller.addUrl(list);
		System.out.println(analyse.getAnalyseWebName() + "  的url装载完毕");

		// controller.start(Crawl.class, 5,true);
	}

	public void start(int crawlCount) {
		System.out.println("正式开始爬取");
		controller.start(DbSaveCrawl.class, crawlCount, true);
	}

	private ArrayList change(HashMap<String, Object> map) {

		ArrayList<NewsEntity> list = new ArrayList<>();
		// 装载url
		Set set = map.keySet();
		Iterator<String> it = set.iterator();

		while (it.hasNext()) {
			String url = it.next();
			String id = MD5Util.MD5(url);
			NewsEntity news = (NewsEntity) map.get(url);
			news.setId(id);
			list.add(news);

		}
		return list;
	}

}
