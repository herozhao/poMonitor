package pomonitor.analyse.entity;

import java.util.List;

/**
 * ��������У�ĳ�����ʵ��
 * 
 * @author caihengyi 2015��12��20�� ����9:28:22
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
