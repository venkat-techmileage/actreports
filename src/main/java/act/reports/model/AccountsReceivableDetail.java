package act.reports.model;

import java.util.List;

public class AccountsReceivableDetail {

	private String asOfDate = "";
	private String accountName = "";
	private String accountPrimaryContact = "";
	private String currentDue = "";
	private String thirtyDaysDue = "";
	private String sixtyDaysDue = "";
	private String nintyDaysDue = "";
	private String moreThanNintyDaysDue = "";
	private String total = "";
	List<AccountsReceivableDetailInfo> accountReceivableDetailInfo;
	
	public String getAsOfDate() {
		return asOfDate;
	}
	public void setAsOfDate(String asOfDate) {
		this.asOfDate = asOfDate;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountPrimaryContact() {
		return accountPrimaryContact;
	}
	public void setAccountPrimaryContact(String accountPrimaryContact) {
		this.accountPrimaryContact = accountPrimaryContact;
	}
	public String getCurrentDue() {
		return currentDue;
	}
	public void setCurrentDue(String currentDue) {
		this.currentDue = currentDue;
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
	public List<AccountsReceivableDetailInfo> getAccountReceivableDetailInfo() {
		return accountReceivableDetailInfo;
	}
	public void setAccountReceivableDetailInfo(
			List<AccountsReceivableDetailInfo> accountReceivableDetailInfo) {
		this.accountReceivableDetailInfo = accountReceivableDetailInfo;
	}					
}