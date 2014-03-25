package act.reports.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import act.reports.model.CommissionDetails;
import act.reports.model.DriverCommissionDetails;
import act.reports.model.DriverIDs;
import act.reports.model.DriverNames;
import act.reports.model.DriverSalesDetails;
import act.reports.model.DriverSalesVsCommission;
import act.reports.model.SearchCriteria;
import act.reports.util.DateUtility;

@Repository("driverSalesVsCommissionDAO")
public class DriverSalesVsCommissionDAO {

	private Logger logger=Logger.getLogger(DriverSalesVsCommissionDAO.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public DriverSalesVsCommission getDriverSalesCommissionDetails(SearchCriteria criteria){
		logger.info("In DriverSalesVsCommission-getReceiptsDetails()...");
		DriverSalesVsCommission driverSalesVsCommission = new DriverSalesVsCommission();
		DriverSalesDetails driverSalesDetails = new DriverSalesDetails();
		DriverCommissionDetails driverCommissionDetails = new DriverCommissionDetails();
		List<String> invoiceIdsList = new ArrayList<String>();
		try {
			String fromDate=criteria.getFromDate();
			String toDate=criteria.getToDate();
			fromDate = DateUtility.convertAsMySqlDateTime(fromDate);
			logger.info("fromDate in DriverSalesVsCommission-getDriverSalesCommissionDetails() : "+fromDate);
			toDate = DateUtility.convertAsMySqlDateTime(toDate);
			toDate = toDate.replace("00:00:00", "23:59:59");
			logger.info("toDate in DriverSalesVsCommission-getDriverSalesCommissionDetails() : "+toDate);
			String searchString = criteria.getSearchString();
			logger.info("searchString : "+criteria.getSearchString());

			String userData = "";
			String driverId = "";
			String driverName = "";
			String driverSalesQry = "";
			String invoiceIdsQry = "";

			if(searchString.equalsIgnoreCase("byDriverId")){
				driverId = criteria.getDriverId();
				logger.info("driverId : "+driverId);
				userData = driverId;
				driverSalesQry = "select count(i.invoiceId) as totalInvoices,SUM(ib.towCharge) as towing,SUM(ib.mileageCharge) as mileage,SUM(ib.laborCharge) as labor,SUM(ib.gateCharge) as gate,"+ 
								 "SUM(ib.totalDiscount) as discounts from Employee e,Invoice i,Invoice_Billing ib,UserAuth u where u.userId='"+driverId+"' and e.isDriver='1' "+
								 "and i.invoiceCreatedDate BETWEEN '"+fromDate+"' and '"+toDate+"' and i.invoiceId=ib.invoiceId and i.driverId=e.idEmployee and u.Employee_idEmployee=e.idEmployee";
				
				invoiceIdsQry = "select distinct i.invoiceId from Employee e,Invoice i,Invoice_Billing ib,UserAuth u where u.userId='"+driverId+"' and e.isDriver='1' and i.invoiceCreatedDate"+
						 		" BETWEEN '"+fromDate+"' and '"+toDate+"' and i.invoiceId=ib.invoiceId and i.driverId=e.idEmployee and u.Employee_idEmployee=e.idEmployee";
			}
			else if(searchString.equalsIgnoreCase("byDriverName")){
				driverName = criteria.getDriverName();
				logger.info("driverName : "+driverName);
				userData = driverName;
				int index = driverName.indexOf(" ");
				String fname = driverName.substring(0, index);
				String lname = driverName.substring(index+1);
				driverSalesQry = "select count(i.invoiceId) as totalInvoices,SUM(ib.towCharge) as towing,SUM(ib.mileageCharge) as mileage,SUM(ib.laborCharge) as labor,"+ 
								 "SUM(ib.gateCharge) as gate,SUM(ib.totalDiscount) as discounts from Employee e,Invoice i,Invoice_Billing ib where e.firstName='"+fname+"' and "+
								 "e.lastName='"+lname+"' and e.isDriver='1' and i.invoiceCreatedDate BETWEEN '"+fromDate+"' and '"+toDate+"' and i.invoiceId=ib.invoiceId and i.driverId=e.idEmployee";
				
				invoiceIdsQry = "select distinct i.invoiceId from Employee e,Invoice i,Invoice_Billing ib where e.firstName='"+fname+"' and e.lastName='"+lname+"' and "+
						 		"e.isDriver='1' and i.invoiceCreatedDate BETWEEN '"+fromDate+"' and '"+toDate+"' and i.invoiceId=ib.invoiceId and i.driverId=e.idEmployee";
			}

			driverSalesDetails = jdbcTemplate.queryForObject(driverSalesQry, new Object[] {}, new RowMapper<DriverSalesDetails>() {

				public DriverSalesDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

					DriverSalesDetails driverSalesDetails = new DriverSalesDetails();					
					driverSalesDetails.setTotalTowingCharges((rs.getString("towing") != null) ? rs.getString("towing") : "0.00");
					driverSalesDetails.setTotalMileageCharges((rs.getString("mileage") != null) ? rs.getString("mileage") : "0.00");
					driverSalesDetails.setTotalLaborCharges((rs.getString("labor") != null) ? rs.getString("labor") : "0.00");
					driverSalesDetails.setTotalGateCharges((rs.getString("gate") != null) ? rs.getString("gate") : "0.00");
					driverSalesDetails.setTotalDiscounts((rs.getString("discounts") != null) ? rs.getString("discounts") : "0.00");
					driverSalesDetails.setTotalAdminCharges("0.00");
					driverSalesDetails.setTotalInvoices((rs.getString("totalInvoices") != null) ? rs.getString("totalInvoices") : "0");
					return driverSalesDetails;
				}
			});

			float subTotal = 0f;
			subTotal += Float.parseFloat(driverSalesDetails.getTotalTowingCharges())+Float.parseFloat(driverSalesDetails.getTotalMileageCharges())+Float.parseFloat(driverSalesDetails.getTotalLaborCharges())+Float.parseFloat(driverSalesDetails.getTotalGateCharges())+Float.parseFloat(driverSalesDetails.getTotalAdminCharges());
			logger.info("subTotal : "+subTotal);

			float totalSales = 0f;
			totalSales = subTotal - Float.parseFloat(driverSalesDetails.getTotalDiscounts());
			logger.info("totalSales : "+totalSales);

			driverSalesDetails.setSubTotal(roundUp(subTotal, 2));
			driverSalesDetails.setTotalSales(roundUp(totalSales, 2));

			driverSalesVsCommission.setFromDate(fromDate);
			driverSalesVsCommission.setToDate(toDate);
			driverSalesVsCommission.setDriverId(driverId);
			driverSalesVsCommission.setDriverName(driverName);
			driverSalesVsCommission.setDriverSalesDetails(driverSalesDetails);
			
			invoiceIdsList = jdbcTemplate.query(invoiceIdsQry, new Object[] {}, new RowMapper<String>() {

				public String mapRow(ResultSet rs, int rowNum) throws SQLException {

					String invoiceId = (rs.getString("invoiceId") != null) ? rs.getString("invoiceId") : "";					
					return invoiceId;
				}
			});
			
			float hourlyCommTotal = 0.0f;
			float percentCommTotal = 0.0f;
			float flatRateCommTotal = 0.0f;
			
			float hourlyHoursOrInvoices = 0.0f;
			float percentHoursOrInvoices = 0.0f;
			float flatRateHoursOrInvoices = 0.0f;
			
			float hourlyRatePerHourOrInvoice = 0.0f;
			float percentRatePerHourOrInvoice = 0.0f;
			float flatRatePerHourOrInvoice = 0.0f;
			
			float totalCommission = 0.0f;
						
			if(invoiceIdsList!=null && invoiceIdsList.size()>0){
				for(int i=0;i<invoiceIdsList.size();i++){						
					hourlyCommTotal += getHourlyCommAmt(searchString, userData, invoiceIdsList.get(i));
					percentCommTotal += getPercentageCommAmt(searchString, userData, invoiceIdsList.get(i));
					flatRateCommTotal += getFlatRateCommAmt(searchString, userData, invoiceIdsList.get(i));
					
					hourlyHoursOrInvoices += getHourlyHoursOrInvoices(searchString, userData, invoiceIdsList.get(i));
					if(isInvoicePaidByPercentageComm(searchString, userData, invoiceIdsList.get(i)))
						percentHoursOrInvoices += 1;
					if(isInvoicePaidByFlatRateComm(searchString, userData, invoiceIdsList.get(i)))
						flatRateHoursOrInvoices += 1;					
				}
			}
			driverCommissionDetails.setHourlyCommTotal(roundUp(hourlyCommTotal, 2));
			driverCommissionDetails.setPercentCommTotal(roundUp(percentCommTotal, 2));
			driverCommissionDetails.setFlatRateCommTotal(roundUp(flatRateCommTotal, 2));
			
			driverCommissionDetails.setHourlyHoursOrInvoices(Float.toString(hourlyHoursOrInvoices));
			driverCommissionDetails.setPercentHoursOrInvoices(Float.toString(percentHoursOrInvoices));
			driverCommissionDetails.setFlatRateHoursOrInvoices(Float.toString(flatRateHoursOrInvoices));
			
			if(hourlyCommTotal>0 && hourlyHoursOrInvoices>0)
				hourlyRatePerHourOrInvoice = hourlyCommTotal / hourlyHoursOrInvoices;
			driverCommissionDetails.setHourlyRatePerHourOrInvoice(Float.toString(hourlyRatePerHourOrInvoice));
			
			if(percentCommTotal>0 && percentHoursOrInvoices>0)
				percentRatePerHourOrInvoice = percentCommTotal / percentHoursOrInvoices;
			driverCommissionDetails.setPercentRatePerHourOrInvoice(Float.toString(percentRatePerHourOrInvoice));
			
			if(flatRateCommTotal>0 && flatRateHoursOrInvoices>0)
				flatRatePerHourOrInvoice = flatRateCommTotal / flatRateHoursOrInvoices;
			driverCommissionDetails.setFlatRatePerHourOrInvoice(Float.toString(flatRatePerHourOrInvoice));
			
			driverSalesVsCommission.setDriverCommissionDetails(driverCommissionDetails);	
			
			totalCommission = hourlyCommTotal + percentCommTotal + flatRateCommTotal;
			driverCommissionDetails.setTotalCommission(roundUp(totalCommission, 2));
			
			driverSalesVsCommission.setTotalInvoices(driverSalesDetails.getTotalInvoices());

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}

		return driverSalesVsCommission;
	}

