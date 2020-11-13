package ie.assignmentOne.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ie.assignmentOne.classes.Occupant;
import ie.assignmentOne.dao.HouseholdDao;
import ie.assignmentOne.dao.OccupantDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:beans.xml")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JUnit_Tests {

	@Autowired
	HouseholdDao householdDao;
	@Autowired
	OccupantDao occupantDao;
	
	@Test
	@Order(1)
	public void testAverageAgeQuery() {
		/*
		 * Tests if the SQL query to return average age of occupants is returning
		 * correct value when compared to doing the calculation in java
		 */
		
		List<Occupant> occupants = occupantDao.getAllOccupants();
		
		int total = 0;
		
		for (Occupant occupant: occupants) {
			total += occupant.getAge();
		}
		
		int avg = total/occupants.size();
				
		assertEquals(avg, occupantDao.getOccupantsAgeAvg());
	}
	
	@Test
	@Order(2)
	public void testDeleteOccupant() {
		
		/*
		 * Tests if an occupant is successfully deleted by comparing the
		 * before and after amount of occupants in the database 
		 */
		
		int countBeforeDelete = occupantDao.getAllOccupants().size();
		
		occupantDao.deleteOccupant("Gearoid Sheehan");
		
		int countAfterDelete = occupantDao.getAllOccupants().size();
		
		assertEquals((countBeforeDelete-1), countAfterDelete);
	}
	
	@Test
	@Order(3)
	public void testAddOccupant() {
		
		/*
		 * Tests if an occupant is successfully added by adding a new occupant
		 * to the database, then retrieving an occupant with the same name
		 * from the database
		 */
		
		Occupant occupant = new Occupant();
		occupant.setName("Megan Cummins");
		occupant.setAge(23);
		occupant.setEircode("T12P3Y0");
		occupant.setOccupation("Nurse");
		
		occupantDao.addOccupant(occupant);
		
		String name = occupantDao.getOccupant("Megan Cummins").getName();
		
		assertEquals(name, occupant.getName());
	}
	
	@Test
	@Order(3)
	public void testCountOAP() {
		
		/*
		 * Test if the counting of old age pensioners works. Counts the number
		 * of old age pensioners, adds an occupant who qualifies as an old age
		 * pentioner to the database, and then does a new count. If the second
		 * count is larger as it should be, the test succeeds
		 */
		
		int countOAP = occupantDao.getCountOAPs();
		
		Occupant occupant = new Occupant();
		occupant.setName("Diarmuid McQuinn");
		occupant.setAge(72);
		occupant.setEircode("T12P3Y0");
		occupant.setOccupation("Retired");
		
		occupantDao.addOccupant(occupant);
		
		int countOAPAfter = occupantDao.getCountOAPs();
		
		assertTrue(countOAP < countOAPAfter);
	}
}
