package act.reports.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import act.reports.model.DispatchedInvoiceDetails;
import act.reports.model.DispatchedInvoices;
import act.reports.model.SearchCriteria;
import act.reports.util.DateUtility;

@Repository("dispatchedInvoicesDAO")
public class DispatchedInvoicesDAO {

	private Logger logger=Logger.getLogger(DispatchedInvoicesDAO.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public DispatchedInvoices getDispatchedInvoices(SearchCriteria criteria){
		logger.info("In DispatchClearDAO-getDispatchedInvoices()...");
		List<DispatchedInvoiceDetails> dispatchedInvoiceDetailsList = null;
		DispatchedInvoices dispatchedInvoices = new DispatchedInvoices();

		try {
			String fromDate=criteria.getFromDate();
			String toDate=criteria.getToDate();
			fromDate = DateUtility.convertAsMySqlDateTime(fromDate);
			logger.info("fromDate in SummaryByReasonDAO-getSummaryByReason() : "+fromDate);
			toDate = DateUtility.convertAsMySqlDateTime(toDate);
			toDate = toDate.replace("00:00:00", "23:59:59");
			logger.info("toDate in SummaryByReasonDAO-getSummaryByReason() : "+toDate);

			String dispatchedInvoicesqQery = "select a.isPORequired,sc.callRecevierId,i.clearedByDispatcher,i.priority,i.driverId,u.userId,u.Employee_idEmployee,i.assignedBy,ib.PONo from Account a,UserAuth u,"+ 
						   					 "ServiceCallInfo sc,Invoice i,Invoice_Billing ib where i.clearedByDispatcher='1' and i.serviceCallId = sc.serviceCallId and i.invoiceId = ib.invoiceId and "+
						   					 "i.dispatchTime>='"+fromDate+"' and i.dispatchTime<='"+toDate+"'and i.driverId=u.Employee_idEmployee and a.idAccount=i.accountId order by sc.callRecevierId";


			dispatchedInvoiceDetailsList = jdbcTemplate.query(dispatchedInvoicesqQery, new Object[] {}, new RowMapper<DispatchedInvoiceDetails>() {

				public DispatchedInvoiceDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

					DispatchedInvoiceDetails dispatchedInvoiceDetails = new DispatchedInvoiceDetails();					
					dispatchedInvoiceDetails.setCallTakerId((rs.getString("callRecevierId") != null) ? rs.getString("callRecevierId") : " ");
					dispatchedInvoiceDetails.setDispatchId((rs.getString("assignedBy") != null) ? rs.getString("assignedBy") : " ");
					dispatchedInvoiceDetails.setDriverId((rs.getString("userId") != null) ? rs.getString("userId") : " ");
					
					String clearedByDispatcher=(rs.getString("clearedByDispatcher") != null) ? rs.getString("clearedByDispatcher") : ""; 
					String priority=(rs.getString("priority") != null) ? rs.getString("priority") : ""; 
					if(clearedByDispatcher.equalsIgnoreCase("1") && priority.equalsIgnoreCase("1"))
						dispatchedInvoiceDetails.setNoOwnerInfo("Y");
					else
						dispatchedInvoiceDetails.setNoOwnerInfo("N");
					
					String isPORequired = (rs.getString("isPORequired") != null) ? rs.getString("isPORequired") : ""; 
					String poNo = (rs.getString("PONo") != null) ? rs.getString("PONo") : ""; 
					if(clearedByDispatcher.equalsIgnoreCase("1") && !priority.equalsIgnoreCase("1")){
						if(!isPORequired.isEmpty() && isPORequired.equalsIgnoreCase("1") && poNo.isEmpty())
							dispatchedInvoiceDetails.setPoRequired("Y");
						else
							dispatchedInvoiceDetails.setPoRequired("N");
					}
					else
						dispatchedInvoiceDetails.setPoRequired("N");
					
					return dispatchedInvoiceDetails;
				}
			});

			dispatchedInvoices.setFromDate(fromDate);
			dispatchedInvoices.setToDate(toDate);
			dispatchedInvoices.setDispatchedInvoiceDetails(dispatchedInvoiceDetailsList);
			dispatchedInvoices.setTotalInvoices(Integer.toString(dispatchedInvoiceDetailsList.size()));			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}		
		return dispatchedInvoices;
	}
}
