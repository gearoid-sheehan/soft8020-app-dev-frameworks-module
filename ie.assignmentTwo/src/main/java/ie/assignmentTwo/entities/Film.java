package ie.assignmentTwo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "films")
public class Film {

	public Film(String title, int yearOfRelease, Director director) {
		this.title = title;
		this.yearOfRelease = yearOfRelease;
		this.director = director;
	}
	
	@Id
	@GeneratedValue
	private int filmId;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "yearofrelease", nullable = false)
	private int yearOfRelease;
	
	@ManyToOne
	@JoinColumn(name = "directorname", nullable = false)
	private Director director;
}