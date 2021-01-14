package ie.assignmentOne.dao;

import java.util.List;

import ie.assignmentOne.classes.Occupant;

public interface IOccupantDao {

	List<Occupant> getOccupants(String eircode);
	
	int addOccupant(Occupant occupant);
	
	boolean moveOccupant(String eircodeTo, String name, String eircodeFrom);
	
	boolean deleteHousehold(String eircode);
	
	boolean deleteOccupant(String name);
	
	int getOccupantsAgeAvg();
	
	int getCountStudents();
	
	int getCountOAPs();
	
	boolean	checkOccupantExists(String name);
	
	Occupant getOccupant(String name);
	
	List<Occupant> getAllOccupants();
}
