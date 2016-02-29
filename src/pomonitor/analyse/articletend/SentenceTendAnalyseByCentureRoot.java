package pomonitor.analyse.articletend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import pomonitor.analyse.entity.TendSentence;
import pomonitor.analyse.entity.TendWord;
import pomonitor.entity.Emotionalword;
import pomonitor.entity.LeverWord;
import pomonitor.util.EmotionalDictionary;
import pomonitor.util.LeverWordDictionary;
import pomonitor.util.NegWordDictionary;

/**
 * �Զ���ľ��������Է��������ķ���������ʽ�汾
 * 
 * @author zhaolong 2015��12��23�� ����7:09:30
 */
public class SentenceTendAnalyseByCentureRoot implements ISentenceTendAnalyse {
	// ��д��ֵ�
	private EmotionalDictionary emotionalDictionary;

	// �̶ȴ��ֵ�
	private LeverWordDictionary levelDictionary;

	// �񶨴��ֵ�
	private NegWordDictionary negDictionary;

	// �񶨴���
	private final float neg = -0.2f;

	// ��ǰ����ľ���
	private TendSentence sentence;

	// ��ǰ������ľ��ӵĴ��б�
	private List<TendWord> wordsList;

	// һ��ǿ���ۼӼ���������ǿ�ȴ�������ۼӴ���ʱ�����ͷ�ǿ��
	private Map<Float, Integer> mapCount;

	/**
	 * ���췽����ʼ��һЩ������Ϣ
	 */
	public SentenceTendAnalyseByCentureRoot() {
		emotionalDictionary = new EmotionalDictionary();
		levelDictionary = new LeverWordDictionary();
		negDictionary = new NegWordDictionary();
	}

	@Override
	public float analyseSentenceTend(TendSentence sentence) {
		this.sentence = sentence;
		wordsList = sentence.getWords();
		mapCount = new HashMap<Float, Integer>();
		int id = getId();
		LevelAndEmotion result = getTendScore(id);
		// ����ǰ�Ľ���������浽sentence����ȥ
		sentence.setTendScore(result.emotion);
		return result.emotion;
	}

	/**
	 * ��ȡ��ǰ���Ӻ��ĵ�Id
	 * 
	 * @return
	 */
	private int getId() {
		int id = -1;
		for (TendWord td : wordsList) {
			if (td.getSemparent() == -1) {
				id = td.getId();
				System.out.println("��ȡ���ĺ��Ĵʣ�" + td.getCont());
			}
		}
		return id;
	}

	/**
	 * ���㵱ǰ���Ľڵ�����״̬
	 * 
	 * @param id
	 * @return LevelAndEmotion
	 */
	private LevelAndEmotion getTendScore(int id) {
		System.out.println();
		System.out.println();

		// ���浱ǰ�ڵ�ļ�����
		LevelAndEmotion le = new LevelAndEmotion();

		// ��ǰ�ʵľ�������
		TendWord tendWord = wordsList.get(id);

		// ���㵱ǰ�ʵ�emotion
		float emotion = findEmotionValue(tendWord.getCont());

		// ���㵱ǰ�ʵ�level
		float level = findLevelValue(tendWord.getCont());
		le.emotion = emotion;
		le.level = level;
		// ���㵱ǰ�ʵĺ���id
		List<Integer> childrenIdList = findChildId(id);

		// if (le.level != 1.0f) {
		// System.out.println(le.level + " ������");
		// mapCount.put(le.level, 1);
		// }

		// ���޺��ӽڵ��򷵻ص�ǰ�ڵ�
		if (childrenIdList.size() == 0) {
			System.out.println("��ǰ�� " + tendWord.getCont() + "  le.emotion: "
					+ le.emotion + "    le.level: " + le.level);
			return le;
		} else {
			System.out.println(tendWord.getCont() + "  ӵ�� "
					+ +childrenIdList.size() + " ������ ");
			// ���յ�ǰ���亢�Ӵʵļ�����
			for (Integer childId : childrenIdList) {
				LevelAndEmotion lae = getTendScore(childId);
				// ���Ӵ˲���ֻΪ�˲��ԣ��Ժ�Ч�ʿ���ʡȥ
				String nowWord = wordsList.get(childId).getCont();
				// �������з������������з���
				if (Math.abs(lae.emotion) > 0) {
					System.out.println(childId + "  �ӷ�  " + lae.emotion);
					le.emotion += lae.emotion;
					System.out.println("��ǰ�ʣ�" + tendWord.getCont() + " ����б� "
							+ nowWord + " ���ӵ� " + le.emotion);
				} else {
					le.level *= lae.level;
					if (lae.level != 1.0f)
						System.out.println("��ǰ�ʣ�" + tendWord.getCont()
								+ " ��ǿ�ȱ� " + nowWord + " ���ӵ� " + le.level);
				}
			}
			le.emotion *= le.level;

			// �������
			if (le.emotion != 0)
				le.level = 1.0f;

			System.out.println("��ǰ�ʣ�" + tendWord.getCont() + " ���������Ϊ "
					+ le.emotion);
		}
		// if (mapCount.containsKey(le.level)) {
		// int count = mapCount.get(le.level);
		// if (count < 2) {
		// count++;
		// mapCount.put(le.level, count);
		// System.out.println(le.level + "��ǰ�������� ��" + count);
		// } else {
		// System.out.println(le.level + " ���Ƴ�");
		// mapCount.remove(le.level);
		// le.level = 1.0f;
		// }
		// } else {
		// }

		return le;
	}

