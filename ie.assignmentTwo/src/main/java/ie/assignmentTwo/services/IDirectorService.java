package ie.assignmentTwo.services;

import java.util.List;

import ie.assignmentTwo.entities.Director;

public interface IDirectorService {

	List<Director> confirmGetDirectorsAlphabetically();
	
	Director confirmAddDirectors(String directorName);
	
	boolean confirmDeleteByDirectorName(String directorName);
}
