package pomonitor.analyse.entity;

import java.util.Comparator;
import java.util.List;

/**
 * ����
 * 
 * @author caihengyi 2015��12��15�� ����10:17:13
 */
public class HotWord {

	private String content;// ��������

	public double weight;// ����Ȩ��
	private boolean isSensitiveWords;// �û����Ƿ������дʣ��������ʣ�
	public List<ArticleShow> articleViews;// �����û�������¼���
	private Attitude attitude;// �û���İ���̬����Ϣ
	private Integer belongto;// �����������

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public boolean isSensitiveWords() {
		return isSensitiveWords;
	}

	public void setSensitiveWords(boolean isSensitiveWords) {
		this.isSensitiveWords = isSensitiveWords;
	}

	public Attitude getAttitude() {
		return attitude;
	}

	public void setAttitude(Attitude attitude) {
		this.attitude = attitude;
	}

	public Integer getBelongto() {
		return belongto;
	}

	public void setBelongto(Integer belongto) {
		this.belongto = belongto;
	}

	public static Comparator<HotWord> getCompByWeight() {
		Comparator<HotWord> comp = new Comparator<HotWord>() {
			@Override
			public int compare(HotWord h1, HotWord h2) {
				if (h1.weight < h2.weight)
					return 1;
				if (h1.weight > h2.weight)
					return -1;
				else
					return 0;
			}
		};
		return comp;
	}

}
