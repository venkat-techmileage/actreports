package act.reports.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import act.reports.model.MissingPODetails;
import act.reports.model.MissingPODetailsList;
import act.reports.model.MissingPOs;
import act.reports.util.DateUtility;

@Repository("missingPOsDAO")
public class MissingPOsDAO {

	private Logger logger=Logger.getLogger(MissingPOsDAO.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public MissingPOs getMissingPOs(){
		logger.info("In MissingPOsDAO-getMissingPOs()...");
		List<MissingPODetails> missingPODetailsList = null;
		MissingPOs missingPOs = new MissingPOs();

		try {
			
			String missingPOsQery = "select i.invoiceId,sc.callCreatedTime,iv.vehicle_Year,iv.vehicle_Make,iv.vehicle_Model,iv.vehicle_VIN,a.name,ib.totalCharge "
									+ "from Account a,ServiceCallInfo sc,Invoice i,Invoice_Vehicle iv,Invoice_Billing ib where a.isPORequired='1' and (ib.PONo='' || ib.PONo IS NULL) "
									+ "and a.idAccount=i.accountId and i.serviceCallId=sc.serviceCallId and i.invoiceId=ib.invoiceId and ib.invoiceId=iv.invoiceId"; 
					
			missingPODetailsList = jdbcTemplate.query(missingPOsQery, new Object[] {}, new RowMapper<MissingPODetails>() {

				public MissingPODetails mapRow(ResultSet rs, int rowNum) throws SQLException {

					MissingPODetails missingPODetails = new MissingPODetails();					
					missingPODetails.setInvoiceId((rs.getString("invoiceId") != null) ? rs.getString("invoiceId") : "");
					
					String serviceCallDate = (rs.getString("callCreatedTime") != null) ? rs.getString("callCreatedTime") : "";
					if(!serviceCallDate.isEmpty() && serviceCallDate.length()>10){
						serviceCallDate=DateUtility.convertToDateTimeFormat(serviceCallDate);
						serviceCallDate=serviceCallDate.substring(0, 10);
					}
					missingPODetails.setServiceCallDate(serviceCallDate);
					missingPODetails.setYear((rs.getString("vehicle_Year") != null) ? rs.getString("vehicle_Year") : "");
					missingPODetails.setMake((rs.getString("vehicle_Make") != null) ? rs.getString("vehicle_Make") : "");
					missingPODetails.setModel((rs.getString("vehicle_Model") != null) ? rs.getString("vehicle_Model") : "");
					missingPODetails.setVin((rs.getString("vehicle_VIN") != null) ? rs.getString("vehicle_VIN") : "");
					missingPODetails.setBilledTo((rs.getString("name") != null) ? rs.getString("name") : "");
					missingPODetails.setAmount((rs.getString("totalCharge") != null) ? rs.getString("totalCharge") : "");
					missingPODetails.setPoNumber("");
					
					return missingPODetails;
				}
			});

			missingPOs.setMissingPODetailsList(missingPODetailsList);
			missingPOs.setTotalInvoices(Integer.toString(missingPODetailsList.size()));			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}		
		return missingPOs;
	}
	
	public MissingPOs saveMissingPOs(MissingPODetailsList missingPODetailsList){
		logger.info("In saveMissingPOs(MissingPODetailsList missingPODetailsList)...");
		List<MissingPODetails> missingPOsList = null;
		MissingPOs missingPOs = new MissingPOs();

		try {
			String updateQry = "";
			for(int i=0;i<missingPODetailsList.getPoNumber().size();i++){
				String invoiceId = missingPODetailsList.getInvoiceId().get(i);
				logger.info("invoiceId : "+invoiceId);
				String poNo = missingPODetailsList.getPoNumber().get(i);
				logger.info("poNo : "+poNo);
				if(!poNo.isEmpty() && poNo!=null){
					updateQry = "update Invoice_Billing set PONo='"+poNo+"' where invoiceId='"+invoiceId+"'";
					int noRowsUpdated = jdbcTemplate.update(updateQry);
					logger.info(noRowsUpdated+"-row's' updated.");
				}
			}
			
			String missingPOsQuery = "select i.invoiceId,sc.callCreatedTime,iv.vehicle_Year,iv.vehicle_Make,iv.vehicle_Model,iv.vehicle_VIN,a.name,ib.totalCharge "
									+ "from Account a,ServiceCallInfo sc,Invoice i,Invoice_Vehicle iv,Invoice_Billing ib where a.isPORequired='1' and (ib.PONo='' || ib.PONo IS NULL) "
									+ "and a.idAccount=i.accountId and i.serviceCallId=sc.serviceCallId and i.invoiceId=ib.invoiceId and ib.invoiceId=iv.invoiceId"; 
					
			missingPOsList = jdbcTemplate.query(missingPOsQuery, new Object[] {}, new RowMapper<MissingPODetails>() {

				public MissingPODetails mapRow(ResultSet rs, int rowNum) throws SQLException {

					MissingPODetails missingPODetails = new MissingPODetails();					
					missingPODetails.setInvoiceId((rs.getString("invoiceId") != null) ? rs.getString("invoiceId") : "");
					
					String serviceCallDate = (rs.getString("callCreatedTime") != null) ? rs.getString("callCreatedTime") : "";
					if(!serviceCallDate.isEmpty() && serviceCallDate.length()>10)
						missingPODetails.setServiceCallDate(serviceCallDate.substring(0, 10));
					
					missingPODetails.setYear((rs.getString("vehicle_Year") != null) ? rs.getString("vehicle_Year") : "");
					missingPODetails.setMake((rs.getString("vehicle_Make") != null) ? rs.getString("vehicle_Make") : "");
					missingPODetails.setModel((rs.getString("vehicle_Model") != null) ? rs.getString("vehicle_Model") : "");
					missingPODetails.setVin((rs.getString("vehicle_VIN") != null) ? rs.getString("vehicle_VIN") : "");
					missingPODetails.setBilledTo((rs.getString("name") != null) ? rs.getString("name") : "");
					missingPODetails.setAmount((rs.getString("totalCharge") != null) ? rs.getString("totalCharge") : "");
					missingPODetails.setPoNumber("");
					
					return missingPODetails;
				}
			});

			missingPOs.setMissingPODetailsList(missingPOsList);
			missingPOs.setTotalInvoices(Integer.toString(missingPOsList.size()));			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}		
		return missingPOs;
	}
}
