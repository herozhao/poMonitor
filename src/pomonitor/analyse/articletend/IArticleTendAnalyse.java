package pomonitor.analyse.articletend;

import pomonitor.analyse.entity.TendAnalyseArticle;
import pomonitor.entity.News;

/**
 * 文章的倾向性分析接口
 * 
 * @author zhaolong 2015年12月14日 上午11:56:45
 */
public interface IArticleTendAnalyse {
	/**
	 * 传入新闻对象，返还一个计算好句子倾向性的acticle对象
	 * 
	 * @param news
	 * @return
	 */
	public TendAnalyseArticle analyseArticleTend(News news);
}
