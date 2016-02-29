package pomonitor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Emotionalword entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "emotionalword", catalog = "pomonitor", uniqueConstraints = @UniqueConstraint(columnNames = "word"))
public class Emotionalword implements java.io.Serializable {

	// Fields

	private Integer id;
	private String word;
	private String speech;
	private Integer polarity;
	private Integer strength;

	// Constructors

	/** default constructor */
	public Emotionalword() {
	}

	/** minimal constructor */
	public Emotionalword(String word) {
		this.word = word;
	}

	/** full constructor */
	public Emotionalword(String word, String speech, Integer polarity,
			Integer strength) {
		this.word = word;
		this.speech = speech;
		this.polarity = polarity;
		this.strength = strength;
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

	@Column(name = "word", unique = true, nullable = false)
	public String getWord() {
		return this.word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	@Column(name = "speech")
	public String getSpeech() {
		return this.speech;
	}

	public void setSpeech(String speech) {
		this.speech = speech;
	}

	@Column(name = "polarity")
	public Integer getPolarity() {
		return this.polarity;
	}

	public void setPolarity(Integer polarity) {
		this.polarity = polarity;
	}

	@Column(name = "strength")
	public Integer getStrength() {
		return this.strength;
	}

	public void setStrength(Integer strength) {
		this.strength = strength;
	}

	public String toString() {
		return new String(word + " " + speech + " " + polarity + " " + strength);
	}
}