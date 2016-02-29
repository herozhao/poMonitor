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
 * 文章预处理器，将新闻字符串对象预处理为需要分析的Article对象
 * 
 * @author zhaolong 2015年12月16日 下午9:31:20
 */
public class ArticlePreAnalyse {

	private NewsEntity news;

	private TendAnalyseArticle article;

	// 文章分析器
	private ArticleSplier articleSplier;

	// 需要加分的词性
	private String propertys[] = { "a", "i", "j", "k", "m", "n", "nd", "nh",
			"ni", "nl", "ns", "nt", "nz", "v", "ws" };
	// 将需要加分的词性组装成set
	private Set<String> propertysSet;

	public ArticlePreAnalyse(ArticleSplier articleSplier) {
		this.articleSplier = new ArticleSplier();
		propertysSet = new HashSet<String>(Arrays.asList(propertys));
	}

	/**
	 * 初始化一篇文章，加载基本参数
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
	 * 主要处理keyWord和title
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
	 * 断句并且分依每一句，主要处理文章正文
	 */

	private void splitArticle() {
		String content = news.getAllContent();
		List<TendSentence> relSentences = articleSplier.spil(content);
		article.setSentences(relSentences);
	}

	/**
	 * 向外提供的文章预处理方法，格式化处理好的文章以待分析
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
