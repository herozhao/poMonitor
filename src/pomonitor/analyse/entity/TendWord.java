package pomonitor.analyse.entity;

import java.util.List;

public class TendWord {
	// �ִʵ����
	private int id;
	// �ִʵ�����
	private String cont;
	// Ϊ���Ա�ע����
	private String pos;
	// ����ʵ������
	private String ne;
	// semparent �� semrelate �ɶԳ���
	// ������������ĸ��׽��id ��
	private int semparent;
	// ���Ӧ�Ĺ�ϵ
	private String semrelate;
	// parent �� relate �ɶԳ���
	// parent Ϊ����䷨�����ĸ��׽��id ��
	private int parent;
	// relate Ϊ���Ӧ�Ĺ�ϵ
	private String relate;

	// ����û�����srl����ķ�����json����л����м�ֵ��arg����ʶ�����顣
	// �����е�ÿ��������һ�������ɫ���κ�һ��ν�ʶ���������ɸ��ö���
	private List<TendArg> arg;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCont() {
		return cont;
	}

	public void setCont(String cont) {
		this.cont = cont;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getNe() {
		return ne;
	}

	public void setNe(String ne) {
		this.ne = ne;
	}

	public int getSemparent() {
		return semparent;
	}

	public void setSemparent(int semparent) {
		this.semparent = semparent;
	}

	public String getSemrelate() {
		return semrelate;
	}

	public void setSemrelate(String semrelate) {
		this.semrelate = semrelate;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public String getRelate() {
		return relate;
	}

	public void setRelate(String relate) {
		this.relate = relate;
	}

	public List<TendArg> getArg() {
		return arg;
	}

	public void setArg(List<TendArg> arg) {
		this.arg = arg;
	}

	public TendWord() {
		super();
	}
}
