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

import act.reports.model.ReceiptDetails;
import act.reports.model.ReceiptTotals;
import act.reports.model.Receipts;
import act.reports.model.SearchCriteria;
import act.reports.model.SelectOption;
import act.reports.util.DateUtility;

@Repository("receiptsDAO")
public class ReceiptsDAO {

	private Logger logger=Logger.getLogger(ReceiptsDAO.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<SelectOption> getLotLocations()
	{
		logger.info("In ReceiptsDAO-getLotLocations()...");
		String accNameQry = "select distinct locationId,locationName from Lot__Locations";		  
		List<SelectOption> lotLocations=null;
		try
		{
			lotLocations =jdbcTemplate.query(accNameQry, new Object[] {}, new RowMapper<SelectOption>() {			
		      public SelectOption mapRow(ResultSet rs, int rowNum) throws SQLException {		     
		    	  SelectOption selectOption = new SelectOption();		    	
		    	  //selectOption.setOptionVal(rs.getString("locationId")!=null?rs.getString("locationId"):"");
		    	  selectOption.setOptionVal(rs.getString("locationName")!=null?rs.getString("locationName"):"");
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
	
	public Receipts getReceiptsDetails(SearchCriteria criteria){
		logger.info("In ReceiptsDAO-getReceiptsDetails()...");
		List<ReceiptDetails> receiptDetailsList = null;
		List<ReceiptTotals> receiptsTotalsList = null;
		Receipts receipts = new Receipts();

		try {
			String fromDate=criteria.getFromDate();
			String toDate=criteria.getToDate();
			fromDate = DateUtility.convertAsMySqlDateTime(fromDate);
			logger.info("fromDate in ReceiptsDAO-getReceiptsDetails() : "+fromDate);
			toDate = DateUtility.convertAsMySqlDateTime(toDate);
			toDate = toDate.replace("00:00:00", "23:59:59");
			logger.info("toDate in ReceiptsDAO-getReceiptsDetails() : "+toDate);
			final String lotLocation = criteria.getLocation();
			logger.info("lotLocation in ReceiptsDAO-getReceiptsDetails() : "+lotLocation);
			
			String receiptDetailsqQery = "";
			
			if(!lotLocation.equalsIgnoreCase("all")){
				/*receiptDetailsqQery = "select distinct i.dropOffLocation,ip.invoiceId,ib.accountName,ip.paymentType,ip.paymentAmt,ip.userId,ir.releaseTo from Invoice i,Invoice_Payment ip,"+ 
									  "Invoice_Billing ib,Invoice_Release ir where i.dropOffLocation='"+lotLocation+"' and ip.paymentDate>='"+fromDate+"' and ip.paymentDate<='"+toDate+
									  "' and ip.invoiceId = ib.invoiceId and ib.invoiceId=i.invoiceId and ir.invoiceId=i.invoiceId order by ip.invoiceId";*/
				
				receiptDetailsqQery = "select distinct i.dropOffLocation,ip.invoiceId,ib.accountName,ip.paymentType,ip.paymentAmt,ip.userId,ir.releaseTo from Invoice_Billing ib,Invoice_Payment ip," +
									  "Invoice i LEFT OUTER JOIN Invoice_Release ir ON ir.invoiceId=i.invoiceId where i.dropOffLocation='"+lotLocation+"' and ip.paymentDate>='"+fromDate+"' " +
									  "and ip.paymentDate<='"+toDate+"' and ip.invoiceId = ib.invoiceId and ib.invoiceId=i.invoiceId order by ip.invoiceId";
				
				
			}else{
				/*receiptDetailsqQery = "select distinct i.dropOffLocation,ip.invoiceId,ib.accountName,ip.paymentType,ip.paymentAmt,ip.userId,ir.releaseTo from Invoice i,Invoice_Payment ip,Invoice_Billing ib,Invoice_Release ir "+ 
						  			  "where ip.paymentDate>='"+fromDate+"' and ip.paymentDate<='"+toDate+"' and ip.invoiceId = ib.invoiceId and ib.invoiceId=i.invoiceId  and ir.invoiceId=i.invoiceId order by ip.invoiceId";*/
				
				receiptDetailsqQery = "select distinct i.dropOffLocation,ip.invoiceId,ib.accountName,ip.paymentType,ip.paymentAmt,ip.userId,ir.releaseTo from Invoice_Billing ib,Invoice_Payment ip,Invoice i LEFT OUTER JOIN " +
									  "Invoice_Release ir ON ir.invoiceId=i.invoiceId where ip.paymentDate>='"+fromDate+"' and ip.paymentDate<='"+toDate+"' and ip.invoiceId = ib.invoiceId and ib.invoiceId=i.invoiceId order by ip.invoiceId";
			}

			receiptDetailsList = jdbcTemplate.query(receiptDetailsqQery, new Object[] {}, new RowMapper<ReceiptDetails>() {

				public ReceiptDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

					ReceiptDetails receiptDetails = new ReceiptDetails();	
					receiptDetails.setLocation((rs.getString("dropOffLocation") != null) ? rs.getString("dropOffLocation") : "");
					receiptDetails.setInvoiceId((rs.getString("invoiceId") != null) ? rs.getString("invoiceId") : "");
					receiptDetails.setAccount((rs.getString("accountName") != null) ? rs.getString("accountName") : "");					
					receiptDetails.setPaymentMethod((rs.getString("paymentType") != null) ? rs.getString("paymentType") : "");
					receiptDetails.setAmount((rs.getString("paymentAmt") != null) ? rs.getString("paymentAmt") : "");
					receiptDetails.setUserId((rs.getString("userId") != null) ? rs.getString("userId") : "");
					receiptDetails.setBillTo((rs.getString("releaseTo") != null) ? rs.getString("releaseTo") : "");
					return receiptDetails;
				}
			});

			String receiptTotalsqQry = "";
			
			if(!lotLocation.equalsIgnoreCase("all")){
				receiptTotalsqQry = "select paymentType,SUM(paymentAmt) as total from Invoice_Payment where invoiceId IN (select ip.invoiceId from Invoice_Billing ib,Invoice_Payment ip,Invoice i LEFT OUTER JOIN "+ 
									"Invoice_Release ir ON ir.invoiceId=i.invoiceId where i.dropOffLocation='"+lotLocation+"' and ip.paymentDate>='"+fromDate+"' and ip.paymentDate<='"+toDate+"' and "+
									"ip.invoiceId=ib.invoiceId and ib.invoiceId=i.invoiceId) and paymentDate>='"+fromDate+"' and paymentDate<='"+toDate+"' group by paymentType";
			}else{
				receiptTotalsqQry = "select paymentType,SUM(paymentAmt) as total from Invoice_Payment where invoiceId IN (select ip.invoiceId from Invoice_Billing ib,Invoice_Payment ip,Invoice i LEFT OUTER JOIN Invoice_Release ir ON ir.invoiceId=i.invoiceId "+ 
					   				 " where ip.paymentDate>='"+fromDate+"' and ip.paymentDate<='"+toDate+"' and ip.invoiceId=ib.invoiceId and ib.invoiceId=i.invoiceId) and paymentDate>='"+fromDate+"' and paymentDate<='"+toDate+"' group by paymentType";
			}
			
			receiptsTotalsList = jdbcTemplate.query(receiptTotalsqQry, new Object[] {}, new RowMapper<ReceiptTotals>() {

				public ReceiptTotals mapRow(ResultSet rs, int rowNum) throws SQLException {

					ReceiptTotals receiptTotals = new ReceiptTotals();					
					receiptTotals.setPaymentMethod((rs.getString("paymentType") != null) ? rs.getString("paymentType") : "");
					receiptTotals.setAmount((rs.getString("total") != null) ? rs.getString("total") : "");
					return receiptTotals;
				}
			});

			float paymentTypeTotal = 0f;
			for(int i=0;i<receiptsTotalsList.size();i++){
				paymentTypeTotal += Float.parseFloat(receiptsTotalsList.get(i).getAmount());
			}
			logger.info("paymentTypeTotal : "+paymentTypeTotal);
			
			receipts.setFromDate(fromDate);
			receipts.setToDate(toDate);
			receipts.setLocation(lotLocation);
			receipts.setReportDate("");
			receipts.setReceiptDetails(receiptDetailsList);
			receipts.setReceiptsTotals(receiptsTotalsList);
			receipts.setPaymentTypeTotal(roundUp(paymentTypeTotal, 2));
			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
		return receipts;
	}
	
	public String roundUp(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.toString();
    }
}
