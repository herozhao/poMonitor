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
 * WordProperty entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "wordproperty", catalog = "pomonitor", uniqueConstraints = @UniqueConstraint(columnNames = "property"))
public class WordProperty implements java.io.Serializable {

	// Fields

	private Integer id;
	private String property;

	// Constructors

	/** default constructor */
	public WordProperty() {
	}

	/** full constructor */
	public WordProperty(String property) {
		this.property = property;
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

	@Column(name = "property", unique = true, nullable = false, length = 10)
	public String getProperty() {
		return this.property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

}