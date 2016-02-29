package pomonitor.entity;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * LeverWord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "levelword", catalog = "pomonitor")
public class LeverWord implements java.io.Serializable {

	// Fields

	private Integer id;
	private String word;
	private Float score;

	// Constructors

	/** default constructor */
	public LeverWord() {
	}

	/** full constructor */
	public LeverWord(Integer id, String word, Float score) {
		this.id = id;
		this.word = word;
		this.score = score;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "word", nullable = false)
	public String getWord() {
		return this.word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	@Column(name = "score", nullable = false, precision = 12, scale = 0)
	public Float getScore() {
		return this.score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

}