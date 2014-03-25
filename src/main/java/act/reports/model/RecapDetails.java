package act.reports.model;

import java.util.List;

public class RecapDetails {

	private List<RecapSalesSummary> recapSalesSummaryList;
	private String salesSummaryTotalTows = "";
	private String salesSummaryTotalCharges = "";
	private String salesSummaryAvgChargePerTow = "";
	
	private String impoundTowCharges = "";
	private String impoundStorageCharges = "";
	private String impoundMileageCharges = "";
	private String impoundOtherCharges = "";
	private String impoundDiscounts = "";
	private String totalImpoundCharges = "";
	
	private String cashReceiptsTotal = "";
	private String chequeReceiptsTotal = "";
	private String creditReceiptsTotal = "";
	private String transferReceiptsTotal = "";
	private String employeeAdvanceTotal = "";
	private String totalReceipts = "";
	
	private String totalCalls = "";
	private String totalCompletedCalls = "";
	private String totalCancelledCalls = "";
	private String totalInProgressCalls = "";
	private String totalOnHoldCalls = "";
	private String avgCallToArrivalTime = "";
	
	private String beginingImpundAsOfDate = "";
	private String endingImpundAsOfDate = "";
	private RecapDateRangeDetails recapDateRangeDetails;
	private RecapCompareToDetails recapCompareToDetails;
	
	public List<RecapSalesSummary> getRecapSalesSummaryList() {
		return recapSalesSummaryList;
	}
	public void setRecapSalesSummaryList(
			List<RecapSalesSummary> recapSalesSummaryList) {
		this.recapSalesSummaryList = recapSalesSummaryList;
	}
	public String getSalesSummaryTotalTows() {
		return salesSummaryTotalTows;
	}
	public void setSalesSummaryTotalTows(String salesSummaryTotalTows) {
		this.salesSummaryTotalTows = salesSummaryTotalTows;
	}
	public String getSalesSummaryTotalCharges() {
		return salesSummaryTotalCharges;
	}
	public void setSalesSummaryTotalCharges(String salesSummaryTotalCharges) {
		this.salesSummaryTotalCharges = salesSummaryTotalCharges;
	}
	public String getSalesSummaryAvgChargePerTow() {
		return salesSummaryAvgChargePerTow;
	}
	public void setSalesSummaryAvgChargePerTow(String salesSummaryAvgChargePerTow) {
		this.salesSummaryAvgChargePerTow = salesSummaryAvgChargePerTow;
	}
	public String getImpoundTowCharges() {
		return impoundTowCharges;
	}
	public void setImpoundTowCharges(String impoundTowCharges) {
		this.impoundTowCharges = impoundTowCharges;
	}
	public String getImpoundStorageCharges() {
		return impoundStorageCharges;
	}
	public void setImpoundStorageCharges(String impoundStorageCharges) {
		this.impoundStorageCharges = impoundStorageCharges;
	}
	public String getImpoundMileageCharges() {
		return impoundMileageCharges;
	}
	public void setImpoundMileageCharges(String impoundMileageCharges) {
		this.impoundMileageCharges = impoundMileageCharges;
	}
	public String getImpoundOtherCharges() {
		return impoundOtherCharges;
	}
	public void setImpoundOtherCharges(String impoundOtherCharges) {
		this.impoundOtherCharges = impoundOtherCharges;
	}
	public String getImpoundDiscounts() {
		return impoundDiscounts;
	}
	public void setImpoundDiscounts(String impoundDiscounts) {
		this.impoundDiscounts = impoundDiscounts;
	}
	public String getTotalImpoundCharges() {
		return totalImpoundCharges;
	}
	public void setTotalImpoundCharges(String totalImpoundCharges) {
		this.totalImpoundCharges = totalImpoundCharges;
	}
	public String getCashReceiptsTotal() {
		return cashReceiptsTotal;
	}
	public void setCashReceiptsTotal(String cashReceiptsTotal) {
		this.cashReceiptsTotal = cashReceiptsTotal;
	}
	public String getChequeReceiptsTotal() {
		return chequeReceiptsTotal;
	}
	public void setChequeReceiptsTotal(String chequeReceiptsTotal) {
		this.chequeReceiptsTotal = chequeReceiptsTotal;
	}
	public String getCreditReceiptsTotal() {
		return creditReceiptsTotal;
	}
	public void setCreditReceiptsTotal(String creditReceiptsTotal) {
		this.creditReceiptsTotal = creditReceiptsTotal;
	}
	public String getTransferReceiptsTotal() {
		return transferReceiptsTotal;
	}
	public void setTransferReceiptsTotal(String transferReceiptsTotal) {
		this.transferReceiptsTotal = transferReceiptsTotal;
	}
	public String getEmployeeAdvanceTotal() {
		return employeeAdvanceTotal;
	}
	public void setEmployeeAdvanceTotal(String employeeAdvanceTotal) {
		this.employeeAdvanceTotal = employeeAdvanceTotal;
	}
	public String getTotalReceipts() {
		return totalReceipts;
	}
	public void setTotalReceipts(String totalReceipts) {
		this.totalReceipts = totalReceipts;
	}
	public String getTotalCalls() {
		return totalCalls;
	}
	public void setTotalCalls(String totalCalls) {
		this.totalCalls = totalCalls;
	}
	public String getTotalCompletedCalls() {
		return totalCompletedCalls;
	}
	public void setTotalCompletedCalls(String totalCompletedCalls) {
		this.totalCompletedCalls = totalCompletedCalls;
	}
	public String getTotalCancelledCalls() {
		return totalCancelledCalls;
	}
	public void setTotalCancelledCalls(String totalCancelledCalls) {
		this.totalCancelledCalls = totalCancelledCalls;
	}
	public String getTotalInProgressCalls() {
		return totalInProgressCalls;
	}
	public void setTotalInProgressCalls(String totalInProgressCalls) {
		this.totalInProgressCalls = totalInProgressCalls;
	}
	public String getTotalOnHoldCalls() {
		return totalOnHoldCalls;
	}
	public void setTotalOnHoldCalls(String totalOnHoldCalls) {
		this.totalOnHoldCalls = totalOnHoldCalls;
	}
	public String getAvgCallToArrivalTime() {
		return avgCallToArrivalTime;
	}
	public void setAvgCallToArrivalTime(String avgCallToArrivalTime) {
		this.avgCallToArrivalTime = avgCallToArrivalTime;
	}
	public RecapDateRangeDetails getRecapDateRangeDetails() {
		return recapDateRangeDetails;
	}
	public String getBeginingImpundAsOfDate() {
		return beginingImpundAsOfDate;
	}
	public void setBeginingImpundAsOfDate(String beginingImpundAsOfDate) {
		this.beginingImpundAsOfDate = beginingImpundAsOfDate;
	}
	public String getEndingImpundAsOfDate() {
		return endingImpundAsOfDate;
	}
	public void setEndingImpundAsOfDate(String endingImpundAsOfDate) {
		this.endingImpundAsOfDate = endingImpundAsOfDate;
	}
	public void setRecapDateRangeDetails(RecapDateRangeDetails recapDateRangeDetails) {
		this.recapDateRangeDetails = recapDateRangeDetails;
	}
	public RecapCompareToDetails getRecapCompareToDetails() {
		return recapCompareToDetails;
	}
	public void setRecapCompareToDetails(RecapCompareToDetails recapCompareToDetails) {
		this.recapCompareToDetails = recapCompareToDetails;
	}	
}