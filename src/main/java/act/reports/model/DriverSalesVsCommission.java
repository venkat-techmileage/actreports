package act.reports.model;


public class DriverSalesVsCommission {

	private String driverId = "";
	private String driverName = "";
	private String fromDate = "";
	private String toDate = "";
	private DriverSalesDetails driverSalesDetails;
	private DriverCommissionDetails driverCommissionDetails;
	private String totalInvoices = "";
	
	public String getDriverId() {
		return driverId;
	}
	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
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
	public DriverSalesDetails getDriverSalesDetails() {
		return driverSalesDetails;
	}
	public void setDriverSalesDetails(DriverSalesDetails driverSalesDetails) {
		this.driverSalesDetails = driverSalesDetails;
	}
	public DriverCommissionDetails getDriverCommissionDetails() {
		return driverCommissionDetails;
	}
	public void setDriverCommissionDetails(
			DriverCommissionDetails driverCommissionDetails) {
		this.driverCommissionDetails = driverCommissionDetails;
	}
	public String getTotalInvoices() {
		return totalInvoices;
	}
	public void setTotalInvoices(String totalInvoices) {
		this.totalInvoices = totalInvoices;
	}			
}