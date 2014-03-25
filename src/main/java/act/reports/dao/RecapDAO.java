package act.reports.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import act.reports.model.RecapCompareToDetails;
import act.reports.model.RecapDateRangeDetails;
import act.reports.model.RecapDetails;
import act.reports.model.RecapSalesSummary;
import act.reports.model.SearchCriteria;
import act.reports.util.DateUtility;

@Repository("recapDAO")
public class RecapDAO {

	private Logger logger=Logger.getLogger(RecapDAO.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public RecapDetails getRecapDetails(SearchCriteria criteria){
		logger.info("In RecapDAO-getRecapDetails()...");
		RecapDetails recapDetails = new RecapDetails();
		List<RecapSalesSummary> recapSalesSummaryList = new ArrayList<RecapSalesSummary>();
		RecapDateRangeDetails recapDateRangeDetails = new RecapDateRangeDetails();
		RecapCompareToDetails recapCompareToDetails = new RecapCompareToDetails();
		List<String> accountTypesList = new ArrayList<String>();

		try {
			String dateRangeFromDate=criteria.getFromDate();
			String dateRangeToDate=criteria.getToDate();
			String compareTofromDate=criteria.getCompareToFromDate();
			String compareToToDate=criteria.getCompareToToDate();
			dateRangeFromDate = DateUtility.convertAsMySqlDateTime(dateRangeFromDate);			
			dateRangeToDate = DateUtility.convertAsMySqlDateTime(dateRangeToDate);
			dateRangeToDate = dateRangeToDate.replace("00:00:00", "23:59:59");
			compareTofromDate = DateUtility.convertAsMySqlDateTime(compareTofromDate);			
			compareToToDate = DateUtility.convertAsMySqlDateTime(compareToToDate);
			compareToToDate = compareToToDate.replace("00:00:00", "23:59:59");
			final String lotLocation = criteria.getLocation();
			logger.info("dateRangeFromDate in RecapDAO-getRecapDetails() : "+dateRangeFromDate);
			logger.info("dateRangeToDate in RecapDAO-getRecapDetails() : "+dateRangeToDate);
			logger.info("compareTofromDate in RecapDAO-getRecapDetails() : "+compareTofromDate);
			logger.info("compareToToDate in RecapDAO-getRecapDetails() : "+compareToToDate);
			logger.info("lotLocation in RecapDAO-getRecapDetails() : "+lotLocation);
			
			String accountTypesQry = "select AccountTypeName from AccountTypes order by AccountTypeName";
			
			accountTypesList = jdbcTemplate.query(accountTypesQry, new Object[] {}, new RowMapper<String>() {

				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					String accountTypeName = rs.getString("AccountTypeName");
					logger.info("accountTypeName in RecapDAO-getRecapDetails() : "+accountTypeName);
					return accountTypeName;
				}
			});
			
			// Sales Summary
			int salesSummaryTotalTows = 0;
			float salesSummaryTotalCharges = 0.0f;
			
			for(int i=0;i<accountTypesList.size();i++){
				RecapSalesSummary recapSalesSummary = new RecapSalesSummary();
				String accountType = accountTypesList.get(i);
				String salesSummaryNoOfTowsQry = "select count(i.invoiceId) from Account a,Invoice i where i.callStatus='Cleared' and a.acct_type='"+accountType+"' "+
												 "and i.invoiceCreatedDate BETWEEN '"+dateRangeFromDate+"' and '"+dateRangeToDate+"' and a.idAccount=i.accountId";
	
				int noOfTows = jdbcTemplate.queryForObject(salesSummaryNoOfTowsQry, Integer.class)!=null ? jdbcTemplate.queryForObject(salesSummaryNoOfTowsQry, Integer.class) : 0;
				logger.info("noOfTows in RecapDAO-getRecapDetails() : "+noOfTows);
				salesSummaryTotalTows += noOfTows;
				
				String salesSummaryTotalChargesQry = "select sum(ib.totalCharge) from Account a,Invoice i,Invoice_Billing ib where i.callStatus='Cleared' and " +
													 "a.acct_type='"+accountType+"' and i.invoiceCreatedDate BETWEEN '"+dateRangeFromDate+"' and '"+dateRangeToDate+"' " +
													 "and i.invoiceId=ib.invoiceId  and a.idAccount=i.accountId and i.accountId=ib.accountId";

				float totalCharges = 0.0f;
				totalCharges = jdbcTemplate.queryForObject(salesSummaryTotalChargesQry, Float.class)!=null ? jdbcTemplate.queryForObject(salesSummaryTotalChargesQry, Float.class) : 0.0f;
				logger.info("totalCharges in RecapDAO-getRecapDetails() : "+totalCharges);
				salesSummaryTotalCharges += totalCharges;
				
				float chargePerTow = 0.0f;
				if(totalCharges>0 && noOfTows>0){
					chargePerTow = totalCharges / noOfTows;					
				}
				logger.info("chargePerTow in RecapDAO-getRecapDetails() : "+chargePerTow);
				
				recapSalesSummary.setAccountType(accountType);
				recapSalesSummary.setNoOfTows(Integer.toString(noOfTows));
				recapSalesSummary.setTotalCharges(roundUp(totalCharges, 2));
				recapSalesSummary.setChargePerTow(roundUp(chargePerTow, 2));
				recapSalesSummaryList.add(recapSalesSummary);
			}
			
			recapDetails.setRecapSalesSummaryList(recapSalesSummaryList);
			logger.info("salesSummaryTotalTows in RecapDAO-getRecapDetails() : "+salesSummaryTotalTows);
			recapDetails.setSalesSummaryTotalTows(Integer.toString(salesSummaryTotalTows));
			logger.info("salesSummaryTotalCharges in RecapDAO-getRecapDetails() : "+salesSummaryTotalCharges);
			recapDetails.setSalesSummaryTotalCharges(Float.toString(salesSummaryTotalCharges));
			
			float salesSummaryAvgChargePerTow = 0.0f;
			salesSummaryAvgChargePerTow = salesSummaryTotalCharges / salesSummaryTotalTows;
			logger.info("salesSummaryAvgChargePerTow in RecapDAO-getRecapDetails() : "+salesSummaryAvgChargePerTow);
			recapDetails.setSalesSummaryAvgChargePerTow(Float.toString(salesSummaryAvgChargePerTow));
			
			// Impound Summary
			String impoundTowChargesQry = "select sum(ib.towCharge) from Invoice_Billing ib,Invoice_Release ir where ib.invoiceDate BETWEEN " +
										  "'"+dateRangeFromDate.substring(0, 10)+"' and '"+dateRangeToDate.substring(0, 10)+"' and ib.invoiceId=ir.invoiceId";
			float impoundTowCharges = 0.0f;
			impoundTowCharges = jdbcTemplate.queryForObject(impoundTowChargesQry, Float.class)!=null ? jdbcTemplate.queryForObject(impoundTowChargesQry, Float.class) : 0.0f;
			logger.info("impoundTowCharges in RecapDAO-getRecapDetails() : "+impoundTowCharges);
			
			String impoundStorageChargesQry = "select sum(ib.storageCharge) from Invoice_Billing ib,Invoice_Release ir where ib.invoiceDate BETWEEN " +
					  					  	  "'"+dateRangeFromDate.substring(0, 10)+"' and '"+dateRangeToDate.substring(0, 10)+"' and ib.invoiceId=ir.invoiceId";
			float impoundStorageCharges = 0.0f;
			impoundStorageCharges = jdbcTemplate.queryForObject(impoundStorageChargesQry, Float.class)!=null ? jdbcTemplate.queryForObject(impoundStorageChargesQry, Float.class) : 0.0f;
			logger.info("impoundStorageCharges in RecapDAO-getRecapDetails() : "+impoundStorageCharges);
			
			String impoundMileageChargesQry = "select sum(ib.mileageCharge) from Invoice_Billing ib,Invoice_Release ir where ib.invoiceDate BETWEEN " +
					  						  "'"+dateRangeFromDate.substring(0, 10)+"' and '"+dateRangeToDate.substring(0, 10)+"' and ib.invoiceId=ir.invoiceId";
			float impoundMileageCharges = 0.0f;
			impoundMileageCharges = jdbcTemplate.queryForObject(impoundMileageChargesQry, Float.class)!=null ? jdbcTemplate.queryForObject(impoundMileageChargesQry, Float.class) : 0.0f;
			logger.info("impoundMileageCharges in RecapDAO-getRecapDetails() : "+impoundMileageCharges);
			
			String impoundLaborChargesQry = "select sum(ib.laborCharge) from Invoice_Billing ib,Invoice_Release ir where ib.invoiceDate BETWEEN " +
					  						"'"+dateRangeFromDate.substring(0, 10)+"' and '"+dateRangeToDate.substring(0, 10)+"' and ib.invoiceId=ir.invoiceId";
			float impoundLaborCharges = 0.0f;
			impoundLaborCharges = jdbcTemplate.queryForObject(impoundLaborChargesQry, Float.class)!=null ? jdbcTemplate.queryForObject(impoundLaborChargesQry, Float.class) : 0.0f;
			logger.info("impoundLaborCharges in RecapDAO-getRecapDetails() : "+impoundLaborCharges);
			
			String impoundWinchChargesQry = "select sum(ib.winch_Charge) from Invoice_Billing ib,Invoice_Release ir where ib.invoiceDate BETWEEN " +
											"'"+dateRangeFromDate.substring(0, 10)+"' and '"+dateRangeToDate.substring(0, 10)+"' and ib.invoiceId=ir.invoiceId";
			float impoundWinchCharges = 0.0f;
			impoundWinchCharges = jdbcTemplate.queryForObject(impoundWinchChargesQry, Float.class)!=null ?jdbcTemplate.queryForObject(impoundWinchChargesQry, Float.class) : 0.0f;
			logger.info("impoundWinchCharges in RecapDAO-getRecapDetails() : "+impoundWinchCharges);
			
			String impoundMiscChargesQry = "select sum(ib.miscCharge) from Invoice_Billing ib,Invoice_Release ir where ib.invoiceDate BETWEEN " +
										   "'"+dateRangeFromDate.substring(0, 10)+"' and '"+dateRangeToDate.substring(0, 10)+"' and ib.invoiceId=ir.invoiceId";
			float impoundMiscCharges = 0.0f;
			impoundMiscCharges = jdbcTemplate.queryForObject(impoundMiscChargesQry, Float.class)!=null ? jdbcTemplate.queryForObject(impoundMiscChargesQry, Float.class) : 0.0f;
			logger.info("impoundMiscCharges in RecapDAO-getRecapDetails() : "+impoundMiscCharges);
			
			String impoundGateChargesQry = "select sum(ib.gateCharge) from Invoice_Billing ib,Invoice_Release ir where ib.invoiceDate BETWEEN " +
										   "'"+dateRangeFromDate.substring(0, 10)+"' and '"+dateRangeToDate.substring(0, 10)+"' and ib.invoiceId=ir.invoiceId";
			float impoundGateCharges = 0.0f;
			impoundGateCharges = jdbcTemplate.queryForObject(impoundGateChargesQry, Float.class)!=null ? jdbcTemplate.queryForObject(impoundGateChargesQry, Float.class) : 0.0f;
			logger.info("impoundGateCharges in RecapDAO-getRecapDetails() : "+impoundGateCharges);
			
			String impoundUserdefinedChargesQry = "select sum(ib.userDefined_Charge) from Invoice_Billing ib,Invoice_Release ir where ib.invoiceDate BETWEEN " +
												  "'"+dateRangeFromDate.substring(0, 10)+"' and '"+dateRangeToDate.substring(0, 10)+"' and ib.invoiceId=ir.invoiceId";
			float impoundUserdefinedCharges = 0.0f;
			impoundUserdefinedCharges = jdbcTemplate.queryForObject(impoundUserdefinedChargesQry, Float.class)!=null ? jdbcTemplate.queryForObject(impoundUserdefinedChargesQry, Float.class) : 0.0f;
			logger.info("impoundUserdefinedCharges in RecapDAO-getRecapDetails() : "+impoundUserdefinedCharges);
			
			String impoundPCC36ChargesQry = "select sum(ib.PCC36Charge) from Invoice_Billing ib,Invoice_Release ir where ib.invoiceDate BETWEEN " +
											"'"+dateRangeFromDate.substring(0, 10)+"' and '"+dateRangeToDate.substring(0, 10)+"' and ib.invoiceId=ir.invoiceId";
			float impoundPCC36rCharges = 0.0f;
			impoundPCC36rCharges = jdbcTemplate.queryForObject(impoundPCC36ChargesQry, Float.class)!=null ? jdbcTemplate.queryForObject(impoundPCC36ChargesQry, Float.class) : 0.0f;
			logger.info("impoundPCC36rCharges in RecapDAO-getRecapDetails() : "+impoundPCC36rCharges);
			
			String impoundAdvancedPayoutQry = "select sum(ib.advancedPayout) from Invoice_Billing ib,Invoice_Release ir where ib.invoiceDate BETWEEN " +
											 "'"+dateRangeFromDate.substring(0, 10)+"' and '"+dateRangeToDate.substring(0, 10)+"' and ib.invoiceId=ir.invoiceId";
			float impoundAdvancedPayout = 0.0f;
			impoundAdvancedPayout = jdbcTemplate.queryForObject(impoundAdvancedPayoutQry, Float.class)!=null ? jdbcTemplate.queryForObject(impoundAdvancedPayoutQry, Float.class) : 0.0f;
			logger.info("impoundAdvancedPayout in RecapDAO-getRecapDetails() : "+impoundAdvancedPayout);
			
			float impoundOtherCharges = 0.0f;
			impoundOtherCharges += impoundLaborCharges + impoundWinchCharges + impoundMiscCharges + impoundGateCharges + impoundPCC36rCharges + impoundAdvancedPayout;
			logger.info("impoundOtherCharges in RecapDAO-getRecapDetails() : "+impoundOtherCharges);
			
			String impoundTotalDiscountQry = "select sum(ib.totalDiscount) from Invoice_Billing ib,Invoice_Release ir where ib.invoiceDate BETWEEN " +
											 "'"+dateRangeFromDate.substring(0, 10)+"' and '"+dateRangeToDate.substring(0, 10)+"' and ib.invoiceId=ir.invoiceId";
			float impoundDiscounts = 0.0f;
			impoundDiscounts = jdbcTemplate.queryForObject(impoundTotalDiscountQry, Float.class)!=null ? jdbcTemplate.queryForObject(impoundTotalDiscountQry, Float.class) : 0.0f;
			logger.info("impoundDiscounts in RecapDAO-getRecapDetails() : "+impoundDiscounts);
			
			float totalImpoundCharges = 0.0f;
			totalImpoundCharges += (impoundTowCharges + impoundStorageCharges + impoundMileageCharges + impoundOtherCharges) - impoundDiscounts;
			logger.info("totalImpoundCharges in RecapDAO-getRecapDetails() : "+totalImpoundCharges);
			
			recapDetails.setImpoundTowCharges(Float.toString(impoundTowCharges));
			recapDetails.setImpoundMileageCharges(Float.toString(impoundMileageCharges));
			recapDetails.setImpoundStorageCharges(Float.toString(impoundStorageCharges));
			recapDetails.setImpoundOtherCharges(Float.toString(impoundOtherCharges));
			recapDetails.setImpoundDiscounts(Float.toString(impoundDiscounts));
			recapDetails.setTotalImpoundCharges(Float.toString(totalImpoundCharges));
			
			// Receipts Summary			
			String cashReceiptsTotalQry = "select sum(paymentAmt) from Invoice_Payment where paymentType='Cash' and paymentDate BETWEEN '"+dateRangeFromDate+"' and '"+dateRangeToDate+"'";
			float cashReceiptsTotal = 0.0f;
			cashReceiptsTotal = jdbcTemplate.queryForObject(cashReceiptsTotalQry, Float.class)!=null ? jdbcTemplate.queryForObject(cashReceiptsTotalQry, Float.class) : 0.0f;
			logger.info("cashReceiptsTotal in RecapDAO-getRecapDetails() : "+cashReceiptsTotal);
			
			String chequeReceiptsTotalQry = "select sum(paymentAmt) from Invoice_Payment where paymentType='Check' and paymentDate BETWEEN '"+dateRangeFromDate+"' and '"+dateRangeToDate+"'";
			float chequeReceiptsTotal = 0.0f;
			chequeReceiptsTotal = jdbcTemplate.queryForObject(chequeReceiptsTotalQry, Float.class)!=null ? jdbcTemplate.queryForObject(chequeReceiptsTotalQry, Float.class) : 0.0f;
			logger.info("chequeReceiptsTotal in RecapDAO-getRecapDetails() : "+chequeReceiptsTotal);
			
			String creditReceiptsTotalQry = "select sum(paymentAmt) from Invoice_Payment where paymentType='Credit' and paymentDate BETWEEN '"+dateRangeFromDate+"' and '"+dateRangeToDate+"'";
			float creditReceiptsTotal = 0.0f;
			creditReceiptsTotal = jdbcTemplate.queryForObject(creditReceiptsTotalQry, Float.class)!=null ? jdbcTemplate.queryForObject(creditReceiptsTotalQry, Float.class) : 0.0f;
			logger.info("creditReceiptsTotal in RecapDAO-getRecapDetails() : "+creditReceiptsTotal);
			
			String transferReceiptsTotalQry = "select sum(paymentAmt) from Invoice_Payment where paymentType='Transfer' and paymentDate BETWEEN '"+dateRangeFromDate+"' and '"+dateRangeToDate+"'";
			float transferReceiptsTotal = 0.0f;
			transferReceiptsTotal = jdbcTemplate.queryForObject(transferReceiptsTotalQry, Float.class)!=null ? jdbcTemplate.queryForObject(transferReceiptsTotalQry, Float.class) : 0.0f;
			logger.info("transferReceiptsTotal in RecapDAO-getRecapDetails() : "+transferReceiptsTotal);
			
			String employeeAdvanceTotalQry = "select sum(paymentAmt) from Invoice_Payment where paymentType='Employee Advance' and paymentDate BETWEEN '"+dateRangeFromDate+"' and '"+dateRangeToDate+"'";
			float employeeAdvanceTotal = 0.0f;
			employeeAdvanceTotal = jdbcTemplate.queryForObject(employeeAdvanceTotalQry, Float.class)!=null ? jdbcTemplate.queryForObject(employeeAdvanceTotalQry, Float.class) : 0.0f;
			logger.info("employeeAdvanceTotal in RecapDAO-getRecapDetails() : "+employeeAdvanceTotal);
			 
			float totalReceipts = 0.0f;
			totalReceipts += cashReceiptsTotal + chequeReceiptsTotal + transferReceiptsTotal + employeeAdvanceTotal;
			logger.info("totalReceipts in RecapDAO-getRecapDetails() : "+totalReceipts);
			
			recapDetails.setCashReceiptsTotal(Float.toString(cashReceiptsTotal));
			recapDetails.setChequeReceiptsTotal(Float.toString(chequeReceiptsTotal));
			recapDetails.setCreditReceiptsTotal(Float.toString(creditReceiptsTotal));
			recapDetails.setTransferReceiptsTotal(Float.toString(transferReceiptsTotal));
			recapDetails.setEmployeeAdvanceTotal(Float.toString(employeeAdvanceTotal));
			recapDetails.setTotalReceipts(Float.toString(totalReceipts));
			
			// Dispatch Summary			
			String totalCallsQry = "select count(serviceCallId) from ServiceCallInfo where callCreatedTime BETWEEN '"+dateRangeFromDate+"' and '"+dateRangeToDate+"'";
			int totalCalls = 0;
			totalCalls = jdbcTemplate.queryForObject(totalCallsQry, Integer.class)!=null ? jdbcTemplate.queryForObject(totalCallsQry, Integer.class) : 0;
			logger.info("totalCalls in RecapDAO-getRecapDetails() : "+totalCalls);
			
			String totalCompletedCallsQry = "select count(i.serviceCallId) from Invoice i,ServiceCallInfo sc where sc.callCreatedTime BETWEEN '"+dateRangeFromDate+"' and '"+dateRangeToDate+"' and i.callStatus='Cleared' and sc.serviceCallId=i.serviceCallId";
			int totalCompletedCalls = 0;
			totalCompletedCalls = jdbcTemplate.queryForObject(totalCompletedCallsQry, Integer.class)!=null ? jdbcTemplate.queryForObject(totalCompletedCallsQry, Integer.class) : 0;
			logger.info("totalCompletedCalls in RecapDAO-getRecapDetails() : "+totalCompletedCalls);
			
			String totalCancelledCallsQry = "select count(i.serviceCallId) from Invoice i,ServiceCallInfo sc where sc.callCreatedTime BETWEEN '"+dateRangeFromDate+"' and '"+dateRangeToDate+"' and i.callStatus='Cancel' and sc.serviceCallId=i.serviceCallId";
			int totalCancelledCalls = 0;
			totalCancelledCalls = jdbcTemplate.queryForObject(totalCancelledCallsQry, Integer.class)!=null ? jdbcTemplate.queryForObject(totalCancelledCallsQry, Integer.class) : 0;
			logger.info("totalCancelledCalls in RecapDAO-getRecapDetails() : "+totalCancelledCalls);
			
			String totalInProgressCallsQry = "select count(i.serviceCallId) from Invoice i,ServiceCallInfo sc where sc.callCreatedTime BETWEEN '"+dateRangeFromDate+"' and '"+dateRangeToDate+"' and i.callStatus!='Cleared' and i.callStatus!='Cancel' and sc.serviceCallId=i.serviceCallId";
			int totalInProgressCalls = 0;
			totalInProgressCalls = jdbcTemplate.queryForObject(totalInProgressCallsQry, Integer.class)!=null ? jdbcTemplate.queryForObject(totalInProgressCallsQry, Integer.class) : 0;
			logger.info("totalInProgressCalls in RecapDAO-getRecapDetails() : "+totalInProgressCalls);
			
			String totalOnHoldCallsQry = "select count(i.serviceCallId) from Invoice i,ServiceCallInfo sc where sc.callCreatedTime BETWEEN '"+dateRangeFromDate+"' and '"+dateRangeToDate+"' and i.callStatus='OnHold' and sc.serviceCallId=i.serviceCallId";
			int totalOnHoldCalls = 0;
			totalOnHoldCalls = jdbcTemplate.queryForObject(totalOnHoldCallsQry, Integer.class)!=null ? jdbcTemplate.queryForObject(totalOnHoldCallsQry, Integer.class) : 0;
			logger.info("totalOnHoldCalls in RecapDAO-getRecapDetails() : "+totalOnHoldCalls);
			
			recapDetails.setTotalCalls(Integer.toString(totalCalls));
			recapDetails.setTotalCompletedCalls(Integer.toString(totalCompletedCalls));
			recapDetails.setTotalCancelledCalls(Integer.toString(totalCancelledCalls));
			recapDetails.setTotalInProgressCalls(Integer.toString(totalInProgressCalls));
			recapDetails.setTotalOnHoldCalls(Integer.toString(totalOnHoldCalls));
			recapDetails.setAvgCallToArrivalTime(Float.toString(getAvgCallToArrivalTime(dateRangeFromDate, dateRangeToDate)));
			
			// Calculations Data
			float dateRangeReleasedPerDay = getReleasedPerDay(dateRangeFromDate, dateRangeToDate);
			float compareToReleasedPerDay = getReleasedPerDay(compareTofromDate, compareToToDate);
			float diffInReleasedPerDay = 0.0f;
			if(dateRangeReleasedPerDay > 0 && compareToReleasedPerDay > 0)
				diffInReleasedPerDay = (dateRangeReleasedPerDay + compareToReleasedPerDay) / (dateRangeReleasedPerDay - compareToReleasedPerDay) * 100;
			logger.info("diffInReleasedPerDay in RecapDAO-getCompareToReleasedPerDay() : "+diffInReleasedPerDay);
			
			float dateRangeAddedToImpoundPerDay = getAddedToImpundPerDay(dateRangeFromDate, dateRangeToDate);
			float compareToAddedToImpoundPerDay = getAddedToImpundPerDay(compareTofromDate, compareToToDate);
			float diffInAddedToImpoundPerDay = 0.0f;
			if(dateRangeAddedToImpoundPerDay > 0 && compareToAddedToImpoundPerDay > 0)
				diffInAddedToImpoundPerDay = (dateRangeAddedToImpoundPerDay - compareToAddedToImpoundPerDay) / compareToAddedToImpoundPerDay * 100;
			logger.info("diffInAddedToImpoundPerDay in RecapDAO-getCompareToAddedToImpundPerDay() : "+diffInAddedToImpoundPerDay);
			
			float dateRangeChargesPerRelease = getChargesPerRelease(dateRangeFromDate, dateRangeToDate);
			float compareToChargesPerRelease = getChargesPerRelease(compareTofromDate, compareToToDate);
			float diffInChargesPerRelease = 0.0f;
			if(dateRangeChargesPerRelease > 0 && compareToChargesPerRelease > 0)
				diffInChargesPerRelease = (dateRangeChargesPerRelease - compareToChargesPerRelease) / compareToChargesPerRelease * 100;
			logger.info("diffInChargesPerRelease in RecapDAO-getRecapDetails() : "+diffInChargesPerRelease);
			
			float dateRangeStorageChargesPerRelease = getStorageChargesPerRelease(dateRangeFromDate, dateRangeToDate);
			float compareToStorageChargesPerRelease = getStorageChargesPerRelease(compareTofromDate, compareToToDate);
			float diffInStorageChargesPerRelease = 0.0f;
			if(dateRangeStorageChargesPerRelease > 0 && compareToStorageChargesPerRelease > 0)
				diffInStorageChargesPerRelease = (dateRangeStorageChargesPerRelease - compareToStorageChargesPerRelease) / compareToStorageChargesPerRelease * 100;
			logger.info("diffInStorageChargesPerRelease in RecapDAO-getRecapDetails() : "+diffInStorageChargesPerRelease);
			
			float dateRangeDiscountPerRelease = getDiscountPerRelease(dateRangeFromDate, dateRangeToDate);
			float compareToDiscountPerRelease = getDiscountPerRelease(compareTofromDate, compareToToDate);
			float diffInDiscountPerRelease = 0.0f;
			if(dateRangeDiscountPerRelease > 0 && compareToDiscountPerRelease > 0)
				diffInDiscountPerRelease = (dateRangeDiscountPerRelease - compareToDiscountPerRelease) / compareToDiscountPerRelease * 100;
			logger.info("diffInDiscountPerRelease in RecapDAO-getRecapDetails() : "+diffInDiscountPerRelease);
			
			int dateRangeNoOfAVRSentInvoices = getNoOfAVRSentInvoices(dateRangeFromDate, dateRangeToDate);
			int compareToNoOfAVRSentInvoices = getNoOfAVRSentInvoices(compareTofromDate, compareToToDate);
			int diffInNoOfAVRSentInvoices = 0;
			if(dateRangeNoOfAVRSentInvoices > 0 && compareToNoOfAVRSentInvoices > 0)
				diffInNoOfAVRSentInvoices = (dateRangeNoOfAVRSentInvoices - compareToNoOfAVRSentInvoices) / compareToNoOfAVRSentInvoices * 100;
			logger.info("diffInNoOfAVRSentInvoices in RecapDAO-getRecapDetails() : "+diffInNoOfAVRSentInvoices);
			
			int dateRangeNoOfReHookedInvoices = getNoOfReHookedInvoices(dateRangeFromDate, dateRangeToDate);
			int compareToNoOfReHookedInvoices = getNoOfReHookedInvoices(compareTofromDate, compareToToDate);
			float diffInNoOfReHookedInvoices = 0.0f;
			if(dateRangeNoOfReHookedInvoices > 0 && compareToNoOfReHookedInvoices > 0)
				diffInNoOfReHookedInvoices = (dateRangeNoOfReHookedInvoices - compareToNoOfReHookedInvoices) / compareToNoOfReHookedInvoices * 100;
			logger.info("diffInNoOfReHookedInvoices in RecapDAO-getRecapDetails() : "+diffInNoOfReHookedInvoices);
			
			int dateRangeNoOfSalvageTitles = getNoOfSalvageTitles(dateRangeFromDate, dateRangeToDate);
			int compareToNoOfSalvageTitles = getNoOfSalvageTitles(compareTofromDate, compareToToDate);
			float diffInNoOfSalvageTitles = 0.0f;
			if(dateRangeNoOfSalvageTitles > 0 && compareToNoOfSalvageTitles > 0)
				diffInNoOfSalvageTitles = (dateRangeNoOfSalvageTitles - compareToNoOfSalvageTitles) / compareToNoOfSalvageTitles * 100;
			logger.info("diffInNoOfSalvageTitles in RecapDAO-getRecapDetails() : "+diffInNoOfSalvageTitles);
			
			float dateRangeRevenuePerTow = getRevenuePerTow(dateRangeFromDate, dateRangeToDate);
			float compareToRevenuePerTow = getRevenuePerTow(compareTofromDate, compareToToDate);
			float diffInRevenuePerTow = 0.0f;
			if(dateRangeRevenuePerTow > 0 && compareToRevenuePerTow > 0)
				diffInRevenuePerTow = (dateRangeRevenuePerTow - compareToRevenuePerTow) / compareToRevenuePerTow * 100;
			logger.info("diffInRevenuePerTow in RecapDAO-getRecapDetails() : "+diffInRevenuePerTow);
			
			int dateRangeNoOfInvoices = getNoOfInvoices(dateRangeFromDate, dateRangeToDate);
			int compareToNoOfInvoices = getNoOfInvoices(compareTofromDate, compareToToDate);
			float diffInNoOfInvoices = 0.0f;
			if(dateRangeNoOfInvoices > 0 && compareToNoOfInvoices > 0)
				diffInNoOfInvoices = (dateRangeNoOfInvoices - compareToNoOfInvoices) / compareToNoOfInvoices * 100;
			logger.info("diffInNoOfInvoices in RecapDAO-getRecapDetails() : "+diffInNoOfInvoices);
			
			int dateRangeNoOfCalls = getNoOfCalls(dateRangeFromDate, dateRangeToDate);
			int compareToNoOfCalls = getNoOfCalls(compareTofromDate, compareToToDate);
			float diffInNoOfCalls = 0.0f;
			if(dateRangeNoOfCalls > 0 && compareToNoOfCalls > 0)
				diffInNoOfCalls = (dateRangeNoOfCalls - compareToNoOfCalls) / compareToNoOfCalls * 100;
			logger.info("diffInNoOfCalls in RecapDAO-getRecapDetails() : "+diffInNoOfCalls);
			
			int dateRangeNoOfCancelledCalls = getNoOfCancelledCalls(dateRangeFromDate, dateRangeToDate);
			int compareToNoOfCancelledCalls = getNoOfCancelledCalls(compareTofromDate, compareToToDate);
			float diffInNoOfCancelledCalls = 0.0f;
			if(dateRangeNoOfCancelledCalls > 0 && compareToNoOfCancelledCalls > 0)
				diffInNoOfCancelledCalls = (dateRangeNoOfCancelledCalls - compareToNoOfCancelledCalls) / compareToNoOfCancelledCalls * 100;
			logger.info("diffInNoOfCancelledCalls in RecapDAO-getRecapDetails() : "+diffInNoOfCancelledCalls);
			
			int dateRangeNoOfLateCalls = getNoOfLateCalls(dateRangeFromDate, dateRangeToDate);
			int compareToNoOfLateCalls = getNoOfLateCalls(compareTofromDate, compareToToDate);
			float diffInNoOfLateCalls = 0.0f;
			if(dateRangeNoOfLateCalls > 0 && compareToNoOfLateCalls > 0)
				diffInNoOfLateCalls = (dateRangeNoOfLateCalls - compareToNoOfLateCalls) / compareToNoOfLateCalls * 100;
			logger.info("diffInNoOfLateCalls in RecapDAO-getRecapDetails() : "+diffInNoOfLateCalls);
			
			float dateRangeTotalReceipts = getTotalReceipts(dateRangeFromDate, dateRangeToDate);
			float compareToTotalReceipts = getTotalReceipts(compareTofromDate, compareToToDate);
			float diffInTotalReceipts = 0.0f;
			if(dateRangeTotalReceipts > 0 && compareToTotalReceipts > 0)
				diffInTotalReceipts = (dateRangeTotalReceipts - compareToTotalReceipts) / compareToTotalReceipts * 100;
			logger.info("diffInTotalReceipts in RecapDAO-getRecapDetails() : "+diffInTotalReceipts);
			
			int dateRangeAddedToImpund = getAddedToImpund(dateRangeFromDate, dateRangeToDate);
			int compareToAddedToImpund = getAddedToImpund(compareTofromDate, compareToToDate);
			float diffInAddedToImpund = 0.0f;
			if(dateRangeAddedToImpund > 0 && compareToAddedToImpund > 0)
				diffInAddedToImpund = (dateRangeAddedToImpund - compareToAddedToImpund) / compareToAddedToImpund * 100;
			logger.info("diffInAddedToImpund in RecapDAO-getRecapDetails() : "+diffInAddedToImpund);
			
			int dateRangeImpoundReleased = getImpoundReleased(dateRangeFromDate, dateRangeToDate);
			int compareToImpoundReleased = getImpoundReleased(compareTofromDate, compareToToDate);
			float diffInImpoundReleased = 0.0f;
			if(dateRangeImpoundReleased > 0 && compareToImpoundReleased > 0)
				diffInImpoundReleased = (dateRangeImpoundReleased - compareToImpoundReleased) / compareToImpoundReleased * 100;
			logger.info("diffInImpoundReleased in RecapDAO-getRecapDetails() : "+diffInImpoundReleased);
			
			int dateRangeAVRSentInvoices = getAVRSentInvoices(dateRangeFromDate, dateRangeToDate);
			int compareToAVRSentInvoices = getAVRSentInvoices(compareTofromDate, compareToToDate);
			float diffInAVRSentInvoices = 0.0f;
			if(dateRangeAVRSentInvoices > 0 && compareToAVRSentInvoices > 0)
				diffInAVRSentInvoices = (dateRangeAVRSentInvoices - compareToAVRSentInvoices) / compareToAVRSentInvoices * 100;
			logger.info("diffInAVRSentInvoices in RecapDAO-getRecapDetails() : "+diffInAVRSentInvoices);
			
			int dateRangeTitleReleaseInvoices = getTitleReleaseInvoices(dateRangeFromDate, dateRangeToDate);
			int compareToTitleReleaseInvoices = getTitleReleaseInvoices(compareTofromDate, compareToToDate);
			float diffInTitleReleaseInvoices = 0.0f;
			if(dateRangeTitleReleaseInvoices > 0 && compareToTitleReleaseInvoices > 0)
				diffInTitleReleaseInvoices = (dateRangeTitleReleaseInvoices - compareToTitleReleaseInvoices) / compareToTitleReleaseInvoices * 100;
			logger.info("diffInTitleReleaseInvoices in RecapDAO-getRecapDetails() : "+diffInTitleReleaseInvoices);
			
			float dateRangeMilesPerTowDriven = getMilesPerTowDriven(dateRangeFromDate, dateRangeToDate);
			float compareToMilesPerTowDriven = getMilesPerTowDriven(compareTofromDate, compareToToDate);
			float diffInMilesPerTowDriven = 0.0f;
			if(dateRangeMilesPerTowDriven > 0 && compareToMilesPerTowDriven > 0)
				diffInMilesPerTowDriven = (dateRangeMilesPerTowDriven - compareToMilesPerTowDriven) / compareToMilesPerTowDriven * 100;
			logger.info("diffInMilesPerTowDriven in RecapDAO-getRecapDetails() : "+diffInMilesPerTowDriven);
			
			float dateRangeFleetUtilization = getFleetUtilization(dateRangeFromDate, dateRangeToDate);
			float compareToFleetUtilization = getFleetUtilization(compareTofromDate, compareToToDate);
			float diffInFleetUtilization = 0.0f;
			if(dateRangeFleetUtilization > 0 && compareToFleetUtilization > 0)
				diffInFleetUtilization = (dateRangeFleetUtilization - compareToFleetUtilization) / compareToFleetUtilization * 100;
			logger.info("diffInFleetUtilization in RecapDAO-getRecapDetails() : "+diffInFleetUtilization);
			
			int dateRangeBeginingImpound = getBeginingImpound(dateRangeFromDate);
			int compareToBeginingImpound = getBeginingImpound(compareTofromDate);			
			float diffInBeginingImpound = 0.0f;
			if(dateRangeBeginingImpound > 0 && compareToBeginingImpound > 0)
				diffInBeginingImpound = (dateRangeBeginingImpound - compareToBeginingImpound) / compareToBeginingImpound * 100;
			logger.info("diffInBeginingImpound in RecapDAO-getRecapDetails() : "+diffInBeginingImpound);
			
			int dateRangeEndingImpound = getEndingImpound(dateRangeFromDate);
			int compareToEndingImpound = getEndingImpound(compareTofromDate);			
			float diffInEndingImpound = 0.0f;
			if(dateRangeEndingImpound > 0 && compareToEndingImpound > 0)
				diffInEndingImpound = (dateRangeEndingImpound - compareToEndingImpound) / compareToEndingImpound * 100;
			logger.info("diffInEndingImpound in RecapDAO-getRecapDetails() : "+diffInEndingImpound);
			
			
			float dateRangeAcountsReceivableAging = getAvgAcountsReceivableAging(dateRangeFromDate, dateRangeToDate);
			float compareToAcountsReceivableAging = getAvgAcountsReceivableAging(compareTofromDate, compareToToDate);
			logger.info("dateRangeAcountsReceivableAging in RecapDAO-getRecapDetails() : "+dateRangeAcountsReceivableAging);
			logger.info("compareToAcountsReceivableAging in RecapDAO-getRecapDetails() : "+compareToAcountsReceivableAging);
			/*float diffInAcountsReceivableAging = 0.0f;
			if(dateRangeAcountsReceivableAging > 0 && compareToAcountsReceivableAging > 0)
				diffInAcountsReceivableAging = (dateRangeAcountsReceivableAging - compareToAcountsReceivableAging) / compareToAcountsReceivableAging * 100;
			logger.info("diffInAcountsReceivableAging in RecapDAO-getRecapDetails() : "+diffInAcountsReceivableAging);*/
			
			float dateRangeAcountsReceivableOutstandingAmt = getAcountsReceivableOutstandingAmt(dateRangeFromDate, dateRangeToDate);
			float compareToAcountsReceivableOutstandingAmt = getAcountsReceivableOutstandingAmt(compareTofromDate, compareToToDate);
			logger.info("dateRangeAcountsReceivableOutstandingAmt in RecapDAO-getRecapDetails() : "+dateRangeAcountsReceivableOutstandingAmt);
			logger.info("compareToAcountsReceivableOutstandingAmt in RecapDAO-getRecapDetails() : "+compareToAcountsReceivableOutstandingAmt);
			
			float dateRangeAcountsReceivableInvoicesAmt = getAcountsReceivableInvoicesAmt(dateRangeFromDate, dateRangeToDate);
			float compareToAcountsReceivableInvoicesAmt = getAcountsReceivableInvoicesAmt(compareTofromDate, compareToToDate);
			logger.info("dateRangeAcountsReceivableInvoicesAmt in RecapDAO-getRecapDetails() : "+dateRangeAcountsReceivableInvoicesAmt);
			logger.info("compareToAcountsReceivableInvoicesAmt in RecapDAO-getRecapDetails() : "+compareToAcountsReceivableInvoicesAmt);
			
			float dateRangeCallToArrivalTime = getAvgCallToArrivalTime(dateRangeFromDate, dateRangeToDate);
			float compareToCallToArrivalTime = getAvgCallToArrivalTime(compareTofromDate, compareToToDate);
			float diffInCallToArrivalTime = 0.0f;
			if(dateRangeCallToArrivalTime > 0 && compareToCallToArrivalTime > 0)
				diffInCallToArrivalTime = (dateRangeCallToArrivalTime - compareToCallToArrivalTime) / compareToCallToArrivalTime * 100;
			logger.info("diffInCallToArrivalTime in RecapDAO-getRecapDetails() : "+diffInCallToArrivalTime);
			
			float dateRangeDispatchToClearTime = getAvgDispatchToClearTime(dateRangeFromDate, dateRangeToDate);
			float compareToDispatchToClearTime = getAvgDispatchToClearTime(compareTofromDate, compareToToDate);
			float diffInDispatchToClearTime = 0.0f;
			if(dateRangeDispatchToClearTime > 0 && compareToDispatchToClearTime > 0)
				diffInDispatchToClearTime = (dateRangeDispatchToClearTime - compareToDispatchToClearTime) / compareToDispatchToClearTime * 100;
			logger.info("diffInDispatchToClearTime in RecapDAO-getRecapDetails() : "+diffInDispatchToClearTime);
			
			recapDateRangeDetails.setReleasedPerDay(roundUp(dateRangeReleasedPerDay, 2));
			recapDateRangeDetails.setAddedToImpoundPerDay(roundUp(dateRangeAddedToImpoundPerDay, 2));
			recapDateRangeDetails.setChargesPerRelease(roundUp(dateRangeChargesPerRelease, 2));
			recapDateRangeDetails.setStoragePerRelease(roundUp(dateRangeStorageChargesPerRelease, 2));
			recapDateRangeDetails.setDiscountPerRelease(roundUp(dateRangeDiscountPerRelease, 2));
			recapDateRangeDetails.setNoOfReHooks(Integer.toString(dateRangeNoOfReHookedInvoices));
			recapDateRangeDetails.setNoOfSalvageTitlesFromTOA(Integer.toString(dateRangeNoOfSalvageTitles));
			recapDateRangeDetails.setRevenuePerTow(roundUp(dateRangeRevenuePerTow, 2));
			recapDateRangeDetails.setInvoices(Integer.toString(dateRangeNoOfInvoices));
			recapDateRangeDetails.setCalls(Integer.toString(dateRangeNoOfCalls));
			recapDateRangeDetails.setCancelledCalls(Integer.toString(dateRangeNoOfCancelledCalls));
			recapDateRangeDetails.setLateCalls(Integer.toString(dateRangeNoOfLateCalls));
			recapDateRangeDetails.setReceipts(roundUp(dateRangeTotalReceipts, 2));
			recapDateRangeDetails.setAddedToImpound(Integer.toString(dateRangeAddedToImpund));
			recapDateRangeDetails.setImpoundReleased(Integer.toString(dateRangeImpoundReleased));
			recapDateRangeDetails.setNoOfAVRSent(Integer.toString(dateRangeAVRSentInvoices));
			recapDateRangeDetails.setNoOfTitleReleases(Integer.toString(dateRangeTitleReleaseInvoices));
			recapDateRangeDetails.setMilesPerTowDriven(roundUp(dateRangeMilesPerTowDriven, 2));
			recapDateRangeDetails.setCallToArrivalTime(roundUp(dateRangeCallToArrivalTime, 2));
			recapDateRangeDetails.setDispatchToClearTime(roundUp(dateRangeDispatchToClearTime, 2));
			recapDateRangeDetails.setFleetUtilization(roundUp(dateRangeFleetUtilization, 2));
			recapDateRangeDetails.setBeginingImpoundAsOfDate(Integer.toString(dateRangeBeginingImpound));
			recapDateRangeDetails.setEndingImpundAsOfDate(Integer.toString(dateRangeEndingImpound));
			recapDateRangeDetails.setAvgAccountReceivableAging(roundUp(dateRangeAcountsReceivableAging, 2));
			recapDateRangeDetails.setAccountReceivableAmountOutstanding(roundUp(dateRangeAcountsReceivableOutstandingAmt, 2));
			recapDateRangeDetails.setAccountReceivableInvoicesAmount(roundUp(dateRangeAcountsReceivableInvoicesAmt, 2));
			
			recapCompareToDetails.setDiffInReleasedPerDay(roundUp(diffInReleasedPerDay, 2));
			recapCompareToDetails.setDiffInAddedToImpoundPerDay(roundUp(diffInAddedToImpoundPerDay, 2));
			recapCompareToDetails.setDiffInChargesPerRelease(roundUp(diffInChargesPerRelease, 2));
			recapCompareToDetails.setDiffInStoragePerRelease(roundUp(diffInStorageChargesPerRelease, 2));
			recapCompareToDetails.setDiffInDiscountPerRelease(roundUp(diffInDiscountPerRelease, 2));
			recapCompareToDetails.setDiffInNoOfReHooks(roundUp(diffInNoOfReHookedInvoices, 2));
			recapCompareToDetails.setDiffInNoOfSalvageTitlesFromTOA(roundUp(diffInNoOfSalvageTitles, 2));
			recapCompareToDetails.setDiffInRevenuePerTow(roundUp(diffInRevenuePerTow, 2));
			recapCompareToDetails.setDiffInInvoices(roundUp(diffInNoOfInvoices, 2));
			recapCompareToDetails.setDiffInCalls(roundUp(diffInNoOfCalls, 2));
			recapCompareToDetails.setDiffInCancelledCalls(roundUp(diffInNoOfCancelledCalls, 2));
			recapCompareToDetails.setDiffInLateCalls(roundUp(diffInNoOfLateCalls, 2));
			recapCompareToDetails.setDiffInReceipts(roundUp(diffInTotalReceipts, 2));
			recapCompareToDetails.setDiffInAddedToImpound(roundUp(diffInAddedToImpund, 2));
			recapCompareToDetails.setDiffInImpoundReleased(roundUp(diffInImpoundReleased, 2));
			recapCompareToDetails.setDiffInNoOfAVRSent(roundUp(diffInAVRSentInvoices, 2));
			recapCompareToDetails.setDiffInNoOfTitleReleases(roundUp(diffInTitleReleaseInvoices, 2));
			recapCompareToDetails.setDiffInMilesPerTowDriven(roundUp(diffInMilesPerTowDriven, 2));
			recapCompareToDetails.setDiffInCallToArrivalTime(roundUp(diffInCallToArrivalTime, 2));
			recapCompareToDetails.setDiffInDispatchToClearTime(roundUp(diffInDispatchToClearTime, 2));
			recapCompareToDetails.setDiffInFleetUtilization(roundUp(diffInFleetUtilization, 2));
			recapCompareToDetails.setDiffInBeginingImpoundAsOfDate(roundUp(diffInBeginingImpound, 2));
			recapCompareToDetails.setDiffInEndingImpundAsOfDate(roundUp(diffInEndingImpound, 2));
			recapCompareToDetails.setAvgAccountReceivableAging(roundUp(compareToAcountsReceivableAging, 2));
			recapCompareToDetails.setAccountReceivableAmountOutstanding(roundUp(compareToAcountsReceivableOutstandingAmt, 2));
			recapCompareToDetails.setAccountReceivableInvoicesAmount(roundUp(compareToAcountsReceivableInvoicesAmt, 2));
			recapDetails.setBeginingImpundAsOfDate(DateUtility.getOneDayBeforeDate(dateRangeFromDate));
			recapDetails.setEndingImpundAsOfDate(DateUtility.getOneDayBeforeDate(dateRangeToDate));
			recapDetails.setRecapDateRangeDetails(recapDateRangeDetails);
			recapDetails.setRecapCompareToDetails(recapCompareToDetails);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
		return recapDetails;
	}
	
	private float getAvgCallToArrivalTime(String fromDate, String toDate){
		logger.info("In getAvgCallToArrivalTime()...");
		List<String> invoiceIdsList = new ArrayList<String>();
		String invoiceIdsQry = "";
		int callToArrival = 0;
		float avgCallToArrival = 0.0f;
		try{
			invoiceIdsQry = "select distinct i.invoiceId from Invoice i,ServiceCallInfo sc where sc.callCreatedTime BETWEEN '"+fromDate+"' and '"+toDate+"' and sc.serviceCallId=i.serviceCallId";
			invoiceIdsList = jdbcTemplate.query(invoiceIdsQry, new Object[] {}, new RowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					String invoiceId = (rs.getString("invoiceId") != null) ? rs.getString("invoiceId") : "";					
					return invoiceId;
				}
			});
			
			if(invoiceIdsList!=null && invoiceIdsList.size()>0){
				for(int i=0;i<invoiceIdsList.size();i++){
					logger.info("invoiceId : "+invoiceIdsList.get(i));
					String arrivalTimeQry = "select arrivedTime from Invoice where invoiceId='"+invoiceIdsList.get(i)+"'";
					String arrivalTime = jdbcTemplate.queryForObject(arrivalTimeQry, String.class)!=null ? jdbcTemplate.queryForObject(arrivalTimeQry, String.class) : "";
					logger.info("arrivalTime : "+arrivalTime);
					if(!arrivalTime.trim().isEmpty() && arrivalTime.trim().length()>11)
						arrivalTime = arrivalTime.substring(11, 20);
					
					String callTimeQry = "select sc.callCreatedTime from Invoice i,ServiceCallInfo sc where i.invoiceId='"+invoiceIdsList.get(i)+"' and sc.serviceCallId=i.serviceCallId";
					String callTime = jdbcTemplate.queryForObject(callTimeQry, String.class)!=null ? jdbcTemplate.queryForObject(callTimeQry, String.class) : "";
					logger.info("callTime : "+callTime);
					if(!callTime.trim().isEmpty() && callTime.trim().length()>11)
						callTime = callTime.substring(11, 20);
					
					if(!callTime.trim().isEmpty() && !arrivalTime.trim().isEmpty()){
						String callToArrivalQry = "SELECT TIMESTAMPDIFF(MINUTE,'"+callTime+"','"+arrivalTime+"')";
						callToArrival = jdbcTemplate.queryForObject(callToArrivalQry, Integer.class);						
					}
					logger.info("callToArrival : "+callToArrival);
				}
				
				if(callToArrival>0 && invoiceIdsList.size()>0)
					avgCallToArrival = callToArrival / invoiceIdsList.size();
			}
			logger.info("avgCallToArrival in RecapDAO-getAvgCallToArrivalTime() : "+avgCallToArrival);
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return avgCallToArrival;
	}
	
