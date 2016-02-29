package pomonitor.analyse;

import java.util.List;

import org.junit.Test;

import pomonitor.analyse.articletend.ArticleTendAnalyseRealize;
import pomonitor.analyse.articletend.IArticleTendAnalyse;
import pomonitor.analyse.entity.TendAnalyseArticle;
import pomonitor.analyse.entity.TendSentence;
import pomonitor.analyse.entity.TendWord;
import pomonitor.entity.EntityManagerHelper;
import pomonitor.entity.News;
import pomonitor.entity.NewsDAO;
import pomonitor.entity.NewsTend;
import pomonitor.entity.NewsTendDAO;
import pomonitor.util.PropertiesReader;

/**
 * 倾向性分析最终向外提供的接口
 * 
 * @author zhaolong 2015年12月25日 下午2:11:35
 */
public class TendDiscoveryAnalyse {
	private NewsDAO newsDao;
	private NewsTendDAO newsTendDao;
	private static float POS_CLASS_1;
	private static float POS_CLASS_2;
	private static float POS_CLASS_3;
	private static float NEG_CLASS_1;
	private static float NEG_CLASS_2;
	private static float NEG_CLASS_3;

	static {
		PropertiesReader propertiesReader = new PropertiesReader();
		POS_CLASS_1 = Float.parseFloat(propertiesReader
				.getPropertyByName("POS_CLASS_1"));
		POS_CLASS_2 = Float.parseFloat(propertiesReader
				.getPropertyByName("POS_CLASS_2"));
		POS_CLASS_3 = Float.parseFloat(propertiesReader
				.getPropertyByName("POS_CLASS_3"));
		POS_CLASS_1 = Float.parseFloat(propertiesReader
				.getPropertyByName("POS_CLASS_1"));
		POS_CLASS_2 = Float.parseFloat(propertiesReader
				.getPropertyByName("POS_CLASS_2"));
		POS_CLASS_3 = Float.parseFloat(propertiesReader
				.getPropertyByName("POS_CLASS_3"));
	}

	public TendDiscoveryAnalyse() {
		newsDao = new NewsDAO();
		newsTendDao = new NewsTendDAO();
	}

	/**
	 * 启动分析服务,每次只分析没有完成的，这里用News中的isFinish作为是否分析完成， 1表示没有，0表示已经完成
	 */
	public void startTendAnalyse() {
		NewsDAO newsDao = new NewsDAO();
		// 只拿那些没有做倾向性分析的新闻
		List<News> list = newsDao.findByIsFinsh(1);
		// 创建倾向性分析器
		IArticleTendAnalyse articleTendAnalyse = new ArticleTendAnalyseRealize();
		for (News news : list) {
			try {
				TendAnalyseArticle article = articleTendAnalyse
						.analyseArticleTend(news);
				// 设置分析完成标志
				news.setIsFinsh(0);
				// 更新news的属性
				EntityManagerHelper.beginTransaction();
				newsDao.update(news);
				EntityManagerHelper.commit();
				NewsTend newsTend = new NewsTend();
				newsTend.setDate(news.getTime());
				newsTend.setTendscore(article.getTendScore());
				newsTend.setNewsId(news.getRelId());
				newsTend.setWeb(news.getWeb());
				// 此处的为对级别的计算
				int classValue = tendClassGet(newsTend.getTendscore());
				newsTend.setTendclass(classValue);
				// 持久化分析结果
				EntityManagerHelper.beginTransaction();
				newsTendDao.save(newsTend);
				EntityManagerHelper.commit();
				// 中间部分做分析过程的观察输出，为了测试
				// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
				System.out.println("当前新闻总句子的数目是："
						+ article.getSentences().size());
				System.out.println("当前新闻主题句的数目是："
						+ article.getSubSentences().size());
				System.out.println("当前新闻的倾向性总分为：" + article.getTendScore());
				for (TendSentence sentence : article.getSubSentences()) {
					String allSentenceStr = "";
					for (TendWord word : sentence.getWords()) {
						allSentenceStr += word.getCont();
					}
					System.out.println("当前句子：  " + allSentenceStr);
					System.out.println("主题分数是： " + sentence.getSubjectScore());
					System.out.println("倾向性分数： " + sentence.getTendScore());
				}
				// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 传入时间参数，返回当前的newsTend列表，并返回前台需要的数据形式，形式有待商定,方法有待完成
	 */

	/**
	 * 对级别的计算
	 */
	private int tendClassGet(float score) {
		int classValue = 0;
		if (score >= POS_CLASS_1 && score < POS_CLASS_2)
			classValue = 1;
		if (score >= POS_CLASS_2 && score < POS_CLASS_3)
			classValue = 2;
		if (score >= POS_CLASS_3)
			classValue = 3;
		if (score <= NEG_CLASS_1 && score > NEG_CLASS_1)
			classValue = -1;
		if (score <= NEG_CLASS_1 && score > NEG_CLASS_1)
			classValue = -2;
		if (score <= NEG_CLASS_1)
			classValue = -3;
		return classValue;
	}

	@Test
	public void test() {
		startTendAnalyse();

	}

}
