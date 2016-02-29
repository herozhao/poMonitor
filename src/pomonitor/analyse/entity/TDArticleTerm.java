package pomonitor.analyse.entity;

/**
 * 话题发现模块中，词项实体
 * 
 * @author caihengyi 2015年12月15日 下午2:40:53
 */
public class TDArticleTerm {
	private String value;// 词项内容
	private TDPosition position;// 该词项在该文本的位置

	public String getvalue() {
		return value;
	}

	public void setvalue(String value) {
		this.value = value;
	}

	public TDPosition getposition() {
		return position;
	}

	public void setposition(TDPosition position) {
		this.position = position;
	}
}