	private float getReleasedPerDay(String fromDate, String toDate){
		float releasedPerDay = 0.0f;
		try{
			String daysBetweenDateRangeQry = "select TIMESTAMPDIFF(DAY,'"+fromDate+"','"+toDate+"')";
			float daysBetweenDateRange = jdbcTemplate.queryForObject(daysBetweenDateRangeQry, Float.class)!=null ? jdbcTemplate.queryForObject(daysBetweenDateRangeQry, Float.class) : 0;
			logger.info("daysBetweenDateRange in RecapDAO-getReleasedPerDay() : "+daysBetweenDateRange);
			
			String releasesBetweenDateRangeQry = "select count(invoiceId) from Invoice_Release where releaseDate BETWEEN '"+fromDate+"' and '"+toDate+"'";
			float releasesBetweenDateRange = jdbcTemplate.queryForObject(releasesBetweenDateRangeQry, Float.class)!=null ? jdbcTemplate.queryForObject(releasesBetweenDateRangeQry, Float.class) : 0;
			logger.info("releasesBetweenDateRange in RecapDAO-getReleasedPerDay() : "+releasesBetweenDateRange);
			
			if(releasesBetweenDateRange > 0){
				releasedPerDay = releasesBetweenDateRange / daysBetweenDateRange;				
			}
			logger.info("releasedPerDay in RecapDAO-getReleasedPerDay() : "+releasedPerDay);
		}
		catch(Exception e){
			logger.error(e);
		}
		return releasedPerDay;
	}
	
