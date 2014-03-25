package act.reports.model;

public class DriverCommissionSummaryDetails {
	
	private String payrollId = "";
	private String userId = "";
	private String fullName = "";
	private String startDate = "";
	private String level = "";
	private String noOfTows = "";
	private String weeklyBase = "";
	private String commission = "";
	private String commissionAdj = "";
	private String totalPay = "";
	
	public String getPayrollId() {
		return payrollId;
	}
	public void setPayrollId(String payrollId) {
		this.payrollId = payrollId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getNoOfTows() {
		return noOfTows;
	}
	public void setNoOfTows(String noOfTows) {
		this.noOfTows = noOfTows;
	}
	public String getWeeklyBase() {
		return weeklyBase;
	}
	public void setWeeklyBase(String weeklyBase) {
		this.weeklyBase = weeklyBase;
	}
	public String getCommission() {
		return commission;
	}
	public void setCommission(String commission) {
		this.commission = commission;
	}
	public String getCommissionAdj() {
		return commissionAdj;
	}
	public void setCommissionAdj(String commissionAdj) {
		this.commissionAdj = commissionAdj;
	}
	public String getTotalPay() {
		return totalPay;
	}
	public void setTotalPay(String totalPay) {
		this.totalPay = totalPay;
	}			
}