package act.reports.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import act.reports.model.CommissionDetails;
import act.reports.model.SearchCriteria;
import act.reports.model.SingleDriverCommission;
import act.reports.model.SingleDriverCommissionDetails;
import act.reports.model.TowTypeSummaryDetails;
import act.reports.model.UserIds;
import act.reports.model.UserNames;
import act.reports.util.DateUtility;

@Repository("singleDriverCommissionDAO")
public class SingleDriverCommissionDAO {

	private Logger logger=Logger.getLogger(SingleDriverCommissionDAO.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public SingleDriverCommission getSingleDriverCommissionDetails(SearchCriteria criteria){
		logger.info("In SingleDriverCommissionDAO-getSingleDriverCommissionDetails()...");
		List<SingleDriverCommissionDetails> singleDriverCommissionDetailsList = null;
		List<TowTypeSummaryDetails> towTypeSummaryDetailsList = null;
		SingleDriverCommission singleDriverCommission = new SingleDriverCommission();

		try{
			String fromDate=criteria.getFromDate();
			String toDate=criteria.getToDate();
			fromDate = DateUtility.convertAsMySqlDateTime(fromDate);
			logger.info("fromDate in SingleDriverCommissionDAO-getDriverCommissionDetails() : "+fromDate);
			toDate = DateUtility.convertAsMySqlDateTime(toDate);
			toDate = toDate.replace("00:00:00", "23:59:59");
			logger.info("toDate in SingleDriverCommissionDAO-getDriverCommissionDetails() : "+toDate);

			String userId="";
			String userName="";
			String driverCommissionQuery="";
			String towTypeSummaryQuery=""; 
			String userDetails = "";
			final String searchString = criteria.getSearchString();
			
			if(criteria.getSearchString().equalsIgnoreCase("byUserId")){
				userId=criteria.getUserId();
				logger.info("userId in SingleDriverCommissionDAO-getDriverCommissionDetails() : "+userId);
				userDetails = userId;
				driverCommissionQuery = "select distinct i.invoiceId,i.towType,i.invoiceCreatedDate,ib.storageCharge,ib.totalCharge from Invoice i,Invoice_Billing ib,"+ 
										"UserAuth u,Employee e where u.userId='"+userId+"' and i.invoiceCreatedDate BETWEEN '"+fromDate+"' and '"+toDate+"' and"+
										" i.callStatus='Cleared' and e.idEmployee=u.Employee_idEmployee and i.driverId=e.idEmployee and i.invoiceId=ib.invoiceId";

				towTypeSummaryQuery = "select i.towType,count(i.towType) as total,SUM(ib.totalCharge) as charges,SUM(ib.storageCharge) as storage from Invoice i,"+ 
									  "Invoice_Billing ib,UserAuth u,Employee e where u.userId='"+userId+"' and i.invoiceCreatedDate BETWEEN '"+fromDate+"' and '"+toDate+"' and "+
									  "i.callStatus='Cleared' and e.idEmployee=u.Employee_idEmployee and i.driverId=e.idEmployee and i.invoiceId=ib.invoiceId group by i.towType";
				
				singleDriverCommission.setDriverId(userId);				
				String userFirstNameQry = "select e.firstName from Employee e,UserAuth u where u.userId='"+userId+"' and e.isDriver='1' and e.idEmployee=u.Employee_idEmployee";
				String firstName = jdbcTemplate.queryForObject(userFirstNameQry, String.class)!=null ? jdbcTemplate.queryForObject(userFirstNameQry, String.class) : "";				
				String userLastNameQry = "select e.lastName from Employee e,UserAuth u where u.userId='"+userId+"' and e.isDriver='1' and e.idEmployee=u.Employee_idEmployee";
				String lastName = jdbcTemplate.queryForObject(userLastNameQry, String.class)!=null ? jdbcTemplate.queryForObject(userLastNameQry, String.class) : "";				
				singleDriverCommission.setDriverName(firstName+" "+lastName);				
			}
			else if(criteria.getSearchString().equalsIgnoreCase("byUserName")){
				userName = criteria.getUserName();
				int index = userName.indexOf(" ");
				String fname = userName.substring(0, index);
				String lname = userName.substring(index+1);
				logger.info("userName in SingleDriverCommissionDAO-getDriverCommissionDetails() : "+userName);
				userDetails = userName;
				driverCommissionQuery = "select distinct i.invoiceId,i.towType,i.invoiceCreatedDate,ib.storageCharge,ib.totalCharge from Invoice i,Invoice_Billing ib,"+ 
										"UserAuth u,Employee e where e.firstName='"+fname+"' and e.lastName='"+lname+"' and i.callStatus='Cleared' and i.invoiceCreatedDate BETWEEN "+
										"'"+fromDate+"' and '"+toDate+"' and e.idEmployee=u.Employee_idEmployee and i.driverId=e.idEmployee and i.invoiceId=ib.invoiceId";

				towTypeSummaryQuery = "select i.towType,count(i.towType) as total,SUM(ib.totalCharge) as charges,SUM(ib.storageCharge) as storage from Invoice i,Invoice_Billing ib,"+ 
									  "UserAuth u,Employee e where e.firstName='"+fname+"' and e.lastName='"+lname+"' and i.callStatus='Cleared' and i.invoiceCreatedDate BETWEEN "+
									  "'"+fromDate+"' and '"+toDate+"' and e.idEmployee=u.Employee_idEmployee and i.driverId=e.idEmployee and i.invoiceId=ib.invoiceId group by i.towType";
				
				singleDriverCommission.setDriverName(userName);
				String userIdQry = "select u.userId from Employee e,UserAuth u where e.firstName='"+fname+"' and e.lastName='"+lname+"' and e.isDriver='1' and e.idEmployee=u.Employee_idEmployee";
				userId = jdbcTemplate.queryForObject(userIdQry, String.class)!=null ? jdbcTemplate.queryForObject(userIdQry, String.class) : "";
				singleDriverCommission.setDriverId(userId);
			}			 
			
			final String userData = userDetails;
			//logger.info("userData in SingleDriverCommissionDAO-getDriverCommissionDetails() : "+userData);
						
			singleDriverCommissionDetailsList = jdbcTemplate.query(driverCommissionQuery, new Object[] {}, new RowMapper<SingleDriverCommissionDetails>() {

				public SingleDriverCommissionDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

					SingleDriverCommissionDetails driverCommissionDetails = new SingleDriverCommissionDetails();
					String invoiceId = (rs.getString("invoiceId") != null) ? rs.getString("invoiceId") : " ";
					driverCommissionDetails.setInvoiceId(invoiceId);
					driverCommissionDetails.setTowType((rs.getString("towType") != null) ? rs.getString("towType") : " ");
					
					String invoiceCreatedDate = (rs.getString("invoiceCreatedDate") != null) ? rs.getString("invoiceCreatedDate") : " ";
					if(invoiceCreatedDate.trim().length()>10){
						invoiceCreatedDate=DateUtility.convertToDateTimeFormat(invoiceCreatedDate);
						invoiceCreatedDate=invoiceCreatedDate.substring(0, 10);
					}
					driverCommissionDetails.setDate(invoiceCreatedDate);
					driverCommissionDetails.setTotalCharge((rs.getString("totalCharge") != null) ? rs.getString("totalCharge") : "0.00");
					driverCommissionDetails.setStorageCharge((rs.getString("storageCharge") != null) ? rs.getString("storageCharge") : "0.00");
					float commissionAmount = calculateCommissionAmount(searchString, userData, invoiceId);
					driverCommissionDetails.setCommissionAmount(roundUp(commissionAmount, 2));
					return driverCommissionDetails;
				}
			});

			towTypeSummaryDetailsList = jdbcTemplate.query(towTypeSummaryQuery, new Object[] {}, new RowMapper<TowTypeSummaryDetails>() {

				public TowTypeSummaryDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

					TowTypeSummaryDetails towTypeSummaryDetails = new TowTypeSummaryDetails();					
					towTypeSummaryDetails.setTowTypeSummary((rs.getString("towType") != null) ? rs.getString("towType") : "");
					towTypeSummaryDetails.setTotal((rs.getString("total") != null) ? rs.getString("total") : "0.00");
					towTypeSummaryDetails.setCharges((rs.getString("charges") != null) ? rs.getString("charges") : "0.00");
					towTypeSummaryDetails.setStorage((rs.getString("storage") != null) ? rs.getString("storage") : "0.00");
					towTypeSummaryDetails.setCommission("0.00");
					return towTypeSummaryDetails;
				}
			});
			
			float totalCharges = 0.0f;
			float totalCommission = 0.0f;
			float totalLightDutyComm = 0.0f;
			float totalMedLightDutyComm = 0.0f;
			float totalMedDutyComm = 0.0f;
			float totalMedHeavyDutyComm = 0.0f;
			float totalHeavyDutyComm = 0.0f;
			for(int i=0;i<singleDriverCommissionDetailsList.size();i++){
				totalCharges += Float.parseFloat(singleDriverCommissionDetailsList.get(i).getTotalCharge());
				totalCommission += Float.parseFloat(singleDriverCommissionDetailsList.get(i).getCommissionAmount());	
				if(singleDriverCommissionDetailsList.get(i).getTowType().equalsIgnoreCase("Light Duty"))
					totalLightDutyComm += Float.parseFloat(singleDriverCommissionDetailsList.get(i).getCommissionAmount());
				else if(singleDriverCommissionDetailsList.get(i).getTowType().equalsIgnoreCase("Medium Light Duty"))
					totalMedLightDutyComm += Float.parseFloat(singleDriverCommissionDetailsList.get(i).getCommissionAmount());
				else if(singleDriverCommissionDetailsList.get(i).getTowType().equalsIgnoreCase("Medium Duty"))
					totalMedDutyComm += Float.parseFloat(singleDriverCommissionDetailsList.get(i).getCommissionAmount());
				else if(singleDriverCommissionDetailsList.get(i).getTowType().equalsIgnoreCase("Medium Heavy Duty"))
					totalMedHeavyDutyComm += Float.parseFloat(singleDriverCommissionDetailsList.get(i).getCommissionAmount());
				else if(singleDriverCommissionDetailsList.get(i).getTowType().equalsIgnoreCase("Heavy Duty"))
					totalHeavyDutyComm += Float.parseFloat(singleDriverCommissionDetailsList.get(i).getCommissionAmount());
			}
			logger.info("totalCharges : "+totalCharges);
			logger.info("totalCommission : "+totalCommission);			
			
			for(int j=0;j<towTypeSummaryDetailsList.size();j++){
				String towType = towTypeSummaryDetailsList.get(j).getTowTypeSummary();
				if(towType.equalsIgnoreCase("Light Duty"))
					towTypeSummaryDetailsList.get(j).setCommission(roundUp(totalLightDutyComm, 2));
				else if(towType.equalsIgnoreCase("Medium Light Duty"))
					towTypeSummaryDetailsList.get(j).setCommission(roundUp(totalMedLightDutyComm, 2));
				else if(towType.equalsIgnoreCase("Medium Duty"))
					towTypeSummaryDetailsList.get(j).setCommission(roundUp(totalMedDutyComm, 2));
				else if(towType.equalsIgnoreCase("Medium Heavy Duty"))
					towTypeSummaryDetailsList.get(j).setCommission(roundUp(totalMedHeavyDutyComm, 2));
				else if(towType.equalsIgnoreCase("Heavy Duty"))
					towTypeSummaryDetailsList.get(j).setCommission(roundUp(totalHeavyDutyComm, 2));
			}
			
			singleDriverCommission.setFromDate(fromDate);
			singleDriverCommission.setToDate(toDate);
			singleDriverCommission.setTotalInvoices(Integer.toString(singleDriverCommissionDetailsList.size()));
			singleDriverCommission.setTotalCharges(roundUp(totalCharges, 2));
			singleDriverCommission.setTotalCommission(roundUp(totalCommission, 2));
			singleDriverCommission.setSingleDriverCommissionDetails(singleDriverCommissionDetailsList);
			singleDriverCommission.setTowTypeSummaryDetails(towTypeSummaryDetailsList);

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}