	public List<DriverIDs> getDriverIDs()
	{
		logger.info("In getDriverIDs()...");
		String driverIDsQry = "select u.userId from UserAuth u,Employee e where e.isDriver='1'and e.idEmployee=u.Employee_idEmployee";		  
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
	
	private  float getHourlyCommAmt(String searchString, String userData, String invoiceId)
	{
		logger.info("In getHourlyCommAmt()...");
		float commissionAmount = 0.0f;
		try{
			String lvlQry = "";			
			if(searchString.equalsIgnoreCase("byDriverId")){
				lvlQry = "select e.level from Employee e,UserAuth u where u.userId='"+userData+"' and e.isDriver='1' and e.idEmployee=u.Employee_idEmployee";				
			}
			else if(searchString.equalsIgnoreCase("byDriverName")){
				int index = userData.indexOf(" ");
				String fname = userData.substring(0, index);
				String lname = userData.substring(index+1);
				lvlQry = "select level from Employee where firstName='"+fname+"' and lastName='"+lname+"' and isDriver='1'";
			}
			String driverLevel = jdbcTemplate.queryForObject(lvlQry, String.class);
			
			String ratePlanQry = "select accountRatePlanId from Invoice where invoiceId='"+invoiceId+"'";
			String accountRatePlan = jdbcTemplate.queryForObject(ratePlanQry, String.class);
			
			String commTypeIdQry = "select driver_commision_type_id from AccountRatePlan where AccountRatePlanId='"+accountRatePlan+"'";
			String commissionTypeId = jdbcTemplate.queryForObject(commTypeIdQry, String.class);
			
			CommissionDetails commissionDetails = getCommissionDetails(commissionTypeId, driverLevel.substring(driverLevel.length()-1));
			
			String commStartFromTime = getCommissionStratFromTime(invoiceId, commissionDetails.getCommStartFrom());
			String commEndByTime = getCommissionEndByTime(invoiceId, commissionDetails.getCommEndBy());
			String durBetweenCommStartEnd = getDurationBetweenTimes(commStartFromTime, commEndByTime);
			float timeBetweenCommStartEnd = 0.0f;
			if(!durBetweenCommStartEnd.equalsIgnoreCase(""))
				timeBetweenCommStartEnd = Float.parseFloat(durBetweenCommStartEnd);
			logger.info("timeBetweenCommStartEnd in getHourlyCommAmt = "+timeBetweenCommStartEnd);
						
			float hourlyComm = Float.parseFloat(commissionDetails.getHourlyComm()!=null?commissionDetails.getHourlyComm():"0.00");
			float hourlyCommMinHours = Float.parseFloat(commissionDetails.getHourlyCommMinHours()!=null?commissionDetails.getHourlyCommMinHours():"0.00");
			float hourlyCommIncrementHours = Float.parseFloat(commissionDetails.getHourlyCommIncrementHours()!=null?commissionDetails.getHourlyCommIncrementHours():"0.00");
									
			if(hourlyCommMinHours > timeBetweenCommStartEnd)
			{
				commissionAmount += hourlyCommMinHours * hourlyComm;
			}
			else if(timeBetweenCommStartEnd > hourlyCommMinHours)
			{
				commissionAmount = hourlyCommMinHours * hourlyComm;
				float remainingHours = timeBetweenCommStartEnd - hourlyCommMinHours;
				if(remainingHours < hourlyCommIncrementHours){
					commissionAmount += hourlyCommIncrementHours * hourlyComm;
				}
				else{
					commissionAmount += remainingHours * hourlyComm;
				}
			}
							
			logger.info("commissionAmount in getHourlyCommAmt : "+commissionAmount);
		}
		catch(Exception e){
			logger.error(e);
		}
		
		return commissionAmount;
	}
	
	private  float getPercentageCommAmt(String searchString, String userData, String invoiceId)
	{
		logger.info("In getPercentageCommAmt()...");
		float commissionAmount = 0.0f;
		try{
			String lvlQry = "";			
			if(searchString.equalsIgnoreCase("byDriverId")){
				lvlQry = "select e.level from Employee e,UserAuth u where u.userId='"+userData+"' and e.isDriver='1' and e.idEmployee=u.Employee_idEmployee";				
			}
			else if(searchString.equalsIgnoreCase("byDriverName")){
				int index = userData.indexOf(" ");
				String fname = userData.substring(0, index);
				String lname = userData.substring(index+1);
				lvlQry = "select level from Employee where firstName='"+fname+"' and lastName='"+lname+"' and isDriver='1'";
			}
			String driverLevel = jdbcTemplate.queryForObject(lvlQry, String.class);
			
			String ratePlanQry = "select accountRatePlanId from Invoice where invoiceId='"+invoiceId+"'";
			String accountRatePlan = jdbcTemplate.queryForObject(ratePlanQry, String.class);
			
			String commTypeIdQry = "select driver_commision_type_id from AccountRatePlan where AccountRatePlanId='"+accountRatePlan+"'";
			String commissionTypeId = jdbcTemplate.queryForObject(commTypeIdQry, String.class);
			
			CommissionDetails commissionDetails = getCommissionDetails(commissionTypeId, driverLevel.substring(driverLevel.length()-1));
			
			float commPercentTowCharge= Float.parseFloat(commissionDetails.getCommPercentTowCharge()!=null?commissionDetails.getCommPercentTowCharge():"0.00");
			float commPercentLabourCharge= Float.parseFloat(commissionDetails.getCommPercentLabourCharge()!=null?commissionDetails.getCommPercentLabourCharge():"0.00");
			float commPercentStorageCharge = Float.parseFloat(commissionDetails.getCommPercentStorageCharge()!=null?commissionDetails.getCommPercentStorageCharge():"0.00");
			float commPercentMileageCharge = Float.parseFloat(commissionDetails.getCommPercentMileageCharge()!=null?commissionDetails.getCommPercentMileageCharge():"0.00");
			float commPercentMiscCharge = Float.parseFloat(commissionDetails.getCommPercentMiscCharge()!=null?commissionDetails.getCommPercentMiscCharge():"0.00");
			float commPercentWinchCharge = Float.parseFloat(commissionDetails.getCommPercentWinchCharge()!=null?commissionDetails.getCommPercentWinchCharge():"0.00");
			
			String towChargesQry = "select towCharge from Invoice_Billing where invoiceId='"+invoiceId+"'";
			float towCharges = jdbcTemplate.queryForObject(towChargesQry, Float.class)!=null ? jdbcTemplate.queryForObject(towChargesQry, Float.class) : 0.0f;
			logger.info("towCharges : "+towCharges);
			
			String laborChargesQry = "select laborCharge from Invoice_Billing where invoiceId='"+invoiceId+"'";
			float laborCharges = jdbcTemplate.queryForObject(laborChargesQry, Float.class)!=null ? jdbcTemplate.queryForObject(laborChargesQry, Float.class) : 0.0f;
			logger.info("laborCharges : "+laborCharges);
			
			String storageChargesQry = "select storageCharge from Invoice_Billing where invoiceId='"+invoiceId+"'";
			float storageCharges = jdbcTemplate.queryForObject(storageChargesQry, Float.class)!=null ? jdbcTemplate.queryForObject(storageChargesQry, Float.class) : 0.0f;
			logger.info("storageCharges : "+storageCharges);
			
			String mileageChargesQry = "select mileageCharge from Invoice_Billing where invoiceId='"+invoiceId+"'";
			float mileageCharges = jdbcTemplate.queryForObject(mileageChargesQry, Float.class)!=null ? jdbcTemplate.queryForObject(mileageChargesQry, Float.class) : 0.0f;
			logger.info("mileageCharges : "+mileageCharges);
			
			String miscChargesQry = "select miscCharge from Invoice_Billing where invoiceId='"+invoiceId+"'";
			float miscCharges = jdbcTemplate.queryForObject(miscChargesQry, Float.class)!=null ? jdbcTemplate.queryForObject(miscChargesQry, Float.class) : 0.0f;
			logger.info("miscCharges : "+miscCharges);
			
			String winchChargesQry = "select winch_Charge from Invoice_Billing where invoiceId='"+invoiceId+"'";
			float winchCharges = jdbcTemplate.queryForObject(winchChargesQry, Float.class)!=null ? jdbcTemplate.queryForObject(winchChargesQry, Float.class) : 0.0f;
			logger.info("winchCharges : "+winchCharges);
			
			if(commPercentTowCharge>0)				
				commissionAmount += (commPercentTowCharge / 100) * towCharges;
			
			if(commPercentLabourCharge>0)				
				commissionAmount += (commPercentLabourCharge / 100) * laborCharges;
			
			if(commPercentStorageCharge>0)				
				commissionAmount += (commPercentStorageCharge / 100) * storageCharges;
			
			if(commPercentMileageCharge>0)				
				commissionAmount += (commPercentMileageCharge / 100) * mileageCharges;
			
			if(commPercentMiscCharge>0)				
				commissionAmount += (commPercentMiscCharge / 100) * miscCharges;
			
			if(commPercentWinchCharge>0)				
				commissionAmount += (commPercentWinchCharge / 100) * winchCharges;
									
			logger.info("commissionAmount in getPercentageCommAmt() : "+commissionAmount);
		}
		catch(Exception e){
			logger.error(e);
		}
		
		return commissionAmount;
	}
	
	private  float getFlatRateCommAmt(String searchString, String userData, String invoiceId)
	{
		logger.info("In getFlatRateCommAmt()...");
		float commissionAmount = 0.0f;
		try{
			String lvlQry = "";			
			if(searchString.equalsIgnoreCase("byDriverId")){
				lvlQry = "select e.level from Employee e,UserAuth u where u.userId='"+userData+"' and e.isDriver='1' and e.idEmployee=u.Employee_idEmployee";				
			}
			else if(searchString.equalsIgnoreCase("byDriverName")){
				int index = userData.indexOf(" ");
				String fname = userData.substring(0, index);
				String lname = userData.substring(index+1);
				lvlQry = "select level from Employee where firstName='"+fname+"' and lastName='"+lname+"' and isDriver='1'";
			}
			String driverLevel = jdbcTemplate.queryForObject(lvlQry, String.class);
			
			String ratePlanQry = "select accountRatePlanId from Invoice where invoiceId='"+invoiceId+"'";
			String accountRatePlan = jdbcTemplate.queryForObject(ratePlanQry, String.class);
			
			String commTypeIdQry = "select driver_commision_type_id from AccountRatePlan where AccountRatePlanId='"+accountRatePlan+"'";
			String commissionTypeId = jdbcTemplate.queryForObject(commTypeIdQry, String.class);
			
			CommissionDetails commissionDetails = getCommissionDetails(commissionTypeId, driverLevel.substring(driverLevel.length()-1));
			
			float flatRateComm = Float.parseFloat(commissionDetails.getFlatRateComm());
			commissionAmount += flatRateComm;
			
			logger.info("commissionAmount in getFlatRateCommAmt() : "+commissionAmount);
		}
		catch(Exception e){
			logger.error(e);
		}
		
		return commissionAmount;
	}
	
	private  float getHourlyHoursOrInvoices(String searchString, String userData, String invoiceId)
	{
		logger.info("In getHourlyHoursOrInvoices()...");
		float hourlyHoursOrInvoices = 0.0f;
		try{
			String lvlQry = "";			
			if(searchString.equalsIgnoreCase("byDriverId")){
				lvlQry = "select e.level from Employee e,UserAuth u where u.userId='"+userData+"' and e.isDriver='1' and e.idEmployee=u.Employee_idEmployee";				
			}
			else if(searchString.equalsIgnoreCase("byDriverName")){
				int index = userData.indexOf(" ");
				String fname = userData.substring(0, index);
				String lname = userData.substring(index+1);
				lvlQry = "select level from Employee where firstName='"+fname+"' and lastName='"+lname+"' and isDriver='1'";
			}
			String driverLevel = jdbcTemplate.queryForObject(lvlQry, String.class);
			
			String ratePlanQry = "select accountRatePlanId from Invoice where invoiceId='"+invoiceId+"'";
			String accountRatePlan = jdbcTemplate.queryForObject(ratePlanQry, String.class);
			
			String commTypeIdQry = "select driver_commision_type_id from AccountRatePlan where AccountRatePlanId='"+accountRatePlan+"'";
			String commissionTypeId = jdbcTemplate.queryForObject(commTypeIdQry, String.class);
			
			CommissionDetails commissionDetails = getCommissionDetails(commissionTypeId, driverLevel.substring(driverLevel.length()-1));
			
			String commStartFromTime = getCommissionStratFromTime(invoiceId, commissionDetails.getCommStartFrom());
			String commEndByTime = getCommissionEndByTime(invoiceId, commissionDetails.getCommEndBy());
			String durBetweenCommStartEnd = getDurationBetweenTimes(commStartFromTime, commEndByTime);
			float timeBetweenCommStartEnd = 0.0f;
			if(!durBetweenCommStartEnd.equalsIgnoreCase(""))
				timeBetweenCommStartEnd = Float.parseFloat(durBetweenCommStartEnd);
			logger.info("timeBetweenCommStartEnd in getHourlyHoursOrInvoices = "+timeBetweenCommStartEnd);
						
			float hourlyComm = Float.parseFloat(commissionDetails.getHourlyComm()!=null?commissionDetails.getHourlyComm():"0.00");
			float hourlyCommMinHours = Float.parseFloat(commissionDetails.getHourlyCommMinHours()!=null?commissionDetails.getHourlyCommMinHours():"0.00");
			float hourlyCommIncrementHours = Float.parseFloat(commissionDetails.getHourlyCommIncrementHours()!=null?commissionDetails.getHourlyCommIncrementHours():"0.00");
			
			if(hourlyComm>0 && hourlyCommMinHours>0 && hourlyCommIncrementHours>0){
				if(hourlyCommMinHours > timeBetweenCommStartEnd)
				{
					hourlyHoursOrInvoices = hourlyCommMinHours;
				}
				else if(timeBetweenCommStartEnd > hourlyCommMinHours)
				{
					hourlyHoursOrInvoices = timeBetweenCommStartEnd;
				}
			}
			logger.info("hourlyHoursOrInvoices in getHourlyHoursOrInvoices() : "+hourlyHoursOrInvoices);
		}
		catch(Exception e){
			logger.error(e);
		}
		
		return hourlyHoursOrInvoices;
	}
	
	private  boolean isInvoicePaidByPercentageComm(String searchString, String userData, String invoiceId)
	{
		logger.info("In isInvoicePaidByPercentageComm()...");
		boolean isInvoicePaidByPercentageComm = false;
		try{
			String lvlQry = "";			
			if(searchString.equalsIgnoreCase("byDriverId")){
				lvlQry = "select e.level from Employee e,UserAuth u where u.userId='"+userData+"' and e.isDriver='1' and e.idEmployee=u.Employee_idEmployee";				
			}
			else if(searchString.equalsIgnoreCase("byDriverName")){
				int index = userData.indexOf(" ");
				String fname = userData.substring(0, index);
				String lname = userData.substring(index+1);
				lvlQry = "select level from Employee where firstName='"+fname+"' and lastName='"+lname+"' and isDriver='1'";
			}
			String driverLevel = jdbcTemplate.queryForObject(lvlQry, String.class);
			
			String ratePlanQry = "select accountRatePlanId from Invoice where invoiceId='"+invoiceId+"'";
			String accountRatePlan = jdbcTemplate.queryForObject(ratePlanQry, String.class);
			
			String commTypeIdQry = "select driver_commision_type_id from AccountRatePlan where AccountRatePlanId='"+accountRatePlan+"'";
			String commissionTypeId = jdbcTemplate.queryForObject(commTypeIdQry, String.class);
			
			CommissionDetails commissionDetails = getCommissionDetails(commissionTypeId, driverLevel.substring(driverLevel.length()-1));
			
			float commPercentTowCharge= Float.parseFloat(commissionDetails.getCommPercentTowCharge()!=null?commissionDetails.getCommPercentTowCharge():"0.00");
			float commPercentLabourCharge= Float.parseFloat(commissionDetails.getCommPercentLabourCharge()!=null?commissionDetails.getCommPercentLabourCharge():"0.00");
			float commPercentStorageCharge = Float.parseFloat(commissionDetails.getCommPercentStorageCharge()!=null?commissionDetails.getCommPercentStorageCharge():"0.00");
			float commPercentMileageCharge = Float.parseFloat(commissionDetails.getCommPercentMileageCharge()!=null?commissionDetails.getCommPercentMileageCharge():"0.00");
			float commPercentMiscCharge = Float.parseFloat(commissionDetails.getCommPercentMiscCharge()!=null?commissionDetails.getCommPercentMiscCharge():"0.00");
			float commPercentWinchCharge = Float.parseFloat(commissionDetails.getCommPercentWinchCharge()!=null?commissionDetails.getCommPercentWinchCharge():"0.00");
			
			if(commPercentTowCharge>0 || commPercentLabourCharge>0 || commPercentStorageCharge>0 || commPercentMileageCharge>0 || commPercentMiscCharge>0 || commPercentWinchCharge>0){
				isInvoicePaidByPercentageComm = true;
			}
									
			logger.info("isInvoicePaidByPercentageComm in isInvoicePaidByPercentageComm() : "+isInvoicePaidByPercentageComm);
		}
		catch(Exception e){
			logger.error(e);
		}
		
		return isInvoicePaidByPercentageComm;
	}
	
	private  boolean isInvoicePaidByFlatRateComm(String searchString, String userData, String invoiceId)
	{
		logger.info("In isInvoicePaidByFlatRateComm()...");
		boolean isInvoicePaidByFlatRateComm = false;
		try{
			String lvlQry = "";			
			if(searchString.equalsIgnoreCase("byDriverId")){
				lvlQry = "select e.level from Employee e,UserAuth u where u.userId='"+userData+"' and e.isDriver='1' and e.idEmployee=u.Employee_idEmployee";				
			}
			else if(searchString.equalsIgnoreCase("byDriverName")){
				int index = userData.indexOf(" ");
				String fname = userData.substring(0, index);
				String lname = userData.substring(index+1);
				lvlQry = "select level from Employee where firstName='"+fname+"' and lastName='"+lname+"' and isDriver='1'";
			}
			String driverLevel = jdbcTemplate.queryForObject(lvlQry, String.class);
			
			String ratePlanQry = "select accountRatePlanId from Invoice where invoiceId='"+invoiceId+"'";
			String accountRatePlan = jdbcTemplate.queryForObject(ratePlanQry, String.class);
			
			String commTypeIdQry = "select driver_commision_type_id from AccountRatePlan where AccountRatePlanId='"+accountRatePlan+"'";
			String commissionTypeId = jdbcTemplate.queryForObject(commTypeIdQry, String.class);
			
			CommissionDetails commissionDetails = getCommissionDetails(commissionTypeId, driverLevel.substring(driverLevel.length()-1));
			
			float flatRateComm = Float.parseFloat(commissionDetails.getFlatRateComm());
			if(flatRateComm>0)
				isInvoicePaidByFlatRateComm = true;
			
			logger.info("isInvoicePaidByFlatRateComm in isInvoicePaidByFlatRateComm() : "+isInvoicePaidByFlatRateComm);
		}
		catch(Exception e){
			logger.error(e);
		}
		
		return isInvoicePaidByFlatRateComm;
	}
	
	private String getDurationBetweenTimes(String fromTime, String toTime){
		int secsBetweenCommStartEnd = 0;
		int minsBetweenCommStartEnd = 0;
		String duration = "";
		int hours = 0;
		try{
			if(!fromTime.trim().isEmpty() && !toTime.trim().isEmpty()){
				String timeBetweenCommStartEndQry = "SELECT TIMESTAMPDIFF(SECOND,'"+fromTime+"','"+toTime+"')";
				secsBetweenCommStartEnd = Integer.parseInt(jdbcTemplate.queryForObject(timeBetweenCommStartEndQry, String.class));				
			}			
			//logger.info("secsBetweenCommStartEnd : "+secsBetweenCommStartEnd);
			
			if(secsBetweenCommStartEnd > 59)
				minsBetweenCommStartEnd = secsBetweenCommStartEnd / 60;
						
			if(minsBetweenCommStartEnd > 0 && minsBetweenCommStartEnd < 60)
				hours = 1;
			else if(minsBetweenCommStartEnd > 59)
				hours = minsBetweenCommStartEnd / 60;
			//logger.info("hours : "+hours);
	        String hoursToDisplay = Integer.toString(hours);
	        
	        if(hours >= 0){
		        if(hours == 0 ) hoursToDisplay = "00";     
		        else if( hours < 10 ) hoursToDisplay = "0" + hoursToDisplay;
		        else hoursToDisplay = "" + hoursToDisplay ;
		        //logger.info("hoursToDisplay : "+hoursToDisplay);
	        }
	        
	        String minsToDisplay = "00";
	        if(minsBetweenCommStartEnd > 59){
		        int minutesToDisplay = minsBetweenCommStartEnd - (hours * 60);
		        //logger.info("minutesToDisplay : "+minutesToDisplay);
		        minsToDisplay = Integer.toString(minutesToDisplay);;
		        if(minutesToDisplay == 0 ) minsToDisplay = "00";     
		        else if( minutesToDisplay < 10 ) minsToDisplay = "0" + minsToDisplay ;
		        else minsToDisplay = "" + minsToDisplay ;		        
	        }
	        //logger.info("minsToDisplay : "+minsToDisplay);
	        duration = hoursToDisplay + "." + minsToDisplay;	        
		}
		catch(Exception e){
			logger.error(e);
		}
		return duration;
	}
	
	private  CommissionDetails getCommissionDetails(String commissionTypeId, String driverLevel)
	{
		logger.info("In getCommissionDetails()...");
		CommissionDetails commissionDetails = new CommissionDetails();
		try{
			String commDataQry = "select min_commission_per_call,flat_rate_comm,hourly_comm,hourly_comm_min_hours,hourly_comm_increment_hours,comm_start_from,comm_end_by," +
					 "comm_percent_all_charges,comm_percent_tow_charge,comm_percent_labour_charge,comm_percent_storage_charge,comm_percent_mileage_charge," +
					 "comm_percent_misc_charges,comm_percent_winch_charge from Commission where commissionTypeId='"+commissionTypeId+"' and driverLevelId='"+driverLevel+"'";

			commissionDetails = jdbcTemplate.queryForObject(commDataQry, new Object[] {}, new RowMapper<CommissionDetails>() {
			
				public CommissionDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
			
					CommissionDetails commissionDetails = new CommissionDetails();
					commissionDetails.setMinCommPerCall(rs.getString("min_commission_per_call")!=null ? rs.getString("min_commission_per_call") : "0.00");
					commissionDetails.setFlatRateComm(rs.getString("flat_rate_comm")!=null ? rs.getString("flat_rate_comm") : "0.00");
					commissionDetails.setHourlyComm(rs.getString("hourly_comm")!=null ? rs.getString("hourly_comm") : "0.00");
					commissionDetails.setHourlyCommMinHours(rs.getString("hourly_comm_min_hours")!=null ? rs.getString("hourly_comm_min_hours") : "0.00");
					commissionDetails.setHourlyCommIncrementHours(rs.getString("hourly_comm_increment_hours")!=null ? rs.getString("hourly_comm_increment_hours") : "0.0");
					commissionDetails.setCommStartFrom(rs.getString("comm_start_from")!=null ? rs.getString("comm_start_from") : "");
					commissionDetails.setCommEndBy(rs.getString("comm_end_by")!=null ? rs.getString("comm_end_by") : "");
					commissionDetails.setCommPercentAllCharges(rs.getString("comm_percent_all_charges")!=null ? rs.getString("comm_percent_all_charges") : "0.00");
					commissionDetails.setCommPercentTowCharge(rs.getString("comm_percent_tow_charge")!=null ? rs.getString("comm_percent_tow_charge") : "0.00");
					commissionDetails.setCommPercentLabourCharge(rs.getString("comm_percent_labour_charge")!=null ? rs.getString("comm_percent_labour_charge") : "0.00");
					commissionDetails.setCommPercentStorageCharge(rs.getString("comm_percent_storage_charge")!=null ? rs.getString("comm_percent_storage_charge") : "0.00");
					commissionDetails.setCommPercentMileageCharge(rs.getString("comm_percent_mileage_charge")!=null ? rs.getString("comm_percent_mileage_charge") : "0.00");
					commissionDetails.setCommPercentMiscCharge(rs.getString("comm_percent_misc_charges")!=null ? rs.getString("comm_percent_misc_charges") : "0.00");
					commissionDetails.setCommPercentWinchCharge(rs.getString("comm_percent_winch_charge")!=null ? rs.getString("comm_percent_winch_charge") : "0.00");
					return commissionDetails;
				}
			});
		}
		catch(Exception e){
			logger.error(e);
		}
		return commissionDetails;
	}
	
