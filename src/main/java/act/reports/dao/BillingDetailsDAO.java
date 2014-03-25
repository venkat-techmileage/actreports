package act.reports.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import act.reports.model.AccountNames;
import act.reports.model.BillingAddressDetails;
import act.reports.model.BillingDetails;
import act.reports.model.BillingInfoDetails;
import act.reports.model.SearchCriteria;
import act.reports.util.DateUtility;

@Repository("billingDetailsDAO")
public class BillingDetailsDAO {

	private Logger logger=Logger.getLogger(BillingDetailsDAO.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

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

	public List<BillingDetails> getBillingDetails(SearchCriteria criteria){
		logger.info("In BillingDetailsDAO-getBillingDetails()...");
		List<BillingDetails> billingDetailsList = new ArrayList<BillingDetails>();
		List<AccountNames> accountNamesList = new ArrayList<AccountNames>();
		try {
			String asOfDate=criteria.getAsOfDate();
			asOfDate = DateUtility.convertAsMySqlDateTime(asOfDate);
			asOfDate = asOfDate.replace("00:00:00", "23:59:59");
			logger.info("asOfDate in BillingDetailsDAO-getBillingDetails() : "+asOfDate);
			String searchString = criteria.getSearchString();
			logger.info("searchString in BillingDetailsDAO-getBillingDetails() : "+searchString);
			String accountName = criteria.getAccountName();
			/*if(accountName.contains("'"))
				accountName = accountName.replace("'", "\'");*/
			logger.info("accountName in BillingDetailsDAO-getBillingDetails() : "+accountName);
			
			if(searchString.equalsIgnoreCase("allBillable")){
				String accountNamesQry = "select distinct name from Account where accountingType='Billable' and isActive='1'";
				accountNamesList = jdbcTemplate.query(accountNamesQry, new Object[] {}, new RowMapper<AccountNames>() {
					public AccountNames mapRow(ResultSet rs, int rowNum) throws SQLException {
						AccountNames accountName = new AccountNames();	
						accountName.setAccountName((rs.getString("name") != null) ? rs.getString("name") : "");					
						return accountName;
					}
				});
				logger.info("accountNamesList.size() : "+accountNamesList.size());
				if(accountNamesList.size()>0){
					for(int i=0;i<accountNamesList.size();i++){
						BillingDetails billingDetails = new BillingDetails();
						billingDetails = getBillingDetailsByAccount(accountNamesList.get(i).getAccountName(), asOfDate);
						if(billingDetails.getBillingInfoDetailsList().size()>0)
							billingDetailsList.add(billingDetails);
					}
				}
			}
			else if(searchString.equalsIgnoreCase("byAccount")){
				BillingDetails billingDetails = new BillingDetails();
				billingDetails = getBillingDetailsByAccount(accountName, asOfDate);
				if(billingDetails.getBillingInfoDetailsList().size()>0)
					billingDetailsList.add(billingDetails);
			}

			logger.info("billingDetailsList.size() : "+billingDetailsList.size());

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}

		return billingDetailsList;
	}

	public BillingDetails getBillingDetailsByAccount(String accountName, String asOfDate){
		logger.info("In BillingDetailsDAO-getBillingDetailsByAccount()...");
		List<BillingInfoDetails> billingInfoDetailsList = null;
		List<BillingInfoDetails> billingInfoDetailsListFinal = new ArrayList<BillingInfoDetails>();
		BillingDetails billingDetails = new BillingDetails();
		String accountNameNew = "";
		try{
			Float currentTotal = 0.0f;
			Float thirtyDaysTotal = 0.0f;
			Float sixtyDaysTotal = 0.0f;
			Float nintyDaysTotal = 0.0f;
			Float moreThanNintyDaysTotal = 0.0f;
			logger.info("accountName in getBillingAddress() = "+accountName);
			if(accountName.contains("'"))
				accountNameNew = accountName.replace("'", "''");
			else
				accountNameNew = accountName;			
			logger.info("accountNameNew in getBillingAddress() = "+accountNameNew);
			final String asOfDate1 = asOfDate;
			String billingQuery = "select distinct i.dropOffLocation,ib.invoiceId,ib.totalCharge,ib.PONo,ib.invoiceDate,iv.vehicle_Comm_Unit_No,iv.vehicle_Year,iv.vehicle_Make,iv.vehicle_Model,iv.vehicle_VIN " +
								  "from Account a,Invoice i,Invoice_Billing ib LEFT OUTER JOIN Invoice_Vehicle iv ON ib.invoiceId=iv.invoiceId where a.name='"+accountNameNew+"' and i.dropOffTime<='"+asOfDate+"' " +
								  "and ib.invoiceDate<='"+asOfDate.substring(0, 10)+"' and a.idAccount=i.accountId and i.invoiceId=ib.invoiceId"; 

			billingInfoDetailsList = jdbcTemplate.query(billingQuery, new Object[] {}, new RowMapper<BillingInfoDetails>() {

				public BillingInfoDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

					BillingInfoDetails billingInfoDetails = new BillingInfoDetails();	
					billingInfoDetails.setLocation((rs.getString("dropOffLocation") != null) ? rs.getString("dropOffLocation") : " ");
					String invoiceId = (rs.getString("invoiceId") != null) ? rs.getString("invoiceId") : " ";
					billingInfoDetails.setInvoiceId(invoiceId);

					float balance = 0.0f;
					String totalCharge = (rs.getString("totalCharge") != null) ? rs.getString("totalCharge") : "0.00";
					logger.info("totalCharge = "+totalCharge);
					
					String paymentAmt = getInvoiceBillingPayment(invoiceId, asOfDate1);					
					logger.info("paymentAmt = "+paymentAmt);
					
					balance = Float.parseFloat(totalCharge) - Float.parseFloat(paymentAmt);
					logger.info("balance = "+balance);
					billingInfoDetails.setAmountOwed(roundUp(balance, 2));
					
					billingInfoDetails.setPoNo((rs.getString("PONo") != null) ? rs.getString("PONo") : " ");
					billingInfoDetails.setDate((rs.getString("invoiceDate") != null) ? rs.getString("invoiceDate") : " ");
					billingInfoDetails.setCommUnitNo((rs.getString("vehicle_Comm_Unit_No") != null) ? rs.getString("vehicle_Comm_Unit_No") : " ");
					billingInfoDetails.setVehicleYear((rs.getString("vehicle_Year") != null) ? rs.getString("vehicle_Year") : " ");
					billingInfoDetails.setVehicleMake((rs.getString("vehicle_Make") != null) ? rs.getString("vehicle_Make") : " ");
					billingInfoDetails.setVehicleModel((rs.getString("vehicle_Model") != null) ? rs.getString("vehicle_Model") : " ");

					String vehicleVIN = (rs.getString("vehicle_VIN") != null) ? rs.getString("vehicle_VIN") : " ";
					if(!vehicleVIN.trim().isEmpty() && vehicleVIN.trim().length()>6)
						billingInfoDetails.setVehicleVIN(vehicleVIN.substring(vehicleVIN.indexOf(vehicleVIN.charAt(vehicleVIN.length()-6))));
					else
						billingInfoDetails.setVehicleVIN(vehicleVIN);
										
					return billingInfoDetails;
				}
			});

			String invoiceDate = "";
			String invoiceId = "";
			Float currentDue = 0.0f;
			Float thirtyDaysDue = 0.0f;
			Float sixtyDaysDue = 0.0f;
			Float nintyDaysDue = 0.0f;
			Float moreThanNintyDaysDue = 0.0f;
			Float accountTotal = 0.0f;

			for(int i=0;i<billingInfoDetailsList.size();i++){
				BillingInfoDetails billingInfoDetails = billingInfoDetailsList.get(i);	
				/*invoiceId = billingInfoDetails.getInvoiceId();				
				logger.info("invoiceId = "+invoiceId);
				String amountOwed = billingInfoDetails.getAmountOwed();
				logger.info("amountOwed = "+amountOwed);
				String invoicePayment = getInvoiceBillingPayment(invoiceId, asOfDate);
				logger.info("invoicePayment = "+invoicePayment);
				
				float balance = Float.parseFloat(amountOwed) - Float.parseFloat(invoicePayment);*/
				if(Float.parseFloat(billingInfoDetails.getAmountOwed()) > 0)
					billingInfoDetailsListFinal.add(billingInfoDetails);
			}
			
			for(int j=0;j<billingInfoDetailsListFinal.size();j++){				
				invoiceDate = billingInfoDetailsListFinal.get(j).getDate();				
				logger.info("invoiceDate = "+invoiceDate);
				invoiceId = billingInfoDetailsListFinal.get(j).getInvoiceId();				
				logger.info("invoiceId = "+invoiceId);
				
				if((accountNameNew!=null && !accountNameNew.isEmpty()) && (invoiceDate!=null && !invoiceDate.isEmpty())){					
					String currentQry = "select SUM(ib.totalCharge) as currentDue from Account a,Invoice_Billing ib where ib.invoiceDate<='"+asOfDate+"' and ib.invoiceDate BETWEEN "+
										"ib.invoiceDate and DATE_ADD(ib.invoiceDate, INTERVAL 10 DAY) and a.name='"+accountNameNew+"' and ib.invoiceId='"+invoiceId+"' and a.idAccount=ib.accountId";					
					currentDue = jdbcTemplate.queryForObject(currentQry, Float.class);
					logger.info("currentDue = "+currentDue);

					DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd");
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(dateFormat.parse(invoiceDate));					
					calendar.add(Calendar.DATE,10);
					String billGraceDate = dateFormat.format(calendar.getTime());
					logger.info("billGraceDate = "+billGraceDate);
					String thirtyDaysQry = "select SUM(ib.totalCharge) as thirtyDaysDue from Account a,Invoice_Billing ib where ib.invoiceDate<='"+asOfDate+"' and ib.invoiceDate BETWEEN '"+billGraceDate+"'" +
										   " and DATE_ADD('"+billGraceDate+"', INTERVAL 30 DAY) and a.name='"+accountNameNew+"' and ib.invoiceId='"+invoiceId+"' and a.idAccount=ib.accountId";		
					thirtyDaysDue = jdbcTemplate.queryForObject(thirtyDaysQry, Float.class);
					logger.info("thirtyDaysDue = "+thirtyDaysDue);

					calendar.setTime(dateFormat.parse(billGraceDate));					
					calendar.add(Calendar.DATE,30);
					String thirtyDaysDate = dateFormat.format(calendar.getTime());
					logger.info("thirtyDaysDate = "+thirtyDaysDate);
					String sixtyDaysQry = "select SUM(ib.totalCharge) as sixtyDaysDue from Account a,Invoice_Billing ib where ib.invoiceDate<='"+asOfDate+"' and ib.invoiceDate BETWEEN '"+thirtyDaysDate+"' " +
							 			  "and DATE_ADD('"+thirtyDaysDate+"', INTERVAL 30 DAY) and a.name='"+accountNameNew+"' and ib.invoiceId='"+invoiceId+"' and a.idAccount=ib.accountId";		
					sixtyDaysDue = jdbcTemplate.queryForObject(sixtyDaysQry, Float.class);
					logger.info("sixtyDaysDue = "+sixtyDaysDue);

					calendar.setTime(dateFormat.parse(thirtyDaysDate));					
					calendar.add(Calendar.DATE,30);
					String sixtyDaysDate = dateFormat.format(calendar.getTime());
					logger.info("sixtyDaysDate = "+sixtyDaysDate);
					String nintyDaysQry = "select SUM(ib.totalCharge) as nintyDaysDue from Account a,Invoice_Billing ib where ib.invoiceDate<='"+asOfDate+"' and ib.invoiceDate BETWEEN '"+sixtyDaysDate+"' " +
							 			  "and DATE_ADD('"+sixtyDaysDate+"', INTERVAL 30 DAY) and a.name='"+accountNameNew+"' and ib.invoiceId='"+invoiceId+"' and a.idAccount=ib.accountId";		
					nintyDaysDue = jdbcTemplate.queryForObject(nintyDaysQry, Float.class);
					logger.info("nintyDaysDue = "+nintyDaysDue);

					calendar.setTime(dateFormat.parse(sixtyDaysDate));					
					calendar.add(Calendar.DATE,30);
					String nintyDaysDate = dateFormat.format(calendar.getTime());
					logger.info("nintyDaysDate = "+nintyDaysDate);
					String moreThanNintyDaysQry = "select SUM(ib.totalCharge) as nintyDaysDue from Account a,Invoice_Billing ib where ib.invoiceDate<='"+asOfDate+"' and " +
							 					  "ib.invoiceDate>'"+nintyDaysDate+"' and a.name='"+accountNameNew+"' and ib.invoiceId='"+invoiceId+"' and a.idAccount=ib.accountId";		
					moreThanNintyDaysDue = jdbcTemplate.queryForObject(moreThanNintyDaysQry, Float.class);
					logger.info("moreThanNintyDaysDue = "+moreThanNintyDaysDue);

					accountTotal += (currentDue != null ? currentDue : 0.0f) + (thirtyDaysDue != null ? thirtyDaysDue : 0.0f) + (sixtyDaysDue != null ? sixtyDaysDue : 0.0f) + (nintyDaysDue != null ? nintyDaysDue : 0.0f) + (moreThanNintyDaysDue != null ? moreThanNintyDaysDue : 0.0f);
					logger.info("accountTotal = "+accountTotal);

					currentTotal += currentDue != null ? currentDue : 0.0f;
					thirtyDaysTotal += thirtyDaysDue != null ? thirtyDaysDue : 0.0f;
					sixtyDaysTotal += sixtyDaysDue != null ? sixtyDaysDue : 0.0f;
					nintyDaysTotal += nintyDaysDue != null ? nintyDaysDue : 0.0f;
					moreThanNintyDaysTotal += moreThanNintyDaysDue != null ? moreThanNintyDaysDue : 0.0f;					
				}
			}

			logger.info("currentTotal = "+currentTotal);
			logger.info("thirtyDaysTotal = "+thirtyDaysTotal);
			logger.info("sixtyDaysTotal = "+sixtyDaysTotal);
			logger.info("nintyDaysTotal = "+nintyDaysTotal);
			logger.info("moreThanNintyDaysTotal = "+moreThanNintyDaysTotal);

			Float grandTotal = 0.0f;
			grandTotal += currentTotal + thirtyDaysTotal + sixtyDaysTotal + nintyDaysTotal + moreThanNintyDaysTotal;
			logger.info("grandTotal = "+grandTotal);

			billingDetails.setAccountName(accountName);
			billingDetails.setCurrentTotal(roundUp(currentTotal, 2));
			billingDetails.setThirtyDaysTotal(roundUp(thirtyDaysTotal, 2));
			billingDetails.setSixtyDaysTotal(roundUp(sixtyDaysTotal, 2));
			billingDetails.setNintyDaysTotal(roundUp(nintyDaysTotal, 2));
			billingDetails.setMoreThanNintyDaysTotal(roundUp(moreThanNintyDaysTotal, 2));
			billingDetails.setGrandTotal(roundUp(grandTotal, 2));
			billingDetails.setBillingInfoDetailsList(billingInfoDetailsListFinal);	
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}

		return billingDetails;
	}