	private float getAddedToImpundPerDay(String fromDate, String toDate){
		float addedToImpoundPerDay = 0.0f;
		try{
			String daysBetweenDateRangeQry = "select TIMESTAMPDIFF(DAY,'"+fromDate+"','"+toDate+"')";
			float daysBetweenDateRange = jdbcTemplate.queryForObject(daysBetweenDateRangeQry, Float.class)!=null ? jdbcTemplate.queryForObject(daysBetweenDateRangeQry, Float.class) : 0;
			logger.info("daysBetweenDateRange in RecapDAO-getDateRangeAddedToImpundPerDay() : "+daysBetweenDateRange);
			
			String dateRangeAddedToImpoundQry = "select count(invoiceId) from Invoice where dropOffAddr_Address_Type='impoundLot' and invoiceCreatedDate BETWEEN '"+fromDate+"' and '"+toDate+"'";
			float dateRangeAddedToImpound = jdbcTemplate.queryForObject(dateRangeAddedToImpoundQry, Float.class)!=null ? jdbcTemplate.queryForObject(dateRangeAddedToImpoundQry, Float.class) : 0;
			logger.info("dateRangeAddedToImpound in RecapDAO-getDateRangeAddedToImpundPerDay() : "+dateRangeAddedToImpound);
			
			if(dateRangeAddedToImpound > 0){
				addedToImpoundPerDay = dateRangeAddedToImpound / daysBetweenDateRange;				
			}
			logger.info("addedToImpoundPerDay in RecapDAO-getDateRangeAddedToImpundPerDay() : "+addedToImpoundPerDay);
		}
		catch(Exception e){
			logger.error(e);
		}
		return addedToImpoundPerDay;
	}
	
