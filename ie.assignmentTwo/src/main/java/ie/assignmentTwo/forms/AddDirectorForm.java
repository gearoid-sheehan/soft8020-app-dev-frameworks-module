package ie.assignmentTwo.forms;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class AddDirectorForm {

	@Size(min=4, max=30)
	@Pattern(regexp="^[A-Za-z]*$",message = "Director name cannot contain non-alphabetical characters!")
	private String newDirectorName;
}
