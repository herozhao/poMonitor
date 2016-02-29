package pomonitor.analyse.entity;

import java.util.Date;

/**
 * չʾ�б��õ��ı���������ͼ�ϱ���Ϊһ����Ϣ�����������tag��
 * 
 * @author caihengyi 2015��12��15�� ����10:29:25
 */
public class ArticleShow implements Comparable<ArticleShow> {

	private String title;
	private String url;
	private String description;
	private Date timestamp;
	private Attitude attitude;// ����������
	private ArticleDegree degree;// �����Էּ�
	private String comeFrom;// ��Դ��վ������
	public Double heat = 0.0;// �������ŵ��ȶ�

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

	public Attitude getAttitude() {
		return attitude;
	}

	public void setAttitude(Attitude attitude) {
		this.attitude = attitude;
	}

	public ArticleDegree getDegree() {
		return degree;
	}

	public void setDegree(ArticleDegree degree) {
		this.degree = degree;
	}

	public String getComeFrom() {
		return comeFrom;
	}

	public void setComeFrom(String comeFrom) {
		this.comeFrom = comeFrom;
	}

	public Double getHeat() {
		return heat;
	}

	public void setHeat(Double heat) {
		this.heat = heat;
	}

	@Override
	public int compareTo(ArticleShow o) {
		if (this.heat < o.heat)
			return -1;
		if (this.heat > o.heat)
			return 1;
		return 0;
	}

	public boolean equals(ArticleShow o) {
		if (this.title == o.title && this.description == o.description
				&& this.url == o.url && this.timestamp == o.timestamp)
			return true;
		return false;
	}
}
