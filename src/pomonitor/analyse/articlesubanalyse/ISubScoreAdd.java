package pomonitor.analyse.articlesubanalyse;

import pomonitor.analyse.entity.TendAnalyseArticle;
import pomonitor.analyse.entity.TendSentence;

/**
 * �������µ�ÿһ������������ԵĽӿ�
 * 
 * @author zhaolong
 * 
 */
public interface ISubScoreAdd {

	/**
	 * 
	 * @param article
	 * @param sentence
	 * @return sentence
	 */
	public TendSentence add(TendAnalyseArticle article, TendSentence sentence);

}
