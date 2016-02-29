package pomonitor.analyse.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ���ⷢ��ģ���д����ݿ�õ������ı�����֮�� ���·�װ�����ڻ��ⷢ�ֵ������ı�����
 * 
 * @author caihengyi 2015��12��15�� ����2:22:32
 */
public class TDArticle {

	private String id;
	private String title;
	private String url;
	private String description;
	private Date timestamp;
	private String comeFrom;// ��Դ��վ������
	private List<TDArticleTerm> articleAllTerms;// �����°��������еĴ����

	private Map<String, Double> termsWeights; // �����ƪ�������д����Ȩ����Ϣ
	public double[] vectorSpace;// �������ı�����vectorSpace��������һ��ȫ�ֵ������������

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getComeFrom() {
		return comeFrom;
	}

	public void setComeFrom(String comeFrom) {
		this.comeFrom = comeFrom;
	}

	public List<TDArticleTerm> getArticleAllTerms() {
		return articleAllTerms;
	}

	public void setArticleAllTerms(List<TDArticleTerm> articleAllTerms) {
		this.articleAllTerms = articleAllTerms;
	}

	public Map<String, Double> getTermsWeights() {
		return termsWeights;
	}

	public void setTermsWeights(Map<String, Double> termsWeights) {
		this.termsWeights = termsWeights;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
