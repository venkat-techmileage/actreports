package act.reports.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import act.reports.model.CommissionDetails;
import act.reports.model.DriverCommissionSummary;
import act.reports.model.DriverCommissionSummaryDetails;
import act.reports.model.SearchCriteria;
import act.reports.model.SingleDriverCommissionDetails;
import act.reports.util.DateUtility;

@Repository("driverCommissionSummaryDAO")
public class DriverCommissionSummaryDAO {

	private Logger logger=Logger.getLogger(DriverCommissionSummaryDAO.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public DriverCommissionSummary getDriverCommissionDetails(SearchCriteria criteria){
		logger.info("In DriverCommissionSummaryDAO-getDriverCommissionDetails()...");
		List<DriverCommissionSummaryDetails> driverCommissionSummaryDetailsList = null;
		DriverCommissionSummary driverCommissionSummary = new DriverCommissionSummary();

		try{
			//String fromDate=criteria.getFromDate();
			//String toDate=criteria.getToDate();
			final String fromDate = DateUtility.convertAsMySqlDateTime(criteria.getFromDate());
			logger.info("fromDate in DriverCommissionSummaryDAO-getDriverCommissionDetails() : "+fromDate);
			String toDate = DateUtility.convertAsMySqlDateTime(criteria.getToDate());
			toDate = toDate.replace("00:00:00", "23:59:59");
			final String toDate1 = toDate;
			logger.info("toDate in DriverCommissionSummaryDAO-getDriverCommissionDetails() : "+toDate);

			String driverCommissionQuery = "select distinct u.userId,e.employeeNo,e.firstName,e.lastName,e.DOJ,e.level,count(i.towType) as noOfTows,e.weekly_salary,e.daily_salary from Invoice i,"+ 
										   "Invoice_Billing ib,UserAuth u,Employee e where i.invoiceCreatedDate BETWEEN '"+fromDate+"' and '"+toDate+"' and i.callStatus='Cleared' "+
										   "and e.idEmployee=u.Employee_idEmployee and e.isDriver='1' and i.driverId=e.idEmployee and i.invoiceId=ib.invoiceId group by u.userId";

			driverCommissionSummaryDetailsList = jdbcTemplate.query(driverCommissionQuery, new Object[] {}, new RowMapper<DriverCommissionSummaryDetails>() {

				public DriverCommissionSummaryDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

					DriverCommissionSummaryDetails driverCommissionSummaryDetails = new DriverCommissionSummaryDetails();					

					driverCommissionSummaryDetails.setPayrollId((rs.getString("employeeNo") != null) ? rs.getString("employeeNo") : "");
					String userId = (rs.getString("userId") != null) ? rs.getString("userId") : "";
					logger.info("userId in DriverCommissionSummaryDAO-getDriverCommissionDetails() : "+userId);
					driverCommissionSummaryDetails.setUserId(userId);
					String fullName = ((rs.getString("firstName") != null) ? rs.getString("firstName") : "") +" "+ ((rs.getString("lastName") != null) ? rs.getString("lastName") : "");
					driverCommissionSummaryDetails.setFullName(fullName);
					
					String startDate=rs.getString("DOJ") != null ? rs.getString("DOJ") : "";
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
					Date parsedDate = null;
					try{
						if(!startDate.trim().isEmpty()){
							parsedDate = sdf.parse(startDate);
							startDate = sdf1.format(parsedDate);
						}
					} catch (ParseException e) {
						logger.error(e);
						e.printStackTrace();
					}
					
					driverCommissionSummaryDetails.setStartDate(startDate);
					driverCommissionSummaryDetails.setLevel((rs.getString("level") != null) ? rs.getString("level") : "");
					driverCommissionSummaryDetails.setNoOfTows((rs.getString("noOfTows") != null) ? rs.getString("noOfTows") : "0");
					String weeklyBase = (rs.getString("weekly_salary") != null) ? rs.getString("weekly_salary") : "0.00";
					if(Float.parseFloat(weeklyBase) > 0)
						weeklyBase = weeklyBase.replaceFirst("^0+(?!$)", "");
					else if(weeklyBase.equalsIgnoreCase("0000000.00"))
						weeklyBase = "0.00";
					driverCommissionSummaryDetails.setWeeklyBase(weeklyBase);
					String dailyBase = (rs.getString("daily_salary") != null) ? rs.getString("daily_salary") : "0.00";
					float commission = getDriverCommission(userId, fromDate, toDate1);
					logger.info("commission in DriverCommissionSummaryDAO-getDriverCommissionDetails() : "+commission);
					driverCommissionSummaryDetails.setCommission(roundUp(commission, 2));
					float commissionAdj = getCommissionAdj(userId, fromDate, toDate1, weeklyBase, dailyBase, commission);
					driverCommissionSummaryDetails.setCommissionAdj(roundUp(commissionAdj, 2));

					//Float totalPay = (Float.parseFloat(driverCommissionSummaryDetails.getWeeklyBase()) + Float.parseFloat(driverCommissionSummaryDetails.getCommission())) - Float.parseFloat(driverCommissionSummaryDetails.getCommissionAdj());   
					Float totalPay = Float.parseFloat(driverCommissionSummaryDetails.getCommission()) + Float.parseFloat(driverCommissionSummaryDetails.getCommissionAdj());
					driverCommissionSummaryDetails.setTotalPay(roundUp(totalPay, 2));

					return driverCommissionSummaryDetails;
				}
			});

			int countOfTows = 0;
			Float weeklyBaseTotal = 0.0f;
			Float totalCommission = 0.0f;
			Float totalCommissionAdj = 0.0f;
			float sumOfTotalPay = 0.0f;
			for(int i=0;i<driverCommissionSummaryDetailsList.size();i++){
				countOfTows += Integer.parseInt(driverCommissionSummaryDetailsList.get(i).getNoOfTows());
				weeklyBaseTotal += Float.parseFloat(driverCommissionSummaryDetailsList.get(i).getWeeklyBase());
				totalCommission += Float.parseFloat(driverCommissionSummaryDetailsList.get(i).getCommission());
				totalCommissionAdj += Float.parseFloat(driverCommissionSummaryDetailsList.get(i).getCommissionAdj());
				sumOfTotalPay += Float.parseFloat(driverCommissionSummaryDetailsList.get(i).getTotalPay());
			}

			logger.info("countOfTows : "+countOfTows);
			logger.info("weeklyBaseTotal : "+weeklyBaseTotal);
			logger.info("totalCommission : "+totalCommission);
			logger.info("totalCommissionAdj : "+totalCommissionAdj);
			logger.info("sumOfTotalPay : "+sumOfTotalPay);

			driverCommissionSummary.setFromDate(fromDate);
			driverCommissionSummary.setToDate(toDate);
			driverCommissionSummary.setCountOfTows(Integer.toString(countOfTows));
			driverCommissionSummary.setWeeklyBaseTotal(roundUp(weeklyBaseTotal, 2));
			driverCommissionSummary.setTotalCommission(roundUp(totalCommission, 2));
			driverCommissionSummary.setTotalCommissionAdj(roundUp(totalCommissionAdj, 2));
			driverCommissionSummary.setSumOfTotalPay(roundUp(sumOfTotalPay, 2));
			driverCommissionSummary.setDriverCommissionSummaryDetailsList(driverCommissionSummaryDetailsList);			

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}

		return driverCommissionSummary;
	}

