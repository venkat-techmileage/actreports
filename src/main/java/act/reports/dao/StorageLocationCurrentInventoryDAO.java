package act.reports.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import act.reports.model.SearchCriteria;
import act.reports.model.SelectOption;
import act.reports.model.StorageLocationCurrentInventoryDetails;
import act.reports.util.DateUtility;

@Repository("storageLocationCurrentInventoryDAO")
public class StorageLocationCurrentInventoryDAO {

	private Logger logger=Logger.getLogger(StorageLocationCurrentInventoryDAO.class);

	@Autowired
	JdbcTemplate jdbcTemplate;
	public List<SelectOption> getLocationList()
	{
		logger.info("In getLocationList()...");
		String lotLocationQry = "select distinct locationId,locationName from Lot__Locations where isImpoundLot='1'";		  
		List<SelectOption> locationList=null;
		try
		{
			locationList =jdbcTemplate.query(lotLocationQry, new Object[] {}, new RowMapper<SelectOption>() {			
		      public SelectOption mapRow(ResultSet rs, int rowNum) throws SQLException {		     
		    	 SelectOption locationList = new SelectOption();
		    	 String locationName = rs.getString("locationName")!=null?rs.getString("locationName"):"";
		    	 /*locationList.setOptionVal(rs.getString("locationId")!=null?rs.getString("locationId"):"");
		    	 locationList.setOptionTxt(rs.getString("locationName")!=null?rs.getString("locationName"):"");*/
		    	 locationList.setOptionVal(locationName);
		    	 locationList.setOptionTxt(locationName);
		    	return locationList;
		      }
		    });
		}
		 catch (Exception e) {
		   logger.error(e);
		  }
	
		  logger.info("locationList.size() : "+locationList.size());
		  return locationList;
	}
	
    
	public List<StorageLocationCurrentInventoryDetails> getStorageLocationCurrentInventoryDetails(SearchCriteria criteria){
		logger.info("In StorageLocationCurrentInventoryDAO-getStorageLocationCurrentInventoryDetails()...");
		List<StorageLocationCurrentInventoryDetails> storageLocationCurrentInventoryDetailsList = null;
		List<StorageLocationCurrentInventoryDetails> storageLocationCurrentInventoryDetailsListNew = new ArrayList<StorageLocationCurrentInventoryDetails>();
		String ageRangeStart="";
		String ageRangeEnd="";
		try{
			ageRangeStart=criteria.getAgeRangeStart();
			ageRangeEnd=criteria.getAgeRangeEnd();
			logger.info("ageRangeStart = "+ageRangeStart);
			logger.info("ageRangeEnd = "+ageRangeEnd);
			final String lotLocation = criteria.getLocation();
			logger.info("lotLocation = "+lotLocation);
			String locationDetailsqQery = "";
			
			if(lotLocation.trim().equalsIgnoreCase("all")){
				/*locationDetailsqQery = "select a.name,sc.callCreatedTime,i.invoiceId,i.reason,i.dropOffLocation,iv.vehicle_Year,iv.vehicle_Make,iv.vehicle_Model,iv.vehicle_VIN,iv.vehicle_Country,iv.vehicle_State,iv.vehicle_Plate_No "
										+ "from Account a,ServiceCallInfo sc,Invoice i LEFT OUTER JOIN Invoice_Vehicle iv ON i.invoiceId=iv.invoiceId where i.isImpound='1' and a.idAccount=i.accountId and sc.serviceCallId=i.serviceCallId";*/
				
				locationDetailsqQery = "select a.name,sc.callCreatedTime,i.invoiceId,i.reason,i.dropOffLocation,iv.vehicle_Year,iv.vehicle_Make,iv.vehicle_Model,iv.vehicle_VIN,iv.vehicle_Country,iv.vehicle_State,iv.vehicle_Plate_No from Invoice_Vehicle iv LEFT OUTER JOIN Invoice i ON " +
						 			   "i.invoiceId=iv.invoiceId LEFT OUTER JOIN Account a ON a.idAccount=i.accountId LEFT OUTER JOIN ServiceCallInfo sc ON sc.serviceCallId=i.serviceCallId where iv.isImpound='1' and i.dropOffLocation NOT IN ('Accounting','Dispatch','Payless')";
			}else{
				/*locationDetailsqQery = "select a.name,sc.callCreatedTime,i.invoiceId,i.reason,i.dropOffLocation,iv.vehicle_Year,iv.vehicle_Make,iv.vehicle_Model,iv.vehicle_VIN,iv.vehicle_Country,iv.vehicle_State,iv.vehicle_Plate_No from Account a," +
									   "ServiceCallInfo sc,Invoice i LEFT OUTER JOIN Invoice_Vehicle iv ON i.invoiceId=iv.invoiceId where i.isImpound='1' and i.dropOffLocation='"+lotLocation+"' and a.idAccount=i.accountId and sc.serviceCallId=i.serviceCallId";*/
				
				locationDetailsqQery = "select a.name,sc.callCreatedTime,i.invoiceId,i.reason,i.dropOffLocation,iv.vehicle_Year,iv.vehicle_Make,iv.vehicle_Model,iv.vehicle_VIN,iv.vehicle_Country,iv.vehicle_State,iv.vehicle_Plate_No from Invoice_Vehicle iv,Invoice i " +
						   			   "LEFT OUTER JOIN Account a ON a.idAccount=i.accountId LEFT OUTER JOIN ServiceCallInfo sc ON sc.serviceCallId=i.serviceCallId where i.dropOffLocation='"+lotLocation+"' and iv.isImpound='1' and i.invoiceId=iv.invoiceId";
			}
			
			storageLocationCurrentInventoryDetailsList = jdbcTemplate.query(locationDetailsqQery, new Object[] {}, new RowMapper<StorageLocationCurrentInventoryDetails>() {


				public StorageLocationCurrentInventoryDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

					StorageLocationCurrentInventoryDetails storageDetails = new StorageLocationCurrentInventoryDetails();					
					storageDetails.setInvoiceId((rs.getString("invoiceId") != null) ? rs.getString("invoiceId") : "");
					
					String callCreatedTime=rs.getString("callCreatedTime") != null ? rs.getString("callCreatedTime") : "";
					String serviceCalldate="";
					if(!callCreatedTime.trim().isEmpty()){
						serviceCalldate=DateUtility.convertToDateTimeFormat(callCreatedTime);
						serviceCalldate=serviceCalldate.substring(0, 10);
					}
					
					storageDetails.setServiceCallDate(serviceCalldate);
					storageDetails.setAccount((rs.getString("name") != null) ? rs.getString("name") : "");
					storageDetails.setReason((rs.getString("reason") != null) ? rs.getString("reason") : "");
					storageDetails.setLotLocation((rs.getString("dropOffLocation") != null) ? rs.getString("dropOffLocation") : "");
					storageDetails.setYear((rs.getString("vehicle_Year") != null) ? rs.getString("vehicle_Year") : "");
					storageDetails.setMake((rs.getString("vehicle_Make") != null) ? rs.getString("vehicle_Make") : "");
					storageDetails.setModel((rs.getString("vehicle_Model") != null) ? rs.getString("vehicle_Model") : "");
					storageDetails.setVin((rs.getString("vehicle_VIN") != null) ? rs.getString("vehicle_VIN") : "");
					storageDetails.setLicensePlateCountry((rs.getString("vehicle_Country") != null) ? rs.getString("vehicle_Country") : "");
					storageDetails.setLicensePlateState((rs.getString("vehicle_State") != null) ? rs.getString("vehicle_State") : "");
					storageDetails.setLicensePlate((rs.getString("vehicle_Plate_No") != null) ? rs.getString("vehicle_Plate_No") : "");
					
					String daysInStorage = " ";
					String currentDate=DateUtility.getCurrentMysqlDateTime();
					if(!callCreatedTime.trim().isEmpty()){
						String daysinStorageQry = "SELECT TIMESTAMPDIFF(DAY,'"+callCreatedTime+"','"+currentDate+"')";
						daysInStorage = jdbcTemplate.queryForObject(daysinStorageQry, String.class);
						logger.info("daysInStorage : "+daysInStorage);						
					}
					storageDetails.setDaysInStorage(daysInStorage);
					
					storageDetails.setMarkedForSalvage("");					
					return storageDetails;
				}
			});		
			if(!ageRangeStart.isEmpty() && !ageRangeEnd.isEmpty())
			{				
				for(int i=0;i<storageLocationCurrentInventoryDetailsList.size();i++)
				{					
					StorageLocationCurrentInventoryDetails storageLocationCurrentInventoryDetails = storageLocationCurrentInventoryDetailsList.get(i);
					int daysInStorage = Integer.parseInt(storageLocationCurrentInventoryDetails.getDaysInStorage());
					logger.info("daysInStorage = "+daysInStorage);
					if(daysInStorage>Integer.parseInt(ageRangeStart) && daysInStorage<=Integer.parseInt(ageRangeEnd))
					{
						storageLocationCurrentInventoryDetailsListNew.add(storageLocationCurrentInventoryDetails);
					}		
				}
				logger.info("storageLocationCurrentInventoryDetailsListNew.size() = "+storageLocationCurrentInventoryDetailsListNew.size());
			}
		}
		catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
		if(!ageRangeStart.isEmpty() && !ageRangeEnd.isEmpty())
			return storageLocationCurrentInventoryDetailsListNew;			
		else
			return storageLocationCurrentInventoryDetailsList;
	}
}
	
