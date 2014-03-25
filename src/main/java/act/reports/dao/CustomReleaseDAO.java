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
import act.reports.model.CustomRelease;
import act.reports.model.CustomReleaseDetails;
import act.reports.model.SearchCriteria;
import act.reports.util.DateUtility;

@Repository("customReleaseDAO")
public class CustomReleaseDAO {

	private Logger logger=Logger.getLogger(CustomReleaseDAO.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public CustomRelease getCustomRelease(SearchCriteria criteria){
		logger.info("In CustomReleaseDAO-getCustomRelease()...");
		List<CustomReleaseDetails> customReleaseDetailsList = null;
		CustomRelease customRelease = new CustomRelease();

		try{
			final String accountName = criteria.getAccountName();
			String fromDate=criteria.getFromDate();
			String toDate=criteria.getToDate();
			fromDate = DateUtility.convertAsMySqlDateTime(fromDate);
			logger.info("fromDate in CustomReleaseDAO-getCustomRelease() : "+fromDate);
			toDate = DateUtility.convertAsMySqlDateTime(toDate);
			toDate = toDate.replace("00:00:00", "23:59:59");
			logger.info("toDate in CustomReleaseDAO-getCustomRelease() : "+toDate);
			logger.info("accountName in CustomReleaseDAO-getCustomRelease() : "+accountName);

			String customReleaseQuery = "select aa.vendorId,aa.contract,i.invoiceId,i.accountRatePlanId,i.dropOffTime,ir.releaseTo,ir.releaseDate,iv.vehicle_Year,iv.vehicle_Make,iv.vehicle_Model,iv.vehicle_VIN," +
										"ib.towCharge,ib.storageCharge,ib.gateCharge,ib.supervisorFee,ib.refrigerationFee,ib.dryRunFee,ib.PCC36Charge from Account a,Invoice i LEFT OUTER JOIN Account_address aa " +
										"ON i.accountId=aa.idAccount LEFT OUTER JOIN Invoice_Billing ib ON i.invoiceId=ib.invoiceId LEFT OUTER JOIN Invoice_Vehicle iv ON i.invoiceId=iv.invoiceId LEFT OUTER JOIN Invoice_Release ir " +
										"ON i.invoiceId=ir.invoiceId where a.name='"+accountName+"' and i.callStatus='Cleared' and i.invoiceCreatedDate BETWEEN '"+fromDate+"' and '"+toDate+"' and a.idAccount=i.accountId";

			customReleaseDetailsList = jdbcTemplate.query(customReleaseQuery, new Object[] {}, new RowMapper<CustomReleaseDetails>() {

				public CustomReleaseDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

					CustomReleaseDetails customReleaseDetails = new CustomReleaseDetails();					
					customReleaseDetails.setId((rs.getString("vendorId") != null) ? rs.getString("vendorId") : "");
					customReleaseDetails.setContract((rs.getString("contract") != null) ? rs.getString("contract") : "");
					customReleaseDetails.setStorageReport((rs.getString("invoiceId") != null) ? rs.getString("invoiceId") : "");
					customReleaseDetails.setYear((rs.getString("vehicle_Year") != null) ? rs.getString("vehicle_Year") : "");
					customReleaseDetails.setMake((rs.getString("vehicle_Make") != null) ? rs.getString("vehicle_Make") : "");
					customReleaseDetails.setModel((rs.getString("vehicle_Model") != null) ? rs.getString("vehicle_Model") : "");
					customReleaseDetails.setVin((rs.getString("vehicle_VIN") != null) ? rs.getString("vehicle_VIN") : "");
					
					String precinct = "";
					if(!accountName.isEmpty())
						precinct = accountName.replaceAll("[a-zA-Z-'.@]", "");					
					customReleaseDetails.setPrecinct(precinct);

					String dropOffTime = (rs.getString("dropOffTime") != null) ? rs.getString("dropOffTime") : "";
					String impoundDate = " ";
					if(!dropOffTime.trim().isEmpty()){
						impoundDate=DateUtility.convertToDateTimeFormat(dropOffTime);
						impoundDate = impoundDate.substring(0, 10);
					}
					customReleaseDetails.setImpoundDate(impoundDate);

					String releaseDateTime = (rs.getString("releaseDate") != null) ? rs.getString("releaseDate") : "";
					String releaseDate = " ";
					if(!releaseDateTime.trim().isEmpty()){
						releaseDate=DateUtility.convertToDateTimeFormat(releaseDateTime);
						releaseDate = releaseDate.substring(0, 10);
					}
					customReleaseDetails.setReleasedDate(releaseDate);
				   
					int releaseTo = rs.getInt("releaseTo");
					customReleaseDetails.setReleasedTo(getAccountName(releaseTo));
					
					customReleaseDetails.setTowCharge((rs.getString("towCharge") != null) ? rs.getString("towCharge") : "");

					if(!dropOffTime.isEmpty() && !releaseDateTime.isEmpty()){
						customReleaseDetails.setOfStorageDays(Integer.toString(DateUtility.getDaysBetweenTwoDates(dropOffTime.substring(0, 10), releaseDateTime.substring(0, 10))));
					}

					customReleaseDetails.setStorageCharge((rs.getString("storageCharge") != null) ? rs.getString("storageCharge") : "");
					customReleaseDetails.setAfterHoursGateFee((rs.getString("gateCharge") != null) ? rs.getString("gateCharge") : "");
					customReleaseDetails.setStandByFee("");
					customReleaseDetails.setSupervisorFee((rs.getString("supervisorFee") != null) ? rs.getString("supervisorFee") : "");
					customReleaseDetails.setRefrigFee((rs.getString("refrigerationFee") != null) ? rs.getString("refrigerationFee") : "");
					customReleaseDetails.setOutSideCity("");
					customReleaseDetails.setDryRunFee((rs.getString("dryRunFee") != null) ? rs.getString("dryRunFee") : "");
					customReleaseDetails.setPccCharge((rs.getString("PCC36Charge") != null) ? rs.getString("PCC36Charge") : "");
					
					String accountRatePlanId = (rs.getString("accountRatePlanId") != null) ? rs.getString("accountRatePlanId") : "";
					if(!accountRatePlanId.isEmpty())
						customReleaseDetails.setLevel(getRatePlanName(accountRatePlanId));
					else
						customReleaseDetails.setLevel(accountRatePlanId);
						
					return customReleaseDetails;
				}
			});
			customRelease.setCustomReleaseDetails(customReleaseDetailsList);
			logger.info("In CustomReleaseDAO-customRelease.getCustomReleaseDetails().size())...");

		}catch (Exception e) {
			logger.error(e);
			e.printStackTrace();

		}		
		return customRelease;
	}
	
	public String getAccountName(int releaseTo)
	{
		String accountName="";		
		logger.info("In getAccountName()...");

		String query_email = "select name from Account where idAccount=?";

		try
		{
			accountName=jdbcTemplate.queryForObject(query_email,new Object[] {releaseTo},String.class);
		}
		catch (NullPointerException npe)
		{
			logger.error(npe);
			accountName = " ";
		}
		catch(Exception e) 
		{
			logger.error(e);
		}

		return accountName;
	}
	
	public String getRatePlanName(String accountRatePlanId)
	{
		String ratePlanName="";		
		logger.info("In getRatePlanName()...");

		String query_email = "select AccountPlanRateName from AccountRatePlan where AccountRatePlanId=?";

		try
		{
			ratePlanName=jdbcTemplate.queryForObject(query_email,new Object[] {accountRatePlanId},String.class);
		}
		catch (NullPointerException npe)
		{
			logger.error(npe);
			ratePlanName = " ";
		}
		catch(Exception e) 
		{
			logger.error(e);
		}

		return ratePlanName;
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

