package pomonitor.analyse.articletend;

import java.util.List;

import org.junit.Test;

import pomonitor.analyse.entity.TendSentence;
import pomonitor.analyse.entity.TendWord;
import pomonitor.entity.Emotionalword;
import pomonitor.entity.LeverWord;
import pomonitor.util.EmotionalDictionary;
import pomonitor.util.LeverWordDictionary;
import pomonitor.util.NegWordDictionary;

/**
 * һ���Զ���ľ��������Է������Դ�ν�����ķ�����
 * 
 * @author zhaolong 2015��12��21�� ����1:02:07
 */
public class SentenceTendAnalysePredicateCenture implements
		ISentenceTendAnalyse {
	// ��д��ֵ�
	private EmotionalDictionary emotionalDictionary;

	// �̶ȴ��ֵ�
	private LeverWordDictionary levelDictionary;

	// �񶨴��ֵ�
	private NegWordDictionary negDictionary;

	// ���Ĵʷ���
	private float predicateScore;

	// �̶ȴʼӳ�
	private float levelWeight;

	private float negWeight;

	// �񶨴���
	private float neg = -0.2f;

	// ��ǰ����ľ���
	private TendSentence sentence;

	// ���Ĵ�Id
	private int headId;

	// ��ǰ������ľ��ӵĴ��б�
	private List<TendWord> wordsList;

	// �����ɷּӷ�
	private float otherScore;

	public SentenceTendAnalysePredicateCenture() {
		emotionalDictionary = new EmotionalDictionary();
		levelDictionary = new LeverWordDictionary();
		negDictionary = new NegWordDictionary();
	}

	// ÿһ�δ����¶���֮ǰ����������ȫ�ֱ���
	private void updateDate() {
		wordsList = sentence.getWords();
		predicateScore = 0;
		levelWeight = 1;
		negWeight = 1;
		otherScore = 0;
	}

	@Override
	public float analyseSentenceTend(TendSentence sentence) {
		this.sentence = sentence;
		updateDate();
		analyseHeadScore();
		analyseNegLevel();
		analysLevel();
		addAllScore();
		return 0;
	}

	// �����ܷ�
	public void addAllScore() {
		float allScore = (predicateScore * levelWeight + otherScore)
				* negWeight;
		System.out.println("���ռ�����������ܷ�Ϊ��" + allScore);
	}

	// ������Ĵʷ���
	private void analyseHeadScore() {
		// ��ȡ�����Ĵ�
		for (TendWord td : wordsList) {
			if (td.getParent() == -1 && td.getRelate().equals("HED")) {
				headId = td.getId();
				String headWord = td.getCont();
				System.out.println("��ȡ�����Ĵʣ�" + headWord);
				// ����дʱ�

				Emotionalword emotionalword = emotionalDictionary
						.getWord(headWord);
				// System.out.println(emotionalword.getWord());
				// ����鵽��˵��������дʱ���
				if (emotionalword != null) {
					predicateScore = emotionalword.getPolarity()
							* (float) emotionalword.getStrength() / 10;
					System.out.println("�������ú��Ĵʷ���Ϊ��" + predicateScore);
					// ��¼���Ĵ�Id
				}

			}
		}
	}

	// ������Ĵʷ�ǰ׺��������񶨳̶�
	private void analyseNegLevel() {
		// ������ִ�ϵͳ�Ƿ�ʶ�𵽷񶨴�
		boolean ifGet = false;
		for (TendWord td : wordsList) {
			if (td.getParent() == headId && td.getSemrelate().equals("mNeg")) {
				System.out.println("���Ĵ�֮�񶨴��ǣ�" + td.getCont());
				negWeight = neg * negWeight;
				System.out.println("��Ȩ�أ�" + negWeight);
				ifGet = true;
				int negId = td.getId();
				for (TendWord negTd : wordsList) {
					// ����ҵ��񶨴�����
					if (negTd.getSemparent() == negId) {
						System.out.println("���Ĵʵķ񶨴ʵ����δ��ǣ�" + negTd.getCont());
						LeverWord lw = levelDictionary.getWord(negTd.getCont());
						// ����ڳ̶ȴ��ֵ����ҵ�������ݳ̶ȴ��ֵ�Է񶨴ʽ��мӳ�
						if (lw != null) {
							// ����2����Ϊ����΢����
							negWeight = negWeight
									* (1 + (lw.getScore() - 2) / 5);
							// ���û�ҵ���ʱ��������
						} else {

						}
						System.out.println("���μӳɺ��Ȩ�أ�" + negWeight);
					}
				}
			}
		}
		// ������û��ʶ����񶨣���Ϊ����񶨴ʿ⻹û�����ƴ˴�������ʵ��
		if (ifGet) {

		}
	}

	// ������Ĵʵ����δʻ�����Ϊ�˷����ߵļӳ�
	private void analysLevel() {
		for (TendWord td : wordsList) {

			if (td.getParent() == headId) {
				String relation = td.getRelate();
				String er = td.getSemrelate();
				// ����������ֹ�ϵ֮һ��Ϊ�Ժ��Ĵ�֮��ǿ
				if (relation.equals("ADV") && !er.equals("mNeg")) {
					System.out.println("���Ĵʵĳ̶ȼ�ǿ��:" + td.getCont());
					LeverWord lw = levelDictionary.getWord(td.getCont());
					if (lw != null) {
						levelWeight = negWeight * (1 + (lw.getScore() - 2) / 5);
					} else {
						// ������β��ڳ̶ȸ��ʱ���Ĭ������0.1
						levelWeight = 1.1f;
					}
					System.out.println("���Ĵʵĳ̶ȼ�ǿ�ʼӷ�:" + levelWeight);
				} else if (relation.equals("SBV") || relation.equals("VOB")
						|| relation.equals("IOB") || relation.equals("FOB")
						|| relation.equals("DBL") || relation.equals("COO")) {
					System.out.println("���ĴʵĹ����ɷִ�:" + td.getCont());
					Emotionalword emotionalword = emotionalDictionary
							.getWord(td.getCont());
					if (emotionalword != null) {
						System.out.println(emotionalword.getWord());
						otherScore += emotionalword.getPolarity()
								* (float) emotionalword.getStrength() / 10;
						System.out.println("���ĴʵĹ����ɷִʼӷ֣�" + otherScore);
					}
					// �����ɷֵ����δʼӷ֣�һ��Ϊ�������ѡ���ۼ�
					for (TendWord dtw : wordsList) {

						if (dtw.getParent() == td.getId()) {
							System.out
									.println("���ĴʵĹ����ɷִʵ����δʣ�" + dtw.getCont());
							LeverWord lw = levelDictionary.getWord(dtw
									.getCont());

							if (lw != null) {
								System.out.println(dtw.getCont());
								otherScore *= (1 + lw.getScore() / 10);
								System.out.println("���ĴʵĹ����ɷִʵ����δʳ̶ȴʼӷֺ�"
										+ otherScore);
							}

							Emotionalword emotionalword2 = emotionalDictionary
									.getWord(dtw.getCont());

							if (emotionalword2 != null) {
								System.out.println(dtw.getCont());
								otherScore += emotionalword2.getPolarity()
										* (float) emotionalword2.getStrength()
										/ 20;
								System.out.println("���ĴʵĹ����ɷִʵ����δʰ���ӷֺ�"
										+ otherScore);
							}
						}
					}

				}

			}
		}

	}

	@Test
	public void test() {
		SentenceSplier splier = new SentenceSplier();
		String testStr = "������֫�ܲ�����";
		List<TendWord> list = splier.spil(testStr);
		TendSentence sentence = new TendSentence();
		sentence.setWords(list);
		SentenceTendAnalysePredicateCenture stapc = new SentenceTendAnalysePredicateCenture();
		stapc.analyseSentenceTend(sentence);
	}
}
