package pomonitor.clawer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import pomonitor.entity.EntityManagerHelper;
import pomonitor.entity.News;
import pomonitor.entity.NewsDAO;
import pomonitor.entity.NewsEntity;
import pomonitor.util.NewsAndNewsEnriryTran;
import pomonitor.util.TextFile;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;

/**
 * 存新闻于数据库的爬虫
 * 
 * @author zhaolong 2015年12月14日 下午9:51:41
 */
public class DbSaveCrawl implements Runnable {

	private List<NewsEntity> worksList;

	private TextFile textFile;

	private int i = 1;

	private String filePath;

	private int id;

	private NewsDAO newsDao = new NewsDAO();

	public void init(int id, String filePath) {
		this.id = id;
		textFile = new TextFile();
		this.filePath = filePath;
	}

	public List<NewsEntity> getWorksList() {
		return worksList;
	}

	public void setWorksList(List<NewsEntity> worksList) {

		this.worksList = worksList;
		System.out.println("爬虫" + id + "  第    " + i + "添加" + worksList.size()
				+ "个");
		for (NewsEntity entity : worksList) {
			entity.setWorking(true);
		}
	}

	@Override
	public void run() {
		EntityManagerHelper.beginTransaction();

		while (worksList.size() > 0) {
			Iterator<NewsEntity> it = worksList.iterator();

			if (it.hasNext()) {
				NewsEntity entity = it.next();
				if (!entity.isFinish()) {
					String url = entity.getUrl();
					String htmlStr = "";

					URL u;
					try {
						u = new URL(url);
						Document doc = Jsoup.parse(u, 3000);
						htmlStr = doc.html();
					} catch (MalformedURLException e1) {
						e1.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

					// 自定义的发送请求的方式不稳定，有乱码，留到后期解决
					// htmlStr = sender.sendGet(url);

					if (htmlStr.equals("")) {
						// 失败一次记录下来
						entity.countFailed();
						System.out.println("爬虫id  " + id);
						System.out.println(url + ":失败"
								+ entity.getFailedCount() + "  次");
						if (entity.isFailed()) {
							entity.setWorking(false);
							worksList.remove(entity);
						}

					} else {
						// 做获取kewwords的处理
						List<String> keyWords = KeyWords.KdWords(htmlStr);
						entity.setKeywords(keyWords);

						System.out.println("获取到的keywords:" + keyWords);

						// 此处将文本打印并输出,并做相应处理
						// System.out.println(htmlStr);
						String fileDir = filePath + entity.getWeb() + "/";

						String content = "";
						try {
							content = ArticleExtractor.INSTANCE
									.getText(htmlStr);
						} catch (BoilerpipeProcessingException e) {
							e.printStackTrace();
							content = "解释失败";
						} catch (Exception e) {
							e.printStackTrace();
							content = "解析失败";
						}

						entity.setFinish(true);
						entity.setWorking(false);
						worksList.remove(entity);

						// 如果获取到的文本不足20个字符则，不做存储
						if (content.length() > 20) {
							entity.setContentPath(fileDir);
							entity.setAllContent(content);
							// 做使用对象和存储对象之间的转换
							News news = NewsAndNewsEnriryTran
									.newsEntityToNews(entity);
							// 持久化存储对象 news
							try {
								// EntityManagerHelper.beginTransaction();
								newsDao.save(news);
								// EntityManagerHelper.commit();
							} catch (Exception e) {
								System.out.println("持久化失败");
								e.printStackTrace();
							}

						}
						System.out.println("爬虫id  " + id);
						System.out.println(entity.getUrl() + "  成功爬取文件"
								+ entity.getId());

					}
					if (worksList.size() <= 0)
						break;
				}
			}
		}
		EntityManagerHelper.commit();
	}
}
