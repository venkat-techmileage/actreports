package act.reports.model;

import java.util.List;

public class SummaryByAccountRatePlan {
	
	private String fromDate = "";
	private String toDate = "";
	private String totalTows = "";
	private String sumOfTotalCharges = "";
	private String avgChargePerTow = "";
	List<AccountRatePlanDetails> accountRatePlanDetails;
	
	public List<AccountRatePlanDetails> getAccountRatePlanDetails() {
		return accountRatePlanDetails;
	}
	public void setAccountRatePlanDetails(
			List<AccountRatePlanDetails> accountRatePlanDetails) {
		this.accountRatePlanDetails = accountRatePlanDetails;
	}
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
	public String getTotalTows() {
		return totalTows;
	}
	public void setTotalTows(String totalTows) {
		this.totalTows = totalTows;
	}
	public String getSumOfTotalCharges() {
		return sumOfTotalCharges;
	}
	public void setSumOfTotalCharges(String sumOfTotalCharges) {
		this.sumOfTotalCharges = sumOfTotalCharges;
	}
	public String getAvgChargePerTow() {
		return avgChargePerTow;
	}
	public void setAvgChargePerTow(String avgChargePerTow) {
		this.avgChargePerTow = avgChargePerTow;
	}
}
