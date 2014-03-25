package act.reports.model;

import java.util.List;

public class MultipleInvoicePayment {

	private String accountName = "";
	private String totalOpenInvoices = "";
	private String msg = "";
	List<MultipleInvoicePaymentDetails> multipleInvoicePaymentDetailsList;
	
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}	
	public String getTotalOpenInvoices() {
		return totalOpenInvoices;
	}
	public void setTotalOpenInvoices(String totalOpenInvoices) {
		this.totalOpenInvoices = totalOpenInvoices;
	}	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<MultipleInvoicePaymentDetails> getMultipleInvoicePaymentDetailsList() {
		return multipleInvoicePaymentDetailsList;
	}
	public void setMultipleInvoicePaymentDetailsList(
			List<MultipleInvoicePaymentDetails> multipleInvoicePaymentDetailsList) {
		this.multipleInvoicePaymentDetailsList = multipleInvoicePaymentDetailsList;
	}	
}