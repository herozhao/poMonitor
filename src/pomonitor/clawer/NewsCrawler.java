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

	// ���ڴ�����ŵ�map
	private Map<String, NewsEntity> newsMap;

	// ���ڿ�����ȡ�Ŀ�����
	private CrawlController controller;

	// ���analyse���б�
	private ArrayList<Ianalyse> analyseList;

	private String fileStore;

	private Frontier frontier;

	public NewsCrawler(String fileStore) {
		newsMap = new HashMap<String, NewsEntity>();
		analyseList = new ArrayList<Ianalyse>();
		this.fileStore = fileStore;
		// ���ҽ����ߴ���һ���ļ����ڣ��Ժ������ع�
		frontier = new Frontier(fileStore);
		frontier.init();
		controller = new CrawlController(fileStore, frontier);
	}

	@Override
	public void addAnalyse(Ianalyse analyse) {
		System.out.println("����ӷ���������վ����" + analyse.getAnalyseWebName());
		analyseList.add(analyse);
	}

	@Override
	public void clawerAll(String keyWords, boolean isLatest) {
		for (Ianalyse analyse : analyseList) {
			try {
				clawerOneWeb(analyse, keyWords, isLatest);
			} catch (Exception e) {
				System.out.println(analyse.getAnalyseWebName() + "װ��ʧ��");
			}
		}

	}

	@Override
	public void clawerOneWeb(Ianalyse analyse, String keyWords, boolean isLatest) {
		HashMap map = analyse.analyseAllPage(keyWords, isLatest);
		String urlPath = fileStore + analyse.getAnalyseWebName() + "/";
		// this.controller= new CrawlController(urlPath);
		System.out.println(analyse.getAnalyseWebName() + "  ��url��ʼװ��");
		ArrayList<NewsEntity> list = change(map);
		System.out.println("�ܹ�url��Ŀ�ǣ� " + map.size());
		controller.addUrl(list);
		System.out.println(analyse.getAnalyseWebName() + "  ��urlװ�����");

		// controller.start(Crawl.class, 5,true);
	}

	public void start(int crawlCount) {
		System.out.println("��ʽ��ʼ��ȡ");
		controller.start(DbSaveCrawl.class, crawlCount, true);
	}

	private ArrayList change(HashMap<String, Object> map) {

		ArrayList<NewsEntity> list = new ArrayList<>();
		// װ��url
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
