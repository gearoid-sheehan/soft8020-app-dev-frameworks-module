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
import ie.assignmentTwo.forms.AddDirectorForm;
import ie.assignmentTwo.forms.AddFilmForm;
import ie.assignmentTwo.forms.UpdateFilmTitleForm;
import ie.assignmentTwo.services.DirectorService;
import ie.assignmentTwo.services.FilmService;

/*All of the controllers which handle requests that require at least 'User' access are located here*/

@Controller
public class UserAccessController {

	//Service layers autowired in for use in the controllers
	
	@Autowired
	FilmService filmService;
	@Autowired
	DirectorService directorService;
	
	//GET - Handles the request which provides form for adding a director to the DB
	
	@GetMapping("/adddirector")
	public String loadAddDirectorGet(Model model) {
		
		//Add form to be sent in the model object for a new director to be input into when page to add director is loaded
		model.addAttribute("addDirectorForm",new AddDirectorForm());
		
		return "adddirector";
	}
	
	//POST - Handles the request which posts the completed addDirectorForm for adding a director to the DB
	
	@PostMapping("/adddirector")
	public String loadAddDirectorPost(@Valid AddDirectorForm addDirectorForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		//Check if the data in the form conforms with the validation restrictions, if does not conform throw Spring boot generated error on same page
		if (bindingResult.hasErrors()) {
			return "adddirector";
		}
		
		//Director object to handle data returned when an attempt to save a director to the database is made 
		Director director = directorService.confirmAddDirectors(addDirectorForm.getNewDirectorName());
		
		//If the director object recieves null data back, redirect to same page and use flash attribute to show custom error message
		if (director == null) {
			redirectAttributes.addFlashAttribute("duplicateDirectorName", addDirectorForm.getNewDirectorName());
			return "redirect:adddirector";
		}
		
		//If director is added to the database successfully, redirect to the page listing all the directors in the database
		redirectAttributes.addFlashAttribute("directorAdded", addDirectorForm.getNewDirectorName());
		return "redirect:adddirector";
	}
	
	//GET Controller handling the request which provides form for adding a film to the DB
	
	@GetMapping("/addfilm")
	public String loadAddFilmGet(Model model, RedirectAttributes redirectAttributes) {
		
		//List of director objects to hold all directors returned by the service layer
		List<Director> directors = directorService.confirmGetDirectorsAlphabetically();
		
		//If there are no directors in the database, redirect to the directorserror page which displays error message and link option to adddirectors
		if (directors == null) {
			return "redirect:directorserror";
			
		} else {
		
			//Add list of directors and form to be sent in the model object for a new film to be input into when page to add film is loaded
			model.addAttribute("directors", directors);
			model.addAttribute("addFilmForm", new AddFilmForm());
			
			return "addfilm";
		}
	}
	
	//POST Controller handling the request which posts the completed addFilmForm for a film to be added to the DB
	
	/*
	 * This controller returns the data as a ModelAndView object. When a validation error occurs and the page is
	 * rendered again, the directors list in the dropdown is lost when. Therefore the controller has been modified to retrive the list of
	 * directors again when the page is re-rendered after a validation error occurs.
	 */
	
	@PostMapping("/addfilm")
	public ModelAndView loadAddFilmPost(@Valid AddFilmForm addFilmForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		//Check if the data in the form conforms with the validation restrictions, if does not conform throw Spring boot generated error on same page
		if (bindingResult.hasErrors()) {
			
			//List of director objects to hold all directors returned by the service layer after re-rendering
			List<Director> directors = directorService.confirmGetDirectorsAlphabetically();
			
			//Returns to the view with the list of directors
			return new ModelAndView("addfilm", "directors", directors);
		}
		
		//Calls filmService layer to add film to the DB and redirects to the films page
		filmService.confirmAddFilm(addFilmForm);
		redirectAttributes.addFlashAttribute("filmAdded", addFilmForm.getNewTitle());
		return new ModelAndView("redirect:addfilm");
	}
	
	//GET Controller handling the request which provides form for editing a films title in the DB
	
	@GetMapping("/editfilmstitle")
	public String loadEditFilmGet(Model model) {
		
		//Retrieves list of all films from the filmService layer
		List<Film> films = filmService.confirmGetAllFilms();
		
		//If there are no films in the database, redirect to the filmserror page which displays error message and link option to addfilms
		if (films == null) {
			return "redirect:filmserror";
			
		} else {
			
			//Add list of films and form to be sent in the model object so a film can be chosen for its title to be updated
			model.addAttribute("films", films);
			model.addAttribute("updateFilmTitleForm",new UpdateFilmTitleForm());
			return "editfilmstitle";
		}
	}
	
	/*
	 * This controller returns the data as a ModelAndView object. When a validation error occurs and the page is
	 * rendered again, the films list in the dropdown is lost when. Therefore the controller has been modified to retrive the list of
	 * films again when the page is re-rendered after a validation error occurs.
	 */
	
	@PostMapping("/editfilmstitle")
	public ModelAndView loadEditFilmPost(@Valid UpdateFilmTitleForm updateFilmTitleForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		//Check if the data in the form conforms with the validation restrictions, if does not conform throw Spring boot generated error on same page
		if (bindingResult.hasErrors()) {
			
			//List of film objects to hold all films returned by the service layer after re-rendering
			List<Film> films = filmService.confirmGetAllFilms();
			
			//Returns to the view with the list of directors
			return new ModelAndView("editfilmstitle", "films", films);
		}
		
		//Calls filmService layer to update film title in the DB and redirects to the films page
		filmService.confirmUpdateFilmTitle(updateFilmTitleForm);
		redirectAttributes.addFlashAttribute("filmtitleupdated", updateFilmTitleForm.getNewTitle());
		return new ModelAndView("redirect:editfilmstitle");
	}
	
}
