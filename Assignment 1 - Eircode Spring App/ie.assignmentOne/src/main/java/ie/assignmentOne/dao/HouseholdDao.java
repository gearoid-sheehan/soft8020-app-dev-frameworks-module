package ie.assignmentOne.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ie.assignmentOne.classes.Household;

//Annotates the class so it becomes a bean and can be scanned by context component scan in beans.xml
@Repository
public class HouseholdDao implements IHouseholdDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	//Query to get a household by its eircode
	public Household getHousehold(String eircode) {
		final String sql = "SELECT FROM households WHERE eircode = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] {eircode}, new HouseholdMapper());
	}
	
	// Query to add a household to the database
	public int addHousehold(final Household household) {
		final String sql = "INSERT INTO households(eircode, address) VALUES (?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
			new PreparedStatementCreator()
			{
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException
				{
					PreparedStatement ps = con.prepareStatement(sql, new String[] {"householdId"});
		
				    ps.setString(1, household.getEircode());
				    ps.setString(2, household.getAddress());
					    
					return ps;
				} 
			}, keyHolder);
			
		return keyHolder.getKey().intValue();
	}
	
	// Query to the get the count of all households in the database
	public int getHouseHoldCount() {
		final String sql = "SELECT COUNT(*) FROM households";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
	//Query to delete a household by passing its eircode from the database
	public Boolean deleteHousehold(String eircode) {
		final String sql = "DELETE FROM occupants WHERE occ_eircode = ?";
		return jdbcTemplate.update(sql, eircode) == 1;
	}
	
	//Query to check if a household exists in the database
	public boolean checkHouseholdExists(String eircode) {
		final String sql = "SELECT COUNT(1) FROM households WHERE eircode = ?";
		return 1 == jdbcTemplate.queryForObject(sql, new Object[] {eircode}, Integer.class);
	}

	//Rowmapper method for the household objects returning from the database
	private static final class HouseholdMapper implements RowMapper<Household> {

		public Household mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			Household household = new Household();
			household.setEircode(rs.getString("eircode"));
			household.setAddress(rs.getString("address"));
			
			return household;
		}
	}
}
