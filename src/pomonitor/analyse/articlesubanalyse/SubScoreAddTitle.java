package pomonitor.analyse.articlesubanalyse;

import java.util.Set;

import pomonitor.analyse.entity.TendAnalyseArticle;
import pomonitor.analyse.entity.TendSentence;
import pomonitor.analyse.entity.TendWord;

/**
 * 文章标题加分，此处将其与关键字加分合并处理，以后在做细化
 * 
 * @author Administrator
 * 
 */
public class SubScoreAddTitle implements ISubScoreAdd {

	@Override
	public TendSentence add(TendAnalyseArticle article, TendSentence sentence) {
		// 出现实词的数目
		float count = 0;
		// title所包含的实词
		Set<String> set = article.getSet();
		for (TendWord td : sentence.getWords()) {
			// 如果此句话包含title中和keyWords中的词则加分
			System.out.println(set);
			System.out.println("是否包含" + td.getCont());
			if (set.contains(td.getCont())) {
				count = 1 + count;
				System.out.println("是");
			} else {
				System.out.println("否");
			}

		}
		// 还是取用百分比的计算方法，后期有待改进
		float titleAndKeyScore = (float) count / set.size();
		sentence.setTitleScore(titleAndKeyScore);

		return sentence;
	}

}
