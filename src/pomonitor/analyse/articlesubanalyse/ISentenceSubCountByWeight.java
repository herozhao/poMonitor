package pomonitor.analyse.articlesubanalyse;

import pomonitor.analyse.entity.TendSentence;

/**
 * 对于Sentence四种分数累加的方法接口
 * 
 * @author zhaolong 2015年12月15日 下午2:06:36
 */
public interface ISentenceSubCountByWeight {
	/**
	 * 
	 * @param sentence
	 * @return sentence
	 */
	public TendSentence sentenceSubCount(TendSentence sentence);
}
