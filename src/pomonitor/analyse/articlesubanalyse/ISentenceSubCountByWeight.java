package pomonitor.analyse.articlesubanalyse;

import pomonitor.analyse.entity.TendSentence;

/**
 * ����Sentence���ַ����ۼӵķ����ӿ�
 * 
 * @author zhaolong 2015��12��15�� ����2:06:36
 */
public interface ISentenceSubCountByWeight {
	/**
	 * 
	 * @param sentence
	 * @return sentence
	 */
	public TendSentence sentenceSubCount(TendSentence sentence);
}
