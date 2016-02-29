package pomonitor.analyse.entity;

import java.util.List;

/**
 * 聚类分析中，某个类别实体
 * 
 * @author caihengyi 2015年12月20日 下午9:28:22
 */
public class TDCentroid {

	public List<TDArticle> GroupedArticle;
	public Integer CentroidNumber = 0;

	public List<TDArticle> getGroupedArticle() {
		return GroupedArticle;
	}

	public void setGroupedArticle(List<TDArticle> groupedArticle) {
		GroupedArticle = groupedArticle;
	}
}
