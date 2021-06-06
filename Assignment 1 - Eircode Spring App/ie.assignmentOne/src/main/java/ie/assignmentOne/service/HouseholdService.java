package ie.assignmentOne.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ie.assignmentOne.classes.Household;
import ie.assignmentOne.classes.Occupant;
import ie.assignmentOne.dao.HouseholdDao;
import ie.assignmentOne.dao.OccupantDao;

@Service
public class HouseholdService implements IHouseholdService {

	@Autowired
	HouseholdDao householdDao;
	@Autowired
	OccupantDao occupantDao;
	
	//TASK 1 - BUSINESS LOGIC
	public String confirmGetOccupants(String eircode) {
		
		/*
		 * Checks if there is a Household at the Eircode passed in. As a household can't be added in the addHousehold()
		 * method without having at least 1 occupant, if the getOccupants() method returns from the DAO empty it means 
		 * a household with this Eircode is not in the database. 
		 */
		
		if (occupantDao.getOccupants(eircode).size() == 0) {
			return "A household with this eircode does not exist in the database";
			
		//If the Eircode exists, gets list of occupants from DAO and return a string of names
		} else {
			List<Occupant> occupants = occupantDao.getOccupants(eircode);
			String listOccupants = "";
			
			for (Occupant occupant: occupants) {
				listOccupants = listOccupants.concat(occupant.getName().toString() + "\n");
			}
			return listOccupants;
		}
	}
	
	//TASK 2 - BUSINESS LOGIC
	public String confirmAddHousehold(Household household, List<Occupant> occupants) {
		
		/*
		 * Calls DAO query which checks if a household with this eircode exists in the database already.
		 * If it does not exist, checks if the user has entered at least one occupant to be added to the household. If at
		 * least one is being added, it checks if the occupant(s) being added are in the database already by calling the DAO
		 * method to check by name.
		 */
		
		if (!householdDao.checkHouseholdExists(household.getEircode())) {
			if (occupants.size() == 0) {
				return "At least one person must live in a household\n";
			}
			
			for (Occupant occupant: occupants) {
				if (occupantDao.checkOccupantExists(occupant.getName())) {
					return occupant.getName() + " is already an occupant in a different household\n";
				}
			}
			
			//If all the above conditions have been passed, adds the household and each occupant to the database by calling DAO
			householdDao.addHousehold(household);
			
			for (Occupant occupant: occupants) {
				occupantDao.addOccupant(occupant);
			}
			return "Household and occupants added\n";
			
		//If the first statement is true then a household already exists and this message is returned
		} else {
			return "This eircode already exists in the database\n";
		}
	}
	
	//TASK 3 - BUSINESS LOGIC
	public String confirmAddOccupants(Occupant occupant) {
		
		/*
		 * Calls DAO query to check if an occupant with this name already exists in the database. Returns a message if one
		 * already exists, if one does not exist checks if a household with given Eircode exists. If the Eircode already exists
		 * in the database, returns message.  
		 */
		
		if (occupantDao.checkOccupantExists(occupant.getName())) {
			return occupant.getName() + " is already an occupant in a different household\n";
			
		} else if (occupantDao.getOccupants(occupant.getEircode()).size() == 0) {
			return "A household with this eircode does not exist in the database\n";
		}
		
		//If occupant does not exist and eircode does, add new occupant to eircode
		occupantDao.addOccupant(occupant);
		return "Successfully added occupant to household\n";
	}
	
	//TASK 4 - BUSINESS LOGIC
	public String confirmMoveOccupant(String name, String eircodeTo) {

		/*
		 * Checks if an occupant with the given name exists in the database.
		 * Checks if a household with the given eircode exists in the database.
		 * Gets the current eircode of the given occupant and moves that occupant
		 * to the new eircode.
		 */
		
		if (!occupantDao.checkOccupantExists(name)) {
			return "An occupant with the name " + name + " does not exist in the database\n";
			
		} else {
			
			if (occupantDao.getOccupants(eircodeTo).size() == 0) {
				return "A household with this eircode does not exist in the database\n";
				
			} else {
				String eircodeFrom = occupantDao.getOccupant(name).getEircode();
		
				if (occupantDao.moveOccupant(name, eircodeTo, eircodeFrom)) {
					return "Successfully moved " + name + " from eircode " + eircodeFrom + " to eircode " + eircodeTo + "\n";
					
				} else {
					return "Failed to move occupant\n";
				}
			}
		}
	}
	
	//TASK 5 - BUSINESS LOGIC
	public String confirmDeleteHousehold(String eircode) {
		
		if (!householdDao.deleteHousehold(eircode)) {
			return "Household deleted\n";
		} else {
			return "Failed to delete household\n";
		}
	}
	
	//TASK 6 - BUSINESS LOGIC
	public String confirmDeleteOccupant(String name) {
		
		/*
		 * Checks if an occupant with the given name exists in the database first.
		 * If an occupant is deleted and it is the only/last remaining occupant in 
		 * the household, the household is also deleted
		 */
		
		String eircode = "";
		
		if (occupantDao.checkOccupantExists(name)) {
			eircode = occupantDao.getOccupant(name).getEircode();
		}
		
		if (!occupantDao.checkOccupantExists(name)) {
			return "An occupant with the name " + name + " does not exist in the database\n";
		}
		
		else if (occupantDao.deleteOccupant(name)) {
			
			String successMsg = "Successfully deleted occupant\n";
			
			int count = occupantDao.getOccupants(eircode).size();
			
			if (count == 0) {
				householdDao.deleteHousehold(eircode);
				
				successMsg = successMsg.concat("Household also deleted as no occupants remain\n");
			}
			
			return successMsg;
			
		} else {
			return "Failed to delete occupant\n";
		}
	}

	//TASK 7 - BUSINESS LOGIC
	public String getStatistics() {
		
		return "Average age of householders: " + occupantDao.getOccupantsAgeAvg() + "\n"
				+ "Number of students in households: " + occupantDao.getCountStudents() + "\n"
				+ "Number of OAPs in households: " + occupantDao.getCountOAPs() + "\n";
	}
}
