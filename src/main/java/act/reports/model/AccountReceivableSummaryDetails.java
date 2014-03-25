package act.reports.model;

public class AccountReceivableSummaryDetails {
	
	private String invoiceDate = "";
	private String accountName = "";
	private String current = "";
	private String thirtyDaysDue = "";
	private String sixtyDaysDue = "";
	private String nintyDaysDue = "";
	private String moreThanNintyDaysDue = "";
	private String total = "";
	
	public String getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getCurrent() {
		return current;
	}
	public void setCurrent(String current) {
		this.current = current;
	}	
	public String getThirtyDaysDue() {
		return thirtyDaysDue;
	}
	public void setThirtyDaysDue(String thirtyDaysDue) {
		this.thirtyDaysDue = thirtyDaysDue;
	}
	public String getSixtyDaysDue() {
		return sixtyDaysDue;
	}
	public void setSixtyDaysDue(String sixtyDaysDue) {
		this.sixtyDaysDue = sixtyDaysDue;
	}
	public String getNintyDaysDue() {
		return nintyDaysDue;
	}
	public void setNintyDaysDue(String nintyDaysDue) {
		this.nintyDaysDue = nintyDaysDue;
	}
	public String getMoreThanNintyDaysDue() {
		return moreThanNintyDaysDue;
	}
	public void setMoreThanNintyDaysDue(String moreThanNintyDaysDue) {
		this.moreThanNintyDaysDue = moreThanNintyDaysDue;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}	
}
