package pomonitor.analyse.entity;

/**
 * ����ǰ���õ��ȴ�node�ڵ�
 * 
 * @author caihengyi
 *         2016��1��15�� ����10:58:21
 */
public class RetHotWord {
	// �ȴʼ��ԣ�0-->���� 1-->���� 2-->����
	private int category;
	// �ȴ�����
	private int index;
	// �ȴ��Ƿ������д�
	private int label;
	// �ȴ�����
	private String name;
	// �ȴ��ȶ�
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
