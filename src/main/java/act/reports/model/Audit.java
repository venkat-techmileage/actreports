package act.reports.model;

import java.util.List;

public class Audit {

	private String fromDate = "";
	private String toDate = "";
	List<AuditDetails> auditDetails;
	List<AuditTotals> auditTotals;
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public List<AuditDetails> getAuditDetails() {
		return auditDetails;
	}
	public void setAuditDetails(List<AuditDetails> auditDetails) {
		this.auditDetails = auditDetails;
	}
	public List<AuditTotals> getAuditTotals() {
		return auditTotals;
	}
	public void setAuditTotals(List<AuditTotals> auditTotals) {
		this.auditTotals = auditTotals;
	}

	
}