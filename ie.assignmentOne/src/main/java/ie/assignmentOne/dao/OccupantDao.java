package ie.assignmentOne.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ie.assignmentOne.classes.Occupant;

//Annotates the class so it becomes a bean and can be scanned by context component scan in beans.xml
@Repository
public class OccupantDao implements IOccupantDao{

	@Autowired
	JdbcTemplate jdbcTemplate;

	//Query to return list of all occupants in a certain eircode
	public List<Occupant> getOccupants(String eircode) {
	    String sql = "SELECT * FROM occupants WHERE occ_eircode = ?";
		return jdbcTemplate.query(sql, new Object[] {eircode}, new OccupantMapper());
	}

	//Query to add occupant to occupants table, uses prepared statement
	public int addOccupant(final Occupant occupant) {
		
		final String INSERT_SQL = "INSERT INTO occupants (name, age, occupation, occ_eircode) VALUES (?, ?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(
			new PreparedStatementCreator()
			{
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException
				{
					PreparedStatement ps = con.prepareStatement(INSERT_SQL, new String[] {"occupantId"});
			
				    ps.setString(1, occupant.getName());
				    ps.setInt(2, occupant.getAge());
				    ps.setString(3, occupant.getOccupation());
				    ps.setString(4, occupant.getEircode());
				
					return ps;
				}
			}, keyHolder);
			
		return keyHolder.getKey().intValue();
	}

	//Query to move an occupant from one eircode to another
	public boolean moveOccupant(String name, String eircodeTo, String eircodeFrom) {
		
		String sql = "UPDATE occupants SET occ_eircode = ? WHERE name = ? AND occ_eircode = ?";
		return jdbcTemplate.update(sql, eircodeTo, name, eircodeFrom) == 1;
	}
	
	//Query to delete a household and all its occupants from the database
	public boolean deleteHousehold(String eircode) {
		
	    String sql = "DELETE occupants FROM occupants LEFT JOIN households ON occupants.occ_eircode = households.eircode WHERE eircode = ?";
	    return jdbcTemplate.update(sql, eircode) == 1;
	}
	
	//Query to delete a single occupant from the database
	public boolean deleteOccupant(String name) {
		
	    String sql = "DELETE FROM occupants WHERE name = ?";
	    return jdbcTemplate.update(sql, name) == 1;
	}
	
	//TASK 7 QUERIES
	
	//Query to get average age of all occupants in the database
	public int getOccupantsAgeAvg() {
	    String sql = "SELECT AVG(age) FROM occupants";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
	//Query to get count of all students in the database
	public int getCountStudents() {
	    String sql = "SELECT COUNT(occupation) AS total FROM occupants WHERE occupation = 'Student'";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
	//Query to get count of all OAPs in the database
	public int getCountOAPs() {
	    String sql = "SELECT COUNT(age) AS total FROM occupants WHERE age > 64";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
				
	//OTHER QUERIES
	
	//Query to check if occupant exists first before allowing others to be called
	public boolean checkOccupantExists(String name) {
		return 1 == jdbcTemplate.queryForObject("SELECT COUNT(1) FROM occupants WHERE name = ?", new Object[] {name}, Integer.class);
	}
	
	//Query to get an occupant by name
	public Occupant getOccupant(String name) {
		String sql = "SELECT * FROM occupants WHERE name = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] {name}, new OccupantMapper());
	}
	
	//Query to get all occupants in the database
	public List<Occupant> getAllOccupants() {
		String sql = "SELECT * FROM occupants";
		return jdbcTemplate.query(sql, new OccupantMapper());
	}
	
	//Rowmapper method for the occupant objects returning from the database
	private static final class OccupantMapper implements RowMapper<Occupant> {

		public Occupant mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			Occupant occupant = new Occupant();
			occupant.setOccupantId(rs.getInt("occupantId"));
			occupant.setName(rs.getString("name"));
			occupant.setAge(rs.getInt("age"));
			occupant.setOccupation(rs.getString("occupation"));
			occupant.setEircode(rs.getString("occ_eircode"));
			
			return occupant;
		}
	}

}
