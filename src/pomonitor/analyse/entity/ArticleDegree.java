package pomonitor.analyse.entity;

public enum ArticleDegree {

	O("0��"), A("һ��"), B("����"), C("����");// ����Խ��Խ����

	private String degree;

	// ö�ٶ����캯��
	private ArticleDegree(String degree) {
		this.degree = degree;
	}

	// ö�ٶ����ȡ����̬�ȵķ���
	public String getDegree() {
		return this.degree;
	}

}
