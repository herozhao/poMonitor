package pomonitor.analyse.entity;

public class HotWordResponse {
	// ×´Ì¬Âë
	private int status;
	// ½á¹û
	private HotWordResult results;
	// message
	private String message;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public HotWordResult getResults() {
		return results;
	}

	public void setResults(HotWordResult results) {
		this.results = results;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
