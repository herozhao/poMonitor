package pomonitor.analyse.articlesubanalyse;

import pomonitor.analyse.entity.TendSentence;

/**
 * ���������⹱�����,��Ϊ�ܵ���������
 * 
 * @author zhaolong 2015��12��20�� ����3:44:38
 */

public class SentenceSubCountByWeightAverage implements
		ISentenceSubCountByWeight {

	@Override
	public TendSentence sentenceSubCount(TendSentence sentence) {
		System.out.println("����Id" + sentence.getId());
		System.out.println("λ�ã�" + sentence.getPosScore());
		System.out.println("title:" + sentence.getTitleScore());
		System.out.println("���Ŵʣ�" + sentence.getThinkScore());
		float allSubScore = sentence.getPosScore() + sentence.getThinkScore()
				+ sentence.getTitleScore();
		System.out.println("������֣�" + allSubScore);
		sentence.setSubjectScore(allSubScore);
		return sentence;
	}

}
