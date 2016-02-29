package pomonitor.analyse.articlesubanalyse;

import pomonitor.analyse.entity.TendSentence;

/**
 * 将三种主题贡献相加,作为总的主题排序
 * 
 * @author zhaolong 2015年12月20日 下午3:44:38
 */

public class SentenceSubCountByWeightAverage implements
		ISentenceSubCountByWeight {

	@Override
	public TendSentence sentenceSubCount(TendSentence sentence) {
		System.out.println("句子Id" + sentence.getId());
		System.out.println("位置：" + sentence.getPosScore());
		System.out.println("title:" + sentence.getTitleScore());
		System.out.println("主张词：" + sentence.getThinkScore());
		float allSubScore = sentence.getPosScore() + sentence.getThinkScore()
				+ sentence.getTitleScore();
		System.out.println("总主题分：" + allSubScore);
		sentence.setSubjectScore(allSubScore);
		return sentence;
	}

}
