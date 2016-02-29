package pomonitor.analyse.entity;

public enum TDPosition {
	META("meta"), BODY("body"), TITLE("title");

	private String position;

	private TDPosition(String position) {
		this.position = position;
	}

	public String getTDPosition() {
		return this.position;
	}
}
