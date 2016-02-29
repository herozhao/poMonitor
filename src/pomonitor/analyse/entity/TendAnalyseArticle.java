package pomonitor.analyse.entity;

import java.util.List;
import java.util.Set;

/**
 * 每篇新闻对应的文章对象
 * 
 * @author Administrator
 * 
 */
public class TendAnalyseArticle {
	// 文章标题
	private String title;

	// 文章的keyWord
	private List<String> keyWords;

	// 文章来源
	private String Web;

	// 文章倾向性分数
	private float tendScore;

	// 文章包含的句子
	private List<TendSentence> sentences;

	// 文章分析完后的主题句
	private List<TendSentence> subSentences;

	// 对于文章标题和关键词所做的分词处理后的结果
	private Set<String> set;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWeb() {
		return Web;
	}

	public void setWeb(String web) {
		Web = web;
	}

	public float getTendScore() {
		return tendScore;
	}

	public void setTendScore(float tendScore) {
		this.tendScore = tendScore;
	}

	public List<TendSentence> getSentences() {
		return sentences;
	}

	public void setSentences(List<TendSentence> sentences) {
		this.sentences = sentences;
	}

	public List<TendSentence> getSubSentences() {
		return subSentences;
	}

	public void setSubSentences(List<TendSentence> subSentences) {
		this.subSentences = subSentences;
	}

	public List<String> getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(List<String> keyWords) {
		this.keyWords = keyWords;
	}

	public Set<String> getSet() {
		return set;
	}

	public void setSet(Set<String> set) {
		this.set = set;
	}

}
