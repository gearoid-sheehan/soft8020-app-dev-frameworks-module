package ie.assignmentTwo.forms;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AddFilmForm {
	
	@NotBlank(message = "The title cannot be empty!")
	private String newTitle;
	
	@Min(1988)
	private int newYearOfRelease;
	
	@NotBlank(message = "A director must be selected!")
	private String newDirector;
}
