package pomonitor.clawer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import pomonitor.entity.NewsEntity;
import pomonitor.util.TextFile;

/**
 * ���ڹ�����ȡ��url����
 * 
 * @author ����
 * 
 */
public class Frontier {
	// ������е�NewsEntity����
	private ArrayList<NewsEntity> workingList = new ArrayList<>();
	// �����ж������е�url�Ƿ��Ѿ�����
	private HashMap<String, String> containUrlIds;

	// ���ڴ洢url������ļ���ַ

	private String filePath;

	public Frontier(String filePath) {
		workingList = new ArrayList<NewsEntity>();
		containUrlIds = new HashMap<String, String>();
		this.filePath = filePath;
	}

	// �˷������ڳ�ʼ��һЩ���ݣ���������ݿ��м���workingList����containUrlIds������
	public void init() {
		initContainUrlIds(filePath);
		initUnFinishedURL(filePath);
	}

	// �˷������ڽ�ִ�н�����������ݿ�
	public void stop() {
		storeContainUrlIds(filePath);
		storeUnFinishedURL(filePath);
	}

	// �����ܵ�urlid map
	private void initContainUrlIds(String filePath) {
		String allFileName = filePath + "/containUrlId.txt";
		File file = new File(allFileName);
		if (file.exists()) {
			String urlMapString = TextFile.read(allFileName);
			String urlIdList[] = urlMapString.split(" KKKKKK ");
			for (String id : urlIdList)
				containUrlIds.put(id, "");
			System.out.println("urlMap������ϣ��ܹ���" + urlIdList.length + "��");
		}
	}

	// �洢�ܵ�urlid map
	private void storeContainUrlIds(String filePath) {
		String allFileName = filePath + "/containUrlId.txt";
		File file = new File(allFileName);
		if (file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Set<String> set = containUrlIds.keySet();
		String containString = "";
		for (String s : set) {
			containString += s + " KKKKKK ";
		}
		TextFile.write(allFileName, false, containString);
		System.out.println("urlMap�洢��ϣ�����" + set.size() + "��");
	}

	// �����ϴ�ʧ�ܵ�url
	private void initUnFinishedURL(String filePath) {
		String allFileName = filePath + "/unfinishedURL.txt";
		File file = new File(allFileName);
		if (file.exists()) {
			String urlMapString = TextFile.read(allFileName);
			String entityStrList[] = urlMapString.split(" KKKKKK ");
			for (String entityStr : entityStrList) {
				// >10��Ϊ���ų��е����һ��KKKKKK
				if (entityStr.length() > 10) {
					NewsEntity entity = new NewsEntity();
					String[] fieldStrList = entityStr.split(" GGGGG ");
					entity.setUrl(fieldStrList[0]);
					entity.setTime(fieldStrList[1]);
					entity.setId(fieldStrList[2]);
					entity.setTitle(fieldStrList[3]);
					entity.setWeb(fieldStrList[4]);
					// �����Ժ���ܻ���ȡ�������ݣ���Ҫ�ع�
					workingList.add(entity);
				}
			}
			System.out.println("��δ��ɵ�urlEntity ������ϣ��ܹ���" + workingList.size()
					+ "��");
		}
	}

	// �洢����ʧ�ܵ�url entity
	private void storeUnFinishedURL(String filePath) {
		String allFileName = filePath + "/unfinishedURL.txt";
		File file = new File(allFileName);
		if (file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String storeStr = "";
		int failedCount = 0;
		for (NewsEntity entity : workingList) {
			if (entity.isFailed()) {
				storeStr += entity.getUrl() + " GGGGG " + entity.getTime()
						+ " GGGGG " + entity.getId() + " GGGGG "
						+ entity.getTitle() + " GGGGG " + entity.getWeb()
						+ " KKKKKK ";
				failedCount++;
			}
		}
		TextFile.write(allFileName, false, storeStr);
		System.out.println("����ʧ�ܵ�url�洢��ϣ�����" + failedCount + "��");
	}

	public void addAll(ArrayList<NewsEntity> list) {
		for (NewsEntity news : list) {
			add(news);
		}
	}

	/**
	 * ����ǰ��ȡ��urlʵ��������ץȡ����
	 * 
	 * @param news
	 */
	public void add(NewsEntity news) {
		String idStr = news.getId();
		if (!containUrlIds.containsKey(idStr)) {
			containUrlIds.put(idStr, "");
			workingList.add(news);
			System.out.println(news.getUrl() + ":��ӳɹ�");
		} else {
			System.out.println(news.getUrl() + ":���Ѿ�����");
		}

		workingList.add(news);
	}

	public ArrayList<NewsEntity> distribute(int count) {
		ArrayList<NewsEntity> listTep = new ArrayList<>();
		int myCount = 1;
		for (NewsEntity news : workingList) {
			if (myCount > count)
				return listTep;
			if (!news.isFinish() && !news.isWorking()) {
				listTep.add(news);
				news.setWorking(true);
				myCount++;
			}
		}
		System.out.println("�����ˣ�" + listTep.size() + "��");
		return listTep;

	}

	public long getQueueLength() {
		int count = 0;
		for (NewsEntity news : workingList) {
			if (!news.isFinish() && !news.isWorking()) {
				count++;
			}
		}
		System.out.println("Ŀǰ��ʣ��" + count + "~~~~~~~~~~~~~~~~~~~~");
		return count;
	}

}
