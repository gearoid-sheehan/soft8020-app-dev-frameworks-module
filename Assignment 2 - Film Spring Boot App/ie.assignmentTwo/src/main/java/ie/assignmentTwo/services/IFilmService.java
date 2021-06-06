package ie.assignmentTwo.services;

import java.util.List;

import ie.assignmentTwo.entities.Director;
import ie.assignmentTwo.entities.Film;
import ie.assignmentTwo.forms.AddFilmForm;
import ie.assignmentTwo.forms.DeleteFilmTitleForm;
import ie.assignmentTwo.forms.UpdateFilmTitleForm;

public interface IFilmService {

	List<Film> confirmGetAllFilms();
	
	List<Film> confirmGetAllFilmsFromDirector(String directorName);
	
	Film confirmAddFilm(AddFilmForm addFilmForm);
	
	void confirmUpdateFilmTitle(UpdateFilmTitleForm updateFilmTitleForm);
	
	boolean confirmDeleteFilm(DeleteFilmTitleForm deleteFilmTitleForm);
	
	List<Film> confirmGetAllFilmsInYear(int year);
}
