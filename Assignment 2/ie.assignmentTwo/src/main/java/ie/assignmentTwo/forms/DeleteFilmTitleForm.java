package ie.assignmentTwo.forms;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class DeleteFilmTitleForm {

	@NotBlank
	private String deleteTitle;
	
}
