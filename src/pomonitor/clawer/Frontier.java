package pomonitor.clawer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import pomonitor.entity.NewsEntity;
import pomonitor.util.TextFile;

/**
 * 用于管理爬取的url队列
 * 
 * @author 赵龙
 * 
 */
public class Frontier {
	// 存放所有的NewsEntity对象
	private ArrayList<NewsEntity> workingList = new ArrayList<>();
	// 用于判断其所有的url是否已经加入
	private HashMap<String, String> containUrlIds;

	// 用于存储url保存的文件地址

	private String filePath;

	public Frontier(String filePath) {
		workingList = new ArrayList<NewsEntity>();
		containUrlIds = new HashMap<String, String>();
		this.filePath = filePath;
	}

	// 此方法用于初始化一些数据，例如从数据库中加载workingList，和containUrlIds的数据
	public void init() {
		initContainUrlIds(filePath);
		initUnFinishedURL(filePath);
	}

	// 此方法用于将执行结果保存入数据库
	public void stop() {
		storeContainUrlIds(filePath);
		storeUnFinishedURL(filePath);
	}

	// 加载总的urlid map
	private void initContainUrlIds(String filePath) {
		String allFileName = filePath + "/containUrlId.txt";
		File file = new File(allFileName);
		if (file.exists()) {
			String urlMapString = TextFile.read(allFileName);
			String urlIdList[] = urlMapString.split(" KKKKKK ");
			for (String id : urlIdList)
				containUrlIds.put(id, "");
			System.out.println("urlMap加载完毕，总共：" + urlIdList.length + "条");
		}
	}

	// 存储总的urlid map
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
		System.out.println("urlMap存储完毕，共：" + set.size() + "条");
	}

	// 加载上次失败的url
	private void initUnFinishedURL(String filePath) {
		String allFileName = filePath + "/unfinishedURL.txt";
		File file = new File(allFileName);
		if (file.exists()) {
			String urlMapString = TextFile.read(allFileName);
			String entityStrList[] = urlMapString.split(" KKKKKK ");
			for (String entityStr : entityStrList) {
				// >10是为了排除切到最后一个KKKKKK
				if (entityStr.length() > 10) {
					NewsEntity entity = new NewsEntity();
					String[] fieldStrList = entityStr.split(" GGGGG ");
					entity.setUrl(fieldStrList[0]);
					entity.setTime(fieldStrList[1]);
					entity.setId(fieldStrList[2]);
					entity.setTitle(fieldStrList[3]);
					entity.setWeb(fieldStrList[4]);
					// 这里以后可能还会取更多内容，需要重构
					workingList.add(entity);
				}
			}
			System.out.println("尚未完成的urlEntity 加载完毕，总共：" + workingList.size()
					+ "条");
		}
	}

	// 存储访问失败的url entity
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
		System.out.println("访问失败的url存储完毕，共：" + failedCount + "条");
	}

	public void addAll(ArrayList<NewsEntity> list) {
		for (NewsEntity news : list) {
			add(news);
		}
	}

	/**
	 * 将当前爬取的url实体放入对列抓取对列
	 * 
	 * @param news
	 */
	public void add(NewsEntity news) {
		String idStr = news.getId();
		if (!containUrlIds.containsKey(idStr)) {
			containUrlIds.put(idStr, "");
			workingList.add(news);
			System.out.println(news.getUrl() + ":添加成功");
		} else {
			System.out.println(news.getUrl() + ":早已经加入");
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
		System.out.println("分配了：" + listTep.size() + "个");
		return listTep;

	}

	public long getQueueLength() {
		int count = 0;
		for (NewsEntity news : workingList) {
			if (!news.isFinish() && !news.isWorking()) {
				count++;
			}
		}
		System.out.println("目前还剩：" + count + "~~~~~~~~~~~~~~~~~~~~");
		return count;
	}

}
