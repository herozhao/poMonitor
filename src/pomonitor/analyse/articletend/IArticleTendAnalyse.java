package pomonitor.analyse.articletend;

import pomonitor.analyse.entity.TendAnalyseArticle;
import pomonitor.entity.News;

/**
 * ���µ������Է����ӿ�
 * 
 * @author zhaolong 2015��12��14�� ����11:56:45
 */
public interface IArticleTendAnalyse {
	/**
	 * �������Ŷ��󣬷���һ������þ��������Ե�acticle����
	 * 
	 * @param news
	 * @return
	 */
	public TendAnalyseArticle analyseArticleTend(News news);
}
