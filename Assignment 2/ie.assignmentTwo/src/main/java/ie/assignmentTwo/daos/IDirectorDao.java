package ie.assignmentTwo.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ie.assignmentTwo.entities.Director;

public interface IDirectorDao extends JpaRepository<Director, String>{
	
	//Finds a list all directors by their names ascending
	List<Director> findAllByOrderByDirectorNameAsc();
	
	//Returns a boolean if a director by the given name exists
	boolean existsByDirectorName(String directorName);
	
	//Returns a director object equal to the name passed in
	Director findByDirectorName(String directorName);
	
	//Deletes a director by equal to the name passed in
	int deleteByDirectorName(String directorName);
}
