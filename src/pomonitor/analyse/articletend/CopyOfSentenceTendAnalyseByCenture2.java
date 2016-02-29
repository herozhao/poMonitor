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
public class CopyOfSentenceTendAnalyseByCenture2 implements
		ISentenceTendAnalyse {
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
	public CopyOfSentenceTendAnalyseByCenture2() {
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
			if (td.getParent() == -1) {
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
		// ���޺��ӽڵ��򷵻ص�ǰ�ڵ�
		if (childrenIdList.size() == 0) {
			System.out.println("��ǰ�� " + tendWord.getCont() + "  le.emotion: "
					+ le.emotion + "    le.level: " + le.level);
			return le;
		} else {
			System.out.println(tendWord.getCont() + "  ӵ�� "
					+ +childrenIdList.size() + " ������ ");
			float allEmotion = 0.0f;
			float allLevel = 1.0f;
			// ���յ�ǰ���亢�Ӵʵļ�����
			for (Integer childId : childrenIdList) {
				LevelAndEmotion lae = getTendScore(childId);
				// ���Ӵ˲���ֻΪ�˲��ԣ��Ժ�Ч�ʿ���ʡȥ
				String nowWord = wordsList.get(childId).getCont();
				if (Math.abs(lae.emotion) > 0) {
					System.out.println(childId + "  �ӷ�  " + lae.emotion);
					allEmotion += lae.emotion;
					System.out.println("��ǰ�ʣ�" + tendWord.getCont() + " ����б� "
							+ nowWord + " ���ӵ� " + le.emotion);
				} else {
					allLevel *= lae.level;
					System.out.println("��ǰ�ʣ�" + tendWord.getCont() + " ��ǿ�ȱ� "
							+ nowWord + " ���ӵ� " + le.level);
				}
			}
			if (le.emotion != 0) {// �������дʣ����ӳ̶���������дʣ������ͷ�
				le.emotion = (allEmotion + le.emotion) * allLevel;
				le.level = 1.0f;
			} else if (le.level != 1) {// ���������дʣ������г̶ȣ�����������ۼ���Ϊ��ǰ��У��̶��۳���Ϊ��ǰ�̶�
				le.emotion = allEmotion;
				le.level *= allLevel;
			} else {// ����Ȳ�����дʣ�Ҳ�޳̶ȣ���ֻ�����ӵ�����ۼӣ���Ϊ��ǰ���
				le.emotion = allEmotion;
			}
			System.out.println("��ǰ�ʣ�" + tendWord.getCont() + " ���������Ϊ "
					+ le.emotion);
		}
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
			if (td.getParent() == id) {
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
		String testStr = "�㲻��һ������";
		List<TendWord> list = splier.spil(testStr);
		TendSentence sentence = new TendSentence();
		sentence.setWords(list);
		CopyOfSentenceTendAnalyseByCenture2 stab = new CopyOfSentenceTendAnalyseByCenture2();
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
