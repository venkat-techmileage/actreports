package act.reports.model;

import java.util.List;

public class AccountsReceivableDetailByAccType {

	private String asOfDate = "";
	private String accountType = "";
	private String currentDue = "";
	private String thirtyDaysDue = "";
	private String sixtyDaysDue = "";
	private String nintyDaysDue = "";
	private String moreThanNintyDaysDue = "";
	private String total = "";
	List<AccountsReceivableAccTypeDetail> accountsReceivableAccTypeDetailList;
	
	public String getAsOfDate() {
		return asOfDate;
	}
	public void setAsOfDate(String asOfDate) {
		this.asOfDate = asOfDate;
	}	
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public List<AccountsReceivableAccTypeDetail> getAccountsReceivableAccTypeDetailList() {
		return accountsReceivableAccTypeDetailList;
	}
	public void setAccountsReceivableAccTypeDetailList(
			List<AccountsReceivableAccTypeDetail> accountsReceivableAccTypeDetailList) {
		this.accountsReceivableAccTypeDetailList = accountsReceivableAccTypeDetailList;
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
}