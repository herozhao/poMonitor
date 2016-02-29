package pomonitor.entity;

import java.util.List;

import javax.persistence.Entity;

public class NewsEntity {

	private String id;
	private String title;
	private String url;
	private String content;
	private String web;
	private String time;
	private String allContent;

	private String contentPath;

	private int failedCount;

	private List<String> keywords;

	private final int maxFailedCount = 4;

	private boolean isFinish = false;

	private boolean isFailed = false;

	private boolean isWorking = false;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isFinish() {
		return isFinish;
	}

	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}

	public String getContentPath() {
		return contentPath;
	}

	public boolean isFailed() {
		return isFailed;
	}

	public void setFailed(boolean isFailed) {
		this.isFailed = isFailed;
	}

	public boolean isWorking() {
		return isWorking;
	}

	public void setWorking(boolean isWorking) {
		this.isWorking = isWorking;
	}

	public int getMaxFailedCount() {
		return maxFailedCount;
	}

	public void setContentPath(String contentPath) {
		this.contentPath = contentPath;
	}

	public int getFailedCount() {
		return failedCount;
	}

	public void setFailedCount(int failedCount) {
		this.failedCount = failedCount;
	}

	public void countFailed() {
		failedCount++;
		if (failedCount >= maxFailedCount) {
			isFinish = true;
			isFailed = true;
		}
	}

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getAllContent() {
		return allContent;
	}

	public void setAllContent(String allContent) {
		this.allContent = allContent;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

}
