package pomonitor.entity;

// default package

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * NewsTend entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "newstend", catalog = "pomonitor", uniqueConstraints = @UniqueConstraint(columnNames = "newsId"))
public class NewsTend implements java.io.Serializable {

	// Fields
	private Integer id;
	private Integer newsId;
	private String web;
	private Date date;
	private Integer tendclass;
	private Float tendscore;

	// Constructors

	/** default constructor */
	public NewsTend() {
	}

	/** full constructor */
	public NewsTend(Integer newsId, String web, Date date, Integer tendclass,
			Float tendscore) {
		this.newsId = newsId;
		this.web = web;
		this.date = date;
		this.tendclass = tendclass;
		this.tendscore = tendscore;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "newsId", unique = true, nullable = false)
	public Integer getNewsId() {
		return this.newsId;
	}

	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}

	@Column(name = "web", nullable = false)
	public String getWeb() {
		return this.web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date", nullable = false, length = 10)
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "tendclass", nullable = false)
	public Integer getTendclass() {
		return this.tendclass;
	}

	public void setTendclass(Integer tendclass) {
		this.tendclass = tendclass;
	}

	@Column(name = "tendscore", nullable = false, precision = 12, scale = 0)
	public Float getTendscore() {
		return this.tendscore;
	}

	public void setTendscore(Float tendscore) {
		this.tendscore = tendscore;
	}

}
