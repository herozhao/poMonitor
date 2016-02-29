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
 * ���������Է�����ʵ��
 * 
 * @author zhaolong 2015��12��24�� ����3:24:28
 */
public class ArticleTendAnalyseRealize implements IArticleTendAnalyse {
	// ����Ԥ������
	private ArticlePreAnalyse preAnalyser;

	// ������ȡ��
	private SubSentenceGet subSentenceGetter;

	// ��ǰ���ڴ�������¶���
	private TendAnalyseArticle nowArticle;

	// ���������Է���������Ҫ������������������Է���
	private ISentenceTendAnalyse sentenceTendAnalyser;

	/**
	 * ���췽������һЩ��Ҫ�õ�������
	 */
	public ArticleTendAnalyseRealize() {
		// �������²����
		preAnalyser = new ArticlePreAnalyse(new ArticleSplier());
		// ��������Ӱ�����ӵļ�Ȩ�㷨
		subSentenceGetter = new SubSentenceGet(
				new SentenceSubCountByWeightAverage());
		// ������������Ӱ�����ӣ��ֱ���λ�ã����Ŵʣ��Լ�title�͹ؼ���
		subSentenceGetter.addScoreAdder(new SubScoreAddPos());
		subSentenceGetter.addScoreAdder(new SubScoreAddThink());
		subSentenceGetter.addScoreAdder(new SubScoreAddTitle());
		// ������������Է�����
		sentenceTendAnalyser = new SentenceTendAnalyseByCenture();
	}

	@Override
	public TendAnalyseArticle analyseArticleTend(News news) {
		// �Ƚ����ݿ����ת��Ϊ�ڴ����
		NewsEntity newsEntity = NewsAndNewsEnriryTran.newsToNewsEntity(news);
		// ��newsEntity��Ԥ����ת��ΪArticle��������
		nowArticle = preAnalyser.getPreArticle(newsEntity);
		// ��ȡ�����
		subSentenceGetter.getSubSentence(3, nowArticle);
		// ��¼�����������ܷ�
		float tendScore = 0;
		for (TendSentence sentence : nowArticle.getSubSentences()) {
			sentenceTendAnalyser.analyseSentenceTend(sentence);
			// ����ֻ�ǽ��������������ܷ����ۼӣ��д��Ż���û�п������������������
			// ���߿���ȫ����ӵ��������ֻܷ���ã�Ϊ�˷���������̫��Ҳ������ƽ��������
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
		System.out.println("��ǰ�����ܾ��ӵ���Ŀ�ǣ�" + article.getSentences().size());
		System.out.println("��ǰ������������Ŀ�ǣ�" + article.getSubSentences().size());
		System.out.println("��ǰ���ŵ��������ܷ�Ϊ��" + article.getTendScore());
		for (TendSentence sentence : article.getSubSentences()) {
			String allSentenceStr = "";
			for (TendWord word : sentence.getWords()) {
				allSentenceStr += word.getCont();
			}
			System.out.println("��ǰ���ӣ�  " + allSentenceStr);
			System.out.println("��������ǣ� " + sentence.getSubjectScore());
			System.out.println("�����Է����� " + sentence.getTendScore());
		}
	}

	@Test
	public void test2() {
		NewsDAO newsDao = new NewsDAO();
		List<News> list = newsDao.findByWeb("����");
		ArticleTendAnalyseRealize articleTendAnalyse = new ArticleTendAnalyseRealize();
		for (News news : list) {
			try {
				TendAnalyseArticle article = articleTendAnalyse
						.analyseArticleTend(news);
				System.out.println("��ǰ�����ܾ��ӵ���Ŀ�ǣ�"
						+ article.getSentences().size());
				System.out.println("��ǰ������������Ŀ�ǣ�"
						+ article.getSubSentences().size());
				System.out.println("��ǰ���ŵ��������ܷ�Ϊ��" + article.getTendScore());
				for (TendSentence sentence : article.getSubSentences()) {
					String allSentenceStr = "";
					for (TendWord word : sentence.getWords()) {
						allSentenceStr += word.getCont();
					}
					System.out.println("��ǰ���ӣ�  " + allSentenceStr);
					System.out.println("��������ǣ� " + sentence.getSubjectScore());
					System.out.println("�����Է����� " + sentence.getTendScore());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
