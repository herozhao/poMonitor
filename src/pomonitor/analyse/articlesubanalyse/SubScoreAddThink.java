package pomonitor.analyse.articlesubanalyse;

import pomonitor.analyse.entity.TendAnalyseArticle;
import pomonitor.analyse.entity.TendSentence;
import pomonitor.analyse.entity.TendWord;
import pomonitor.util.IdeaWordDictionary;

/**
 * 主张类词语加分
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
		// 主张类词语出现的数量
		float count = 0;
		for (TendWord tw : sentence.getWords()) {
			String nowWord = tw.getCont();
			System.out.println(ideaWordDictionary.map.keySet());
			System.out.println("是否包含：" + nowWord);

			if (ideaWordDictionary.map.containsKey(nowWord)) {
				count = count + 1;
				System.out.println("是");
			}
		}
		float thinkScore = count / sentence.getWords().size();
		sentence.setThinkScore(thinkScore);
		return sentence;
	}
}