	private float getChargesPerRelease(String fromDate, String toDate){
		float chargesPerRelease = 0.0f;
		try{
			String noOfInvoicesReleasedQry = "select count(invoiceId) from Invoice_Release where releaseDate BETWEEN '"+fromDate+"' and '"+toDate+"'";
			float noOfInvoicesReleased = jdbcTemplate.queryForObject(noOfInvoicesReleasedQry, Float.class)!=null ? jdbcTemplate.queryForObject(noOfInvoicesReleasedQry, Float.class) : 0;
			logger.info("noOfInvoicesReleased in RecapDAO-getChargesPerRelease() : "+noOfInvoicesReleased);
			
			String releasedInvoicesTotalChargesQry = "select sum(ib.totalCharge) from Invoice_Billing ib,Invoice_Release ir where ir.releaseDate BETWEEN '"+fromDate+"' and '"+toDate+"' and ir.invoiceId=ib.invoiceId";
			float releasedInvoicesTotalCharges = jdbcTemplate.queryForObject(releasedInvoicesTotalChargesQry, Float.class)!=null ? jdbcTemplate.queryForObject(releasedInvoicesTotalChargesQry, Float.class) : 0f;
			logger.info("releasedInvoicesTotalCharges in RecapDAO-getChargesPerRelease() : "+releasedInvoicesTotalCharges);
			
			if(releasedInvoicesTotalCharges > 0){
				chargesPerRelease = noOfInvoicesReleased / releasedInvoicesTotalCharges;				
			}
			logger.info("chargesPerRelease in RecapDAO-getChargesPerRelease() : "+chargesPerRelease);
		}
		catch(Exception e){
			logger.error(e);
		}
		return chargesPerRelease;
	}
	
