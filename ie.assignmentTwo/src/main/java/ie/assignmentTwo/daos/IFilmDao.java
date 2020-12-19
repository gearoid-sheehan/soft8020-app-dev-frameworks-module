package ie.assignmentTwo.daos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ie.assignmentTwo.entities.Film;

public interface IFilmDao extends JpaRepository<Film, Integer>{
	
	//Returns list of films by a certain director
	List<Film> findAllByDirector_DirectorName(String directorName);
	
	//Updates the title of the film passed in
	@Modifying
	@Query(value = "UPDATE films f SET f.title = :newTitle WHERE f.title = :title", nativeQuery = true)
	@Transactional
	void updateFilmTitle(@Param("newTitle") String newTitle, @Param("title") String title);
	
	//Deletes a film by its title name
	int deleteByTitle(String title);
	
	List<Film> findAllByYearOfRelease(int year);
}
