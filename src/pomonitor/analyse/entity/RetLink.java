package pomonitor.analyse.entity;

/**
 * 热词的关联
 * 
 * @author caihengyi
 *         2016年1月15日 上午11:03:47
 */
public class RetLink {
	private int source;
	private int target;
	private double weight;

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

}
