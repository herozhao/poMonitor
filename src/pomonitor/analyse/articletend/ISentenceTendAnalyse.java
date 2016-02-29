package pomonitor.analyse.articletend;

import pomonitor.analyse.entity.TendSentence;

/**
 * 句子倾向性分析
 * 
 * @author zhaolong 2015年12月15日 上午11:44:49
 */
public interface ISentenceTendAnalyse {

	/**
	 * 
	 * @param sentence
	 * @return sentenceTendScore
	 */
	public float analyseSentenceTend(TendSentence sentence);
}
