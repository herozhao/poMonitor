package pomonitor.analyse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import pomonitor.analyse.articletend.ArticleTendAnalyseRealize;
import pomonitor.analyse.entity.TendAnalyseArticle;
import pomonitor.entity.EntityManagerHelper;
import pomonitor.entity.News;
import pomonitor.entity.NewsDAO;
import pomonitor.entity.NewsTend;
import pomonitor.entity.NewsTendDAO;
import pomonitor.util.PropertiesReader;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author xiaoyulun 2016年1月5日 上午11:44:52
 */
public class ArticleTendAnalyse {
	private static int positiveScore;
	private static int negativeScore;

	static {
		PropertiesReader propertiesReader = new PropertiesReader();
		positiveScore = Integer.parseInt(propertiesReader
				.getPropertyByName("PositiveScore"));
		negativeScore = Integer.parseInt(propertiesReader
				.getPropertyByName("NegativeScore"));
	}

	public static void tendAnalyse(String start_time, String end_time,
			String UserId) {
		NewsDAO newsDAO = new NewsDAO();
		List<News> newsList = newsDAO.findBetweenDate(start_time, end_time);
		HashMap<String, Float> hashMap = new HashMap<>();
		ArticleTendAnalyseRealize analyseRealize = new ArticleTendAnalyseRealize();
		for (News news : newsList) {
			if (news.getIsFinsh() == 0) {
				continue;
			}
			TendAnalyseArticle tendArticle = new TendAnalyseArticle();

			try {
				tendArticle = analyseRealize.analyseArticleTend(news);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("文本过长");
				continue;
			}
			String webName = tendArticle.getWeb();
			Float score = tendArticle.getTendScore();
			InsertNewsTend(news, newsDAO, score);
		}
	}

	/**
	 * 从newstend表中返回数据 showWebTend
	 * 
	 * @param start_time
	 * @param end_time
	 * @param UserId
	 * @return
	 */
	public HashMap<String, WebScore> showWebTend(String start_time,
			String end_time, String UserId) {

		NewsTendDAO newsTendDAO = new NewsTendDAO();
		List<NewsTend> newsList = newsTendDAO.findBetweenDate(start_time,
				end_time);
		System.out.println(newsList.size());
		HashMap<String, WebScore> hashMap = new HashMap<>();
		for (NewsTend news : newsList) {
			String webName = news.getWeb();
			Float score = news.getTendscore();

			if (hashMap.get(webName) == null) {
				WebScore webScore = new WebScore();
				webScore.setScore(score);
				hashMap.put(webName, webScore);
			} else {
				WebScore webScore = hashMap.get(webName);
				webScore.setScore(score);
				hashMap.put(webName, webScore);
			}
		}
		return hashMap;
	}

	/**
	 * 将计算完后的新闻，插入newstend表中,并更改news的IsFinished字段 InsertNewsTend
	 * 
	 * @param news
	 * @param newsDAO
	 * @param tendScore
	 */
	private static void InsertNewsTend(News news, NewsDAO newsDAO,
			Float tendScore) {
		news.setIsFinsh(0);
		NewsTend newsTend = new NewsTend();
		newsTend.setDate(news.getTime());
		// try {
		// newsTend.setWeb(new String(gbk2utf8(news.getWeb()), "utf-8"));
		// System.out.println(newsTend.getWeb()
		// + "*****************************88888");
		// } catch (UnsupportedEncodingException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		newsTend.setWeb(ChineseWebNameToEng(news.getWeb()));
		newsTend.setTendscore(tendScore);
		newsTend.setNewsId(news.getRelId());
		if (newsTend.getTendscore() > 1) {
			newsTend.setTendclass(1);
		} else if (newsTend.getTendscore() < -1) {
			newsTend.setTendclass(-1);
		} else {
			newsTend.setTendclass(0);
		}
		NewsTendDAO newsTendDAO = new NewsTendDAO();
		try {
			EntityManagerHelper.beginTransaction();
			newsTendDAO.save(newsTend);
			EntityManagerHelper.commit();

			EntityManagerHelper.beginTransaction();
			newsDAO.update(news);
			EntityManagerHelper.commit();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String GenerateJSon(String start_time, String end_time, String UserId) {
		String resJson = "";

		// tendAnalyse(start_time, end_time, UserId);
		HashMap<String, WebScore> hashMap = showWebTend(start_time, end_time,
				UserId);
		Result result = new Result();
		Test test = new Test();
		for (Iterator iterator = hashMap.keySet().iterator(); iterator
				.hasNext();) {
			String webName = (String) iterator.next();
			WebScore webScore = hashMap.get(webName);
			int all = webScore.allSize();
			result.xAxis.add(EnglishWebNameToChinese(webName));
			result.series[0].data.add(webScore.pos * 1.0 / all);
			result.series[1].data.add(webScore.obj * 1.0 / all);
			result.series[2].data.add(webScore.neg * 1.0 / all);
		}
		test.message = "Query Success";
		test.status = 0;
		test.results = result;
		resJson = JSON.toJSONString(test);
		return resJson;
	}

	public static String ChineseWebNameToEng(String webname) {
		switch (webname) {
		case "光明网":
			webname = "GuangMing";
			break;
		case "凤凰":
			webname = "FengHuang";
			break;
		case "华声在线":
			webname = "HuaSheng";
			break;
		case "搜狐":
			webname = "SouHu";
			break;
		case "新华网":
			webname = "XinHua";
			break;
		case "新浪":
			webname = "Sina";
			break;
		case "红网":
			webname = "RedNet";
			break;
		case "红网论坛":
			webname = "RedNetBBS";
			break;
		case "网易":
			webname = "WangYi";
			break;
		case "腾讯":
			webname = "TengXun";
			break;
		default:
			break;
		}
		return webname;
	}

	public static String EnglishWebNameToChinese(String webname) {
		switch (webname) {
		case "GuangMing":
			webname = "光明网";
			break;
		case "FengHuang":
			webname = "凤凰";
			break;
		case "HuaSheng":
			webname = "华声在线";
			break;
		case "SouHu":
			webname = "搜狐";
			break;
		case "XinHua":
			webname = "新华网";
			break;
		case "Sina":
			webname = "新浪";
			break;
		case "RedNet":
			webname = "红网";
			break;
		case "RedNetBBS":
			webname = "红网论坛";
			break;
		case "WangYi":
			webname = "网易";
			break;
		case "TengXun":
			webname = "腾讯";
			break;
		default:
			break;
		}
		return webname;
	}

	/**
	 * 构造json数据的内部类
	 */

	class WebScore {
		public int pos; // 正面情感新闻数量
		public int obj; // 客观情感新闻数量
		public int neg; // 负面情感新闻数量

		public void setScore(float score) {
			if (score > positiveScore) {
				pos++;
			} else if (score < negativeScore) {
				neg++;
			} else {
				obj++;
			}
		}

		public int allSize() {
			return pos + obj + neg;
		}
	}

	class Series {
		public ArrayList<Double> data;
		public String name;

		public Series() {
			data = new ArrayList<>();
			name = "";
		}
	}

	class Result {
		public Series[] series;
		public ArrayList<String> xAxis;

		public Result() {
			series = new Series[3];
			for (int i = 0; i < 3; i++) {
				series[i] = new Series();
			}
			series[0].name = "褒";
			series[1].name = "中";
			series[2].name = "贬";
			xAxis = new ArrayList<>();
		}
	}

	class Test {
		public String message;
		public int status;
		public Result results;

		// public Test(String message, String status, Result result) {
		// this.messag = message;
		// this.status = status;
		// this.result = result;
		// }

	}
}
