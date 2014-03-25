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

import act.reports.model.NoAbandonedVehicle;
import act.reports.model.NoAbandonedVehicleDetails;
import act.reports.util.DateUtility;

@Repository("noAbandonedVehicleDAO")
public class NoAbandonedVehicleDAO {

	private Logger logger=Logger.getLogger(NoAbandonedVehicleDAO.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public NoAbandonedVehicle getNoAbandonedVehicle(){
		logger.info("In NoAbandonedVehicleDAO-getNoAbandonedVehicle()...");
		List<NoAbandonedVehicleDetails> noAbandonedVehicleDetailsList = null;
		List<NoAbandonedVehicleDetails> noAbandonedVehicleDetailsListFinal = new ArrayList<NoAbandonedVehicleDetails>();
		NoAbandonedVehicle noAbandonedvehicle = new NoAbandonedVehicle();
       
		try{	
			final String currentDate = DateUtility.getCurrentMysqlDateTime();
			
			String noabandonedquery = "select distinct a.name,i.reason,i.dropOffTime,i.dropOffLocation,sc.callCreatedTime,ib.PoliceHoldOnce,iv.invoiceId,iv.vehicle_Year,iv.vehicle_Make,iv.vehicle_Model," +
									  "iv.vehicle_VIN,iv.vehicle_Plate_No from Account a,ServiceCallInfo sc,Invoice i,Invoice_Vehicle iv LEFT OUTER JOIN Invoice_Billing ib ON iv.invoiceId=ib.invoiceId LEFT OUTER JOIN " +
									  "Invoice_TitleProcess it ON it.avrDate='' where iv.isImpound='1' and a.idAccount=i.accountId and i.serviceCallId=sc.serviceCallId and i.invoiceId=iv.invoiceId order by iv.invoiceId";
			 
			noAbandonedVehicleDetailsList = jdbcTemplate.query(noabandonedquery, new Object[] {}, new RowMapper<NoAbandonedVehicleDetails>() {

				public NoAbandonedVehicleDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

					NoAbandonedVehicleDetails noAbandonedVehicleDetails = new NoAbandonedVehicleDetails();	
					noAbandonedVehicleDetails.setAccount((rs.getString("name") != null) ? rs.getString("name") : "");					
					noAbandonedVehicleDetails.setReason((rs.getString("reason") != null) ? rs.getString("reason") : "");
					noAbandonedVehicleDetails.setDropOffTime((rs.getString("dropOffTime") != null) ? rs.getString("dropOffTime") : "");
					noAbandonedVehicleDetails.setLotLocation((rs.getString("dropOffLocation") != null) ? rs.getString("dropOffLocation") : "");
					
					String serviceCalldate=rs.getString("callCreatedTime") != null ? rs.getString("callCreatedTime") : "";
					if(!serviceCalldate.trim().isEmpty())
						/*serviceCalldate=DateUtility.convertToDateTimeFormat(serviceCalldate);
						serviceCalldate=serviceCalldate.substring(0, 10);*/
						noAbandonedVehicleDetails.setServiceCallDate(DateUtility.convertToDateTimeFormat(serviceCalldate).substring(0, 10));
					else
						noAbandonedVehicleDetails.setServiceCallDate(serviceCalldate);
					
					
					
					noAbandonedVehicleDetails.setPoliceHoldOnce((rs.getString("PoliceHoldOnce") != null) ? rs.getString("PoliceHoldOnce") : "");
					noAbandonedVehicleDetails.setInvoice((rs.getString("invoiceId") != null) ? rs.getString("invoiceId") : "");
					noAbandonedVehicleDetails.setYear((rs.getString("vehicle_Year") != null) ? rs.getString("vehicle_Year") : "");
					noAbandonedVehicleDetails.setMake((rs.getString("vehicle_Make") != null) ? rs.getString("vehicle_Make") : "");
					noAbandonedVehicleDetails.setModel((rs.getString("vehicle_Model") != null) ? rs.getString("vehicle_Model") : "");
					noAbandonedVehicleDetails.setVin((rs.getString("vehicle_VIN") != null) ? rs.getString("vehicle_VIN") : "");
					noAbandonedVehicleDetails.setLicensePlate((rs.getString("vehicle_Plate_No") != null) ? rs.getString("vehicle_Plate_No") : "");
					
					String daysInStorage = " ";					
					if(!serviceCalldate.trim().isEmpty()){
						String daysinStorageQry = "SELECT TIMESTAMPDIFF(DAY,'"+serviceCalldate+"','"+currentDate+"')";
				    	daysInStorage = jdbcTemplate.queryForObject(daysinStorageQry, String.class);
				    	logger.info("daysInStorage : "+daysInStorage);				    	
					}
					noAbandonedVehicleDetails .setDaysInStorage(daysInStorage);
					
					return noAbandonedVehicleDetails;
				}
			});

			for(int i=0;i<noAbandonedVehicleDetailsList.size();i++){
				NoAbandonedVehicleDetails noAbandonedVehicleDetails = noAbandonedVehicleDetailsList.get(i);
				int daysDiff = 0;
				String policeHoldOnce = noAbandonedVehicleDetails.getPoliceHoldOnce();
				String dropOffTime = noAbandonedVehicleDetails.getDropOffTime();
				if(!dropOffTime.trim().isEmpty()){
					String daysDiffQry = "SELECT TIMESTAMPDIFF(DAY,'"+dropOffTime+"','"+currentDate+"')";
					daysDiff = jdbcTemplate.queryForObject(daysDiffQry, Integer.class);
					logger.info("daysDiff = "+daysDiff);
				}
				if(policeHoldOnce.equalsIgnoreCase("1") && daysDiff>40)
					noAbandonedVehicleDetailsListFinal.add(noAbandonedVehicleDetails);
				else if(policeHoldOnce.equalsIgnoreCase("0") && daysDiff>10)
					noAbandonedVehicleDetailsListFinal.add(noAbandonedVehicleDetails);
			}
			
			noAbandonedvehicle.setNoAbandonedVehicleDetails(noAbandonedVehicleDetailsListFinal);
			noAbandonedvehicle.setTotalVehicles(Integer.toString(noAbandonedVehicleDetailsList.size()));	
			
		}catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}		
		return noAbandonedvehicle;
	}
}