	private float getStorageChargesPerRelease(String fromDate, String toDate){
		float storageChargesPerRelease = 0.0f;
		try{
			String noOfInvoicesReleasedQry = "select count(invoiceId) from Invoice_Release where releaseDate BETWEEN '"+fromDate+"' and '"+toDate+"'";
			float noOfInvoicesReleased = jdbcTemplate.queryForObject(noOfInvoicesReleasedQry, Float.class)!=null ? jdbcTemplate.queryForObject(noOfInvoicesReleasedQry, Float.class) : 0;
			logger.info("noOfInvoicesReleased in RecapDAO-getStorageChargesPerRelease() : "+noOfInvoicesReleased);
			
			String releasedInvoicesStorageChargesQry = "select sum(ib.storageCharge) from Invoice_Billing ib,Invoice_Release ir where ir.releaseDate BETWEEN '"+fromDate+"' and '"+toDate+"' and ir.invoiceId=ib.invoiceId";
			float releasedInvoicesStorageCharges = jdbcTemplate.queryForObject(releasedInvoicesStorageChargesQry, Float.class)!=null ? jdbcTemplate.queryForObject(releasedInvoicesStorageChargesQry, Float.class) : 0.0f;
			logger.info("releasedInvoicesStorageCharges in RecapDAO-getStorageChargesPerRelease() : "+releasedInvoicesStorageCharges);
			
			if(releasedInvoicesStorageCharges > 0){
				storageChargesPerRelease = noOfInvoicesReleased / releasedInvoicesStorageCharges;				
			}
			logger.info("storageChargesPerRelease in RecapDAO-getStorageChargesPerRelease() : "+storageChargesPerRelease);
		}
		catch(Exception e){
			logger.error(e);
		}
		return storageChargesPerRelease;
	}
	
	private float getDiscountPerRelease(String fromDate, String toDate){
		float discountPerRelease = 0.0f;
		try{
			String noOfInvoicesReleasedQry = "select count(invoiceId) from Invoice_Release where releaseDate BETWEEN '"+fromDate+"' and '"+toDate+"'";
			float noOfInvoicesReleased = jdbcTemplate.queryForObject(noOfInvoicesReleasedQry, Float.class)!=null ? jdbcTemplate.queryForObject(noOfInvoicesReleasedQry, Float.class) : 0;
			logger.info("noOfInvoicesReleased in RecapDAO-getDiscountPerRelease() : "+noOfInvoicesReleased);
			
			String discountOnReleasedInvoicesQry = "select sum(ib.totalDiscount) from Invoice_Billing ib,Invoice_Release ir where ir.releaseDate BETWEEN '"+fromDate+"' and '"+toDate+"' and ir.invoiceId=ib.invoiceId";
			float discountOnReleasedInvoices = jdbcTemplate.queryForObject(discountOnReleasedInvoicesQry, Float.class)!=null ? jdbcTemplate.queryForObject(discountOnReleasedInvoicesQry, Float.class) : 0.0f;
			logger.info("discountOnReleasedInvoices in RecapDAO-getDiscountPerRelease() : "+discountOnReleasedInvoices);
			
			if(discountOnReleasedInvoices > 0){
				discountPerRelease = noOfInvoicesReleased / discountOnReleasedInvoices;				
			}
			logger.info("discountPerRelease in RecapDAO-getDiscountPerRelease() : "+discountPerRelease);
		}
		catch(Exception e){
			logger.error(e);
		}
		return discountPerRelease;
	}
	
