package pomonitor.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * News entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "news", catalog = "pomonitor", uniqueConstraints = {
		@UniqueConstraint(columnNames = "id"),
		@UniqueConstraint(columnNames = "url") })
public class News implements java.io.Serializable {

	// Fields

	private Integer relId;
	private String id;
	private String title;
	private String url;
	private String content;
	private String web;
	private Date time;
	private String allContent;
	private String keyWords;
	private String contentPath;
	private Integer failedCount;
	private Integer isFinsh;
	private Integer isFailed;
	private Integer isWorking;

	// Constructors

	/** default constructor */
	public News() {
	}

	/** minimal constructor */
	public News(String id, String title, String url, String content,
			String web, Date time) {
		this.id = id;
		this.title = title;
		this.url = url;
		this.content = content;
		this.web = web;
		this.time = time;
	}

	/** full constructor */
	public News(String id, String title, String url, String content,
			String web, Date time, String allContent, String keyWords,
			String contentPath, Integer failedCount, Integer isFinsh,
			Integer isFailed, Integer isWorking) {
		this.id = id;
		this.title = title;
		this.url = url;
		this.content = content;
		this.web = web;
		this.time = time;
		this.allContent = allContent;
		this.keyWords = keyWords;
		this.contentPath = contentPath;
		this.failedCount = failedCount;
		this.isFinsh = isFinsh;
		this.isFailed = isFailed;
		this.isWorking = isWorking;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "relId", unique = true, nullable = false)
	public Integer getRelId() {
		return this.relId;
	}

	public void setRelId(Integer relId) {
		this.relId = relId;
	}

	@Column(name = "id", unique = true, nullable = false, length = 50)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "title", nullable = false, length = 100)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "url", unique = true, nullable = false)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "content", nullable = false, length = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "web", nullable = false)
	public String getWeb() {
		return this.web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "time", nullable = false, length = 10)
	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Column(name = "allContent", length = 65535)
	public String getAllContent() {
		return this.allContent;
	}

	public void setAllContent(String allContent) {
		this.allContent = allContent;
	}

	@Column(name = "keyWords")
	public String getKeyWords() {
		return this.keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	@Column(name = "contentPath")
	public String getContentPath() {
		return this.contentPath;
	}

	public void setContentPath(String contentPath) {
		this.contentPath = contentPath;
	}

	@Column(name = "failedCount")
	public Integer getFailedCount() {
		return this.failedCount;
	}

	public void setFailedCount(Integer failedCount) {
		this.failedCount = failedCount;
	}

	@Column(name = "isFinsh")
	public Integer getIsFinsh() {
		return this.isFinsh;
	}

	public void setIsFinsh(Integer isFinsh) {
		this.isFinsh = isFinsh;
	}

	@Column(name = "isFailed")
	public Integer getIsFailed() {
		return this.isFailed;
	}

	public void setIsFailed(Integer isFailed) {
		this.isFailed = isFailed;
	}

	@Column(name = "isWorking")
	public Integer getIsWorking() {
		return this.isWorking;
	}

	public void setIsWorking(Integer isWorking) {
		this.isWorking = isWorking;
	}

}