	private String getCommissionStratFromTime(String invoiceId, String commStartFrom){
		String commStartFromQry = "";
		String commStartFromTime = "";
		try{
			if(commStartFrom.equalsIgnoreCase("Requested")){
				commStartFromQry = "select requestTime from Invoice where invoiceId='"+invoiceId+"'";
				commStartFromTime = jdbcTemplate.queryForObject(commStartFromQry, String.class)!=null ? jdbcTemplate.queryForObject(commStartFromQry, String.class) : "";				
			}
			else if(commStartFrom.equalsIgnoreCase("Dispatch")){
				commStartFromQry = "select dispatchTime from Invoice where invoiceId='"+invoiceId+"'";
				commStartFromTime = jdbcTemplate.queryForObject(commStartFromQry, String.class)!=null ? jdbcTemplate.queryForObject(commStartFromQry, String.class) : "";				
			}
			else if(commStartFrom.equalsIgnoreCase("Accepted")){
				commStartFromQry = "select acceptedTime from Invoice where invoiceId='"+invoiceId+"'";
				commStartFromTime = jdbcTemplate.queryForObject(commStartFromQry, String.class)!=null ? jdbcTemplate.queryForObject(commStartFromQry, String.class) : "";				
			}
			else if(commStartFrom.equalsIgnoreCase("Enroute")){
				commStartFromQry = "select enrouteTime from Invoice where invoiceId='"+invoiceId+"'";
				commStartFromTime = jdbcTemplate.queryForObject(commStartFromQry, String.class)!=null ? jdbcTemplate.queryForObject(commStartFromQry, String.class) : "";				
			}
			//logger.info("commStartFromTime = "+commStartFromTime);
		}
		catch (Exception e) {
			logger.error(e);
		}
		return commStartFromTime;
	}
	