	private  float getDriverCommission(String userId, String fromDate, String toDate)
	{
		logger.info("In getDriverCommission()...");
		float commissionAmount = 0.0f;
		List<SingleDriverCommissionDetails> singleDriverCommissionDetailsList = null;
		try{
			final String userID = userId;
			String driverCommissionQry = "select distinct i.invoiceId from Invoice i,Invoice_Billing ib,UserAuth u,Employee e where u.userId='"+userId+"' and  i.invoiceCreatedDate BETWEEN '"+fromDate+"' " +
										 "and '"+toDate+"' and i.callStatus='Cleared' and e.idEmployee=u.Employee_idEmployee and e.isDriver='1' and i.driverId=e.idEmployee and i.invoiceId=ib.invoiceId";

			singleDriverCommissionDetailsList = jdbcTemplate.query(driverCommissionQry, new Object[] {}, new RowMapper<SingleDriverCommissionDetails>() {

				public SingleDriverCommissionDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

					SingleDriverCommissionDetails driverCommissionDetails = new SingleDriverCommissionDetails();
					String invoiceId = (rs.getString("invoiceId") != null) ? rs.getString("invoiceId") : "";
					driverCommissionDetails.setInvoiceId(invoiceId);
					float commissionAmount = calculateCommissionAmount(userID, invoiceId);
					driverCommissionDetails.setCommissionAmount(roundUp(commissionAmount, 2));
					return driverCommissionDetails;
				}
			});

			for(int i=0;i<singleDriverCommissionDetailsList.size();i++){
				commissionAmount += Float.parseFloat(singleDriverCommissionDetailsList.get(i).getCommissionAmount());
			}

		}
		catch(Exception e){
			logger.error(e);
		}

