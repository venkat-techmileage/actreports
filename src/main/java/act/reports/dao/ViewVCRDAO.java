package act.reports.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import act.reports.model.DriverIDs;
import act.reports.model.DriverNames;
import act.reports.model.SearchCriteria;
import act.reports.model.TruckIDs;
import act.reports.model.ViewVCRData;
import act.reports.model.ViewVCRDetails;
import act.reports.util.DateUtility;

@Repository("viewVCRDAO")
public class ViewVCRDAO {

	private Logger logger=Logger.getLogger(ViewVCRDAO.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public ViewVCRData viewVCRDetails(SearchCriteria criteria){
		logger.info("In DriverSalesVsCommission-viewVCRDetails()...");
		List<ViewVCRDetails> viewVCRDetailsList = null;
		ViewVCRData viewVCRData = new ViewVCRData();
		try {
			String fromDate=criteria.getFromDate();
			String toDate=criteria.getToDate();
			fromDate = DateUtility.convertAsMySqlDateTime(fromDate);
			logger.info("fromDate in ViewVCRDAO-viewVCRDetails() : "+fromDate);
			toDate = DateUtility.convertAsMySqlDateTime(toDate);
			toDate = toDate.replace("00:00:00", "23:59:59");
			logger.info("toDate in ViewVCRDAO-viewVCRDetails() : "+toDate);
			String searchString = criteria.getSearchString();
			logger.info("searchString : "+searchString);
			
			String driverId = "";
			String driverName = "";
			String truckId = "";
			String viewVCRQry = "";
			int index = searchString.indexOf("-");
			logger.info("index = "+index);
			
			if(searchString.substring(0, index).equalsIgnoreCase("All"))
			{
				if(searchString.substring(index+1).equalsIgnoreCase("All")){
					viewVCRQry = "select truckId,inspectionDateTime,inspectionType,vehicle_inspection_Notes,inspection_Notes,driverId " +
								 "from truck_preinspection_record where inspectionDateTime BETWEEN '"+fromDate+"' and '"+toDate+"'";
				}
				else if(searchString.substring(index+1).equalsIgnoreCase("DriverName")){
					driverName = criteria.getDriverName();
					logger.info("driverName : "+driverName);
					int index1 = driverName.indexOf(" ");
					String fname = driverName.substring(0, index1);
					String lname = driverName.substring(index1+1);
					viewVCRQry = "select distinct t.truckId,t.inspectionDateTime,t.inspectionType,t.vehicle_inspection_Notes,t.inspection_Notes,t.driverId from truck_preinspection_record t,Employee e " +
							 	 "where e.firstName='"+fname+"' and e.lastName='"+lname+"' and t.inspectionDateTime BETWEEN '"+fromDate+"' and '"+toDate+"' and t.driverId=e.idEmployee";
					
				}
				else if(searchString.substring(index+1).equalsIgnoreCase("DriverID")){
					driverId = criteria.getDriverId();
					logger.info("driverId : "+driverId); 					
					viewVCRQry = "select distinct t.truckId,t.inspectionDateTime,t.inspectionType,t.vehicle_inspection_Notes,t.inspection_Notes,t.driverId from truck_preinspection_record t" +
								 ",UserAuth u where u.userId='"+driverId+"' and t.inspectionDateTime BETWEEN '"+fromDate+"' and '"+toDate+"' and t.driverId=u.Employee_idEmployee";					
					
				}
				else if(searchString.substring(index+1).equalsIgnoreCase("TruckID")){
					truckId = criteria.getTruckId();
					logger.info("truckId : "+truckId); 
					viewVCRQry = "select distinct truckId,inspectionDateTime,inspectionType,vehicle_inspection_Notes,inspection_Notes,driverId from " +
								 "truck_preinspection_record where inspectionDateTime BETWEEN '"+fromDate+"' and '"+toDate+"' and truckId='"+truckId+"'";
				}
			}
			else if(searchString.substring(0, index).equalsIgnoreCase("WithDefects"))
			{
				if(searchString.substring(index+1).equalsIgnoreCase("All")){
					viewVCRQry = "select distinct truckId,inspectionDateTime,inspectionType,vehicle_inspection_Notes,inspection_Notes,driverId from " +
								 "truck_preinspection_record where inspectionDateTime BETWEEN '"+fromDate+"' and '"+toDate+"' and vehicle_inspection_Notes!=''";
				}
				else if(searchString.substring(index+1).equalsIgnoreCase("DriverName")){
					driverName = criteria.getDriverName();
					logger.info("driverName : "+driverName); 
					int index1 = driverName.indexOf(" ");
					String fname = driverName.substring(0, index1);
					String lname = driverName.substring(index1+1);
					viewVCRQry = "select distinct t.truckId,t.inspectionDateTime,t.inspectionType,t.vehicle_inspection_Notes,t.inspection_Notes,t.driverId from truck_preinspection_record t,Employee e " +
								 "where e.firstName='"+fname+"' and e.lastName='"+lname+"' and t.inspectionDateTime BETWEEN '"+fromDate+"' and '"+toDate+"'  and vehicle_inspection_Notes!='' and t.driverId=e.idEmployee";
				}
				else if(searchString.substring(index+1).equalsIgnoreCase("DriverID")){
					driverId = criteria.getDriverId();
					logger.info("driverId : "+driverId); 
					viewVCRQry = "select distinct t.truckId,t.inspectionDateTime,t.inspectionType,t.vehicle_inspection_Notes,t.inspection_Notes,t.driverId from truck_preinspection_record t,UserAuth u " +
								 "where u.userId='"+driverId+"' and t.inspectionDateTime BETWEEN '"+fromDate+"' and '"+toDate+"' and vehicle_inspection_Notes!='' and t.driverId=u.Employee_idEmployee";
				}
				else if(searchString.substring(index+1).equalsIgnoreCase("TruckID")){
					truckId = criteria.getTruckId();
					logger.info("truckId : "+truckId); 
					viewVCRQry = "select distinct truckId,inspectionDateTime,inspectionType,vehicle_inspection_Notes,inspection_Notes,driverId from truck_preinspection_record " +
								 "where inspectionDateTime BETWEEN '"+fromDate+"' and '"+toDate+"' and vehicle_inspection_Notes!='' and truckId='"+truckId+"'";
				}
			}
				
			
			viewVCRDetailsList = jdbcTemplate.query(viewVCRQry, new Object[] {}, new RowMapper<ViewVCRDetails>() {

				public ViewVCRDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

					ViewVCRDetails viewVCRDetails = new ViewVCRDetails();	
					String truckId = (rs.getString("truckId") != null) ? rs.getString("truckId") : " ";
					logger.info("truckId in viewVCRDetails() = "+truckId);
					viewVCRDetails.setTruckId(truckId);
					
					String inspectionDateTime = (rs.getString("inspectionDateTime") != null) ? rs.getString("inspectionDateTime") : "";
					if(!inspectionDateTime.isEmpty()){
						/*inspectionDateTime=DateUtility.convertToDateTimeFormat(inspectionDateTime);
						inspectionDateTime=inspectionDateTime.substring(0, 10);*/
						viewVCRDetails.setDate(DateUtility.convertToDateTimeFormat(inspectionDateTime).substring(0, 10));
					}
					else
						viewVCRDetails.setDate(inspectionDateTime);
						
					String inspectionType = (rs.getString("inspectionType") != null) ? rs.getString("inspectionType") : " ";
					logger.info("inspectionType in viewVCRDetails() = "+inspectionType);
					viewVCRDetails.setPreOrPost(inspectionType);
					
					String vehicleInspectionNotes = (rs.getString("vehicle_inspection_Notes") != null) ? rs.getString("vehicle_inspection_Notes") : "";
					viewVCRDetails.setVehicleInspectionNotes(vehicleInspectionNotes);
					if(!vehicleInspectionNotes.isEmpty())
						viewVCRDetails.setDefects("Y");
					else
						viewVCRDetails.setDefects("N");
					
					String inspectionNotes = (rs.getString("inspection_Notes") != null) ? rs.getString("inspection_Notes") : "";
					viewVCRDetails.setInspectionNotes(inspectionNotes);
					
					String driverId = (rs.getString("driverId") != null) ? rs.getString("driverId") : " ";					
					if(!driverId.isEmpty())
						viewVCRDetails.setDriverId(getUserId(driverId));
					else
						viewVCRDetails.setDriverId(driverId);
					logger.info("driverId in viewVCRDetails() = "+driverId);
					
					String mileage = "";
					String mileageQry = "";
					if(inspectionType.trim().equalsIgnoreCase("Pre") && !truckId.trim().isEmpty() && !driverId.trim().isEmpty() && !inspectionDateTime.trim().isEmpty() && inspectionDateTime.trim().length()>10){
						mileageQry = "select startMileage from TruckDriverTrack where truckId='"+truckId+"' and driverId='"+driverId+"' and loginTime LIKE '"+inspectionDateTime.substring(0, 16)+"%'";
						try{
							mileage = jdbcTemplate.queryForObject(mileageQry, String.class);
						}
						catch(EmptyResultDataAccessException erda){
							logger.info("No data found for mileage.");
							mileage = " ";
						}
					}
					else if(inspectionType.trim().equalsIgnoreCase("Post") && !truckId.trim().isEmpty() && !driverId.trim().isEmpty() && !inspectionDateTime.trim().isEmpty() && inspectionDateTime.trim().length()>10){
						mileageQry = "select endMileage from TruckDriverTrack where truckId='"+truckId+"' and driverId='"+driverId+"' and logoutTime LIKE '"+inspectionDateTime.substring(0, 16)+"%'";
						try{
							mileage = jdbcTemplate.queryForObject(mileageQry, String.class);
						}
						catch(EmptyResultDataAccessException erda){
							logger.info("No data found for mileage.");
							mileage = " ";
						}
					}
					logger.info("mileage in viewVCRDetails() = "+mileage);
					viewVCRDetails.setMileage(mileage);				
					
					return viewVCRDetails;
				}
			});		
			
			viewVCRData.setFromDate(criteria.getFromDate());
			viewVCRData.setToDate(criteria.getToDate());
			viewVCRData.setDriverId(driverId);
			viewVCRData.setDriverName(driverName);
			viewVCRData.setTruckId(truckId);
			if(searchString.substring(0, index).equalsIgnoreCase("WithDefects"))
				viewVCRData.setSearchType("WithDefects");
			viewVCRData.setTotalRecords(Integer.toString(viewVCRDetailsList.size()));
			viewVCRData.setViewVCRDetails(viewVCRDetailsList);	
			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
		return viewVCRData;
	}
	
	public String getUserId(String driverId)
	{
		String userIdQry = "select userId from UserAuth where Employee_idEmployee=?";
		String userId="";
		try
		{					
			userId=jdbcTemplate.queryForObject(userIdQry,new Object[] {driverId},String.class);
		}
		catch (NullPointerException npe)
		{
			logger.error(npe);
			userId = "";
		}
		catch (Exception e)
		{
			logger.error(e);
		}

		return userId;
	}
	
	public List<DriverIDs> getDriverIDs()
	{
		logger.info("In getDriverIDs()...");
		String driverIDsQry = "select u.userId from UserAuth u,Employee e where u.Employee_idEmployee=e.idEmployee and e.isDriver='1'";
		List<DriverIDs> driverIDsList=null;
		try
		{
			driverIDsList =jdbcTemplate.query(driverIDsQry, new Object[] {}, new RowMapper<DriverIDs>() {			
		      public DriverIDs mapRow(ResultSet rs, int rowNum) throws SQLException {		     
		    	  DriverIDs driverIDs = new DriverIDs();		    	
		    	  driverIDs.setDriverID(rs.getString("userId")!=null?rs.getString("userId"):"");
		    	return driverIDs;
		      }
		    });
	     }
		 catch (Exception e) {
		   logger.error(e);
		  }
		  logger.info("driverIDsList.size() : "+driverIDsList.size());
		  return driverIDsList;
	}
	
	public List<DriverNames> getDriverNames()
	{
		logger.info("In getDriverNames()...");
		String driverNamesQry = "select firstName,lastName from Employee where isDriver='1'";		  
		List<DriverNames> driverNamesList=null;
		try
		{
			driverNamesList =jdbcTemplate.query(driverNamesQry, new Object[] {}, new RowMapper<DriverNames>() {			
		      public DriverNames mapRow(ResultSet rs, int rowNum) throws SQLException {		     
		    	  DriverNames driverNames = new DriverNames();	
		    	  String firstName = rs.getString("firstName")!=null?rs.getString("firstName"):"";
				  String lastName = rs.getString("lastName")!=null?rs.getString("lastName"):"";
				  driverNames.setDriverName(firstName+" "+lastName); 		    	  		    	
		    	return driverNames;
		      }
		    });
	     }
		 catch (Exception e) {
		   logger.error(e);
		  }
		  logger.info("driverNamesList.size() : "+driverNamesList.size());
		  return driverNamesList;
	}
	
	public List<TruckIDs> getTruckIDs()
										{
		logger.info("In getDriverIDs()...");
		String truckIDsQry = "select TruckId from Truck";		  
		List<TruckIDs> truckIDsList=null;
		try
		{
			truckIDsList =jdbcTemplate.query(truckIDsQry, new Object[] {}, new RowMapper<TruckIDs>() {			
		      public TruckIDs mapRow(ResultSet rs, int rowNum) throws SQLException {		     
		    	  TruckIDs truckIDs = new TruckIDs();		    	
		    	  truckIDs.setTruckID(rs.getString("TruckId")!=null?rs.getString("TruckId"):"");		    	  		    	
		    	return truckIDs;
		      }
		    });
	     }
		 catch (Exception e) {
		   logger.error(e);
		  }
		  logger.info("truckIDsList.size() : "+truckIDsList.size());
		  return truckIDsList;
	}
}
