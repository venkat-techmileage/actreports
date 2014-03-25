package act.reports.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import act.reports.model.AccountNames;
import act.reports.model.ResponseTimeDetails;
import act.reports.model.SearchCriteria;
import act.reports.model.UserIds;
import act.reports.model.UserNames;
import act.reports.util.DateUtility;

@Repository("responseTimeDAO")
public class ResponseTimeDAO {

	private Logger logger=Logger.getLogger(ResponseTimeDAO.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<ResponseTimeDetails> getResponseTimeDetails(SearchCriteria criteria){
		logger.info("In ResponseTimeDAO-getResponseTimeDetails()...");
		List<ResponseTimeDetails> responseTimeDetailsList = null;
		try {
			String fromDate=criteria.getFromDate();
			String toDate=criteria.getToDate();
			fromDate = DateUtility.convertAsMySqlDateTime(fromDate);
			logger.info("fromDate in ResponseTimeDAO-getResponseTimeDetails() : "+fromDate);
			toDate = DateUtility.convertAsMySqlDateTime(toDate);
			toDate = toDate.replace("00:00:00", "23:59:59");
			logger.info("toDate in ResponseTimeDAO-getResponseTimeDetails() : "+toDate);
			final String searchString = criteria.getSearchString();
			logger.info("searchString : "+searchString);
			
			String userId = "";
			String userName = "";
			String accountName = "";
			String responseTimeQry = "";
			final int index = searchString.indexOf("-");
			logger.info("index = "+index);
			
			if(searchString.substring(0, index).equalsIgnoreCase("All"))
			{
				if(searchString.substring(index+1).equalsIgnoreCase("All")){
					responseTimeQry = "select distinct a.name,i.invoiceId,i.dispatchTime,i.assignedBy,i.arrivedTime,i.clearedTime,sc.callRecevierId,sc.callCreatedTime,u.userId " +
									  "from Account a,Invoice i,ServiceCallInfo sc,UserAuth u where i.invoiceCreatedDate BETWEEN '"+fromDate+"' and '"+toDate+"' and " +
									  "a.idAccount=i.accountId and a.idAccount=sc.accountId and sc.serviceCallId=i.serviceCallId and u.Employee_idEmployee=i.driverId";								
				}
				else if(searchString.substring(index+1).equalsIgnoreCase("UserName")){
					userName = criteria.getUserName();
					int index1 = userName.indexOf(" ");
					String fname = userName.substring(0, index1);
					String lname = userName.substring(index1+1);
					logger.info("userName : "+userName); 
					responseTimeQry = "select distinct a.name,i.invoiceId,i.dispatchTime,i.assignedBy,i.arrivedTime,i.clearedTime,sc.callRecevierId,sc.callCreatedTime,u.userId from Account a,Invoice i," +
									  "ServiceCallInfo sc,UserAuth u,Employee e where i.invoiceCreatedDate BETWEEN '"+fromDate+"' and '"+toDate+"' and e.firstName='"+fname+"' and e.lastName='"+lname+"' " +
									  "and a.idAccount=i.accountId and a.idAccount=sc.accountId and sc.serviceCallId=i.serviceCallId and i.driverId=e.idEmployee and e.idEmployee=u.Employee_idEmployee";	
				}
				else if(searchString.substring(index+1).equalsIgnoreCase("UserID")){
					userId = criteria.getUserId();
					logger.info("userId : "+userId); 
					responseTimeQry = "select distinct a.name,i.invoiceId,i.dispatchTime,i.assignedBy,i.arrivedTime,i.clearedTime,sc.callRecevierId,sc.callCreatedTime,u.userId from Account a," +
									  "Invoice i,ServiceCallInfo sc,UserAuth u where i.invoiceCreatedDate BETWEEN '"+fromDate+"' and '"+toDate+"' and u.userId='"+userId+"' and " +
									  "a.idAccount=i.accountId and a.idAccount=sc.accountId and sc.serviceCallId=i.serviceCallId and i.driverId=u.Employee_idEmployee";
				}
				else if(searchString.substring(index+1).equalsIgnoreCase("Account")){
					accountName = criteria.getAccountName();
					logger.info("accountName : "+accountName); 
					responseTimeQry = "select distinct a.name,i.invoiceId,i.dispatchTime,i.assignedBy,i.arrivedTime,i.clearedTime,sc.callRecevierId,sc.callCreatedTime,u.userId from Account a," +
									  "Invoice i,ServiceCallInfo sc,UserAuth u where i.invoiceCreatedDate BETWEEN '"+fromDate+"' and '"+toDate+"' and a.name='"+accountName+"' and " +
									  "a.idAccount=i.accountId and a.idAccount=sc.accountId and sc.serviceCallId=i.serviceCallId and i.driverId=u.Employee_idEmployee";
				}
			}
			else if(searchString.substring(0, index).equalsIgnoreCase("LateOnly"))
			{
				if(searchString.substring(index+1).equalsIgnoreCase("All")){
					responseTimeQry = "select distinct a.name,i.invoiceId,i.dispatchTime,i.assignedBy,i.arrivedTime,i.clearedTime,sc.callRecevierId,sc.callCreatedTime,u.userId from Account a,Invoice i,ServiceCallInfo sc," +
									  "UserAuth u where i.invoiceCreatedDate BETWEEN '"+fromDate+"' and '"+toDate+"' and a.maxArrivalTime<(SELECT TIMESTAMPDIFF(MINUTE,sc.callCreatedTime,i.arrivedTime)) " +
									  "and a.idAccount=i.accountId and a.idAccount=sc.accountId and sc.serviceCallId=i.serviceCallId and i.driverId=u.Employee_idEmployee";
				}
				else if(searchString.substring(index+1).equalsIgnoreCase("UserName")){
					userName = criteria.getUserName();
					int index1 = userName.indexOf(" ");
					String fname = userName.substring(0, index1);
					String lname = userName.substring(index1+1);
					logger.info("userName : "+userName);  
					responseTimeQry = "select distinct a.name,i.invoiceId,i.dispatchTime,i.assignedBy,i.arrivedTime,i.clearedTime,sc.callRecevierId,sc.callCreatedTime,u.userId from Account a,Invoice i,ServiceCallInfo sc,UserAuth u,Employee e " +
							  		  "where i.invoiceCreatedDate BETWEEN '"+fromDate+"' and '"+toDate+"' and e.firstName='"+fname+"' and e.lastName='"+lname+"' and a.maxArrivalTime<(SELECT TIMESTAMPDIFF(MINUTE,sc.callCreatedTime,i.arrivedTime)) " +
							  		  "and a.idAccount=i.accountId and a.idAccount=sc.accountId and sc.serviceCallId=i.serviceCallId and i.driverId=u.Employee_idEmployee and e.idEmployee=u.Employee_idEmployee";
				}
				else if(searchString.substring(index+1).equalsIgnoreCase("UserID")){
					userId = criteria.getUserId();
					logger.info("userId : "+userId); 
					responseTimeQry = "select distinct a.name,i.invoiceId,i.dispatchTime,i.assignedBy,i.arrivedTime,i.clearedTime,sc.callRecevierId,sc.callCreatedTime,u.userId from Account a,Invoice i,ServiceCallInfo sc,UserAuth u " +
									  "where i.invoiceCreatedDate BETWEEN '"+fromDate+"' and '"+toDate+"' and u.userId='"+userId+"' and a.maxArrivalTime<(SELECT TIMESTAMPDIFF(MINUTE,sc.callCreatedTime,i.arrivedTime)) " + 
									  "and a.idAccount=i.accountId and a.idAccount=sc.accountId and sc.serviceCallId=i.serviceCallId and i.driverId=u.Employee_idEmployee";
				}
				else if(searchString.substring(index+1).equalsIgnoreCase("Account")){
					accountName = criteria.getAccountName();
					logger.info("accountName : "+accountName); 
					responseTimeQry = "select distinct a.name,i.invoiceId,i.dispatchTime,i.assignedBy,i.arrivedTime,i.clearedTime,sc.callRecevierId,sc.callCreatedTime,u.userId from Account a,Invoice i,ServiceCallInfo sc,UserAuth u " +
									  "where i.invoiceCreatedDate BETWEEN '"+fromDate+"' and '"+toDate+"' and a.name='"+accountName+"' and a.maxArrivalTime<(SELECT TIMESTAMPDIFF(MINUTE,sc.callCreatedTime,i.arrivedTime)) " +
									  "and a.idAccount=i.accountId and a.idAccount=sc.accountId and sc.serviceCallId=i.serviceCallId and i.driverId=u.Employee_idEmployee";
				}
			}
							
			responseTimeDetailsList = jdbcTemplate.query(responseTimeQry, new Object[] {}, new RowMapper<ResponseTimeDetails>() {

				public ResponseTimeDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

					ResponseTimeDetails responseTimeDetails = new ResponseTimeDetails();					
					responseTimeDetails.setAccount((rs.getString("name") != null) ? rs.getString("name") : " ");
					responseTimeDetails.setInvoiceId((rs.getString("invoiceId") != null) ? rs.getString("invoiceId") : " ");
					responseTimeDetails.setDriverId((rs.getString("userId") != null) ? rs.getString("userId") : " ");
					
					String dispatchedTime = (rs.getString("dispatchTime") != null) ? rs.getString("dispatchTime") : " ";
					logger.info("dispatchedTime : "+dispatchedTime);
					if(!dispatchedTime.trim().isEmpty())
						responseTimeDetails.setDispatchedTime(dispatchedTime.substring(11, 16));
					else
						responseTimeDetails.setDispatchedTime(dispatchedTime);
					
					String arrivalTime = (rs.getString("arrivedTime") != null) ? rs.getString("arrivedTime") : " ";
					logger.info("arrivalTime : "+arrivalTime);
					if(!arrivalTime.trim().isEmpty())
						responseTimeDetails.setArrivalTime(arrivalTime.substring(11, 16));
					else
						responseTimeDetails.setArrivalTime(arrivalTime);
					
					String clearedTime = (rs.getString("clearedTime") != null) ? rs.getString("clearedTime") : " ";
					logger.info("clearedTime : "+clearedTime);
					if(!clearedTime.trim().isEmpty())
						responseTimeDetails.setClearedTime(clearedTime.substring(11, 16));
					else
						responseTimeDetails.setClearedTime(clearedTime);
					
					responseTimeDetails.setCallerTakerId((rs.getString("callRecevierId") != null) ? rs.getString("callRecevierId") : " ");
					
					String callTime = (rs.getString("callCreatedTime") != null) ? rs.getString("callCreatedTime") : " ";
					logger.info("callTime : "+callTime);
					if(!callTime.trim().isEmpty())
						responseTimeDetails.setCallTime(callTime.substring(11, 16));
					else
						responseTimeDetails.setCallTime(callTime);
					
					responseTimeDetails.setDispatchId((rs.getString("assignedBy") != null) ? rs.getString("assignedBy") : " ");	
					
					String callToDispatch = " ";
					if(!callTime.trim().isEmpty() && !dispatchedTime.trim().isEmpty()){
						String callToDispatchQry = "SELECT TIMESTAMPDIFF(MINUTE,'"+callTime+"','"+dispatchedTime+"')";
						callToDispatch = jdbcTemplate.queryForObject(callToDispatchQry, String.class);
						responseTimeDetails.setCallToDispatch(callToDispatch);
					}
					logger.info("callToDispatch : "+callToDispatch);
					
					String callToArrival = " ";
					if(!callTime.trim().isEmpty() && !arrivalTime.trim().isEmpty()){
						String callToArrivalQry = "SELECT TIMESTAMPDIFF(MINUTE,'"+callTime+"','"+arrivalTime+"')";
						callToArrival = jdbcTemplate.queryForObject(callToArrivalQry, String.class);
						responseTimeDetails.setCallToArrival(callToArrival);
					}
					logger.info("callToArrival : "+callToArrival);
					
					String dispatchToClear = " ";
					if(!dispatchedTime.trim().isEmpty() && !clearedTime.trim().isEmpty()){
						String dispatchToClearQry = "SELECT TIMESTAMPDIFF(MINUTE,'"+dispatchedTime+"','"+clearedTime+"')";
						dispatchToClear = jdbcTemplate.queryForObject(dispatchToClearQry, String.class);
						responseTimeDetails.setDispatchToClear(dispatchToClear);
					}
					logger.info("dispatchToClear : "+dispatchToClear);
					
					if(searchString.substring(0, index).equalsIgnoreCase("LateOnly")){
						responseTimeDetails.setLate("Y");						
					}
					else if(searchString.substring(0, index).equalsIgnoreCase("All")){
						String invoiceId = responseTimeDetails.getInvoiceId();
						if(!invoiceId.trim().isEmpty()){
							String qry = "select count(i.invoiceId) count from Account a,Invoice i where i.invoiceId='"+invoiceId+"' and a.maxArrivalTime<'"+callToArrival+"' and a.idAccount=i.accountId";
							int count = jdbcTemplate.queryForObject(qry, Integer.class);
							if(count > 0)
								responseTimeDetails.setLate("Y");
							else
								responseTimeDetails.setLate("N");
						}												
					}
					
					return responseTimeDetails;
				}		
				
			});		
			
			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
		return responseTimeDetailsList;
	}

	public List<UserNames> getUserNames()
	{
		logger.info("In getUserNames()...");
		String userNamesQry = "select firstName,lastName from Employee";		  
		List<UserNames> userNamesList=null;
		try
		{
			userNamesList =jdbcTemplate.query(userNamesQry, new Object[] {}, new RowMapper<UserNames>() {			
		      public UserNames mapRow(ResultSet rs, int rowNum) throws SQLException {		     
		    	  UserNames userNames = new UserNames();		    	
		    	  String firstName = rs.getString("firstName")!=null?rs.getString("firstName"):"";
					String lastName = rs.getString("lastName")!=null?rs.getString("lastName"):"";
					userNames.setUserName(firstName+" "+lastName);		    	  		    	
		    	return userNames;
		      }
		    });
	     }
		 catch (Exception e) {
		   logger.error(e);
		  }
		  logger.info("userNamesList.size() : "+userNamesList.size());
		  return userNamesList;
	}
	
	public List<UserIds> getUserIds()
	{
		logger.info("In getUserIds()...");
		String userIdsQry = "select userId from UserAuth";		  
		List<UserIds> userIdsList=null;
		try
		{
			userIdsList =jdbcTemplate.query(userIdsQry, new Object[] {}, new RowMapper<UserIds>() {			
		      public UserIds mapRow(ResultSet rs, int rowNum) throws SQLException {		     
		    	  UserIds userIds = new UserIds();		    	
		    	  userIds.setUserId(rs.getString("userId")!=null?rs.getString("userId"):"");		    	  		    	
		    	return userIds;
		      }
		    });
	     }
		 catch (Exception e) {
		   logger.error(e);
		  }
		  logger.info("userIdsList.size() : "+userIdsList.size());
		  return userIdsList;
	}
	
	public List<AccountNames> getAccountNames()
	{
		logger.info("In getAccountNames()...");
		String accountNamesQry = "select name from Account";		  
		List<AccountNames> accountNamesList=null;
		try
		{
			accountNamesList =jdbcTemplate.query(accountNamesQry, new Object[] {}, new RowMapper<AccountNames>() {			
		      public AccountNames mapRow(ResultSet rs, int rowNum) throws SQLException {		     
		    	  AccountNames accountNames = new AccountNames();		    	
		    	  accountNames.setAccountName(rs.getString("name")!=null?rs.getString("name"):"");		    	  		    	
		    	return accountNames;
		      }
		    });
	     }
		 catch (Exception e) {
		   logger.error(e);
		  }
		  logger.info("accountNamesList.size() : "+accountNamesList.size());
		  return accountNamesList;
	}	
}