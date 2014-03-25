package act.reports.model;

import java.util.List;

public class AccountReceivableSummary {

	private String asOfDate = "";
	private String currentTotal = "";
	private String thirtyDaysTotal = "";
	private String sixtyDaysTotal = "";
	private String nintyDaysTotal = "";
	private String moreThanNintyDaysTotal = "";
	private String grandTotal = "";
	List<AccountReceivableSummaryDetails> accountReceivableSummaryDetails;
	
	public String getAsOfDate() {
		return asOfDate;
	}
	public void setAsOfDate(String asOfDate) {
		this.asOfDate = asOfDate;
	}
	public String getCurrentTotal() {
		return currentTotal;
	}
	public void setCurrentTotal(String currentTotal) {
		this.currentTotal = currentTotal;
	}
	public String getThirtyDaysTotal() {
		return thirtyDaysTotal;
	}
	public void setThirtyDaysTotal(String thirtyDaysTotal) {
		this.thirtyDaysTotal = thirtyDaysTotal;
	}
	public String getSixtyDaysTotal() {
		return sixtyDaysTotal;
	}
	public void setSixtyDaysTotal(String sixtyDaysTotal) {
		this.sixtyDaysTotal = sixtyDaysTotal;
	}
	public String getNintyDaysTotal() {
		return nintyDaysTotal;
	}
	public void setNintyDaysTotal(String nintyDaysTotal) {
		this.nintyDaysTotal = nintyDaysTotal;
	}
	public String getMoreThanNintyDaysTotal() {
		return moreThanNintyDaysTotal;
	}
	public void setMoreThanNintyDaysTotal(String moreThanNintyDaysTotal) {
		this.moreThanNintyDaysTotal = moreThanNintyDaysTotal;
	}
	public String getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(String grandTotal) {
		this.grandTotal = grandTotal;
	}
	public List<AccountReceivableSummaryDetails> getAccountReceivableSummaryDetails() {
		return accountReceivableSummaryDetails;
	}
	public void setAccountReceivableSummaryDetails(
			List<AccountReceivableSummaryDetails> accountReceivableSummaryDetails) {
		this.accountReceivableSummaryDetails = accountReceivableSummaryDetails;
	}				
}