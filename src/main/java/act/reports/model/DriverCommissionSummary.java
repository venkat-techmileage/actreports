package act.reports.model;

import java.util.List;

public class DriverCommissionSummary {

	private String fromDate = "";
	private String toDate = "";
	private String countOfTows = "";
	private String weeklyBaseTotal = "";
	private String totalCommission = "";
	private String totalCommissionAdj = "";
	private String sumOfTotalPay = "";
	List<DriverCommissionSummaryDetails> driverCommissionSummaryDetailsList;
	
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getCountOfTows() {
		return countOfTows;
	}
	public void setCountOfTows(String countOfTows) {
		this.countOfTows = countOfTows;
	}
	public String getWeeklyBaseTotal() {
		return weeklyBaseTotal;
	}
	public void setWeeklyBaseTotal(String weeklyBaseTotal) {
		this.weeklyBaseTotal = weeklyBaseTotal;
	}
	public String getTotalCommission() {
		return totalCommission;
	}
	public void setTotalCommission(String totalCommission) {
		this.totalCommission = totalCommission;
	}
	public String getTotalCommissionAdj() {
		return totalCommissionAdj;
	}
	public void setTotalCommissionAdj(String totalCommissionAdj) {
		this.totalCommissionAdj = totalCommissionAdj;
	}
	public String getSumOfTotalPay() {
		return sumOfTotalPay;
	}
	public void setSumOfTotalPay(String sumOfTotalPay) {
		this.sumOfTotalPay = sumOfTotalPay;
	}
	public List<DriverCommissionSummaryDetails> getDriverCommissionSummaryDetailsList() {
		return driverCommissionSummaryDetailsList;
	}
	public void setDriverCommissionSummaryDetailsList(
			List<DriverCommissionSummaryDetails> driverCommissionSummaryDetailsList) {
		this.driverCommissionSummaryDetailsList = driverCommissionSummaryDetailsList;
	}				
}