	private int getNoOfAVRSentInvoices(String fromDate, String toDate){
		int noOfAVRSentInvoices = 0;
		try{
			String noOfAVRSentInvoicesQry = "select count(invoiceId) from Invoice_TitleProcess where avrDate BETWEEN '"+fromDate.substring(0, 10)+"' and '"+toDate.substring(0, 10)+"'";
			noOfAVRSentInvoices = jdbcTemplate.queryForObject(noOfAVRSentInvoicesQry, Integer.class)!=null ? jdbcTemplate.queryForObject(noOfAVRSentInvoicesQry, Integer.class) : 0;
			logger.info("noOfAVRSentInvoices in RecapDAO-getNoOfAVRSentInvoices() : "+noOfAVRSentInvoices);			
		}
		catch(Exception e){
			logger.error(e);
		}
		return noOfAVRSentInvoices;
	}
	
	private int getNoOfReHookedInvoices(String fromDate, String toDate){
		int noOfReHookedInvoices = 0;
		try{
			String noOfReHookedInvoicesQry = "select count(rehookInvoiceId) from Invoice where rehookInvoiceId!='' and invoiceCreatedDate BETWEEN '"+fromDate+"' and '"+toDate+"'";
			noOfReHookedInvoices = jdbcTemplate.queryForObject(noOfReHookedInvoicesQry, Integer.class)!=null ? jdbcTemplate.queryForObject(noOfReHookedInvoicesQry, Integer.class) : 0;
			logger.info("noOfReHookedInvoices in RecapDAO-getNoOfReHookedInvoices() : "+noOfReHookedInvoices);			
		}
		catch(Exception e){
			logger.error(e);
		}
		return noOfReHookedInvoices;
	}
	
	private int getNoOfSalvageTitles(String fromDate, String toDate){
		int noOfSalvageTitles = 0;
		try{
			String noOfSalvageTitlesQry = "select count(invoiceId) from Invoice_TitleProcess where titleDate BETWEEN '"+fromDate.substring(0, 10)+"' and '"+toDate.substring(0, 10)+"'";
			noOfSalvageTitles = jdbcTemplate.queryForObject(noOfSalvageTitlesQry, Integer.class)!=null ? jdbcTemplate.queryForObject(noOfSalvageTitlesQry, Integer.class) : 0;
			logger.info("noOfSalvageTitles in RecapDAO-getNoOfSalvageTitles() : "+noOfSalvageTitles);			
		}
		catch(Exception e){
			logger.error(e);
		}
		return noOfSalvageTitles;
	}
	
	private float getRevenuePerTow(String fromDate, String toDate){
		int noOfInvoices = 0;
		float totalCharges = 0.0f;
		float storageCharges = 0.0f;
		float revenuePerTow = 0.0f;
		try{
			String noOfInvoicesQry = "select count(i.invoiceId) from Invoice i,Invoice_Billing ib  where i.invoiceCreatedDate BETWEEN '"+fromDate+"' and '"+toDate+"' and i.invoiceId=ib.invoiceId";
			noOfInvoices = jdbcTemplate.queryForObject(noOfInvoicesQry, Integer.class)!=null ? jdbcTemplate.queryForObject(noOfInvoicesQry, Integer.class) : 0;
			logger.info("noOfInvoices in RecapDAO-getAvgRevenuePerTow() : "+noOfInvoices);		
			
			String totalChargesQry = "select sum(ib.totalCharge) from Invoice i,Invoice_Billing ib where i.invoiceCreatedDate BETWEEN '"+fromDate+"' and '"+toDate+"' and i.invoiceId=ib.invoiceId";
			totalCharges = jdbcTemplate.queryForObject(totalChargesQry, Float.class)!=null ? jdbcTemplate.queryForObject(totalChargesQry, Float.class) : 0.0f;
			logger.info("totalCharges in RecapDAO-getAvgRevenuePerTow() : "+totalCharges);
			
			String storageChargesQry = "select sum(ib.storageCharge) from Invoice i,Invoice_Billing ib where i.invoiceCreatedDate BETWEEN '"+fromDate+"' and '"+toDate+"' and i.invoiceId=ib.invoiceId";
			storageCharges = jdbcTemplate.queryForObject(storageChargesQry, Float.class)!=null ? jdbcTemplate.queryForObject(storageChargesQry, Float.class) : 0.0f;
			logger.info("storageCharges in RecapDAO-getAvgRevenuePerTow() : "+storageCharges);
			
			float charges = totalCharges - storageCharges;
			if(charges > 0){
				revenuePerTow = noOfInvoices / charges;				
			}
			logger.info("revenuePerTow in RecapDAO-getAvgRevenuePerTow() : "+revenuePerTow);
		}
		catch(Exception e){
			logger.error(e);
		}
		return revenuePerTow;
	}
	
	private int getNoOfInvoices(String fromDate, String toDate){
		int noOfInvoices = 0;
		try{
			String noOfInvoicesQry = "select count(invoiceId) from Invoice where callStatus='Cleared' and invoiceCreatedDate BETWEEN '"+fromDate+"' and '"+toDate+"'";
			noOfInvoices = jdbcTemplate.queryForObject(noOfInvoicesQry, Integer.class)!=null ? jdbcTemplate.queryForObject(noOfInvoicesQry, Integer.class) : 0;
			logger.info("noOfInvoices in RecapDAO-getNoOfInvoices() : "+noOfInvoices);			
		}
		catch(Exception e){
			logger.error(e);
		}
		return noOfInvoices;
	}
	
	private int getNoOfCalls(String fromDate, String toDate){
		int noOfCalls = 0;
		try{
			String noOfCallsQry = "select count(serviceCallId) from ServiceCallInfo where callCreatedTime BETWEEN '"+fromDate+"' and '"+toDate+"'";
			noOfCalls = jdbcTemplate.queryForObject(noOfCallsQry, Integer.class)!=null ? jdbcTemplate.queryForObject(noOfCallsQry, Integer.class) : 0;
			logger.info("noOfCalls in RecapDAO-getNoOfCalls() : "+noOfCalls);			
		}
		catch(Exception e){
			logger.error(e);
		}
		return noOfCalls;
	}
	
	private int getNoOfCancelledCalls(String fromDate, String toDate){
		int noOfCancelledCalls = 0;
		try{
			String noOfCancelledCallsQry = "select count(i.serviceCallId) from Invoice i,ServiceCallInfo sc where sc.callCreatedTime BETWEEN '"+fromDate+"' and '"+toDate+"' and i.callStatus='Cancel' and sc.serviceCallId=i.serviceCallId";
			noOfCancelledCalls = jdbcTemplate.queryForObject(noOfCancelledCallsQry, Integer.class)!=null ? jdbcTemplate.queryForObject(noOfCancelledCallsQry, Integer.class) : 0;
			logger.info("noOfCancelledCalls in RecapDAO-getNoOfCancelledCalls() : "+noOfCancelledCalls);			
		}
		catch(Exception e){
			logger.error(e);
		}
		return noOfCancelledCalls;
	}
	
	private int getNoOfLateCalls(String fromDate, String toDate){
		int noOfLateCalls = 0;
		try{
			String noOfLateCallsQry = "select count(i.serviceCallId) from Account a,Invoice i,ServiceCallInfo sc where sc.callCreatedTime BETWEEN '"+fromDate+"' and '"+toDate+"' and " +
									  "a.maxArrivalTime<(SELECT TIMESTAMPDIFF(MINUTE,sc.callCreatedTime,i.arrivedTime)) and a.idAccount=i.accountId and a.idAccount=sc.accountId and sc.serviceCallId=i.serviceCallId";
			noOfLateCalls = jdbcTemplate.queryForObject(noOfLateCallsQry, Integer.class)!=null ? jdbcTemplate.queryForObject(noOfLateCallsQry, Integer.class) : 0;
			logger.info("noOfLateCalls in RecapDAO-getNoOfLateCalls() : "+noOfLateCalls);			
		}
		catch(Exception e){
			logger.error(e);
		}
		return noOfLateCalls;
	}
	
	private float getTotalReceipts(String fromDate, String toDate){
		float totalReceipts = 0.0f;
		try{
			String totalReceiptsQry = "select sum(ib.totalCharge) from Invoice i,Invoice_Billing ib where i.invoiceCreatedDate BETWEEN '"+fromDate+"' and '"+toDate+"' and i.invoiceId=ib.invoiceId";
			totalReceipts = jdbcTemplate.queryForObject(totalReceiptsQry, Float.class)!=null ? jdbcTemplate.queryForObject(totalReceiptsQry, Float.class) : 0.0f;
			logger.info("totalReceipts in RecapDAO-getTotalReceipts() : "+totalReceipts);			
		}
		catch(Exception e){
			logger.error(e);
		}
		return totalReceipts;
	}
	
	private int getAddedToImpund(String fromDate, String toDate){
		int addedToImpound = 0;
		try{
			String addedToImpoundQry = "select count(invoiceId) from Invoice where dropOffAddr_Address_Type='impoundLot' and invoiceCreatedDate BETWEEN '"+fromDate+"' and '"+toDate+"'";
			addedToImpound = jdbcTemplate.queryForObject(addedToImpoundQry, Integer.class)!=null ? jdbcTemplate.queryForObject(addedToImpoundQry, Integer.class) : 0;
			logger.info("addedToImpoundQry in RecapDAO-getDateRangeAddedToImpundPerDay() : "+addedToImpoundQry);			
		}
		catch(Exception e){
			logger.error(e);
		}
		return addedToImpound;
	}
	
	private int getImpoundReleased(String fromDate, String toDate){
		int impoundReleased = 0;
		try{
			String impoundReleasedQry = "select count(invoiceId) from Invoice_Release where releaseDate BETWEEN '"+fromDate+"' and '"+toDate+"'";
			impoundReleased = jdbcTemplate.queryForObject(impoundReleasedQry, Integer.class)!=null ? jdbcTemplate.queryForObject(impoundReleasedQry, Integer.class) : 0;
			logger.info("impoundReleased in RecapDAO-getImpoundReleased() : "+impoundReleased);			
		}
		catch(Exception e){
			logger.error(e);
		}
		return impoundReleased;
	}
	
	private int getAVRSentInvoices(String fromDate, String toDate){
		int avrSentInvoices = 0;
		try{
			String avrSentInvoicesQry = "select count(ib.invoiceId) from Invoice_Billing ib, Invoice_TitleProcess it where ib.invoiceDate BETWEEN '"+fromDate+"' and '"+toDate+"' and it.avrDate<>'' and ib.invoiceId=it.invoiceId";
			avrSentInvoices = jdbcTemplate.queryForObject(avrSentInvoicesQry, Integer.class)!=null ? jdbcTemplate.queryForObject(avrSentInvoicesQry, Integer.class) : 0;
			logger.info("avrSentInvoices in RecapDAO-getAVRSentInvoices() : "+avrSentInvoices);			
		}
		catch(Exception e){
			logger.error(e);
		}
		return avrSentInvoices;
	}
	
