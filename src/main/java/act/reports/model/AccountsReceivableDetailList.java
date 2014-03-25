package act.reports.model;

import java.util.List;

public class AccountsReceivableDetailList {

	private List<String> invoiceId;
	private List<String> serviceCallDate;
	private List<String> dueDate;
	private List<String> age;
	private List<String> balance;
	
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
	public List<String> getDueDate() {
		return dueDate;
	}
	public void setDueDate(List<String> dueDate) {
		this.dueDate = dueDate;
	}
	public List<String> getAge() {
		return age;
	}
	public void setAge(List<String> age) {
		this.age = age;
	}
	public List<String> getBalance() {
		return balance;
	}
	public void setBalance(List<String> balance) {
		this.balance = balance;
	}					
}
