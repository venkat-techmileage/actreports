package act.reports.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import act.reports.model.AbandonedVehicle;
import act.reports.model.AbandonedVehicleDetails;
import act.reports.model.AccountNames;
import act.reports.model.SearchCriteria;
import act.reports.util.DateUtility;

@Repository("abandonedVehicleDAO")
public class AbandonedVehicleDAO {

	private Logger logger=Logger.getLogger(AbandonedVehicleDAO.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public AbandonedVehicle getAbandonedVehicle(SearchCriteria criteria){
		logger.info("In AbandonedVehicleDAO-getAbandonedVehicle()...");
		List<AbandonedVehicleDetails> abandonedVehicleDetailsList = null;
		AbandonedVehicle abandonedVehicle = new AbandonedVehicle();

		try{
			String accountName = criteria.getAccountName();
			String fromDate=criteria.getFromDate();
			String toDate=criteria.getToDate();
			fromDate = DateUtility.convertAsMySqlDateTime(fromDate);
			logger.info("fromDate in AbandonedVehicleDAO-getAbandonedVehicle() : "+fromDate);
			toDate = DateUtility.convertAsMySqlDateTime(toDate);
			toDate = toDate.replace("00:00:00", "23:59:59");
			logger.info("toDate in AbandonedVehicleDAO-getAbandonedVehicle() : "+toDate);
			logger.info("accountName in AbandonedVehicleDAO-getAbandonedVehicle() : "+accountName);

			/*String abandonedquery = "select sc.callCreatedTime,iv.vehicle_OR_DR_No,iv.vehicle_Plate_No,iv.vehicle_VIN,i.releaseDate from Account a,ServiceCallInfo sc,Invoice i,Invoice_Vehicle iv " +
					"where a.name='"+accountName+"' and i.releaseDate BETWEEN '"+fromDate+"' and '"+toDate+"' and a.idAccount=i.accountId and i.serviceCallId=sc.serviceCallId and i.invoiceId=iv.invoiceId";*/
			
			String abandonedquery = "select sc.callCreatedTime,iv.vehicle_OR_DR_No,iv.vehicle_Plate_No,iv.vehicle_VIN,it.avrDate,it.titleDate,it.invoiceId,ir.releaseDate,ir.invoiceId from Account a,ServiceCallInfo sc," +
									"Invoice i,Invoice_Vehicle iv,Invoice_Release ir LEFT OUTER JOIN Invoice_TitleProcess it ON ir.invoiceId=it.invoiceId where a.name='"+accountName+"' and iv.isImpound='1' " +
									" and ir.releaseDate BETWEEN '"+fromDate+"' and '"+toDate+"' and i.invoiceId=ir.invoiceId and a.idAccount=i.accountId and i.serviceCallId=sc.serviceCallId and i.invoiceId=iv.invoiceId";

			abandonedVehicleDetailsList = jdbcTemplate.query(abandonedquery, new Object[] {}, new RowMapper<AbandonedVehicleDetails>() {

				public AbandonedVehicleDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

					AbandonedVehicleDetails abandonedVehicleDetails = new AbandonedVehicleDetails();					
					
					String callCreatedTime=rs.getString("callCreatedTime") != null ? rs.getString("callCreatedTime") : "";
					if(!callCreatedTime.trim().isEmpty()){
						callCreatedTime=DateUtility.convertToDateTimeFormat(callCreatedTime);
						callCreatedTime = callCreatedTime.substring(0, 10);
					}
					abandonedVehicleDetails.setServiceCallDate(callCreatedTime);
					
					abandonedVehicleDetails.setOrDr((rs.getString("vehicle_OR_DR_No") != null) ? rs.getString("vehicle_OR_DR_No") : "");
					abandonedVehicleDetails.setVin((rs.getString("vehicle_VIN") != null) ? rs.getString("vehicle_VIN") : "");
					abandonedVehicleDetails.setLicensePlate((rs.getString("vehicle_Plate_No") != null) ? rs.getString("vehicle_Plate_No") : "");
					

					String avrDate=rs.getString("avrDate") != null ? rs.getString("avrDate") : "";
					if(!avrDate.trim().isEmpty()){
						avrDate=DateUtility.convertToDateTimeFormat(avrDate);
						avrDate = avrDate.substring(0, 10);
					}
					abandonedVehicleDetails.setTitleRequested(avrDate);
					 
					String titleDate=rs.getString("titleDate") != null ? rs.getString("titleDate") : "";
					if(!titleDate.trim().isEmpty()){
						titleDate=DateUtility.convertToDateTimeFormat(titleDate);
						titleDate = titleDate.substring(0, 10);
					}
					abandonedVehicleDetails.setTitleReceived(titleDate);
					
					String releaseDate=rs.getString("releaseDate") != null ? rs.getString("releaseDate") : "";
					if(!releaseDate.trim().isEmpty()){
						releaseDate=DateUtility.convertToDateTimeFormat(releaseDate);
						releaseDate = releaseDate.substring(0, 10);
					}
					abandonedVehicleDetails.setReleased(releaseDate);
					
					return abandonedVehicleDetails;
				}
			});
			abandonedVehicle.setFromDate(fromDate);
			abandonedVehicle.setToDate(toDate);
			abandonedVehicle.setAbandonedVehicleDetails(abandonedVehicleDetailsList);
			abandonedVehicle.setTotalVehicles(Integer.toString(abandonedVehicleDetailsList.size()));	

		}catch (Exception e) {
			logger.error(e);
			e.printStackTrace();

		}		
		return abandonedVehicle;
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

