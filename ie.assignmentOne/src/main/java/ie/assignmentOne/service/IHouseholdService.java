package ie.assignmentOne.service;

import java.util.List;

import ie.assignmentOne.classes.Household;
import ie.assignmentOne.classes.Occupant;

public interface IHouseholdService {
	
	String confirmGetOccupants(String eircode);

	String confirmAddHousehold(Household household, List<Occupant> occupants);
	
	String confirmAddOccupants(Occupant occupant);
	
	String confirmMoveOccupant(String name, String eircodeTo);
	
	String confirmDeleteHousehold(String eircode);
	
	String confirmDeleteOccupant(String name);
	
	String getStatistics();
}
