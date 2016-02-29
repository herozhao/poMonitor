package pomonitor.test;

import org.junit.Test;

import pomonitor.analyse.articlesubanalyse.SentenceSubCountByWeightAverage;
import pomonitor.analyse.articlesubanalyse.SubScoreAddPos;
import pomonitor.analyse.articlesubanalyse.SubScoreAddThink;
import pomonitor.analyse.articlesubanalyse.SubScoreAddTitle;
import pomonitor.analyse.articlesubanalyse.SubSentenceGet;
import pomonitor.analyse.articletend.ArticlePreAnalyse;
import pomonitor.analyse.articletend.ArticleSplier;
import pomonitor.analyse.entity.TendAnalyseArticle;
import pomonitor.analyse.entity.TendSentence;
import pomonitor.analyse.entity.TendWord;
import pomonitor.entity.News;
import pomonitor.entity.NewsDAO;
import pomonitor.entity.NewsEntity;
import pomonitor.util.NewsAndNewsEnriryTran;

public class TestArticlePreAnalyse {
	@Test
	public void testGetPreArticle() {

		ArticlePreAnalyse apa = new ArticlePreAnalyse(new ArticleSplier());
		NewsDAO newsDao = new NewsDAO();
		News news = newsDao.findById(8);
		NewsEntity newsEntity = NewsAndNewsEnriryTran.newsToNewsEntity(news);
		TendAnalyseArticle article = apa.getPreArticle(newsEntity);
		System.out.println(article.getSentences());
		System.out.println("title��keyword��ȡʵ��֮��" + article.getSet());
	}

	@Test
	public void testSubSetenceGet() {
		ArticlePreAnalyse apa = new ArticlePreAnalyse(new ArticleSplier());
		NewsDAO newsDao = new NewsDAO();
		News news = newsDao.findById(678);
		NewsEntity newsEntity = NewsAndNewsEnriryTran.newsToNewsEntity(news);
		TendAnalyseArticle article = apa.getPreArticle(newsEntity);
		System.out.println(article.getSentences());
		System.out.println("title��keyword��ȡʵ��֮��" + article.getSet());
		SubSentenceGet ssg = new SubSentenceGet(
				new SentenceSubCountByWeightAverage());
		ssg.addScoreAdder(new SubScoreAddPos());
		ssg.addScoreAdder(new SubScoreAddThink());
		ssg.addScoreAdder(new SubScoreAddTitle());
		ssg.getSubSentence(3, article);
		for (TendSentence sentence : article.getSubSentences()) {
			String allSentence = "";
			for (TendWord td : sentence.getWords()) {
				allSentence += td.getCont();
			}
			System.out.println("������ţ�" + sentence.getId());
			System.out.println("��ǰ���ӣ�" + allSentence);
			System.out.println("λ�üӷ֣�" + sentence.getPosScore());
			System.out.println("���Ŵʼӷ֣�" + sentence.getThinkScore());
			System.out.println("title�ӷ�" + sentence.getTitleScore());
			System.out.println("�ܷ�" + sentence.getSubjectScore());
		}
	}
}
