package pomonitor.analyse.articletend;

import pomonitor.analyse.entity.TendSentence;

/**
 * ���������Է���
 * 
 * @author zhaolong 2015��12��15�� ����11:44:49
 */
public interface ISentenceTendAnalyse {

	/**
	 * 
	 * @param sentence
	 * @return sentenceTendScore
	 */
	public float analyseSentenceTend(TendSentence sentence);
}
