package pomonitor.analyse.entity;

/**
 * 返回前端用的热词node节点
 * 
 * @author caihengyi
 *         2016年1月15日 上午10:58:21
 */
public class RetHotWord {
	// 热词极性，0-->中性 1-->褒义 2-->贬义
	private int category;
	// 热词索引
	private int index;
	// 热词是否是敏感词
	private int label;
	// 热词名称
	private String name;
	// 热词热度
	private double value;

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getLabel() {
		return label;
	}

	public void setLabel(int label) {
		this.label = label;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
