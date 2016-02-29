package pomonitor.analyse.entity;

import java.util.List;

/**
 * 每篇文章所分的句子对象
 * 
 * @author Administrator
 * 
 */
public class TendSentence {
	// 句子在文章中的序号
	private int id;
	// 句子包含的词集合
	private List<TendWord> words;

	// 倾向性分数
	private float tendScore;

	// 主题分数
	private float subjectScore;

	// 词贡献的句子主题分数
	private float wordSubScore;

	// 标题贡献的句子主题分数
	private float titleScore;

	// 句子位置贡献的句子分数
	private float posScore;

	// 主张词贡献的句子分数
	private float thinkScore;

	public float getTendScore() {
		return tendScore;
	}

	public void setTendScore(float tendScore) {
		this.tendScore = tendScore;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<TendWord> getWords() {
		return words;
	}

	public void setWords(List<TendWord> words) {
		this.words = words;
	}

	public float getSubjectScore() {
		return subjectScore;
	}

	public void setSubjectScore(float subjectScore) {
		this.subjectScore = subjectScore;
	}

	public float getWordSubScore() {
		return wordSubScore;
	}

	public void setWordSubScore(float wordSubScore) {
		this.wordSubScore = wordSubScore;
	}

	public float getTitleScore() {
		return titleScore;
	}

	public void setTitleScore(float titleScore) {
		this.titleScore = titleScore;
	}

	public float getPosScore() {
		return posScore;
	}

	public void setPosScore(float posScore) {
		this.posScore = posScore;
	}

	public float getThinkScore() {
		return thinkScore;
	}

	public void setThinkScore(float thinkScore) {
		this.thinkScore = thinkScore;
	}

}
