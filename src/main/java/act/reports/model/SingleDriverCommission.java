package act.reports.model;

import java.util.List;

public class SingleDriverCommission {

	private String fromDate = "";
	private String toDate = "";
	private String driverName = "";
	private String driverId = "";
	private String totalInvoices = "";
	private String totalCharges = "";
	private String totalCommission = "";
	List<SingleDriverCommissionDetails> singleDriverCommissionDetails;
	List<TowTypeSummaryDetails> towTypeSummaryDetails;
	
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
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getDriverId() {
		return driverId;
	}
	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
	public String getTotalInvoices() {
		return totalInvoices;
	}
	public void setTotalInvoices(String totalInvoices) {
		this.totalInvoices = totalInvoices;
	}
	public String getTotalCharges() {
		return totalCharges;
	}
	public void setTotalCharges(String totalCharges) {
		this.totalCharges = totalCharges;
	}
	public String getTotalCommission() {
		return totalCommission;
	}
	public void setTotalCommission(String totalCommission) {
		this.totalCommission = totalCommission;
	}
	public List<SingleDriverCommissionDetails> getSingleDriverCommissionDetails() {
		return singleDriverCommissionDetails;
	}
	public void setSingleDriverCommissionDetails(
			List<SingleDriverCommissionDetails> singleDriverCommissionDetails) {
		this.singleDriverCommissionDetails = singleDriverCommissionDetails;
	}
	public List<TowTypeSummaryDetails> getTowTypeSummaryDetails() {
		return towTypeSummaryDetails;
	}
	public void setTowTypeSummaryDetails(
			List<TowTypeSummaryDetails> towTypeSummaryDetails) {
		this.towTypeSummaryDetails = towTypeSummaryDetails;
	}		
}