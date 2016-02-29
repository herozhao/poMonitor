package pomonitor.analyse.articletend;

import java.util.List;

import org.junit.Test;

import pomonitor.analyse.articlesubanalyse.SentenceSubCountByWeightAverage;
import pomonitor.analyse.articlesubanalyse.SubScoreAddPos;
import pomonitor.analyse.articlesubanalyse.SubScoreAddThink;
import pomonitor.analyse.articlesubanalyse.SubScoreAddTitle;
import pomonitor.analyse.articlesubanalyse.SubSentenceGet;
import pomonitor.analyse.entity.TendAnalyseArticle;
import pomonitor.analyse.entity.TendSentence;
import pomonitor.analyse.entity.TendWord;
import pomonitor.entity.News;
import pomonitor.entity.NewsDAO;
import pomonitor.entity.NewsEntity;
import pomonitor.util.NewsAndNewsEnriryTran;

/**
 * 文章倾向性分析的实现
 * 
 * @author zhaolong 2015年12月24日 下午3:24:28
 */
public class ArticleTendAnalyseRealize implements IArticleTendAnalyse {
	// 文章预处理器
	private ArticlePreAnalyse preAnalyser;

	// 主题句抽取器
	private SubSentenceGet subSentenceGetter;

	// 当前正在处理的文章对象
	private TendAnalyseArticle nowArticle;

	// 句子倾向性分析器，主要用来对主题句做倾向性分析
	private ISentenceTendAnalyse sentenceTendAnalyser;

	/**
	 * 构造方法构造一些需要用到的器具
	 */
	public ArticleTendAnalyseRealize() {
		// 传入文章拆分器
		preAnalyser = new ArticlePreAnalyse(new ArticleSplier());
		// 传入主题影响因子的加权算法
		subSentenceGetter = new SubSentenceGet(
				new SentenceSubCountByWeightAverage());
		// 加载三种主题影响因子，分别是位置，主张词，以及title和关键字
		subSentenceGetter.addScoreAdder(new SubScoreAddPos());
		subSentenceGetter.addScoreAdder(new SubScoreAddThink());
		subSentenceGetter.addScoreAdder(new SubScoreAddTitle());
		// 构造句子倾向性分析器
		sentenceTendAnalyser = new SentenceTendAnalyseByCenture();
	}

	@Override
	public TendAnalyseArticle analyseArticleTend(News news) {
		// 先将数据库对象转化为内存对象
		NewsEntity newsEntity = NewsAndNewsEnriryTran.newsToNewsEntity(news);
		// 对newsEntity做预处理转化为Article分析对象
		nowArticle = preAnalyser.getPreArticle(newsEntity);
		// 提取主题句
		subSentenceGetter.getSubSentence(3, nowArticle);
		// 记录文章倾向性总分
		float tendScore = 0;
		for (TendSentence sentence : nowArticle.getSubSentences()) {
			sentenceTendAnalyser.analyseSentenceTend(sentence);
			// 这里只是将主题句的倾向性总分做累加，有待优化，没有考虑主题句的主题分数，
			// 或者考虑全体句子的倾向性总分会更好，为了分数不至于太大也可以做平均化处理
			tendScore += sentence.getTendScore();
		}
		nowArticle.setTendScore(tendScore);
		return nowArticle;
	}

	@Test
	public void test() {
		NewsDAO newsDao = new NewsDAO();
		News news = newsDao.findById(54);
		ArticleTendAnalyseRealize articleTendAnalyse = new ArticleTendAnalyseRealize();
		TendAnalyseArticle article = articleTendAnalyse
				.analyseArticleTend(news);
		System.out.println("当前新闻总句子的数目是：" + article.getSentences().size());
		System.out.println("当前新闻主题句的数目是：" + article.getSubSentences().size());
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
	}

	@Test
	public void test2() {
		NewsDAO newsDao = new NewsDAO();
		List<News> list = newsDao.findByWeb("新浪");
		ArticleTendAnalyseRealize articleTendAnalyse = new ArticleTendAnalyseRealize();
		for (News news : list) {
			try {
				TendAnalyseArticle article = articleTendAnalyse
						.analyseArticleTend(news);
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
