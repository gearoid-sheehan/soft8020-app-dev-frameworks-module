package ie.assignmentTwo.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ie.assignmentTwo.daos.IDirectorDao;
import ie.assignmentTwo.daos.IFilmDao;
import ie.assignmentTwo.entities.Director;
import ie.assignmentTwo.entities.Film;
import ie.assignmentTwo.forms.AddFilmForm;
import ie.assignmentTwo.forms.DeleteFilmTitleForm;
import ie.assignmentTwo.forms.UpdateFilmTitleForm;

@Service
@Transactional
public class FilmService implements IFilmService{

	//DAO layers autowired in for use in the controllers
	
	@Autowired
	IDirectorDao directorDao;
	@Autowired
	IFilmDao filmDao;
	
	//Calls the filmDao to retrieve and return all films present in the database
	public List<Film> confirmGetAllFilms() {
			
		return filmDao.findAll();
	}
	
	//Calls the filmDao to retrieve and return a list of all the films from the selected director
	public List<Film> confirmGetAllFilmsFromDirector(String directorName) {
		
		//If the selected director has no films in the database, returns null
		if (filmDao.findAllByDirector_DirectorName(directorName).size() == 0) {
			return null;
			
		//Else returns a list of all the films from the selected director
		} else {
		
			return filmDao.findAllByDirector_DirectorName(directorName);
		}
	}
	
	//Calls the filmDao to add a Film using the form passed in and returns that Film as a Film object 
	public Film confirmAddFilm(AddFilmForm addFilmForm) {
		
		//Saves a Director object which has a name equal to the director selected from a dropdown
		Director director = directorDao.findByDirectorName(addFilmForm.getNewDirector());
		
		//Creates a Film object using the remaining data from the form and the Director object saved above
		Film film = new Film(addFilmForm.getNewTitle(), addFilmForm.getNewYearOfRelease(), director);

		//Saves the film to the DB and returns it as a Film object
		return filmDao.save(film);
	}
	
	//Call film dao and updates a particular films title
	public void confirmUpdateFilmTitle(UpdateFilmTitleForm updateFilmTitleForm) {
		
		//Send the form with the title to change and the new title to the dao
		filmDao.updateFilmTitle(updateFilmTitleForm.getNewTitle(), updateFilmTitleForm.getOldTitle());
	}
	
	public boolean confirmDeleteFilm(DeleteFilmTitleForm deleteFilmTitleForm) {
		
		//Gets result of whether a film has been delted or not
		int result = filmDao.deleteByTitle(deleteFilmTitleForm.getDeleteTitle());
		
		//If the result is equal to 1 return true
		if (result == 1) {
			return true;
		} 
		
		//Else return false
		else {
			
			return false;
		}
	}
	
	public List<Film> confirmGetAllFilmsInYear(int year) {
		
		return filmDao.findAllByYearOfRelease(year);
	}
}
