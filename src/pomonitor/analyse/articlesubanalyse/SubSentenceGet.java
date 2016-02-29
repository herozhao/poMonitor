package pomonitor.analyse.articlesubanalyse;

import java.util.ArrayList;
import java.util.List;

import pomonitor.analyse.entity.TendAnalyseArticle;
import pomonitor.analyse.entity.TendSentence;

/**
 * �������������Ĵ�����
 * 
 * @author zhaolong 2015��12��15�� ����2:26:10
 */

public class SubSentenceGet {
	// �������Ȩ����������
	private ISentenceSubCountByWeight countWeight;
	// ��Ҫ���������
	private TendAnalyseArticle article;
	// ���������и���Ӱ�����ӵķ������б�
	public List<ISubScoreAdd> adderList;

	public void addScoreAdder(ISubScoreAdd adder) {
		adderList.add(adder);
	}

	/**
	 * 
	 * @param countByWeight
	 *            �������Ȩ����
	 */
	public SubSentenceGet(ISentenceSubCountByWeight countByWeight) {
		adderList = new ArrayList<ISubScoreAdd>();
		this.countWeight = countByWeight;
	}

	/**
	 * ������ÿһ�����������������������洢����Ӧ���ӵ���Ӧ������ �������ÿ�����ӵ������ܷ�,�����浽�����ܷ��ֶ�
	 */
	private void countSubScore() {
		for (TendSentence sentence : article.getSentences()) {
			for (ISubScoreAdd add : adderList) {
				add.add(article, sentence);
			}
			// ��Ȩ��������Ӱ������
			countWeight.sentenceSubCount(sentence);
		}
	}

	/**
	 * �����洢ÿһƪ�������ܴ��������ǰ��������
	 * 
	 * @param outCount
	 *            ��Ҫ��ȡ����������
	 */
	public void getSubSentence(int outCount, TendAnalyseArticle article) {
		this.article = article;
		countSubScore();
		List<TendSentence> sentences = this.article.getSentences();
		List<TendSentence> subSentences = new ArrayList<TendSentence>();
		int count = 0;
		int index;
		for (int i = 0; i < sentences.size(); i++) {
			if (count >= outCount) {
				break;
			}
			count++;
			index = i;
			for (int j = i; j < sentences.size(); j++) {
				if (sentences.get(j).getSubjectScore() > sentences.get(index)
						.getSubjectScore()) {
					// ��¼��ǰ���
					index = j;
				}
			}
			subSentences.add(sentences.get(index));
			sentences.remove(index);

		}
		article.setSubSentences(subSentences);
	}
}
