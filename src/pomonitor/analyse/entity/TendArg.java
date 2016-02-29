package pomonitor.analyse.entity;

public class TendArg {
	// ĞòºÅ
	private String id;
	// ½ÇÉ«Ãû³Æ
	private String type;
	// ½áÊøĞòºÅ
	private int end;

	// ¿ªÊ¼ĞòºÅ
	private int beg;

	public TendArg() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getBeg() {
		return beg;
	}

	public void setBeg(int beg) {
		this.beg = beg;
	}

}
