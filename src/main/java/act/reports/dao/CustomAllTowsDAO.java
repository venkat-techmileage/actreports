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
import act.reports.model.CustomAllTows;
import act.reports.model.CustomAllTowsDetails;
import act.reports.model.SearchCriteria;
import act.reports.util.DateUtility;

@Repository("customAllTowsDAO")
public class CustomAllTowsDAO {

	private Logger logger=Logger.getLogger(CustomAllTowsDAO.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public CustomAllTows getCustomAllTows(SearchCriteria criteria){
		logger.info("In CustomAllTowsDAO-getCustomAllTows()...");
		List<CustomAllTowsDetails> customAllTowsDetailsList = null;
		CustomAllTows customAllTows = new CustomAllTows();

		try{
			final String accountName = criteria.getAccountName();
			String fromDate=criteria.getFromDate();
			String toDate=criteria.getToDate();
			fromDate = DateUtility.convertAsMySqlDateTime(fromDate);
			logger.info("fromDate in CustomAllTowsDAO-getCustomAllTows() : "+fromDate);
			toDate = DateUtility.convertAsMySqlDateTime(toDate);
			toDate = toDate.replace("00:00:00", "23:59:59");
			logger.info("toDate in CustomAllTowsDAO-getCustomAllTows() : "+toDate);
			logger.info("accountName in CustomAllTowsDAO-getCustomAllTows() : "+accountName);

			String customAllTowsQuery = "select aa.vendorId,aa.contract,sc.callCreatedTime,i.invoiceId,i.arrivedTime,i.dropOffLocation,i.towType,iv.vehicle_Year,iv.vehicle_Make,iv.vehicle_Model,iv.vehicle_VIN " +
										"from Account a,Invoice i LEFT OUTER JOIN Account_address aa ON i.accountId=aa.idAccount LEFT OUTER JOIN ServiceCallInfo sc ON sc.serviceCallId=i.serviceCallId LEFT OUTER JOIN " +
										"Invoice_Vehicle iv ON i.invoiceId=iv.invoiceId where a.name='"+accountName+"' and i.invoiceCreatedDate BETWEEN '"+fromDate+"' and '"+toDate+"' and a.idAccount=i.accountId";

			customAllTowsDetailsList = jdbcTemplate.query(customAllTowsQuery, new Object[] {}, new RowMapper<CustomAllTowsDetails>() {

				public CustomAllTowsDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

					CustomAllTowsDetails customAllTowsDetails = new CustomAllTowsDetails();					
					customAllTowsDetails.setId((rs.getString("vendorId") != null) ? rs.getString("vendorId") : " ");
					customAllTowsDetails.setContract((rs.getString("contract") != null) ? rs.getString("contract") : " ");
					customAllTowsDetails.setStorageReport((rs.getString("invoiceId") != null) ? rs.getString("invoiceId") : " ");
					
					String callCreatedDateTime = (rs.getString("callCreatedTime") != null) ? rs.getString("callCreatedTime") : "";
					String pdRequestDate = " ";
					String pdRequestTime = " ";
					
					if(!callCreatedDateTime.trim().isEmpty()){
						callCreatedDateTime=DateUtility.convertToDateTimeFormat(callCreatedDateTime);
						pdRequestDate = callCreatedDateTime.substring(0, 10);
						pdRequestTime = callCreatedDateTime.substring(11, 16);
					}
					customAllTowsDetails.setPdRequestDate(pdRequestDate);
					customAllTowsDetails.setPdRequestTime(pdRequestTime);
					
					String arrivedDateTime = (rs.getString("arrivedTime") != null) ? rs.getString("arrivedTime") : "";
					String towArrivedDate = " ";
					String towArrivedTime = " ";
					
					if(!arrivedDateTime.trim().isEmpty()){
						arrivedDateTime=DateUtility.convertToDateTimeFormat(arrivedDateTime);
						towArrivedDate = arrivedDateTime.substring(0, 10);
						towArrivedTime = arrivedDateTime.substring(11, 16);
					}
					customAllTowsDetails.setTowArrivalDate(towArrivedDate);
					customAllTowsDetails.setTowArrivalTime(towArrivedTime);
					customAllTowsDetails.setYear((rs.getString("vehicle_Year") != null) ? rs.getString("vehicle_Year") : " ");
					customAllTowsDetails.setMake((rs.getString("vehicle_Make") != null) ? rs.getString("vehicle_Make") : " ");
					customAllTowsDetails.setModel((rs.getString("vehicle_Model") != null) ? rs.getString("vehicle_Model") : " ");
					customAllTowsDetails.setVin((rs.getString("vehicle_VIN") != null) ? rs.getString("vehicle_VIN") : "");
					
					String precinct = "";
					if(!accountName.isEmpty())
						precinct = accountName.replaceAll("[a-zA-Z-'.@]", "");					
					customAllTowsDetails.setPrecinct(precinct);
					
					customAllTowsDetails.setTowedTo((rs.getString("dropOffLocation") != null) ? rs.getString("dropOffLocation") : " ");
					customAllTowsDetails.setTowedType((rs.getString("towType") != null) ? rs.getString("towType") : " ");


					return customAllTowsDetails;
				}
			});
			customAllTows.setCustomAllTowsDetails(customAllTowsDetailsList);	

		}catch (Exception e) {
			logger.error(e);
			e.printStackTrace();

		}		
		return customAllTows;
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

