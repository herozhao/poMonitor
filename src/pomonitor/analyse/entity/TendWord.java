package pomonitor.analyse.entity;

import java.util.List;

public class TendWord {
	// 分词的序号
	private int id;
	// 分词的内容
	private String cont;
	// 为词性标注内容
	private String pos;
	// 命名实体内容
	private String ne;
	// semparent 与 semrelate 成对出现
	// 语义依存分析的父亲结点id 号
	private int semparent;
	// 相对应的关系
	private String semrelate;
	// parent 与 relate 成对出现
	// parent 为依存句法分析的父亲结点id 号
	private int parent;
	// relate 为相对应的关系
	private String relate;

	// 如果用户做了srl级别的分析，json结果中还会有键值名arg所标识的数组。
	// 数组中的每个对象是一项语义角色，任何一个谓词都会带有若干个该对象；
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
