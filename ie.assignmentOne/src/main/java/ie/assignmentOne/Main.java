package ie.assignmentOne;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ie.assignmentOne.classes.Household;
import ie.assignmentOne.classes.Occupant;
import ie.assignmentOne.service.IHouseholdService;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		
		//Beans
		IHouseholdService householdService = (IHouseholdService) context.getBean("householdService");
		
		//Variables and objects
		boolean quit = false;
		int numberOfOccupants;
		String eircode;
		String name;
		Household household = new Household();
		List<Occupant> occupants = new ArrayList<Occupant>();
		
		while (quit != true) {
			
			//Menu printing to console
			System.out.println("****** HOUSEHOLD SEARCH PROGRAM ******\n"
							 + "Press 1 to search for a household by Eircode and view its occupants.\n"
							 + "Press 2 to add a new household along with its occupants.\n"
							 + "Press 3 to add a new occupant to a household.\n"
							 + "Press 4 to move an occupant from one household to another.\n"
							 + "Press 5 to delete a household and its occupants.\n"
							 + "Press 6 to delete an occupant.\n"
							 + "Press 7 to display some statistics.\n"
							 + "Press 8 to quit the program\n");
			
			Scanner input = new Scanner(System.in);	
			boolean isNumericTask = false;
			
			//Checks if input is a numeric value
			while (!isNumericTask) {
				try {
					System.out.println("Enter your selection: ");
					int entry = input.nextInt(); 
					input.nextLine();
					isNumericTask = true;
				
				//Checks if number input is a valid task choice, if not restarts program
				if (entry >= 1 && entry <= 8 ) {
					
					//Switch statement for accessing each of the available menu options
					switch(entry) {
					
						//TASK 1 - SEARCH FOR A HOUSEHOLD BY ITS EIRCODE
						case 1:
							System.out.println("Please enter the Eircode for the household you would like to view the occupants of:");
							eircode = input.nextLine().toUpperCase();
							System.out.println(householdService.confirmGetOccupants(eircode) + "\n");
							break;
						
						//TASK 2 - ADD A NEW HOUSEHOLD ALONG WITH ITS OCCUPANTS
						case 2:
							System.out.println("Eircode: ");
							household.setEircode(input.nextLine().toUpperCase());
							System.out.println("Address: ");
							household.setAddress(input.nextLine());
							
							boolean isNumericOcc = false;
							
							//Checks if input is a numeric value
							while (!isNumericOcc) {
								try {
									System.out.println("Number of occupants: ");
									numberOfOccupants = input.nextInt(); 
									input.nextLine();
									isNumericOcc = true;
							
									//Calls occupantInput method i times for each occupant being added to household
									for (int i = 0; i < numberOfOccupants; i++) {
										System.out.println("Occupant no." + (i + 1));
										Occupant occupant = occupantInput(input, household.getEircode());
										occupants.add(occupant);
									}	
								
								//Exception hander to catch if non-numeric value has been entered
								} catch (InputMismatchException ime) {
									System.out.println("Please enter numeric values only");
									input.nextLine();
								}
							}
										
							System.out.println(householdService.confirmAddHousehold(household, occupants));
							break;
						
						//TASK 3 - ADD A NEW PERSON TO A HOUSEHOLD
						case 3:
							System.out.println("Please enter the household you would like to add an occupant to:");
							eircode = input.nextLine().toUpperCase();
							Occupant occupant = occupantInput(input, eircode);
							System.out.println(householdService.confirmAddOccupants(occupant));
							break;
						
						//TASK 4 - MOVE A PERSON FROM ONE HOUSEHOLD TO ANOTHER
						case 4:
							System.out.println("Please enter the name of the occupant you would like to move:");
							name = input.nextLine();
							System.out.println("New Eircode: ");
							String eircodeTo = input.nextLine().toUpperCase();
							System.out.println(householdService.confirmMoveOccupant(name, eircodeTo));
							break;
						
						//TASK 5 - DELETE A HOUSEHOLD AND ITS OCCUPANTS
						case 5:
							System.out.println("Please enter the Eircode of the household you would like to delete: ");
							eircode = input.nextLine().toUpperCase();
							System.out.println(householdService.confirmDeleteHousehold(eircode));
							break;
						
						//TASK 6 - DELETE AN OCCUPANT FROM THE DATABASE
						case 6:
							System.out.println("Please enter the name occupant to delete:");
							name = input.nextLine();
							System.out.println(householdService.confirmDeleteOccupant(name));
							break;
						
						//TASK 7 - DISPLAY SOME STATISTICS
						case 7:
							System.out.println("Below are some statistics:");
							System.out.println(householdService.getStatistics());
							break;
						
						//QUIT PROGRAM
						case 8:
							System.out.print("Quitting Program");
							quit = true;
						}
					
					//Message if invalid task choice is selected
					} else {
						System.out.println("Invalid Selection");
					}
			
				//Exception hander to catch if non-numeric value has been entered
				} catch (InputMismatchException ime) {
					System.out.println("Please enter numeric values only");
					input.nextLine();
				}
			}
		}
	}
	
	//Method for taking in the details of a new occupant being added to the database
	public static Occupant occupantInput(Scanner input, String eircode) {
		Occupant occupant = new Occupant();
		System.out.println("Name:");
		occupant.setName(input.nextLine());
		boolean isNumericAge = false;
		
		//Checks if input is a numeric value
		while (!isNumericAge) {
			try {
				System.out.println("Age:");
				occupant.setAge(input.nextInt()); 
				input.nextLine();
				isNumericAge= true;
				
			//Exception hander to catch if non-numeric value has been entered
			} catch (InputMismatchException ime) {
				System.out.println("Please enter numeric values only");
				input.nextLine();
			}
		}
		
		System.out.println("Occupation:");
		occupant.setOccupation(input.nextLine());
		occupant.setEircode(eircode);
		
		return occupant;
	}

}
 
