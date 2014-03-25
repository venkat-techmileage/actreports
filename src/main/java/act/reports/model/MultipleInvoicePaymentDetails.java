package act.reports.model;

public class MultipleInvoicePaymentDetails {
	
	private String invoiceId = "";
	private String serviceCallDate = "";
	private String vehicleYear = "";
	private String vehicleMake = "";
	private String vehicleModel = "";
	private String vehicleVIN = "";
	private String balanceAmt = "";
	private String paymentAmt = "";
	private String paidInFull = "";
	
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
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
	public String getVehicleVIN() {
		return vehicleVIN;
	}
	public void setVehicleVIN(String vehicleVIN) {
		this.vehicleVIN = vehicleVIN;
	}
	public String getServiceCallDate() {
		return serviceCallDate;
	}
	public void setServiceCallDate(String serviceCallDate) {
		this.serviceCallDate = serviceCallDate;
	}
	public String getBalanceAmt() {
		return balanceAmt;
	}
	public void setBalanceAmt(String balanceAmt) {
		this.balanceAmt = balanceAmt;
	}
	public String getPaymentAmt() {
		return paymentAmt;
	}
	public void setPaymentAmt(String paymentAmt) {
		this.paymentAmt = paymentAmt;
	}
	public String getPaidInFull() {
		return paidInFull;
	}
	public void setPaidInFull(String paidInFull) {
		this.paidInFull = paidInFull;
	}				
}