package ie.assignmentOne.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Occupant {
	
	private int occupantId;
	private String name;
	private int age;
	private String occupation;
	private String eircode;
}
