package act.reports.model;

import java.util.List;

public class SingleDriverCommissionDetailsList {

	private List<String> date;
	private List<String> invoiceId;
	private List<String> towType;
	private List<String> totalCharge;
	private List<String> totalStorageCharge;
	private List<String> commissionAmount;
	
	public List<String> getDate() {
		return date;
	}
	public void setDate(List<String> date) {
		this.date = date;
	}
	public List<String> getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(List<String> invoiceId) {
		this.invoiceId = invoiceId;
	}
	public List<String> getTowType() {
		return towType;
	}
	public void setTowType(List<String> towType) {
		this.towType = towType;
	}
	public List<String> getTotalCharge() {
		return totalCharge;
	}
	public void setTotalCharge(List<String> totalCharge) {
		this.totalCharge = totalCharge;
	}
	public List<String> getTotalStorageCharge() {
		return totalStorageCharge;
	}
	public void setTotalStorageCharge(List<String> totalStorageCharge) {
		this.totalStorageCharge = totalStorageCharge;
	}
	public List<String> getCommissionAmount() {
		return commissionAmount;
	}
	public void setCommissionAmount(List<String> commissionAmount) {
		this.commissionAmount = commissionAmount;
	}		
}