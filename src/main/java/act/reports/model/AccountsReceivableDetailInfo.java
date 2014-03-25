package act.reports.model;

public class AccountsReceivableDetailInfo {
	
	private String invoiceId = "";
	private String serviceCallDate = "";
	private String dueDate = "";
	private String age = "";
	private String balance = "";
	
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getServiceCallDate() {
		return serviceCallDate;
	}
	public void setServiceCallDate(String serviceCallDate) {
		this.serviceCallDate = serviceCallDate;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}		
}
