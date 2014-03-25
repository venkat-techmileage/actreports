package act.reports.model;

import java.util.List;

public class AccountReceivableSummaryDetailsList {

	private List<String> accountName;
	private List<String> current;
	private List<String> thirtyDaysDue;
	private List<String> sixtyDaysDue;
	private List<String> nintyDaysDue;
	private List<String> moreThanNintyDaysDue;
	private List<String> total;
	
	public List<String> getAccountName() {
		return accountName;
	}
	public void setAccountName(List<String> accountName) {
		this.accountName = accountName;
	}
	public List<String> getCurrent() {
		return current;
	}
	public void setCurrent(List<String> current) {
		this.current = current;
	}
	public List<String> getThirtyDaysDue() {
		return thirtyDaysDue;
	}
	public void setThirtyDaysDue(List<String> thirtyDaysDue) {
		this.thirtyDaysDue = thirtyDaysDue;
	}
	public List<String> getSixtyDaysDue() {
		return sixtyDaysDue;
	}
	public void setSixtyDaysDue(List<String> sixtyDaysDue) {
		this.sixtyDaysDue = sixtyDaysDue;
	}
	public List<String> getNintyDaysDue() {
		return nintyDaysDue;
	}
	public void setNintyDaysDue(List<String> nintyDaysDue) {
		this.nintyDaysDue = nintyDaysDue;
	}
	public List<String> getMoreThanNintyDaysDue() {
		return moreThanNintyDaysDue;
	}
	public void setMoreThanNintyDaysDue(List<String> moreThanNintyDaysDue) {
		this.moreThanNintyDaysDue = moreThanNintyDaysDue;
	}
	public List<String> getTotal() {
		return total;
	}
	public void setTotal(List<String> total) {
		this.total = total;
	}				
}