	/**
	 * ���ҵ�ǰ�ڵ�ĺ���Id
	 * 
	 * @param id
	 * @return
	 */
	private List<Integer> findChildId(int id) {
		List<Integer> childrenIdList = new ArrayList<>();
		for (TendWord td : wordsList) {
			if (td.getSemparent() == id) {
				childrenIdList.add(td.getId());
			}
		}
		return childrenIdList;
	}

	/**
	 * ���Ҳ����㵱ǰ�ʵ���з���
	 * 
	 * @param word
	 * @return float
	 */
	private float findEmotionValue(String word) {
		System.out.println("��ǰ�ʣ�" + word);
		float score = 0;
		// ������дʵ�
		System.out.println(emotionalDictionary);
		Emotionalword eword = emotionalDictionary.getWord(word);
		if (eword != null) {
			System.out.println(eword.getId());
			System.out.println(eword.getPolarity() + "~~~~~~~");
			System.out.println(eword.getStrength() + "~~~~~~~~~");
			// �������
			score = eword.getPolarity() * (float) eword.getStrength() / 10;
		} else {
			// �˴����ҽ���ʵ�������
		}
		System.out.println("��д� " + word + " �÷֣�" + score);
		return score;
	}

	/**
	 * ���Ҳ����㵱ǰ�ʵ�ǿ�ȷ���
	 * 
	 * @param word
	 * @return float
	 */
	private float findLevelValue(String word) {
		float score = 1;
		LeverWord lvWord = levelDictionary.getWord(word);
		if (lvWord != null) {
			score *= (lvWord.getScore() / 10 + 1);
		} else {
			// ��������ڷ񶨴ʿ⣬�򽵼�ȡ��
			if (negDictionary.map.containsKey(word)) {
				score *= neg;
			}
		}
		System.out.println("ǿ�ȴ� " + word + " �÷֣�" + score);
		return score;
	}

	/**
	 * һ�����ڱ���ÿ�μ���״̬����
	 * 
	 * @author zhaolong 2015��12��23�� ����7:18:17
	 */
	class LevelAndEmotion {
		boolean isEmotion = false;
		float level = 1;
		float emotion = 0;
		String count;
	}

	@Test
	public void test() {
		SentenceSplier splier = new SentenceSplier();
		String testStr = "˵��ҽ�ƴ�����ѧ�����Դ�����ߵ�Ե�ʣ�����������ƴ�ѧ�Ļ�ӭ���׷����Ϻϲ�֮·������ҽ�ƴ����Ĵ���ѧ����ɽҽ�ƴ�����ɽ��ѧ������ҽ�ƴ��뱱����ѧ���Ϻ�ҽ�ƴ��븴����ѧ��ɽ��ҽ�ƴ���ɽ����ѧ���ߵĶ���ǿǿ�ϲ�·�ߣ�������������Ϊ��������ռ�˱��˵�˵����ҽ�ƴ�������ѧ�Ƶ������ԣ��õ�ҽѧ����Ͽɾ����ǳɹ��ˣ��ϲ���ĳ�̶ֳ��Ϸ�������������ɫ����������Ϊ�����ϵ������ҽ�ƴ��ںϲ�֮�����ԡ�������ѧҽѧ�������Ϻ�ҽ�ƴ��ԡ�������ѧҽѧԺ������ݶ���������������Щ����ҽ�ƴ���˵���ϲ��ĺ�������ڿ��Եõ������Ӳ���Ͳ����ϵ�֧��";
		List<TendWord> list = splier.spil(testStr);
		TendSentence sentence = new TendSentence();
		sentence.setWords(list);
		SentenceTendAnalyseByCentureRoot stab = new SentenceTendAnalyseByCentureRoot();
		float score = stab.analyseSentenceTend(sentence);
		System.out.println(score);
	}

	@Test
	public void testLv() {
		Map<Float, Integer> map = new HashMap<>();
		map.put(1.2f, 1);
		map.put(1.0f, 4);
		System.out.println(map.get(1.0f));
	}
}
