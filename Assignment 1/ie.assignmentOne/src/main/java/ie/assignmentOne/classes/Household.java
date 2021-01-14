package ie.assignmentOne.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Household {

	private int householdId;
	private String eircode;
	private String address;
}