	public BillingAddressDetails getBillingAddress(String accountName)
	{
		logger.info("In getBillingAddress()...");
		BillingAddressDetails billingAddressDetails = new BillingAddressDetails();
		List<BillingAddressDetails> billingAddressDetailsList = null;
		try
		{
			if(accountName.contains("'"))
				accountName = accountName.replace("'", "''");
			logger.info("accountName in getBillingAddress() = "+accountName);
			String billAddrQry = "select bill_addr_line1,bill_addr_line2,bill_addr_city,bill_addr_state,bill_addr_zip,bill_addr_phone,bill_addr_phone_ext,"+
								 "bill_addr_fax,bill_email_id from Account a,Account_address aa where a.name='"+accountName+"' and a.idAccount=aa.idAccount";
			logger.info("billAddrQry = "+billAddrQry);

			billingAddressDetailsList =jdbcTemplate.query(billAddrQry, new Object[] {}, new RowMapper<BillingAddressDetails>() {			
				public BillingAddressDetails mapRow(ResultSet rs, int rowNum) throws SQLException {		     
					BillingAddressDetails billingAddressDetails1 = new BillingAddressDetails();		    	
					billingAddressDetails1.setBillAddrLine1(rs.getString("bill_addr_line1")!=null?rs.getString("bill_addr_line1"):"");		    	  		    	
					billingAddressDetails1.setBillAddrLine2(rs.getString("bill_addr_line2")!=null?rs.getString("bill_addr_line2"):"");
					billingAddressDetails1.setBillAddrCity(rs.getString("bill_addr_city")!=null?rs.getString("bill_addr_city"):"");
					billingAddressDetails1.setBillAddrState(rs.getString("bill_addr_state")!=null?rs.getString("bill_addr_state"):"");
					billingAddressDetails1.setBillAddrZip(rs.getString("bill_addr_zip")!=null?rs.getString("bill_addr_zip"):"");
					billingAddressDetails1.setBillAddrPhone(rs.getString("bill_addr_phone")!=null?rs.getString("bill_addr_phone"):"");
					billingAddressDetails1.setBillAddrPhoneExt(rs.getString("bill_addr_phone_ext")!=null?rs.getString("bill_addr_phone_ext"):"");
					billingAddressDetails1.setBillAddrFax(rs.getString("bill_addr_fax")!=null?rs.getString("bill_addr_fax"):"");
					billingAddressDetails1.setBillEmailId(rs.getString("bill_email_id")!=null?rs.getString("bill_email_id"):"");
					return billingAddressDetails1;
				}						
			});
			
			if(billingAddressDetailsList!=null && !billingAddressDetailsList.isEmpty()){
				billingAddressDetails = billingAddressDetailsList.get(0);
				logger.info("BillAddrLine1 : "+billingAddressDetails.getBillAddrLine1());
				logger.info("BillAddrLine2 : "+billingAddressDetails.getBillAddrLine2());
				logger.info("BillAddrCity : "+billingAddressDetails.getBillAddrCity());
				logger.info("BillAddrState : "+billingAddressDetails.getBillAddrState());
				logger.info("BillAddrZip : "+billingAddressDetails.getBillAddrZip());
				logger.info("BillAddrPhone : "+billingAddressDetails.getBillAddrPhone());
				logger.info("BillAddrPhoneExt : "+billingAddressDetails.getBillAddrPhoneExt());
				logger.info("BillAddrFax : "+billingAddressDetails.getBillAddrFax());
				logger.info("BillEmailId : "+billingAddressDetails.getBillEmailId());
			}			
		}
		catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}

		return billingAddressDetails;
	}
	
	public String getInvoiceBillingPayment(String invoiceId, String asOfDate)
	{
		String paymentQry = "select sum(paymentAmt) as pay from Invoice_Payment where invoiceId='"+invoiceId+"' and paymentDate<='"+asOfDate+"' group by invoiceId";
		float payment=0.0f;
		try
		{					
			payment=jdbcTemplate.queryForObject(paymentQry,new Object[] {},new RowMapper<Float>() {

				public Float mapRow(ResultSet rs, int rowNum)throws SQLException {
					return rs.getFloat("pay");
				}
			});
		}
		catch (Exception e)
		{
			logger.error(e);
		}
		
		return roundUp(payment, 2);
	}
	
	public String getBillToMailId(String accountName)
	{
		String mailId=null;		
		logger.info("In getBillToMailId()...");

		String query_email = "select bill_email_id from Account a,Account_address aa where a.name=? and a.idAccount=aa.idAccount";

		try
		{
			mailId=jdbcTemplate.queryForObject(query_email,new Object[] {accountName},String.class);
		}
		catch(Exception e) 
		{
			logger.error(e);
		}

		return mailId;
	}
	
	public String roundUp(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.toString();
    }
}
