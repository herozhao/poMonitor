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
 * 一个自定义的句子倾向性分析，自创谓语中心分析法
 * 
 * @author zhaolong 2015年12月21日 下午1:02:07
 */
public class SentenceTendAnalysePredicateCenture implements
		ISentenceTendAnalyse {
	// 情感词字典
	private EmotionalDictionary emotionalDictionary;

	// 程度词字典
	private LeverWordDictionary levelDictionary;

	// 否定词字典
	private NegWordDictionary negDictionary;

	// 核心词分数
	private float predicateScore;

	// 程度词加成
	private float levelWeight;

	private float negWeight;

	// 否定处理
	private float neg = -0.2f;

	// 当前处理的句子
	private TendSentence sentence;

	// 核心词Id
	private int headId;

	// 当前所处理的句子的词列表
	private List<TendWord> wordsList;

	// 其他成分加分
	private float otherScore;

	public SentenceTendAnalysePredicateCenture() {
		emotionalDictionary = new EmotionalDictionary();
		levelDictionary = new LeverWordDictionary();
		negDictionary = new NegWordDictionary();
	}

	// 每一次处理新对象之前都更新类中全局变量
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

	// 计算总分
	public void addAllScore() {
		float allScore = (predicateScore * levelWeight + otherScore)
				* negWeight;
		System.out.println("最终计算的倾向性总分为：" + allScore);
	}

	// 计算核心词分数
	private void analyseHeadScore() {
		// 获取到核心词
		for (TendWord td : wordsList) {
			if (td.getParent() == -1 && td.getRelate().equals("HED")) {
				headId = td.getId();
				String headWord = td.getCont();
				System.out.println("获取到核心词：" + headWord);
				// 查情感词表

				Emotionalword emotionalword = emotionalDictionary
						.getWord(headWord);
				// System.out.println(emotionalword.getWord());
				// 如果查到，说明其在情感词表中
				if (emotionalword != null) {
					predicateScore = emotionalword.getPolarity()
							* (float) emotionalword.getStrength() / 10;
					System.out.println("计算所得核心词分数为：" + predicateScore);
					// 记录核心词Id
				}

			}
		}
	}

	// 计算核心词否定前缀并计算其否定程度
	private void analyseNegLevel() {
		// 哈工大分词系统是否识别到否定词
		boolean ifGet = false;
		for (TendWord td : wordsList) {
			if (td.getParent() == headId && td.getSemrelate().equals("mNeg")) {
				System.out.println("核心词之否定词是：" + td.getCont());
				negWeight = neg * negWeight;
				System.out.println("否定权重：" + negWeight);
				ifGet = true;
				int negId = td.getId();
				for (TendWord negTd : wordsList) {
					// 如果找到否定词修饰
					if (negTd.getSemparent() == negId) {
						System.out.println("核心词的否定词的修饰词是：" + negTd.getCont());
						LeverWord lw = levelDictionary.getWord(negTd.getCont());
						// 如果在程度词字典中找到，则根据程度词字典对否定词进行加成
						if (lw != null) {
							// 减少2是因为”稍微不“
							negWeight = negWeight
									* (1 + (lw.getScore() - 2) / 5);
							// 如果没找到暂时不作处理
						} else {

						}
						System.out.println("修饰加成后否定权重：" + negWeight);
					}
				}
			}
		}
		// 哈工大没有识别出否定，因为自身否定词库还没有完善此处还不做实现
		if (ifGet) {

		}
	}

	// 计算核心词的修饰词或者其为此发出者的加成
	private void analysLevel() {
		for (TendWord td : wordsList) {

			if (td.getParent() == headId) {
				String relation = td.getRelate();
				String er = td.getSemrelate();
				// 如果存在这种关系之一则为对核心词之加强
				if (relation.equals("ADV") && !er.equals("mNeg")) {
					System.out.println("核心词的程度加强词:" + td.getCont());
					LeverWord lw = levelDictionary.getWord(td.getCont());
					if (lw != null) {
						levelWeight = negWeight * (1 + (lw.getScore() - 2) / 5);
					} else {
						// 如果修饰不在程度副词表，则默认增加0.1
						levelWeight = 1.1f;
					}
					System.out.println("核心词的程度加强词加分:" + levelWeight);
				} else if (relation.equals("SBV") || relation.equals("VOB")
						|| relation.equals("IOB") || relation.equals("FOB")
						|| relation.equals("DBL") || relation.equals("COO")) {
					System.out.println("核心词的关联成分词:" + td.getCont());
					Emotionalword emotionalword = emotionalDictionary
							.getWord(td.getCont());
					if (emotionalword != null) {
						System.out.println(emotionalword.getWord());
						otherScore += emotionalword.getPolarity()
								* (float) emotionalword.getStrength() / 10;
						System.out.println("核心词的关联成分词加分：" + otherScore);
					}
					// 其他成分的修饰词加分，一般为定语，这里选择累加
					for (TendWord dtw : wordsList) {

						if (dtw.getParent() == td.getId()) {
							System.out
									.println("核心词的关联成分词的修饰词：" + dtw.getCont());
							LeverWord lw = levelDictionary.getWord(dtw
									.getCont());

							if (lw != null) {
								System.out.println(dtw.getCont());
								otherScore *= (1 + lw.getScore() / 10);
								System.out.println("核心词的关联成分词的修饰词程度词加分后："
										+ otherScore);
							}

							Emotionalword emotionalword2 = emotionalDictionary
									.getWord(dtw.getCont());

							if (emotionalword2 != null) {
								System.out.println(dtw.getCont());
								otherScore += emotionalword2.getPolarity()
										* (float) emotionalword2.getStrength()
										/ 20;
								System.out.println("核心词的关联成分词的修饰词褒贬加分后："
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
		String testStr = "他的四肢很不发达";
		List<TendWord> list = splier.spil(testStr);
		TendSentence sentence = new TendSentence();
		sentence.setWords(list);
		SentenceTendAnalysePredicateCenture stapc = new SentenceTendAnalysePredicateCenture();
		stapc.analyseSentenceTend(sentence);
	}
}