	private int getTitleReleaseInvoices(String fromDate, String toDate){
		int titleReleaseInvoices = 0;
		try{
			String titleReleaseInvoicesQry = "select count(ib.invoiceId) from Invoice_Billing ib, Invoice_TitleProcess it where ib.invoiceDate BETWEEN '"+fromDate+"' and '"+toDate+"' and it.titleDate<>'' and ib.invoiceId=it.invoiceId";
			titleReleaseInvoices = jdbcTemplate.queryForObject(titleReleaseInvoicesQry, Integer.class)!=null ? jdbcTemplate.queryForObject(titleReleaseInvoicesQry, Integer.class) : 0;
			logger.info("titleReleaseInvoices in RecapDAO-getTitleReleaseInvoices() : "+titleReleaseInvoices);			
		}
		catch(Exception e){
			logger.error(e);
		}
		return titleReleaseInvoices;
	}
	
	private float getMilesPerTowDriven(String fromDate, String toDate){
		float totalLoadedMiles = 0.0f;
		float totalUnloadedMiles = 0.0f;
		int totalInvoices = 0;
		float milesPerTowDriven = 0.0f;
		try{
			String totallLoadedMilesQry = "select sum(loadedMiles) from Invoice_Billing where invoiceDate BETWEEN '"+fromDate+"' and '"+toDate+"'";
			totalLoadedMiles = jdbcTemplate.queryForObject(totallLoadedMilesQry, Float.class)!=null ? jdbcTemplate.queryForObject(totallLoadedMilesQry, Float.class) : 0;
			logger.info("milesPerTowDriven in RecapDAO-getMilesPerTowDriven() : "+milesPerTowDriven);	
			
			String totallUnloadedMilesQry = "select sum(unloadedMiles) from Invoice_Billing where invoiceDate BETWEEN '"+fromDate+"' and '"+toDate+"'";
			totalUnloadedMiles = jdbcTemplate.queryForObject(totallUnloadedMilesQry, Float.class)!=null ? jdbcTemplate.queryForObject(totallUnloadedMilesQry, Float.class) : 0;
			logger.info("totalUnloadedMiles in RecapDAO-getMilesPerTowDriven() : "+totalUnloadedMiles);
			
			String totallInvoicesQry = "select count(invoiceId) from Invoice_Billing where invoiceDate BETWEEN '"+fromDate+"' and '"+toDate+"'";
			totalInvoices = jdbcTemplate.queryForObject(totallInvoicesQry, Integer.class)!=null ? jdbcTemplate.queryForObject(totallInvoicesQry, Integer.class) : 0;
			logger.info("totalInvoices in RecapDAO-getMilesPerTowDriven() : "+totalInvoices);
			
			milesPerTowDriven = (totalLoadedMiles + totalUnloadedMiles) / totalInvoices;
			logger.info("milesPerTowDriven in RecapDAO-getMilesPerTowDriven() : "+milesPerTowDriven);			
		}
		catch(Exception e){
			logger.error(e);
		}
		return milesPerTowDriven;
	}
	
	private float getFleetUtilization(String fromDate, String toDate){
		float fleetUtilization = 0.0f;
		float avgDailyTowCount = 0;
		int totalTrucksInFleet = 0;
		List<String> totalTrucksList = new ArrayList<String>();
		List<String> totalTrucksInServiceList = new ArrayList<String>();
		try{
			String totallInvoicesQry = "select count(invoiceId) from Invoice_Billing where invoiceDate BETWEEN '"+fromDate+"' and '"+toDate+"'";
			int totalInvoices = jdbcTemplate.queryForObject(totallInvoicesQry, Integer.class)!=null ? jdbcTemplate.queryForObject(totallInvoicesQry, Integer.class) : 0;
			logger.info("totalInvoices in RecapDAO-getFleetUtilization() : "+totalInvoices);
			
			String daysBetweenDateRangeQry = "select TIMESTAMPDIFF(DAY,'"+fromDate+"','"+toDate+"')";
			int daysBetweenDateRange = jdbcTemplate.queryForObject(daysBetweenDateRangeQry, Integer.class)!=null ? jdbcTemplate.queryForObject(daysBetweenDateRangeQry, Integer.class) : 0;
			logger.info("daysBetweenDateRange in RecapDAO-getFleetUtilization() : "+daysBetweenDateRange);
			
			if(totalInvoices>0 && daysBetweenDateRange>0)
				avgDailyTowCount = totalInvoices / daysBetweenDateRange;
			logger.info("avgDailyTowCount in RecapDAO-getFleetUtilization() : "+avgDailyTowCount);
			
			String totalTrucksQry = "select distinct TruckId from Truck_Service_Details where CreatedDate<='"+toDate.substring(0, 10)+"' order by TruckId";
			totalTrucksList = jdbcTemplate.query(totalTrucksQry, new Object[] {}, new RowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					String truckId = (rs.getString("TruckId") != null) ? rs.getString("TruckId") : "";					
					return truckId;
				}
			});
			logger.info("totalTrucksList.size() = "+totalTrucksList.size());
			
			for(int i=0;i<totalTrucksList.size();i++){
				logger.info("TruckId = "+totalTrucksList.get(i));
				Date inServiceDate = null;
				String inServiceDateQry = "select MAX(CreatedDate) from Truck_Service_Details where ServiceStatus='In Service' and TruckId='"+totalTrucksList.get(i)+"'";
				try{
					inServiceDate = jdbcTemplate.queryForObject(inServiceDateQry, Date.class);
				}catch(Exception e){
					logger.error(e);
				}
				logger.info("inServiceDate in RecapDAO-getFleetUtilization() : "+inServiceDate);
				
				Date soldDate = null;
				String soldDateQry = "select MAX(CreatedDate) from Truck_Service_Details where ServiceStatus='Sold' and TruckId='"+totalTrucksList.get(i)+"'";
				try{
					soldDate = jdbcTemplate.queryForObject(soldDateQry, Date.class);
				}catch(Exception e){
					logger.error(e);
				}
				logger.info("soldDate in RecapDAO-getFleetUtilization() : "+soldDate);
				
				if(inServiceDate!=null && soldDate!=null){
					if(inServiceDate.compareTo(soldDate)>0){
						/*String totalTrucksQry = "select count(TruckId) from Truck_Service_Details where ServiceStatus='In Service' and CreatedDate<='"+toDate.substring(0, 10)+"' group by TruckId";
						totalTrucksInFleet = jdbcTemplate.queryForObject(totalTrucksQry, Integer.class)!=null ? jdbcTemplate.queryForObject(totalTrucksQry, Integer.class) : 0;
						logger.info("totalTrucksInFleet in RecapDAO-getFleetUtilization() : "+totalTrucksInFleet);*/
						totalTrucksInServiceList.add(totalTrucksList.get(i));
					}
				}
				else if(inServiceDate!=null && soldDate==null){
					/*String totalTrucksQry = "select count(TruckId) from Truck_Service_Details where ServiceStatus='In Service' and CreatedDate<='"+toDate.substring(0, 10)+"' group by TruckId";
					totalTrucksInFleet = jdbcTemplate.queryForObject(totalTrucksQry, Integer.class)!=null ? jdbcTemplate.queryForObject(totalTrucksQry, Integer.class) : 0;
					logger.info("totalTrucksInFleet in RecapDAO-getFleetUtilization() : "+totalTrucksInFleet);*/
					totalTrucksInServiceList.add(totalTrucksList.get(i));
				}
			}
			
