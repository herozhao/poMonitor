package pomonitor.analyse.articlesubanalyse;

import pomonitor.analyse.entity.TendAnalyseArticle;
import pomonitor.analyse.entity.TendSentence;
import pomonitor.analyse.entity.TendWord;
import pomonitor.util.IdeaWordDictionary;

/**
 * ���������ӷ�
 * 
 * @author Administrator
 * 
 */
public class SubScoreAddThink implements ISubScoreAdd {
	private IdeaWordDictionary ideaWordDictionary;

	public SubScoreAddThink() {
		ideaWordDictionary = new IdeaWordDictionary();
	}

	@Override
	public TendSentence add(TendAnalyseArticle article, TendSentence sentence) {
		// �����������ֵ�����
		float count = 0;
		for (TendWord tw : sentence.getWords()) {
			String nowWord = tw.getCont();
			System.out.println(ideaWordDictionary.map.keySet());
			System.out.println("�Ƿ������" + nowWord);

			if (ideaWordDictionary.map.containsKey(nowWord)) {
				count = count + 1;
				System.out.println("��");
			}
		}
		float thinkScore = count / sentence.getWords().size();
		sentence.setThinkScore(thinkScore);
		return sentence;
	}
}