		return singleDriverCommission;
	}

	private  float calculateCommissionAmount(String searchString, String userData, String invoiceId)
	{
		logger.info("In calculateCommissionAmount()...");
		float commissionAmount = 0.0f;
		try{
			String lvlQry = "";			
			if(searchString.equalsIgnoreCase("byUserId")){
				//lvlQry = "select level from Employee where idEmployee='"+userData+"' and isDriver='1'";
				lvlQry = "select e.level from Employee e,UserAuth u where u.userId='"+userData+"' and e.isDriver='1' and e.idEmployee=u.Employee_idEmployee";				
			}
			else if(searchString.equalsIgnoreCase("byUserName")){
				int index = userData.indexOf(" ");
				String fname = userData.substring(0, index);
				String lname = userData.substring(index+1);
				lvlQry = "select level from Employee where firstName='"+fname+"' and lastName='"+lname+"' and isDriver='1'";
			}
			String driverLevel = jdbcTemplate.queryForObject(lvlQry, String.class);
			//logger.info("driverLevel = "+driverLevel);
			
			String ratePlanQry = "select accountRatePlanId from Invoice where invoiceId='"+invoiceId+"'";
			String accountRatePlan = jdbcTemplate.queryForObject(ratePlanQry, String.class);
			//logger.info("accountRatePlan = "+accountRatePlan);
			
			String commTypeIdQry = "select driver_commision_type_id from AccountRatePlan where AccountRatePlanId='"+accountRatePlan+"'";
			String commissionTypeId = jdbcTemplate.queryForObject(commTypeIdQry, String.class);
			//logger.info("commissionTypeId = "+commissionTypeId);
			
			CommissionDetails commissionDetails = getCommissionDetails(commissionTypeId, driverLevel.substring(driverLevel.length()-1));
			
			String commStartFromTime = getCommissionStratFromTime(invoiceId, commissionDetails.getCommStartFrom());
			String commEndByTime = getCommissionEndByTime(invoiceId, commissionDetails.getCommEndBy());
			String durBetweenCommStartEnd = getDurationBetweenTimes(commStartFromTime, commEndByTime);
			float timeBetweenCommStartEnd = 0.0f;
			if(!durBetweenCommStartEnd.equalsIgnoreCase(""))
				timeBetweenCommStartEnd = Float.parseFloat(durBetweenCommStartEnd);
			//logger.info("timeBetweenCommStartEnd = "+timeBetweenCommStartEnd);
						
			float minCommPerCall = Float.parseFloat(commissionDetails.getMinCommPerCall());
			float flatRateComm = Float.parseFloat(commissionDetails.getFlatRateComm());
			float hourlyComm = Float.parseFloat(commissionDetails.getHourlyComm()!=null?commissionDetails.getHourlyComm():"0.00");
			float hourlyCommMinHours = Float.parseFloat(commissionDetails.getHourlyCommMinHours()!=null?commissionDetails.getHourlyCommMinHours():"0.00");
			float hourlyCommIncrementHours = Float.parseFloat(commissionDetails.getHourlyCommIncrementHours()!=null?commissionDetails.getHourlyCommIncrementHours():"0.00");
			//float commPercentAllCharges= Float.parseFloat(commissionDetails.getCommPercentAllCharges()!=null?commissionDetails.getCommPercentAllCharges():"0.00");
			float commPercentTowCharge= Float.parseFloat(commissionDetails.getCommPercentTowCharge()!=null?commissionDetails.getCommPercentTowCharge():"0.00");
			float commPercentLabourCharge= Float.parseFloat(commissionDetails.getCommPercentLabourCharge()!=null?commissionDetails.getCommPercentLabourCharge():"0.00");
			float commPercentStorageCharge = Float.parseFloat(commissionDetails.getCommPercentStorageCharge()!=null?commissionDetails.getCommPercentStorageCharge():"0.00");
			float commPercentMileageCharge = Float.parseFloat(commissionDetails.getCommPercentMileageCharge()!=null?commissionDetails.getCommPercentMileageCharge():"0.00");
			float commPercentMiscCharge = Float.parseFloat(commissionDetails.getCommPercentMiscCharge()!=null?commissionDetails.getCommPercentMiscCharge():"0.00");
			float commPercentWinchCharge = Float.parseFloat(commissionDetails.getCommPercentWinchCharge()!=null?commissionDetails.getCommPercentWinchCharge():"0.00");
			
			String totalChargesQry = "select totalCharge from Invoice_Billing where invoiceId='"+invoiceId+"'";
			float totalCharges = jdbcTemplate.queryForObject(totalChargesQry, Float.class)!=null ? jdbcTemplate.queryForObject(totalChargesQry, Float.class) : 0.0f;
			logger.info("totalCharges : "+totalCharges);
			
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
			
			/*if(commPercentAllCharges>0)
				commissionAmount += (commPercentAllCharges / 100) * totalCharges;*/
			
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
									
			//if(timeBetweenCommStartEnd > 0){
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
			//}
				
			commissionAmount += flatRateComm;
			
			if(commissionAmount < minCommPerCall)
				commissionAmount = minCommPerCall;
			
			logger.info("commissionAmount : "+commissionAmount);
		}
		catch(Exception e){
			logger.error(e);
		}
		
		return commissionAmount;
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
		
	public List<UserIds> getUserIds()
	{
		logger.info("In getUserIds()...");
		String userIdsQry = "select u.userId from UserAuth u,Employee e where e.isDriver='1' and e.idEmployee=u.Employee_idEmployee";    
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
		//logger.info("userIdsList.size() : "+userIdsList.size());
		return userIdsList;
	}
	
	public List<UserNames> getUserNames()
	{
		logger.info("In getUserNames()...");
		String userNamesQry = "select firstName,lastName from Employee where isDriver='1'";    
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
		//logger.info("userNamesList.size() : "+userNamesList.size());
		return userNamesList;
	}
	
	public String roundUp(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.toString();
    }
}
