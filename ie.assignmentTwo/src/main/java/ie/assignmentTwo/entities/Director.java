package ie.assignmentTwo.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "directors")
public class Director {
	
	public Director(String name) {
		this.directorName = name;
	}
	
	@Id
	@GeneratedValue
	private int directorId;
	
	@Column(name = "directorname", nullable = false, unique = true)
	private String directorName;
	
	//Lazy means when getting director info, don't get list of films automatically. This can be changed by using 'EAGER'
	@OneToMany(mappedBy = "director", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<Film> films = new ArrayList<>();

}