	private String getCommissionEndByTime(String invoiceId, String commEndBy){
		String commEndByQry = "";
		String commEndByTime = "";
		try{			
			if(commEndBy.equalsIgnoreCase("Arrived")){
				commEndByQry = "select arrivedTime from Invoice where invoiceId='"+invoiceId+"'";
				commEndByTime = jdbcTemplate.queryForObject(commEndByQry, String.class)!=null ? jdbcTemplate.queryForObject(commEndByQry, String.class) : "";				
			}
			else if(commEndBy.equalsIgnoreCase("Hooked")){
				commEndByQry = "select hookedTime from Invoice where invoiceId='"+invoiceId+"'";
				commEndByTime = jdbcTemplate.queryForObject(commEndByQry, String.class)!=null ? jdbcTemplate.queryForObject(commEndByQry, String.class) : "";				
			}
			else if(commEndBy.equalsIgnoreCase("Dropped")){
				commEndByQry = "select dropOffTime from Invoice where invoiceId='"+invoiceId+"'";
				commEndByTime = jdbcTemplate.queryForObject(commEndByQry, String.class)!=null ? jdbcTemplate.queryForObject(commEndByQry, String.class) : "";				
			}
			else if(commEndBy.equalsIgnoreCase("Cleared")){
				commEndByQry = "select clearedTime from Invoice where invoiceId='"+invoiceId+"'";
				commEndByTime = jdbcTemplate.queryForObject(commEndByQry, String.class)!=null ? jdbcTemplate.queryForObject(commEndByQry, String.class) : "";				
			}
			//logger.info("commEndByTime = "+commEndByTime);
		}
		catch (Exception e) {
			logger.error(e);
		}
		return commEndByTime;
	}
	
	public String roundUp(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.toString();
    }
}