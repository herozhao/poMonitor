package pomonitor.analyse.articlesubanalyse;

import java.util.Set;

import pomonitor.analyse.entity.TendAnalyseArticle;
import pomonitor.analyse.entity.TendSentence;
import pomonitor.analyse.entity.TendWord;

/**
 * ���±���ӷ֣��˴�������ؼ��ּӷֺϲ������Ժ�����ϸ��
 * 
 * @author Administrator
 * 
 */
public class SubScoreAddTitle implements ISubScoreAdd {

	@Override
	public TendSentence add(TendAnalyseArticle article, TendSentence sentence) {
		// ����ʵ�ʵ���Ŀ
		float count = 0;
		// title��������ʵ��
		Set<String> set = article.getSet();
		for (TendWord td : sentence.getWords()) {
			// ����˾仰����title�к�keyWords�еĴ���ӷ�
			System.out.println(set);
			System.out.println("�Ƿ����" + td.getCont());
			if (set.contains(td.getCont())) {
				count = 1 + count;
				System.out.println("��");
			} else {
				System.out.println("��");
			}

		}
		// ����ȡ�ðٷֱȵļ��㷽���������д��Ľ�
		float titleAndKeyScore = (float) count / set.size();
		sentence.setTitleScore(titleAndKeyScore);

		return sentence;
	}

}
