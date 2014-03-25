package act.reports.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import act.reports.model.Audit;
import act.reports.model.AuditDetails;
import act.reports.model.AuditTotals;
import act.reports.model.SearchCriteria;
import act.reports.util.DateUtility;

@Repository("auditDAO")
public class AuditDAO {

	private Logger logger=Logger.getLogger(AuditDAO.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public Audit getAuditDetails(SearchCriteria criteria){
		logger.info("In AuditDAO-getAuditDetails()...");
		List<AuditDetails> auditDetailsList = null;
		List<AuditTotals> auditTotalsList = null;
		Audit auditLog = new Audit();

		try {
			String fromDate=criteria.getFromDate();
			String toDate=criteria.getToDate();
			fromDate = DateUtility.convertAsMySqlDateTime(fromDate);
			logger.info("fromDate in AuditDAO-getAuditDetails() : "+fromDate);
			toDate = DateUtility.convertAsMySqlDateTime(toDate);
			toDate = toDate.replace("00:00:00", "23:59:59");
			logger.info("toDate in AuditDAO-getAuditDetails() : "+toDate);

			String auditDetailsqQery = "select userId,changeDateTime,invoiceId,originalTotalCharges,newTotalCharges "
										+ "from Invoice_Billing_Audit where changeDateTime BETWEEN '"+fromDate+"' and '"+toDate+"'";


			auditDetailsList = jdbcTemplate.query(auditDetailsqQery, new Object[] {}, new RowMapper<AuditDetails>() {

				public AuditDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

					AuditDetails auditDetails = new AuditDetails();					
					auditDetails.setUserId((rs.getString("userId") != null) ? rs.getString("userId") : " ");
					
					String changeDateTime = (rs.getString("changeDateTime") != null) ? rs.getString("changeDateTime") : " ";
					if(!changeDateTime.isEmpty()){
						changeDateTime=DateUtility.convertToDateTimeFormat(changeDateTime);
						auditDetails.setChangeDate(changeDateTime.substring(0, 10));
						auditDetails.setChangeTime(changeDateTime.substring(11, 16));
					}
					
					auditDetails.setInvoice((rs.getString("invoiceId") != null) ? rs.getString("invoiceId") : " ");
					auditDetails.setOriginalTotalCharges((rs.getString("originalTotalCharges") != null) ? rs.getString("originalTotalCharges") : "0.00");
					auditDetails.setNewTotalCharges((rs.getString("newTotalCharges") != null) ? rs.getString("newTotalCharges") : "0.00");
					
					Float chargesDifference = Float.parseFloat(auditDetails.getOriginalTotalCharges()) - Float.parseFloat(auditDetails.getNewTotalCharges());
					auditDetails.setChargesDifference(Float.toString(chargesDifference));

					return auditDetails;
				}
			});

			String auditTotalsqQery = "select userId,count(invoiceId) as totalChanges,SUM(originalTotalCharges) "
					                  + "as orgTtlChrgs,SUM(newTotalCharges)as sumNewTtlChrgs from Invoice_Billing_Audit where "
					                  + "changeDateTime BETWEEN '"+fromDate+"' and '"+toDate+"' group by userId";

			auditTotalsList = jdbcTemplate.query(auditTotalsqQery, new Object[] {}, new RowMapper<AuditTotals>() {

				public AuditTotals mapRow(ResultSet rs, int rowNum) throws SQLException {

					AuditTotals auditTotals = new AuditTotals();					
					auditTotals.setUserId((rs.getString("userId") != null) ? rs.getString("userId") : "");
					auditTotals.setTotalChanges((rs.getString("totalChanges") != null) ? rs.getString("totalChanges") : "");
					auditTotals.setOriginalTotalCharges((rs.getString("orgTtlChrgs") != null) ? rs.getString("orgTtlChrgs") : "");
					auditTotals.setNewTotalCharges((rs.getString("sumNewTtlChrgs") != null) ? rs.getString("sumNewTtlChrgs") : "");
					
					Float chargesDifference = Float.parseFloat(auditTotals.getOriginalTotalCharges()) - Float.parseFloat(auditTotals.getNewTotalCharges());
					auditTotals.setChargesDifference(Float.toString(chargesDifference));
					return auditTotals;
				}
			});
			
			auditLog.setFromDate(fromDate);
			auditLog.setToDate(toDate);
			auditLog.setAuditDetails(auditDetailsList);
			auditLog.setAuditTotals(auditTotalsList);
			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
		return auditLog;
	}
}
