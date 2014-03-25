package act.reports.model;

public class SingleDriverCommissionDetails {
	
	private String date = "";
	private String invoiceId = "";
	private String towType = "";
	private String totalCharge = "";
	private String storageCharge = "";
	private String commissionAmount = "";
	
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
	public String getTowType() {
		return towType;
	}
	public void setTowType(String towType) {
		this.towType = towType;
	}
	public String getTotalCharge() {
		return totalCharge;
	}
	public void setTotalCharge(String totalCharge) {
		this.totalCharge = totalCharge;
	}	
	public String getStorageCharge() {
		return storageCharge;
	}
	public void setStorageCharge(String storageCharge) {
		this.storageCharge = storageCharge;
	}
	public String getCommissionAmount() {
		return commissionAmount;
	}
	public void setCommissionAmount(String commissionAmount) {
		this.commissionAmount = commissionAmount;
	}		
}