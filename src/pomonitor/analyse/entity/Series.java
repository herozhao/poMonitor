package pomonitor.analyse.entity;

import org.apache.taglibs.standard.tag.el.sql.SetDataSourceTag;

/**
 * 
 * @author xiaoyulun 2016��1��5�� ����11:44:44
 */
public class Series {
	public float data;
	public String polarity;

	public void setData(float data) {
		this.data = data;
	}

	public void setPolarity(String polarity) {
		this.polarity = polarity;
	}
}
