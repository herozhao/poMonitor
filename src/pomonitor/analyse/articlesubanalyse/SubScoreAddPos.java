package pomonitor.analyse.articlesubanalyse;

import pomonitor.analyse.entity.TendAnalyseArticle;
import pomonitor.analyse.entity.TendSentence;

/**
 * ����λ�üӷ�
 * 
 * @author Administrator
 * 
 */
public class SubScoreAddPos implements ISubScoreAdd {
	// ��߷�
	private float heighScore = 1;

	@Override
	public TendSentence add(TendAnalyseArticle article, TendSentence sentence) {
		float subScore = 0;
		if (sentence.getId() == 0) {
			subScore = heighScore;
		} else {
			float sentenceNum = article.getSentences().size();
			float sentenceTh = sentence.getId();
			subScore = (float) (heighScore - Math.log10(sentenceNum
					/ sentenceTh));
		}
		sentence.setPosScore(subScore);
		return sentence;
	}
}
