package act.reports.model;

import java.util.List;

public class DriverSalesVsCommissionDetailsList {

	private List<String> saleType;
	private List<String> salesAmount;
	private List<String> commissionType;
	private List<String> commissionAmount;
	private List<String> hoursOrInvoices;
	private List<String> ratePerHourOrInvoice;
	
	public List<String> getSaleType() {
		return saleType;
	}
	public void setSaleType(List<String> saleType) {
		this.saleType = saleType;
	}
	public List<String> getSalesAmount() {
		return salesAmount;
	}
	public void setSalesAmount(List<String> salesAmount) {
		this.salesAmount = salesAmount;
	}
	public List<String> getCommissionType() {
		return commissionType;
	}
	public void setCommissionType(List<String> commissionType) {
		this.commissionType = commissionType;
	}
	public List<String> getCommissionAmount() {
		return commissionAmount;
	}
	public void setCommissionAmount(List<String> commissionAmount) {
		this.commissionAmount = commissionAmount;
	}
	public List<String> getHoursOrInvoices() {
		return hoursOrInvoices;
	}
	public void setHoursOrInvoices(List<String> hoursOrInvoices) {
		this.hoursOrInvoices = hoursOrInvoices;
	}
	public List<String> getRatePerHourOrInvoice() {
		return ratePerHourOrInvoice;
	}
	public void setRatePerHourOrInvoice(List<String> ratePerHourOrInvoice) {
		this.ratePerHourOrInvoice = ratePerHourOrInvoice;
	}		
}