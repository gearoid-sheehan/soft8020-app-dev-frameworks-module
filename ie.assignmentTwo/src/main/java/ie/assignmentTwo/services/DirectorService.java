package ie.assignmentTwo.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ie.assignmentTwo.daos.IDirectorDao;
import ie.assignmentTwo.entities.Director;

@Service
@Transactional
public class DirectorService implements IDirectorService {
	
	@Autowired
	IDirectorDao directorDao;
	
	//Calls the directorDao and retrives all directors in alphabetical order by their surnames
	public List<Director> confirmGetDirectorsAlphabetically() {
		
		//If there are no directors in the database, returns null
		if (directorDao.findAllByOrderByDirectorNameAsc().size() == 0) {
			return null;
			
		//Otherwise gets a list of all the directors
		} else {
			
			return directorDao.findAllByOrderByDirectorNameAsc();
		}
	}
	
	//Calls the director dao to add directors to the database
	public Director confirmAddDirectors(String directorName) {
		
		//Creates new director object and sets the name
		Director director = new Director();
		director.setDirectorName(directorName);
		
		//If the director already exists in the database returns null
		if (directorDao.existsByDirectorName(directorName) == true) {
			return null;
		
		//Otherwise adds director to the database
		} else {
			
			return directorDao.save(director);
		}
	}
	
	//Calls the director dao and deletes the director equal to the name passed in plus all their films
	public boolean confirmDeleteByDirectorName(String directorName) {
		
		//Gets the result of whether the director was successfully deleted or not
		int result = directorDao.deleteByDirectorName(directorName);
		
		//If the director was deleted return true
		if (result == 1) {
			return true;
		} 
		
		//Otherwise return false
		else {
			return false;
		}
	}
}
