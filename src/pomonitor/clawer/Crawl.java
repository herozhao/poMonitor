package pomonitor.clawer;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import pomonitor.util.TextFile;
import pomonitor.entity.NewsEntity;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;

/**
 * 
 * @author xiaoyulun 2015��12��14�� ����12:04:55
 */
public class Crawl implements Runnable {

	private List<NewsEntity> worksList;

	private TextFile textFile;

	private int i = 1;

	private String filePath;

	private int id;

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
						File file = new File(fileDir);
						if (!file.exists())
							file.mkdir();
						String filaNameTime = entity.getTime().substring(0, 4)
								+ "___" + System.currentTimeMillis() + ".txt";
						String allPath = fileDir + filaNameTime;
						System.out.println(allPath);
						String content = "";
						try {
							content = ArticleExtractor.INSTANCE
									.getText(htmlStr);
						} catch (BoilerpipeProcessingException e) {
							e.printStackTrace();
							content = "����ʧ��";
						}
						// �����ȡ�����ı�����20���ַ��򣬲����洢
						if (content.length() > 20) {
							String allContent = entity.getUrl() + "###"
									+ entity.getTitle() + "###" + content;
							textFile.write(allPath, false, allContent);
							entity.setContentPath(allPath);
						}
						System.out.println(this.hashCode());
						System.out.println("����id  " + id);
						System.out.println(entity.getUrl() + "  �ɹ���ȡ�ļ�"
								+ entity.getId());
						entity.setFinish(true);
						entity.setWorking(false);
						worksList.remove(entity);
					}
					if (worksList.size() <= 0)
						break;
				}
			}
		}
	}

}
