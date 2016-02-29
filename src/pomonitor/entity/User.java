package pomonitor.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user", catalog = "pomonitor")
public class User implements java.io.Serializable {

	// Fields

	private Integer userid;
	private String username;
	private String userpwd;
	private String userlevel;
	private Set<Sensword> senswords = new HashSet<Sensword>(0);

	// Constructors

	/** default constructor */
	public User() {
	}

	/** full constructor */
	public User(String username, String userpwd, String userlevel,
			Set<Sensword> senswords) {
		this.username = username;
		this.userpwd = userpwd;
		this.userlevel = userlevel;
		this.senswords = senswords;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "userid", unique = true, nullable = false)
	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Column(name = "username")
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "userpwd")
	public String getUserpwd() {
		return this.userpwd;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

	@Column(name = "userlevel")
	public String getUserlevel() {
		return this.userlevel;
	}

	public void setUserlevel(String userlevel) {
		this.userlevel = userlevel;
	}

	// @JoinColumn(name="sensword")
	// @OneToMany(targetEntity=Sensword.class,cascade = CascadeType.ALL, fetch =
	// FetchType.LAZY)
	@JoinColumn(name = "userid")
	public Set<Sensword> getSenswords() {
		return this.senswords;
	}

	public void setSenswords(Set<Sensword> senswords) {
		this.senswords = senswords;
	}

}
