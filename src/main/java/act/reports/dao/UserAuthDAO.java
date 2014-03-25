package act.reports.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import act.reports.model.UserDetail;


@Repository("userDao")
public class UserAuthDAO
{
	
    private Logger logger=Logger.getLogger(UserAuthDAO.class);
    
   
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public UserDetail getUserDetails(String userId)
	{
		
		
		//String query="select userId,password,corporation,company,firstName,lastName,role,saltKey from userdetails where userId=?";
		String query="select e.idEmployee,ua.userId,ua.encyPassword,e.isDriver,e.firstName,e.lastName,e.level,uak.userSaltKey from UserAuth ua inner join" + 
				" UserAuthKey uak on uak.UserAuth_userId=ua.userId inner join Employee e on uak.UserAuth_Employee_idEmployee=e.idEmployee" + 
				" where ua.userId=?";
		logger.info(query+userId);
		logger.info(jdbcTemplate);
		UserDetail userDetails=new UserDetail();
		try
		{
			userDetails=jdbcTemplate.queryForObject(query, new Object[] {userId}, new RowMapper<UserDetail>(){
		
			public UserDetail mapRow(ResultSet rs, int rowNum) throws SQLException {

			UserDetail ud=new UserDetail();
			
			ud.setEmployeeId(rs.getInt("idEmployee"));
			ud.setUserId(rs.getString("userId"));
			ud.setPassword(rs.getBytes("encyPassword"));
			ud.setFirstName(rs.getString("firstName"));
			ud.setLastName(rs.getString("lastName"));
			ud.setRole(rs.getString("level"));
			ud.setSaltKey(rs.getBytes("userSaltKey"));
			
			
			return ud;
		}
		
	});
			
		}
		catch (Exception e) {
			logger.error(e);
		}
		return userDetails;
	}
	
	

}
