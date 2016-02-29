package pomonitor.analyse.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 话题发现模块中从数据库得到新闻文本对象之后， 重新封装的用于话题发现的新闻文本对象
 * 
 * @author caihengyi 2015年12月15日 下午2:22:32
 */
public class TDArticle {

	private String id;
	private String title;
	private String url;
	private String description;
	private Date timestamp;
	private String comeFrom;// 来源网站的名字
	private List<TDArticleTerm> articleAllTerms;// 该文章包括的所有的词项集合

	private Map<String, Double> termsWeights; // 代表该篇文章所有词项的权重信息
	public double[] vectorSpace;// 和所有文本向量vectorSpace关联的有一个全局的有序词项序列

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
