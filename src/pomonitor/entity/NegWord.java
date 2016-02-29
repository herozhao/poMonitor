package pomonitor.entity;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * NegWord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "negword", catalog = "pomonitor", uniqueConstraints = @UniqueConstraint(columnNames = "word"))
public class NegWord implements java.io.Serializable {

	// Fields

	private Integer id;
	private String word;

	// Constructors

	/** default constructor */
	public NegWord() {
	}

	/** full constructor */
	public NegWord(String word) {
		this.word = word;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "word", unique = true, nullable = false, length = 10)
	public String getWord() {
		return this.word;
	}

	public void setWord(String word) {
		this.word = word;
	}

}