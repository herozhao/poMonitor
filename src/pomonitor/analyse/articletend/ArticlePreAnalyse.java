package pomonitor.analyse.articletend;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pomonitor.analyse.entity.TendAnalyseArticle;
import pomonitor.analyse.entity.TendSentence;
import pomonitor.analyse.entity.TendWord;
import pomonitor.entity.NewsEntity;

/**
 * ����Ԥ���������������ַ�������Ԥ����Ϊ��Ҫ������Article����
 * 
 * @author zhaolong 2015��12��16�� ����9:31:20
 */
public class ArticlePreAnalyse {

	private NewsEntity news;

	private TendAnalyseArticle article;

	// ���·�����
	private ArticleSplier articleSplier;

	// ��Ҫ�ӷֵĴ���
	private String propertys[] = { "a", "i", "j", "k", "m", "n", "nd", "nh",
			"ni", "nl", "ns", "nt", "nz", "v", "ws" };
	// ����Ҫ�ӷֵĴ�����װ��set
	private Set<String> propertysSet;

	public ArticlePreAnalyse(ArticleSplier articleSplier) {
		this.articleSplier = new ArticleSplier();
		propertysSet = new HashSet<String>(Arrays.asList(propertys));
	}

	/**
	 * ��ʼ��һƪ���£����ػ�������
	 * 
	 * @param news
	 */
	private void init(NewsEntity news) {
		this.news = news;
		article = new TendAnalyseArticle();
		article.setKeyWords(news.getKeywords());
		article.setTitle(news.getTitle());
		article.setWeb(news.getWeb());
	}

	/**
	 * ��Ҫ����keyWord��title
	 */
	private void splitTitieAndKeyWord() {
		Set<String> usefulWordSet = new HashSet<>();
		String keyWords = "";
		for (String key : article.getKeyWords()) {
			keyWords += "#" + key;
		}

		String titleAndKey = news.getTitle() + keyWords;
		List<TendSentence> sentenceList = articleSplier.spil(titleAndKey);
		for (TendSentence ts : sentenceList) {
			for (TendWord td : ts.getWords())
				if (propertysSet.contains(td.getPos())) {
					usefulWordSet.add(td.getCont());
				}
		}
		article.setSet(usefulWordSet);
	}

	/**
	 * �Ͼ䲢�ҷ���ÿһ�䣬��Ҫ������������
	 */

	private void splitArticle() {
		String content = news.getAllContent();
		List<TendSentence> relSentences = articleSplier.spil(content);
		article.setSentences(relSentences);
	}

	/**
	 * �����ṩ������Ԥ����������ʽ������õ������Դ�����
	 * 
	 * @param news
	 * @return TendAnalyseArticle
	 */
	public TendAnalyseArticle getPreArticle(NewsEntity news) {
		init(news);
		splitArticle();
		splitTitieAndKeyWord();
		return article;
	}
}
