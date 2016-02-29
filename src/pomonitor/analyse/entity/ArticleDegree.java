package pomonitor.analyse.entity;

public enum ArticleDegree {

	O("0级"), A("一级"), B("二级"), C("三级");// 级别越高越严重

	private String degree;

	// 枚举对象构造函数
	private ArticleDegree(String degree) {
		this.degree = degree;
	}

	// 枚举对象获取褒贬态度的方法
	public String getDegree() {
		return this.degree;
	}

}
