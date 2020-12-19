package ie.assignmentTwo.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ie.assignmentTwo.entities.Director;
import ie.assignmentTwo.entities.Film;
import ie.assignmentTwo.forms.DeleteDirectorForm;
import ie.assignmentTwo.forms.DeleteFilmTitleForm;
import ie.assignmentTwo.services.DirectorService;
import ie.assignmentTwo.services.FilmService;

@Controller
public class AdminAccessController {

	@Autowired
	FilmService filmService;
	@Autowired
	DirectorService directorService;
	
	@GetMapping("/deletefilm")
	public String loadDeleteFilmGet(Model model) {
		
		//Retrieves list of all films from the filmService layer
		List<Film> films = filmService.confirmGetAllFilms();
		
		//If there are no films in the database, redirect to the filmserror page which displays error message and link option to add films
		if (films == null) {
			
			return "redirect:filmserror";
		} 
			
		//Add list of films and form to be sent in the model object so a film can be chosen for its title to be updated
		else {
			
			model.addAttribute("films", films);
			model.addAttribute("deleteFilmTitleForm",new DeleteFilmTitleForm());
			
			return "deletefilm";
		}
	}
	
	@PostMapping("/deletefilm")
	public ModelAndView loadDeleteFilmPost(@Valid DeleteFilmTitleForm deleteFilmTitleForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		//Check if the data in the form conforms with the validation restrictions, if does not conform throw Spring boot generated error on same page
		if (bindingResult.hasErrors()) {
			
			//List of film objects to hold all films returned by the service layer after re-rendering
			List<Film> films = filmService.confirmGetAllFilms();
			
			//Returns to the view with the view model object
			return new ModelAndView("deletefilm", "films", films);
		}
		
		//Calls filmService layer to delete film from the DB
		filmService.confirmDeleteFilm(deleteFilmTitleForm);
		redirectAttributes.addFlashAttribute("filmdeleted", deleteFilmTitleForm.getDeleteTitle());
		return new ModelAndView("redirect:deletefilm");
	}
	
	@GetMapping("/deletedirectorandfilms")
	public String loadDeleteDirectorGet(Model model) {
		
		//List of director objects to hold all directors returned by the service layer
		List<Director> directors = directorService.confirmGetDirectorsAlphabetically();
		
		//If there are no directors in the database, redirect to the directorsserror page which displays error message and link option to add directors
		if (directors == null) {
			
			return "redirect:directorserror";
			
		} else {
			
		model.addAttribute("directors", directors);
		model.addAttribute("deleteDirectorForm", new DeleteDirectorForm());
		
		return "deletedirectorandfilms";
		
		}
	}
	
	@PostMapping("/deletedirectorandfilms")
	public ModelAndView loadDeleteDirectorPost(@Valid DeleteDirectorForm deleteDirectorForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		//Check if the data in the form conforms with the validation restrictions, if does not conform throw Spring boot generated error on same page
		if (bindingResult.hasErrors()) {
			
			//List of director objects to hold all directors returned by the service layer after re-rendering
			List<Director> directors = directorService.confirmGetDirectorsAlphabetically();
			
			//Returns to the view with the list of directors
			return new ModelAndView("deletefilm", "films", directors);
		}
		
		directorService.confirmDeleteByDirectorName(deleteDirectorForm.getDeleteName());
		
		return new ModelAndView("redirect:deletedirectorandfilms");
	}
	
	@GetMapping("/filmsintheyear/{year}")
	public List<Film> getAllFilmsInYear() {
		
		int year = 1998;
		List<Film> films = filmService.confirmGetAllFilmsInYear(year);
	
		return films;
	}
}