			totalTrucksInFleet = totalTrucksInServiceList.size();
			logger.info("totalTrucksInFleet in RecapDAO-getFleetUtilization() : "+totalTrucksInFleet);
			if(totalTrucksInFleet>0)
				fleetUtilization = avgDailyTowCount / totalTrucksInFleet;
			logger.info("fleetUtilization in RecapDAO-getFleetUtilization() : "+fleetUtilization);			
		}
		catch(Exception e){
			logger.error(e);
		}
		return fleetUtilization;
	}
	
	private int getBeginingImpound(String date){
		float totalCharges = 0.0f;
		float totalPayments = 0.0f;
		float balance = 0.0f;
		int totalInvoices = 0;
		List<String> invoiceIdsList = new ArrayList<String>();
		List<String> invoiceIdsListFinal = new ArrayList<String>();
		
		try{
			String invoiceIdsQry = "select distinct invoiceId from Invoice_Billing ib where invoiceDate<='"+DateUtility.getOneDayBeforeDate(date)+"' and ib.isPaid<>'1' order by invoiceId";
			invoiceIdsList = jdbcTemplate.query(invoiceIdsQry, new Object[] {}, new RowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					String invoiceId = (rs.getString("invoiceId") != null) ? rs.getString("invoiceId") : "";					
					return invoiceId;
				}
			});
			
			for(int i=0;i<invoiceIdsList.size();i++){
				String totalChargesQry = "select totalCharge from Invoice_Billing where invoiceId='"+invoiceIdsList.get(i)+"'";
				totalCharges = jdbcTemplate.queryForObject(totalChargesQry, Float.class)!=null ? jdbcTemplate.queryForObject(totalChargesQry, Float.class) : 0.0f;
				logger.info("totalCharges in RecapDAO-getBeginingImpound() : "+totalCharges);
				
				String totalPaymentsQry = "select SUM(paymentAmt) from Invoice_Payment where invoiceId='"+invoiceIdsList.get(i)+"'";
				totalPayments = jdbcTemplate.queryForObject(totalPaymentsQry, Float.class)!=null ? jdbcTemplate.queryForObject(totalPaymentsQry, Float.class) : 0.0f;
				logger.info("totalPayments in RecapDAO-getBeginingImpound() : "+totalPayments);	
				
				balance = totalCharges - totalPayments;
				logger.info("balance in RecapDAO-getBeginingImpound() : "+balance);
				
				if(balance>0)
					invoiceIdsListFinal.add(invoiceIdsList.get(i));
			}
			logger.info("invoiceIdsList.size() in RecapDAO-getBeginingImpound() : "+invoiceIdsList.size());
			logger.info("invoiceIdsListFinal.size() in RecapDAO-getBeginingImpound() : "+invoiceIdsListFinal.size());
			
			totalInvoices = invoiceIdsListFinal.size();
			logger.info("totalInvoices in RecapDAO-getBeginingImpound() : "+totalInvoices);
		}
		catch(Exception e){
			logger.error(e);
		}
		return totalInvoices;
	}
	
	private int getEndingImpound(String date){
		float totalCharges = 0.0f;
		float totalPayments = 0.0f;
		float balance = 0.0f;
		int totalInvoices = 0;
		List<String> invoiceIdsList = new ArrayList<String>();
		List<String> invoiceIdsListFinal = new ArrayList<String>();
		
		try{
			String invoiceIdsQry = "select distinct invoiceId from Invoice_Billing ib where invoiceDate<='"+DateUtility.getOneDayBeforeDate(date)+"' and ib.isPaid<>'1' order by invoiceId";
			invoiceIdsList = jdbcTemplate.query(invoiceIdsQry, new Object[] {}, new RowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					String invoiceId = (rs.getString("invoiceId") != null) ? rs.getString("invoiceId") : "";					
					return invoiceId;
				}
			});
			
			for(int i=0;i<invoiceIdsList.size();i++){
				String totalChargesQry = "select totalCharge from Invoice_Billing where invoiceId='"+invoiceIdsList.get(i)+"'";
				totalCharges = jdbcTemplate.queryForObject(totalChargesQry, Float.class)!=null ? jdbcTemplate.queryForObject(totalChargesQry, Float.class) : 0.0f;
				logger.info("totalCharges in RecapDAO-getBeginingImpound() : "+totalCharges);
				
				String totalPaymentsQry = "select SUM(paymentAmt) from Invoice_Payment where invoiceId='"+invoiceIdsList.get(i)+"'";
				totalPayments = jdbcTemplate.queryForObject(totalPaymentsQry, Float.class)!=null ? jdbcTemplate.queryForObject(totalPaymentsQry, Float.class) : 0.0f;
				logger.info("totalPayments in RecapDAO-getBeginingImpound() : "+totalPayments);	
				
				balance = totalCharges - totalPayments;
				logger.info("balance in RecapDAO-getBeginingImpound() : "+balance);
				
				if(balance>0)
					invoiceIdsListFinal.add(invoiceIdsList.get(i));
			}
			logger.info("invoiceIdsList.size() in RecapDAO-getBeginingImpound() : "+invoiceIdsList.size());
			logger.info("invoiceIdsListFinal.size() in RecapDAO-getBeginingImpound() : "+invoiceIdsListFinal.size());
			
			totalInvoices = invoiceIdsListFinal.size();
			logger.info("totalInvoices in RecapDAO-getBeginingImpound() : "+totalInvoices);
		}
		catch(Exception e){
			logger.error(e);
		}
		return totalInvoices;
	}
	
	private float getAvgAcountsReceivableAging(String fromDate, String toDate){
		float totalCharges = 0.0f;
		float totalPayments = 0.0f;
		float balance = 0.0f;
		float avgAcountsReceivableAging = 0.0f;
		String droppedDate = "";
		int totalInvoices = 0;
		int totalDays = 0;
		List<String> invoiceIdsList = new ArrayList<String>();
		
		try{
			String invoiceIdsQry = "select distinct i.invoiceId from Invoice i, Invoice_Billing ib where i.invoiceCreatedDate BETWEEN '"+fromDate+"' and '"+toDate+"' and i.invoiceId=ib.invoiceId order by i.invoiceId";
			invoiceIdsList = jdbcTemplate.query(invoiceIdsQry, new Object[] {}, new RowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					String invoiceId = (rs.getString("invoiceId") != null) ? rs.getString("invoiceId") : "";					
					return invoiceId;
				}
			});
			
			for(int i=0;i<invoiceIdsList.size();i++){
				String totalChargesQry = "select totalCharge from Invoice_Billing where invoiceId='"+invoiceIdsList.get(i)+"'";
				totalCharges = jdbcTemplate.queryForObject(totalChargesQry, Float.class)!=null ? jdbcTemplate.queryForObject(totalChargesQry, Float.class) : 0.0f;
				logger.info("totalCharges in RecapDAO-getAvgAcountsReceivableAging() : "+totalCharges);
				
				String totalPaymentsQry = "select SUM(paymentAmt) from Invoice_Payment where invoiceId='"+invoiceIdsList.get(i)+"' and paymentDate<='"+toDate+"'";
				totalPayments = jdbcTemplate.queryForObject(totalPaymentsQry, Float.class)!=null ? jdbcTemplate.queryForObject(totalPaymentsQry, Float.class) : 0.0f;
				logger.info("totalPayments in RecapDAO-getAvgAcountsReceivableAging() : "+totalPayments);	
				
				balance = totalCharges - totalPayments;
				logger.info("balance in RecapDAO-getAvgAcountsReceivableAging() : "+balance);
				
				if(balance>0){
					String droppedDateQry = "select dropOffTime from Invoice where invoiceId='"+invoiceIdsList.get(i)+"'";
					droppedDate = jdbcTemplate.queryForObject(droppedDateQry, String.class)!=null ? jdbcTemplate.queryForObject(droppedDateQry, String.class) : "";
					logger.info("droppedDate in RecapDAO-getAvgAcountsReceivableAging() : "+droppedDate);
					
					String daysBetweenDatesQry = "select TIMESTAMPDIFF(DAY,'"+toDate+"','"+DateUtility.getCurrentMysqlDateTime()+"')";
					int daysBetweenDates = jdbcTemplate.queryForObject(daysBetweenDatesQry, Integer.class)!=null ? jdbcTemplate.queryForObject(daysBetweenDatesQry, Integer.class) : 0;
					logger.info("daysBetweenDates in RecapDAO-getAvgAcountsReceivableAging() : "+daysBetweenDates);
					
					totalInvoices += 1;
					totalDays += daysBetweenDates;
				}
			}
			
			if(totalInvoices>0)
				avgAcountsReceivableAging = totalDays / totalInvoices;
			logger.info("avgAcountsReceivableAging in RecapDAO-getAvgAcountsReceivableAging() : "+avgAcountsReceivableAging);
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return avgAcountsReceivableAging;
	}
	
	private float getAcountsReceivableOutstandingAmt(String fromDate, String toDate){
		float totalCharges = 0.0f;
		float totalPayments = 0.0f;
		float balance = 0.0f;		
		float totalBalance = 0.0f;
		int totalInvoices = 0;
		float acountsReceivableOutstandingAmt = 0.0f;
		List<String> invoiceIdsList = new ArrayList<String>();
		
		try{
			String invoiceIdsQry = "select distinct i.invoiceId from Invoice i, Invoice_Billing ib where i.invoiceCreatedDate BETWEEN '"+fromDate+"' and '"+toDate+"' and i.invoiceId=ib.invoiceId order by i.invoiceId";
			invoiceIdsList = jdbcTemplate.query(invoiceIdsQry, new Object[] {}, new RowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					String invoiceId = (rs.getString("invoiceId") != null) ? rs.getString("invoiceId") : "";					
					return invoiceId;
				}
			});
			
			for(int i=0;i<invoiceIdsList.size();i++){
				String totalChargesQry = "select totalCharge from Invoice_Billing where invoiceId='"+invoiceIdsList.get(i)+"'";
				totalCharges = jdbcTemplate.queryForObject(totalChargesQry, Float.class)!=null ? jdbcTemplate.queryForObject(totalChargesQry, Float.class) : 0.0f;
				logger.info("totalCharges in RecapDAO-getAcountsReceivableOutstandingAmt() : "+totalCharges);
				
				String totalPaymentsQry = "select SUM(paymentAmt) from Invoice_Payment where invoiceId='"+invoiceIdsList.get(i)+"' and paymentDate<='"+toDate+"'";
				totalPayments = jdbcTemplate.queryForObject(totalPaymentsQry, Float.class)!=null ? jdbcTemplate.queryForObject(totalPaymentsQry, Float.class) : 0.0f;
				logger.info("totalPayments in RecapDAO-getAcountsReceivableOutstandingAmt() : "+totalPayments);	
				
				balance = totalCharges - totalPayments;
				logger.info("balance in RecapDAO-getAcountsReceivableOutstandingAmt() : "+balance);
				
				if(balance>0){
					totalInvoices += 1;
					totalBalance += balance;
				}
			}
			
			if(totalInvoices>0)
				acountsReceivableOutstandingAmt = totalBalance / totalInvoices;
			logger.info("acountsReceivableOutstandingAmt in RecapDAO-getAcountsReceivableOutstandingAmt() : "+acountsReceivableOutstandingAmt);
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return acountsReceivableOutstandingAmt;
	}
	
	private float getAcountsReceivableInvoicesAmt(String fromDate, String toDate){
		float totalCharges = 0.0f;
		float totalPayments = 0.0f;
		float balance = 0.0f;		
		float acountsReceivableInvoicesAmt = 0.0f;
		List<String> invoiceIdsList = new ArrayList<String>();
		
		try{
			String invoiceIdsQry = "select distinct i.invoiceId from Invoice i, Invoice_Billing ib where i.invoiceCreatedDate BETWEEN '"+fromDate+"' and '"+toDate+"' and i.invoiceId=ib.invoiceId order by i.invoiceId";
			invoiceIdsList = jdbcTemplate.query(invoiceIdsQry, new Object[] {}, new RowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					String invoiceId = (rs.getString("invoiceId") != null) ? rs.getString("invoiceId") : "";					
					return invoiceId;
				}
			});
			
			for(int i=0;i<invoiceIdsList.size();i++){
				String totalChargesQry = "select totalCharge from Invoice_Billing where invoiceId='"+invoiceIdsList.get(i)+"'";
				totalCharges = jdbcTemplate.queryForObject(totalChargesQry, Float.class)!=null ? jdbcTemplate.queryForObject(totalChargesQry, Float.class) : 0.0f;
				logger.info("totalCharges in RecapDAO-getAcountsReceivableInvoicesAmt() : "+totalCharges);
				
				String totalPaymentsQry = "select SUM(paymentAmt) from Invoice_Payment where invoiceId='"+invoiceIdsList.get(i)+"' and paymentDate<='"+toDate+"'";
				totalPayments = jdbcTemplate.queryForObject(totalPaymentsQry, Float.class)!=null ? jdbcTemplate.queryForObject(totalPaymentsQry, Float.class) : 0.0f;
				logger.info("totalPayments in RecapDAO-getAcountsReceivableInvoicesAmt() : "+totalPayments);	
				
				balance = totalCharges - totalPayments;
				logger.info("balance in RecapDAO-getAcountsReceivableInvoicesAmt() : "+balance);
				
				if(balance>0){
					acountsReceivableInvoicesAmt += balance;
				}
			}
			
			logger.info("acountsReceivableInvoicesAmt in RecapDAO-getAcountsReceivableOutstandingAmt() : "+acountsReceivableInvoicesAmt);
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return acountsReceivableInvoicesAmt;
	}
	
	private float getAvgDispatchToClearTime(String fromDate, String toDate){
		logger.info("In getAvgDispatchToClearTime()...");
		List<String> invoiceIdsList = new ArrayList<String>();
		String invoiceIdsQry = "";
		int dispatchToClear = 0;
		float avgDispatchToClear = 0.0f;
		try{
			invoiceIdsQry = "select distinct i.invoiceId from Invoice i,ServiceCallInfo sc where sc.callCreatedTime BETWEEN '"+fromDate+"' and '"+toDate+"' and sc.serviceCallId=i.serviceCallId";
			invoiceIdsList = jdbcTemplate.query(invoiceIdsQry, new Object[] {}, new RowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					String invoiceId = (rs.getString("invoiceId") != null) ? rs.getString("invoiceId") : "";					
					return invoiceId;
				}
			});
			
			if(invoiceIdsList!=null && invoiceIdsList.size()>0){
				for(int i=0;i<invoiceIdsList.size();i++){
					logger.info("invoiceId : "+invoiceIdsList.get(i));
					String dispatchTimeQry = "select dispatchTime from Invoice where invoiceId='"+invoiceIdsList.get(i)+"'";
					String dispatchTime = jdbcTemplate.queryForObject(dispatchTimeQry, String.class)!=null ? jdbcTemplate.queryForObject(dispatchTimeQry, String.class) : "";
					logger.info("dispatchTime : "+dispatchTime);
					if(!dispatchTime.trim().isEmpty() && dispatchTime.trim().length()>11)
						dispatchTime = dispatchTime.substring(11, 20);
					
					String clearedTimeQry = "select clearedTime from Invoice where invoiceId='"+invoiceIdsList.get(i)+"'";
					String clearedTime = jdbcTemplate.queryForObject(clearedTimeQry, String.class)!=null ? jdbcTemplate.queryForObject(clearedTimeQry, String.class) : "";
					logger.info("clearedTime : "+clearedTime);
					if(!clearedTime.trim().isEmpty() && clearedTime.trim().length()>11)
						clearedTime = clearedTime.substring(11, 20);
					
					if(!dispatchTime.trim().isEmpty() && !clearedTime.trim().isEmpty()){
						String dispatchToClearQry = "SELECT TIMESTAMPDIFF(MINUTE,'"+dispatchTime+"','"+clearedTime+"')";
						dispatchToClear = jdbcTemplate.queryForObject(dispatchToClearQry, Integer.class);						
					}
					logger.info("dispatchToClear : "+dispatchToClear);
				}
				
				if(dispatchToClear>0 && invoiceIdsList.size()>0)
					avgDispatchToClear = dispatchToClear / invoiceIdsList.size();
			}
			logger.info("avgDispatchToClear in RecapDAO-getAvgCallToArrivalTime() : "+avgDispatchToClear);
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return avgDispatchToClear;
	}
	
	public String roundUp(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.toString();
    }
}
