package pomonitor.analyse.articlesubanalyse;

import pomonitor.analyse.entity.TendAnalyseArticle;
import pomonitor.analyse.entity.TendSentence;

/**
 * 对于文章的每一句分析其倾向性的接口
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
