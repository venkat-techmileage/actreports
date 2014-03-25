package act.reports.model;

import java.util.List;

public class BillingDetails {

	private String accountName = "";
	private String currentTotal = "";
	private String thirtyDaysTotal = "";
	private String sixtyDaysTotal = "";
	private String nintyDaysTotal = "";
	private String moreThanNintyDaysTotal = "";
	private String grandTotal = "";	
	List<BillingInfoDetails> billingInfoDetailsList;
	
	
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
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
	public List<BillingInfoDetails> getBillingInfoDetailsList() {
		return billingInfoDetailsList;
	}
	public void setBillingInfoDetailsList(
			List<BillingInfoDetails> billingInfoDetailsList) {
		this.billingInfoDetailsList = billingInfoDetailsList;
	}					
}