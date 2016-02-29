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
 * �����������ݿ������
 * 
 * @author zhaolong 2015��12��14�� ����9:51:41
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
		System.out.println("����" + id + "  ��    " + i + "���" + worksList.size()
				+ "��");
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

					// �Զ���ķ�������ķ�ʽ���ȶ��������룬�������ڽ��
					// htmlStr = sender.sendGet(url);

					if (htmlStr.equals("")) {
						// ʧ��һ�μ�¼����
						entity.countFailed();
						System.out.println("����id  " + id);
						System.out.println(url + ":ʧ��"
								+ entity.getFailedCount() + "  ��");
						if (entity.isFailed()) {
							entity.setWorking(false);
							worksList.remove(entity);
						}

					} else {
						// ����ȡkewwords�Ĵ���
						List<String> keyWords = KeyWords.KdWords(htmlStr);
						entity.setKeywords(keyWords);

						System.out.println("��ȡ����keywords:" + keyWords);

						// �˴����ı���ӡ�����,������Ӧ����
						// System.out.println(htmlStr);
						String fileDir = filePath + entity.getWeb() + "/";

						String content = "";
						try {
							content = ArticleExtractor.INSTANCE
									.getText(htmlStr);
						} catch (BoilerpipeProcessingException e) {
							e.printStackTrace();
							content = "����ʧ��";
						} catch (Exception e) {
							e.printStackTrace();
							content = "����ʧ��";
						}

						entity.setFinish(true);
						entity.setWorking(false);
						worksList.remove(entity);

						// �����ȡ�����ı�����20���ַ��򣬲����洢
						if (content.length() > 20) {
							entity.setContentPath(fileDir);
							entity.setAllContent(content);
							// ��ʹ�ö���ʹ洢����֮���ת��
							News news = NewsAndNewsEnriryTran
									.newsEntityToNews(entity);
							// �־û��洢���� news
							try {
								// EntityManagerHelper.beginTransaction();
								newsDao.save(news);
								// EntityManagerHelper.commit();
							} catch (Exception e) {
								System.out.println("�־û�ʧ��");
								e.printStackTrace();
							}

						}
						System.out.println("����id  " + id);
						System.out.println(entity.getUrl() + "  �ɹ���ȡ�ļ�"
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
