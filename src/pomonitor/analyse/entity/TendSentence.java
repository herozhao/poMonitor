package pomonitor.analyse.entity;

import java.util.List;

/**
 * ÿƪ�������ֵľ��Ӷ���
 * 
 * @author Administrator
 * 
 */
public class TendSentence {
	// �����������е����
	private int id;
	// ���Ӱ����Ĵʼ���
	private List<TendWord> words;

	// �����Է���
	private float tendScore;

	// �������
	private float subjectScore;

	// �ʹ��׵ľ����������
	private float wordSubScore;

	// ���⹱�׵ľ����������
	private float titleScore;

	// ����λ�ù��׵ľ��ӷ���
	private float posScore;

	// ���Ŵʹ��׵ľ��ӷ���
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
