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

import act.reports.model.AccountNames;
import act.reports.model.InvoicePaymentDetailsUpdate;
import act.reports.model.MultipleInvoicePayment;
import act.reports.model.MultipleInvoicePaymentDetails;
import act.reports.model.MultipleInvoicePaymentDetailsUpdate;
import act.reports.model.SearchCriteria;
import act.reports.model.SelectOption;
import act.reports.model.ServiceStatus;
import act.reports.util.DateUtility;

@Repository("multipleInvoicePaymentDAO")
public class MultipleInvoicePaymentDAO {

	private Logger logger=Logger.getLogger(MultipleInvoicePaymentDAO.class);

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
	
	public List<SelectOption> getLotLocations()
	{
		logger.info("In getLotLocations()...");
		String accNameQry = "select distinct locationId,locationName from Lot__Locations";		  
		List<SelectOption> lotLocations=null;
		try
		{
			lotLocations =jdbcTemplate.query(accNameQry, new Object[] {}, new RowMapper<SelectOption>() {			
		      public SelectOption mapRow(ResultSet rs, int rowNum) throws SQLException {		     
		    	  SelectOption selectOption = new SelectOption();		    	
		    	  selectOption.setOptionVal(rs.getString("locationId")!=null?rs.getString("locationId"):"");
		    	  selectOption.setOptionTxt(rs.getString("locationName")!=null?rs.getString("locationName"):"");		    	
		    	return selectOption;
		      }
		    });
	     }
		 catch (Exception e) {
		   logger.error(e);
		  }
		  logger.info("lotLocations.size() : "+lotLocations.size());
		  return lotLocations;
	}
	
	public MultipleInvoicePayment getInvoicesDetails(SearchCriteria criteria){
		logger.info("In MultipleInvoicePaymentDAO-getInvoicesDetails()...");
		MultipleInvoicePayment multipleInvoicePayment = new MultipleInvoicePayment();
		List<MultipleInvoicePaymentDetails> multipleInvoicePaymentDetailsList = null;
		List<MultipleInvoicePaymentDetails> multipleInvoicePaymentDetailsListFinal = new ArrayList<MultipleInvoicePaymentDetails>();
		try{
			String accountName=criteria.getAccountName();
			logger.info("accountName in MultipleInvoicePaymentDAO-getAuctionListInvoicesDetails() : "+accountName);

			String invoiceDetailsQuery = "select i.invoiceId,sc.callCreatedTime,iv.vehicle_Year,iv.vehicle_Make,iv.vehicle_Model,iv.vehicle_VIN,SUM(ib.totalCharge) as totalCharge from Account a,"+ 
						   				 "Invoice_Billing ib,Invoice i LEFT OUTER JOIN Invoice_Vehicle iv ON i.invoiceId=iv.invoiceId LEFT OUTER JOIN ServiceCallInfo sc ON sc.serviceCallId=i.serviceCallId "+
						   				 "where a.name='"+accountName+"' and a.idAccount=i.accountId and i.invoiceId=ib.invoiceId group by i.invoiceId";

			multipleInvoicePaymentDetailsList = jdbcTemplate.query(invoiceDetailsQuery, new Object[] {}, new RowMapper<MultipleInvoicePaymentDetails>() {

				public MultipleInvoicePaymentDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

					MultipleInvoicePaymentDetails multipleInvoicePaymentDetails = new MultipleInvoicePaymentDetails();					
					String invoiceId = (rs.getString("invoiceId") != null) ? rs.getString("invoiceId") : " ";
					multipleInvoicePaymentDetails.setInvoiceId(invoiceId);			
					
					String serviceCallDate = (rs.getString("callCreatedTime") != null) ? rs.getString("callCreatedTime") : " ";
					if(!serviceCallDate.isEmpty() && serviceCallDate.trim().length()>10){
						serviceCallDate=DateUtility.convertToDateTimeFormat(serviceCallDate);
						serviceCallDate = serviceCallDate.substring(0, 10);
					}
					multipleInvoicePaymentDetails.setServiceCallDate(serviceCallDate);
					multipleInvoicePaymentDetails.setVehicleYear((rs.getString("vehicle_Year") != null) ? rs.getString("vehicle_Year") : " ");
					multipleInvoicePaymentDetails.setVehicleMake((rs.getString("vehicle_Make") != null) ? rs.getString("vehicle_Make") : " ");
					multipleInvoicePaymentDetails.setVehicleModel((rs.getString("vehicle_Model") != null) ? rs.getString("vehicle_Model") : " ");
					multipleInvoicePaymentDetails.setVehicleVIN((rs.getString("vehicle_VIN") != null) ? rs.getString("vehicle_VIN") : " ");
					
					String totalCharge = (rs.getString("totalCharge") != null) ? rs.getString("totalCharge") : "0.00";
					
					Float paymentAmt = 0f;
					if(invoiceId.trim().length()>0)
						paymentAmt = getInvoiceBillingPayment(invoiceId);
					
					logger.info("totalCharge = "+totalCharge);
					logger.info("paymentAmt = "+paymentAmt);
					
					Float balance = Float.parseFloat(totalCharge) - paymentAmt; 
					multipleInvoicePaymentDetails.setBalanceAmt(roundUp(balance, 2));
					multipleInvoicePaymentDetails.setPaymentAmt(" ");
					multipleInvoicePaymentDetails.setPaidInFull(" ");
					
					return multipleInvoicePaymentDetails;
				}
			});	
			
			for(int i=0;i<multipleInvoicePaymentDetailsList.size();i++){
				MultipleInvoicePaymentDetails multipleInvoicePaymentDetails = multipleInvoicePaymentDetailsList.get(i);
				float balanceAmt = Float.parseFloat(multipleInvoicePaymentDetails.getBalanceAmt());
				if(balanceAmt > 0)
					multipleInvoicePaymentDetailsListFinal.add(multipleInvoicePaymentDetails);
			}
			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
		multipleInvoicePayment.setTotalOpenInvoices(Integer.toString(multipleInvoicePaymentDetailsListFinal.size()));
		multipleInvoicePayment.setMultipleInvoicePaymentDetailsList(multipleInvoicePaymentDetailsListFinal);
		return multipleInvoicePayment;
	}
	
