package act.reports.model;

import java.util.List;

public class DispatchedInvoices {

	private String fromDate = "";
	private String toDate = "";
	private String totalInvoices = "";
	List<DispatchedInvoiceDetails> dispatchedInvoiceDetails;
	
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
	public String getTotalInvoices() {
		return totalInvoices;
	}
	public void setTotalInvoices(String totalInvoices) {
		this.totalInvoices = totalInvoices;
	}
	public List<DispatchedInvoiceDetails> getDispatchedInvoiceDetails() {
		return dispatchedInvoiceDetails;
	}
	public void setDispatchedInvoiceDetails(
			List<DispatchedInvoiceDetails> dispatchedInvoiceDetails) {
		this.dispatchedInvoiceDetails = dispatchedInvoiceDetails;
	}			
}