		return commissionAmount;
	}

	private  float calculateCommissionAmount(String userId, String invoiceId)
	{
		logger.info("In calculateCommissionAmount()...");
		float commissionAmount = 0.0f;
		try{
			String lvlQry = "select e.level from Employee e,UserAuth u where u.userId='"+userId+"' and e.isDriver='1' and e.idEmployee=u.Employee_idEmployee";				
			String driverLevel = jdbcTemplate.queryForObject(lvlQry, String.class);
			logger.info("driverLevel = "+driverLevel);

			String ratePlanQry = "select accountRatePlanId from Invoice where invoiceId='"+invoiceId+"'";
			String accountRatePlan = jdbcTemplate.queryForObject(ratePlanQry, String.class);
			//logger.info("accountRatePlan = "+accountRatePlan);

			String commTypeIdQry = "select driver_commision_type_id from AccountRatePlan where AccountRatePlanId='"+accountRatePlan+"'";
			String commissionTypeId = jdbcTemplate.queryForObject(commTypeIdQry, String.class);
			logger.info("commissionTypeId = "+commissionTypeId);

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
			float hourlyComm = Float.parseFloat(commissionDetails.getHourlyComm());
			float hourlyCommMinHours = Float.parseFloat(commissionDetails.getHourlyCommMinHours());
			float hourlyCommIncrementHours = Float.parseFloat(commissionDetails.getHourlyCommIncrementHours());
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
			logger.info("secsBetweenCommStartEnd : "+secsBetweenCommStartEnd);

			if(secsBetweenCommStartEnd > 59)
				minsBetweenCommStartEnd = secsBetweenCommStartEnd / 60;

			if(minsBetweenCommStartEnd > 0 && minsBetweenCommStartEnd < 60)
				hours = 1;
			else if(minsBetweenCommStartEnd > 59)
				hours = minsBetweenCommStartEnd / 60;
			logger.info("hours : "+hours);
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
			logger.info("commStartFromTime = "+commStartFromTime);
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
			logger.info("commEndByTime = "+commEndByTime);
		}
		catch (Exception e) {
			logger.error(e);
		}
		return commEndByTime;
	}

	private  float getCommissionAdj(String userId, String fromDate, String toDate, String weeklyBase, String dailyBase, float commission)
	{
		logger.info("In getCommissionAdj()...");
		float commissionAdj = 0.0f;
		int totalDays = 0;
		try{
			String noOfLoginsQry = "select count(t.loginTime) from TruckDriverTrack t,UserAuth u where t.loginTime BETWEEN '"+fromDate+"' and '"+toDate+"' and u.userId='"+userId+"' and t.driverId=u.Employee_idEmployee";
			int noOfLogins = jdbcTemplate.queryForObject(noOfLoginsQry, Integer.class)!=null ? jdbcTemplate.queryForObject(noOfLoginsQry, Integer.class) : 0;
			logger.info("noOfLogins = "+noOfLogins);

			String noOfLogoutsQry = "select count(t.logoutTime) from TruckDriverTrack t,UserAuth u where t.logoutTime BETWEEN '"+fromDate+"' and '"+toDate+"' and u.userId='"+userId+"' and t.driverId=u.Employee_idEmployee";
			int noOfLogouts = jdbcTemplate.queryForObject(noOfLogoutsQry, Integer.class)!=null ? jdbcTemplate.queryForObject(noOfLogoutsQry, Integer.class) : 0;
			logger.info("noOfLogouts = "+noOfLogouts);

			if(noOfLogins == noOfLogouts)
				totalDays = noOfLogins;
			else if(noOfLogins > noOfLogouts)
				totalDays = noOfLogouts;
			logger.info("totalDays = "+totalDays);

			float salaryOnDailyBase = totalDays * Float.parseFloat(dailyBase);
			logger.info("salaryOnDailyBase = "+salaryOnDailyBase);

			if(Float.parseFloat(weeklyBase) < salaryOnDailyBase){
				if(commission < Float.parseFloat(weeklyBase))
					commissionAdj = Float.parseFloat(weeklyBase) - commission;
			}
			else if(Float.parseFloat(weeklyBase) > salaryOnDailyBase){
				if(commission < salaryOnDailyBase)
					commissionAdj = salaryOnDailyBase - commission;
			}

		}
		catch(Exception e){
			logger.error(e);
		}
		return commissionAdj;
	}
	
	public String roundUp(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.toString();
    }
}
