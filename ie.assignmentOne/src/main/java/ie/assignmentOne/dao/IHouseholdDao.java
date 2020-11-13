package ie.assignmentOne.dao;

import ie.assignmentOne.classes.Household;

public interface IHouseholdDao {

	Household getHousehold(String eircode);
	
	int addHousehold(Household household);
	
	int getHouseHoldCount();
	
	Boolean deleteHousehold(String eircode);
	
	boolean checkHouseholdExists(String eircode);
}
