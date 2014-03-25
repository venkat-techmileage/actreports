package act.reports.model;

import java.util.List;

public class ReceiptDetailsList {

	private List<String> invoiceId;
	private List<String> account;
	private List<String> billTo;
	private List<String> paymentMethod;
	private List<String> amount;
	private List<String> userId;
	private List<String> location;
	
	public List<String> getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(List<String> invoiceId) {
		this.invoiceId = invoiceId;
	}
	public List<String> getAccount() {
		return account;
	}
	public void setAccount(List<String> account) {
		this.account = account;
	}
	public List<String> getBillTo() {
		return billTo;
	}
	public void setBillTo(List<String> billTo) {
		this.billTo = billTo;
	}
	public List<String> getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(List<String> paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public List<String> getAmount() {
		return amount;
	}
	public void setAmount(List<String> amount) {
		this.amount = amount;
	}	
	public List<String> getUserId() {
		return userId;
	}
	public void setUserId(List<String> userId) {
		this.userId = userId;
	}
	public List<String> getLocation() {
		return location;
	}
	public void setLocation(List<String> location) {
		this.location = location;
	}	
}
