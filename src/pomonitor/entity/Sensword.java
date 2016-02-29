package pomonitor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Sensword entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sensword", catalog = "pomonitor")
/*
 * @SecondaryTables(
 * 
 * @SecondaryTable(name = "user") )
 */
public class Sensword implements java.io.Serializable {

	// Fields

	private Integer sensid;
	private String senslevel;
	private String sensvalue;
	private Integer userid;

	// Constructors

	/** default constructor */
	public Sensword() {
	}

	/** minimal constructor */
	public Sensword(Integer userid) {
		this.userid = userid;
	}

	/** full constructor */
	public Sensword(String senslevel, String sensvalue, Integer userid) {
		this.senslevel = senslevel;
		this.sensvalue = sensvalue;
		this.userid = userid;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "sensid", unique = true, nullable = false)
	public Integer getSensid() {
		return this.sensid;
	}

	public void setSensid(Integer sensid) {
		this.sensid = sensid;
	}

	@Column(name = "senslevel")
	public String getSenslevel() {
		return this.senslevel;
	}

	public void setSenslevel(String senslevel) {
		this.senslevel = senslevel;
	}

	@Column(name = "sensvalue")
	public String getSensvalue() {
		return this.sensvalue;
	}

	public void setSensvalue(String sensvalue) {
		this.sensvalue = sensvalue;
	}

	@Column(name = "userid", nullable = false)
	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

}
