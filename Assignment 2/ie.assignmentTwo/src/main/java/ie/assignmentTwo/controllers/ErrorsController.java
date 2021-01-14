package ie.assignmentTwo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorsController {
	
	//GET - Displays error page when no directors are present in the database
	
	@GetMapping("/directorserror")
	public String loadDirectorsErrorGet() {
		
		return "directorserror";
	}
	
	//GET - Displays error page when no films are present in the database
	
	@GetMapping("/filmserror")
	public String loadFilmsErrorGet() {
		
		return "filmserror";
	}

}
