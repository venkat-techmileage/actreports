package act.reports.model;

import java.util.List;

public class SummaryByTruck {
	
	private String fromDate = "";
	private String toDate = "";
	private String totalTows = "";
	private String sumOfTotalCharges = "";
	private String avgChargePerTow = "";
	private String actualMileageTotal = "";
	private String avgMilesPerTow = "";
	List<TruckDetails> truckDetails;
	
	public List<TruckDetails> getTruckDetails() {
		return truckDetails;
	}
	public void setTruckDetails(List<TruckDetails> truckDetails) {
		this.truckDetails = truckDetails;
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
	public String getActualMileageTotal() {
		return actualMileageTotal;
	}
	public void setActualMileageTotal(String actualMileageTotal) {
		this.actualMileageTotal = actualMileageTotal;
	}
	public String getAvgMilesPerTow() {
		return avgMilesPerTow;
	}
	public void setAvgMilesPerTow(String avgMilesPerTow) {
		this.avgMilesPerTow = avgMilesPerTow;
	}
}
