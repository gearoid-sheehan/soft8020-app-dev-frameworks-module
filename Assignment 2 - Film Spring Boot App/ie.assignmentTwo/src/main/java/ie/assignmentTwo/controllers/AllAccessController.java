package ie.assignmentTwo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ie.assignmentTwo.entities.Director;
import ie.assignmentTwo.entities.Film;
import ie.assignmentTwo.forms.AddDirectorForm;
import ie.assignmentTwo.forms.AddFilmForm;
import ie.assignmentTwo.forms.DeleteDirectorForm;
import ie.assignmentTwo.forms.DeleteFilmTitleForm;
import ie.assignmentTwo.forms.UpdateFilmTitleForm;
import ie.assignmentTwo.services.DirectorService;
import ie.assignmentTwo.services.FilmService;

@Controller
public class AllAccessController {

	@Autowired
	FilmService filmService;
	@Autowired
	DirectorService directorService;
	
	@GetMapping("/")
	public String loadHomeGet() {
		return "index";
	}
	
	//Controller to retrieve all the directors in the database
	@GetMapping("/directors")
	public String loadDirectorsGet(Model model) {
		
		//List of director objects to hold all directors returned by the service layer
		List<Director> directors = directorService.confirmGetDirectorsAlphabetically();
		
		
		if (directors == null) {
			return "notfounderror";
		}
		
		model.addAttribute("directors", directors);
		return "directors";
	}
	
	@GetMapping("/films")
	public String loadFilmsGet(Model model) {
		
		List<Film> films = filmService.confirmGetAllFilms();
		
		if (films == null) {
			return "notfounderror";
		}
		
		model.addAttribute("films", films);
		return "films";
	}
	
	@GetMapping("/directorsfilms/{directorName}")
	public String loadDirectorsFilmsGet(@PathVariable(name="directorName", required = true) String directorName, Model model) {
		
		List<Film> directorsFilms = filmService.confirmGetAllFilmsFromDirector(directorName);
		
		if (directorsFilms == null) {
			return "notfounderror";
		}
		
		model.addAttribute("directorsfilms", directorsFilms);
		return "directorsfilms";
	}
	
	@GetMapping("/edit")
	public String loadEditGet() {
		return "edit";
	}

}
