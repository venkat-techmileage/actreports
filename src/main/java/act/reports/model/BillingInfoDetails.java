package act.reports.model;

public class BillingInfoDetails {
	
	private String date = "";
	private String invoiceId = "";
	private String commUnitNo = "";
	private String vehicleYear = "";
	private String vehicleMake = "";
	private String vehicleModel = "";
	private String poNo = "";
	private String vehicleVIN = "";
	private String location = "";
	private String amountOwed = "";
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getCommUnitNo() {
		return commUnitNo;
	}
	public void setCommUnitNo(String commUnitNo) {
		this.commUnitNo = commUnitNo;
	}
	public String getVehicleYear() {
		return vehicleYear;
	}
	public void setVehicleYear(String vehicleYear) {
		this.vehicleYear = vehicleYear;
	}
	public String getVehicleMake() {
		return vehicleMake;
	}
	public void setVehicleMake(String vehicleMake) {
		this.vehicleMake = vehicleMake;
	}
	public String getVehicleModel() {
		return vehicleModel;
	}
	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}
	public String getPoNo() {
		return poNo;
	}
	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}
	public String getVehicleVIN() {
		return vehicleVIN;
	}
	public void setVehicleVIN(String vehicleVIN) {
		this.vehicleVIN = vehicleVIN;
	}	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getAmountOwed() {
		return amountOwed;
	}
	public void setAmountOwed(String amountOwed) {
		this.amountOwed = amountOwed;
	}		
}