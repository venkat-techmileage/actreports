package act.reports.model;

import java.util.List;

public class MultipleInvoicePaymentDetailsUpdate {

	private List<String> invoiceId;
	private List<String> paymentType;
	private List<String> paymentAmt;
	private List<String> chequeNo;
	private List<String> accountName;
	private List<String> ccTxNo;
	private List<String> userId;
	private List<String> paymentLoc;
		
	public List<String> getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(List<String> invoiceId) {
		this.invoiceId = invoiceId;
	}
	public List<String> getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(List<String> paymentType) {
		this.paymentType = paymentType;
	}
	public List<String> getPaymentAmt() {
		return paymentAmt;
	}
	public void setPaymentAmt(List<String> paymentAmt) {
		this.paymentAmt = paymentAmt;
	}
	public List<String> getChequeNo() {
		return chequeNo;
	}
	public void setChequeNo(List<String> chequeNo) {
		this.chequeNo = chequeNo;
	}
	public List<String> getCcTxNo() {
		return ccTxNo;
	}
	public void setCcTxNo(List<String> ccTxNo) {
		this.ccTxNo = ccTxNo;
	}	
	public List<String> getAccountName() {
		return accountName;
	}
	public void setAccountName(List<String> accountName) {
		this.accountName = accountName;
	}
	public List<String> getUserId() {
		return userId;
	}
	public void setUserId(List<String> userId) {
		this.userId = userId;
	}
	public List<String> getPaymentLoc() {
		return paymentLoc;
	}
	public void setPaymentLoc(List<String> paymentLoc) {
		this.paymentLoc = paymentLoc;
	}							
}