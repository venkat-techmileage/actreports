package act.reports.model;

import java.util.List;

public class DriverCommissionSummaryDetailsList {

	private List<String> payrollId;
	private List<String> userId;
	private List<String> fullName;
	private List<String> startDate;
	private List<String> level;
	private List<String> noOfTows;
	private List<String> weeklyBase;
	private List<String> commission;
	private List<String> commissionAdj;
	private List<String> totalPay;
	
	public List<String> getPayrollId() {
		return payrollId;
	}
	public void setPayrollId(List<String> payrollId) {
		this.payrollId = payrollId;
	}
	public List<String> getUserId() {
		return userId;
	}
	public void setUserId(List<String> userId) {
		this.userId = userId;
	}
	public List<String> getFullName() {
		return fullName;
	}
	public void setFullName(List<String> fullName) {
		this.fullName = fullName;
	}
	public List<String> getStartDate() {
		return startDate;
	}
	public void setStartDate(List<String> startDate) {
		this.startDate = startDate;
	}
	public List<String> getLevel() {
		return level;
	}
	public void setLevel(List<String> level) {
		this.level = level;
	}
	public List<String> getNoOfTows() {
		return noOfTows;
	}
	public void setNoOfTows(List<String> noOfTows) {
		this.noOfTows = noOfTows;
	}
	public List<String> getWeeklyBase() {
		return weeklyBase;
	}
	public void setWeeklyBase(List<String> weeklyBase) {
		this.weeklyBase = weeklyBase;
	}
	public List<String> getCommission() {
		return commission;
	}
	public void setCommission(List<String> commission) {
		this.commission = commission;
	}
	public List<String> getCommissionAdj() {
		return commissionAdj;
	}
	public void setCommissionAdj(List<String> commissionAdj) {
		this.commissionAdj = commissionAdj;
	}
	public List<String> getTotalPay() {
		return totalPay;
	}
	public void setTotalPay(List<String> totalPay) {
		this.totalPay = totalPay;
	}			
}