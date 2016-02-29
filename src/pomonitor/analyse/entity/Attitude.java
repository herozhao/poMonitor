package pomonitor.analyse.entity;

public enum Attitude {

	PRAISE("praise"), NEUTRAL("neutral"), DEROGATORY("derogatory");

	private String attitude;

	private Attitude(String attitude) {
		this.attitude = attitude;
	}

	public String getAttitude() {
		return this.attitude;
	}
}
