package act.reports.model;

import java.util.List;

public class Receipts {

	private String location = "";
	private String fromDate = "";
	private String toDate = "";
	private String reportDate = "";
	private String paymentTypeTotal = "";
	List<ReceiptDetails> receiptDetails;
	List<ReceiptTotals> receiptsTotals;
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
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
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public String getPaymentTypeTotal() {
		return paymentTypeTotal;
	}
	public void setPaymentTypeTotal(String paymentTypeTotal) {
		this.paymentTypeTotal = paymentTypeTotal;
	}
	public List<ReceiptDetails> getReceiptDetails() {
		return receiptDetails;
	}
	public void setReceiptDetails(List<ReceiptDetails> receiptDetails) {
		this.receiptDetails = receiptDetails;
	}
	public List<ReceiptTotals> getReceiptsTotals() {
		return receiptsTotals;
	}
	public void setReceiptsTotals(List<ReceiptTotals> receiptsTotals) {
		this.receiptsTotals = receiptsTotals;
	}	
}