package act.reports.model;

import java.util.List;

public class MultipleInvoicePaymentDetailsList {

	private List<String> invoiceId;
	private List<String> serviceCallDate;
	private List<String> vehicleYear;
	private List<String> vehicleMake;
	private List<String> vehicleModel;
	private List<String> vehicleVIN;
	private List<String> balanceAmt;
	private List<String> paymentAmt;
	private List<String> paidInFull;
	
	public List<String> getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(List<String> invoiceId) {
		this.invoiceId = invoiceId;
	}
	public List<String> getServiceCallDate() {
		return serviceCallDate;
	}
	public void setServiceCallDate(List<String> serviceCallDate) {
		this.serviceCallDate = serviceCallDate;
	}
	public List<String> getVehicleYear() {
		return vehicleYear;
	}
	public void setVehicleYear(List<String> vehicleYear) {
		this.vehicleYear = vehicleYear;
	}
	public List<String> getVehicleMake() {
		return vehicleMake;
	}
	public void setVehicleMake(List<String> vehicleMake) {
		this.vehicleMake = vehicleMake;
	}
	public List<String> getVehicleModel() {
		return vehicleModel;
	}
	public void setVehicleModel(List<String> vehicleModel) {
		this.vehicleModel = vehicleModel;
	}
	public List<String> getVehicleVIN() {
		return vehicleVIN;
	}
	public void setVehicleVIN(List<String> vehicleVIN) {
		this.vehicleVIN = vehicleVIN;
	}
	public List<String> getBalanceAmt() {
		return balanceAmt;
	}
	public void setBalanceAmt(List<String> balanceAmt) {
		this.balanceAmt = balanceAmt;
	}
	public List<String> getPaymentAmt() {
		return paymentAmt;
	}
	public void setPaymentAmt(List<String> paymentAmt) {
		this.paymentAmt = paymentAmt;
	}
	public List<String> getPaidInFull() {
		return paidInFull;
	}
	public void setPaidInFull(List<String> paidInFull) {
		this.paidInFull = paidInFull;
	}					
}