package pomonitor.analyse.entity;

import java.util.List;
import java.util.Set;

/**
 * ÿƪ���Ŷ�Ӧ�����¶���
 * 
 * @author Administrator
 * 
 */
public class TendAnalyseArticle {
	// ���±���
	private String title;

	// ���µ�keyWord
	private List<String> keyWords;

	// ������Դ
	private String Web;

	// ���������Է���
	private float tendScore;

	// ���°����ľ���
	private List<TendSentence> sentences;

	// ���·������������
	private List<TendSentence> subSentences;

	// �������±���͹ؼ��������ķִʴ����Ľ��
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