	public MultipleInvoicePayment updateInvoicePayment(MultipleInvoicePaymentDetailsUpdate multipleInvoicePaymentDetailsUpdate){
		logger.info("In updateInvoicePayment(MultipleInvoicePaymentDetailsList multipleInvoicePaymentDetailsList)...");
		List<MultipleInvoicePaymentDetails> multipleInvoicePaymentDetailsList = null;
		MultipleInvoicePayment multipleInvoicePayment = new MultipleInvoicePayment();
		List<MultipleInvoicePaymentDetails> multipleInvoicePaymentDetailsListFinal = new ArrayList<MultipleInvoicePaymentDetails>();
		String accountName = "";
		try {
			String query = "insert into Invoice_Payment (invoiceId,paymentType,paymentAmt,chequeNo,CCTxNo,userId,paymentLocation,paymentDate) values(?,?,?,?,?,?,?,?)";
			int j=0;
			for(int i=0;i<multipleInvoicePaymentDetailsUpdate.getInvoiceId().size();i++){
				String invoiceId = multipleInvoicePaymentDetailsUpdate.getInvoiceId().get(i);
				logger.info("invoiceId : "+invoiceId);
				String paymentType = multipleInvoicePaymentDetailsUpdate.getPaymentType().get(i);
				logger.info("paymentType : "+paymentType);
				String paymentAmt = multipleInvoicePaymentDetailsUpdate.getPaymentAmt().get(i);
				if(paymentAmt.trim().length()==0 && !paymentAmt.contains("."))
					paymentAmt = "0.00";
				else if(paymentAmt.trim().length()>0 && !paymentAmt.contains("."))
					paymentAmt = paymentAmt+".00";
				logger.info("paymentAmt : "+paymentAmt);
				String chequeNo = multipleInvoicePaymentDetailsUpdate.getChequeNo().get(i);
				logger.info("chequeNo : "+chequeNo);
				String ccTxNo = multipleInvoicePaymentDetailsUpdate.getCcTxNo().get(i);
				logger.info("ccTxNo : "+ccTxNo);
				String userId = multipleInvoicePaymentDetailsUpdate.getUserId().get(i);
				logger.info("userId : "+userId);
				String paymentLoc = multipleInvoicePaymentDetailsUpdate.getPaymentLoc().get(i);
				logger.info("paymentLoc : "+paymentLoc);
				if(!invoiceId.isEmpty() && invoiceId!=null && !paymentAmt.equalsIgnoreCase("0.00")){
					j=jdbcTemplate.update(query,new Object[] {
							invoiceId,
							paymentType,
							paymentAmt,
							chequeNo,
							ccTxNo,
							userId,
							paymentLoc,
							DateUtility.getCurrentMysqlDateTime()							
						 });
				}
			}
			
			if(j>0)
				multipleInvoicePayment.setMsg("Invoice payment details updated Successfully.");				
			else
				multipleInvoicePayment.setMsg("Invoice payment details not updated Successfully.");
			
			accountName = multipleInvoicePaymentDetailsUpdate.getAccountName().get(0);
			if(accountName!=null && !accountName.trim().equalsIgnoreCase("")){
				/*String invoiceDetailsQuery = "select i.invoiceId,sc.callCreatedTime,iv.vehicle_Year,iv.vehicle_Make,iv.vehicle_Model,iv.vehicle_VIN,SUM(ib.totalCharge) as totalCharge from "+ 
	   										 "Invoice i,ServiceCallInfo sc,Account a,Invoice_Billing ib,Invoice_Vehicle iv where a.name='"+accountName+"' and i.callStatus!='Cleared' and "+
	   										 "a.idAccount=i.accountId and i.invoiceId=ib.invoiceId and ib.invoiceId=iv.invoiceId and sc.serviceCallId=i.serviceCallId group by i.invoiceId";*/
				
				String invoiceDetailsQuery = "select i.invoiceId,sc.callCreatedTime,iv.vehicle_Year,iv.vehicle_Make,iv.vehicle_Model,iv.vehicle_VIN,SUM(ib.totalCharge) as totalCharge from Account a,"+ 
		   				 					 "Invoice_Billing ib,Invoice i LEFT OUTER JOIN Invoice_Vehicle iv ON i.invoiceId=iv.invoiceId LEFT OUTER JOIN ServiceCallInfo sc ON sc.serviceCallId=i.serviceCallId "+
		   				 					 "where a.name='"+accountName+"' and a.idAccount=i.accountId and i.invoiceId=ib.invoiceId group by i.invoiceId";

				multipleInvoicePaymentDetailsList = jdbcTemplate.query(invoiceDetailsQuery, new Object[] {}, new RowMapper<MultipleInvoicePaymentDetails>() {

					public MultipleInvoicePaymentDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

						MultipleInvoicePaymentDetails multipleInvoicePaymentDetails = new MultipleInvoicePaymentDetails();	
						String invoiceId = (rs.getString("invoiceId") != null) ? rs.getString("invoiceId") : " ";
						multipleInvoicePaymentDetails.setInvoiceId(invoiceId);			

						String serviceCallDate = (rs.getString("callCreatedTime") != null) ? rs.getString("callCreatedTime") : " ";
						if(!serviceCallDate.isEmpty() && serviceCallDate.trim().length()>10)
							multipleInvoicePaymentDetails.setServiceCallDate(serviceCallDate.substring(0, 10));
						else
							multipleInvoicePaymentDetails.setServiceCallDate(serviceCallDate);

						multipleInvoicePaymentDetails.setVehicleYear((rs.getString("vehicle_Year") != null) ? rs.getString("vehicle_Year") : " ");
						multipleInvoicePaymentDetails.setVehicleMake((rs.getString("vehicle_Make") != null) ? rs.getString("vehicle_Make") : " ");
						multipleInvoicePaymentDetails.setVehicleModel((rs.getString("vehicle_Model") != null) ? rs.getString("vehicle_Model") : " ");
						multipleInvoicePaymentDetails.setVehicleVIN((rs.getString("vehicle_VIN") != null) ? rs.getString("vehicle_VIN") : " ");

						String totalCharge = (rs.getString("totalCharge") != null) ? rs.getString("totalCharge") : "0.00";
						
						Float paymentAmt = 0.0f;
						if(invoiceId.trim().length()>0)
							paymentAmt = getInvoiceBillingPayment(invoiceId);
						
						logger.info("totalCharge = "+totalCharge);
						logger.info("paymentAmt = "+paymentAmt);
						
						Float balance = Float.parseFloat(totalCharge) - paymentAmt; 
						multipleInvoicePaymentDetails.setBalanceAmt(roundUp(balance, 2));
						multipleInvoicePaymentDetails.setPaymentAmt(" ");
						multipleInvoicePaymentDetails.setPaidInFull(" ");

						return multipleInvoicePaymentDetails;
					}
				});	
				
				for(int i=0;i<multipleInvoicePaymentDetailsList.size();i++){
					MultipleInvoicePaymentDetails multipleInvoicePaymentDetails = multipleInvoicePaymentDetailsList.get(i);
					float balanceAmt = Float.parseFloat(multipleInvoicePaymentDetails.getBalanceAmt());
					if(balanceAmt > 0)
						multipleInvoicePaymentDetailsListFinal.add(multipleInvoicePaymentDetails);
				}
			}
		}catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}		
		
		multipleInvoicePayment.setAccountName(accountName);
		multipleInvoicePayment.setTotalOpenInvoices(Integer.toString(multipleInvoicePaymentDetailsListFinal.size()));
		multipleInvoicePayment.setMultipleInvoicePaymentDetailsList(multipleInvoicePaymentDetailsListFinal);
		return multipleInvoicePayment;		
	}
	
	public ServiceStatus updateInvoicePayment(List<InvoicePaymentDetailsUpdate> invoicePaymentUpdateList){
		logger.info("In updateInvoicePayment(List<InvoicePaymentDetailsUpdate> invoicePaymentUpdateList)...");
		ServiceStatus serviceStatus = new ServiceStatus();
		InvoicePaymentDetailsUpdate invoicePaymentDetailsUpdate = null;
		try {
			String query = "insert into Invoice_Payment (invoiceId,paymentType,paymentAmt,chequeNo,CCTxNo,userId,paymentLocation,paymentDate) values(?,?,?,?,?,?,?,?)";
			int j=0;
			for(int i=0;i<invoicePaymentUpdateList.size();i++){
				invoicePaymentDetailsUpdate = invoicePaymentUpdateList.get(i);
				String invoiceId = invoicePaymentDetailsUpdate.getInvoiceId();
				logger.info("invoiceId : "+invoiceId);
				String paymentType = invoicePaymentDetailsUpdate.getPaymentType();
				logger.info("paymentType : "+paymentType);
				String paymentAmt = invoicePaymentDetailsUpdate.getPaymentAmt();
				logger.info("paymentAmt : "+paymentAmt);
				String chequeNo = invoicePaymentDetailsUpdate.getChequeNo();
				logger.info("chequeNo : "+chequeNo);
				String ccTxNo = invoicePaymentDetailsUpdate.getCcTxNo();
				logger.info("ccTxNo : "+ccTxNo);
				String userId = invoicePaymentDetailsUpdate.getUserId();
				logger.info("userId : "+userId);
				String paymentLoc = invoicePaymentDetailsUpdate.getPaymentLoc();
				logger.info("paymentLoc : "+paymentLoc);
				String paymentDate = invoicePaymentDetailsUpdate.getPaymentDate();
				logger.info("paymentDate : "+paymentDate);
				if(!invoiceId.isEmpty() && invoiceId!=null && !paymentAmt.equalsIgnoreCase("0.00")){
					j=jdbcTemplate.update(query,new Object[] {
							invoiceId,
							paymentType,
							paymentAmt,
							chequeNo,
							ccTxNo,
							userId,
							paymentLoc,
							paymentDate							
						 });
				}
			}
			
			if(j>0){
				serviceStatus.setMessage("Invoice payment details updated Successfully.");
				serviceStatus.setStatus("Success");
			}								
			else{
				serviceStatus.setMessage("Invoice payment details not updated Successfully.");
				serviceStatus.setStatus("Fail");
			}			
		}
		catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}		
		
		return serviceStatus;		
	}
	
	public Float getInvoiceBillingPayment(String invoiceId)
	{
		String paymentQry = "select sum(paymentAmt) from Invoice_Payment where invoiceId=?";
		float payment=(float)0;
		try
		{					
			payment=jdbcTemplate.queryForObject(paymentQry,new Object[] {invoiceId},Float.class);
		}
		catch (NullPointerException npe)
		{
			logger.error(npe);
			payment = 0.0f;
		}
		catch (Exception e)
		{
			logger.error(e);
		}

		return payment;
	}

	
	public String roundUp(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.toString();
    }
}